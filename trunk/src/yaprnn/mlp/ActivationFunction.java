package yaprnn.mlp;

import java.io.Serializable;

public interface ActivationFunction extends Serializable{

	/**
	 * Die normale Aktivierungsfunktion
	 * @param x Parameter der Aktivierungsfunktion. 
	 * @return Funktionswert der Aktivierungsfunktion an der Stelle x
	 */
	public double compute(double x);
	
	/**
	 * Die abgeleitete Aktivierungsfunktion
	 * @param x Parameter der abgeleiteten Aktivierungsfunktion
	 * @return Funktionswert der abgeleiteten Aktivierungsfunktion an der Stelle x
	 */
	public double derivation(double x);

	public double getMinimumValue();

}
