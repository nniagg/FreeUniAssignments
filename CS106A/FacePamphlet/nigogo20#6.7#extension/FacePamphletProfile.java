
/*
 * File: FacePamphletProfile.java
 * ------------------------------
 * This class keeps track of all the information for one profile
 * in the FacePamphlet social network.  Each profile contains a
 * name, an image (which may not always be set), a status (what
 * the person is currently doing, which may not always be set),
 * and a list of friends.
 */

import acm.graphics.*;
import java.util.*;

public class FacePamphletProfile implements FacePamphletConstants {
	ArrayList<String> friendsList = new ArrayList<String>();
	private String profileStatus = "";
	private String profileName;
	private String profile;
	private GImage profileImage;
	private int profileAge = 0;
	private String profileGender = "";
	private String profilePassword = "";
	private String securityQuestion = "";
	private String answer = "";

	/**
	 * Constructor This method takes care of any initialization needed for the
	 * profile.
	 */
	public FacePamphletProfile(String name) {
		profileName = name;
	}

	/** This method returns the gender associated with the profile. */
	public String getGender() {
		return profileGender;
	}

	/** This method sets the gender associated with the profile. */
	public void setGender(String gender) {
		profileGender = gender;
	}

	/** This method returns the name associated with the profile. */
	public String getName() {
		return profileName;
	}

	/**
	 * This method returns the image associated with the profile. If there is no
	 * image associated with the profile, the method returns null.
	 */
	public GImage getImage() {
		return profileImage;
	}

	/** This method sets the image associated with the profile. */
	public void setImage(GImage image) {
		profileImage = image;
	}

	/**
	 * This method returns the status associated with the profile. If there is no
	 * status associated with the profile, the method returns the empty string ("").
	 */
	public String getStatus() {
		return profileStatus;
	}

	/** This method sets the status associated with the profile. */
	public void setStatus(String status) {
		profileStatus = status;
	}

	/**
	 * This method returns the age associated with the profile. If there is no age
	 * associated with the profile, the method returns 0.
	 */
	public int getAge() {
		return profileAge;
	}

	/** This method sets the age associated with the profile. */
	public void setAge(int age) {
		profileAge = age;
	}

	/** This method gets the password associated with the profile. */
	public String getPassword() {
		return profilePassword;
	}

	/** This method sets the password associated with the profile. */
	public void setPassword(String str) {
		profilePassword = str;
	}

	/** This method gets the security question associated with the profile. */
	public String getQuestion() {
		return securityQuestion;
	}

	/** This method sets the security question associated with the profile. */
	public void setQuestion(String str) {
		securityQuestion = str;
	}

	/**
	 * This method gets the security question answer associated with the profile.
	 */
	public String getAnswer() {
		return answer;
	}

	/**
	 * This method sets the security question answer associated with the profile.
	 */
	public void setAnswer(String str) {
		answer = str;
	}

	/**
	 * This method adds the named friend to this profile's list of friends. It
	 * returns true if the friend's name was not already in the list of friends for
	 * this profile (and the name is added to the list). The method returns false if
	 * the given friend name was already in the list of friends for this profile (in
	 * which case, the given friend name is not added to the list of friends a
	 * second time.)
	 */
	public boolean addFriend(String friend) {
		for (int i = 0; i < friendsList.size(); i++) {
			if (friendsList.get(i).equals(friend)) {
				return false;
			}
		}
		friendsList.add(friend);
		return true;
	}

	/**
	 * This method removes the named friend from this profile's list of friends. It
	 * returns true if the friend's name was in the list of friends for this profile
	 * (and the name was removed from the list). The method returns false if the
	 * given friend name was not in the list of friends for this profile (in which
	 * case, the given friend name could not be removed.)
	 */
	public boolean removeFriend(String friend) {
		for (int i = 0; i < friendsList.size(); i++) {
			if (friendsList.get(i).equals(friend)) {
				friendsList.remove(i);
				return true;
			}
		}
		return false;
	}

	/**
	 * This method returns an iterator over the list of friends associated with the
	 * profile.
	 */
	public Iterator<String> getFriends() {
		return friendsList.iterator();
	}

	/**
	 * This method returns a string representation of the profile. This string is of
	 * the form: "name (status): list of friends", where name and status are set
	 * accordingly and the list of friends is a comma separated list of the names of
	 * all of the friends in this profile.
	 *
	 * For example, in a profile with name "Alice" whose status is "coding" and who
	 * has friends Don, Chelsea, and Bob, this method would return the string:
	 * "Alice (coding): Don, Chelsea, Bob"
	 */
	public String toString() {
		String friends = "";
		for (int i = 0; i < friendsList.size(); i++) {
			String indexName = friendsList.get(i);
			friends += ',' + indexName;
		}
		profile = profileName + ':' + profileStatus + friends;
		return profile;
	}

}
