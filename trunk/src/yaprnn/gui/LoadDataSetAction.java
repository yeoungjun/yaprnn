package yaprnn.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

class LoadDataSetAction implements ActionListener {

	private GUI gui;

	LoadDataSetAction(GUI gui) {
		this.gui = gui;
		setEnabled(false);
		gui.getView().getMenuLoadDataSet().addActionListener(this);
		gui.getView().getToolLoadDataSet().addActionListener(this);

		// TODO: Das Laden eines Datasets ist noch nicht implementiert und
		// die entsprechenden Menus werden erstmal versteckt.
		gui.getView().getMenuLoadDataSet().setVisible(false);
		gui.getView().getToolLoadDataSet().setVisible(false);
	}

	void setEnabled(boolean enabled) {
		gui.getView().getMenuLoadDataSet().setEnabled(enabled);
		gui.getView().getToolLoadDataSet().setEnabled(enabled);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser chooser = new JFileChooser();
		chooser.setMultiSelectionEnabled(false);
		chooser.setFileFilter(GUI.FILEFILTER_YDS);
		if (chooser.showOpenDialog(gui.getView()) == JFileChooser.APPROVE_OPTION)
			// TODO : Laderoutine für DataSets
			JOptionPane.showMessageDialog(null, chooser.getSelectedFile()
					.getPath());
	}

}
