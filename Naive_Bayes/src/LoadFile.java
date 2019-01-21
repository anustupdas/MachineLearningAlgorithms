import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * The class loads the file and stores into Arraylist of String[]
 */
public class LoadFile {
	private ArrayList<String[]> data;
	
	/**
     * Constructor for the LoadFile class
     *
     */
	public LoadFile() {
		data = new ArrayList<>();
	}
	
	/**
     * This function is used to load the file passed as the first argument
     * when the program is ran
     *
     * @param data_file the file to be loaded
     */
	public void load(String data_file) {
		try(BufferedReader reader = new BufferedReader(new FileReader(data_file))) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
            	data.add(currentLine.split(","));
            }
            reader.close();
        } catch (IOException io) {
        	io.printStackTrace();
		}
	}

	public ArrayList<String[]> getData() {
		return data;
	}

	public void setData(ArrayList<String[]> data) {
		this.data = data;
	}
}
