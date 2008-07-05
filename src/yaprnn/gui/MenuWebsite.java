package yaprnn.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class MenuWebsite implements ActionListener {

	private GUI gui;

	MenuWebsite(GUI gui) {
		this.gui = gui;
		gui.getView().getMenuWebsite().addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}

}