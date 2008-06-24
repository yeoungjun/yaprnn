package yaprnn.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import yaprnn.gui.view.MainView;

public class MenuExitActionListener implements ActionListener {

	private MainView mainView;

	MenuExitActionListener(MainView mainView) {
		this.mainView = mainView;
		mainView.getMenuExit().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		mainView.setVisible(false);
		mainView.dispose();
		// TODO : Need a safer shutdown method here!
	}

}
