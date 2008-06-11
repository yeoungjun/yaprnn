package yaprnn.mlp;

public class BadConfigException extends Exception {
	private static final long serialVersionUID = 1L;
	private static int identifer = 0;
	
	public static int INVALID_NEURON_NUMBER = 1;
	public static int INVALID_ACTIVATION_FUNCTION = 2;
	public static int INVALID_INPUT_DIMENSION = 3;
	public static int INVALID_INPUT_LAYER_DIMENSION = 4;
	public static int INVALID_OUTPUT_LAYER_DIMENSION = 5;
	public static int INVALID_ETA = 6;
	public static int INVALID_NUMBER_OF_BIAS = 7;
	public static int INVALID_NUMBER_OF_FUNCTIONS = 8;
	public static int INVALID_TARGET_VECTOR = 9;
	public static int INVALID_ERROR_VECTOR = 10;
	
	public BadConfigException(String message) {
		super(message);
	}
	
	public BadConfigException(String message, int id) {
		super(message);
		identifer = id;
	}
	
	public int getId(){
		return identifer;
	}
	
}
