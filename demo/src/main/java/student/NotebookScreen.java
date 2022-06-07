package student;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NotebookScreen extends Screen {
	private static JButton addNotebook;
    private static JTextField nameNotebook;
	private static JPanel miniPanel;

	
    public NotebookScreen(){
    	
        backButton();
        
        nameNotebook = new JTextField();
        nameNotebook.setColumns(10);
        nameNotebook.setText("New Notebook");
        nameNotebook.setFont(Window.getFont(40));
        nameNotebook.setForeground(ThemeManager.getColorText());
        nameNotebook.setBackground(ThemeManager.getColorBoxLight());
        nameNotebook.setBorder(BorderFactory.createLineBorder(ThemeManager.getColorBoxLight()));
        panel.add(nameNotebook);
        nameNotebook.setBounds(5, 5, 40, 40);
        
        addNotebook = new JButton("+");
        addNotebook.setFont(Window.getFont(40));
        addNotebook.setForeground(ThemeManager.getColorText());
        addNotebook.setBackground(ThemeManager.getColorBox());
        addNotebook.setFocusPainted(false);
        addNotebook.setBorder(null);
        addNotebook.addActionListener(new addNotebook_Action());
        panel.add(addNotebook);
        addNotebook.setBounds(50, 5, 40, 40); //top left
        
        miniPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(miniPanel);
        miniPanel.setBackground(ThemeManager.getColorBox());
        miniPanel.setBounds(10, 50, Window.screenWidth - 25, Window.screenHeight - 70);
        

    }
    
    static class addNotebook_Action implements ActionListener{
        public void actionPerformed (ActionEvent e){
            //create a small notebook to visually see
        	JButton newButton = new JButton();
            miniPanel.add(newButton);
            newButton.setBounds(10,10, 150, 200);
            newButton.setSize(150, 200);
            newButton.setPreferredSize(new Dimension(150,200));
            newButton.setMaximumSize(new Dimension(150,200));

            newButton.setVisible(true);
            newButton.addActionListener(new newButton_Action());

            
            //hash map to store different notebooks
        	//that small notebook when clicked should zoom in to the notebook -- object
        }
    }
    
    static class newButton_Action implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}

    }

}
