package yaprnn.mlp;

public interface NeuralNetwork {

	/**
	 * Liefert die Anzahl der Schichten eines neuronalen Netzes.
	 * 
	 * @return Anzahl der Schichten.
	 */
	public int getNumLayers();

	/**
	 * Liefert die Größe einer Schicht.
	 * 
	 * @param layer
	 *            Die Schicht (Startet mit 0).
	 * @return Anzahl der Neuronen dieser Schicht.
	 */
	public int getLayerSize(int layer);

	/**
	 * Liefert die Gewichtsmatrix zwischen zwei Schichten.
	 * 
	 * @param layer
	 *            Die Hintere dieser beiden Schichten (Startet mit 1).
	 * @return Eine Gewichtsmatrix der Form double[][], wobei die erste
	 *         Dimension die Neuronen der hinteren Schicht ist und die zweite
	 *         die Neuronen der vorhergehenden Schicht.
	 */
	public double[][] getWeights(int layer);

	/**
	 * Liefert die Aktivierungsfunktion einer Schicht.
	 * 
	 * @param layer
	 *            Die Schicht (Startet mit 0).
	 * @return Die Aktivierungsfunktion dieser Schicht.
	 */
	public ActivationFunction getActivationFunction(int layer);

	/**
	 * Liefert den Bias einer Schicht.
	 * 
	 * @param layer
	 *            Die Schicht (Startet mit 0).
	 * @return Der Bias.
	 */
	public double getBias(int layer);

	/**
	 * @return name of the MLP
	 */
	public String getName();

}
