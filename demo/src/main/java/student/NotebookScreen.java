package student;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NotebookScreen extends Screen {
	private static JButton addNotebook;
	private static JTextField nameNotebook;
	private static JPanel miniPanel;
	private JList list;


	public NotebookScreen(){

		backButton();

		//initialization and UI for a text field so that user could name the notebook
		nameNotebook = new JTextField();
		nameNotebook.setColumns(10);
		nameNotebook.setText("New Notebook");
		nameNotebook.setFont(Window.getFont(40));
		nameNotebook.setForeground(ThemeManager.getColorText());
		nameNotebook.setBackground(ThemeManager.getColorBoxLight());
		nameNotebook.setBorder(BorderFactory.createLineBorder(ThemeManager.getColorBoxLight()));
		panel.add(nameNotebook);
		nameNotebook.setBounds(5, 5, 40, 40);

		//initialization and UI for "+" button
		addNotebook = new JButton("Add");
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

		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list.setVisibleRowCount(-1);
	}

	//The + button creates a new text file
	static class addNotebook_Action implements ActionListener{
		public void actionPerformed (ActionEvent e){
			String name = nameNotebook.getText();
			File file = new File(name + ".java");

			try {

				// create a new file with name specified by the file object
				boolean value = file.createNewFile();
				if (value) {
					System.out.println("New Java File is created.");
				}
				else {
					System.out.println("The file already exists.");
				}
			}
			catch(Exception f) {
				f.getStackTrace();
			}
			
		}
	}
	
}

