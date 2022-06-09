package student;
import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Color;
import java.awt.Font;
import java.io.File;

/*
 * UI developed by Kayla
 */
public class Window {
    
    private static CardLayout card;
    private static JFrame frame;
    private static JPanel cardPane;

    private static Font font;
    public static int screenWidth;
    public static int screenHeight;

    private static LockScreen lock;
    private static CalculatorScreen calculator;
    private static HomeScreen home;
    private static CalendarScreen calendar;
    private static NotebookScreen notebook;
    private static SettingsScreen settings;
    
    public Window(String start) {
        
        screenWidth = 1280;
        screenHeight = 720;
        
        // Frame settings
        frame = new JFrame();
        frame.setSize(screenWidth, screenHeight);
        frame.setResizable(false);
        frame.setUndecorated(true); // removes the bar on the top of the window
        frame.setLocationRelativeTo(null); // center the window
        frame.setTitle("Student Assistance App");
        frame.setIconImage((new ImageIcon("res/IconApp.png").getImage()));

        try {
            font = font.createFont(Font.TRUETYPE_FONT, new File("res/Montserrat.ttf"));
        } catch (Exception e) {
            font = new Font("Calibri", Font.PLAIN, 32);
        }

        // Card settings
        card = new CardLayout();
        cardPane = new JPanel();
        cardPane.setLayout(card);
        
        updateBG();
        createScreens();
        
        switchScreen(start);
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
    public static void updateScreens(){
        updateBG();
        createScreens();
        switchScreen("settings");
    }
    private static void updateBG(){
        cardPane.setBackground(ThemeManager.getColorBG());
    }
    private static void createScreens(){
        // Create all screens here
        home = new HomeScreen();
        lock = new LockScreen();
        calendar = new CalendarScreen();
        calculator = new CalculatorScreen();
        // notebook = new NotebookScreen();
        settings = new SettingsScreen();
        
        // Add all screens to cardpane
        cardPane.add(home.getPanel(), "home");
        cardPane.add(lock.getPanel(), "lock");
        cardPane.add(calendar.getPanel(), "calendar");
        cardPane.add(calculator.getPanel(), "calculator");
        // cardPane.add(notebook.getPanel(), "notebook");
        cardPane.add(settings.getPanel(), "settings");
    }
}