package yaprnn.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class MenuAddAction implements ActionListener {

	private GUI gui;

	MenuAddAction(GUI gui) {
		this.gui = gui;
		setEnabled(false);
		gui.getView().getMenuAdd().addActionListener(this);
	}
	
	void setEnabled(boolean enabled) {
		gui.getView().getMenuAdd().setEnabled(enabled);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}

}