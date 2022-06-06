package student;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.Color;

public class SettingsScreen extends Screen
{
    private JSlider[] sliders = null;
    private JTextField textField = null;
    
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
        
        textField = new JTextField();
        textField.setColumns(1);
        textField.setFont(Window.getFont(28));
        textField.setBackground(ThemeManager.getColorBox());
        textField.setForeground(ThemeManager.getColorText());
        textField.setEditable(true);
        textField.setBorder(BorderFactory.createLineBorder(ThemeManager.getColorBox()));
        textField.setBounds((
            315), // Center the X origin point
            (50), // Center the Y origin point
            200, // Width of the textfield
            40); // Height of the text field
        
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

        panel.add(textField);
    }
    public void fieldUpdateColor(String color){
        
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
    public void sliderUpdateColor(){
        int[] RGB = new int[3];
        
        RGB[0] = sliders[0].getValue();
        RGB[1] = sliders[1].getValue();
        RGB[2] = sliders[2].getValue();
        textField.setBackground(new Color(RGB[0], RGB[1], RGB[2]));
        
        String hex = "";
        for(int i = 0; i < 3; i++){
            String val = Integer.toHexString(RGB[i]);
            val = (String.format("%02d", val)).toString();
            hex += val;
        }
        textField.setText(hex);
        
    }
}
