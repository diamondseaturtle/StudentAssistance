import java.util.GregorianCalendar;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;

import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.Component;
import java.awt.Container;
import java.awt.Color;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CalendarScreen extends Screen{
    
    private static JLabel currMonth, lblYear;
    private static JButton btnPrev, btnNext;
    private static JTable tblCalendar;
    private static JComboBox cmbYear;
    private static JFrame frmMain;
    private static DefaultTableModel mtblCalendar; // Table model
    private static JScrollPane stblCalendar; // The scrollpanel
    private static int realYear, realMonth, realDay, currentYear, currentMonth;
    
    public CalendarScreen(){
        
        backButton();
        
        // Get real month/year
        
        GregorianCalendar cal = new GregorianCalendar(); 
        realDay = cal.get(GregorianCalendar.DAY_OF_MONTH); // Get day
        realMonth = cal.get(GregorianCalendar.MONTH); // Get month
        realYear = cal.get(GregorianCalendar.YEAR); // Get year
        currentMonth = realMonth; // Match month and year
        currentYear = realYear;
        
        mtblCalendar = new DefaultTableModel()
        {
            public boolean isCellEditable(int rowIndex, int mColIndex){
                return false;
            }
        };
        
        final int calendarWidth = 600;
        final int calendarHeight = 390; // Optimally divisible by 6
        
        tblCalendar = new JTable(mtblCalendar);
        tblCalendar.setGridColor(Window.colorBorder); // Border inside grid
        tblCalendar.setBorder(BorderFactory.createLineBorder(Window.colorBorder)); // Border outside grid
        tblCalendar.setRowHeight(
            (calendarHeight-30) // Top column is set at 30 pixels
            /6 // Divide by 6 for columns
        ); 
       
        stblCalendar = new JScrollPane(tblCalendar);
        stblCalendar.setBorder(BorderFactory.createLineBorder(Window.colorBox)); 
        stblCalendar.setBounds(
            1280/2 - calendarWidth/2,   
            720/2 - calendarHeight/2, 
            calendarWidth, 
            calendarHeight+1 // +1 is to account for scrollbar; must be larger than bounds
        ); 
        panel.add(stblCalendar);
        
        // Add headers
        
        String[] headers = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"}; //All headers
        for (int i=0; i<7; i++){
            mtblCalendar.addColumn(headers[i]);
        }
        
        // Header settings
        tblCalendar.getTableHeader().setFont(Window.getFont(20));
        tblCalendar.getTableHeader().setBackground(Window.colorBox);
        tblCalendar.getTableHeader().setForeground(Color.WHITE);
        tblCalendar.getTableHeader().setBorder(BorderFactory.createLineBorder(Window.colorBorder));
        
        tblCalendar.getTableHeader().setResizingAllowed(false);
        tblCalendar.getTableHeader().setReorderingAllowed(false);
        
        // Single cell selection
        tblCalendar.setColumnSelectionAllowed(true);
        tblCalendar.setRowSelectionAllowed(true);
        tblCalendar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Set row/column count
        mtblCalendar.setColumnCount(7);
        mtblCalendar.setRowCount(6);
        
        currMonth = new JLabel ("January");
        currMonth.setFont(Window.getFont(28));
        currMonth.setForeground(Window.colorText);
        currMonth.setBounds(
            160-currMonth.getPreferredSize().width/2, 
            25, 
            100, 
            50
        );
        panel.add(currMonth);
        
        lblYear = new JLabel ("Change year:");
        lblYear.setFont(Window.getFont(28));
        lblYear.setForeground(Window.colorText);
        lblYear.setBounds(10, 305, 80, 20); 
        // panel.add(lblYear);
        
        cmbYear = new JComboBox();
        cmbYear.setFont(Window.getFont(28));
        cmbYear.setForeground(Window.colorText);
        cmbYear.setBackground(Window.colorBox);
        cmbYear.setBorder(null);
        cmbYear.setBounds(230, 305, 80, 20);
        cmbYear.addActionListener(new cmbYear_Action());
        // panel.add(cmbYear);
        
        btnPrev = new JButton ("<");
        btnPrev.setFont(Window.getFont(28));
        btnPrev.setForeground(Window.colorText);
        btnPrev.setBackground(Window.colorBox);
        btnPrev.setFocusPainted(false);
        btnPrev.setBorder(null);
        btnPrev.setBounds(10, 25, 50, 25);
        btnPrev.addActionListener(new btnPrev_Action());
        panel.add(btnPrev);
         
        btnNext = new JButton (">");
        btnNext.setFont(Window.getFont(28));
        btnNext.setForeground(Window.colorText);
        btnNext.setBackground(Window.colorBox);
        btnNext.setFocusPainted(false);
        btnNext.setBorder(null);
        btnNext.setBounds(260, 25, 50, 25);
        btnNext.addActionListener(new btnNext_Action());
        panel.add(btnNext);
        
        // Populate table.
        for (int i=realYear-100; i<=realYear+100; i++){
            cmbYear.addItem(String.valueOf(i));
        }
        
        // Refresh calendar
        refreshCalendar (realMonth, realYear);
    }
    
    public static void refreshCalendar(int month, int year){
        // Variables
        String[] months =  {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        int nod, som; //Number Of Days, Start Of Month
        
        // Allow/disallow buttons
        btnPrev.setEnabled(true);
        btnNext.setEnabled(true);
        
        // Too early
        if (month == 0 && year <= realYear-10){
            btnPrev.setEnabled(false);
        } 
        
        // Too late
        if (month == 11 && year >= realYear+100){
            btnNext.setEnabled(false);
        } 
        
        currMonth.setText(months[month]); //Refresh the month label (at the top)
        currMonth.setBounds(160-currMonth.getPreferredSize().width/2, 25, 180, 25); // Re-align label with calendar
        cmbYear.setSelectedItem(String.valueOf(year)); //Select the correct year in the combo box
        
        // Clear table
        for (int i=0; i<6; i++){
            for (int j=0; j<7; j++){
                mtblCalendar.setValueAt(null, i, j);
            }
        }
        
        // Get first day of month and number of days
        GregorianCalendar cal = new GregorianCalendar(year, month, 1);
        nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        som = cal.get(GregorianCalendar.DAY_OF_WEEK);
        
        // Draw calendar
        for (int i=1; i<=nod; i++){
            int row = new Integer((i+som-2)/7);
            int column  =  (i+som-2)%7;
            mtblCalendar.setValueAt(i, row, column);
        }
        
        // Apply renderers
        tblCalendar.setDefaultRenderer(tblCalendar.getColumnClass(0), new tblCalendarRenderer());
    }
    
    static class tblCalendarRenderer extends DefaultTableCellRenderer{
        public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column){
            super.getTableCellRendererComponent(table, value, selected, focused, row, column);
            if (column == 0 || column == 6){ //Week-end
                setBackground(Window.colorBox);
            }
            else{ //Week
                setBackground(Window.colorBox);
            }
            if (value != null){
                if (Integer.parseInt(value.toString()) == realDay && currentMonth == realMonth && currentYear == realYear){ //Today
                    setBackground(Window.colorBox);
                }
            }
            setBorder(null);
            setForeground(Window.colorText);
            setFont(Window.getFont(25));
            return this;
        }
    }
    
    static class btnPrev_Action implements ActionListener{
        public void actionPerformed (ActionEvent e){
            if (currentMonth == 0){ //Back one year
                currentMonth = 11;
                currentYear -= 1;
            }
            else{ //Back one month
                currentMonth -= 1;
            }
            refreshCalendar(currentMonth, currentYear);
        }
    }
    static class btnNext_Action implements ActionListener{
        public void actionPerformed (ActionEvent e){
            if (currentMonth == 11){ //Foward one year
                currentMonth = 0;
                currentYear += 1;
            }
            else{ //Foward one month
                currentMonth += 1;
            }
            refreshCalendar(currentMonth, currentYear);
        }
    }
    static class cmbYear_Action implements ActionListener{
        public void actionPerformed (ActionEvent e){
            if (cmbYear.getSelectedItem() != null){
                String b = cmbYear.getSelectedItem().toString();
                currentYear = Integer.parseInt(b);
                refreshCalendar(currentMonth, currentYear);
            }
        }
    }
}

