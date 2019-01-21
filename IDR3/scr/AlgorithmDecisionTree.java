import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlgorithmDecisionTree {

	// Build the Decision tree starting from the root node.
	public static TreeNode buildTree(Data data, List<Data.TraningInstanceSet> intances, List<String> factors, TreeNode.MapNodes parentMap) {
		String firstClass = null;
		Boolean sameClass = true;
		
		// weather all the intances belong to the same catagory or not.
		for(Data.TraningInstanceSet intance : intances) {
			if(firstClass == null)
			{
				firstClass = intance.classes; 
			} 
			else if( !intance.classes.equals(firstClass))
			{
				sameClass = false; 
				break;
			}
		}

		//  If all instances in training data belongs to one class, then create node that belongs to that class and halt.	
		// 	create the leaf node.
		if(sameClass) {
			return new LeafNode(data, parentMap, intances, firstClass);
		}
		
		// Calculating the Information gain of all the factors
		// Selecting the attribute with maximum information gain and making it the deciding node.
		InnerNode node = getNodeWithMaxGain(intances, data, factors, parentMap);
		
		List<String> attributesSet = new ArrayList<String>();
		attributesSet.addAll(factors);
		
		// Deleting the attribute with maxInfo Gain So that the next level of the tree can be build with other remaining attributes. 
		attributesSet.remove(node.attribute); 
		
		// create branches for each value of the attribute in the node and sort all the training examples corresponding to the respective attribute values.
		List<String> values = data.factorVals.get(node.attribute);
		for(String value : values) {
			TreeNode.MapNodes link = new TreeNode.MapNodes(node, null, node.attribute, value);
			List<Data.TraningInstanceSet> examplesSubset = data.getMatchingData(intances, node.attribute, value);
			// Call the decision tree algo recursively to create subtrees and branches below the selected nodes.
			link.childNode = AlgorithmDecisionTree.buildTree(data, examplesSubset, attributesSet, link);
			
			node.childs.add(link);
		}
		
		return node;
	}
	
	// Get the attribute[Node] with maximum Information Gain.
	public static InnerNode getNodeWithMaxGain( List<Data.TraningInstanceSet> trainingInstances, Data data, List<String> factors, TreeNode.MapNodes parentMap ) {
		Map<String, Double> informationGains = new HashMap< String, Double>();
		for( String attribute : factors ) {
			informationGains.put( attribute, data.getInfoGain(trainingInstances, attribute) );
		}
		
		Double maximumInfoGain = -1.0;
		String attrWithMaxInfoGain = null;
		
		for( Map.Entry<String,Double> gain : informationGains.entrySet() ) {
			if(gain.getValue() > maximumInfoGain) {
				maximumInfoGain = gain.getValue();
				attrWithMaxInfoGain = gain.getKey();
			}
		}
		
		InnerNode node = new InnerNode( data, parentMap, trainingInstances, attrWithMaxInfoGain);
		node.infoGainVal = maximumInfoGain;
		node.trainingIntances = trainingInstances;
		
		return node;
	}
}
