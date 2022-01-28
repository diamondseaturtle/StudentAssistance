package student;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/*
 * UI developed by Kayla
 */
public class Screen{
    
    protected JLayeredPane panel = new JLayeredPane();
    
    public Screen(){
        panel.setBounds(0, 0, Window.screenWidth, Window.screenHeight);
    }
    
    protected void closeButton(){
        // Closes the window
        JLabel close = new JLabel("", new ImageIcon("res/Close.png"), JLabel.LEFT);
        close.setBounds(Window.screenWidth-45, 0, 45, 45);
        panel.add(close);
        close.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });
    }
    
    protected void backButton(){
        // Closes the window
        JLabel close = new JLabel("", new ImageIcon("res/Back.png"), JLabel.LEFT);
        close.setBounds(Window.screenWidth-50, 5, 45, 45);
        panel.add(close);
        close.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e) {
                Window.switchScreen("home");
            }
        });
    }
    
    protected JLayeredPane getPanel(){
        return panel;
    }
}
