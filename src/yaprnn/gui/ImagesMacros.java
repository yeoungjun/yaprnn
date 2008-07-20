package yaprnn.gui;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import yaprnn.dvv.Data;

class ImagesMacros {

	private final static int RESIZE_MAXSIZE = 4096;

	/**
	 * Resizes an image.
	 * 
	 * @param image
	 * @param width
	 *            the new width
	 * @param height
	 *            the new height
	 * @param filterOp
	 *            one of the filter ops defined in AffineTransform
	 * @return the resized image
	 */
	static Image resizeImage(Image image, int width, int height, int filterOp) {
		if (image == null)
			return null;
		int ow = image.getWidth(null);
		int oh = image.getHeight(null);
		if (ow == 0 || oh == 0)
			return null;
		double rw = 1.0d / ow;
		double rh = 1.0d / oh;

		// Transform wird zur skalierung benoetigt.
		BufferedImage bi = new BufferedImage(ow, oh,
				BufferedImage.TYPE_INT_ARGB);
		bi.getGraphics().drawImage(image, 0, 0, ow, oh, null);
		AffineTransform tx = new AffineTransform();

		// Damit die Bilder auf eine maximale Groeße beschränkt sind, aber das
		// Seitenverhaeltnis behalten, suchen wir noch einen Zoom-Korrekturwert
		double correctionW = RESIZE_MAXSIZE / (double) width;
		double correctionH = RESIZE_MAXSIZE / (double) height;
		double zoomCorrection;
		zoomCorrection = correctionW < correctionH ? correctionW : correctionH;
		// Wir wollen nur einpassen wenn das Bild zu groß wird, nicht zu klein!
		if (zoomCorrection > 1.0)
			zoomCorrection = 1.0;

		tx.scale(width * rw * zoomCorrection, height * rh * zoomCorrection);
		Image ret = new AffineTransformOp(tx, filterOp).filter(bi, null);

		// Speicher des Zwischenobjektes freigeben
		bi.flush();

		return ret;
	}

	/**
	 * Loads an icon from a location and resizes it to a default size to fit the
	 * tree optic.
	 * 
	 * @param location
	 *            location to load from
	 * @return the icon
	 */
	static ImageIcon loadIcon(int hsize, int vsize, String location) {
		Image iconImageSrc = new ImageIcon(Class.class.getResource(location))
				.getImage();
		ImageIcon ret = new ImageIcon(ImagesMacros.resizeImage(iconImageSrc,
				hsize, vsize, AffineTransformOp.TYPE_BICUBIC));
		// Speicher der Bild-Quelle freigeben
		iconImageSrc.flush();
		return ret;
	}

	/**
	 * Creates a preview image of a Data-object.
	 * 
	 * @param data
	 *            the data object to create a preview from
	 * @param subsampled
	 *            if true the preview will be the subsampled one, else it will
	 *            be the original
	 * @param zoom
	 *            the zoom factor to be used
	 * @param resolution
	 *            subsampling parameter resolution, ignored if subsampled is
	 *            false
	 * @param overlap
	 *            subsampling parameter overlap, ignored if subsampled is false
	 * @return
	 */
	static Image createPreview(Data data, double zoom, boolean subsampled,
			int resolution, double overlap) {
		if (data == null)
			return null;

		// Zu starke Verkleinerung/grï¿½ï¿½erung ist nicht erlaubt
		double zoomVal = limit(zoom, 0.5, 100);

		Image image = null;

		if (data.isPicture()) {
			if (!subsampled)
				image = createImagePreview((byte[][]) data.previewRawData());
			else
				image = createImagePreview((byte[][]) data
						.previewSubsampledData(resolution, overlap));
		} else if (data.isAudio()) {
			if (!subsampled)
				image = createAudioPreview((double[]) data.previewRawData());
			else
				image = createAudioPreview((double[]) data
						.previewSubsampledData(resolution, overlap));
		}

		if (image == null)
			return null;

		Image resized = resizeImage(image,
				(int) (image.getWidth(null) * zoomVal), (int) (image
						.getHeight(null) * zoomVal),
				AffineTransformOp.TYPE_NEAREST_NEIGHBOR);

		// Speicher auf das Zwischenobjekt freigeben
		image.flush();

		return resized;
	}

	/**
	 * Creates a preview image of a weights matrix.
	 * 
	 * @param data
	 *            the data object to create a preview from
	 * @param zoom
	 *            the zoom factor to be used
	 * @param min
	 *            precalculated min value, used for scaling
	 * @param max
	 *            precalculated max value, used for scaling
	 * @param gamma
	 *            Gamma color modification
	 * @return
	 */
	static Image createWeightsImage(double[][] weights, double zoom,
			double min, double max, double gamma) {
		if (weights == null)
			return null;

		// Zu starke Verkleinerung/groesserung ist nicht erlaubt
		double zoomVal = limit(zoom, 0.5, 100);

		int height = weights.length, width = weights[0].length;
		double scale = 1.0d / (max - min);

		// Gammawert so skalieren, dass Werte unter oder gleich 0.5 einen
		// eigentlichen Gammawert zwischen 0 und 1 bedeuten. Und fuer Werte
		// goesser 0.5 auf setzen wir dass diese gegen einen undendlich grossen
		// Gamma-Wert laufen sollen.
		double gammaReal;
		if (gamma <= 0.5)
			gammaReal = gamma * 2;
		else
			// Wir quadrieren zur Verstärkung.
			gammaReal = 1 / Math.pow((1 - gamma) * 2, 2);

		// BufferdImage erstellen aus weights, Darstellung als Graustufen-Bild.
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_BYTE_GRAY);
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++) {
				boolean flipped = false;
				double color = (weights[y][x] - min) * scale;
				if (color > 0.5) {
					color = 1 - color;
					flipped = true;
				}

				// Gamma anwenden
				color = Math.pow(color * 2, gammaReal) * 0.5;
				if (flipped)
					color = 1 - color;

				// Graufarbenes Bild erstellen
				int pixelValue = (int) limit(color * 255.d, 0, 255);
				pixelValue = pixelValue | (pixelValue << 8)
						| (pixelValue << 16);

				image.setRGB(x, y, pixelValue);
			}

		// Dann noch zoomen
		Image resized = resizeImage(image, (int) (width * zoomVal),
				(int) (height * zoomVal),
				AffineTransformOp.TYPE_NEAREST_NEIGHBOR);

		// Speicher auf das Zwischenobjekt freigeben
		image.flush();

		return resized;
	}

	/**
	 * Creates a preview image from raw byte data of an image.
	 * 
	 * @param data
	 *            raw image data
	 * @return a BufferedImage of the raw data
	 */
	private static Image createImagePreview(byte[][] data) {
		if (data == null)
			return null;

		int height = data.length, width = data[0].length;

		// BufferdImage erstellen aus data, data stellt ein Graustufen-Bild da.
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_BYTE_GRAY);
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++) {
				// Graufarbenes Bild erstellen
				int pixelValue = uByteToInt(data[y][x]);
				pixelValue = pixelValue | (pixelValue << 8)
						| (pixelValue << 16);
				image.setRGB(x, y, pixelValue);
			}

		return image;
	}

	/**
	 * Creates a preview image from audio data.
	 * 
	 * @param data
	 *            raw audio data
	 * @return a BufferedImage of the raw data
	 */
	private static Image createAudioPreview(double[] data) {
		if (data == null)
			return null;
		final int HEIGHT = 255;
		final int EDGE = 1;
		int width = data.length;
		double max = 0.0;
		BufferedImage image = new BufferedImage(width + 2 * EDGE, HEIGHT + 2
				* EDGE, BufferedImage.TYPE_INT_RGB);
		// Skalierung bestimmen
		for (int i = 0; i < width; i++)
			if (data[i] > max)
				max = data[i];
		double divisor = max / HEIGHT;
		// BufferedImage is initialized with 0x000000 (black) for all pixels
		for (int x = EDGE; x < width + EDGE; x++)
			for (int y = HEIGHT + EDGE - (int) (data[x - EDGE] / divisor); y <= HEIGHT
					+ EDGE; y++)
				image.setRGB(x, y, 16765440 - (int) Math.round((256 - y + EDGE)
						/ (256.0 / 210.0)) * 256);

		return image;
	}

	/**
	 * Limits a double value to a range
	 * 
	 * @param value
	 *            the double value to be limited
	 * @param min
	 *            minimum of the range
	 * @param max
	 *            maximum of the range
	 */
	private static double limit(double value, double min, double max) {
		return ((value > min ? value : min) < max) ? value : max;
	}

	/**
	 * Helper to convert the signed byte to a positive integer.
	 * 
	 * @param b
	 * @return
	 */
	private static int uByteToInt(byte b) {
		final int i = b;
		return i >= 0 ? i : 128 + (i & 0x7F);
	}

}
