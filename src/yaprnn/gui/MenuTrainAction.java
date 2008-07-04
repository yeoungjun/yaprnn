package yaprnn.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class MenuTrainAction implements ActionListener {

	private GUI gui;

	MenuTrainAction(GUI gui) {
		this.gui = gui;
		setEnabled(false);
		gui.getView().getMenuTrain().addActionListener(this);
	}

	void setEnabled(boolean enabled) {
		gui.getView().getMenuTrain().setEnabled(enabled);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO train action
	}

}
