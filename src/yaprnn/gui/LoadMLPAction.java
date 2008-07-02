package yaprnn.gui;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import yaprnn.dvv.NoSuchFileException;

class LoadMLPAction extends AbstractAction {

	private static final long serialVersionUID = 5796346954151719099L;

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
			} catch (NoSuchFileException ex) {
				JOptionPane.showMessageDialog(gui.getView(),
						"An error occured",
						"The file you have choosen is invalid.\n"
								+ ex.getMessage(), JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}
