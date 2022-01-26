import javax.swing.JLayeredPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CalculatorScreen extends Screen {

    private JTextField textFields = null;
    private JLabel[][] buttons = null;

    private String calculate(String equation) {
        // convert the double to a string so it can be shown in the ui textfield
        return "";
    }

    public CalculatorScreen() {

        backButton();

        final String[][] buttonVals =  {
                    {"7","4","1","C"},
                    {"8","5","2","0"},
                    {"9","6","3","="},
                    {"(","+","*","^"},
                    {")","-","/","."}
                };

        final ImageIcon icon = new ImageIcon("res/IconEmpty.png");

        final int buttonSize = 55; // button length in pixels
        final int buttonSpacing = 6; // set the pixel spacing between the buttons
        final int calculatorWidth = (buttonSize * buttonVals.length) + (buttonSpacing * buttonVals.length);
        final int calculatorHeight = (buttonSize * buttonVals[0].length) + (buttonSpacing * buttonVals[0].length);

        buttons = new JLabel[buttonVals.length][buttonVals[0].length];
        for (int j = 0; j < buttonVals[0].length; j++) {
            for (int i = 0; i < buttonVals.length; i++) {
                buttons[i][j] = new JLabel(buttonVals[i][j], icon, JLabel.CENTER);

                // Get dimensions of all buttons laid out

                buttons[i][j].setBounds(
                    (1280 / 2 - calculatorWidth / 2) + // Center the X origin point
                    (i * buttonSize) + // Offset by buttons 
                    (i * buttonSpacing), // Offset by spacing

                    (720 / 2 - calculatorHeight / 2) + // Center the Y origin point
                    ((j + 1) * buttonSize) + // Offset by buttons. The +1 is to account for the textfield on top
                    ((j + 1) * buttonSpacing), // Offset by spacing. The +1 is to account for the textfield on top

                    buttonSize, buttonSize); // button dimensions

                buttons[i][j].setFont(Window.getFont(32));
                buttons[i][j].setForeground(Window.colorText);
                buttons[i][j].setHorizontalTextPosition(JLabel.CENTER);
                buttons[i][j].setVerticalTextPosition(JLabel.CENTER);

                buttons[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        JLabel label = (JLabel) e.getSource(); // Get JLabel
                        String string = label.getText();
                        if (string.equals("=")) {
                            textFields.setText( // 3. Calculated result is put back into the text field
                                calculate( // 2. String is put into calculator
                                    textFields.getText() // 1. String equation from the text field is obtained
                                ));
                        } else if (string.equals("C")) {
                            textFields.setText(""); // Clear text
                        } else {
                            textFields.setText(textFields.getText() + string); // Add character to textfield
                        }
                    }
                });

                panel.add(buttons[i][j]);
            }
        }


        textFields = new JTextField();
        textFields.setColumns(1);
        textFields.setFont(Window.getFont(28));
        textFields.setBackground(Window.colorBox);
        textFields.setForeground(Window.colorText);
        textFields.setEditable(false);
        textFields.setBorder(BorderFactory.createLineBorder(Window.colorBox));

        textFields.setBounds((
                1280 / 2 - calculatorWidth / 2), // Center the X origin point
            (720 / 2 - calculatorHeight / 2) - buttonSize - buttonSpacing, // Center the Y origin point. The button size and spacing is to place the field a buttons-length above the button grid
            calculatorWidth, // Width of the textfield, same width as the button grid
            buttonSize); // Height of the text field, same as a button

        panel.add(textFields);
    }
}