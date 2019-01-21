import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class InnerNode extends TreeNode {
	
	public String attribute; 
	
	// Child nodes of the inner node.
	public List<TreeNode.MapNodes> childs = new ArrayList<MapNodes>(); 
	public double infoGainVal; 
	
	public InnerNode(Data record, TreeNode.MapNodes parentLink, List<Data.TraningInstanceSet> examples, String attribute) {
		super(record, parentLink, examples); 
		this.attribute = attribute; 
	}
	
	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public List<TreeNode.MapNodes> getChilds() {
		return childs;
	}

	public void setChilds(List<TreeNode.MapNodes> childs) 
	{
		this.childs = childs;
	}

	public double getGain() {
		return infoGainVal;
	}

	public void setGain(double gain) {
		this.infoGainVal = gain;
	}
	
	
	public String toString() {
		return convertToXml();
		}
	
	
	// traversing the node object and creating the nodes based on the parent child link and creating respective XML text.
	public String convertToXml() {
		StringBuilder xmlString = new StringBuilder();
		String tag = null;
		
		if (parentMap == null) {
			tag = new String("tree");
		} else {
			tag = new String("node");
		}
		
		StringBuilder classes = new StringBuilder();
		
		
		Map<String,Integer> classCount = new HashMap<String, Integer>();
		for( String grp : trainingData.classifications ) {
			classCount.put( grp, 0 );
		}
		for(Data.TraningInstanceSet ex : trainingIntances) {
			classCount.put(ex.classes, classCount.get(ex.classes) + 1);
		}
		
		//printing the classified catagories with its respective values.
		for(Entry<String, Integer> entry : classCount.entrySet()) {
			if( entry.getValue() > 0 ) {
				classes.append( String.format("%s:%s,", entry.getKey(), entry.getValue().toString()) );
			}
		}
		if(classes.length() > 0) {
			classes.setLength(classes.length()-1); 
		}
		
		String entropy = Double.valueOf(trainingData.getEntropy(trainingIntances)).toString();
		
		// appending the Entropy value to the xml string
		xmlString.append( String.format( "<%s classes=\"%s\" entropy=\"%s\"", tag, classes.toString(), entropy) );
		if( parentMap != null ) {
			
			//appending the name of the ParentNode and its respective value to the xml string.
			xmlString.append( String.format(" %s=\"%s\"", parentMap.attribute, parentMap.value ) );
		}
		xmlString.append( ">\n" );
		
		//creating the xml string for the child node mapped with the parent node.
		for( TreeNode.MapNodes map : childs ) {
			String childString = map.childNode.toString(); // recursively make the subtree.
			String[] lines = childString.split( "\\n" );
			for( String line : lines ) {
				xmlString.append( "\t" );
				xmlString.append( line );
				xmlString.append( "\n" );
			}
		}
		
		xmlString.append(String.format("</%s>", tag));
		System.out.println("inner node");
		return xmlString.toString();
	}
	
	
}
