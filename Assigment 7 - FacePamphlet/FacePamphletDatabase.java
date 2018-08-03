/*
 * File: FacePamphletDatabase.java
 * -------------------------------
 * This class keeps track of the profiles of all users in the
 * FacePamphlet application.  Note that profile names are case
 * sensitive, so that "ALICE" and "alice" are NOT the same name.
 */

import java.util.*;

public class FacePamphletDatabase implements FacePamphletConstants {

	
	
	HashMap<String, FacePamphletProfile> profiles;
	
	public FacePamphletDatabase() {
		profiles = new HashMap<String, FacePamphletProfile>();
	}
	

	public void addProfile(FacePamphletProfile profile) {
		profiles.put(profile.getName(), profile);
	}

	public FacePamphletProfile getProfile(String name) {
		if (profiles.containsKey(name)) {
		return profiles.get(name);
		}
		else {
			return null;
		}
	}
	
	
	public void deleteProfile(String name) {
		if(profiles.get(name).getFriends() != null) {
			Iterator<String> it = profiles.get(name).getFriends();
			while (it.hasNext()) {
				String currentFriend = it.next();
				profiles.get(currentFriend).removeFriend(name);
			}
		}
		profiles.remove(name);
	}


	public boolean containsProfile(String name) {
		return profiles.containsKey(name);
	}
	


}
