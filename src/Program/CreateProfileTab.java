package Program;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;


public class CreateProfileTab extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
// up casting to be used on listener functions
	ImageIcon icon = new ImageIcon("icon.png");
	JTextField profileName;
	JButton submitButton;

	CreateProfileTab(Frame parent) {

		// setting the locationof the dialog relative to the main frame
		super(parent, "Create Profile Dialog", true);
		setLocationRelativeTo(parent);
		


		// setting dimensions
		this.setSize(500, 75);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle("SelfStartUp - Create Profile");
		this.setIconImage(icon.getImage());
		this.setLayout(new BorderLayout());

		// Creating main pane, textfield to the profile name and the submit buttton
		JPanel main = new JPanel();
		
		profileName = new JTextField("Profile name", 32);

		submitButton = new JButton();
		submitButton.setPreferredSize(new Dimension(75, 25));
		JLabel submitText = new JLabel("Submit");
		submitButton.add(submitText);
		submitButton.setMargin(new Insets(0, 17, 0, 0));
		submitButton.addActionListener(this);
		
		//adds components to main
		main.add(profileName);
		main.add(submitButton);
		
		//adds main to dialog
		this.add(main, BorderLayout.CENTER);

		//set it visible
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(submitButton)) {
			// sets the profileDir and the file name from the text field
			String folderPath = "./profiles";
			File profileDir = new File(folderPath);
			String fileName = profileName.getText();

			try {

				File profileObj = new File(profileDir, fileName + ".txt"); // create File object
				if (profileObj.createNewFile()) { // try to create the file
					System.out.println("File created: " + profileObj.getName());
					//close dialog after profile is created
					this.dispose();
				} else {
					JOptionPane.showMessageDialog(this, "This file already exists!",  "Error", JOptionPane.ERROR_MESSAGE);
				}
			} catch (IOException e1) {
				System.out.println("An error occurred.");
				e1.printStackTrace(); // Print error details
			}
			
		}
	}

}
