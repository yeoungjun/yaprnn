package yaprnn.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
		boolean autoEncoder = false;

		// Für die Eingabe-Schleife.
		boolean notSatisfied;

		// Input Dialog vorbereiten.
		JLabel messageText = new JLabel();
		JTextField textField = new JTextField("2");
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Character.isDigit(e.getKeyChar()))
					e.consume();
			}
		});
		JPanel panel = new JPanel(new GridLayout(2, 1));
		panel.add(messageText);
		panel.add(textField);

		// Anzahl der Schichten?
		messageText
				.setText("How many layers do you want? (please input a value greater then 0)");
		notSatisfied = true;
		while (notSatisfied) {
			int ret = JOptionPane.showConfirmDialog(gui.getView(), panel,
					"New MLP", JOptionPane.OK_CANCEL_OPTION);
			if (ret == JOptionPane.CANCEL_OPTION)
				return;
			try {
				numLayers = Integer.parseInt(textField.getText());
				if (numLayers > 0)
					notSatisfied = false;
			} catch (Exception ex) {
			}
		}

		// Anzahl der Neuronen pro Schicht?
		messageText
				.setText("How many neurons per layer do you want? (please input a value greater then 0)");
		notSatisfied = true;
		while (notSatisfied) {
			int ret = JOptionPane.showConfirmDialog(gui.getView(), panel,
					"New MLP", JOptionPane.OK_CANCEL_OPTION);
			if (ret == JOptionPane.CANCEL_OPTION)
				return;
			try {
				numNeurons = Integer.parseInt(textField.getText());
				if (numNeurons > 0)
					notSatisfied = false;
			} catch (Exception ex) {
			}
		}

		// AutoEncoder?
		int ret = JOptionPane.showConfirmDialog(gui.getView(),
				"Do you want to apply auto encoding initialization?",
				"New MLP", JOptionPane.YES_NO_CANCEL_OPTION);
		if (ret == JOptionPane.CANCEL_OPTION)
			return;
		autoEncoder = (ret == JOptionPane.YES_OPTION);

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
		NeuralNetwork mlp = gui.getCore().newMLP(layer, avf, bias, autoEncoder);
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
