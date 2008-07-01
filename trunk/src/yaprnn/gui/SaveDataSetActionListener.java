package yaprnn.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

class SaveDataSetActionListener implements ActionListener {

	private GUI gui;

	SaveDataSetActionListener(GUI gui) {
		this.gui = gui;
		gui.getView().getMenuSaveDataSet().addActionListener(this);
		gui.getView().getToolSaveDataSet().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser chooser = new JFileChooser();
		chooser.setMultiSelectionEnabled(false);
		chooser.setFileFilter(GUI.FILEFILTER_YDS);
		if (chooser.showSaveDialog(gui.getView()) == JFileChooser.APPROVE_OPTION) {
			// TODO : Speicherroutine für DataSets
			JOptionPane.showMessageDialog(null, chooser.getSelectedFile()
					.getPath());
		}
	}

}
