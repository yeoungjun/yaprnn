package yaprnn.gui;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

import yaprnn.dvv.Data;

class ImagesMacros {

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
		// Transform wird zur skalierung benötigt.
		BufferedImage bi = new BufferedImage(ow, oh,
				BufferedImage.TYPE_INT_ARGB);
		bi.getGraphics().drawImage(image, 0, 0, ow, oh, null);
		AffineTransform tx = new AffineTransform();
		tx.scale(width / (double) ow, height / (double) oh);
		return new AffineTransformOp(tx, filterOp).filter(bi, null);
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
		return new ImageIcon(ImagesMacros.resizeImage(new ImageIcon(Class.class
				.getResource(location)).getImage(), hsize, vsize,
				AffineTransformOp.TYPE_BICUBIC));
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
	 * @return
	 */
	static Image createPreview(Data data, double zoom, boolean subsampled,
			int resolution, double overlap) {
		if (data == null)
			return null;

		// Zu starke Verkleinerung/größerung ist nicht erlaubt
		double zoomVal = limit(zoom, 0.5, 100);

		Image image = null;

		if (data.isPicture()) {
			if (subsampled)
				image = createImagePreview((byte[][]) data.previewRawData());
			else
				image = createImagePreview((byte[][]) data
						.previewSubsampledData(resolution, overlap));
		} else if (data.isAudio()) {
//			if (subsampled)
//				image = createAudioPreview((double[]) data.previewRawData());
//			else
//				image = createAudioPreview((double[]) data
//						.previewSubsampledData(resolution, overlap));
		}

		if (image == null)
			return null;
		return resizeImage(image, (int) (image.getWidth(null) * zoomVal),
				(int) (image.getHeight(null) * zoomVal),
				AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
	}

	/**
	 * Creates a preview image from raw byte data of an image.
	 * 
	 * @param data
	 *            raw image data
	 * @return a BufferedImage of the raw data
	 */
	private static Image createImagePreview(byte[][] data) {
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
		// TODO : createAudioPreview
		return null;
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
		final int i = (int) b;
		return i >= 0 ? i : 128 + (i & 0x7F);
	}

}
