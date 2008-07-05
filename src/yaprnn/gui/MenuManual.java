package yaprnn.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class MenuManual implements ActionListener {

	private GUI gui;

	MenuManual(GUI gui) {
		this.gui = gui;
		gui.getView().getMenuManual().addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}

}