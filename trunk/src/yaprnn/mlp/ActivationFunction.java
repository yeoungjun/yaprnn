package yaprnn.mlp;

import java.io.Serializable;

public interface ActivationFunction extends Serializable{

	/**
	 * The regular activation function
	 * @param x  The parameter of activation function. 
	 * @return Value of activation function at x
	 */
	public double compute(double x);
	
	/**
	 * The derivative of  the activation function
	 * @param x  The parameter of the derivative of  the activation function
	 * @return Value of the derivative of  the activation function at x
	 */
	public double derivation(double x);

	/**
	 * Returns the minimum value of the implementing activation function.
	 * @return The minimum value. (Infinity -> Double.MAX_VALUE / Double.MIN_VALUE)
	 */
	public double getMinimumValue();

}
