package yaprnn.dvv;

import java.util.*;
import java.io.*;
import javax.sound.sampled.*;
import yaprnn.mlp.ActivationFunction;
import edu.emory.mathcs.jtransforms.fft.*;

/** AiffMSound is the class for holding a single soundfile.
 *  It provides funcionality for previewing raw and subsampled data and for
 *  subsampling and scaling the data.
 */
class AiffSound extends Data {

	private double[] data;
	private byte[] rawData;
	private String label;
	private int target;
	private String filename;

	/** Constructs an AiffSound object from the specified data.
	 *
	 *  @param rawData the sound data
	 *  @param label   the classifying label
	 *  @param filename the file this object was loaded from
	 */
	public AiffSound(byte[] rawData, String label, String filename) {
		this.rawData = rawData;
		this.label = label;
		this.filename = filename;
		//TODO: Target erstellen (was ist gewünscht?)
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
	public byte[] previewRawData() {
		return rawData;
	}

	/** Returns the filename this sound was read from.
	 *
	 *  @return the filename this object was read from
	 */
	public String getFilename() {
		return filename;
	}
	
	/** Returns the name of this sound.
	 *
	 *  @return the name of this object
	 */
	public String getName() {
		return filename;
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

	
	
	/** Performs the subsampling and scaling of the data.
	 *
	 *  @param resoltion       the desired resolution
	 *  @param overlap         the overlap between adjacent windows in the range [0, 0.95]
	 *  @param scalingFunction the function used to scale the subsampled data
	 */
	public void subsample(int resolution, double overlap,
				ActivationFunction scalingFunction) {
		convertByteToDouble();
		/*for (int i = 0; i < 40; i++)
			System.out.print(data[i] + "|");
		System.out.println(); */
		oneMorePowerOfTwo();
		/*for (int i = 0; i < 40; i++)
			System.out.print(data[i] + "|");
		System.out.println();*/
		edu.emory.mathcs.jtransforms.fft.DoubleFFT_1D fft = new edu.emory.mathcs.jtransforms.fft.DoubleFFT_1D(data.length/2);
		fft.realForwardFull(data);
		/*for (int i = 0; i < 40; i++)
			System.out.print(data[i] + "|");
		System.out.println();*/
		calcAbsolutValue();
		/*for (int i = 0; i < 40; i++)
			System.out.print(data[i] + "|");*/
		}

	/** Reads several Sounds from the specified files and returns them as a collection.
	 *
	 *  @param dataFilename  the collection holding the filenames
	 *  @return a collection of the loaded sounds
	 */
	 
	 public static Collection<Data> readFromFile(Collection<String> filenames)
			throws InvalidFileException {
				//TODO: error handling
				try {
			Collection<Data> result = new ArrayList<Data>(filenames.size());
			for (String name : filenames){
				AudioInputStream audioInput = AudioSystem.getAudioInputStream(new File(name));
				int frameSize = audioInput.getFormat().getFrameSize();
				long frameLenght = audioInput.getFrameLength();
				byte[] data = new byte[frameSize * (int)frameLenght];
				int read = audioInput.read(data); //read wird nicht gebraucht, aber verlangt.
				String filename = name.substring(name.lastIndexOf("/")+1); //es handelt sich hierbei um eine Pfadangabe z.B. /home/bla/a3-08.aiff
				String label = filename.substring(0, 1); //label ist der erste Buchstabe von filename
				result.add(new AiffSound(data, label, filename));
			}	return result;
			} catch(IOException e) {
				e.printStackTrace();
			}
			  catch(UnsupportedAudioFileException e) {
				e.printStackTrace();
			  }
				return null;
		}
	
	/** Converts a bytearray into a doublearray
	 *
	 *  @param data  the bytearray
	 *  @return doubledata doublearray
	 */
	  
	 private void convertByteToDouble(){
		 data = new double[rawData.length/2];
		 int accumulator1;
		 int accumulator2;
		 for (int i=0; i<rawData.length; i = i+2){
			 accumulator1 = 0;
			 accumulator2 = 0;
			 accumulator1 = (accumulator1 | rawData[i]);
			 accumulator1 = (accumulator1 << 8);
			 accumulator2 = (rawData[i+1] & 0xff);
			 accumulator1 = (accumulator1 | accumulator2);
			 data[i/2] = accumulator1;
		 }
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
		double[] newdata = new double[data.length/2];
		for (int i = 0; i < data.length; i +=2)
			newdata[i/2] = java.lang.Math.sqrt(data[i]*data[i] + data[i+1]*data[i+1]);
		data = newdata;
	}
	
}
