package yaprnn.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class MenuClassifyAction implements ActionListener {

	private GUI gui;

	MenuClassifyAction(GUI gui) {
		this.gui = gui;
		setEnabled(false);
		gui.getView().getMenuClassify().addActionListener(this);
	}

	void setEnabled(boolean enabled) {
		gui.getView().getMenuClassify().setEnabled(enabled);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO : classify action
	}

}
