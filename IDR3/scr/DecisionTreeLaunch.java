import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;


public class DecisionTreeLaunch {
	
	public static void main(String[] args) {
		
		Data dataset = new Data();
		
		// loading the data file and properties file into the system.
		dataset.executeFileLoader("cardata.properties", "car.data");
		
		// building the Decision Tree based on the data set and storing the information to the nodes in a tree format.
		TreeNode node = AlgorithmDecisionTree.buildTree(dataset, dataset.traningInstanceSet, dataset.factors, null);
		
		// converting the Node object into XML string.
		writetoXML(node.toString());
	}
	
	
	// Generating the XML file with the Dataset converted in to a decision tree.
	public static void writetoXML(String nodeXML) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("decisionTree_carData.xml"));
			out.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
			out.write("\n");
			out.write(nodeXML);
			out.close();
			System.out.println("File Generated");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
