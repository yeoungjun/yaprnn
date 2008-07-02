package yaprnn.gui;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Collection;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

class ImportAudioAction extends AbstractAction {

	private static final long serialVersionUID = 1988236914859516756L;

	private GUI gui;

	ImportAudioAction(GUI gui) {
		this.gui = gui;
		gui.getView().getMenuImportAudio().addActionListener(this);
		gui.getView().getToolImportAudio().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(GUI.FILEFILTER_AIFF);
		chooser.setMultiSelectionEnabled(true);
		if (chooser.showOpenDialog(gui.getView()) == JFileChooser.APPROVE_OPTION) {
			Collection<String> filenames = new Vector<String>();
			for (File f : chooser.getSelectedFiles())
				filenames.add(f.getPath());
			try {
				gui.getCore().openAiffSound(filenames);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(gui.getView(), "Import failed!\n"
						+ ex.toString() + "\n" + ex.getStackTrace(),
						"An error occured", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}
