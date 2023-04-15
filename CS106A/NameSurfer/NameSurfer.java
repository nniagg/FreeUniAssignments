
/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.program.*;
import java.awt.event.*;
import java.util.HashMap;

import javax.swing.*;

public class NameSurfer extends Program implements NameSurferConstants {
	private NameSurferGraph graph;
	private NameSurferEntry entry;
	private NameSurferDataBase base;
	private JTextField textField = new JTextField(20);

	/* Method: init() */
	/**
	 * This method has the responsibility for reading in the data base and
	 * initializing the interactors at the bottom of the window.
	 */
	public void init() {
		graph = new NameSurferGraph();
		add(graph);
		JLabel name = new JLabel("Name");
		add(name, SOUTH);
		add(textField, SOUTH);
		textField.addActionListener(this);
		JButton graphButton = new JButton("Graph");
		add(graphButton, SOUTH);
		graphButton.addActionListener(this);
		JButton clearButton = new JButton("Clear");
		add(clearButton, SOUTH);
		clearButton.addActionListener(this);
		addActionListeners();
		base = new NameSurferDataBase(NAMES_DATA_FILE);
	}

	/* Method: actionPerformed(e) */
	/**
	 * This class is responsible for detecting when the buttons are clicked, so you
	 * will have to define a method to respond to button actions.
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Graph")) {
			String st = Name(textField.getText());
			entry = base.findEntry(st);
			graph.addEntry(entry);
		}
		if (e.getActionCommand().equals("Clear")) {
			graph.clear();
		}
	}

	// private String Name is to change text entered in textField in the way it is
	// in database

	private String Name(String str) {
		char c = str.charAt(0);
		if (c >= 'a' && c <= 'z') {
			str = (char) (c - 32) + str.substring(1);
		}
		for (int i = 1; i < str.length(); i++) {
			char n = str.charAt(i);
			if (n >= 'A' && n <= 'Z') {
				str = str.substring(0, i) + (char) (n + 32) + str.substring(i + 1);
			}
		}
		return str;
	}
}