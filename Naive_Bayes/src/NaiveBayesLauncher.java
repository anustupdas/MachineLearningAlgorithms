import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class NaiveBayesLauncher {
	
	public static void main(String[] args) {
		
		//reading file from command line
		if (args.length == 1) {
			String dataFile = args[0];
			LoadFile file = new LoadFile();
			//load the data set from the file
			file.load(dataFile);
			ArrayList<String[]> totalData = file.getData(); // getting all the data from the file
			ArrayList<String[]> total = new ArrayList<>();
			System.out.println("Determining mean error rate for over 100 different random samples.");
			for(int i = 0; i < 10; i++){
				// ran the classifier for total outcome X 10 times > 100 samples
				// considered 2/3 of the data as the training data
				Data data = new Data(totalData, 66.667);
				data.generateTrainingAndTestData();
				ArrayList<String[]> trainingData = data.getTrainingData(); // get the training data
				ArrayList<String[]> testData = data.getTestData(); // get the test data
				
				// Execution of the Naive Bayes Algo with training data and test data
				NaiveBayes naive = new NaiveBayes(totalData, trainingData, testData);
				ArrayList<String[]> result = naive.getResult();
				total.addAll(result);
			}
			
			// Confusion matrix creation and error rate calculation.
			Matrix confusionMatrix = new Matrix(total);
			confusionMatrix.confusionMatrixCreation();
			confusionMatrix.confusionMatrixPrint();
		} else {
			System.out.println("You need to pass atleast 1 parameter (cardata.txt) as the argument");
		}
	}
}