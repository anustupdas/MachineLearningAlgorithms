import java.util.ArrayList;
import java.util.Iterator;

/**
 * <h1>Matrix Class</h1>
 * The class makes and plots confusion matrix, also generates the error rate
 * of the algorithm
 */
public class Matrix {
	private ArrayList<String[]> m_data;
    private ArrayList<String> m_classes;
    private int[][] m_matrix;
    
    /**
     * Constructor for the Matrix class
     *
     */
    public Matrix(ArrayList<String[]> m_data) {
    	this.m_data = m_data;
    	this.m_classes = getAllDistinctClasses();
    	this.m_matrix = new int[m_classes.size()][m_classes.size()];
    }
    
    /**
     * This function is used to get all the distinct classes from the data ArrayList
     *
     * @return result all the distinct classes
     */
    private ArrayList<String> getAllDistinctClasses() {
    	ArrayList<String> result = new ArrayList<>();
        Iterator<String[]> iter = this.m_data.iterator();
        
        while(iter.hasNext()){
            String[] classIterator = iter.next();
            int i;
            for(i = 0; i < classIterator.length; i++){
                if(!result.contains(classIterator[i]))
                    result.add(classIterator[i]);
            }
        }
        return result;
    }
    
    /**
     * This function is used to count and generate the confusion matrix
     * true positives (TP): These are cases in which we predicted the same class as the actual class.
	 * true negatives (TN): These are cases in which we predicted the same class as the actual class.
     * false positives (FP): These are cases in which the predicted class is different as the actual class.
     * false negatives (FN): These are cases in which the predicted class is different as the actual class.
     */
    public void confusionMatrixCreation(){
        
        Iterator<String[]> iterator = m_data.iterator();
        
        while(iterator.hasNext()){
            String[] classIterator = iterator.next();
            m_matrix[m_classes.indexOf(classIterator[0])][m_classes.indexOf(classIterator[1])]++;
        }
    }
    
    /**
     * This function is used to print the confusion matrix in the output along with the error rate
	 * Misclassification Rate: Overall, how often is it wrong?: 1 - Accuracy
	 * Accuracy: Overall, how often is the classifier correct?: (TruePossitive+TrueNegetive)/total
     */
    public void confusionMatrixPrint() {
    	System.out.println("Confusion Matrix");
    	Iterator<String> iterator = this.m_classes.iterator();
    	System.out.print("      ");
    	while(iterator.hasNext()) {
    		String predictedClass = iterator.next();
    		System.out.format("%7s", predictedClass);
    	}
       	System.out.println();
    	int i, j;
    	int classifiedClass = 0;
    	for (i = 0; i < this.m_matrix.length; i++) {
            System.out.format("%-5s ", this.m_classes.get(i));
            for (j = 0; j < this.m_matrix[i].length; j++) {
            	if (i == j) {
    				classifiedClass += m_matrix[i][j];
    			}
            	System.out.format("%6d ", m_matrix[i][j]/10); // divide the by the number of times ran
            }
            System.out.println();
        }
        System.out.println();
        double error_rate = ( 100.0 - ( (double)classifiedClass / this.m_data.size())*100.0);
        System.out.println(String.format("Error Rate: %.2f%%", error_rate));
    }
}
