/**
 * 
 */
package yaprnn.mlp;

/**
 * This interface enables the use of diverse learning rate modifications.
 */
public interface Eta {
	/**
	 * Returns The learning rate and may assign a new one.
	 * @param lastError The last computed error.
	 * @return The learning rate.
	 */
	public double getEta(double lastError);
}
