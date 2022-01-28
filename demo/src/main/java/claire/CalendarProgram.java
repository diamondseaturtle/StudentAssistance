package claire;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


public class CalendarProgram{
    static JLabel currMonth, lblYear, currMonthBig;
    static JButton btnPrev, btnNext, btnPrevBig, btnNextBig, btnCurrent;
    static JTable tblCalendar, tblCalendarBig;
    static JComboBox cmbYear;
    static JFrame frmMain;
    static Container pane;
    static DefaultTableModel mtblCalendar, mtblCalendarBig; //Table model
    static JScrollPane stblCalendar, stblCalendarBig; //The scrollpane
    static JPanel pnlCalendar, pnlCalendarBig;
    static int realYear, realMonth, realDay, currentYear, currentMonth;
    static Dimension screenSize;
    
    public static void main (String args[]){
        //Look and feel
        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
        catch (ClassNotFoundException e) {}
        catch (InstantiationException e) {}
        catch (IllegalAccessException e) {}
        catch (UnsupportedLookAndFeelException e) {}
        
        //Prepare frame
        frmMain = new JFrame ("Calendar"); //Create frame
//        frmMain.setSize(330, 375); //Set size to 400x400 pixels //previous dimensions (330,375)
        pane = frmMain.getContentPane(); //Get content pane
        pane.setLayout(null); //Apply null layout
        frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Close when X is clicked
        makeFrameFullSize(frmMain);
        
        //Create controls
        currMonth = new JLabel ("January");
        lblYear = new JLabel ("Change year:");
        cmbYear = new JComboBox();
        btnPrev = new JButton ("<");
        btnNext = new JButton (">");
        mtblCalendar = new DefaultTableModel(){public boolean isCellEditable(int rowIndex, int mColIndex){return false;}};
        tblCalendar = new JTable(mtblCalendar);
        stblCalendar = new JScrollPane(tblCalendar);
        pnlCalendar = new JPanel(null);
        
        // testing to create the controls for the big calendar
        currMonthBig = new JLabel("January");
        pnlCalendarBig = new JPanel(null);
        btnPrevBig = new JButton("Previous");
        btnNextBig = new JButton("Next");
        mtblCalendarBig = new DefaultTableModel(){public boolean isCellEditable(int rowIndex, int mColIndex){return false;}};
        tblCalendarBig = new JTable(mtblCalendarBig);
        stblCalendarBig = new JScrollPane(tblCalendarBig);
        btnCurrent = new JButton("Today");
        
        //Set border
        pnlCalendar.setBorder(BorderFactory.createTitledBorder("Calendar"));
        
        //testing to set border for the big calendar
        pnlCalendarBig.setBorder(BorderFactory.createTitledBorder(""));
        
        //Register action listeners
        btnPrev.addActionListener(new btnPrev_Action());
        btnNext.addActionListener(new btnNext_Action());
//        btnCurrent.addActionListener(new btnCurrent_Action());
        cmbYear.addActionListener(new cmbYear_Action());
        
        //testing to register action listeners
        btnPrevBig.addActionListener(new btnPrev_Action());
        btnNextBig.addActionListener(new btnNext_Action());
//        btnCurrent.addActionListener(new btnCurrent_Action());
        
        //Add controls to pane
        pane.add(pnlCalendar);
        pnlCalendar.add(currMonth);
        pnlCalendar.add(lblYear);
        pnlCalendar.add(cmbYear);
        pnlCalendar.add(btnPrev);
        pnlCalendar.add(btnNext);
        pnlCalendar.add(stblCalendar);
        
        //testing to add controls to pane for big calendar
        pane.add(pnlCalendarBig);
        pnlCalendarBig.add(currMonthBig);
        pnlCalendarBig.add(btnNextBig);
        pnlCalendarBig.add(btnPrevBig);
        pnlCalendarBig.add(btnCurrent);
        pnlCalendarBig.add(stblCalendarBig);

        
        //Set bounds
        pnlCalendar.setBounds(0, 5, screenSize.width/4, 335); //previous dimensions (0, 0, 320, 335)
        currMonth.setBounds(((screenSize.width/4) - (screenSize.width/7)), 25, 100, 25); //previous dimensions (160-currMonth.getPreferredSize().width/2, 25, 100, 25)
        lblYear.setBounds(10, 305, 80, 20); //previous dimensions (10, 305, 80, 20)
        cmbYear.setBounds(230, 305, 80, 20); //previous dimensions (230, 305, 80, 20)
        btnPrev.setBounds(10, 25, 50, 25); //previous dimensions (10, 25, 50, 25)
        btnNext.setBounds(260, 25, 50, 25); //previous dimensions (260, 25, 50, 25)
        stblCalendar.setBounds(10, 50, 300, 250); //previous dimensions (10, 50, 300, 250)
        
        //testing to set bounds for big calendar
        pnlCalendarBig.setBounds(350, 10, screenSize.width - 400, screenSize.height - 60);
        currMonthBig.setBounds(((screenSize.width - currMonth.WIDTH)/4) + 50, 50, 1000, 50); 
        currMonthBig.setFont(currMonthBig.getFont().deriveFont(24.0f));
        btnPrevBig.setBounds(25, 70, 100, 25);
        btnNextBig.setBounds(screenSize.width - 530, 70, 100, 25);
//        btnCurrent.setBounds(, 70, 100, );
        stblCalendarBig.setBounds(20, 100, screenSize.width - 445, 500);  
        
        //Make frame visible
        frmMain.setResizable(false);
        frmMain.setVisible(true);
        
        //Get real month/year
        GregorianCalendar cal = new GregorianCalendar(); //Create calendar
        realDay = cal.get(GregorianCalendar.DAY_OF_MONTH); //Get day
        realMonth = cal.get(GregorianCalendar.MONTH); //Get month
        realYear = cal.get(GregorianCalendar.YEAR); //Get year
        currentMonth = realMonth; //Match month and year
        currentYear = realYear;
        
        //Add headers
        String[] headers = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"}; //All headers
        for (int i=0; i<7; i++){
            mtblCalendar.addColumn(headers[i]);
        }
        
        //testing to add headers for the big calendar
        String[] headersBig = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"}; //All headers
        for (int i=0; i<7; i++){
            mtblCalendarBig.addColumn(headersBig[i]);
        }
        
        tblCalendar.getParent().setBackground(tblCalendar.getBackground()); //Set background
        tblCalendarBig.getParent().setBackground(tblCalendarBig.getBackground()); //Set background for the big calendar
        
        //No resize/reorder
        tblCalendar.getTableHeader().setResizingAllowed(false);
        tblCalendar.getTableHeader().setReorderingAllowed(false);
        
        //no resize/reorder for the big calendar
        tblCalendarBig.getTableHeader().setResizingAllowed(false);
        tblCalendarBig.getTableHeader().setReorderingAllowed(false);
        
        //Single cell selection
        tblCalendar.setColumnSelectionAllowed(true);
        tblCalendar.setRowSelectionAllowed(true);
        tblCalendar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
       //Single cell selection for the big calendar
        tblCalendarBig.setColumnSelectionAllowed(true);
        tblCalendarBig.setRowSelectionAllowed(true);
        tblCalendarBig.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        //Set row/column count
        tblCalendar.setRowHeight(38);
        mtblCalendar.setColumnCount(7);
        mtblCalendar.setRowCount(6);
        
        //testing to set row/column count for the big calendar
        tblCalendarBig.setRowHeight(79);
        mtblCalendarBig.setColumnCount(7);
        mtblCalendarBig.setRowCount(6);
        
        //Populate table
        for (int i=realYear-100; i<=realYear+100; i++){
            cmbYear.addItem(String.valueOf(i));
        }
        
        //Refresh calendar
        refreshCalendar (realMonth, realYear); //Refresh calendar
    }
    
    //make the frame fill the full size of the screen
    private static void makeFrameFullSize(JFrame aFrame) {
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        aFrame.setSize(screenSize.width, screenSize.height);
    }
    
    public static void refreshCalendar(int month, int year){
        //Variables
        String[] months =  {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        int nod, som; //Number Of Days, Start Of Month
        
        //Allow/disallow buttons
        btnPrev.setEnabled(true);
        btnNext.setEnabled(true);
        if (month == 0 && year <= realYear-10){btnPrev.setEnabled(false);} //Too early
        if (month == 11 && year >= realYear+100){btnNext.setEnabled(false);} //Too late
        currMonth.setText(months[month]); //Refresh the month label (at the top)
        currMonth.setBounds(((screenSize.width/4) - (screenSize.width/7)), 25, 180, 25); //Re-align label with calendar
        cmbYear.setSelectedItem(String.valueOf(year)); //Select the correct year in the combo box
        
        //allow/disallow buttons for big calendar
        currMonthBig.setText(months[month]); //Refresh the month label (at the top)
        currMonthBig.setBounds(((screenSize.width - currMonth.WIDTH)/4) + 50, 25, 1000, 25); //Re-align label with calendar
        btnPrevBig.setEnabled(true);
        btnNextBig.setEnabled(true);
        if (month == 0 && year <= realYear-10){btnPrevBig.setEnabled(false);} //Too early
        if (month == 11 && year >= realYear+100){btnNextBig.setEnabled(false);} //Too late
        
        //Clear table
        for (int i=0; i<6; i++){
            for (int j=0; j<7; j++){
                mtblCalendar.setValueAt(null, i, j);
            }
        }
        
        //testing to clear table for the big calendar
        for (int i=0; i<6; i++){
            for (int j=0; j<7; j++){
                mtblCalendarBig.setValueAt(null, i, j);
            }
        }
        
        //Get first day of month and number of days
        GregorianCalendar cal = new GregorianCalendar(year, month, 1);
        nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        som = cal.get(GregorianCalendar.DAY_OF_WEEK);
        
        //Draw calendar - the numbers
        for (int i=1; i<=nod; i++){
            int row = new Integer((i+som-2)/7);
            int column  =  (i+som-2)%7;
            mtblCalendar.setValueAt(i, row, column);
        }
        
        //testing to draw the big calendar
        for (int i=1; i<=nod; i++){
            int row = new Integer((i+som-2)/7);
            int column  =  (i+som-2)%7;
            mtblCalendarBig.setValueAt(i, row, column);
        }
        
        //Apply renderers
        tblCalendar.setDefaultRenderer(tblCalendar.getColumnClass(0), new tblCalendarRenderer());
        
        //Apply renderers for the big calendar
        tblCalendarBig.setDefaultRenderer(tblCalendarBig.getColumnClass(0), new tblCalendarRenderer());
    }
    
    static class tblCalendarRenderer extends DefaultTableCellRenderer{
        public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column){
            super.getTableCellRendererComponent(table, value, selected, focused, row, column);
            if (column == 0 || column == 6){ //Week-end
                setBackground(new Color(255, 220, 220));
            }
            else{ //Week
                setBackground(new Color(255, 255, 255));
            }
            if (value != null){
                if (Integer.parseInt(value.toString()) == realDay && currentMonth == realMonth && currentYear == realYear){ //Today
                    setBackground(new Color(220, 220, 255));
                }
            }
            setBorder(null);
            setForeground(Color.black);
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

