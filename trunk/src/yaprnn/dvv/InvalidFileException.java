package yaprnn.dvv;

public class InvalidFileException extends Exception {

	private String filename;

	public InvalidFileException(String filename) {
		this.filename = filename;
	}

	public String getFilename() {
		return filename;
	}

}
