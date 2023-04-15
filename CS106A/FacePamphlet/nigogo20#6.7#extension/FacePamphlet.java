
/*
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;

import java.util.StringTokenizer;

import java.util.Iterator;
import acm.graphics.*;
import acm.util.*;

import java.awt.Graphics;
import java.awt.event.*;
import javax.swing.*;
import javafx.scene.image.*;
import javafx.scene.paint.*;

public class FacePamphlet extends Program implements FacePamphletConstants {
	private FacePamphletDatabase database = new FacePamphletDatabase();
	private JTextField textField = new JTextField(TEXT_FIELD_SIZE);
	private JTextField statusTextField = new JTextField(TEXT_FIELD_SIZE);
	private JTextField friendTextField = new JTextField(TEXT_FIELD_SIZE);
	private JTextField pictureTextField = new JTextField(TEXT_FIELD_SIZE);
	private JTextField ageTextField = new JTextField(TEXT_FIELD_SIZE);
	private JComboBox<String> genderChooser = new JComboBox<String>();
	private JTextField passwordTextField = new JTextField(TEXT_FIELD_SIZE);
	private JButton passButton = new JButton("Log in");
	private JTextField passTextField = new JTextField(27);
	private JComboBox<String> questions = new JComboBox<String>();
	private JTextField answer = new JTextField(TEXT_FIELD_SIZE);
	private JTextField secAnswer = new JTextField(TEXT_FIELD_SIZE);
	private FacePamphletCanvas canvas;
	public JFrame frame;
	public GCanvas additionalCanvas;
	private JButton enterButton = new JButton("Enter");
	private JButton statusButton = new JButton("Change Status");
	private JButton pictureButton = new JButton("Change Picture");
	private JButton addFriendButton = new JButton("Add Friend");
	private JButton ageButton = new JButton("Change Age");
	private JLabel gender = new JLabel("Select Gender");
	private JLabel friendsOfFriend = new JLabel("Find Friends of Your Friend");
	private JTextField friends = new JTextField(TEXT_FIELD_SIZE);
	private FacePamphletProfile currentProfile = null;
	private FacePamphletProfile friendProfile = null;
	private JButton forgotPass = new JButton("Forgot Password?");
	private JButton saveButton = new JButton("Save Answer");
	private JLabel securityQuestion = new JLabel("In case you forgot password:");
	private JButton setButton = new JButton("Set Password");
	private JLabel password = new JLabel("Secure Your Account");
	private JButton findButton = new JButton("Find");
	private JLabel friend = new JLabel("Find friends of your friend");

	/**
	 * This method has the responsibility for initializing the interactors in the
	 * application, and taking care of any other initialization that needs to be
	 * performed.
	 */
	public void init() {
//canvas init
		canvas = new FacePamphletCanvas();
		add(canvas);
		addActionListeners();
		setSize(1200, 600);

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
		add(statusButton, WEST);
		statusButton.addActionListener(this);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);

		add(pictureTextField, WEST);
		pictureTextField.addActionListener(this);
		add(pictureButton, WEST);
		pictureButton.addActionListener(this);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);

		add(friendTextField, WEST);
		friendTextField.addActionListener(this);
		add(addFriendButton, WEST);
		addFriendButton.addActionListener(this);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);

		add(ageTextField, WEST);
		ageTextField.addActionListener(this);
		add(ageButton, WEST);
		ageButton.addActionListener(this);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);

		add(gender, WEST);
		add(genderChooser, WEST);
		genderChooser.addItem("Male");
		genderChooser.addItem("Female");
		genderChooser.addItem("Other");
		genderChooser.addActionListener(this);
		genderChooser.setEditable(false);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);

		add(friend, EAST);
		add(friends, EAST);

		add(findButton, EAST);
		findButton.addActionListener(this);
		add(new JLabel(EMPTY_LABEL_TEXT), EAST);
		add(new JLabel(EMPTY_LABEL_TEXT), EAST);

		passwordTextField.setText("");

		add(password, EAST);
		add(passwordTextField, EAST);

		add(setButton, EAST);
		setButton.addActionListener(this);
		add(new JLabel(EMPTY_LABEL_TEXT), EAST);

		add(securityQuestion, EAST);
		add(questions, EAST);
		questionsBox();

		add(answer, EAST);

		add(saveButton, EAST);
		saveButton.addActionListener(this);
		add(new JLabel(EMPTY_LABEL_TEXT), EAST);

		add(forgotPass, EAST);
		forgotPass.addActionListener(this);
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
				add();
			} else {
				currentProfile = database.getProfile((textField.getText()));
				canvas.displayProfile(currentProfile);
				canvas.showMessage("A profile with name " + currentProfile.getName() + " already exists");
			}
		}

		if (e.getActionCommand().equals("Delete")) {
			if (database.containsProfile(textField.getText()) == true) {
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
			} else {
				canvas.showMessage("A profile with the name " + textField.getText() + " does not exist");
			}
		}

		if (e.getActionCommand().equals("Lookup")) {
			if (database.containsProfile(textField.getText())) {
				currentProfile = database.getProfile((textField.getText()));
				if (!currentProfile.getPassword().equals("")) {
					remove();
					loginWindow();
					canvas.displayProfile(currentProfile);
					canvas.showMessage("Displaying " + currentProfile.getName());
				} else {
					canvas.displayProfile(currentProfile);
					canvas.showMessage("Displaying " + currentProfile.getName());
					add();
				}
			} else {
				canvas.removeAll();
				canvas.showMessage("A profile with the name " + textField.getText() + " does not exist");
			}

		}
		if (e.getActionCommand().equals("Change Status")) {
			if (currentProfile != null) {
				currentProfile.setStatus(statusTextField.getText());
				canvas.displayProfile(currentProfile);
				canvas.showMessage("Status updated to " + currentProfile.getStatus());
				statusTextField.setText("");
			} else {
				canvas.showMessage("Please select a profile to change status");
			}
		}

		if (e.getActionCommand().equals("Add Friend")) {
			if (currentProfile != null) {
				if (database.containsProfile(friendTextField.getText())) {
					friendProfile = database.getProfile(friendTextField.getText());
					if (currentProfile.addFriend(friendTextField.getText())) {
						currentProfile.addFriend(friendTextField.getText());
						friendProfile.addFriend(currentProfile.getName());
						canvas.displayProfile(currentProfile);
						canvas.showMessage(friendProfile.getName() + " added as a friend");
						friendTextField.setText("");
					} else {
						canvas.showMessage(currentProfile.getName() + " already has " + friendTextField.getText()
								+ " as a friend");
					}
				} else {
					canvas.showMessage(friendTextField.getText() + " does not exist");
				}
			} else {
				canvas.showMessage("Please select a profile to add friend");
			}
		}

		if (e.getActionCommand().equals("Change Picture")) {
			if (currentProfile != null) {
				if (pictureTextField.getText().equals("")) {
					canvas.showMessage("Please enter valid picture name");
				} else {
					GImage image = null;
					try {
						image = new GImage(pictureTextField.getText());
						currentProfile.setImage(image);
						canvas.displayProfile(currentProfile);
						pictureTextField.setText("");
						canvas.showMessage("Profile picture changed");
					} catch (ErrorException ex) {
						canvas.showMessage("Unable to open image file: " + pictureTextField.getText());
					}
				}
			} else {
				canvas.showMessage("Please select a profile to change picture");
			}
		}

		if (e.getActionCommand().equals("Change Age")) {
			if (currentProfile != null) {
				if (!ageTextField.getText().equals("")) {
					String str = ageTextField.getText();
					int z = 0;
					// checks if entered string contains only numbers
					for (int i = 0; i < str.length(); i++) {
						if (str.charAt(i) < '0' || str.charAt(i) > '9') {
							z++;
						}
					}
					if (z == 0) {
						int age = Integer.parseInt(ageTextField.getText());
						if (age > 0 && age < 150) {
							currentProfile.setAge(age);
							canvas.displayProfile(currentProfile);
							canvas.showMessage("Profile age changed to " + currentProfile.getAge());
							ageTextField.setText("");
						} else {
							canvas.showMessage("Enter the valid age");
						}
					} else {
						canvas.displayProfile(currentProfile);
						canvas.showMessage("Enter the valid age");
					}
				} else {
					canvas.showMessage("Enter the valid age");
				}
			} else {
				canvas.showMessage("Please select a profile to change age");
			}
		}

		if (e.getSource().equals(genderChooser)) {
			String gender = (String) genderChooser.getSelectedItem();
			if (currentProfile != null) {
				currentProfile.setGender(gender);
				canvas.displayProfile(currentProfile);
				canvas.showMessage("Profile gender changed to " + currentProfile.getGender());
			} else {
				canvas.showMessage("Please select a profile to change gender");
			}
		}

		// finds friends of friend, friends of friend display is temporary, diminishes
		// after any other action
		if (e.getActionCommand().equals("Find")) {
			if (currentProfile != null) {
				if (database.containsProfile(friends.getText())) {
					if (checkFriend(currentProfile, friends.getText())) {
						canvas.displayFriendsOfFriend(database.getProfile(friends.getText()));
						friends.setText("");
					}
				}
			}
		}

		if (e.getActionCommand().equals("Set Password")) {
			if (currentProfile != null) {
				if (passwordTextField.getText().length() >= 6) {
					currentProfile.setPassword(passwordTextField.getText());
					canvas.showMessage("Profile password saved.");
					passwordTextField.setText("");
				} else {
					canvas.showMessage("Enter the Password of minimum 6 characters length.");
				}
			} else {
				canvas.showMessage("Please select a profile to set password.");
			}
		}

		if (e.getActionCommand().equals("Log in")) {
			if (currentProfile != null) {
				if (correctWrongPass(currentProfile, passTextField.getText())) {
					frame.dispose();
					canvas.showMessage("You logged in successfully");
					add();
				} else {
					remove();
					frame.dispose();
					canvas.showMessage("Password incorect");
				}
			}
		}

		// saves security question answer
		if (e.getActionCommand().equals("Save Answer")) {
			if (currentProfile != null) {
				if (!currentProfile.getPassword().equals("")) {
					String question = questions.getSelectedItem().toString();
					currentProfile.setQuestion(question);
					currentProfile.setAnswer(answer.getText());
					answer.setText("");
					canvas.showMessage("Security answer saved.");
					answer.setText("");
				} else {
					canvas.showMessage("You have to set password first.");
				}
			} else {
				canvas.showMessage("Please select a profile to set security answer.");
			}
		}

		// if user has set security question and answer he can use button "Forgot
		// Password?"
		if (e.getActionCommand().equals("Forgot Password?")) {
			if (currentProfile != null) {
				if (currentProfile.getQuestion().equals("")) {
					canvas.showMessage("There is no security question associated with your account");
				} else {
					remove();
					passResetWindow(currentProfile.getQuestion(), currentProfile.getAnswer());
				}
			} else {
				canvas.showMessage("Please select a profile first");
			}
		}

		// "Enter" button is in security question additional window where user answers
		// security question
		if (e.getActionCommand().equals("Enter")) {
			if (correctWrongAns(currentProfile, secAnswer.getText())) {
				frame.dispose();
				canvas.showMessage("Set new password");
				add();
			} else {
				frame.dispose();
				canvas.showMessage("Answer was incorrect");
			}
		}
	}

	// checks if profile has friend of that name
	private boolean checkFriend(FacePamphletProfile profile, String friend) {
		Iterator<String> iterator = profile.getFriends();
		int x = 0;
		while (iterator.hasNext()) {
			if (friend == iterator.next()) {
				x++;
			}
		}
		if (x != 0) {
			return false;
		}
		return true;
	}

	public void loginWindow() {
		frame = new JFrame("Login");
		frame.setSize(300, 120);
		frame.setResizable(false);
		frame.setVisible(true);

		additionalCanvas = new GCanvas();
		additionalCanvas.setSize(200, 200);
		additionalCanvas.setVisible(true);
		JLabel passLabel = new JLabel("Enter Password");
		frame.add(passLabel, NORTH);
		frame.add(passTextField, CENTER);

		JButton passButton = new JButton("Log in");
		frame.add(passButton, SOUTH);
		passButton.addActionListener(this);
	}

	// appears on "Forgot Password?" button click ( security question and textfield
	// for answer )
	public void passResetWindow(String quest, String answ) {
		frame = new JFrame("Answer Security Question");
		frame.setSize(300, 120);
		frame.setResizable(false);
		frame.setVisible(true);

		additionalCanvas = new GCanvas();
		additionalCanvas.setSize(200, 200);
		additionalCanvas.setVisible(true);
		JLabel passLabel = new JLabel(quest);
		frame.add(passLabel, NORTH);
		frame.add(secAnswer, CENTER);

		frame.add(enterButton, SOUTH);
		enterButton.addActionListener(this);
	}

	// checks password
	private boolean correctWrongPass(FacePamphletProfile profile, String str) {
		if (profile.getPassword().equals(passTextField.getText())) {
			return true;
		} else {
			return false;
		}
	}

	// checks security question answer
	private boolean correctWrongAns(FacePamphletProfile profile, String str) {
		if (profile.getAnswer().equals(secAnswer.getText())) {
			return true;
		} else {
			return false;
		}
	}

	// remove and add methods are for password secured accounts, until not logged in
	// no one can change profile information
	private void remove() {
		statusTextField.setVisible(false);
		statusButton.setVisible(false);
		pictureTextField.setVisible(false);
		pictureButton.setVisible(false);
		friendTextField.setVisible(false);
		addFriendButton.setVisible(false);
		ageTextField.setVisible(false);
		ageButton.setVisible(false);
		gender.setVisible(false);
		genderChooser.setVisible(false);
		friendsOfFriend.setVisible(false);
		forgotPass.setVisible(false);
		saveButton.setVisible(false);
		securityQuestion.setVisible(false);
		setButton.setVisible(false);
		password.setVisible(false);
		findButton.setVisible(false);
		friend.setVisible(false);
		answer.setVisible(false);
		friends.setVisible(false);
		questions.setVisible(false);
		passwordTextField.setVisible(false);
	}

	private void add() {
		statusTextField.setVisible(true);
		statusButton.setVisible(true);
		pictureTextField.setVisible(true);
		pictureButton.setVisible(true);
		friendTextField.setVisible(true);
		addFriendButton.setVisible(true);
		ageTextField.setVisible(true);
		ageButton.setVisible(true);
		gender.setVisible(true);
		genderChooser.setVisible(true);
		friendsOfFriend.setVisible(true);
		forgotPass.setVisible(true);
		saveButton.setVisible(true);
		securityQuestion.setVisible(true);
		setButton.setVisible(true);
		password.setVisible(true);
		findButton.setVisible(true);
		friend.setVisible(true);
		answer.setVisible(true);
		friends.setVisible(true);
		questions.setVisible(true);
		passwordTextField.setVisible(true);
	}

	// adds security questions
	private void questionsBox() {
		questions.addItem("What is the name of your street?");
		questions.addItem("What primary school did you attend?");
		questions.addItem("What is the middle name of your oldest child?");
		questions.addItem("What time of the day were you born?");
	}

}