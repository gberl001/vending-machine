package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class DAOManager implements Serializable {
	
	/** private variables */
	private static final long serialVersionUID = 1123581321L;
	
    private DAOManager() { }
    
    /**
    * SingletonHolder is loaded on the first execution of Singleton.getInstance() 
    * or the first access to SingletonHolder.INSTANCE, not before.
    */
    private static class SingletonHolder { 
    	public static final DAOManager INSTANCE = new DAOManager();
    }

    /**
     * returns a singleton instance of DAOManager
     * @return DAOManager instance
     */
    public static DAOManager getInstance() {
    	return SingletonHolder.INSTANCE;
    }
	
    /**
     * Store an object using serialization
     * @param o	the object to store
     * @param p	the path to store the serialized file
     */
	public <T extends Serializable> void freeze(T o, String p) {
		FileOutputStream fileOut = null;
		ObjectOutputStream out = null;
		try {
			fileOut = new FileOutputStream(p);
			out = new ObjectOutputStream(fileOut);
			out.writeObject(o);
		} catch (IOException e) {
			System.err.println("IOException: writing file " + e.getMessage());
			e.printStackTrace();
		} catch(Exception e) {
			System.err.println("Unhandled... " + e.getCause() + " - " + 
					e.getMessage());
		} finally {
			// Close them here so they close even if an exception is caught
			try {
				out.close();
			} catch (Exception ignore) { }
			try {
				fileOut.close();
			} catch (Exception ignore) { }
		}
	}
	
	/**
	 * Retrieve a serialized object from "storage"
	 * @param p	the file path to retrieve the object from
	 * @return	the retrieved object
	 */
	@SuppressWarnings("unchecked")
	public <T extends Serializable> T thaw(String p) {
		T o = null;
		FileInputStream fileIn = null;
		ObjectInputStream in = null;
		
		try {
			fileIn = new FileInputStream(p);
			in = new ObjectInputStream(fileIn);
			o = (T) in.readObject();
		} catch(IOException e) {
			System.err.println("IOException: error reading file " + 
					e.getMessage());
			e.printStackTrace();
		} catch(ClassNotFoundException e) {
			System.err.println("ClassNotFoundException: " + e.getMessage());
		} catch(ClassCastException e) {
			System.err.println("ClassCastException: " + e.getMessage());
		} finally {
			// Close them here so they close even if an exception is caught
			try {
				in.close();
			} catch (Exception ignore) { }
			try {
				fileIn.close();
			} catch (Exception ignore) { }
		}
		return o;
	}

	
	/**
	 * Logs data in the global transaction log (no path needed to pass)
	 * @param data	an array of data to log
	 * @param path	the path of the csv file to store the array of data
	 * @throws IOException if the file cannot be found
	 */
	public void appendToCSV(String[] data, String path) throws IOException {
		// Check if the file exists, if not, create it
		File f = new File(path);
		if (!f.exists()) {
			System.out.println("No File existed, created");
			f.createNewFile();
		}
		// Create an append FileWriter
		BufferedWriter writer = new BufferedWriter(new FileWriter(f, true));
		
		// Build the string to write
		StringBuilder sb = new StringBuilder();
		for (int i = 0 ; i < data.length; i++) {
			sb.append("\"" + data[i] + "\"");
			if (i != data.length - 1) {
				sb.append(",");
			} else {
				sb.append("\n");
			}
		}
		
		// Write the data to the file
		writer.write(sb.toString());

		// Clean up
		writer.close();
	}
	
	/**
	 * Get a list of all data in the transaction log
	 * @param path the path of the csv file to read.
	 * @return transaction log data
	 * @throws IOException if the file cannot be found
	 */
	public List<String[]> readCSV(String path) throws IOException {
		BufferedReader reader;
		// Create the BufferedReader
		try {
			reader = new BufferedReader(new FileReader(path));	
		} catch (FileNotFoundException e) {
			System.err.println("Transaction Log file does not exist");
			return null;
		}
		List<String[]> retVal = new ArrayList<String[]>();
		
		// Loop through and read file contents
		String line = null;
		int i = 0;
		while ((line = reader.readLine()) != null) {
			if (i == 0) {
				i++;
				continue;
			}
			retVal.add(line.split(","));
		}
		
		// Clean up
		reader.close();
		
		return retVal;
	}

}
