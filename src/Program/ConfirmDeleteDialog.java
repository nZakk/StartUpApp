package Program;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ConfirmDeleteDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;

	boolean choice = false;
	ImageIcon icon = new ImageIcon("icon.png");

	// upcasting to use on listener dunctions
	JButton confirmButton;
	JButton cancelButton;

	// Constructor to frame parent
	ConfirmDeleteDialog(Frame parent) {
		// setting the locationof the dialog relative to the main frame
		super(parent, "Create Profile Dialog", true);
		setLocationRelativeTo(parent);

		// setting dimensions
		this.setSize(500, 110);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle("SelfStartUp - Delete");
		this.setIconImage(icon.getImage());
		this.setLayout(new BorderLayout());

		// Creating main pane and main text
		JPanel main = new JPanel();
		main.setPreferredSize(new Dimension(500, 35));
		JLabel text = new JLabel("Are you sure you want to delete?");
		main.add(text);

		// Creating the bottom panel and its dimension
		JPanel bottom = new JPanel();
		bottom.setLayout(new BorderLayout());
		bottom.setBackground(Color.LIGHT_GRAY);
		bottom.setPreferredSize(new Dimension(0, 100));

		// Creating buttons
		confirmButton = new JButton();
		confirmButton.setPreferredSize(new Dimension(75, 25));
		JLabel confirmText = new JLabel("Confirm");
		confirmButton.add(confirmText);
		confirmButton.setMargin(new Insets(0, 13, 0, 0));
		confirmButton.addActionListener(this);

		cancelButton = new JButton();
		cancelButton.setPreferredSize(new Dimension(75, 25));
		JLabel cancelText = new JLabel("Cancel");
		cancelButton.add(cancelText);
		cancelButton.setMargin(new Insets(0, 16, 0, 0));
		cancelButton.addActionListener(this);

		// creaing buttons label adding buttons
		JLabel buttons = new JLabel();
		buttons.setLayout(new FlowLayout());
		buttons.add(confirmButton);
		buttons.add(cancelButton);
		buttons.setPreferredSize(new Dimension(750, 35));

		// adding buttons label to bottom panel
		bottom.add(buttons, BorderLayout.SOUTH);

		// adding main and bottom panel into the frame
		this.add(main, BorderLayout.NORTH);
		this.add(bottom, BorderLayout.SOUTH);

		// setting it visible
		this.setVisible(true);
	}

	// Constructor to dialog parent
	public ConfirmDeleteDialog(JDialog parent) {
		// setting the locationof the dialog relative to the main frame
		super(parent, "Create Profile Dialog", true);
		setLocationRelativeTo(parent);

		// setting dimensions
		this.setSize(500, 110);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle("SelfStartUp - Delete");
		this.setIconImage(icon.getImage());
		this.setLayout(new BorderLayout());

		// Creating main pane and main text
		JPanel main = new JPanel();
		main.setPreferredSize(new Dimension(500, 35));
		JLabel text = new JLabel("Are you sure you want to delete?");
		main.add(text);

		// Creating the bottom panel and its dimension
		JPanel bottom = new JPanel();
		bottom.setLayout(new BorderLayout());
		bottom.setBackground(Color.LIGHT_GRAY);
		bottom.setPreferredSize(new Dimension(0, 100));

		// Creating buttons
		confirmButton = new JButton();
		confirmButton.setPreferredSize(new Dimension(75, 25));
		JLabel confirmText = new JLabel("Confirm");
		confirmButton.add(confirmText);
		confirmButton.setMargin(new Insets(0, 13, 0, 0));
		confirmButton.addActionListener(this);

		cancelButton = new JButton();
		cancelButton.setPreferredSize(new Dimension(75, 25));
		JLabel cancelText = new JLabel("Cancel");
		cancelButton.add(cancelText);
		cancelButton.setMargin(new Insets(0, 16, 0, 0));
		cancelButton.addActionListener(this);

		// creaing buttons label adding buttons
		JLabel buttons = new JLabel();
		buttons.setLayout(new FlowLayout());
		buttons.add(confirmButton);
		buttons.add(cancelButton);
		buttons.setPreferredSize(new Dimension(750, 35));

		// adding buttons label to bottom panel
		bottom.add(buttons, BorderLayout.SOUTH);

		// adding main and bottom panel into the dialog
		this.add(main, BorderLayout.NORTH);
		this.add(bottom, BorderLayout.SOUTH);

		// setting it visible
		this.setVisible(true);
	}

	public boolean getChoice() {
		return choice;
	}

	public void confirmation(boolean choice) {
		this.choice = choice;
	}

	// Dispose this dialog after the choice is made
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(confirmButton)) {

			confirmation(true);
			this.dispose();
		}
		if (e.getSource().equals(cancelButton)) {

			confirmation(false);
			this.dispose();

		}
	}

}
