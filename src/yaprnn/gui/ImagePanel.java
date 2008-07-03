package yaprnn.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

/**
 * ImagePanel displays an image on a panel. It supports scrolling by being added
 * into a scrollpane.
 */
public class ImagePanel extends JPanel {

	private static final long serialVersionUID = 6823314791815950876L;

	private Image image;
	private Dimension size = new Dimension();

	public ImagePanel() {
		super();
	}

	public ImagePanel(Image image) {
		super();
		setImage(image);
	}

	public void setImage(Image image) {
		Dimension old = new Dimension(size);
		this.image = image;
		size.setSize(image.getWidth(this), image.getHeight(this));
		firePropertyChange("preferredSize", old, size);
		revalidate();
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (image != null) {
			g.clearRect(0, 0, getWidth(), getHeight());
			int x = (getWidth() - size.width) / 2;
			int y = (getHeight() - size.height) / 2;
			g.drawImage(image, x, y, this);
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return size;
	}

}