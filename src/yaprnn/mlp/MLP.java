package yaprnn.mlp;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import yaprnn.dvv.Data;

public class MLP implements Serializable, NeuralNetwork {

	private static final long serialVersionUID = -5212835785366190139L;

	/**
	 * Readable name identifier
	 */
	private String name;
	private boolean isTrained = false;
	private Layer[] layer;  

	/**
	 * Constructor; Builds the Network and sets all the variables
	 * 
	 * 
	 * @param inputNeurons
	 * @param outputNeurons
       * @param hiddenLayers ,array with hidden layers
       * @param functions, array with activation functions
       * @param bias, array with biases
	 * @param autoencoder 
	 */
	public MLP(String name, int inputNeurons, int outputNeurons, int[] hiddenLayers,
			ActivationFunction[] functions, double[] bias, boolean autoencoder)
			throws BadConfigException {

		// Tests the configuration
		if (inputNeurons < 1)
			throw new BadConfigException(
					"Ungueltige Anzahl fuer Neuronen in der Eingabeschicht!",
					BadConfigException.INVALID_INPUT_LAYER_DIMENSION);
		if (outputNeurons < 1)
			throw new BadConfigException(
					"Ungueltige Anzahl fuer Neuronen in der Ausgabeschicht!",
					BadConfigException.INVALID_OUTPUT_LAYER_DIMENSION);
		if (functions.length != hiddenLayers.length + 2)
			throw new BadConfigException(
					"Anzahl der Aktivierungsfunktionen stimmt nicht mit den Layern überein!",
					BadConfigException.INVALID_NUMBER_OF_FUNCTIONS);
		if (bias.length != hiddenLayers.length)
			throw new BadConfigException(
					"Anzahl der Bias(se?) stimmt nicht mit den Layern Ueberein!",
					BadConfigException.INVALID_NUMBER_OF_BIAS);

		this.name = name;
		
		// Creates the input layer
		layer = new Layer[hiddenLayers.length + 2];
		layer[0] = new Layer(null, inputNeurons, functions[0], 0);

		// creates  hidden layers
		for (int i = 0; i < hiddenLayers.length; i++) {
			layer[i + 1] = new Layer(layer[i], hiddenLayers[i], functions[i],
					bias[i]);
		}

		// creates the outputlayer
		layer[layer.length - 1] = new Layer(layer[layer.length - 2],
				outputNeurons, functions[functions.length - 1], 0);

		if (!autoencoder)
			return;

		// Trains as Autoencoder 
		layer[layer.length - 1].makeAutoencoder(Math.random(), 250, 0.001, 0.2);
	}

	/**
	 * This function performs the online calculation with the the Network
	 * 
	 * 
	 * @param dataCollection
	 *            A collection of the type  dvv.Data with input- and targetvalues
	 *            
	 * @param eta
	 *            The learning rate to be used.
	 * @throws BadConfigException
	 */
	public double runOnline(Collection<Data> dataCollection, double eta, double momentum) {
		if (layer == null)
			return 0;

		isTrained  = true;
		
		double[] out;
		double[] target = new double[layer[layer.length - 1].getSize()];
		double[] errVec = new double[target.length];

		for (Data theData : dataCollection) {
			// creates target values
			Arrays.fill(target, 0);
			target[theData.getTarget()] = 1;

			// Sets the input data
			if(!layer[0].setInput(theData.getData()))
				System.out.println("Can't set input data!");

			// Calculate the output
			out = layer[layer.length - 1].getOutput();

			// Calculates the error of the output layer
			for (int h = 0; h < target.length; h++)
				errVec[h] = (out[h] - target[h]) * layer[layer.length-1].getActivationFunction().derivation(layer[layer.length-1].layerInput[h]);

			// Error backpropagation 
			layer[layer.length - 1].backPropagate(errVec);

			// Adjust the weights
			if(momentum > 0)
				layer[layer.length - 1].update(eta, momentum);
			else
				layer[layer.length - 1].update(eta);
		}

		return runTest(dataCollection);
	}

	/**This function performs the batch calculation  with the Network
	 * 
	 * 
	 * 
	 * @param dataCollection
	 *            A collection of the type  dvv.Data with input- and targetvalues
	 *            
	 * @param eta
	 *            The learning rate to be used.
	 * @return den Testfehler. In case of an  error returns 0.
	 */
	
	public double runBatch(Collection<Data> dataCollection, int batchSize, double eta, double momentum) {
		if (layer == null)
			return 0;

		isTrained  = true;
		
		double[] out;
		double[] target = new double[layer[layer.length - 1].getSize()];
		double[] errVec = new double[target.length];
		int iterations = 0;
		
		for (Data theData : dataCollection) {
			// creates target values
			Arrays.fill(target, 0);
			target[theData.getTarget()] = 1;

			// Sets the input data
			if(!layer[0].setInput(theData.getData()))
				System.out.println("Can't set input data!");

			// Calculate the output
			out = layer[layer.length - 1].getOutput();

			// Calculates the error of the output layer
			for (int h = 0; h < target.length; h++)
				errVec[h] = (out[h] - target[h]) * layer[layer.length-1].getActivationFunction().derivation(layer[layer.length-1].layerInput[h]);

			// Error backpropagation 
			layer[layer.length - 1].backPropagate(errVec);
			iterations++;

			if(iterations % batchSize == 0 ) {
				// Adjust the weights
				if(momentum > 0)
					layer[layer.length - 1].update(eta, momentum);
				else
					layer[layer.length - 1].update(eta);
			}

		}
		return runTest(dataCollection);
	}
	

	/**
	 * This method performs the test using delivered data.
	 * 
	 * @param dataCollection
	 *            The data, tu be used for the test
	 * @return The test error. If an error occurse, returns 0.
	 */
	public double runTest(Collection<Data> dataCollection) {
		double err = 0;
		double[] out;
		double[] target = new double[layer[layer.length - 1].getSize()];


		for (Data theData : dataCollection) {
			// Creates target values
			Arrays.fill(target, 0);
			target[theData.getTarget()] = 1;

			// Sets the input data
			if (!layer[0].setInput(theData.getData()))
				return 0;

			// Calculate the output
			out = layer[layer.length - 1].getOutput();

			// Calculates the error of the output layer
			for (int h = 0; h < target.length; h++)
				err += Math.pow(out[h] - target[h], 2);
		}

		return (0.5 * err) / dataCollection.size();
	}

	/**
	 * This method starts a testrun.
	 * 
	 * @return the output of neurons in  percents.
	 */
	public double[] classify(double[] input) {
		layer[0].setInput(input);
//		double[] netOutput = layer[layer.length - 1].getOutput();
//		double[] retVal = new double[netOutput.length];
//
//		
//		// Get maximum value
//		double maxValue = 0;
//		for(double v : netOutput)
//			if(maxValue < v) maxValue = v;
//
//		for(int i = 0; i < netOutput.length; i++)
//			retVal[i] = 100 - ((maxValue - netOutput[i]) / maxValue) * 100;
//		
//		for (double g : netOutput)
//			G += g - layer[layer.length - 1].getActivationFunction().getMinimumValue();
//
//		if (G == 0) {
//			double val = 100 / retVal.length;
//			for (int i = 0; i < retVal.length; i++)
//				retVal[i] = val;
//			return retVal;
//		}
//
//		for (int i = 0; i < retVal.length; i++)
//			retVal[i] = (netOutput[i] - layer[layer.length - 1]	.getActivationFunction().getMinimumValue()) * 100 / G;

		return layer[layer.length - 1].getOutput();
	}

	/**
	 * String-representation of the neuronal network
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < layer.length; i++) {
			buffer.append("Layer " + i);
			buffer.append("\n" + layer[i].toString() + "\n");
		}
		return buffer.toString();
	}

	public ActivationFunction getActivationFunction(int layer) {
		if (layer > (this.layer.length - 1))
			return null;

		return this.layer[layer].getActivationFunction();
	}

	public double getBias(int layer) {
		if (layer > (this.layer.length - 1))
			return 0;

		return this.layer[layer].getBias();
	}

	public int getLayerSize(int layer) {
		if (layer > (this.layer.length - 1))
			return -1;

		return this.layer[layer].getSize();
	}

	public int getNumLayers() {
		return this.layer.length;
	}
	
	public double[][] getWeights(int layer) {
		if (layer > (this.layer.length - 1))
			return null;

		return this.layer[layer].getWeightMatrix();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isTrained(){
		return isTrained;
	}
}
