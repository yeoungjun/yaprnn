package yaprnn.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;

class ImportAudioActionListener implements ActionListener {

	private GUI gui;

	ImportAudioActionListener(GUI gui) {
		this.gui = gui;
		gui.getView().getMenuImportAudio().addActionListener(this);
		gui.getView().getMenuImportAudio2().addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(GUI.FILEFILTER_AIFF);
		chooser.setMultiSelectionEnabled(true);
		if (chooser.showOpenDialog(gui.getView()) == JFileChooser.APPROVE_OPTION)
			for(File f : chooser.getSelectedFiles())
				f.canExecute();
				//TODO : Audiodateien laden
	}

}
