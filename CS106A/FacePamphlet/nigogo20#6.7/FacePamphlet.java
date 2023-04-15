
/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;
import acm.graphics.*;
import acm.util.*;
import java.awt.event.*;
import javax.swing.*;

public class FacePamphlet extends Program implements FacePamphletConstants {
	private FacePamphletDatabase database = new FacePamphletDatabase();
	private JTextField textField = new JTextField(TEXT_FIELD_SIZE);
	private JTextField statusTextField = new JTextField(TEXT_FIELD_SIZE);
	private JTextField friendTextField = new JTextField(TEXT_FIELD_SIZE);
	private JTextField pictureTextField = new JTextField(TEXT_FIELD_SIZE);

	private FacePamphletCanvas canvas;

	private FacePamphletProfile currentProfile = null;

	/**
	 * This method has the responsibility for initializing the interactors in the
	 * application, and taking care of any other initialization that needs to be
	 * performed.
	 */
	public void init() {
		JLabel name = new JLabel("Name");
		add(name, NORTH);

		add(textField, NORTH);
		JButton addButton = new JButton("Add");
		add(addButton, NORTH);
		addButton.addActionListener(this);
		JButton deleteButton = new JButton("Delete");
		add(deleteButton, NORTH);
		deleteButton.addActionListener(this);
		JButton lookupButton = new JButton("Lookup");
		add(lookupButton, NORTH);
		lookupButton.addActionListener(this);

		add(statusTextField, WEST);
		statusTextField.addActionListener(this);
		JButton statusButton = new JButton("Change Status");
		add(statusButton, WEST);
		statusButton.addActionListener(this);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);

		add(pictureTextField, WEST);
		pictureTextField.addActionListener(this);
		JButton pictureButton = new JButton("Change Picture");
		add(pictureButton, WEST);
		pictureButton.addActionListener(this);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);

		add(friendTextField, WEST);
		friendTextField.addActionListener(this);
		JButton addFriendButton = new JButton("Add Friend");
		add(addFriendButton, WEST);
		addFriendButton.addActionListener(this);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);

		canvas = new FacePamphletCanvas();
		add(canvas);
		addActionListeners();

		statusTextField.setText("");
		friendTextField.setText("");
		pictureTextField.setText("");
	}

	/**
	 * This class is responsible for detecting when the buttons are clicked or
	 * interactors are used, so you will have to add code to respond to these
	 * actions.
	 */
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("Add")) {

			if (!database.containsProfile(textField.getText())) {
				currentProfile = new FacePamphletProfile(textField.getText());
				database.addProfile(currentProfile);
				canvas.displayProfile(currentProfile);
				canvas.showMessage("New profile created");
				textField.setText("");
			} else {
				currentProfile = database.getProfile(textField.getText());
				canvas.displayProfile(currentProfile);
				canvas.showMessage("A profile with name " + currentProfile.getName() + " already exists");
			}
		}

		if (e.getActionCommand().equals("Delete")) {
			if (database.containsProfile(textField.getText())) {
				currentProfile = database.getProfile((textField.getText()));
				String profileName = currentProfile.getName();
				Iterator<String> itr = currentProfile.getFriends();
				while (itr.hasNext()) {
					String friendName = itr.next();
					database.getProfile(friendName).removeFriend(profileName);
				}
				database.deleteProfile(profileName);
				canvas.removeAll();
				currentProfile = null;
				canvas.showMessage("Profile of " + profileName + " deleted");
				textField.setText("");
			} else {
				canvas.showMessage("A profile with the name " + textField.getText() + " does not exist");
			}
		}

		if (e.getActionCommand().equals("Lookup")) {
			if (database.containsProfile(textField.getText())) {
				currentProfile = database.getProfile(textField.getText());
				canvas.displayProfile(database.getProfile(textField.getText()));
				canvas.showMessage("Displaying " + textField.getText());
				textField.setText("");
			} else {
				canvas.removeAll();
				canvas.showMessage("A profile with the name " + textField.getText() + " does not exist");
			}
		}

		if (e.getActionCommand().equals("Change Status") || e.getSource() == statusTextField) {
			if (currentProfile != null) {
				currentProfile.setStatus(statusTextField.getText());
				canvas.displayProfile(currentProfile);
				canvas.showMessage("Status updated to " + currentProfile.getStatus());
				statusTextField.setText("");
			} else {
				canvas.showMessage("Please select a profile to change status");
			}
		}

		if (e.getActionCommand().equals("Add Friend") || e.getSource() == friendTextField) {
			if (currentProfile != null) {
				if (database.containsProfile(friendTextField.getText())) {
					FacePamphletProfile friendProfile = database.getProfile(friendTextField.getText());
					String userFriend = friendTextField.getText();
					if (currentProfile.addFriend(userFriend) == true) {
						currentProfile.addFriend(userFriend);
						friendProfile.addFriend(currentProfile.getName());
						canvas.displayProfile(currentProfile);
						canvas.showMessage(friendProfile.getName() + " added as a friend");
						friendTextField.setText("");
					} else {
						canvas.showMessage(
								currentProfile.getName() + " already has " + friendProfile.getName() + " as a friend");
						friendTextField.setText("");
					}
				} else {
					canvas.showMessage(friendTextField.getText() + " does not exist");
					friendTextField.setText("");
				}
			} else {
				canvas.showMessage("Please select a profile to add friend");
				friendTextField.setText("");
			}
		}

		if (e.getActionCommand().equals("Change Picture") || e.getSource() == pictureTextField) {
			if (currentProfile != null) {
				GImage image = null;
				try {
					image = new GImage(pictureTextField.getText());
					currentProfile.setImage(image);
					canvas.displayProfile(currentProfile);
					canvas.showMessage("Profile picture changed");
					pictureTextField.setText("");
				} catch (ErrorException ex) {
					canvas.showMessage("Unable to open image file: " + pictureTextField.getText());
					pictureTextField.setText("");
				}
			} else {
				canvas.showMessage("Please select a profile to change picture");
			}
		}
	}
}
