package yaprnn.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class MenuManualAction implements ActionListener {

	private GUI gui;

	MenuManualAction(GUI gui) {
		this.gui = gui;
		gui.getView().getMenuManual().addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}

}