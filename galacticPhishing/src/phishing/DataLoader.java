package phishing;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Author: Rayne Aurit 
 * Date: 2025-03-01
 * 
 * Methods to load up data from .csv files to lists of names, domains, and body text.
 */

public class DataLoader {	
	/**Loads lines from files and returns the lines as an array list
	 * 
	 * @param filePath
	 * @return
	 */
	public static ArrayList<String> loadDataFromFile(String fileName) {
		
		Scanner s = null;
		try {
			File file = new File(fileName);
			String absolutePath = file.getAbsolutePath().replace("\\", "\\\\").replace("\\\\galacticPhishing","\\\\galacticPhishing\\\\data");
			s = new Scanner(new File(absolutePath));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}

		ArrayList<String> lines = new ArrayList<>();
		
		s.nextLine();
		
		while (s.hasNextLine()) {
			lines.add(s.nextLine());
		}
		
		s.close();
		return lines;
	}
}