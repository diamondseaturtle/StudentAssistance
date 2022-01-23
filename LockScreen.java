
import javax.swing.JLayeredPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.Color;

public class LockScreen{
    private JLayeredPane panel = new JLayeredPane();
    private JLabel ui = null;
    private JLabel close = null;
    private JLabel buttonAccount = null;
    private JLabel buttonLogin = null;
    private JTextField[] textFields = null;
    private boolean creatingAccount;
    
    private boolean createAccount(String username, String password){
        // return true if account is successfully created
        // return false otherwise; username is already taken
        
        return false;
    }
    private boolean login(String username, String password){
        // return true if login is successful
        // return false otherwise; username or password is incorrect
        
        return false;
    }
    
    public LockScreen(){
        
        panel.setBounds(0, 0, 1280, 720);
        
        // user interface image overlay
        ui = new JLabel("", new ImageIcon("res/UILogin.png"), JLabel.LEFT);
        ui.setBounds(0, 0, 1280, 720);
        panel.add(ui, 1);

        // X button. Closes the window
        close = new JLabel("", new ImageIcon("res/Close.png"), JLabel.LEFT);
        close.setBounds(1280-45, 0, 45, 45);
        panel.add(close, 2);
        close.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        // Button: Create a new account
        creatingAccount = false;
        buttonAccount = new JLabel("", new ImageIcon("res/AccountCreate.png"), JLabel.LEFT);
        buttonAccount.setBounds(587, 447, 450, 55);
        panel.add(buttonAccount, 3);
        buttonAccount.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e) {
                textFields[0].setText("");
                textFields[1].setText("");
                
                creatingAccount = !creatingAccount; // swap boolean value
                if (creatingAccount){
                  ui.setIcon(new ImageIcon("res/UIAccount.png")); 
                  buttonAccount.setIcon(new ImageIcon("res/AccountLogin.png"));
                  
                }
                else{
                  ui.setIcon(new ImageIcon("res/UILogin.png"));
                  buttonAccount.setIcon(new ImageIcon("res/AccountCreate.png"));
                  
                }
            }
        });

        // Button: Login
        buttonLogin = new JLabel("", new ImageIcon("res/Login.png"), JLabel.LEFT);
        buttonLogin.setBounds(1010, 447, 260, 55);
        panel.add(buttonLogin, 4);
        buttonLogin.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e) {
                /*System.out.println(textFields[0].getText());
                System.out.println(textFields[1].getText());*/
                boolean successful;
                if (creatingAccount){
                  successful = createAccount(textFields[0].getText(), textFields[1].getText());
                  if (successful){
                      ui.setIcon(new ImageIcon("res/UISuccessfulLogin.png")); // Temporary code for testing
                  }else{
                      ui.setIcon(new ImageIcon("res/UIAccountError.png")); // Temporary code for testing
                  }
                }
                else{
                  successful = login(textFields[0].getText(), textFields[1].getText());
                  if (successful){
                      ui.setIcon(new ImageIcon("res/UISuccessfulLogin.png")); // Temporary code for testing
                  }else{
                      ui.setIcon(new ImageIcon("res/UILoginError.png")); // Temporary code for testing
                  }
                }
            }
        });

        Color colorBox = new Color(48,47,78);
        Color colorText = new Color(225,225,225);

        textFields = new JTextField[2];
        for (int i = 0; i < 2; i++) {
            textFields[i] = new JTextField();
            textFields[i].setColumns(1);
            textFields[i].setFont(Window.font);
            textFields[i].setBackground(colorBox);
            textFields[i].setBorder(BorderFactory.createLineBorder(colorBox));
            textFields[i].setForeground(colorText);
        }
        
        textFields[0].setBounds(853, 303, 353, 55);
        panel.add(textFields[0], 5);

        textFields[1].setBounds(853, 375, 353, 55);
        panel.add(textFields[1], 6);
        
        Window.frame.add(panel);
        Window.frame.setVisible(true);
    }
    
}