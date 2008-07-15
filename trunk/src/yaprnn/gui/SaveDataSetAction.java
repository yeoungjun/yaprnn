package yaprnn.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

class SaveDataSetAction implements ActionListener {

	private GUI gui;

	SaveDataSetAction(GUI gui) {
		this.gui = gui;
		setEnabled(false);
		gui.getView().getMenuSaveDataSet().addActionListener(this);
		gui.getView().getToolSaveDataSet().addActionListener(this);
	}

	void setEnabled(boolean enabled) {
		gui.getView().getMenuSaveDataSet().setEnabled(enabled);
		gui.getView().getToolSaveDataSet().setEnabled(enabled);
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
