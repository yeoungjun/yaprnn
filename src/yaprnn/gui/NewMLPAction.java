package yaprnn.gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import yaprnn.mlp.BadConfigException;
import yaprnn.mlp.NeuralNetwork;

class NewMLPAction implements ActionListener {

	private final static int DEFAULT_NUMLAYERS = 1;
	private final static int DEFAULT_NUMNEURONS = 2;
	private final static double DEFAULT_BIAS = 1;
	private final static int DEFAULT_ACTIVATIONFUNCTION = 0;
	private final static boolean DEFAULT_AUTOENCODING = false;
	private static int counter = 1;

	private GUI gui;

	NewMLPAction(GUI gui) {
		this.gui = gui;
		setEnabled(false);
		gui.getView().getMenuNewMLP().addActionListener(this);
		gui.getView().getToolNewMLP().addActionListener(this);
	}

	void setEnabled(boolean enabled) {
		gui.getView().getMenuNewMLP().setEnabled(enabled);
		gui.getView().getToolNewMLP().setEnabled(enabled);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String name = "Network " + counter++;
		int numLayers = 0, numNeurons = 0;
		double bias = 0;

		// F�r die Eingabe-Schleife.
		boolean notSatisfied;

		// Input Dialog vorbereiten.
		JPanel panel = new JPanel(new GridLayout(9, 1));
		JTextField optionName = new JTextField(name);
		JTextField optionNumLayers = new JTextField(Integer
				.toString(DEFAULT_NUMLAYERS));
		JTextField optionNumNeurons = new JTextField(Integer
				.toString(DEFAULT_NUMNEURONS));
		JTextField optionBias = new JTextField(Double.toString(DEFAULT_BIAS));
		JCheckBox optionAutoEncoding = new JCheckBox(
				"Use auto encoding initialization", DEFAULT_AUTOENCODING);
		KeyListener onlyDigits = new OnlyNumbersKeyAdapter();
		optionNumLayers.addKeyListener(onlyDigits);
		optionNumNeurons.addKeyListener(onlyDigits);
		optionBias.addKeyListener(onlyDigits);
		optionAutoEncoding.setSelected(false);
		panel.add(new JLabel("Name"));
		panel.add(optionName);
		panel.add(new JLabel(
				"How many layers do you want? (value must be greater then 0)"));
		panel.add(optionNumLayers);
		panel
				.add(new JLabel(
						"How many neurons per Layer do you want? (value must be greater then 0)"));
		panel.add(optionNumNeurons);
		panel.add(new JLabel("Bias (recommended value between -1 and 1)"));
		panel.add(optionBias);
		panel.add(optionAutoEncoding);

		// Parameter anfragen
		notSatisfied = true;
		while (notSatisfied) {
			int ret = JOptionPane.showConfirmDialog(gui.getView(), panel,
					"New MLP", JOptionPane.OK_CANCEL_OPTION);
			if (ret == JOptionPane.CANCEL_OPTION)
				return;
			try {
				name = optionName.getText();
				numLayers = Integer.parseInt(optionNumLayers.getText());
				numNeurons = Integer.parseInt(optionNumNeurons.getText());
				bias = Double.parseDouble(optionBias.getText());
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(gui.getView(),
						"Please enter valid values only.", "Parsing error",
						JOptionPane.ERROR_MESSAGE);
			}
			if (numLayers > 0 && numNeurons > 0 && name.length() > 0)
				notSatisfied = false;
			else {
				// Die Felder mit ungueltigen Eingaben werden mit hellem rot
				// unterlegt.
				if (numLayers <= 0)
					optionNumLayers.setBackground(new Color(255, 128, 128));
				else
					optionNumLayers.setBackground(SystemColor.text);
				if (numNeurons <= 0)
					optionNumNeurons.setBackground(new Color(255, 128, 128));
				else
					optionNumNeurons.setBackground(SystemColor.text);
				if (name == null || name.length() == 0)
					optionName.setBackground(new Color(255, 128, 128));
				else
					optionName.setBackground(SystemColor.text);
			}
		}

		// Parameter ausfuellen
		int[] layer = new int[numLayers];
		int[] avf = new int[numLayers + 2];
		double[] biases = new double[numLayers];
		for (int i = 0; i < numLayers; i++) {
			// Annahme von Standardwerten
			layer[i] = numNeurons;
			biases[i] = bias;
			avf[i] = DEFAULT_ACTIVATIONFUNCTION;
		}
		avf[numLayers] = 0;
		avf[numLayers + 1] = 0;

		// MLP erstellen
		try {
			NeuralNetwork mlp = gui.getCore().newMLP(name, layer, avf, biases,
					optionAutoEncoding.isSelected());
			gui.getTreeModel().add(mlp);
		} catch (BadConfigException err) {
			JOptionPane.showMessageDialog(gui.getView(), err.getMessage(),
					"NewMLP: Error occured", JOptionPane.ERROR_MESSAGE);
		}
	}
}
