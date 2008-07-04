package yaprnn.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class MenuChooseRandomTrainingTestSetAction implements ActionListener {

	private GUI gui;

	MenuChooseRandomTrainingTestSetAction(GUI gui) {
		this.gui = gui;
		setEnabled(false);
		gui.getView().getMenuChooseRandomTrainingTestData().addActionListener(
				this);
	}

	void setEnabled(boolean enabled) {
		gui.getView().getMenuChooseRandomTrainingTestData().setEnabled(enabled);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
