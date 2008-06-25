package yaprnn.dvv;

public class InvalidFileException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8045507416358627917L;
	
	private String filename;

	public InvalidFileException(String filename) {
		this.filename = filename;
	}

	public String getFilename() {
		return filename;
	}

}
