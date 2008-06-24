package yaprnn.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import yaprnn.gui.view.MainView;

class ToolImportActionListener implements ActionListener {

	private MainView mainView;

	ToolImportActionListener(MainView mainView) {
		this.mainView = mainView;
		mainView.getToolImport().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton toolImport = mainView.getToolImport();
		mainView.getPopupImport().show(toolImport, 0, toolImport.getHeight());
	}

}
