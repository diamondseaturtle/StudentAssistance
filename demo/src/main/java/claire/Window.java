package claire;
import java.awt.Color;
import javax.swing.JFrame;
import java.awt.Font;
import java.io.File;

public class Window{
    public static JFrame frame;
    public static Font font;
    public static Color colorBox;
    public static Color colorText;
    
    public Window(){
        frame = new JFrame();
        
        frame.setSize(1280, 720);
        frame.setResizable(false);
        frame.setUndecorated(true); // removes the bar on the top of the window
        frame.setLocationRelativeTo(null); // center the window
        frame.getContentPane().setBackground(new Color(37,36,62));
        
        colorBox = new Color(48,47,78);
        colorText = new Color(225,225,225);
        
        try {
            font = font.createFont(Font.TRUETYPE_FONT, new File("res/Montserrat.ttf"));
            font = font.deriveFont(Font.TRUETYPE_FONT, 28f); // Workaround for font size not being able to be set after
        }
        catch(Exception e){
            font = new Font("Calibri", Font.PLAIN, 32);
        }
        
    }
}
