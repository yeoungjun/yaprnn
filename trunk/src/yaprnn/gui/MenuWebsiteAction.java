package yaprnn.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class MenuWebsiteAction implements ActionListener {

	private GUI gui;

	MenuWebsiteAction(GUI gui) {
		this.gui = gui;
		gui.getView().getMenuWebsite().addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}

}