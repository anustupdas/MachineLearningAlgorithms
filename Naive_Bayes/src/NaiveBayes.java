import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * <h1>Naive Bayes classifier</h1>
 * The class implements the Machine Learning Naive Bayes classifier
 */
public class NaiveBayes {
	private ArrayList<String[]> total_data;         // all data
    private ArrayList<String[]> total_trainingData; // training data
    private ArrayList<String[]> total_testData;     // testing data
    
    private ArrayList<String[]> result;       // result
    
    public ArrayList<String[]> getResult() {
        return this.result;
    }
    
    /**
     * Constructor for the NaiveBayes class
     *
     */
    public NaiveBayes(ArrayList<String[]> data, ArrayList<String[]> trainingData, ArrayList<String[]> testData) {
        this.total_data = data;
    	this.total_trainingData = trainingData;
        this.total_testData = testData;
        this.result = this.createClassification();
    }
    
    /**
     * This function is used to get the distinct class values in that position of the training data
     *
     * @param value the class value
     * @param position the class position in the data
     * @return result number of distinct class
     */
    private double countDistinctClass(String value, int position) {
    	double result = 0.0;
    	Iterator<String[]> iter = this.total_data.iterator();
        while(iter.hasNext()){
            String[] classIterator = iter.next();
            if(classIterator[position].equals(value))
                result++;
        }
    	return result;
    }

    /**
     * This function is used to count the number attribute values belonging to that position
     * given the class value at the class position
     *
     * @param attributeValue the attribute value
     * @param attribPosition attribute value position inside the String
     * @param classValue class value
     * @param classPosition class value position inside the String
     * @return result number of attribute value belonging to the class
     */
    public double countAttribteClass(String attributeValue, int attribPosition, String classValue, int classPosition){
        
        double result = 0.0;    
        Iterator<String[]> iter = this.total_data.iterator();
        
        while(iter.hasNext()){
            String[] classIterator = iter.next();
            
            if(classIterator[attribPosition].equals(attributeValue) && classIterator[classPosition].equals(classValue))
                result++;
        }
        
        return result;
    }
    
    /**
     * This function is used to classify the test data according to the training data
     *
     * @return result Arraylist of String[] that holds the correctly classified class value
     */
    private ArrayList<String[]> createClassification(){
        ArrayList<String[]> result = new ArrayList<>();
        
        int classPosition = this.total_trainingData.get(0).length-1;
        HashMap<String,Double> classes = new HashMap<>();
        double trainingDataSize = (double)this.total_trainingData.size();
        
        int i;
        for(i = 0; i < this.total_trainingData.size();i++){
            String classValue = this.total_trainingData.get(i)[classPosition];
            if(!classes.containsKey(classValue)){
                classes.put(classValue, this.countDistinctClass(classValue, classPosition));
            }
        }
        
        //iterate through test examples and classify each one
        Iterator iter_data = total_testData.iterator();
        while(iter_data.hasNext()){
            String[] line = (String[])iter_data.next();
            HashMap<String,Double> attributeProbability = new HashMap<>();
            
            //iterate through classes
            Iterator iteratorClasses = classes.entrySet().iterator();
            while(iteratorClasses.hasNext()){
                Map.Entry pairs = (Map.Entry)iteratorClasses.next();
                double probability = (double)pairs.getValue()/trainingDataSize;

                //iterate through attributes
                for(i = 0; i < line.length-1; i++){
                    String attvalue = line[i];
                    double classifiedClass = this.countAttribteClass(attvalue, i, (String)pairs.getKey(), classPosition);
                    probability = probability * classifiedClass/(double)pairs.getValue();
                }
                attributeProbability.put((String)pairs.getKey(), probability);
            }
            
            //iterate through probabilities and select the classification
            Iterator iteratorProbability = attributeProbability.entrySet().iterator();
            double maxprob = 0;
            String maxclass = "";
            while(iteratorProbability.hasNext()){
                Map.Entry classpairs = (Map.Entry)iteratorProbability.next();
                if((double)classpairs.getValue() >= maxprob){
                    maxprob = (double)classpairs.getValue();
                    maxclass = (String)classpairs.getKey();
                }
            }
            String[] resultString = new String[2];
            resultString[0] = line[classPosition];
            resultString[1] = maxclass;
            result.add(resultString);
        }
        return result;
    }
    
}
