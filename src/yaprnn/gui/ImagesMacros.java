package yaprnn.gui;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

class ImagesMacros {

	/**
	 * Resizes an image.
	 * 
	 * @param image
	 * @param width
	 * @param height
	 * @return the resized image
	 */
	static Image resizeImage(Image image, int width, int height) {
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
		return new AffineTransformOp(tx, AffineTransformOp.TYPE_BICUBIC)
				.filter(bi, null);
	}

	/**
	 * Creates an BufferedImage from raw byte data.
	 * @param data raw image data
	 * @return a BufferedImage of the raw data 
	 */
	static Image createImage(byte[][] data) {
		// TODO : createImage
		return null;
	}
	
}
