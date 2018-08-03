/*
 * File: NameSurferEntry.java
 * --------------------------
 * This class represents a single entry in the database.  Each
 * NameSurferEntry contains a name and a list giving the popularity
 * of that name for each decade stretching back to 1900.
 */

import acm.util.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class NameSurferEntry implements NameSurferConstants {

/* Constructor: NameSurferEntry(line) */

	public NameSurferEntry(String line) {
		ranks = new HashMap<Integer, Integer>();
		this.line = line;
		extractRanks(line);
	}
	
	public void extractRanks(String line) {
		//Put all the ranks for each year in a Hashmap
		
		//Removing the name from the line
		int nameEnd = line.indexOf(" ");
		String lineOnlyNumbers = line.substring(nameEnd + 1);
		
		//Creating a tokenizer to pull out the different ranks
		StringTokenizer tokenizer = new StringTokenizer(lineOnlyNumbers, " ");
		int year = START_DECADE;
		while(tokenizer.hasMoreTokens()) {
				String currentNumber = tokenizer.nextToken();
				ranks.put(year, Integer.parseInt(currentNumber));
				year += YEAR_INCREASE;
		}

	}

	public String getName() {
		int nameEnd = line.indexOf(" ");
		return line.substring(0, nameEnd);
	}

/* Method: getRank(decade) */

	public int getRank(int decade) {
		return ranks.get(decade);
	}

	public String toString() {
		return line;
	}
	
	//Instance Variables
	private String line; //whole line including name
	private HashMap<Integer, Integer> ranks; //Create map where the key is the year, and the element is the ranking of that year.
	
}

