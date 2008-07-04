package yaprnn.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class MenuRemoveAction implements ActionListener {

	private GUI gui;

	MenuRemoveAction(GUI gui) {
		this.gui = gui;
		setEnabled(false);
		gui.getView().getMenuRemove().addActionListener(this);
	}
	
	void setEnabled(boolean enabled) {
		gui.getView().getMenuRemove().setEnabled(enabled);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
