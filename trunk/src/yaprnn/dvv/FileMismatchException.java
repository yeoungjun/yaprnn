package yaprnn.dvv;

public class FileMismatchException extends Exception {

	private String dataFilename;
	private String labelFilename;

	public FileMismatchException(String dataFilename, String labelFilename) {
		this.dataFilename = dataFilename;
		this.labelFilename = labelFilename;
	}

	public String getDataFilename() {
		return dataFilename;
	}

	public String getLabelFilename() {
		return labelFilename;
	}

}
