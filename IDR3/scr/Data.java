import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Data {
	
	public class TraningInstanceSet {
		public Map<String, String> factors; // Mapping the factors with the respective values from the training examples.
		public String classes; // classes of the training examples.
	}
	
	// Training Data set
	public List<TraningInstanceSet> traningInstanceSet;
	// Factors defining the tree classification. 
	public List<String> factors; 
	// Map of the factors with its respective all possible values in the training data.
	public Map<String, List<String>> factorVals; 
	// classification label
	public List<String> classifications; 
	
	// Load the Data and Property Files and mapping the Data with the attributes defined in the property file.
	// Property file :  cardata.properties
	// Data file : Training_Data/car.data
	public void executeFileLoader(String propertyFile, String datasetFile) {
			Properties propertyVals = new Properties();
			try {
				InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertyFile);
				propertyVals.load(inputStream);
			} catch (IOException e) {
				System.out.println("File not Found.");
				e.printStackTrace();
			}
			
			// Mapping all the possibles values of the factors with the respective factor label.
			String factorAttributes = propertyVals.getProperty("attributes");
			factors = Arrays.asList(factorAttributes.split("\\s*,\\s*"));
			factorVals = new HashMap<String, List<String>>();
			for (String attribute : factors) {
				String values = propertyVals.getProperty(attribute);
				factorVals.put(attribute, Arrays.asList(values.split("\\s*,\\s*")));
			}
			
			String classes = propertyVals.getProperty("classes");
			classifications = Arrays.asList(classes.split("\\s*,\\s*"));
			
			//Reading the data from the data file and mapping them to the respective attributes mentioned in the property file.
			String user_directory = System.getProperty("user.dir");
			String data_file = new String(user_directory + "/Training_Data/" + datasetFile);
			List<String> traningDataInstances = new ArrayList<String>();
			
			try (BufferedReader x = new BufferedReader(new FileReader(data_file))) {
				String currentLine;
				while((currentLine = x.readLine()) != null) {
					if (currentLine != null) {
						traningDataInstances.add(currentLine);
					}
				}
			} catch(IOException e) {
				System.out.println("Error");
			}
			
			traningInstanceSet = new ArrayList<TraningInstanceSet>();
			for(String string : traningDataInstances) {
				TraningInstanceSet trainningInstance = new TraningInstanceSet();
				trainningInstance.factors = new HashMap<String, String>();
				String[] values = string.split(",");
				for(int i = 0; i < factors.size(); i++) {
					trainningInstance.factors.put(factors.get(i), values[i]); // maintain the order of the attributes and the instance values
				}
				trainningInstance.classes = values[factors.size()];
				traningInstanceSet.add(trainningInstance);
			}
		}
	
	
	// Calculating the Entropy. [the measure of impurity]
	public double getEntropy(List<TraningInstanceSet> examples) {
		
		double logVal = Math.log(2);
		double entropy = 0.0;
				
		for (String category : this.classifications) {
			double pi = calculateProbability(examples, category);
			if(pi != 0) {
				entropy -= pi*(Math.log(pi)/logVal);
			}
		}	
		return entropy;
	}
	
	// Calculating the information gain of the factors
	public double getInfoGain(List<TraningInstanceSet> traningData, String attribute) {
		double mainEntropy = getEntropy(traningData); 
		double informtionGain;
		
		List<String> vals = this.factorVals.get(attribute);
		double attributeEntropies = 0.0;
		for(String val : vals) {
			List<TraningInstanceSet> matchingdata = getMatchingData(traningData, attribute, val);
			if(matchingdata.size() > 0) {
			double entropy = getEntropy(matchingdata);
			attributeEntropies += ((double)matchingdata.size() / (double)traningData.size()) * entropy ;
			}
		}
				informtionGain = (double)mainEntropy - (double)attributeEntropies;
				return informtionGain;
	}
	
	// Matching all the training data with the selected attribute and its respective value.
	public List<TraningInstanceSet> getMatchingData(List<TraningInstanceSet> examples, String attribute, String value) {
		List<TraningInstanceSet> match = new ArrayList<TraningInstanceSet>();
		for(TraningInstanceSet example : examples) {
			if(example.factors.get(attribute).equals(value)) {
				match.add(example);
			}
		}
		return match;
	}


	public double calculateProbability(List<TraningInstanceSet> examples, String category) {
		int favOcc = 0;
		for (TraningInstanceSet example : examples) {
			if(example.classes.equals(category)) {
				favOcc++;
			}
		}
		int totalOccs = examples.size();
		return (double)favOcc/(double)totalOccs;
	}
		
}
