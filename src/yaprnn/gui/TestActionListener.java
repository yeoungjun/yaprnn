package yaprnn.gui;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

class TestActionListener implements java.awt.event.ActionListener {

	public TestActionListener() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog((Component) e.getSource(), "Test");
	}

}
