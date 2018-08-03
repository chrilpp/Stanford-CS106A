import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import acm.util.ErrorException;


public class NameSurferDataBase implements NameSurferConstants {
	
	
	private HashMap<String, NameSurferEntry> database; 

	public NameSurferDataBase(String filename) {
		
		database = new HashMap<String, NameSurferEntry>();
		
		/* Reads in the whole database into a Hashmap, with the name beeing the key and the element
		 * a object of NameSurferEntry */
		try {
			BufferedReader rd = new BufferedReader(new FileReader(filename));
			while(true) {
				String line = rd.readLine();
				if (line == null) { break; }
				NameSurferEntry entry = new NameSurferEntry(line);
				database.put(parseName(line), entry);
				
			}
			rd.close();
		} catch (IOException e) {
			throw new ErrorException(e);
		}
	}
	
	public String parseName(String entry) {
		int nameEnd = entry.indexOf(" ");
		return entry.substring(0, nameEnd);
	}

	public NameSurferEntry findEntry(String name) {
		if (database.isEmpty()) {
			return null;
		}
		else if(database.containsKey(name)) {
			return database.get(name);
		}
		else {
			return null;
		}
	}
}

