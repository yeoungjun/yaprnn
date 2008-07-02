package yaprnn.dvv;

import java.util.Collection;
import java.util.LinkedList;
import java.util.ArrayList;
import java.io.IOException;
import yaprnn.mlp.ActivationFunction;

/** DVV is the main data managment and preprocessing class.
 *  It provides methods for reading picture and sound files and for preprocessing loaded files.
 */
public class DVV {

	private Collection<Data> allData;
	private Collection<Data> trainingData;
	private Collection<Data> testData;
	private int numInputNeurons;
	private int numOutputNeurons;

	/** Constructs a DVV for holding {@link IdxPicture} data with the specified filenames.
	 *
	 *  @param dataFilename  the name of the file containing the image data
	 *  @param labelFilename the name of the file containing the labels/targets
	 *  @throws InvalidFileException if one of the files does not have the required format
	 *  @throws FileMismatchException if the files do not appear to belong to the same dataset
	 *  @throws NoSuchFileException if one of the files does not exist
	 */
	public DVV(String dataFilename, String labelFilename)
				throws InvalidFileException, FileMismatchException, NoSuchFileException, IOException {
		allData = IdxPicture.readFromFile(dataFilename, labelFilename);
		numOutputNeurons = 10;
	}
	
	/** Constructs a DVV for holding {@link AiffSound} data with the specified filenames.
	 *
	 *  @param filename the names of the file containing the sound data
	 *  @throws InvalideFileException if one of the files does not have the required format
	 *  @throws NoSuchFileException if one of the files does not exist
	 */
	public DVV(Collection<String> filenames) throws InvalidFileException, NoSuchFileException {
		allData = AiffSound.readFromFile(filenames);
		numOutputNeurons = 5;
	}

	/** Returns the whole data set.
	 *
	 *  @return the data set
	 */
	public Collection<Data> getDataSet() {
		return allData;
	}

	/** Returns the training data set.
	 *
	 *  @return the training data
	 */
	public Collection<Data> getTrainingData() {
		if(trainingData == null) selectTrainingData();
		return trainingData;
	}

	/** Returns the test data set.
	 *
	 *  @return the test data
	 */
	public Collection<Data> getTestData() {
		if(testData == null) selectTrainingData();
		return testData;
	}

	/** Randomly selects data for the training or test data set, according to the specified percentages.
	 *
	 *  @param trainingDataPercentage the percentage of data to be used for training
	 *  @param testDataPercentage the percentage of data to be used for testing
	 */ 
	public void chooseRandomTrainingData(double trainingDataPercentage, double testDataPercentage) {
		int numTraining = (int)(allData.size() * trainingDataPercentage);
		int numTest = (int)(allData.size() * testDataPercentage);
		int numNotUsed = allData.size() - numTraining - numTest;
		java.util.Random random = new java.util.Random();
		for(Data data : allData) {
			switch(random.nextInt(3)) {
				case 0: if(numTraining > 0) {
					--numTraining; data.setTraining(); break;
				}
				case 1: if(numTest > 0) {
					--numTest; data.setTest(); break;
				}
				case 2: if(numNotUsed > 0) {
					--numNotUsed; data.setNotUsed(); break;
				}
				case 3: if(numTraining > 0) {
					--numTraining; data.setTraining(); break;
				}
				case 4: if(numTest > 0) {
					--numTest; data.setTest(); break;
				}
			}
		}
	}

	/** Preprocesses the whole data set.
	 *
	 *  @param resolution      the data is to be sampled to
	 *  @param overlap         the overlap used when determining the window sizes
	 *  @param scalingFunction the function used to scale the subsampled data
	 */
	public void preprocess(int resolution, double overlap, ActivationFunction scalingFunction) {
		//TODO: error handling
		for(Data data : allData)
			data.subsample(resolution, overlap, scalingFunction);
		Collection t = new ArrayList<AiffSound>(); 
		if (allData.getClass().isInstance(t))
			numInputNeurons = resolution;
		else 
			numInputNeurons = resolution * resolution;
	}

	/** Returns the size of the input vector.
	 *
	 *  @return the size of the input vector
	 */
	public int getNumInputNeurons() {
		return numInputNeurons;
	}

	/** Retuns the size of the output vector.
	 *
	 *  @return the size of the output vector
	 */
	public int getNumOutputNeurons() {
		return numOutputNeurons;
	}

	/** Selects training and test data and stores them in the appropriate collections. */
	private void selectTrainingData() {
		trainingData = new LinkedList<Data>();
		testData = new LinkedList<Data>();
		for(Data data : allData)
			if(data.isTraining()) 
				trainingData.add(data);
			else if(data.isTest())
				testData.add(data);
	}

}
