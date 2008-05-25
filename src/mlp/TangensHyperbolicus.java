package mlp;

public class TangensHyperbolicus implements ActivationFunction {

	/**
	 * Einfache Evaluierung der Funktion Tangens-Hyperbolicus mit dem Parameter x.
	 * @param x Eingabewert in die Funktion tanh.
	 * @return Der Funktionswert der Funktion tanh mit dem Parameter x.
	 */
	public double compute(double x) {
		return Math.tanh(x);
	}

	/**
	 * Evaluiert die Ableitung der Funktion tanh mit dem Pararmeter x.
	 * @param x Eingabewert in die Ableitung der Funktion tanh.
	 * @return Der Funktionswert der Ableitung der Funktion tanh mit dem Parameter x.
	 */
	public double derivation(double x) {
		double v = Math.tanh(x);
		return 1 - v * v;
	}

	public double getMinimumValue() {
		return -1;
	}

}
