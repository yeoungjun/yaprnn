package yaprnn.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class MenuEditAction implements ActionListener {

	private GUI gui;

	MenuEditAction(GUI gui) {
		this.gui = gui;
		setEnabled(false);
		gui.getView().getMenuEdit().addActionListener(this);
	}
	
	void setEnabled(boolean enabled) {
		gui.getView().getMenuEdit().setEnabled(enabled);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}

}
