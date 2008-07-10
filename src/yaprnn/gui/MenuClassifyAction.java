package yaprnn.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import yaprnn.dvv.Data;
import yaprnn.gui.view.ClassifyView;
import yaprnn.mlp.NeuralNetwork;

class MenuClassifyAction implements ActionListener {

	/**
	 * Used to hold required parameters and view objects.
	 */
	private class ClassifyInfo {

		GUI gui;
		ClassifyView cv;
		Data data;
		NeuralNetwork network;
		double zoom = 1.0;

		ClassifyInfo(GUI gui, ClassifyView cv, Data data, NeuralNetwork network) {
			this.gui = gui;
			this.cv = cv;
			this.data = data;
			this.network = network;
		}

	}

	/**
	 * NetworkBox just packs a network to return the network's name through
	 * toString() to get it displayed correctly in in the combobox
	 * optionNetwork.
	 */
	private class NetworkBox {
		private NeuralNetwork network;

		NetworkBox(NeuralNetwork network) {
			this.network = network;
		}

		@Override
		public String toString() {
			return network.getName();
		}
	}

	private class ZoomAction implements ActionListener {

		private ClassifyInfo ci;

		ZoomAction(ClassifyInfo ci) {
			this.ci = ci;
			ci.cv.getOptionZoom().addActionListener(this);
			ci.cv.getOptionZoom().addKeyListener(new OnlyNumbersKeyAdapter());
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			Object item = ci.cv.getOptionZoom().getSelectedItem();
			if (item instanceof String) {
				try {
					ci.zoom = Double.parseDouble((String) item);
					MenuClassifyAction.createPreview(ci);
				} catch (Exception ex) {
					JOptionPane
							.showMessageDialog(
									ci.cv,
									"The value you have entered cannot be parsed to floating point value. Please enter a correct zoom value.",
									"Parsing error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}

	}

	private class ClassifyAction implements ActionListener {

		private ClassifyInfo ci;

		ClassifyAction(ClassifyInfo ci) {
			this.ci = ci;
			ci.cv.getToolClassify().addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// Symbole festlegen
			// TODO : sollte besser von Core.classify zurueckgegeben werden!
			String[] symbols = (ci.data.isAudio()) ? new String[] { "a", "e",
					"i", "o", "u" } : null;
			if (ci.data.isPicture())
				symbols = new String[] { "0", "1", "2", "3", "4", "5", "6",
						"7", "8", "9" };

			// Klassifizieren
			double[] out = ci.gui.getCore().classify(ci.data);

			// -1, da letzter Output Bias des Output-layers. (uninteressant)
			int rows = out.length - 1, cols = 2;

			// Werte auslesen
			Object[][] out2 = new Object[rows][cols];
			for (int y = 0; y < rows; y++) {
				out2[y][0] = symbols[y];
				out2[y][1] = Math.round(out[y] * 1000) / 1000d;
			}

			// Ins model packen
			ci.cv.getTableClassification().setModel(
					new DefaultTableModel(out2, new Object[] { "symbol",
							"value" }) {
						private static final long serialVersionUID = 7651494044161476471L;

						@Override
						public boolean isCellEditable(int row, int column) {
							// Wir machen das JTable nicht editierbar.
							return false;
						}
					});

		}

	}

	private GUI gui;

	MenuClassifyAction(GUI gui) {
		this.gui = gui;
		setEnabled(false);
		gui.getView().getMenuClassify().addActionListener(this);
	}

	void setEnabled(boolean enabled) {
		gui.getView().getMenuClassify().setEnabled(enabled);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Data data = gui.getSelectedData();
		if (data == null || gui.getTreeModel().getNetworks().isEmpty())
			return;

		NeuralNetwork network = null;

		if(gui.getTreeModel().getNetworks().size() > 1) {
			Vector<NetworkBox> boxes = new Vector<NetworkBox>();
			for (NeuralNetwork n : gui.getTreeModel().getNetworks())
				boxes.add(new NetworkBox(n));

			// Input Dialog vorbereiten.
			JPanel panel = new JPanel(new GridLayout(2, 1));
			JComboBox optionNetwork = new JComboBox(boxes);
			optionNetwork.setEditable(false);
			panel.add(new JLabel(
					"Please select the network with which you want to classify "
							+ data.getName()));
			panel.add(optionNetwork);
	
			// Parameter anfragen
			int ret = JOptionPane.showConfirmDialog(gui.getView(), panel,
					"Classify", JOptionPane.OK_CANCEL_OPTION);
			if (ret == JOptionPane.CANCEL_OPTION)
				return;
			if (optionNetwork.getSelectedItem() == null)
				return;
	
			network = ((NetworkBox) optionNetwork.getSelectedItem()).network;
		} else
			network = gui.getTreeModel().getNetworks().get(0);
		
		// Die View oeffnen
		ClassifyInfo ci = new ClassifyInfo(gui, new ClassifyView(), data,
				network);
		new ClassifyAction(ci);
		new ZoomAction(ci);

		MenuClassifyAction.createPreview(ci);

		ci.cv.setTitle("Classify: " + data.getName() + " by "
				+ network.getName());

		ci.cv.setVisible(true);

	}

	private static void createPreview(ClassifyInfo ci) {
		ci.cv.getLabelPreview().setImage(
				ImagesMacros.createPreview(ci.data, ci.zoom, false, 0, 0));
	}

}
