package yaprnn.gui;

import java.awt.Component;
import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 * NetworkTreeRenderer is used to display the correct icons for the nodes in
 * treeNeuralNetwork.
 */
class NetworkTreeRenderer extends DefaultTreeCellRenderer {

	private static final long serialVersionUID = -4438149908755280690L;

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {

		// Das ist nötig um die Knoten-Labels anzeigen zu lassen!
		super.getTreeCellRendererComponent(tree, value, sel, expanded,
				leaf, row, hasFocus);

		Icon icon = null;

		// Nimm das Icon des ModelNode.
		if (value instanceof ModelNode)
			icon = ((ModelNode) value).getIcon();

		if (icon != null)
			setIcon(icon);
		else {
			if (expanded)
				setIcon(NetworkTreeModel.ICON_OPENED);
			else
				setIcon(NetworkTreeModel.ICON_CLOSED);
		}

		return this;
	}
	
}
