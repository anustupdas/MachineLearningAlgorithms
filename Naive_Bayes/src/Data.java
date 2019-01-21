import java.util.ArrayList;
import java.util.Random;

/**
 * <h1>Data Class</h1>
 * The class gets the data and generates training and test data
 */
public class Data {
	
	private ArrayList<String[]> totalData;
	private ArrayList<String[]> trainData;
	private double percentage;
	private ArrayList<String[]> testData;
	
	/**
     * Constructor
     *
     */
	public Data(ArrayList<String[]> data, double percentage) {
		this.totalData = data;
		this.percentage = percentage;
		this.trainData = new ArrayList<>();
		this.testData = new ArrayList<>();
	}
	
	public int getDataSize() {
		return totalData.size();
	}
	
	public ArrayList<String[]> getTrainingData() {
		return this.trainData;
	}
	
	public ArrayList<String[]> getTestData() {
		return this.testData;
	}
	
	/**
     * This function is used to generate the training data (2/3 of Data) and test data
     * from the data
     */
	public void generateTrainingAndTestData() {
		int trainDataSize = (int)(this.totalData.size() * (this.percentage / 100.0));
        
        ArrayList<String[]> dummyData = new ArrayList<>(this.totalData);
        
        // checking with dummy value. change it to random
        int i = 0;
        Random generator = new Random();
        while(i < trainDataSize){
        	int random = generator.nextInt(dummyData.size()-0) + 0;
            String[] line = dummyData.get(random);
        	dummyData.remove(random);
        	this.trainData.add(line);
            i++;
        }

        int j = 0;
        while(j < (this.totalData.size() - trainDataSize)){                        
        	this.testData.add(dummyData.get(j));
            j++;
        }
	}
}
