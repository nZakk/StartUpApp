package Program;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class AppsTab extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;

	ImageIcon icon = new ImageIcon("icon.png");

	// up casting to use on listener functions
	JButton addButton;
	JButton saveButton;
	JButton deleteButton;
	JButton submitButton;
	JButton cancelButton;
	JTextField pathTextField;
	String profilePath;

	//the apps paths edit need to be saved, if cancel is pressed all the changes are lost
	DefaultListModel<String> listModel = new DefaultListModel<>();
	JList<String> list = new JList<>(listModel);
	String[] apps = null;

	AppsTab(Frame parent, String profilePath) throws IOException {
		// setting the locationof the dialog relative to the main frame
		super(parent, "Apps Dialog", true);
		this.profilePath = profilePath;
		setLocationRelativeTo(parent);

		// setting dimensions
		this.setSize(750, 500);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle("SelfStartUp - Edit Profile");
		this.setIconImage(icon.getImage());
		this.setLayout(new BorderLayout());

		// creating top panel and top title
		JPanel top = new JPanel();
		top.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 15));
		top.setBackground(Color.LIGHT_GRAY);
		top.setPreferredSize(new Dimension(0, 50));
		JLabel title = new JLabel("Apps");
		top.add(title);

		JPanel main = new JPanel();

		// input all paths from file into scroll pane
		try (BufferedReader br = new BufferedReader(new FileReader(profilePath))) {
			for (String line; (line = br.readLine()) != null;) {
				listModel.addElement(line);
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(720, 290));

		// adds scroll panel to main panel
		main.add(scrollPane);

		//creating button and bottom panels
		JPanel bottom = new JPanel();
		bottom.setLayout(new BorderLayout());
		bottom.setBackground(Color.LIGHT_GRAY);
		bottom.setPreferredSize(new Dimension(0, 100));

		JLabel buttons = new JLabel();
		buttons.setLayout(new FlowLayout(FlowLayout.TRAILING));
		buttons.setPreferredSize(new Dimension(750, 35));

		//creating texts field for direct paths input
		pathTextField = new JTextField(24);

		//Creating the buttons components
		submitButton = new JButton();
		submitButton.setPreferredSize(new Dimension(75, 25));
		JLabel submitText = new JLabel("Submit");
		submitButton.add(submitText);
		submitButton.setMargin(new Insets(0, 17, 0, 0));
		submitButton.addActionListener(this);

		deleteButton = new JButton();
		deleteButton.setPreferredSize(new Dimension(75, 25));
		JLabel deleteText = new JLabel("Delete");
		deleteButton.add(deleteText);
		deleteButton.setMargin(new Insets(0, 17, 0, 0));
		deleteButton.addActionListener(this);

		addButton = new JButton();
		addButton.setPreferredSize(new Dimension(75, 25));
		JLabel addText = new JLabel("Add");
		addButton.add(addText);
		addButton.setMargin(new Insets(0, 22, 0, 0));
		addButton.addActionListener(this);

		saveButton = new JButton();
		saveButton.setPreferredSize(new Dimension(75, 25));
		JLabel saveText = new JLabel("Save");
		saveButton.add(saveText);
		saveButton.setMargin(new Insets(0, 19, 0, 0));
		saveButton.addActionListener(this);

		cancelButton = new JButton();
		cancelButton.setPreferredSize(new Dimension(75, 25));
		JLabel cancelText = new JLabel("Cancel");
		cancelButton.add(cancelText);
		cancelButton.setMargin(new Insets(0, 16, 0, 0));
		cancelButton.addActionListener(this);

		// adding textfield buttons to buttons panel and then to bottom panel
		buttons.add(pathTextField);
		buttons.add(submitButton);
		buttons.add(addButton);
		buttons.add(deleteButton);
		buttons.add(saveButton);
		buttons.add(cancelButton);
		bottom.add(buttons, BorderLayout.SOUTH);

		// adds panels to the dialog
		this.add(top, BorderLayout.NORTH);
		this.add(main, BorderLayout.CENTER);
		this.add(bottom, BorderLayout.SOUTH);

		// set dialog visible
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(submitButton)) {
			//check if the path input is a valid file and if is executable
			File selectedFile = new File(pathTextField.getText());
			
			if(selectedFile.exists() && selectedFile.isFile() && selectedFile.canExecute()) {
				listModel.addElement(pathTextField.getText());
			}
			
		}
		if (e.getSource().equals(deleteButton)) {

			//check if a app path is selected
			if (list.getSelectedIndex() == -1) {
				JOptionPane.showMessageDialog(this, "Please select a path.", "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				//open the confirm dialog
				ConfirmDeleteDialog confirmation = new ConfirmDeleteDialog(this);

				if (confirmation.getChoice() == true) {
					//remove from model
					listModel.removeElement(list.getSelectedValue());
				}
			}
		}

		if (e.getSource().equals(addButton)) {

			try {
				JFileChooser fileChooser = new JFileChooser();

				FileNameExtensionFilter filter = new FileNameExtensionFilter("Executable files", "exe", "lnk");// create
																												// a
																												// filter
																												// to
																												// just
																												// include
																												// the
																												// file
																												// types
																												// said

				fileChooser.setFileFilter(filter); // sets the filter to filechooser

				fileChooser.setCurrentDirectory(new File("C:\\"));// sets the curr directory to fileChooser it need to
																	// be a File type, cant be just the path

				int result = fileChooser.showOpenDialog(null); // showOpenDialog show the file explore of the file
																// chooser null means to show in the center od the
																// screen

				if (result == JFileChooser.APPROVE_OPTION) {

					File selectedFile = new File(fileChooser.getSelectedFile().getAbsolutePath());// we are creating a
																									// File object with
																									// the path of the
																									// file selected by
																									// the fileChooser
					System.out.println("Filepath " + selectedFile);

					if (!Desktop.isDesktopSupported()) { // basically means "can we open this with an application that
															// we have?"
						System.out.println("Not supported");
					} else {
						listModel.addElement(selectedFile.getAbsolutePath());
					}

				} else if (result == JFileChooser.CANCEL_OPTION) {
					System.out.println("Cancelled");
				}

			} catch (Exception e2) {

				System.out.println(e2);

			}

		}

		if (e.getSource().equals(saveButton)) {

			//Open and write all the changes made into the apps paths into the txt file
			try {
				BufferedWriter f_writer = new BufferedWriter(new FileWriter(profilePath));
				for (int i = 0; i < listModel.getSize(); i++) {
					f_writer.write(listModel.get(i) + "\n");
				}
				f_writer.close();
			} catch (IOException e1) {
				System.out.println(e1.getMessage());
			}

			this.dispose();
		}

		if (e.getSource().equals(cancelButton)) {
			// cancel the edit and close the dialog without and changes
			this.dispose();
		}

	}

}