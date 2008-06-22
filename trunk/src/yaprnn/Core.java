package yaprnn;

import java.util.*;
import java.io.*;
import yaprnn.dvv.*;
import yaprnn.mlp.*;

/** Core is the main control class.
 *  It contains the methods for opening files, preprocessing, training and classifying,
 *  which are called by the GUI.
 */
class Core {

	private MLP mlp;
	private DVV dvv;
	private List<ActivationFunction> activations;

	/** Constructs a new Core Object. */
	public Core() {
		activations = new LinkedList<ActivationFunction>();
		activations.add(new TangensHyperbolicus());
		activations.add(new Sigmoid());
	}

	/** Classifies the given data item. This method returns a vector of percentages,
	 *  the size of which is equal to the number of classes into which the data
	 *  is classified. An entry   classify(input)[k] == p   means that input has
	 *  a chance of p to belong to class k.
	 *
	 *  @param  input the input data item
	 *  @return the vector of percentages
	 */
	public double[] classify(Data input) {
		if(mlp != null)
			//TODO: try-catch block provisorisch, eigentlich nicht nötig
			try {
				return mlp.classify(input.getData());
			} catch(BadConfigException e) {
				return null;
			}
		else
			return null;
	}

	/** Opens an IdxPicture data set contained in the specified filenames.
	 *  
	 *  @param dataFilename the file containing the image data
	 *  @param labelFilename the file containing the image labels
	 */
	public void openIdxPicture(String dataFilename, String labelFilename) {
		//TODO: Hier muss entschieden werden, wie die Fehlerbehandlung abläuft.
		//	Sollen die Exceptions direkt nach außen an die GUI weitergereicht werden ?
		//	In den Exceptions sind alle relevanten Daten (die Dateinamen) vorhanden.
		//
		//	Oder soll eine Funktion der GUI aufgerufen werden?
		//	Diese Funktionen könnten dann in dem GUI-Interface sein.
		try {
			dvv = new DVV(dataFilename, labelFilename);
		} catch(NoSuchFileException e) {
			e.printStackTrace();
		} catch(InvalidFileException e) {
			e.printStackTrace();
		} catch(FileMismatchException e) {
			e.printStackTrace();
		}
	}

	/** Creates a new MLP using the specified parameters.
	 *
	 *  @param layer              an array holding the number of neurons per layer
	 *  @param activationFunction an array holding the activation function to use for each layer
	 *  @param bias	              an array holding the bias for each layer
	 *  @param autoEncoder        true if the MLP is to be initialized with autoencoding, false otherwise
	 */
	public void newMLP(int[] layer, int[] activationFunction, double[] bias, boolean autoEncoder) {
		if(activationFunction.length == layer.length+2 && layer.length == bias.length && dvv != null) {
			final int numInputNeurons = dvv.getNumInputNeurons();
			final int numOutputNeurons = dvv.getNumOutputNeurons();
			if(numInputNeurons > 0 && numOutputNeurons > 0) {
				ActivationFunction[] functions = new ActivationFunction[activationFunction.length];
				for(int i=0; i<functions.length; i++)
					functions[i] = activations.get(i);
				//TODO: try-catch block provisorisch, eignetlich ist hier keine Exception nötig
				try {
					mlp = new MLP(numInputNeurons, numOutputNeurons, layer,
							functions, bias, autoEncoder);
				} catch(BadConfigException e) { }
			}
		}
	}

	/** Loads a MLP from the specified file.
	 *
	 *  @param filename the name of the file from which to read
	 *  @return a NeuralNetwork interface representing the loaded mlp
	 *  @throws NoSuchFileException if the specified file does not exist or could not be opened
	 */
	public NeuralNetwork loadMLP(String filename) throws NoSuchFileException {
		ObjectInputStream in;
		try {
			in = new ObjectInputStream(new FileInputStream(filename));
		} catch(FileNotFoundException e) {
			throw new NoSuchFileException(filename);
		} catch(IOException e) {
			//TODO: Soll hier bei einem Fehler beim Lesen (nicht FileNotFoundException)
			//	null zurückgegeben oder eine Exception geworfen werden ?
			return null;
		}
		try {
			mlp = (MLP)in.readObject();
		} catch(Exception e) {
			return null;
		}
		return mlp;
	}

	/** Saves the current MLP to the specified filename.
	 *
	 *  @param filename the name of the file where the MLP is to be stored
	 */
	public void SaveMLP(String filename) throws NoSuchFileException {
		ObjectOutputStream out;
		try {
			out = new ObjectOutputStream(new FileOutputStream(filename));
			out.writeObject(mlp);
		} catch(FileNotFoundException e) {
			throw new NoSuchFileException(filename);
		} catch(Exception e) {
			//TODO: Was soll hier im Fall einer IOException gemacht werden?
			//	Eine neue Exception dafür, oder lieber einen Rückgabewert?
		}
	}

	/** Preprocesses the currently loaded data set using the specified parameters.
	 *  If no data set is loaded, this method does nothing.
	 *
	 *  @param resolution the desired resolution of the result
	 *  @param overlap    the window overlap used for subsampling. Must be a value between 0 and 0.95
	 *  @param scalingFunction the function used to scale (e.g. to the range [0, 1]) the subsampled data
	 */
	public void preprocess(int resolution, double overlap, ActivationFunction scalingFunction) {
		//TODO: Was soll hier passieren, falls die Subsampling-Parameter ungültig sind ?
		if(dvv != null)
			dvv.preprocess(resolution, overlap, scalingFunction);
	}

	/** Returns a list of all available activation functions.
	 *
	 *  @return the list of activationFunctions
	 */
	public List<ActivationFunction> getAllActivationFunctions() {
		return activations;
	}

}
