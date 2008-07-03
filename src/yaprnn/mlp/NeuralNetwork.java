package yaprnn.mlp;

public interface NeuralNetwork {

	/**
	 * Returns the number of layers in the neuronal network.
	 * 
	 * @return Number of layers.
	 */
	public int getNumLayers();

	/**
	 * Returns the size of the layer.
	 * 
	 * @param layer
	 *            The layer (starts with 0).
	 * @return Number of neurons in this layes.
	 */
	public int getLayerSize(int layer);

	/**
	 * returns the weight matrix between two layers.
	 * 
	 * @param layer
	 *            The second layer(starts with  1).
	 * @return The weight matrix of type double[][], where the first dimension represents
	 *         the number of neurons in the second layer and the second- in the first one.
	 *         
	 */
	public double[][] getWeights(int layer);

	/**
	 * Returns the activation function of the layer.
	 * 
	 * @param layer
	 *            The layer(starts with 0).
	 * @return The activation function of the layer.
	 */
	public ActivationFunction getActivationFunction(int layer);

	/**
	 * Returns the bias.
	 * 
	 * @param layer
	 *            The layer(starts with 0).
	 * @return The bias.
	 */
	public double getBias(int layer);

	/**
	 * Returns a readable name of NeuralNetwork
	 * 
	 * @return name name of the NeuralNetwork
	 */
	public String getName();

	/**
	 * Sets a readable name for the NeuralNetwork
	 * 
	 * @param name
	 *            new name of the NeuralNetwork
	 */
	public void setName(String name);

}
