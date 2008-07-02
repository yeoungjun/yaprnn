package yaprnn.gui;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

class LoadDataSetAction extends AbstractAction {

	private static final long serialVersionUID = 8703727693067607283L;

	private GUI gui;

	LoadDataSetAction(GUI gui) {
		this.gui = gui;
		gui.getView().getMenuLoadDataSet().addActionListener(this);
		gui.getView().getToolLoadDataSet().addActionListener(this);
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
