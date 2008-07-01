package yaprnn.gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

class ToolSearchForFileActionListener implements ActionListener {

	private Component parent;
	private JTextField target;
	private FileFilter filter;

	ToolSearchForFileActionListener(Component parent, JTextField target,
			FileFilter filter) {
		this.parent = parent;
		this.target = target;
		this.filter = filter;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser chooser = new JFileChooser();
		chooser.setMultiSelectionEnabled(false);
		chooser.setFileFilter(filter);
		if (chooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION)
			target.setText(chooser.getSelectedFile().getPath());
	}

}
