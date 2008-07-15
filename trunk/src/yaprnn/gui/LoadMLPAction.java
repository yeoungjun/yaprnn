package yaprnn.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import yaprnn.mlp.NeuralNetwork;

class LoadMLPAction implements ActionListener {

	private GUI gui;

	LoadMLPAction(GUI gui) {
		this.gui = gui;
		gui.getView().getMenuLoadMLP().addActionListener(this);
		gui.getView().getToolLoadMLP().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		NeuralNetwork n = null;
		JFileChooser chooser = new JFileChooser();
		chooser.setMultiSelectionEnabled(false);
		chooser.setFileFilter(GUI.FILEFILTER_MLP);
		if (chooser.showOpenDialog(gui.getView()) == JFileChooser.APPROVE_OPTION) {
			try {
				// MLP laden
				n = gui.getCore().loadMLP(chooser.getSelectedFile().getPath());
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(gui.getView(),
						"An error occured",
						"An error occured while loading the mlp.\n"
								+ ex.getMessage(), JOptionPane.ERROR_MESSAGE);
			}
		}

		if (n == null)
			return;

		// TODO : Da wir noch nicht mehrere MLPs unterstützen, löschen wir das
		// gerade stehende MLP aus dem Baum!
		if (gui.getTreeModel().getNetworks().size() > 0)
			gui.getTreeModel().remove(gui.getTreeModel().getNetworks().get(0));

		gui.getTreeModel().add(n);
	}

}
