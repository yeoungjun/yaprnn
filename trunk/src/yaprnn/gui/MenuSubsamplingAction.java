package yaprnn.gui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

class MenuSubsamplingAction extends AbstractAction {

	private static final long serialVersionUID = -5135923938075939994L;

	private GUI gui;

	MenuSubsamplingAction(GUI gui) {
		this.gui = gui;
		gui.getView().getMenuSubsampling().addActionListener(this);
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
