package yaprnn.gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

class ImportImagesActionListener implements ActionListener {

	private GUI gui;

	ImportImagesActionListener(GUI gui) {
		this.gui = gui;
		gui.getView().getMenuImportImages().addActionListener(this);
		gui.getView().getMenuImportImages2().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String labelsPKG = null;
		String imagesPKG = null;

		// Für die Eingabe-Schleife.
		boolean notSatisfied;

		// Input Dialog vorbereiten.
		JTextField optionLabelsPKG = new JTextField();
		JButton toolSearchLabelsPKG = new JButton("...");
		toolSearchLabelsPKG
				.addActionListener(new ToolSearchForFileActionListener(gui
						.getView(), optionLabelsPKG, GUI.FILEFILTER_LBLPKG));
		JTextField optionImagesPKG = new JTextField();
		JButton toolSearchImagesPKG = new JButton("...");
		toolSearchImagesPKG
				.addActionListener(new ToolSearchForFileActionListener(gui
						.getView(), optionImagesPKG, GUI.FILEFILTER_IMGPKG));
		JPanel innerPanel1 = new JPanel(new GridLayout(1, 2));
		innerPanel1.add(optionLabelsPKG);
		innerPanel1.add(toolSearchLabelsPKG);
		JPanel innerPanel2 = new JPanel(new GridLayout(1, 2));
		innerPanel2.add(optionImagesPKG);
		innerPanel2.add(toolSearchImagesPKG);
		JPanel panel = new JPanel(new GridLayout(4, 1));
		panel.add(new JLabel("Please select the labels package:"));
		panel.add(innerPanel1);
		panel.add(new JLabel("Please select the images package:"));
		panel.add(innerPanel2);

		// Parameter anfragen
		notSatisfied = true;
		while (notSatisfied) {
			int ret = JOptionPane.showConfirmDialog(gui.getView(), panel,
					"Import images", JOptionPane.OK_CANCEL_OPTION);
			if (ret == JOptionPane.CANCEL_OPTION)
				return;
			try {
				labelsPKG = optionLabelsPKG.getText();
				imagesPKG = optionImagesPKG.getText();
				File labelsFile = new File(labelsPKG);
				File imagesFile = new File(imagesPKG);
				if (labelsFile.exists() && imagesFile.exists())
					notSatisfied = false;
				if (labelsFile.exists())
					optionLabelsPKG.setBackground(new Color(255, 128, 128));
				else
					optionLabelsPKG.setBackground(SystemColor.text);
				if (labelsFile.exists())
					optionImagesPKG.setBackground(new Color(255, 128, 128));
				else
					optionImagesPKG.setBackground(SystemColor.text);
			} catch (Exception ex) {
			}
		}

		// Importieren
		try {
			gui.getCore().openIdxPicture(imagesPKG, labelsPKG);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(gui.getView(), "Import failed!\n"
					+ ex.toString() + "\n" + ex.getStackTrace(),
					"An error occured", JOptionPane.ERROR_MESSAGE);
		}

	}
}
