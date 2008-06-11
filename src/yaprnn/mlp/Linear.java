package yaprnn.mlp;

public class Linear implements ActivationFunction{

	private static final long serialVersionUID = -7102325169146600623L;

	public double compute(double x) {
		return x;
	}

	public double derivation(double x) {
		return 1;
	}

	public double getMinimumValue() {
		return Double.MIN_VALUE;
	}

}
