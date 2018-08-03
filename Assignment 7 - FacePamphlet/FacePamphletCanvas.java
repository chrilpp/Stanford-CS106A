/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */


import acm.graphics.*;
import java.awt.*;
import java.util.*;

public class FacePamphletCanvas extends GCanvas 
					implements FacePamphletConstants {
	
	
	//Instance Variables
	private GLabel name;
	private GLabel status;
	private GLabel friends;
	private GLabel message;
	
	private GRect noImageRect;
	private GImage image;
	private GLabel noImageLabel;
	private FacePamphletProfile profile;
	
	public FacePamphletCanvas() {
		// You fill this in
	}

	
	public void showMessage(String msg) {
		if(message == null) {
			message = new GLabel(msg);
			message.setFont(MESSAGE_FONT);
			add(message, (getWidth() - message.getWidth())/2 , getHeight() - BOTTOM_MESSAGE_MARGIN);
		}
		else {
			message.setLabel(msg);
			add(message, (getWidth() - message.getWidth())/2 , getHeight() - BOTTOM_MESSAGE_MARGIN);
		}
	}
	
	public void displayProfile(FacePamphletProfile profile) {
		//display name, image, status, list of friends
		removeAll();
		displayName(profile);
		displayImage(profile);
		displayStatus(profile);
		displayFriendList(profile);
		
	}
	
	public void displayName(FacePamphletProfile profile) {
		if(profile != null) {
			name = new GLabel(profile.getName());
			name.setFont(PROFILE_NAME_FONT);
			name.setColor(Color.BLUE);
			add(name, LEFT_MARGIN, name.getHeight() + TOP_MARGIN);
		}
	}
	
	public void displayImage(FacePamphletProfile profile) {
		
		if (profile.getImage() == null) {
			noImageRect = new GRect(IMAGE_WIDTH, IMAGE_HEIGHT);
			noImageRect.setFilled(true);
			noImageRect.setFillColor(Color.WHITE);
			noImageLabel = new GLabel("No Image");
			noImageLabel.setFont(PROFILE_IMAGE_FONT);
			add(noImageRect, LEFT_MARGIN, name.getHeight() + TOP_MARGIN + IMAGE_MARGIN);
			add(noImageLabel, LEFT_MARGIN + (IMAGE_WIDTH /2) - (noImageLabel.getWidth())/ 2, (name.getHeight() + TOP_MARGIN + IMAGE_MARGIN) + (IMAGE_HEIGHT/2));
		}
		else {
			image = profile.getImage();
			image.setSize(IMAGE_WIDTH, IMAGE_HEIGHT);
			add(image, LEFT_MARGIN, name.getHeight() + TOP_MARGIN + IMAGE_MARGIN);
		}
	}
	
	public void displayStatus(FacePamphletProfile profile) {
		if (profile.getStatus().isEmpty()) {
			status = new GLabel("No current status");
			status.setFont(PROFILE_STATUS_FONT);
			add(status, LEFT_MARGIN, (name.getHeight() + TOP_MARGIN + IMAGE_MARGIN) + (IMAGE_HEIGHT) + STATUS_MARGIN);
		}
		else {
			status = new GLabel(profile.getName() + " is " + profile.getStatus());
			status.setFont(PROFILE_STATUS_FONT);
			add(status, LEFT_MARGIN, (name.getHeight() + TOP_MARGIN + IMAGE_MARGIN) + (IMAGE_HEIGHT) + STATUS_MARGIN);
		}
		
	}
	
	public void displayFriendList(FacePamphletProfile profile) {
		GLabel friendListLabel = new GLabel("FRIENDS",getWidth()/2,name.getHeight() + TOP_MARGIN + IMAGE_MARGIN);
		friendListLabel.setFont(PROFILE_FRIEND_LABEL_FONT);
		add(friendListLabel, getWidth()/2,name.getHeight() + TOP_MARGIN + IMAGE_MARGIN);
		
		if (profile.getFriends() != null) {
			Iterator<String> it = profile.getFriends();
			int counter = 0;

			while (it.hasNext()) {
				GLabel friend = new GLabel(it.next());
				counter += friend.getHeight() + FRIEND_LIST_MARGIN;
				friend.setFont(PROFILE_FRIEND_LABEL_FONT);
				add(friend, getWidth()/2,name.getHeight() + TOP_MARGIN + IMAGE_MARGIN + counter);
				
			}
		}
	}
	
	public void clearCanvas() {
		removeAll();
	}
	

	
}
