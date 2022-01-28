import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.Timer;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
*   Home Screen User Interface by Kayla
*/
public class HomeScreen extends Screen {

    private JLabel[] buttons = null;
    private JLabel time = null;
    private JLabel date = null;
    private DateTimeFormatter dtf = null;
    private LocalTime localTime = null;
    private LocalDate localDate = null;

    public HomeScreen() {
        
        closeButton();
        
        time = new JLabel(getTime(), JLabel.LEFT);
        time.setBounds(500, 160, 500, 250);
        time.setFont(Window.getFont(72));
        time.setForeground(Window.colorText);
        panel.add(time);

        date = new JLabel(getDate(), JLabel.CENTER);
        date.setBounds(0, 0, Window.screenWidth, 680);
        date.setFont(Window.getFont(20));
        date.setForeground(Window.colorText);
        panel.add(date);

        // Repaint time every second
        Timer timer = new Timer(500, new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                time.setText(getTime());
                time.repaint();
            }
        });
        timer.start();

        // Set up app buttons
        final String[] buttonVals = {
            //"notebook",
            "calculator",
            "calendar",
            "exit"
        };

        final int buttonSize = 64; // button length in pixels
        final int buttonSpacing = 8; // set the pixel spacing between the buttons

        // height is same as button size
        // No X Offset; always centered
        final int width = (buttonSize * buttonVals.length) + (buttonSpacing * buttonVals.length);
        final int yOffset = 500;

        buttons = new JLabel[buttonVals.length];
        for (int i = 0; i < buttonVals.length; i++) {
            buttons[i] = new JLabel(buttonVals[i], new ImageIcon("res/Icon" + buttonVals[i] + ".png"), JLabel.LEFT);

            // Get dimensions of all buttons laid out

            buttons[i].setBounds(
                (Window.screenWidth / 2 - width / 2) + // Center the X origin point
                (i * buttonSize) + // Offset by buttons 
                (i * buttonSpacing), // Offset by spacing
                yOffset, // Position on screen manually decided
                buttonSize, buttonSize); // button dimensions

            buttons[i].addMouseListener(new MouseAdapter() 
            {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JLabel label = (JLabel) e.getSource(); // Get JLabel
                    String app = label.getText();
                    if (app.equals("exit")) {
                        System.exit(0);
                    }
                    Window.switchScreen(app);
                }
            });

            panel.add(buttons[i]);
        }
    }

    private String getTime() {
        dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        localTime = LocalTime.now();
        return dtf.format(localTime);
    }

    private String getDate() {
        dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd");
        localDate = LocalDate.now();
        return dtf.format(localDate);
    }
}