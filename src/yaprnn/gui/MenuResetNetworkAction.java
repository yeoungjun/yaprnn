package yaprnn.gui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

class MenuResetNetworkAction extends AbstractAction {

	private static final long serialVersionUID = 5796346954151719099L;

	private GUI gui;

	MenuResetNetworkAction(GUI gui) {
		this.gui = gui;
		gui.getView().getMenuResetNetwork().addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO : reset network action

	}

}
