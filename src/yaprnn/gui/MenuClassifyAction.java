package yaprnn.gui;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

public class MenuClassifyAction extends AbstractAction {

	private static final long serialVersionUID = 5796346954151719099L;

	private GUI gui;

	MenuClassifyAction(GUI gui) {
		this.gui = gui;
		gui.getView().getMenuClassify().addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO : classify action
	}

}
