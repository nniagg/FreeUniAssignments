
/*
 * File: NameSurferDataBase.java
 * -----------------------------
 * This class keeps track of the complete database of names.
 * The constructor reads in the database from a file, and
 * the only public method makes it possible to look up a
 * name and get back the corresponding NameSurferEntry.
 * Names are matched independent of case, so that "Eric"
 * and "ERIC" are the same names.
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import acm.util.ErrorException;

public class NameSurferDataBase implements NameSurferConstants {
	private HashMap<String, String> nameRankings = new HashMap<String, String>();

	/* Constructor: NameSurferDataBase(filename) */
	/**
	 * Creates a new NameSurferDataBase and initializes it using the data in the
	 * specified file. The constructor throws an error exception if the requested
	 * file does not exist or if an error occurs as the file is being read.
	 */
	public NameSurferDataBase(String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}
				StringTokenizer st = new StringTokenizer(line, " ");
				// key - name, values - line
				if (st.hasMoreTokens()) {
					nameRankings.put(st.nextToken(), line);
				}
			}
			// br.close(); doesn't work properly if br closed
		} catch (Exception exc) {
			throw new ErrorException(exc);
		}
	}

	/* Method: findEntry(name) */
	/**
	 * Returns the NameSurferEntry associated with this name, if one exists. If the
	 * name does not appear in the database, this method returns null.
	 */
	public NameSurferEntry findEntry(String name) {
		if (nameRankings.containsKey(name) == true) {
			return new NameSurferEntry(nameRankings.get(name));
		} else {
			return null;
		}
	}
}
