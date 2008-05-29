package yaprnn.mlp;

import java.io.Serializable;
import java.util.Collection;
import java.util.Arrays;

import dvv.Data;

public class MLP implements Serializable, NeuralNetwork {

	private static final long serialVersionUID = -5212835785366190139L;
	protected Layer[] layer;

	/**
	 * Konstruktor; Erstellt das neuronale Netz und setzt die benötigten
	 * Variablen.
	 * 
	 * @param inputNeurons
	 */
	public MLP(int inputNeurons, int outputNeurons, int[] hiddenLayers,
			ActivationFunction[] functions, double[] bias, boolean autoencoder)
			throws BadConfigException {

		// Konfiguration überprüfen
		if (inputNeurons < 1)
			throw new BadConfigException(
					"Ungültige Anzahl für Neuronen in der Eingabeschicht!",
					BadConfigException.INVALID_INPUT_LAYER_DIMENSION);
		if (outputNeurons < 1)
			throw new BadConfigException(
					"Ungültige Anzahl für Neuronen in der Ausgabeschicht!",
					BadConfigException.INVALID_OUTPUT_LAYER_DIMENSION);
		if (functions.length != hiddenLayers.length + 2)
			throw new BadConfigException(
					"Anzahl der Aktivierungsfunktionen stimmt nicht mit den Layern überein!",
					BadConfigException.INVALID_NUMBER_OF_FUNCTIONS);
		if (bias.length != hiddenLayers.length)
			throw new BadConfigException(
					"Anzahl der Bias(se?) stimmt nicht mit den Layern überein!",
					BadConfigException.INVALID_NUMBER_OF_BIAS);

		// inputLayer erzeugen
		layer = new Layer[hiddenLayers.length + 2];
		layer[0] = new Layer(null, inputNeurons, functions[0], 0);

		// hiddenLayer erzeugen
		for (int i = 0; i < hiddenLayers.length; i++) {
			layer[i + 1] = new Layer(layer[i], hiddenLayers[i], functions[i],
					bias[i]);
		}

		// outputLayer erzeugen
		layer[layer.length - 1] = new Layer(layer[layer.length - 2],
				outputNeurons, functions[functions.length - 1], 0);

		if (!autoencoder)
			return;

		// Als Autoencoder trainieren
		// TODO Autencoder training implementieren
	}

	/**
	 * Diese Funktion führt eine Berechnung mit dem neuronalen Netz im
	 * Online-Lernmmodus durch
	 *
	 * @param dataCollection Eine Collection vom Typ dvv.Data mit Eingabewerten und Zielwerten
	 * @param eta Die Lernrate die verwendet werden soll.
	 * @throws BadConfigException
	 */
	public double runOnline(Collection<Data> dataCollection, double eta) throws BadConfigException {
		if (layer == null)
			return 0;
		if (eta < 0) throw new BadConfigException("Eta is negative!", BadConfigException.INVALID_ETA);

		double err = 0;
		double[] out;
		double[] target = new double[layer[layer.length - 1].getSize()];
		
		for (Data theData : dataCollection) {

			// Zielwert erzeugen
			Arrays.fill(target, 0);
			if(theData.getTarget() > target.length) throw new BadConfigException("Invalid Target: " + theData.getTarget(), BadConfigException.INVALID_TARGET_VECTOR);
			target[theData.getTarget()] = 1;

			// Eingabedaten setzen
			layer[0].setInput(theData.getData());

			// Ausgabe berechnen
			out = layer[layer.length - 1].getOutput();

			// Fehler bestimmen
			err += layer[layer.length - 1].getError(target);

			// Aktuelle Werte ausgeben
			for (double value : out)
				System.out.print(value + " | ");
			System.out.println("Der Fehler beträgt noch " + err);

			// Den Fehler an der Ausgabeschicht berechnen
			double[] errVec = new double[target.length];
			for (int h = 0; h < target.length; h++)
				errVec[h] = out[h] - target[h];

			// Fehler zurückpropagieren
			layer[layer.length - 1].backPropagate(errVec);

			// Gewichte anpassen
			layer[layer.length - 1].update(1, eta);

		}
		
		// Mittelwert berechnen  und Fehler zurückgeben
		return err = err / dataCollection.size();
	}

	/**
	 * Diese Funktion führt eine Berechnung mit dem neuronalen Netz im
	 * Batch-Lernmmodus durch
	 * 
	 * @param dataCollection Eine Collection vom Typ dvv.Data mit Eingabewerten und Zielwerten
	 * @param eta Die Lernrate die verwendet werden soll.
	 * @throws BadConfigException 
	 */
	public double runBatch(Collection<Data> dataCollection, double eta) throws BadConfigException {
		if (layer == null)
			return 0;
		if (eta < 0) throw new BadConfigException("Eta is negative!", BadConfigException.INVALID_ETA);

		double err = 0;
		double[] out;
		double[] target = new double[layer[layer.length - 1].getSize()];
		
		for (Data theData : dataCollection) {

			// Zielwert erzeugen
			Arrays.fill(target, 0);
			if(theData.getTarget() > target.length) throw new BadConfigException("Invalid Target: " + theData.getTarget(), BadConfigException.INVALID_TARGET_VECTOR);
			target[theData.getTarget()] = 1;

			// Eingabedaten setzen
			layer[0].setInput(theData.getData());

			// Ausgabe berechnen
			out = layer[layer.length - 1].getOutput();

			// Fehler bestimmen
			err += layer[layer.length - 1].getError(target);

			// Aktuelle Werte ausgeben
			for (double value : out)
				System.out.print(value + " | ");
			System.out.println("Der Fehler beträgt noch " + err);

			// Den Fehler an der Ausgabeschicht berechnen
			double[] errVec = new double[target.length];
			for (int h = 0; h < target.length; h++)
				errVec[h] = out[h] - target[h];

			// Fehler zurückpropagieren
			layer[layer.length - 1].backPropagate(errVec);
		}

		// Gewichte anpassen
		layer[layer.length - 1].update(dataCollection.size(), eta);
		
		// Mittelwert berechnen  und Fehler zurückgeben
		return err = err / dataCollection.size();
		
	}

	/**
	 * Mit dieser Methode kann ein Testlauf gestartet werden.
	 * 
	 * @return Die Ausgabe der Neuronen in einem prozentualen Verhältnis.
	 */
	public double[] classify(double[] input) throws BadConfigException {
		layer[0].setInput(input);
		double[] netOutput = layer[layer.length - 1].getOutput();
		double[] retVal = new double[netOutput.length];
		double G = 0;

		for (double g : netOutput)
			G += g
					- layer[layer.length - 1].getActivationFunction()
							.getMinimumValue();
		if (G == 0) {
			double val = 100 / retVal.length;
			for (int i = 0; i < retVal.length; i++)
				retVal[i] = val;
			return retVal;
		}

		for (int i = 0; i < retVal.length; i++) {
			retVal[i] = (netOutput[i] - layer[layer.length - 1]
					.getActivationFunction().getMinimumValue())
					* 100 / G;
		}

		return retVal;
	}

	/**
	 * Erzeugt eine String-Repersenation des neuronalen Netzes
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < layer.length; i++) {
			buffer.append("Layer " + i);
			buffer.append("\n" + layer[i].toString() + "\n");
		}
		return buffer.toString();
	}

	/**
	 * Liefert die Anzahl der Schichten eines neuronalen Netzes.
	 * 
	 * @return Anzahl der Schichten.
	 */
	public ActivationFunction getActivationFunction(int layer) {
		if (layer > (this.layer.length - 1))
			return null;
		return this.layer[layer].getActivationFunction();
	}

	/**
	 * Liefert die Größe einer Schicht.
	 * 
	 * @param layer
	 *            Die Schicht (Startet mit 0).
	 * @return Anzahl der Neuronen dieser Schicht.
	 */
	public double getBias(int layer) {
		if (layer > (this.layer.length - 1))
			return 0;
		return this.layer[layer].getBias();
	}

	/**
	 * Liefert die Gewichtsmatrix zwischen zwei Schichten.
	 * 
	 * @param layer
	 *            Die Hintere dieser beiden Schichten (Startet mit 1).
	 * @return Eine Gewichtsmatrix der Form double[][], wobei die erste
	 *         Dimension die Neuronen der hinteren Schicht ist und die zweite
	 *         die Neuronen der vorhergehenden Schicht.
	 */
	public int getLayerSize(int layer) {
		if (layer > (this.layer.length - 1))
			return -1;
		return this.layer[layer].getSize();
	}

	/**
	 * Liefert die Aktivierungsfunktion einer Schicht.
	 * 
	 * @return Die Aktivierungsfunktion dieser Schicht.
	 */
	public int getNumLayers() {
		return this.layer.length;
	}

	/**
	 * Liefert den Bias einer Schicht.
	 * 
	 * @param layer
	 *            Die Schicht (Startet mit 0).
	 * @return Der Bias.
	 */
	public double[][] getWeights(int layer) {
		if (layer > (this.layer.length - 1))
			return null;
		return this.layer[layer].getWeightMatrix();
	}
}
