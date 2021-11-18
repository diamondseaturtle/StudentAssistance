import javax.swing.*;  
public class Main {  
    //javafx test
    public static void main(String[] args) {  
    // frame and button creation
    JFrame f = new JFrame();       
    JButton b = new JButton("click"); 

    //button bounds and add to frame
    b.setBounds(130, 100, 100 ,40);       
    f.add(b); 
    
    //window settings 
    f.setSize(400,500);
    f.setLayout(null);
    f.setVisible(true); 
    }  
}  