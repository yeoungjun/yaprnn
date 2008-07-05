package yaprnn.mlp;

import java.io.Serializable;

public class Layer implements Serializable {
	private static final long serialVersionUID = -4607204450973284028L;

	private Layer prevLayer;

	private double[][] weightMatrix;
	private double[][] gradientMatrix;

	private ActivationFunction function;

	private double[] output;
	private double[] input; // TODO unnecessary
	private double[] layerInput;
	private double bias;

	/**
	 * Constructor; Is  initialized with the previuos layer, activation function  number of neurons and the bias
	 * @param prevLayer The previous layer; Null if this layer is the first one. 
	 * @param neurons Number of neurons.
	 * @param function Activation function of this layer
	 * @param bias The bias
	 * @throws BadConfigException Is thrown in case of incorrect configuration
	 */
	public Layer(Layer prevLayer, int neurons, ActivationFunction function, double bias) throws BadConfigException {
		
		// Tests  configuration
		if (neurons <= 0)
			throw new BadConfigException("Unglültige Anzahl für Neuronen: "
					+ neurons, BadConfigException.INVALID_NEURON_NUMBER);
		if (function == null)
			throw new BadConfigException("Keine ActivationFunction übergeben!",
					BadConfigException.INVALID_ACTIVATION_FUNCTION);

		// Initializing variables
		this.function = function;
		this.bias = bias;
		this.output = new double[neurons];

		if (prevLayer == null) return;
		
		this.layerInput = new double[neurons];		
		this.prevLayer = prevLayer;
		this.weightMatrix = new double[neurons][prevLayer.getSize()];
		this.gradientMatrix = new double[neurons][prevLayer.getSize()];
		
		// Setting values of matrices and arrays
		for (int h = 0; h < neurons; h++) {
			output[h] = 0;
			for (int i = 0; i < prevLayer.getSize(); i++) {
				weightMatrix[h][i] = Math.random() * (Math.random() < 0.5 ? -1 : 1);
				gradientMatrix[h][i] = 0;
			}
		}
	}

	/**
	 *  This function is to be used at the inout layer and sets the input data.
	 * @param input The input vector, that needs the same dimension as the layer.
	 * @throws BadConfigException Is thrown in case of wrong configuration
	 */
	public boolean setInput(double[] input) {
		if (output.length != input.length)
			return false;
		
		output = input;
		return true;
	}

	/**
	 *  This function uses the reference on the last Layer to calculate the output vector of this Layer . It is recursive reverted  to the first Layer
	 *   and multiplied  with the 	corresponding  weights  to create the output vector.
	 * @return The output vector of this layer.  if this layer is the output layer, this vector is the output of the network
	 */
	public double[] getOutput() {
		// Recursion cancel
		if (prevLayer == null)
			return output;

		// Saving of backPropagation
		 input = prevLayer.getOutput();

		// Generate the output
		for (int h = 0; h < output.length; h++) {
			// Reset and add a bias
			output[h] = bias;

			//  Multiply every output of the last Layer with the corresponding  matrix  and add it.
			for (int i = 0; i < input.length; i++)
				output[h] += input[i] * weightMatrix[h][i];

			layerInput[h] = output[h] - bias;
			
			// Use the activation function on the sum.
			output[h] = function.compute(output[h]);
		}

		return output;
	}

	/**
	 * The number on neurons  in the current layer.
	 * @return Number of neurons
	 */
	public int getSize() {
		return output.length;
	}

	/**
	 * 
	 * @param error  Applies the Error of the previuos layer of the current layer .  If this layer the last one is, the output error will be used. After calculating and saving
	 * of the nescesary weight modifications , the error of the previous  layer will be calculated  and  passed to the next layer.
	 * @throws BadConfigException if the error vector wrong is.
	 */
	public void backPropagate(double[] error) {
		if(prevLayer == null) return;
		
//		if(error.length != output.length) throw new BadConfigException("Flascher Fehler-Vektor Uebergeben!", BadConfigException.INVALID_ERROR_VECTOR);
		
		
		double[] localError = new double[output.length];
		double preLayerError[] = new double[prevLayer.getSize()];

		for(int i = 0; i <localError.length; i++) {
			localError[i] = function.derivation(layerInput[i]);
			localError[i] = localError[i] * error[i];
			for(int h = 0; h < preLayerError.length; h++)
				gradientMatrix[i][h] += localError[i] * input[h]; 
		}
		
		for (int i = 0; i < preLayerError.length; i++) {
			preLayerError[i] =  0;
				for(int h = 0; h < output.length; h++)
					preLayerError[i] += weightMatrix[h][i] * localError[h];
		}

		prevLayer.backPropagate(preLayerError);
	}

	/**
	 * Ajusts recursively the weights of the net.
	 * @param iterations the number of iteration since the last update.
	 * @param eta The learning rate to be used. 
	 */
	public void update(int iterations, double eta) {
		if(prevLayer == null) return;
		
		for(int i = 0; i < output.length; i++)
			for(int h = 0; h < prevLayer.getSize(); h++){
				weightMatrix[i][h] -= eta * (gradientMatrix[i][h] / iterations);
				gradientMatrix[i][h] = 0;
			}
		
		prevLayer.update(iterations, eta);
	}

	/**
	 *  Returns the current Bias. [unnecessary]
	 * @return The current bias.
	 */
	public double getBias() {
		return bias;
	}

	/**
	 * String-representation of the current weights and neurons.
	 */
	public String toString() {
		if (weightMatrix == null) return "";

		StringBuffer buffer = new StringBuffer();

		buffer.append("lastLayer:");
		for(int i = 0; i < weightMatrix[0].length; i++)
			buffer.append("\t[" + i + "]");
		
		for(int i = 0; i < output.length; i++){
			buffer.append("\nNeuron [" + i + "]");
			for(int h = 0; h < prevLayer.getSize(); h++) {
				buffer.append("\t" + weightMatrix[i][h]);
			}
		}
		return buffer.toString();
	}
	
	/**
	 * Returns the activation function.
	 * @return The activation function, that is used in this layer.
	 */
	public ActivationFunction getActivationFunction(){
		return function;
	}

	public double[][] getWeightMatrix() {
		return this.weightMatrix;
	}
	
	public double[] makeAutoencoder(double value, double maxIterations, double upperBound, double eta) throws BadConfigException{
		
		double[] trainingValues = new double[this.getSize()];
		trainingValues[0] = value;
		if(prevLayer == null)	return trainingValues;

		// Input Data
		double[] lastLayerOutput = prevLayer.makeAutoencoder(value, maxIterations, upperBound, eta);

		// Remove tha layer
		Layer prevLayer = this.prevLayer.prevLayer;
		this.prevLayer.prevLayer = null;
		
		// Add the new layer
		Layer additionalLayer = new Layer(this, this.prevLayer.getSize(), this.prevLayer.getActivationFunction(), this.prevLayer.getBias());		
		
		// use output ans new input
		this.prevLayer.setInput(lastLayerOutput);
		
		// train Online
		double out[] = null;
		double[] errVec = new double[additionalLayer.getSize()];
		double overallError;
		
		for(int i = 0; i < maxIterations; i++) {
			out = additionalLayer.getOutput();
			
			// calculate the arror at the output layer
			for (int h = 0; h < errVec.length; h++)
				errVec[h] = out[h] - lastLayerOutput[h];
	
			overallError = 0;
			for(double e : errVec) overallError += Math.pow(e,2);
			if(overallError < (2 * upperBound)) break;
			
			// Backpropagate the error
			additionalLayer.backPropagate(errVec);
	
			// Adjust the weights
			additionalLayer.update(1, eta);
		}

		// Restore the layer
		this.prevLayer.prevLayer = prevLayer;

		return getOutput();
	}
}
