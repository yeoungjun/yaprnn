package yaprnn.mlp;

import java.io.Serializable;
import java.util.Arrays;

public class Layer implements Serializable {
	private static final long serialVersionUID = -4607204450973284028L;

	private Layer prevLayer;

	private double[][] weightMatrix;
	private double[][] gradientMatrix;
	private double[][] lastGradientMatrix;

	private ActivationFunction function;

	private double[] output;
	double[] input = null;
	private double bias;
	double[] layerInput;

	/**
	 * Constructor; Is  initialized with the previuos layer, activation function  number of neurons and the bias
	 * @param prevLayer The previous layer; Null if this layer is the first one. 
	 * @param neurons Number of neurons.
	 * @param function Activation function of this layer
	 * @param bias The bias
	 * @throws BadConfigException Is thrown in case of incorrect configuration
	 */
	public Layer(Layer prevLayer, int neurons, ActivationFunction function, double bias) throws BadConfigException {
		
		neurons++;
		
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
		this.layerInput = new double[neurons];
		
		if (prevLayer == null) return;

		this.prevLayer = prevLayer;
		this.weightMatrix = new double[neurons][prevLayer.getSize()];
		this.gradientMatrix = new double[neurons][prevLayer.getSize()];
		this.lastGradientMatrix = new double[neurons][prevLayer.getSize()];
		
		// Setting values of matrices and arrays
		for (int h = 0; h < neurons; h++)
			for (int i = 0; i < prevLayer.getSize(); i++)
				weightMatrix[h][i] = Math.random() * (Math.random() < 0.5 ? -1 : 1);
	}

	/**
	 *  This function is to be used at the inout layer and sets the input data.
	 * @param input The input vector, that needs the same dimension as the layer.
	 * @throws BadConfigException Is thrown in case of wrong configuration
	 */
	public boolean setInput(double[] input) {
		if ((output.length - 1)!= input.length) 	return false;
	
		this.input = input;

		output = Arrays.copyOf(input, output.length);
		output[output.length - 1] = 0;
		return true;
	}

	/**
	 *  This function uses the reference on the last Layer to calculate the output vector of this Layer . It is recursive reverted  to the first Layer
	 *   and multiplied  with the 	corresponding  weights  to create the output vector.
	 * @return The output vector of this layer.  if this layer is the output layer, this vector is the output of the network
	 */
	public double[] getOutput() {
		// Recursion cancel
		if (prevLayer == null){
			layerInput = output;
			return output;
		}

		input  = prevLayer.getOutput();

		
		// Generate the output
		for (int h = 0; h < output.length - 1; h++) {
			// Reset
			output[h] = 0;

			//  Multiply every output of the last Layer with the corresponding  matrix  and add it.
			for (int i = 0; i < input.length; i++)
				output[h] += input[i] * weightMatrix[h][i];
			
			layerInput[h] = output[h];
			// Use the activation function on the sum.
			output[h] = function.compute(output[h]);
		}
		
		output[output.length - 1] = bias;
		
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
		try {
		if(prevLayer == null) return;
		
		// init
			double[] preLayerError = new double[prevLayer.getSize()];
			double preLayerNetOutput = 0;

			// calculate previous layer's overall output
			for (double in : prevLayer.input) preLayerNetOutput += in;

			// alter gradient
			for (int i = 0; i < gradientMatrix.length; i++)
				for (int h = 0; h < prevLayer.output.length; h++)
					gradientMatrix[i][h] += error[i] * prevLayer.output[h];
			
			// generate preLayerError
			for (int i = 0; i < prevLayer.getSize(); i++) {
				for (int h = 0; h < error.length; h++)
					preLayerError[i] += error[h] * weightMatrix[h][i];

				preLayerError[i] *= prevLayer.function.derivation(prevLayer.layerInput[i]);
			}

			prevLayer.backPropagate(preLayerError);
		
		} catch (Exception e) {
			e.printStackTrace();
			
			System.exit(0);
		}

	}

	/**
	 * Ajusts recursively the weights of the net.
	 * @param eta 
	 * @param iterations the number of iteration since the last update.
	 * @param eta The learning rate to be used. 
	 * @param iterations 
	 */
	public void update(double iterations, double eta) {
		if(prevLayer == null) return;
		
		for(int i = 0; i < output.length; i++)
			for(int h = 0; h < prevLayer.getSize(); h++){
				weightMatrix[i][h] -= eta * (gradientMatrix[i][h] / iterations);
				gradientMatrix[i][h] = 0;
			}
		
		prevLayer.update(iterations, eta);
	}

	public void update(double iterations, double eta, double momentum) {
		if(prevLayer == null) return;
		
		for(int i = 0; i < output.length; i++)
			for(int h = 0; h < prevLayer.getSize(); h++){
				
				lastGradientMatrix[i][h] = eta * ( (1 - momentum )*  (gradientMatrix[i][h] / iterations) + momentum * lastGradientMatrix[i][h]);
				weightMatrix[i][h] -= lastGradientMatrix[i][h];
				gradientMatrix[i][h] = 0;
			}
		
		prevLayer.update(iterations, eta, momentum);
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
		Layer additionalLayer = new Layer(this, this.prevLayer.getSize() - 1, this.prevLayer.getActivationFunction(), this.prevLayer.getBias());		
		
		double[] firstLayerinput =Arrays.copyOf(lastLayerOutput, lastLayerOutput.length - 1);
		
		// use output ans new input
		if(!this.prevLayer.setInput(firstLayerinput))
			System.out.println("Lenght of lastLayerOutput: " + lastLayerOutput.length);
		
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
			additionalLayer.update(1, eta, 0);
		}

		// Restore the layer
		this.prevLayer.prevLayer = prevLayer;

		return getOutput();
	}
}
