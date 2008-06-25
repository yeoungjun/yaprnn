package yaprnn.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class MenuExitActionListener implements ActionListener {

	private GUI gui;

	MenuExitActionListener(GUI gui) {
		this.gui = gui;
		gui.getView().getMenuExit().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		gui.getView().setVisible(false);
		gui.getView().dispose();
		// TODO : Need a safer shutdown method here!
	}

}
