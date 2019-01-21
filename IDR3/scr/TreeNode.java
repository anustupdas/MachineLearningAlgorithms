import java.util.List;

public class TreeNode {
	
	public static class MapNodes {
		public TreeNode parentNode; 
		public TreeNode childNode; 
		
		public String attribute; 
		public String value; 
		
		// Mapping the Parent nodes with the Child nodes.
		public MapNodes(TreeNode parent, TreeNode child, String attribute, String value) {
			this.parentNode = parent;
			this.childNode = child;
			this.attribute = attribute;
			this.value = value;
		}
	}
	
	
	// Parent node information.
	public MapNodes parentMap; 
	public List<Data.TraningInstanceSet> trainingIntances;
	public Data trainingData; 
	
	
	// Node builder along with parent node info.
	public TreeNode( Data record, MapNodes parentLink, List<Data.TraningInstanceSet> trainingInstances ) {
		this.trainingData = record;
		this.parentMap = parentLink;
		this.trainingIntances = trainingInstances;
	}

	//getter
	public MapNodes getParentLink() {
		return parentMap;
	}

	//setter
	public void setParentLink(MapNodes parentLink) {
		this.parentMap = parentLink;
	}

	//getter
	public Data getData()
	{
		return trainingData;
	}
	//setter
	public void setData(Data data) 
	{
		this.trainingData = data;
	}
	
	
	//getter
	public List<Data.TraningInstanceSet> getExamples() 
	{
		return trainingIntances;
	}
	//setter
	public void setExamples(List<Data.TraningInstanceSet> examples) 
	{
		this.trainingIntances = examples;
	}

}
