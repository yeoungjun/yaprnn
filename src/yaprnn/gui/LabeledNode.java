package yaprnn.gui;

import javax.swing.ImageIcon;

/**
 * LabeledNode is used internally for giving a container of data a readable
 * label.
 * 
 * @param <D>
 *            Type of the value contained in this node.
 */
class LabeledNode<D> {

	private ImageIcon icon;
	private String label;
	private D value;

	LabeledNode(ImageIcon icon, String label, D value) {
		this.icon = icon;
		this.label = label;
		this.value = value;
	}

	D getValue() {
		return value;
	}

	ImageIcon getIcon() {
		return icon;
	}

	@Override
	public String toString() {
		return label;
	}

}
