package student;

import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.util.HashMap;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsScreen extends Screen
{
    private static JSlider[] sliders = null;
    private JTextField[] sliderFields1 = null;
    private JTextField[] sliderFields2 = null;
    private JTextField textField = null;
    private static JTextField bgDisplay = null;
    private static JTextField windowDisplay = null;
    private JButton btnSave = null;
    private JButton btnLight = null;
    private JButton btnDark = null;
    private JButton btnSaveBG = null;
    private JButton btnSaveWindow = null;
    private JButton btnSaveText = null;
    private static HashMap < String, Color > colors = new HashMap < > ();
    
    private static Color colorBackground = null;
    private static Color colorBorder = null;
    private static Color colorBoxLight = null;
    private static Color colorText = null;
    private static Color colorBox = null;
    
    private static JTextField[] colorFields = null;
    private static String hex = "000000";
    
    public SettingsScreen() {
    
        backButton();
            
        sliders = new JSlider[3];
        for (int i = 0; i < sliders.length; i++) {
            sliders[i] = new JSlider(0, 255);
            sliders[i].setLabelTable(sliders[i].createStandardLabels(255));
            sliders[i].setFont(Window.getFont());

            sliders[i].setBounds(
                (50), // X
                (50 + i*40), // Y
                255, 40); // button dimensions

            sliders[i].addChangeListener(new ChangeListener() 
            {
                @Override
                public void stateChanged(ChangeEvent ce) {
                    sliderUpdateColor();
                }
            });
            panel.add(sliders[i]);
        }
        
        sliderFields1 = new JTextField[3];
        for (int i = 0; i < sliders.length; i++) {
            sliderFields1[i] = new JTextField();
            sliderFields1[i].setColumns(1);
            sliderFields1[i].setFont(Window.getFont(28));
            sliderFields1[i].setBackground(ThemeManager.getColorBox());
            sliderFields1[i].setForeground(ThemeManager.getColorText());
            sliderFields1[i].setEditable(false);
            sliderFields1[i].setBorder(BorderFactory.createLineBorder(ThemeManager.getColorBox()));
            
            sliderFields1[i].setBounds(
                (325), // X
                (50 + i*40), // Y
                90, 40); // button dimensions

            panel.add(sliderFields1[i]);
        }
        
        sliderFields2 = new JTextField[3];
        for (int i = 0; i < sliders.length; i++) {
            sliderFields2[i] = new JTextField();
            sliderFields2[i].setColumns(1);
            sliderFields2[i].setFont(Window.getFont(28));
            sliderFields2[i].setBackground(ThemeManager.getColorBox());
            sliderFields2[i].setForeground(ThemeManager.getColorText());
            sliderFields2[i].setEditable(false);
            sliderFields2[i].setBorder(BorderFactory.createLineBorder(ThemeManager.getColorBox()));
            
            sliderFields2[i].setBounds(
                (435), // X
                (50 + i*40), // Y
                90, 40); // button dimensions

            panel.add(sliderFields2[i]);
        }
        
        textField = new JTextField();
        textField.setColumns(1);
        textField.setFont(Window.getFont(28));
        textField.setBackground(ThemeManager.getColorBox());
        textField.setForeground(ThemeManager.getColorText());
        textField.setEditable(true);
        textField.setBorder(BorderFactory.createLineBorder(ThemeManager.getColorBox()));
        textField.setBounds((
            545), // Center the X origin point
            (50), // Center the Y origin point
            180, // Width of the textfield
            40); // Height of the text field
        panel.add(textField);    
        
        bgDisplay = new JTextField();
        bgDisplay.setBackground(ThemeManager.getColorBox());
        bgDisplay.setEditable(false);
        bgDisplay.setBorder(BorderFactory.createLineBorder(ThemeManager.getColorBox()));
        bgDisplay.setBounds((
            50), // Center the X origin point
            (190), // Center the Y origin point
            675, // Width of the textfield
            480); // Height of the text field
        panel.add(bgDisplay); 
        
        windowDisplay = new JTextField();
        windowDisplay.setFont(Window.getFont(28));
        windowDisplay.setBackground(ThemeManager.getColorBoxLight());
        windowDisplay.setForeground(ThemeManager.getColorText());
        windowDisplay.setEditable(true);
        windowDisplay.setText("                 Theme Preview");
        windowDisplay.setBorder(BorderFactory.createLineBorder(ThemeManager.getColorBox()));
        windowDisplay.setBounds((
            135), // Center the X origin point
            (260), // Center the Y origin point
            500, // Width of the textfield
            340); // Height of the text field
        panel.add(windowDisplay);
        panel.moveToFront(windowDisplay);
        
        btnSave = new JButton("Apply Selected");
        btnSave.setFont(Window.getFont(20));
        btnSave.setForeground(ThemeManager.getColorText());
        btnSave.setBackground(ThemeManager.getColorBox());
        btnSave.setFocusPainted(false);
        btnSave.setBorder(null);
        btnSave.addActionListener(new btnApply_Action());
        btnSave.setBounds(905, 50, 180, 40);
        panel.add(btnSave);
        
        btnLight = new JButton("Default Light");
        btnLight.setFont(Window.getFont(20));
        btnLight.setForeground(ThemeManager.getColorText());
        btnLight.setBackground(ThemeManager.getColorBox());
        btnLight.setFocusPainted(false);
        btnLight.setBorder(null);
        btnLight.addActionListener(new btnLight_Action());
        btnLight.setBounds(905, 100, 180, 40);
        panel.add(btnLight);
        
        btnDark = new JButton("Default Dark");
        btnDark.setFont(Window.getFont(20));
        btnDark.setForeground(ThemeManager.getColorText());
        btnDark.setBackground(ThemeManager.getColorBox());
        btnDark.setFocusPainted(false);
        btnDark.setBorder(null);
        btnDark.addActionListener(new btnDark_Action());
        btnDark.setBounds(905, 150, 180, 40);
        panel.add(btnDark);
        
        btnSaveBG = new JButton("Save BG Color");
        btnSaveBG.setFont(Window.getFont(20));
        btnSaveBG.setForeground(ThemeManager.getColorText());
        btnSaveBG.setBackground(ThemeManager.getColorBox());
        btnSaveBG.setFocusPainted(false);
        btnSaveBG.setBorder(null);
        btnSaveBG.addActionListener(new btnSaveBG_Action());
        btnSaveBG.setBounds(770, 300, 220, 40);
        panel.add(btnSaveBG);
        
        btnSaveText = new JButton("Save Text Color");
        btnSaveText.setFont(Window.getFont(20));
        btnSaveText.setForeground(ThemeManager.getColorText());
        btnSaveText.setBackground(ThemeManager.getColorBox());
        btnSaveText.setFocusPainted(false);
        btnSaveText.setBorder(null);
        btnSaveText.addActionListener(new btnSaveText_Action());
        btnSaveText.setBounds(770, 350, 220, 40);
        panel.add(btnSaveText);
        
        btnSaveWindow = new JButton("Save Window Color");
        btnSaveWindow.setFont(Window.getFont(20));
        btnSaveWindow.setForeground(ThemeManager.getColorText());
        btnSaveWindow.setBackground(ThemeManager.getColorBox());
        btnSaveWindow.setFocusPainted(false);
        btnSaveWindow.setBorder(null);
        btnSaveWindow.addActionListener(new btnSaveWindow_Action());
        btnSaveWindow.setBounds(770, 400, 220, 40);
        panel.add(btnSaveWindow);
        
        // 0 = Background Color
        // 1 = Text Color
        // 2 = Window Color
        colorFields = new JTextField[3];
        for (int i = 0; i < sliders.length; i++) {
            colorFields[i] = new JTextField();
            colorFields[i].setColumns(1);
            colorFields[i].setFont(Window.getFont(28));
            colorFields[i].setBackground(ThemeManager.getColorBox());
            colorFields[i].setForeground(ThemeManager.getColorText());
            colorFields[i].setEditable(false);
            colorFields[i].setBorder(BorderFactory.createLineBorder(ThemeManager.getColorBox()));
            
            colorFields[i].setBounds(
                (1010), // X
                (300 + i*50), // Y
                220, 40); // button dimensions

            panel.add(colorFields[i]);
        }
        
        /*
        textField.getDocument().addDocumentListener(new SimpleDocumentListener() 
        {
            @Override
            public void update(DocumentEvent e) {
                fieldUpdateColor();
             }
        });
        */
        
        /*
        textField.addCaretListener(e -> 
        {
            String currentVal = textField.getText();
            String oldVal = "";

            if(!currentVal.equals(oldVal)) {
                oldVal = currentVal;
                fieldUpdateColor(currentVal);
            }
        });
        */
       
        
        
    }
    /*
    public void fieldUpdateColor(){
        String color = textField.getText();
        
        // Check if code uses hexadecimal
        boolean isHex = color.matches("^[0-9a-fA-F]+$");
        // Additionally, check if code is 6 characters long to account for RGB
        if (color.length() == 6 && isHex){ 
            int[] RGB = new int[3];
            
            // Hexadecimal uses base16, so valueOf uses the value 16 in its second parameter
            RGB[0] = Integer.parseInt(color.substring(0, 2), 16);
            RGB[1] = Integer.parseInt(color.substring(2, 4), 16);
            RGB[2] = Integer.parseInt(color.substring(4, 6), 16);
            
            sliders[0].setValue(RGB[0]); 
            sliders[1].setValue(RGB[1]); 
            sliders[2].setValue(RGB[2]); 
            textField.setBackground(new Color(RGB[0], RGB[1], RGB[2]));
            
        }
    }
    */
    public void sliderUpdateColor(){
        int[] RGB = new int[3];
        
        RGB[0] = sliders[0].getValue();
        RGB[1] = sliders[1].getValue();
        RGB[2] = sliders[2].getValue();
        
        // Set the bg of the text field so the user knows what color is set
        // textField.setBackground(new Color(RGB[0], RGB[1], RGB[2]));
        // Set the text color to the inverse of the selected color; ensures its always visible
        // textField.setForeground(new Color(255-RGB[0], 255-RGB[1], 255-RGB[2]));
        
        hex = "";
        for(int i = 0; i < 3; i++){
            String val = Integer.toHexString(RGB[i]);
            
            // val = (String.format("%02d", val)).toString();
            // Parse 0 to the beginning if color value is only one character; ensures hex code is always 6 characters
            if(val.length()==1){
                val = "0"+val;
            }
            
            hex += val;
        }
        
        textField.setText(hex);
        sliderFields1[0].setText(""+sliders[0].getValue());
        sliderFields1[1].setText(""+sliders[1].getValue());
        sliderFields1[2].setText(""+sliders[2].getValue());
        sliderFields2[0].setText(hex.substring(0, 2));
        sliderFields2[1].setText(hex.substring(2, 4));
        sliderFields2[2].setText(hex.substring(4, 6));
        
        textField.setBackground(getSliderColor());
        textField.setForeground(new Color(255-RGB[0], 255-RGB[1], 255-RGB[2]));
    }
    private static Color getSliderColor(){
        int[] RGB = new int[3];
        for(int i = 0; i < 3; i++){
            RGB[i] = sliders[i].getValue();
        }
        
        return (new Color(RGB[0], RGB[1], RGB[2]));
    }
    private static Color getSliderColor(int shift){
        int[] RGB = new int[3];
        for(int i = 0; i < 3; i++){
            RGB[i] = sliders[i].getValue()+shift;
            if(RGB[i] < 0){
                RGB[i] = 0;
            }
            if(RGB[i] > 255){
                RGB[i] = 255;
            }
        }
        
        return (new Color(RGB[0], RGB[1], RGB[2]));
    }
    static class btnApply_Action implements ActionListener{
        public void actionPerformed (ActionEvent e){
            /*
               * ColorBox
                 * ColorBoxLight = for use in the calendar, slightly lighter color for inputting new tasks
                   * ColorText
                     * ColorBorder = for use in calendar, slightly darker color for dividing up the table
                       * ColorBG
                         */
        
            Theme custom = new Theme(
                colorBox,
                colorBoxLight,
                colorText,
                colorBorder,
                colorBackground
                );
                
            ThemeManager.addTheme("custom", custom);
            ThemeManager.switchTheme("custom");
        }
    }
    static class btnLight_Action implements ActionListener{
        public void actionPerformed (ActionEvent e){
            ThemeManager.switchTheme("light");
        }
    }
    static class btnDark_Action implements ActionListener{
        public void actionPerformed (ActionEvent e){
            ThemeManager.switchTheme("dark");
        }
    }
    static class btnSaveBG_Action implements ActionListener{
        public void actionPerformed (ActionEvent e){
            colorFields[0].setText(hex);
            colorBackground = getSliderColor();
            bgDisplay.setBackground(colorBackground);
        }
    }
    static class btnSaveText_Action implements ActionListener{
        public void actionPerformed (ActionEvent e){
            colorFields[1].setText(hex);
            colorText = getSliderColor();
            colorBorder = getSliderColor(-20);
            windowDisplay.setForeground(colorText);
        }
    }
    static class btnSaveWindow_Action implements ActionListener{
        public void actionPerformed (ActionEvent e){
            colorFields[2].setText(hex);
            colorBox = getSliderColor();
            colorBoxLight = getSliderColor(15);
            
            windowDisplay.setBackground(colorBox);
            windowDisplay.setBorder(BorderFactory.createLineBorder(colorBox));
        }
    }
}
