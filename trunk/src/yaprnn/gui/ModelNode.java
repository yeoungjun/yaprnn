package yaprnn.gui;

import javax.swing.Icon;

/**
 * Base node class for all nodes in NetworkTreeModel
 */
class ModelNode {

	private Icon icon;
	private String label;

	ModelNode(Icon icon, String label) {
		this.icon = icon;
		this.label = label;
	}

	int getIndexOf(ModelNode child) {
		return -1;
	}

	ModelNode getChild(int index) {
		return null;
	}

	int getChildsCount() {
		return 0;
	}

	/**
	 * Returns false when this node can have childs, else true.
	 */
	boolean isLeaf() {
		return false;
	}

	final Icon getIcon() {
		return icon;
	}

	final void setIcon(Icon icon) {
		this.icon = icon;
	}
	
	@Override
	public String toString() {
		return label;
	}

}
