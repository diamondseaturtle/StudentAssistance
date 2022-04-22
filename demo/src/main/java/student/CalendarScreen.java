package student;
import java.util.HashMap;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;
import java.time.Month;


import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/*
 * To do list functionality developed by Samskrithi
 * Calendar functionality developed by Samskrithi 
 * UI developed by Samskrithi and Kayla
 * Additionall features developed by Claire
 */
public class CalendarScreen extends Screen{ 
    private static JLabel currMonthBig, lblTodo, taskList;
    private static JButton btnPrevBig, btnNextBig, btnCurrent, btnSaveTodo;
    private static JTable tblCalendarBig;
    private static JFrame frmMain;
    private static Container pane;
    private static DefaultTableModel mtblCalendarBig; //Table model
    private static JScrollPane stblCalendarBig; //The scrollpane
    private static JPanel todoListPanel, allToDoListPanel, eventPanel, taskListPanel;
    private static int realYear, realMonth, realDay, currentYear, currentMonth;
    private static Dimension screenSize;
    private static JTextField txtTodo;
    private static int currentRow;
    private static int currentCol;
    private static HashMap<String, ArrayList<String>> indivTD = new HashMap<>();

    public CalendarScreen(){

        backButton();

        

        // testing to create the controls for the big calendar
        currMonthBig = new JLabel("January");
        currMonthBig.setHorizontalAlignment(JLabel.CENTER);
        currMonthBig.setFont(Window.getFont(28));
        currMonthBig.setForeground(Window.colorText);
        
        btnPrevBig = new JButton("<");
        btnPrevBig.setFont(Window.getFont(20));
        btnPrevBig.setForeground(Window.colorText);
        btnPrevBig.setBackground(Window.colorBox);
        btnPrevBig.setFocusPainted(false);
        btnPrevBig.setBorder(null);
        
        btnNextBig = new JButton(">");
        btnNextBig.setFont(Window.getFont(20));
        btnNextBig.setForeground(Window.colorText);
        btnNextBig.setBackground(Window.colorBox);
        btnNextBig.setFocusPainted(false);
        btnNextBig.setBorder(null);
        
        btnCurrent = new JButton("Today");
        btnCurrent.setFont(Window.getFont(20));
        btnCurrent.setForeground(Window.colorText);
        btnCurrent.setBackground(Window.colorBox);
        btnCurrent.setFocusPainted(false);
        btnCurrent.setBorder(null);
        
        mtblCalendarBig = new DefaultTableModel()
        {
            public boolean isCellEditable(int rowIndex, int mColIndex){
                return false;
              }
          };
          
        // Manually set height or width of the calendar
        final int calendarWidth = 600;
        final int calendarHeight = 390; // Optimally divisible by 6
          
        tblCalendarBig = new JTable(mtblCalendarBig);
        tblCalendarBig.setGridColor(Window.colorBorder); // Border inside grid
        tblCalendarBig.setBorder(BorderFactory.createLineBorder(Window.colorBorder)); // Border outside grid
        
        stblCalendarBig = new JScrollPane(tblCalendarBig);
        stblCalendarBig.setBorder(BorderFactory.createLineBorder(Window.colorBox)); 
        
        //todo
        todoListPanel = new JPanel();
        todoListPanel.setBackground(Window.colorBox);
        todoListPanel.setBorder(BorderFactory.createTitledBorder("To Do")); 
        ((javax.swing.border.TitledBorder) todoListPanel.getBorder()).setTitleFont(Window.getFont(20)); // set border font
        ((javax.swing.border.TitledBorder) todoListPanel.getBorder()).setTitleColor(Window.colorText); // set border text color
        
        lblTodo = new JLabel("Enter a to do: ");
        lblTodo.setFont(Window.getFont(20));
        lblTodo.setForeground(Window.colorText);
        
        txtTodo = new JTextField();
        txtTodo.setColumns(10);
        txtTodo.setText("Reading");
        txtTodo.setFont(Window.getFont(20));
        txtTodo.setForeground(Window.colorText);
        txtTodo.setBackground(Window.colorBoxLight);
        txtTodo.setBorder(BorderFactory.createLineBorder(Window.colorBoxLight));
        
        btnSaveTodo = new JButton("Save");
        btnSaveTodo.setFont(Window.getFont(20));
        btnSaveTodo.setForeground(Window.colorText);
        btnSaveTodo.setBackground(Window.colorBoxLight);
        btnSaveTodo.setFocusPainted(false);
        btnSaveTodo.setBorder(null);
        
        allToDoListPanel = new JPanel();
        allToDoListPanel.setBackground(Window.colorBox);
        allToDoListPanel.setBorder(BorderFactory.createTitledBorder("List of To Do item"));
        ((javax.swing.border.TitledBorder) allToDoListPanel.getBorder()).setTitleFont(Window.getFont(20)); // set border font
        ((javax.swing.border.TitledBorder) allToDoListPanel.getBorder()).setTitleColor(Window.colorText); // set border text color

        //testing to register action listeners
        btnPrevBig.addActionListener(new btnPrev_Action());
        btnNextBig.addActionListener(new btnNext_Action());
        btnCurrent.addActionListener(new btnCurrent_Action());
        btnSaveTodo.addActionListener(new btnSaveTodo_Action());

        //testing to add controls to pane for big calendar
        panel.add(currMonthBig);
        panel.add(btnNextBig);
        panel.add(btnPrevBig);
        panel.add(btnCurrent);
        panel.add(stblCalendarBig);

        //add controls - todo
        panel.add(todoListPanel);
        todoListPanel.add(lblTodo);
        todoListPanel.add(txtTodo);
        todoListPanel.add(btnSaveTodo);
        panel.add(allToDoListPanel);
        
        //testing to set bounds for big calendar
        currMonthBig.setBounds(
            0, // Center X origin done by JLabel.Center
            
            Window.screenHeight/2-calendarHeight/2 // Center Y origin
            -90, // Offset
            
            Window.screenWidth, // Centered by JLabel.Center
            50 // Element height
        ); 
        
        btnPrevBig.setBounds(
            Window.screenWidth/2 // Center X origin
            - 0 // Button is aligned on left by default, so subtract nothing
            - calendarWidth/2, // Offset by calendar
            
            Window.screenHeight/2-calendarHeight/2 // Center Y origin
            - 40, // Offset
            
            60, // Button width
            30 // Button height
        );
        
        btnCurrent.setBounds(
            Window.screenWidth/2 // Center X origin
            - 75/2, // To center the label, subtract its width divided by 2
            
            Window.screenHeight/2-calendarHeight/2 // Center Y origin
            - 40, // Offset
            
            75, // Button width
            30 // Button height
        );
        
        btnNextBig.setBounds(
            Window.screenWidth/2 // Center X origin
            - 60 // To align label on the right, subtract its width
            + calendarWidth/2, // Offset by calendar
            
            Window.screenHeight/2-calendarHeight/2 // Center Y origin
            - 40, // Offset
            
            60, // Button width
            30 // Button height
        );
        
        stblCalendarBig.setBounds(
            Window.screenWidth/2 - calendarWidth/2, // Center X origin
            Window.screenHeight/2 - calendarHeight/2, // Center Y origin
            calendarWidth, 
            calendarHeight+1 // +1 is to account for scrollbar; must be larger than bounds
        );  
        
        todoListPanel.setBounds(
            0, 
            Window.screenHeight/2 - calendarHeight/2, // Center Y origin
            320, 
            calendarHeight*1/4 // Takes up 1/4 of the left bar
        );
        
        allToDoListPanel.setBounds(
            0, 
            
            Window.screenHeight/2 - calendarHeight/2
            + calendarHeight*1/4 // Offset by todolist
            + 3,
            
            320, 
            calendarHeight*3/4 // Takes up 3/4 of the left bar
        );
        
        btnSaveTodo.setEnabled(true);
       
        //Get real month/year
        GregorianCalendar cal = new GregorianCalendar(); //Create calendar
        realDay = cal.get(GregorianCalendar.DAY_OF_MONTH); //Get day
        realMonth = cal.get(GregorianCalendar.MONTH); //Get month
        realYear = cal.get(GregorianCalendar.YEAR); //Get year
        currentMonth = realMonth; //Match month and year
        currentYear = realYear;

        //testing to add headers for the big calendar
        String[] headersBig = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"}; //All headers
        for (int i=0; i<7; i++){
            mtblCalendarBig.addColumn(headersBig[i]);
        }

        tblCalendarBig.getParent().setBackground(tblCalendarBig.getBackground()); //Set background for the big calendar

        //No resize/reorder
        tblCalendarBig.getTableHeader().setResizingAllowed(false);
        tblCalendarBig.getTableHeader().setReorderingAllowed(false);

        tblCalendarBig.addMouseListener(new java.awt.event.MouseAdapter() 
        { 
            public void mouseClicked(java.awt.event.MouseEvent e) {
            	int row=tblCalendarBig.rowAtPoint(e.getPoint());
            	int col= tblCalendarBig.columnAtPoint(e.getPoint());     
            	Integer selectedValue = (Integer) mtblCalendarBig.getValueAt(row, col);
            	System.out.println (selectedValue);
            	
            	String dateStr = currMonthBig.getText() + " " + selectedValue + ", " + currentYear;
                parseDates(dateStr);
            	if (selectedValue != null) {
            		taskList.setText("Tasks to Complete: " + dateStr);
            	}
            	
            }
        });
        //no resize/reorder for the big calendar
        tblCalendarBig.getTableHeader().setResizingAllowed(false);
        tblCalendarBig.getTableHeader().setReorderingAllowed(false);
        tblCalendarBig.setColumnSelectionAllowed(true);
        tblCalendarBig.setRowSelectionAllowed(true);
        tblCalendarBig.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Header customization settings
        tblCalendarBig.getTableHeader().setFont(Window.getFont(20));
        tblCalendarBig.getTableHeader().setForeground(Color.WHITE);
        tblCalendarBig.getTableHeader().setBackground(Window.colorBox);
        tblCalendarBig.getTableHeader().setBorder(BorderFactory.createLineBorder(Window.colorBorder));

        //Single cell selection
        tblCalendarBig.setColumnSelectionAllowed(true);
        tblCalendarBig.setRowSelectionAllowed(true);
        tblCalendarBig.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //Single cell selection for the big calendar
        tblCalendarBig.setColumnSelectionAllowed(true);
        tblCalendarBig.setRowSelectionAllowed(true);
        tblCalendarBig.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //testing to set row/column count for the big calendar
        tblCalendarBig.setRowHeight(
            (calendarHeight-30) // Headers are always set at a 30 pixel height
            /6 // Divide by 6 for rows
        ); 
        mtblCalendarBig.setColumnCount(7);
        mtblCalendarBig.setRowCount(6);

    	//create a JPanel to expand tasks per day
        taskListPanel = new JPanel();
        taskList = new JLabel("Tasks to Complete Today " );
        taskList.setFont(Window.getFont(15));
        taskList.setForeground(Color.WHITE);
        
        panel.add(taskListPanel);
        taskListPanel.setBounds(
        		Window.screenWidth/2 - calendarWidth/2 + calendarWidth + 5, 
                Window.screenHeight/2 - calendarHeight/2, // Center Y origin
                320, 
                calendarHeight
            ); 
        taskListPanel.setBorder(BorderFactory.createTitledBorder("Tasks to do:")); 
        taskListPanel.add(taskList);
        taskListPanel.setVisible(true);
        taskListPanel.setBackground(Window.colorBox);
        ((javax.swing.border.TitledBorder) taskListPanel.getBorder()).setTitleFont(Window.getFont(20)); // set border font
        ((javax.swing.border.TitledBorder) taskListPanel.getBorder()).setTitleColor(Window.colorText); // set border text color

        //Refresh calendar
        refreshCalendar (realMonth, realYear); //Refresh calendar
    }

    //extra methods
    private static void addNewList(String key, String action)
    {
        ArrayList<String> actionList = indivTD.get(key);
        if (actionList == null)
        {
            actionList = new ArrayList<String>();
            actionList.add(action);
            indivTD.put(key, actionList);
        }
    }

    //parse date for key
    private static String parseDates(String fullDate)
    {
        String[] tokens = fullDate.split("[, ]+");
        String monthName = tokens[0];
        String monthVal = Integer.toString(Month.valueOf(monthName.toUpperCase()).getValue());
        String parsed = monthVal + tokens[1] + tokens[2];
        System.out.println(parsed);
        return parsed;
        
    }

    //make the frame fill the full size of the screen
    private static void makeFrameFullSize(JFrame aFrame) {
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        aFrame.setSize(Window.screenWidth, Window.screenHeight);
    }

    public static void refreshCalendar(int month, int year){
        //Variables
        String[] months =  {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        int nod, som; //Number Of Days, Start Of Month

        //Allow/disallow buttons
        btnPrevBig.setEnabled(true);
        btnNextBig.setEnabled(true);
        if (month == 0 && year <= realYear-10){
            btnPrevBig.setEnabled(false);
        } //Too early
        if (month == 11 && year >= realYear+100){
            btnNextBig.setEnabled(false);
        } //Too late
        
        //allow/disallow buttons for big calendar
        currMonthBig.setText(months[month]); //Refresh the month label (at the top)
        btnPrevBig.setEnabled(true);
        btnNextBig.setEnabled(true);
        btnCurrent.setEnabled(true);
        if (month == 0 && year <= realYear-10){
            btnPrevBig.setEnabled(false);
        } //Too early
        if (month == 11 && year >= realYear+100){
            btnNextBig.setEnabled(false);
        } //Too late

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


        //testing to draw the big calendar
        int currDate = 0;
        for (int i=1; i<=nod; i++){
            int row = new Integer((i+som-2)/7);
            int column  =  (i+som-2)%7;
            mtblCalendarBig.setValueAt(i, row, column);
            currDate = i;
        }

        //Apply renderers for the big calendar
        tblCalendarBig.setDefaultRenderer(tblCalendarBig.getColumnClass(0), new tblCalendarRenderer());
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
                //if (Integer.parseInt(value.toString()) == realDay && currentMonth == realMonth && currentYear == realYear){ //Today
                    setBackground(Window.colorBox);
                //}
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
            if (currentMonth == 11){ //Forward one year
                currentMonth = 0;
                currentYear += 1;
            }
            else{ //Forward one month
                currentMonth += 1;
            }
            refreshCalendar(currentMonth, currentYear);
        }
    }
    static class btnSaveTodo_Action implements ActionListener{
        public void actionPerformed (ActionEvent e){
            mtblCalendarBig.setValueAt(txtTodo.getText(), currentRow, currentCol);
            JLabel task = new JLabel(txtTodo.getText());
            task.setFont(Window.getFont(20));
            task.setForeground(Color.WHITE);
            allToDoListPanel.add(task);

        }

    }
    //Today button
    static class btnCurrent_Action implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            currentMonth = realMonth; //Match month and year
            currentYear = realYear;
            refreshCalendar(currentMonth, currentYear);
        }
    }

}

