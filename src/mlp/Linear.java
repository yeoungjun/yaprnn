package mlp;

public class Linear implements ActivationFunction{

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
