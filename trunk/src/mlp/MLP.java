package mlp;

public class MLP {

	protected Layer[] layer;
	double eta;
	
	/**
	 * Konstruktor; Erstellt das neuronale Netz und setzt die benötigten
	 * Variablen.
	 * 
	 * @param inputNeurons
	 *            Die Anzahl der Eingabeneuronen.
	 * @param outputNeurons
	 *            Die Anzahl der Ausgabeneuronen
	 * @param hiddenLayers
	 *            Die Array, dessen Größe gleich der Anzahl der versteckten
	 *            Schichten ist. Als jeweiligen Wert ist die Anzahl der Neuronen
	 *            der betreffenden Schicht anzugeben.
	 * @param eta
	 *            Die Lernrate
	 * @param functions
	 *            Dieses Array muss die entsprechenden Aktivierungsfunktionen
	 *            der versteckten Schichten und der Eingabe- sowie
	 *            Ausgabeschicht enthalten und braucht deshalb die Größe des
	 *            Array hiddenLayers + 2
	 * @param bias
	 *            Ein Array dessen Wert den Bias jeder versteckten Schicht
	 *            beschreibt. Braucht deshalb die Größe des Array hiddenLayers
	 * @param autoencoder
	 *            Gibt an ob das Netz als Autoencoder initialisiert werden soll.
	 * @throws BadConfigException
	 *             Wird bei fehlerhafter Konfiguration zurückgeworfen und
	 *             enthält einen Fehlercode, mit dem der Fehler eindeutig
	 *             bestimmt werden kann.
	 */
	public MLP(int inputNeurons, int outputNeurons, int[] hiddenLayers,
										double eta, ActivationFunction[] functions, double[] bias,
										boolean autoencoder) throws BadConfigException {

		// Konfiguration überprüfen
		if(inputNeurons < 1 ) throw new BadConfigException("Ungültige Anzahl für Neuronen in der Eingabeschicht!", BadConfigException.INVALID_INPUT_LAYER_DIMENSION);
		if(outputNeurons < 1 ) throw new BadConfigException("Ungültige Anzahl für Neuronen in der Ausgabeschicht!", BadConfigException.INVALID_OUTPUT_LAYER_DIMENSION);
		if(eta < 0) throw new BadConfigException("Ungültiges Eta!", BadConfigException.INVALID_ETA);
		if(functions.length != hiddenLayers.length + 2) throw new BadConfigException("Anzahl der Aktivierungsfunktionen stimmt nicht mit den Layern überein!", BadConfigException.INVALID_NUMBER_OF_FUNCTIONS);
		if(bias.length != hiddenLayers.length) throw new BadConfigException("Anzahl der Bias(se?) stimmt nicht mit den Layern überein!", BadConfigException.INVALID_NUMBER_OF_BIAS);
	
		// Lernrate
		this.eta = eta;
		
		// inputLayer erzeugen
		layer = new Layer[hiddenLayers.length + 2];
		layer[0] = new Layer(null, inputNeurons, functions[0], 0);

		// hiddenLayer erzeugen
		for(int i = 0; i < hiddenLayers.length; i++) {
			layer[i+1] = new Layer(layer[i], hiddenLayers[i], functions[i], bias[i]);
		}
		
		// outputLayer erzeugen
		layer[layer.length -1] = new Layer(layer[layer.length -2], outputNeurons, functions[functions.length - 1], 0);

		if(!autoencoder) return;
		
		// Als Autoencoder trainieren
		// TODO Autencoder training implementieren
	}

	/**
	 * Diese Funktion führt eine Berechnung mit dem neuronalen Netz im
	 * Online-Lernmmodus durch
	 * 
	 * @param in
	 *            Ein Eingabevektor, der als Eingabe für das Netz dient
	 * @param target
	 *            Der Zeilvektor; also die Sollausgabe des neuronalen Netzes
	 * @param maxIterations
	 *            maximale Iterationen
	 * @param targetOverallError
	 *            Bei unterschreiten dieser Grenze wird mit den Lernen
	 *            abgebrochen.
	 */
	public double runOnline(double[] in, double[] target) throws BadConfigException {
		if(layer == null) return 0;

		layer[0].setInput(in);
		double[] out = layer[layer.length -1].getOutput();
		double err = layer[layer.length -1].getError(target);
				
		for(double value : out)
			System.out.print(value + " | ");

		System.out.println("Der Fehler beträgt noch " + err);
				
		double[] errVec = new double[target.length];
		for(int h = 0 ; h < target.length; h++)
			errVec[h] = out[h] - target[h];
			
		layer[layer.length -1].backPropagate(errVec);
		layer[layer.length -1].update(1, eta);
		
		return err;
	}
	
	/**
	 * Diese Funktion führt eine Berechnung mit dem neuronalen Netz im
	 * Batch-Lernmmodus durch
	 * 
	 * @param in
	 *            Ein Eingabevektor, der als Eingabe für das Netz dient
	 * @param target
	 *            Der Zeilvektor; also die Sollausgabe des neuronalen Netzes
	 * @param maxIterations
	 *            maximale Iterationen
	 * @param targetOverallError
	 *            Bei unterschreiten dieser Grenze wird mit den Lernen
	 *            abgebrochen.
	 */
	public void runBatch(double[] in, double[] target, int maxIterations, double targetOverallError) {
		// TODO runBatch implementieren
	}
	
	/**
	 * Mit dieser Methode kann ein Testlauf gestartet wetden
	 * 
	 * @return
	 */
	public double[] classify(double[] input) throws BadConfigException {
		layer[0].setInput(input);
		double[] netOutput = layer[layer.length -1].getOutput();
		double [] retVal = new double[netOutput.length];
		double G = 0;
		
		for(double g: netOutput) G += g - layer[layer.length -1].getActivationFunction().getMinimumValue();
		if(G == 0) {
			double val = 100 / retVal.length;
			for(int i = 0; i < retVal.length; i ++)
				retVal[i] = val; 
			return retVal;
		}

		for(int i = 0; i < retVal.length;  i++) {
			retVal[i] =(netOutput[i] - layer[layer.length -1].getActivationFunction().getMinimumValue())*100 / G;
		}
		
		return retVal;
	}
	
	/**
	 * Setzt die Lernrate des Netzes und akzeptiert nur Werte zwischen 0 und 1.
	 * 
	 * @param newEta
	 *            Die neue Lernrate
	 */
	public void setEta(double newEta){
		if(newEta < 0) return;
// if(newEta > 1) return;
		this.eta = newEta;
	}
	
	/**
	 * Erzeugt eine String-Repersenation des neuronalen Netzes
	 */
	public String toString(){
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < layer.length; i++){
			buffer.append("Layer " + i);
			buffer.append("\n" + layer[i].toString() + "\n");
		}
		return buffer.toString();
	}
}
