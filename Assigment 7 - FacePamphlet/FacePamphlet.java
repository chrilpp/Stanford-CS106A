/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import java.awt.event.*;
import java.util.Iterator;

import javax.swing.*;

public class FacePamphlet extends Program 
					implements FacePamphletConstants {

	//Instance Variables WEST side
	private JTextField changeStatusTextField;
	private JButton changeStatusButton;
	private JTextField changePictureTextField;
	private JButton changePictureButton;
	private JTextField addFriendTextField;
	private JButton addFriendButton;
	private JLabel emptyLabel;
	private JLabel nameLabel;
	
	//Instance Variables NORTH side
	private JTextField nameTextField;
	private JButton addButton;
	private JButton deleteButton;
	private JButton lookUpButton;
	
	//Database
	private FacePamphletDatabase database;
	
	//Current profile
	private FacePamphletProfile currentProfile;
	
	//Canvas
	private FacePamphletCanvas canvas;
	
	public void init() {
		
		setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
		canvas = new FacePamphletCanvas();
		add(canvas);
		createAndAddButtons();
		database = new FacePamphletDatabase();
		
    }
	
	private void createAndAddButtons() {
		
		//Components to the west
		changeStatusTextField = new JTextField(TEXT_FIELD_SIZE);
		changeStatusTextField.setActionCommand("changeStatusTextField");
		changeStatusTextField.addActionListener(this);
		changeStatusButton = new JButton("Change Status");
		changeStatusButton.addActionListener(this);
		
		changePictureTextField = new JTextField(TEXT_FIELD_SIZE);
		changePictureTextField.setActionCommand("changePictureTextField");
		changePictureTextField.addActionListener(this);
		changePictureButton = new JButton("Change Picture");
		changePictureButton.addActionListener(this);
		
		addFriendTextField = new JTextField(TEXT_FIELD_SIZE);
		addFriendTextField.setActionCommand("addFriendTextField");
		addFriendTextField.addActionListener(this);
		addFriendButton = new JButton("Add Friend");
		addFriendButton.addActionListener(this);
		
		emptyLabel = new JLabel(EMPTY_LABEL_TEXT);
	

		//Components to the north
		nameTextField = new JTextField(TEXT_FIELD_SIZE);
		nameTextField.addActionListener(this);
		addButton = new JButton("Add");
		addButton.addActionListener(this);
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(this);
		lookUpButton = new JButton("Lookup");
		lookUpButton.addActionListener(this);
		nameLabel = new JLabel("Name");
		
		//Adding west components
		add(changeStatusTextField, WEST);
		add(changeStatusButton, WEST);
		add (emptyLabel, WEST);
		add(changePictureTextField, WEST);
		add(changePictureButton, WEST);
		add (emptyLabel, WEST);
		add(addFriendTextField, WEST);
		add(addFriendButton, WEST);
		add (emptyLabel, WEST);
		
		//Adding north components
		add(nameLabel, NORTH);
		add(nameTextField, NORTH);
		add(addButton, NORTH);
		add(deleteButton, NORTH);
		add(lookUpButton, NORTH);
		
	}
    
    public void actionPerformed(ActionEvent e) { 	
    	
    		if (e.getActionCommand().equals("Add")) {
			String message = nameTextField.getText();
			if (!message.equals("")) {
				if (database.containsProfile(message)) {
					canvas.showMessage("Profile " + message + " already exists.");
				}
				else {
					database.addProfile(new FacePamphletProfile(message));
					currentProfile = database.getProfile(message);
					updateCanvas("New profile: " + database.getProfile(message).getName());
					
				}
			}
			nameTextField.setText("");
		}
		
		if (e.getActionCommand().equals("Delete")) {
			String message = nameTextField.getText();
			if (database.containsProfile(message)) {
				database.deleteProfile(message);
				currentProfile = null;
				canvas.clearCanvas();
				canvas.showMessage("Profile of " + message + " was deleted.");
			}
			else {
				canvas.showMessage("Profile with name " + message + " does not exist.");
			}
			nameTextField.setText("");

		}
		
		if (e.getActionCommand().equals("Lookup")) {
			String message = nameTextField.getText();
			print("Lookup: ");
			if (database.containsProfile(message)) {
				currentProfile = database.getProfile(message);
				updateCanvas("Found profile: " + message);
			}
			else {
				updateCanvas("Profile with name " + message + " does not exist.");
			}
			nameTextField.setText("");
			
		}
		
		if (e.getActionCommand().equals("Change Status") || e.getActionCommand().equals("changeStatusTextField")) {
			String message = changeStatusTextField.getText();
			if (!message.equals("")) {
				if (currentProfile != null) {
					currentProfile.setStatus(message);
					updateCanvas("Status updated");
				}
				else {
					canvas.showMessage("Theres is current profile to set status to");
				}
			}
			changeStatusTextField.setText("");
			
		}
		
		if (e.getActionCommand().equals("Change Picture") || e.getActionCommand().equals("changePictureTextField")) {
			String message = changePictureTextField.getText();
			if (!message.isEmpty()) {
				if (currentProfile != null) {
					GImage image = null;
					try {
						image = new GImage(message);
					}
					catch(ErrorException ex) {
						canvas.showMessage("Cant find picture with name: " + message);
					}
						if (image != null) {
							currentProfile.setImage(image);
							updateCanvas("You changed picture");
						}
				}
				else {
					canvas.showMessage("Please enter vaild picturename");
				}
			}
			changePictureTextField.setText("");
		}
		
		if (e.getActionCommand().equals("Add Friend") || e.getActionCommand().equals("addFriendTextField")) {
			String message = addFriendTextField.getText();
			
			if (!message.isEmpty()) {
				if(currentProfile != null) {
					if(database.containsProfile(message) && message != currentProfile.getName()) {
						if(checkFriendList(message)) {
							canvas.showMessage("You are already friends with " + message);
						}
						else {
							currentProfile.addFriend(message);
							database.getProfile(message).addFriend(currentProfile.getName());	
							updateCanvas(message + " added as a friend");
						}	
					}
					else {
						canvas.showMessage("There is no profile with the name " + message + " to add.");
					}
				}
				else {
					canvas.showMessage("There is no current profile to add friends to. Please select a profile");
				}
			}
		}
		addFriendTextField.setText("");
	}
    
    private boolean checkFriendList(String message) {
	    if (database.getProfile(currentProfile.getName()).getFriends() == null) {
	    	return false;
	    }
	    Iterator<String> it = currentProfile.getFriends();
    		while (it.hasNext()) {
    			String next = it.next();
    			if (next.toLowerCase() == message.toLowerCase()) { //FIXA DETTA
    				return true;
    			}
    		}
    		return false;
    }
    
    private void updateCanvas(String message){
    		if(currentProfile != null) {
    		canvas.displayProfile(currentProfile);
    		canvas.showMessage(message);
    		}
    }

}
