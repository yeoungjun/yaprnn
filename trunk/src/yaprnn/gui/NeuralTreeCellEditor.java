package yaprnn.gui;

import java.util.EventObject;
import java.awt.Component;
import java.awt.event.MouseEvent;
import javax.swing.event.CellEditorListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.CellEditor;

class NeuralTreeCellEditor implements TreeCellEditor {
	private CellEditor currentEditor;
	private DefaultCellEditor neuronEditor;
	private ActivationFunctionEditor avfEditor;

	public NeuralTreeCellEditor() {
		neuronEditor = new DefaultCellEditor(new JTextField());
		avfEditor = new ActivationFunctionEditor(new String[] { "tanh",
				"sigmoid", "linear" });
	}

	@Override
	public Component getTreeCellEditorComponent(JTree tree, Object value,
			boolean isSelected, boolean expanded, boolean leaf, int row) {

		DefaultMutableTreeNode tmp = (DefaultMutableTreeNode) value;
		currentEditor = neuronEditor;
		if (((String) tmp.getUserObject()).indexOf("Neurons") > -1) {
			currentEditor = neuronEditor;
		} else if (((String) tmp.getUserObject()).indexOf("AVF") > -1) {
			currentEditor = avfEditor;
		}
		return (Component) currentEditor;
	}

	@Override
	public Object getCellEditorValue() {
		return currentEditor.getCellEditorValue();
	}

	@Override
	public boolean isCellEditable(EventObject evt) {
		if (evt instanceof MouseEvent)
			if (((MouseEvent) evt).getClickCount() > 1)
				return true;
		return false;
	}

	@Override
	public boolean shouldSelectCell(EventObject evt) {
		return currentEditor.shouldSelectCell(evt);
	}

	@Override
	public boolean stopCellEditing() {
		return currentEditor.stopCellEditing();
	}

	@Override
	public void cancelCellEditing() {
		currentEditor.cancelCellEditing();
	}

	@Override
	public void addCellEditorListener(CellEditorListener l) {
		neuronEditor.addCellEditorListener(l);
		avfEditor.addCellEditorListener(l);
	}

	@Override
	public void removeCellEditorListener(CellEditorListener l) {
		neuronEditor.removeCellEditorListener(l);
		avfEditor.removeCellEditorListener(l);
	}

}
