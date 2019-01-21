import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class LeafNode extends TreeNode {
	
	public String group; 
	
	public LeafNode(Data data, TreeNode.MapNodes parentMap, List<Data.TraningInstanceSet> trainingIntances, String classification) {
		super(data, parentMap, trainingIntances); 
		this.group = classification; 
	}
	
	public String getCategory() {
		return group;
	}

	public void setCategory(String category) {
		this.group = category;
	}
		
	public String toString()
	{
		return convertToXml();
	}
	
	// traversing the node object and creating the leaf nodes based on the parent child link and creating respective XML text.	
	public String convertToXml() {
		
		StringBuilder xmlString = new StringBuilder();
		Integer count = 0;
		String tag = null;
		
		if (parentMap == null) {
			tag = new String("tree");
		} else {
			tag = new String("node");
		}
		
		StringBuilder classes = new StringBuilder();
		
		Map<String,Integer> classCount = new HashMap<String, Integer>();
		
		for( String cat : trainingData.classifications ) {
			classCount.put( cat, 0 );
		}
		for( Data.TraningInstanceSet ex : trainingIntances ) {
			classCount.put( ex.classes, classCount.get( ex.classes ) + 1);
		}
		
		//printing the classified catagories with its respective values.
		for(Entry<String, Integer> entry : classCount.entrySet()) {
			if(entry.getValue() > 0) {
				classes.append(String.format("%s:%s,", entry.getKey(), entry.getValue().toString()));
			}
		}
		if(classes.length() > 0) {
			classes.setLength(classes.length()-1);
		}
		
		// appending the Entropy value to the xml string
		String entropy = Double.valueOf(trainingData.getEntropy(trainingIntances)).toString();
		xmlString.append( String.format( "<%s classes=\"%s\" entropy=\"%s\"", tag, classes.toString(), entropy ) );
		if( parentMap != null ) {
			
			//appending the name of the ParentNode and its respective value to the xml string.
			xmlString.append( String.format(" %s=\"%s\"", parentMap.attribute, parentMap.value ) );
		}
		xmlString.append(String.format(">%s</%s>", group, tag));
		System.out.println("leaf");
		count++;
		return xmlString.toString();
	}
	
	
}