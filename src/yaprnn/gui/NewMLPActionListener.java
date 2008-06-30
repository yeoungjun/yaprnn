package yaprnn.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import yaprnn.mlp.NeuralNetwork;

class NewMLPActionListener implements ActionListener {

	private GUI gui;

	NewMLPActionListener(GUI gui) {
		this.gui = gui;
		gui.getView().getMenuNewMLP().addActionListener(this);
		gui.getView().getToolNewMLP().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int numLayers = 0;
		int numNeurons = 0;

		// Für die Eingabe-Schleife.
		boolean notSatisfied;

		// Input Dialog vorbereiten.
		JTextField optionNumLayers = new JTextField("1");
		optionNumLayers.addKeyListener(new OnlyNumbersKeyAdapter());
		JTextField optionNumNeurons = new JTextField("2");
		optionNumLayers.addKeyListener(new OnlyNumbersKeyAdapter());
		JCheckBox optionAutoEncoding = new JCheckBox(
				"Use auto encoding initialization");
		optionAutoEncoding.setSelected(false);

		JPanel panel = new JPanel(new GridLayout(5, 1));
		panel.add(new JLabel(
				"How many layers do you want? (value must be greater then 0)"));
		panel.add(optionNumLayers);
		panel
				.add(new JLabel(
						"How many neurons per Layer do you want? (value must be greater then 0)"));
		panel.add(optionNumNeurons);
		panel.add(optionAutoEncoding);

		// Parameter anfragen
		notSatisfied = true;
		while (notSatisfied) {
			int ret = JOptionPane.showConfirmDialog(gui.getView(), panel,
					"New MLP", JOptionPane.OK_CANCEL_OPTION);
			if (ret == JOptionPane.CANCEL_OPTION)
				return;
			try {
				numLayers = Integer.parseInt(optionNumLayers.getText());
				numNeurons = Integer.parseInt(optionNumNeurons.getText());
				if (numLayers > 0 && numNeurons > 0)
					notSatisfied = false;
			} catch (Exception ex) {
			}
		}

		// Parameter ausfüllen
		int[] layer = new int[numLayers];
		int[] avf = new int[numLayers + 2];
		double[] bias = new double[numLayers];
		for (int i = 0; i < numLayers; i++) {
			// Annahme von Standardwerten
			layer[i] = 2;
			avf[i] = 0;
			bias[i] = 0.2;
		}
		avf[numLayers] = 0;
		avf[numLayers + 1] = 0;

		// MLP erstellen
		NeuralNetwork mlp = gui.getCore().newMLP(layer, avf, bias,
				optionAutoEncoding.isSelected());
		if (mlp == null)
			// TODO : Eine genauere Fehlerbeschreibung wäre toll hier
			JOptionPane.showMessageDialog(gui.getView(),
					"NewMLP: Error occured",
					"Something went wrong while creating the new mlp!",
					JOptionPane.ERROR_MESSAGE);
		else
			gui.getTreeModel().add(mlp);
	}
}
