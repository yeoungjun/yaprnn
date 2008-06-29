package yaprnn.gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

class MenuExitActionListener implements ActionListener {

	MenuExitActionListener(GUI gui) {
		gui.getView().getMenuExit().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (JOptionPane.showConfirmDialog((Component) e.getSource(),
				"Do you really want to exit?", "Exit",
				JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION)
			System.exit(0);
	}

}
