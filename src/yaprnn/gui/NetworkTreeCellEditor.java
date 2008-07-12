package yaprnn.gui;

import java.util.EventObject;
import java.util.Vector;
import java.awt.Component;
import javax.swing.event.CellEditorListener;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.TreeCellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JTree;
import yaprnn.gui.NetworkTreeModel.AVFNode;
import yaprnn.gui.NetworkTreeModel.BiasNode;
import yaprnn.gui.NetworkTreeModel.ModelNode;
import yaprnn.gui.NetworkTreeModel.NetworkNode;
import yaprnn.gui.NetworkTreeModel.NeuronsNode;
import yaprnn.mlp.ActivationFunction;

/**
 * TreeCell editor to customize the structure of a neural network.
 */
class NetworkTreeCellEditor implements TreeCellEditor {

	/* Die durch getTreeCellEditorComponent selektierte Node */
	private ModelNode selected = null;

	// Die Editoren der jeweiligen Node-Typen
	private TreeCellEditor networkEditor;
	private TreeCellEditor biasEditor;
	private TreeCellEditor neuronsEditor;
	private TreeCellEditor avfEditor;

	// Wird benutzt für avfEditor
	private JTextField optionNetwork;
	private JTextField optionNeurons;
	private JComboBox optionAVF;
	private JTextField optionBias;

	NetworkTreeCellEditor(GUI gui) {
		JTree tree = gui.getView().getTreeNeuralNetwork();
		NetworkTreeRenderer ntr = gui.getTreeRenderer();

		// Editor-Components
		optionNetwork = new JTextField();
		optionNeurons = new JTextField();
		optionAVF = new JComboBox(new Vector<ActivationFunction>(gui.getCore()
				.getAllActivationFunctions()));
		optionAVF.setEditable(false);
		optionBias = new JTextField();

		// Editoren
		networkEditor = new DefaultTreeCellEditor(tree, ntr,
				new DefaultCellEditor(optionNetwork));
		neuronsEditor = new DefaultTreeCellEditor(tree, ntr,
				new DefaultCellEditor(optionNeurons));
		avfEditor = new DefaultTreeCellEditor(tree, ntr, new DefaultCellEditor(
				optionAVF));
		biasEditor = new DefaultTreeCellEditor(tree, ntr,
				new DefaultCellEditor(optionBias));

	}

	@Override
	public Component getTreeCellEditorComponent(JTree tree, Object value,
			boolean isSelected, boolean expanded, boolean leaf, int row) {
		if (!(value instanceof ModelNode)) {
			selected = null;
			return null;
		}

		Component comp = null;

		// Selektierte Node merken
		selected = (ModelNode) value;

		// Editor-Component auswählen
		if (isNetworkNode(selected)) {
			comp = networkEditor.getTreeCellEditorComponent(tree, value,
					isSelected, expanded, leaf, row);
			optionNetwork.setText(((NetworkNode) selected).getNetwork()
					.getName());
		}

		if (isNeuronsNode(selected)) {
			comp = neuronsEditor.getTreeCellEditorComponent(tree, value,
					isSelected, expanded, leaf, row);
			NeuronsNode nn = (NeuronsNode) selected;
			optionNeurons.setText(Integer.toString(nn.getNetwork()
					.getLayerSize(nn.getLayerIndex())));
		}

		if (isAVFNode(selected)) {
			comp = avfEditor.getTreeCellEditorComponent(tree, value,
					isSelected, expanded, leaf, row);
			AVFNode an = (AVFNode) selected;
			optionAVF.setSelectedItem(an.getNetwork().getActivationFunction(
					an.getLayerIndex()));
		}

		if (isBiasNode(selected)) {
			comp = biasEditor.getTreeCellEditorComponent(tree, value,
					isSelected, expanded, leaf, row);
			BiasNode bn = (BiasNode) selected;
			optionBias.setText(Double.toString(bn.getNetwork().getBias(
					bn.getLayerIndex())));
		}

		return comp;
	}

	@Override
	public Object getCellEditorValue() {
		if (isNetworkNode(selected))
			return networkEditor.getCellEditorValue();
		if (isNeuronsNode(selected))
			return neuronsEditor.getCellEditorValue();
		if (isAVFNode(selected))
			return avfEditor.getCellEditorValue();
		if (isBiasNode(selected))
			return biasEditor.getCellEditorValue();
		return null;
	}

	@Override
	public boolean isCellEditable(EventObject evt) {
		// Wenn evt == null, dann wurde das Editieren durch das PopupMenu Edit
		// aufgerufen! Nur dann erlauben wir das Editieren.
		return evt == null;
	}

	@Override
	public boolean shouldSelectCell(EventObject evt) {
		return true;
	}

	@Override
	public boolean stopCellEditing() {
		return networkEditor.stopCellEditing()
				|| neuronsEditor.stopCellEditing()
				|| avfEditor.stopCellEditing() || biasEditor.stopCellEditing();
	}

	@Override
	public void cancelCellEditing() {
		networkEditor.cancelCellEditing();
		neuronsEditor.cancelCellEditing();
		avfEditor.cancelCellEditing();
		biasEditor.cancelCellEditing();
	}

	@Override
	public void addCellEditorListener(CellEditorListener l) {
		networkEditor.addCellEditorListener(l);
		neuronsEditor.addCellEditorListener(l);
		avfEditor.addCellEditorListener(l);
		biasEditor.addCellEditorListener(l);
	}

	@Override
	public void removeCellEditorListener(CellEditorListener l) {
		networkEditor.removeCellEditorListener(l);
		neuronsEditor.removeCellEditorListener(l);
		avfEditor.removeCellEditorListener(l);
		biasEditor.removeCellEditorListener(l);
	}

	private static boolean isNetworkNode(ModelNode n) {
		return n instanceof NetworkNode;
	}

	private static boolean isNeuronsNode(ModelNode n) {
		return n instanceof NeuronsNode;
	}

	private static boolean isAVFNode(ModelNode n) {
		return n instanceof AVFNode;
	}

	private static boolean isBiasNode(ModelNode n) {
		return n instanceof BiasNode;
	}

}
