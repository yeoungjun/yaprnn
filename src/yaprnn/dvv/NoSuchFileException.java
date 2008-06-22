package yaprnn.dvv;

public class NoSuchFileException extends Exception {

	private String filename;

	public NoSuchFileException(String filename) {
		this.filename = filename;
	}

	public String getFilename() {
		return filename;
	}

}
