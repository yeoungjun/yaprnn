package yaprnn.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

class ToolImportActionListener implements ActionListener {

	private GUI gui;

	ToolImportActionListener(GUI gui) {
		this.gui = gui;
		gui.getView().getToolImport().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton toolImport = gui.getView().getToolImport();
		gui.getView().getPopupImport().show(toolImport, 0,
				toolImport.getHeight());
	}

}
