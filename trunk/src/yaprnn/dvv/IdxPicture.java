package yaprnn.dvv;

import java.util.*;
import java.io.*;
import yaprnn.mlp.ActivationFunction;

/** IdxPicture is the class for holding a single image loaded from an IDX file.
 *  It provides funcionality for previewing raw and subsampled data and for
 *  subsampling and scaling the data.
 */
class IdxPicture extends Data {

	private static int DATA_MAGIC_NUMBER = 2051;
	private static int LABEL_MAGIC_NUMBER = 2049;

	private double[] data;
	private byte[][] rawData;
	private String label;
	private int target;
	private String filename;
	private int fileIndex;

	/** Constructs an IdxPicture object from the specified data.
	 *
	 *  @param rawData the image data
	 *  @param label   the classifying label
	 *  @param filename the file this picture was loaded from
	 *  @param fileIndex the index this picture had in the file
	 */
	public IdxPicture(byte[][] rawData, String label, String filename, int fileIndex) {
		this.rawData = rawData;
		this.label = label;
		this.filename = filename;
		this.fileIndex = fileIndex;
		target = Integer.parseInt(label);
	}

	/** Returns the completely preprocessed data of this image.
	 *
	 *  @return the preprocessed data
	 */
	public double[] getData() {
		return data;
	}

	/** Returns the raw data for previewing.
	 *
	 *  @return the raw data
	 */
	public byte[][] previewRawData() {
		return rawData;
	}

	/** Performs the subsampling and scaling of the data.
	 *
	 *  @param resoltion       the desired resolution
	 *  @param overlap         the overlap between adjacent windows in the range [0, 0.95]
	 *  @param scalingFunction the function used to scale the subsampled data
	 */
	public void subsample(int resolution, double overlap,
				ActivationFunction scalingFunction) {
		data = new double[rawData.length * rawData[0].length];
		for(int i=0; i<rawData.length; i++)
			for(int j=0; j<rawData[0].length; j++)
				data[i*rawData[0].length + j] =
						scalingFunction.compute(rawData[i][j]);
	}

	/** Returns the filename this image was read from.
	 *
	 *  @return the filename this object was read from
	 */
	public String getFilename() {
		return filename;
	}

	/** Returns the name of this image. The name is constructed from the filename
	 *  and the file index of this image.
	 *
	 *  @return the name of this image
	 */
	public String getName() {
		return filename + "_" + fileIndex;
	}

	/** Returns the target for this image.
	 *
	 *  @return the target for this image
	 */
	public int getTarget() {
		return target;
	}

	/** Returns the label for this image.
	 *
	 *  @return the label for this image
	 */
	public String getLabel() {
		return label;
	}

	/** Maps a target to the corresponding label. The result only depends on the argument.
	 *
	 *  @param target the target to be mapped to a label
	 *  @return the label corresponding to the specified target
	 */
	public String getLabelFromTarget(int target) {
		return "" + target;
	}

	/** Reads several images from the specified files and returns them as a collection.
	 *
	 *  @param dataFilename  the file holding the actual image data
	 *  @param labelFilename the file holding the data labels
	 *  @return a collection of the loaded images
	 */
	public static Collection<Data> readFromFile(String dataFilename, String labelFilename)
				throws InvalidFileException {
		//TODO: error handling
		try {
			DataInputStream dataInput = new DataInputStream(
					new FileInputStream(dataFilename));
			DataInputStream labelInput = new DataInputStream(
					new FileInputStream(labelFilename));	
			final int dataMagicNumber = dataInput.readInt();		
			if(dataMagicNumber != DATA_MAGIC_NUMBER)
				throw new InvalidFileException(dataFilename);
			final int labelMagicNumber = labelInput.readInt();		
			if(labelMagicNumber != LABEL_MAGIC_NUMBER)
				throw new InvalidFileException(labelFilename);
			final int numImagesData = dataInput.readInt();
			final int numImagesLabel = labelInput.readInt();
			if(numImagesData != numImagesLabel)
				throw new InvalidFileException(dataFilename);
			return readDataFromFile(dataInput, labelInput, numImagesData, dataFilename);
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static Collection<Data> readDataFromFile(DataInputStream dataInput,
				DataInputStream labelInput, int numImages, String dataFilename) {
		//TODO: error handling
		Collection<Data> result = new ArrayList<Data>(numImages);
		try {
			final int numRows = dataInput.readInt();
			final int numCols = dataInput.readInt();
			for(int i=0; i<numImages; i++) {
				byte[][] image = new byte[numRows][numCols];
				for(int j=0; j<numRows; j++)
					dataInput.read(image[j]);
				String label = "" + labelInput.readByte();
				result.add(new IdxPicture(image, label, dataFilename, i));
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}
