package yaprnn.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import yaprnn.dvv.NoSuchFileException;

class LoadMLPActionListener implements ActionListener {

	private GUI gui;

	LoadMLPActionListener(GUI gui) {
		this.gui = gui;
		gui.getView().getMenuLoadMLP().addActionListener(this);
		gui.getView().getToolLoadMLP().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Datei öffnen Dialog
		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(new FileNameExtensionFilter("Neural network",
				"mlp"));
		if (chooser.showOpenDialog(gui.getView()) == JFileChooser.APPROVE_OPTION) {
			try {
				// MLP laden und zum TreeModel hinzufügen.
				gui.getTreeModel().add(
						gui.getCore().loadMLP(
								chooser.getSelectedFile().getPath()));
			} catch (NoSuchFileException ex) {
				JOptionPane.showMessageDialog(gui.getView(),
						"An error occured",
						"The file you have choosen is invalid.\n"
								+ ex.getMessage(), JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}
