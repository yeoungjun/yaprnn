package yaprnn.gui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

class MenuTrainAction extends AbstractAction {

	private static final long serialVersionUID = 2689238883447005165L;

	private GUI gui;

	MenuTrainAction(GUI gui) {
		this.gui = gui;
		gui.getView().getMenuTrain().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO train action

	}

}
