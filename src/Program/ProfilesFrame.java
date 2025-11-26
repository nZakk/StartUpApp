package Program;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ProfilesFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	ImageIcon icon = new ImageIcon("icon.png");

	// up casting to use on listener functions
	JButton addButton;
	JButton editButton;
	JButton startButton;
	JButton deleteButton;

	JDialog apps;
	JPanel main;

	// setting the profiles directory
	String profileFolder = "./profiles";

	// creating the main scroll pane
	DefaultListModel<String> listModel = new DefaultListModel<>();
	JList<String> profileList = new JList<>(listModel);
	JScrollPane scrollPane = new JScrollPane(profileList);

	ProfilesFrame() {

		super();
		// setting main frame dimensions and layout
		this.setSize(750, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("SelfStartUp");
		this.setIconImage(icon.getImage());
		this.setLayout(new BorderLayout());

		// creating and setting dimension and layout to top panel
		JPanel top = new JPanel();
		top.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 15));
		top.setBackground(Color.LIGHT_GRAY);
		top.setPreferredSize(new Dimension(0, 50));
		JLabel title = new JLabel("Profiles");
		top.add(title);
		this.add(top, BorderLayout.NORTH);

		// creating main panel
		main = new JPanel();

		// function to updadte de list in scrool panel to show profiles in the folder
		updateList();

		// settings scroll pane
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(720, 290));
		main.add(scrollPane);
		this.add(main, BorderLayout.CENTER);

		// Creating bottom panel
		JPanel bottom = new JPanel();
		bottom.setLayout(new BorderLayout());
		bottom.setBackground(Color.LIGHT_GRAY);
		bottom.setPreferredSize(new Dimension(0, 100));

		// Creating buttons layout and buttos components with actioner listener
		JLabel buttons = new JLabel();
		buttons.setLayout(new FlowLayout(FlowLayout.TRAILING));
		buttons.setPreferredSize(new Dimension(750, 35));

		addButton = new JButton();
		addButton.setPreferredSize(new Dimension(75, 25));
		JLabel addText = new JLabel("Add");
		addButton.add(addText);
		addButton.setMargin(new Insets(0, 22, 0, 0));
		addButton.addActionListener(this);

		editButton = new JButton();
		editButton.setPreferredSize(new Dimension(75, 25));
		JLabel editText = new JLabel("Edit");
		editButton.add(editText);
		editButton.setMargin(new Insets(0, 21, 0, 0));
		editButton.addActionListener(this);

		deleteButton = new JButton();
		deleteButton.setPreferredSize(new Dimension(75, 25));
		JLabel deleteText = new JLabel("Delete");
		deleteButton.add(deleteText);
		deleteButton.setMargin(new Insets(0, 18, 0, 0));
		deleteButton.addActionListener(this);

		startButton = new JButton();
		startButton.setPreferredSize(new Dimension(75, 25));
		JLabel startText = new JLabel("Start");
		startButton.add(startText);
		startButton.setMargin(new Insets(0, 19, 0, 0));
		startButton.addActionListener(this);

		// adding components to button label
		buttons.add(addButton);
		buttons.add(editButton);
		buttons.add(deleteButton);
		buttons.add(startButton);

		// adding buttons label to panel
		bottom.add(buttons, BorderLayout.SOUTH);
		this.add(bottom, BorderLayout.SOUTH);

		// setting grame visible
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(editButton)) {
			// test if a profile is selected
			if (profileList.getSelectedIndex() == -1) {
				JOptionPane.showMessageDialog(this, "Please select a Profile.", "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				try {
					// open the edit profile dialog with the selected profile path
					new AppsTab(this, profileFolder + "/" + profileList.getSelectedValue() + ".txt");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}

		if (e.getSource().equals(addButton)) {
			// open create profile dialog
			new CreateProfileTab(this);

			// and update profile list
			updateList();
		}

		if (e.getSource().equals(deleteButton)) {
			// test if a profile is selected
			if (profileList.getSelectedIndex() == -1) {
				JOptionPane.showMessageDialog(this, "Please select a Profile.", "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				// open a confirm dialog to delete a profile
				ConfirmDeleteDialog confirmation = new ConfirmDeleteDialog(this);

				// delete if confirmation is true
				if (confirmation.getChoice() == true) {
					File profileFile = new File(profileFolder + "/" + profileList.getSelectedValue() + ".txt");
					profileFile.delete();

					// and update profile list
					updateList();
				}
			}

		}

		if (e.getSource().equals(startButton)) {
			// test if a profile is selected
			if (profileList.getSelectedIndex() == -1) {
				JOptionPane.showMessageDialog(this, "Please select a Profile.", "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				// open the profile selected
				try (BufferedReader br = new BufferedReader(
						new FileReader(profileFolder + "/" + profileList.getSelectedValue() + ".txt"))) {
					// read the path each line and open it
					for (String line; (line = br.readLine()) != null;) {
						File file = new File(line);

						if (!Desktop.isDesktopSupported()) {// if is a executable

						} else {
							
							Desktop desktop = Desktop.getDesktop();
							desktop.open(file); // opens the file
						}
					}
				} catch (IOException e2) {
					System.out.println(e2.getMessage());

				}
			}
		}

	}

	// update the scrollpane list with the profiles names in the profiles folder
	void updateList() {
		File folder = new File(profileFolder);
		listModel.clear();

		if (folder.exists() && folder.isDirectory()) {

			File[] files = folder.listFiles();

			if (files != null) {
				for (File file : files) {
					if (file.isFile()) {
						listModel.addElement(file.getName().substring(0, file.getName().length() - 4));
					}
				}
			} else {
				System.out.println("The folder is empty or an I/O error ocurred!");
			}

		} else {
			System.out.println("The folder path is not valid!");
		}

	}

}
