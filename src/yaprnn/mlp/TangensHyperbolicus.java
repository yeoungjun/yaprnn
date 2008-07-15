package yaprnn.mlp;

public class TangensHyperbolicus implements ActivationFunction {

	private static final long serialVersionUID = -7432193650916186201L;

	public double compute(double x) {
		return Math.tanh(x);
	}

	public double derivation(double x) {
		double v = Math.tanh(x);
		return 1 - v * v;
	}


	@Override
	public String toString() {
		return "Tangens hyperbolicus";
	}

}
