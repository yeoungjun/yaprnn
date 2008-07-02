package yaprnn.gui;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import yaprnn.dvv.NoSuchFileException;

class SaveMLPAction extends AbstractAction {

	private static final long serialVersionUID = -8667106085248179171L;

	private GUI gui;

	SaveMLPAction(GUI gui) {
		this.gui = gui;
		gui.getView().getMenuSaveMLP().addActionListener(this);
		gui.getView().getToolSaveMLP().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser chooser = new JFileChooser();
		chooser.setMultiSelectionEnabled(false);
		chooser.setFileFilter(GUI.FILEFILTER_MLP);
		if (chooser.showSaveDialog(gui.getView()) == JFileChooser.APPROVE_OPTION) {
			try {
				// TODO : Ausgewähltes MLP speichern.
				gui.getCore().saveMLP(chooser.getSelectedFile().getPath());
			} catch (NoSuchFileException ex) {
				JOptionPane.showMessageDialog(gui.getView(),
						"An error occured",
						"The file you have choosen is invalid.\n"
								+ ex.getMessage(), JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}
