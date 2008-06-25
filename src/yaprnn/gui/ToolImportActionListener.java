package yaprnn.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ToolImportActionListener implements ActionListener {

	private GUI gui;

	ToolImportActionListener(GUI gui) {
		this.gui = gui;
		gui.getView().getToolImport().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		gui.getView().getPopupImport().show(gui.getView().getToolImport(), 0,
				gui.getView().getToolImport().getHeight());
	}

}
