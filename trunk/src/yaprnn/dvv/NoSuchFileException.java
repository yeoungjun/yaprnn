package yaprnn.dvv;

public class NoSuchFileException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9011975069079364930L;
	
	private String filename;

	public NoSuchFileException(String filename) {
		this.filename = filename;
	}

	public String getFilename() {
		return filename;
	}

}
