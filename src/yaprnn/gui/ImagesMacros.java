package yaprnn.gui;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

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
	 * Creates an preview image from raw byte data of an image.
	 * 
	 * @param data
	 *            raw image data
	 * @param zoom
	 *            magnification zoom value
	 * @return a BufferedImage of the raw data
	 */
	static Image createImagePreview(byte[][] data, double zoom) {
		int height = data.length, width = data[0].length;

		// Zu starke Verkleinerung/größerung ist nicht erlaubt
		double zoomVal = limit(zoom, 0.5, 100);

		// BufferdImage erstellen aus data, data stellt ein Graustufen-Bild da.
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_BYTE_GRAY);
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++) {
				// Graufarbenes Bild erstellen
				int pixelValue = uByteToInt(data[y][x]);
				pixelValue = pixelValue | (pixelValue << 8) | (pixelValue << 16);
				image.setRGB(x, y, pixelValue);
			}

		return resizeImage(image, (int) (width * zoomVal),
				(int) (height * zoomVal),
				AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
	}

	/**
	 * Creates an preview image from audio data.
	 * 
	 * @param data
	 *            raw audio data
	 * @param zoom
	 *            magnification zoom value
	 * @return a BufferedImage of the raw data
	 */
	static Image createAudioPreview(double[] data, double zoom) {
		// Zu starke Verkleinerung/größerung ist nicht erlaubt
		double zoomVal = limit(zoom, 0.5, 2);

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

	private static int uByteToInt(byte b) {
		final int i = (int)b;
		return i >= 0 ? i : 128 + (i & 0x7F);
	}

}
