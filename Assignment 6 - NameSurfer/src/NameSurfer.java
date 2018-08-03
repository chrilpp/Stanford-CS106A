/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.ErrorException;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*;

public class NameSurfer extends Program implements NameSurferConstants {

	public void init() {
		//Setup Graph and window
		graph = new NameSurferGraph();
		add(graph);
		setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
		
		//Initiating components
		name = new JLabel("Name");
		textField = new JTextField(20);
		graphButton = new JButton("Graph");
		clearButton = new JButton("Clear");
		
		//Adding action listeners
		graphButton.addActionListener(this);
		clearButton.addActionListener(this);
		textField.addActionListener(this);
		
		//Adding buttons to layout
		add(name, SOUTH);
		add(textField, SOUTH);
		add(graphButton, SOUTH);
		add(clearButton, SOUTH);
		
		//Setup database of names
		database = new NameSurferDataBase(NAMES_DATA_FILE);
		
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Graph") || e.getSource() == textField) {
			//Changing the text so its not case-sensitive against and works with names-data.txt
			String writtenText = textField.getText();
			writtenText = writtenText.toLowerCase();
			String correctedText = writtenText.substring(0, 1).toUpperCase() + writtenText.substring(1);
			
			//Finding the object in the database if the name written exists.
			NameSurferEntry currentEntry = database.findEntry(correctedText);
			if (currentEntry != null) {
				println(currentEntry.toString());;
				graph.addEntry(currentEntry);
			}
		}
		if (e.getActionCommand().equals("Clear")) {
			graph.clear();
			textField.setText("");
		}
	}
	
		//Instance variables - Classes
		private NameSurferDataBase database;
		private NameSurferGraph graph;
	
		//Instance variables
		private JLabel name;
		private JTextField textField;
		private JButton graphButton, clearButton;
	  
	  
	  
	
}
