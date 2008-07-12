package yaprnn.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Collection;
import java.util.Vector;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import yaprnn.dvv.InvalidFileException;
import yaprnn.dvv.NoSuchFileException;

class ImportAudioAction implements ActionListener {

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
			
			// Vorher versuchen etwas Speicher frei zu machen
			GUI.tryFreeMemory();
			
			try {
				gui.getCore().openAiffSound(filenames);
			} catch (InvalidFileException ex) {
				JOptionPane.showMessageDialog(gui.getView(), "Import failed!\n"
						+ "Unsupported audio file format in" + "\n" + ex.getFilename(), //ex.getStackTrace(),
						"An error occured", JOptionPane.ERROR_MESSAGE);
			}
			  catch (NoSuchFileException ex) {
				JOptionPane.showMessageDialog(gui.getView(), "Import failed!\n"
						+ "This file has not been found" + "\n" + ex.getFilename(), //ex.getStackTrace(),
						"An error occured", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}
