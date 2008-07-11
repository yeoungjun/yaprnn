package yaprnn.dvv;

import java.util.*;
import java.io.*;
import javax.sound.sampled.*;
import yaprnn.mlp.ActivationFunction;



/** AiffSound is the class for holding a single soundfile.
 *  It provides funcionality for previewing raw and subsampled data and for
 *  subsampling and scaling the data.
 */
public class AiffSound extends Data {

	private double[] data;
	private short[] rawData;
	private String label;
	private int target;
	private String filename;

	/** Constructs an AiffSound object from the specified data.
	 *
	 *  @param rawData the sound data
	 *  @param label   the classifying label
	 *  @param filename the file this object was loaded from
	 */
	public AiffSound(short[] rawData, String label, String filename) {
		this.rawData = rawData;
		this.label = label;
		this.filename = filename;
		if (label.equalsIgnoreCase("a")) this.target = 0;
		if (label.equalsIgnoreCase("e")) this.target = 1;
		if (label.equalsIgnoreCase("i")) this.target = 2;
		if (label.equalsIgnoreCase("o")) this.target = 3;
		if (label.equalsIgnoreCase("u")) this.target = 4;
		this.data = new double[this.rawData.length];
		for (int i = 0; i < this.rawData.length; i++)
			this.data[i] = this.rawData[i];
	}

	/** Returns the completely preprocessed data of this sound.
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
	public Object previewRawData() {
		return rawData;
	}

	/** Returns the filename this sound was read from.
	 *
	 *  @return the filename this object was read from
	 */
	public String getFilename() {
		return filename.substring(filename.lastIndexOf("/")+1);
	}
	
	/** Returns the name of this sound.
	 *
	 *  @return the name of this object
	 */
	public String getName() {
		return new File(filename).getName();
	}
	
	/** Returns the target for this sound.
	 *
	 *  @return the target for this object
	 */
	public int getTarget() {
		return target;
	}
	
	/** Returns the label for this sound.
	 *
	 *  @return the label for this object
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

	/** Returns the data subsampled with the specified parameters (not yet scaled).
	 *
	 *  @param resolution      the desired resolution
	 *  @param overlap         the overlap between adjacent windows in the range [0, 0.95]
	 */
	public Object previewSubsampledData(int resolution, double overlap) {
		final double LAMBDA = 1.02;
		oneMorePowerOfTwo();
		edu.emory.mathcs.jtransforms.fft.DoubleFFT_1D fft = new edu.emory.mathcs.jtransforms.fft.DoubleFFT_1D(data.length/2);
		fft.realForwardFull(data);
		calcAbsolutValue();
		double[] newData = new double[resolution];
		double width = getFirstWidth(resolution, overlap, LAMBDA);
		double[] wArray = new double[resolution];
		double[] iArray = new double[resolution];
		double index=0.0;
		for (int i = 0; i < resolution; i++){
			wArray[i] = width;
			width = LAMBDA*width;
		}
		for (int i = 0; i< resolution; i++){
			iArray[i] = (index);
			index += (1-overlap)*wArray[i];
		}
		for (int i = 0; i<resolution; i++){
			newData[i] = middle(wArray[i], iArray[i], overlap); 
		}
		return newData;
	}

	
	
	
	/** Performs the subsampling and scaling of the data.
	 *
	 *  @param resoltion       the desired resolution
	 *  @param overlap         the overlap between adjacent windows in the range [0, 0.95]
	 *  @param scalingFunction the function used to scale the subsampled data
	 */
	public void subsample(int resolution, double overlap,
				ActivationFunction scalingFunction) {
		final double LAMBDA = 1.02;
		/*writeFile("/home/fisch/Uni/mpgi3/vokale/wave_fft/" + this.filename + "-wave.csv");*/
		oneMorePowerOfTwo();
		edu.emory.mathcs.jtransforms.fft.DoubleFFT_1D fft = new edu.emory.mathcs.jtransforms.fft.DoubleFFT_1D(data.length/2);
		fft.realForwardFull(data);
		calcAbsolutValue();
		/*writeFile("/home/fisch/Uni/mpgi3/vokale/wave_fft/" + this.filename + "-fft.csv");*/
		double[] newData = new double[resolution];
		double width = getFirstWidth(resolution, overlap, LAMBDA);
		double[] wArray = new double[resolution];
		double[] iArray = new double[resolution];
		double index=0.0;
		for (int i = 0; i < resolution; i++){
			wArray[i] = width;
			width = LAMBDA*width;
		}
		for (int i = 0; i< resolution; i++){
			iArray[i] = (index);
			index += (1-overlap)*wArray[i];
		}
		/*System.out.println(this.data.length);*/
		for (int i = 0; i<resolution; i++){
			newData[i] = middle(wArray[i], iArray[i], overlap); 
		}
		for (int i = 0; i < resolution; i++ )
			newData[i] = scalingFunction.compute(newData[i]/500000);
		this.data = newData;
		/*for (double data : this.data)
			System.out.println(data);*/
		}
	
	
	/** Calculates the average of a stated sequence from data
	 * 
	 * @param index the first element
	 * @param widht the width of the sequence
	 * @return the average 
	 */
	
	private double middle(double width, double index, double overlap){
		double average = 0;
		int leftIndex = (int)Math.round(index-(width*overlap));
		int rightIndex = (int)Math.round(leftIndex+width);
		/*System.out.println("w: " + width);
		System.out.println("i: " + index);
		System.out.println("l: " + leftIndex);
		System.out.println("r: " + rightIndex);*/
		if (leftIndex<0){
			for(int i = 0; i <= rightIndex;i++){
				average += this.data[i];
			}
			average = average/ (rightIndex-leftIndex);
			return average;
		}
		for(int i = leftIndex; i <= rightIndex;i++){
			average += this.data[i];
		}
		if (rightIndex-leftIndex == 0){
			return average;
		}
		average = average/ (rightIndex-leftIndex);
		return average;
	}

	/** Reads several Sounds from the specified files and returns them as a collection.
	 *
	 *  @param dataFilename  the collection holding the filenames
	 *  @return a collection of the loaded sounds
	 */
	 
	 public static Collection<Data> readFromFile(Collection<String> filenames)
			throws InvalidFileException, NoSuchFileException {
		 		AudioInputStream audioInput = null;
		 		File file = null;
			Collection<Data> result = new ArrayList<Data>(filenames.size());
			for (String name : filenames){
				file = new File(name);
				try {
					audioInput = AudioSystem.getAudioInputStream(file);
				}	catch(UnsupportedAudioFileException e) {
					throw new InvalidFileException(name);
				}   catch (IOException e) {
					throw new NoSuchFileException(name);
				}
				int frameSize = audioInput.getFormat().getFrameSize();
				long frameLenght = audioInput.getFrameLength();
				byte[] data = new byte[frameSize * (int)frameLenght];
				try {
					audioInput.read(data); //read wird nicht gebraucht, aber verlangt.
				}	catch (IOException e) {
					e.printStackTrace();
				}
				String filename = name; //.substring(name.lastIndexOf("/")+1); //es handelt sich hierbei um eine Pfadangabe z.B. /home/bla/a3-08.aiff
				String label = (new File(filename)).getName().substring(0, 1); //label ist der erste Buchstabe von filename
				result.add(new AiffSound(convertByteToShort(data), label, filename));
			}
			return result;
	 }
	
	/** Converts a bytearray into a doublearray
	 *
	 *  @param data  the bytearray
	 *  @return doubledata doublearray
	 */
	  
	 private static short[] convertByteToShort(byte[] rawData){
		 short[] newData = new short[rawData.length/2];
		 int accumulator1;
		 int accumulator2;
		 for (int i=0; i<rawData.length; i = i+2){
			 accumulator1 = 0;
			 accumulator2 = 0;
			 accumulator1 = (accumulator1 | rawData[i]);
			 accumulator1 = (accumulator1 << 8);
			 accumulator2 = (rawData[i+1] & 0xff);
			 accumulator1 = (accumulator1 | accumulator2);
			 newData[i/2] = (short)accumulator1;
		 }
		 return newData;
	 }


	 /** Checks if the length of data is a power of two. If not it reduces the array
	  *  by cutting some data from the front and the end of the array.
	  */
	 
	private void oneMorePowerOfTwo(){
		int length = data.length;
		int minPowerOfTwo = 1;
			for (int i = 1; i<=length; i*=2){
				if (i == length){ //schon eine 2er potenz
					double[] newdata = new double[i*2];
					for (int j = 0; j < i; j++) //schreibe alte daten in erte Hälfte
						newdata[j] = data[j];
					for (int k = i; k<i*2; k++) //fülle 2te Hälfte mit Nullen
						newdata[k] = 0.0;
					return;
				}
				minPowerOfTwo = i;
			}
		double[] newData = new double[minPowerOfTwo*2];
		for (int i = ((length-minPowerOfTwo)/2)-1; i<((length-minPowerOfTwo)/2)-1+minPowerOfTwo; i++){
			newData[i-(((length-minPowerOfTwo)/2)-1)] = data[i]; // werte ab (differenz zur kleineren 2er-potenz)/2
		}														 // vorn und hinten abgeschnitten vom stream
		for (int i = minPowerOfTwo; i<minPowerOfTwo*2; i++)
			newData[i] = 0.0;
		data = newData;
	}

	
	/** Calculates the abolute value from data with size n.
	 *  The physical layout of the input data has to be as follows:
 	 *	a[2*k] = Re[k], 
 	 *	a[2*k+1] = Im[k], 0<=k<n
 	 *	The new size of data i n/2
	 */
	
	private void calcAbsolutValue(){
		double[] newdata = new double[data.length/4];
		for (int i = 0; i < data.length/2; i +=2)
			newdata[i/2] = java.lang.Math.sqrt(data[i]*data[i] + data[i+1]*data[i+1]);
		data = newdata;
	}

	/** Is it a picture?
	 * @return Returns false.
	 */
	public boolean isPicture() {
		return false;
	}

	/** Is it an audio?
	 * @return Returns true.
	 */
	public boolean isAudio() {
		return true;
	}
	
	/** Calculates the first window width 
	 * 
	 * @param resolution the new resolution of data
	 * @param overlap the overlap of the windows
	 * @param lambda the increase of the window width
	 * @return the first window width
	 */
	private double getFirstWidth(int resolution, double overlap, double lambda){
		double temp=0;
		for (int i = 0; i<=resolution-2; i++)
			temp += Math.pow(lambda,i);
		double width = (this.data.length) / ((1-overlap)*temp + Math.pow(lambda,resolution-1));
		return width;
	}

	public String getPath() {
		return filename;
	}
}
