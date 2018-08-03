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
	
	
	private String name;
	private String status;
	private GImage image;
	private ArrayList<String> friendList;
	
	public FacePamphletProfile(String name) {
		
		// Initiating instance variable with name
		this.name = name;
		
		//Initiating instance variable for status. ("") to begin with
		status = "";
		
		//Initiating instance variable for GImage, set first to null
		image = null;
		
		//Creating a ArrayList for names of friends
		friendList = new ArrayList<String>();
		
	}

	public String getName() {
		return name;
	}

	public GImage getImage() {
		if (image != null) {
			return image;
		}
		else {
			return null;
		}
	}

	public void setImage(GImage image) {
		this.image = image;
	}
	
	public String getStatus() {
		if (!status.isEmpty()) {
			return status;
		}
		else {
			return ("");
		}
	}
	 
	public void setStatus(String status) {
		this.status = status;
	}

	public boolean addFriend(String friend) {
		if (!friendList.contains(friend)) {
			friendList.add(friend);
			return true;
		}
		else {
			return false;
		}
	}


	public boolean removeFriend(String friend) {
		if (friendList.contains(friend)) {
			friendList.remove(friend);
			return true;
		}
		else {
			return false;
		}
	}


	public Iterator<String> getFriends() {
		if (!friendList.isEmpty()) {
			return friendList.iterator();
		}
		return null;
	}
	
	public String toString() {
		String nameList = "";
		if(!friendList.isEmpty()) {
			Iterator<String> it = getFriends();
			while(it.hasNext()) {
				String next = it.next();
				nameList += " " + next;
			}
		}
		return "" + name + " (" + status + ") " + nameList;
	}
	
	
}
