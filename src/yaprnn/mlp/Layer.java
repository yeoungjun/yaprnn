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
	 * Konstruktor; wird mit der vorhergehenden Schicht initialisiert, sowie der Aktivierungsfunktion und des Bias
	 * @param prevLayer Die vorhergehende Schicht; Null wenn diese Schicht die Eingabeschicht des Netzes ist. 
	 * @param neurons Die Neuronen dieser Schicht.
	 * @param function Die Aktivierungsfunktion dieser Schicht
	 * @param bias Der Bias. Ein Wert der zur Eingabe jedes Neurons eingerechnet wird
	 * @throws BadConfigException Wird bei ungültiger Konfiguration zurückgeworfen.
	 */
	public Layer(Layer prevLayer, int neurons, ActivationFunction function, double bias) throws BadConfigException {
		
		// Konfiguration überprüfen
		if (neurons <= 0)
			throw new BadConfigException("Unglültige Anzahl für Neuronen: "
					+ neurons, BadConfigException.INVALID_NEURON_NUMBER);
		if (function == null)
			throw new BadConfigException("Keine ActivationFunction übergeben!",
					BadConfigException.INVALID_ACTIVATION_FUNCTION);

		// Variablen initialisieren
		this.function = function;
		this.bias = bias;
		this.output = new double[neurons];

		if (prevLayer == null) return;
		
		this.layerInput = new double[neurons];		
		this.prevLayer = prevLayer;
		this.weightMatrix = new double[neurons][prevLayer.getSize()];
		this.gradientMatrix = new double[neurons][prevLayer.getSize()];
		
		// Werte der Matritzen und Arrays setzen
		for (int h = 0; h < neurons; h++) {
			output[h] = 0;
			for (int i = 0; i < prevLayer.getSize(); i++) {
				weightMatrix[h][i] = Math.random() * (Math.random() < 0.5 ? -1 : 1);
				gradientMatrix[h][i] = 0;
			}
		}
	}

	/**
	 *  Diese Funktion wird bei der Eingabeschicht verwendet und setzt die Eingabedaten.
	 * @param input Ein Eingabevektor, der die gleiche Dimension wie die Neuronen braucht
	 * @throws BadConfigException Wird bei falscher Konfiguration zurückgeworfen
	 */
	public boolean setInput(double[] input) {
		if (output.length != input.length)
			return false;
		
		output = input;
		return true;
	}

	/**
	 *  Diese Funktion verwendet den Verweis auf die letzte Schicht um den Ausgabevektor dieser Schicht zu erzeugen. Es wird also Rekursiv bis zur Eingabeschicht
	 *  zurückgegriffen und mit den entsprechenden Gewichten multipliziert und ein Ausgabevektor erzeugt.
	 * @return Der Ausgabevektor dieser Schicht. Wenn diese Schicht die Ausgabeschicht ist, ist dieser Vektor das Ergebnis des Netztes.
	 */
	public double[] getOutput() {
		// Rekursion abbrechen
		if (prevLayer == null)
			return output;

		// Speicherung wg. backPropagation
		 input = prevLayer.getOutput();

		// Den Output generieren
		for (int h = 0; h < output.length; h++) {
			// Resetten und Bias einrechnen
			output[h] = bias;

			for (int i = 0; i < input.length; i++) {
				// Jede Ausgabe des letzten Layers mit der verbindenden Matrix multiplizieren und addieren
				output[h] += input[i] * weightMatrix[h][i];
			}

			layerInput[h] = output[h] - bias;
			// Akativierungsfunktion auf die Summe anwenden
			output[h] = function.compute(output[h]);
		}

		return output;
	}

	/**
	 * Die Anzahl der Neuronen dieser Schicht.
	 * @return Anzahl der Neuronen
	 */
	public int getSize() {
		return output.length;
	}

	/**
	 * 
	 * @param error Der Fehler der vorherigen Schicht auf diese Schicht bezogen. Wenn diese Schicht die Augabeschicht ist, wird der Ausgabefehler gebraucht. Nach berechnung
	 * der nötigen Gewichtsveränderungen und Speicherung dieser, wird der Fehler der vorherigen Schicht errechnet und dieser dann an die nächste Schicht weitergegeben.
	 * @throws BadConfigException Bei falschem Fehlervektor.
	 */
	public void backPropagate(double[] error) {
		if(prevLayer == null) return;
		
//		if(error.length != output.length) throw new BadConfigException("Flascher Fehler-Vektor übergeben!", BadConfigException.INVALID_ERROR_VECTOR);
		
		
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
	 * Passt die Gewichte des Netztes an rekursiv an.
	 * @param iterations Die Anzahl der Durchläufe des Netztes seit dem letzten Update
	 * @param eta Die Lernrate, die verwendet werden soll. 
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
	 * Der aktuelle Biaswert. [unnecessary]
	 * @return Der aktuelle Bias.
	 */
	public double getBias() {
		return bias;
	}

	/**
	 * String-repräsentation der aktuellen Neuronen und Gewichte.
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
	 * Gibt die Aktivierungsfunktion zurück.
	 * @return Das ActivationFunction Objekt welches diese Schicht benutzt.
	 */
	public ActivationFunction getActivationFunction(){
		return function;
	}

	public double[][] getWeightMatrix() {
		return this.weightMatrix;
	}
	
	public double[] makeAutoencoder(double value, double maxIterations, double upperBound, double eta) throws BadConfigException{
		
		double[] trainingValues = new double[this.getSize()];
//		Arrays.fill(trainingValues, 0);
		trainingValues[0] = value;
		if(prevLayer == null)	return trainingValues;

		// Eingabedaten holen
		double[] lastLayerOutput = prevLayer.makeAutoencoder(value, maxIterations, upperBound, eta);
		//System.out.println("##################################nextLayer################");
		// Layer herausnehmen
		Layer prevLayer = this.prevLayer.prevLayer;
		this.prevLayer.prevLayer = null;
		
		// neuen Layer hinzufügen
		Layer additionalLayer = new Layer(this, this.prevLayer.getSize(), this.prevLayer.getActivationFunction(), this.prevLayer.getBias());		
		
		// Ausgabe als neue Eingabe verwenden
		this.prevLayer.setInput(lastLayerOutput);
		//System.out.println("\n INPUT: ");
		//for(double o : lastLayerOutput) 	System.out.print("\t" + o + "\n");
		
//		System.out.println("\n OUTPUT: ");
//		double[] outVal = getOutput();
//		for(double o : outVal) 	System.out.print("\t" + o + "\n");
//
//		System.out.println("\n OUTPUT(ADD): ");
//		outVal = additionalLayer.getOutput();
//		for(double o : outVal) 	System.out.print("\t" + o + "\n");

		// train Online
		double out[] = null;
		           
		// Ausgabe berechnen
		for(int i = 0; i < maxIterations; i++) {
			out = additionalLayer.getOutput();
			
			// Den Fehler an der Ausgabeschicht berechnen
			double[] errVec = new double[additionalLayer.getSize()];
			for (int h = 0; h < errVec.length; h++)
				errVec[h] = out[h] - lastLayerOutput[h];
	
			double overallError = 0;
			for(double e : errVec) overallError += Math.pow(e,2);
			if(overallError < (2 * upperBound)) break;
//			else System.out.println("OverallError: " + 0.5 * overallError);
			
			// Fehler zurückpropagieren
			additionalLayer.backPropagate(errVec);
	
			// Gewichte anpassen
			additionalLayer.update(1, eta);
			
		}

		// Layer wieder integrieren
		this.prevLayer.prevLayer = prevLayer;
//		System.out.println("\n später OUTPUT: ");
//		outVal = getOutput();
//		for(double o : outVal) 	System.out.print("\t" + o + "\n");
//
//		System.out.println("\n OUTPUT(ADD): ");
//		outVal = additionalLayer.getOutput();
//		for(double o : outVal) 	System.out.print("\t" + o + "\n");
		
		return getOutput();
	}
}
