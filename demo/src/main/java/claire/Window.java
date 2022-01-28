package claire;
import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Font;
import java.io.File;

public class Window {

    private static CardLayout card;
    private static JFrame frame;
    private static JPanel cardPane;

    private static Font font;
    public static Color colorBox;
    public static Color colorText;

    private static LockScreen lock;
    private static CalculatorScreen calculator;
    private static HomeScreen home;

    public Window() {

        // Frame settings
        frame = new JFrame();
        frame.setSize(1280, 720);
        frame.setResizable(false);
        frame.setUndecorated(true); // removes the bar on the top of the window
        frame.setLocationRelativeTo(null); // center the window
        frame.setTitle("Student Assistance App");
        frame.setIconImage((new ImageIcon("res/IconApp.png").getImage()));

        // Set up static variables/colors
        colorBox = new Color(48, 47, 78);
        colorText = new Color(225, 225, 225);

        try {
            font = font.createFont(Font.TRUETYPE_FONT, new File("res/Montserrat.ttf"));
        } catch (Exception e) {
            font = new Font("Calibri", Font.PLAIN, 32);
        }

        // Card settings
        card = new CardLayout();
        cardPane = new JPanel();
        cardPane.setLayout(card);
        cardPane.setBackground(new Color(37, 36, 62));

        // Create all screens here
        lock = new LockScreen();
        calculator = new CalculatorScreen();
        home = new HomeScreen();

        // Add all screens to cardpane
        cardPane.add(lock.getPanel(), "lock");
        cardPane.add(calculator.getPanel(), "calculator");
        cardPane.add(home.getPanel(), "home");

        switchScreen("lock");
        frame.add(cardPane);
        frame.setVisible(true);

    }

    public static void switchScreen(String screen) {
        card.show(cardPane, screen);
    }
    public static Font getFont() {
        return font.deriveFont(Font.TRUETYPE_FONT, 28);
    }
    public static Font getFont(int size) {
        return font.deriveFont(Font.TRUETYPE_FONT, size);
    }
}
