
/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */

import acm.graphics.*;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.util.*;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class FacePamphletCanvas extends GCanvas implements FacePamphletConstants {

	private GLabel message = new GLabel("");
	private GLabel name;
	private GLabel age;
	private GLabel gender;
	private GLabel friends;
	private GLabel friend;

	/**
	 * Constructor This method takes care of any initialization needed for the
	 * display
	 */
	public FacePamphletCanvas() {
		add(message);
	}

	/**
	 * This method displays a message string near the bottom of the canvas. Every
	 * time this method is called, the previously displayed message (if any) is
	 * replaced by the new message text passed in.
	 */

	public void showMessage(String msg) {
		remove(message);
		message = new GLabel(msg);
		message.setFont(MESSAGE_FONT);
		add(message, (getWidth() - message.getWidth()) / 2, getHeight() - BOTTOM_MESSAGE_MARGIN);
	}

	/**
	 * This method displays the given profile on the canvas. The canvas is first
	 * cleared of all existing items (including messages displayed near the bottom
	 * of the screen) and then the given profile is displayed. The profile display
	 * includes the name of the user from the profile, the corresponding image (or
	 * an indication that an image does not exist), the status of the user, and a
	 * list of the user's friends in the social network.
	 */
	public void displayProfile(FacePamphletProfile profile) {
		removeAll();
		displayName(profile);
		displayImage(profile);
		displayStatus(profile);
		displayFriends(profile);
		displayAge(profile);
		displayGender(profile);
	}

	private void displayName(FacePamphletProfile profile) {
		name = new GLabel(profile.getName());
		add(name, LEFT_MARGIN, TOP_MARGIN + name.getHeight());
		name.setFont(PROFILE_NAME_FONT);
		name.setColor(Color.BLUE);
		add(name);
	}

	private void displayImage(FacePamphletProfile profile) {
		if (profile.getImage() != null) {
			profile.getImage().scale(IMAGE_WIDTH / profile.getImage().getWidth(),
					IMAGE_HEIGHT / profile.getImage().getHeight());
			add(profile.getImage(), LEFT_MARGIN, (TOP_MARGIN + IMAGE_MARGIN + name.getHeight()));
		} else {
			GRect noImage = new GRect(LEFT_MARGIN, TOP_MARGIN + IMAGE_MARGIN + name.getHeight(), IMAGE_WIDTH,
					IMAGE_HEIGHT);
			add(noImage);

			GLabel noImageLabel = new GLabel("No Image");
			noImageLabel.setFont(PROFILE_IMAGE_FONT);
			add(noImageLabel, LEFT_MARGIN + IMAGE_WIDTH / 2 - noImageLabel.getWidth() / 2,
					TOP_MARGIN + IMAGE_MARGIN + name.getHeight() + IMAGE_HEIGHT / 2);
		}
	}

	private void displayStatus(FacePamphletProfile profile) {
		if (profile.getStatus() == "") {
			GLabel status = new GLabel("No current status");
			status.setFont(PROFILE_STATUS_FONT);
			add(status, LEFT_MARGIN, TOP_MARGIN + IMAGE_MARGIN + IMAGE_HEIGHT + STATUS_MARGIN + name.getHeight());
		} else {
			GLabel status = new GLabel(profile.getName() + " is " + profile.getStatus());
			status.setFont(PROFILE_STATUS_FONT);
			add(status, LEFT_MARGIN, TOP_MARGIN + IMAGE_MARGIN + IMAGE_HEIGHT + STATUS_MARGIN + name.getHeight());
		}
	}

	private void displayFriends(FacePamphletProfile profile) {
		GLabel friends = new GLabel("Friends");
		friends.setFont(PROFILE_FRIEND_LABEL_FONT);
		add(friends, getWidth() / 2, TOP_MARGIN + name.getHeight());
		Iterator<String> iterator = profile.getFriends();
		int x = -1;
		while (iterator.hasNext()) {
			GLabel friend = new GLabel(iterator.next());
			x++;
			friend.setFont(PROFILE_FRIEND_FONT);
			add(friend, getWidth() / 2, TOP_MARGIN + friends.getHeight() + 2 + name.getHeight() + friends.getHeight()
					+ (x * (2 + friend.getHeight())));
		}
	}

	public void displayFriendsOfFriend(FacePamphletProfile profile) {
		friends = new GLabel(profile.getName() + "'s friends:");
		friends.setFont(PROFILE_FRIEND_LABEL_FONT);
		add(friends, getWidth() / 4 * 3, TOP_MARGIN + name.getHeight());
		int x = -1;
		Iterator<String> iterator = profile.getFriends();
		while (iterator.hasNext()) {
			x++;
			friend = new GLabel(iterator.next());
			friend.setFont(PROFILE_FRIEND_FONT);
			add(friend, getWidth() / 4 * 3, TOP_MARGIN + friends.getHeight() + 2 + name.getHeight()
					+ friends.getHeight() + (x * (2 + friend.getHeight())));
		}
	}

	private void displayAge(FacePamphletProfile profile) {
		if (profile.getAge() == 0) {
			age = new GLabel("Profile age is unknown");
			age.setFont(PROFILE_STATUS_FONT);
			add(age, LEFT_MARGIN,
					TOP_MARGIN + IMAGE_MARGIN + IMAGE_HEIGHT + STATUS_MARGIN + name.getHeight() + age.getHeight());
		} else {
			GLabel age = new GLabel("Age: " + profile.getAge());
			age.setFont(PROFILE_STATUS_FONT);
			add(age, LEFT_MARGIN,
					TOP_MARGIN + IMAGE_MARGIN + IMAGE_HEIGHT + STATUS_MARGIN + name.getHeight() + age.getHeight());
		}
	}

	private void displayGender(FacePamphletProfile profile) {
		if (profile.getGender() == "") {
			gender = new GLabel("Profile gender is unknown");
			gender.setFont(PROFILE_STATUS_FONT);
			add(gender, LEFT_MARGIN, TOP_MARGIN + IMAGE_MARGIN + IMAGE_HEIGHT + STATUS_MARGIN + name.getHeight()
					+ age.getHeight() + gender.getHeight());
		} else {
			gender = new GLabel("Profile Gender: " + profile.getGender());
			gender.setFont(PROFILE_STATUS_FONT);
			add(gender, LEFT_MARGIN, TOP_MARGIN + IMAGE_MARGIN + IMAGE_HEIGHT + STATUS_MARGIN + name.getHeight()
					+ age.getHeight() + gender.getHeight());
		}
	}
}
