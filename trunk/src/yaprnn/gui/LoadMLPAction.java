package yaprnn.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

class LoadMLPAction implements ActionListener {

	private GUI gui;

	LoadMLPAction(GUI gui) {
		this.gui = gui;
		gui.getView().getMenuLoadMLP().addActionListener(this);
		gui.getView().getToolLoadMLP().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser chooser = new JFileChooser();
		chooser.setMultiSelectionEnabled(false);
		chooser.setFileFilter(GUI.FILEFILTER_MLP);
		if (chooser.showOpenDialog(gui.getView()) == JFileChooser.APPROVE_OPTION) {
			try {
				// MLP laden und zum TreeModel hinzufügen.
				gui.getTreeModel().add(
						gui.getCore().loadMLP(
								chooser.getSelectedFile().getPath()));
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(gui.getView(),
						"An error occured",
						"An error occured while loading the mlp.\n"
								+ ex.getMessage(), JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}
