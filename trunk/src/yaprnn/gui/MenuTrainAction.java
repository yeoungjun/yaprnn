package yaprnn.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import yaprnn.gui.view.TrainingView;
import yaprnn.mlp.DynamicEtaAdjustment;
import yaprnn.mlp.Eta;
import yaprnn.mlp.NeuralNetwork;
import yaprnn.mlp.NoEtaAdjustment;
import yaprnn.mlp.StaticEtaAdjustment;

class MenuTrainAction implements ActionListener {

	private final static ImageIcon ICON_TRAIN = ImagesMacros.loadIcon(22, 22,
			"/yaprnn/gui/view/iconTraining.png");
	private final static ImageIcon ICON_STOP = ImagesMacros.loadIcon(22, 22,
			"/yaprnn/gui/view/iconStop.png");

	/**
	 * Used to hold required parameters and view objects.
	 */
	private class TrainingInfo {

		GUI gui;
		TrainingView tv;
		NeuralNetwork network;
		TrainingWorker tw = null;

		// JFreeChart Einbindung, Messpunkte
		XYSeries trainingError = new XYSeries("Training error");
		XYSeries testError = new XYSeries("Test error");

		TrainingInfo(GUI gui, TrainingView tv, NeuralNetwork network) {
			this.gui = gui;
			this.tv = tv;
			this.network = network;
		}

	}

	private abstract class TrainingMethod {
	}

	private class OnlineTraining extends TrainingMethod {
		@Override
		public String toString() {
			return "Online";
		}
	}

	private class BatchTraining extends TrainingMethod {
		@Override
		public String toString() {
			return "Batch";
		}
	}

	private class TrainAction implements ActionListener {

		private TrainingInfo ti;

		TrainAction(TrainingInfo ti) {
			this.ti = ti;
			ti.tv.getToolTrain().addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			ti.tv.getToolTrain().setEnabled(false);
			if (ti.tw != null)
				ti.gui.getCore().stopLearning();
			else {
				// Messpunkte loeschen
				ti.testError.clear();
				ti.trainingError.clear();

				// Trainieren in einem Background-Worker arbeiten lassen
				ti.tw = new TrainingWorker(
						ti,
						((Double) ti.tv.getOptionLearningRate().getValue())
								.doubleValue(),
						((Integer) ti.tv.getOptionMaxIterations().getValue())
								.intValue(),
						((Double) ti.tv.getOptionMaxError().getValue())
								.doubleValue(),
						ti.tv.getOptionTrainingMethod().getSelectedItem() instanceof OnlineTraining,
						ti.tv.getOptionUseMomentum().isSelected(),
						((Double) ti.tv.getOptionMomentum().getValue())
								.doubleValue(),
						ti.tv.getOptionModifyLearningrate().isSelected(),
						ti.tv.getOptionDynamicAdjustment().isSelected(),
						((Double) ti.tv.getOptionDynamicReductionfactor()
								.getValue()).doubleValue(),
						((Double) ti.tv.getOptionDynamicMultiplier().getValue())
								.doubleValue(),
						((Double) ti.tv.getOptionStaticReductionfactor()
								.getValue()).doubleValue(),
						((Integer) ti.tv.getOptionStaticIterations().getValue())
								.intValue());
				ti.tw.execute();
			}
		}
	}

	/**
	 * This worker invokes the training method to not block the awt dispatcher
	 * thread.
	 */
	private class TrainingWorker extends SwingWorker<Object, Object> {

		private TrainingInfo ti;
		double maxError;
		int maxIterations;
		boolean onlineLearning;
		double learningRate;
		boolean useMomentum;
		double momentum;
		boolean modifyLearningrate;
		boolean dynamicAdjustment;
		double dynamicReductionFactor;
		double dynamicMultiplier;
		double staticReductionFactor;
		int staticIterations;

		TrainingWorker(TrainingInfo ti, double learningRate, int maxIterations,
				double maxError, boolean onlineLearning, boolean useMomentum,
				double momentum, boolean modifyLearningrate,
				boolean dynamicAdjustment, double dynamicReductionFactor,
				double dynamicMultiplier, double staticReductionFactor,
				int staticIterations) {
			this.ti = ti;
			this.learningRate = learningRate;
			this.maxError = maxError;
			this.maxIterations = maxIterations;
			this.onlineLearning = onlineLearning;
			this.useMomentum = useMomentum;
			this.momentum = momentum;
			this.modifyLearningrate = modifyLearningrate;
			this.dynamicAdjustment = dynamicAdjustment;
			this.dynamicReductionFactor = dynamicReductionFactor;
			this.dynamicMultiplier = dynamicMultiplier;
			this.staticReductionFactor = staticReductionFactor;
			this.staticIterations = staticIterations;
		}

		@Override
		protected Object doInBackground() {
			ti.tv.getToolTrain().setIcon(ICON_STOP);
			ti.tv.getToolTrain().setText("Stop");
			ti.tv.getToolTrain().setEnabled(true);

			// Momentum auswaehlen
			double momentum = 0.0;
			if (useMomentum)
				momentum = this.momentum;

			Eta eta = null; 
			
			if(modifyLearningrate) {
				if (dynamicAdjustment)
					eta = new DynamicEtaAdjustment(learningRate, dynamicReductionFactor, dynamicMultiplier);
				else
					eta = new StaticEtaAdjustment(learningRate, staticReductionFactor, staticIterations);
			} else
				eta = new NoEtaAdjustment(learningRate);
					
			
			if(onlineLearning)
				ti.gui.getCore().trainOnline(eta, maxIterations,maxError, momentum);
			else
				ti.gui.getCore().trainBatch(eta, maxIterations,maxError, 20, momentum); //TODO: 20 ist batchSize und muss aus der GUI kommen!
			return null;
		}

		@Override
		protected void done() {
			ti.tw = null;
			ti.tv.getToolTrain().setIcon(ICON_TRAIN);
			ti.tv.getToolTrain().setText("Train");
			ti.tv.getToolTrain().setEnabled(true);
		}

	}

	private class TrainingWindowListener implements WindowListener {

		private TrainingInfo ti;

		TrainingWindowListener(TrainingInfo ti) {
			this.ti = ti;
			ti.tv.addWindowListener(this);
		}

		@Override
		public void windowActivated(WindowEvent e) {
		}

		@Override
		public void windowClosed(WindowEvent e) {
			ti = null;
		}

		@Override
		public void windowClosing(WindowEvent e) {
			if (ti.tw == null) {
				((JFrame) e.getSource()).dispose();
				// Siehe Kommentar bei Definition MenuTrainAction.ti.
				MenuTrainAction.ti = null;
			}
		}

		@Override
		public void windowDeactivated(WindowEvent e) {
		}

		@Override
		public void windowDeiconified(WindowEvent e) {
		}

		@Override
		public void windowIconified(WindowEvent e) {
		}

		@Override
		public void windowOpened(WindowEvent e) {
		}

	}

	/**
	 * Handles the enabling states of certain sub options, which are only
	 * usefull when the corresponding enabling option is selected.
	 */
	private class OptionItemChange implements ItemListener {

		private TrainingInfo ti;

		OptionItemChange(TrainingInfo ti) {
			this.ti = ti;
			ti.tv.getOptionUseMomentum().addItemListener(this);
			ti.tv.getOptionModifyLearningrate().addItemListener(this);
			ti.tv.getOptionDynamicAdjustment().addItemListener(this);
			ti.tv.getOptionStaticAdjustment().addItemListener(this);
		}

		@Override
		public void itemStateChanged(ItemEvent e) {
			Object src = e.getSource();
			boolean enable = e.getStateChange() == ItemEvent.SELECTED;

			// Aktiviere Momentum-Optionen, wenn optionUseMomentum selektiert
			// ist
			if (src == ti.tv.getOptionUseMomentum())
				ti.tv.getPreferencesTabs().setEnabledAt(2, enable);

			// Aktiviere Learningrate-Optionen, wenn optionModifyLearningrate
			// selektiert ist
			if (src == ti.tv.getOptionModifyLearningrate())
				ti.tv.getPreferencesTabs().setEnabledAt(1, enable);

			// Aktiviere ...Adjustment-Optionen, wenn
			// option...Adjustment selektiert ist
			if (src == ti.tv.getOptionDynamicAdjustment()) {
				ti.tv.getOptionDynamicMultiplier().setEnabled(enable);
				ti.tv.getOptionDynamicReductionfactor().setEnabled(enable);
			}
			if (src == ti.tv.getOptionStaticAdjustment()) {
				ti.tv.getOptionStaticIterations().setEnabled(enable);
				ti.tv.getOptionStaticReductionfactor().setEnabled(enable);
			}

		}

	}

	// TODO : Zur zeit kann nur ein Netzwerk trainiert werden.
	// private static Dictionary<NeuralNetwork, TrainingInfo> trainingInfos =
	// new Hashtable<NeuralNetwork, TrainingInfo>();
	private static TrainingInfo ti = null;
	private GUI gui;

	MenuTrainAction(GUI gui) {
		this.gui = gui;
		setEnabled(false);
		gui.getView().getMenuTrain().addActionListener(this);
	}

	void setEnabled(boolean enabled) {
		gui.getView().getMenuTrain().setEnabled(enabled);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (gui.getSelectedNetwork() == null)
			// Kein Netzwerk ausgewaehlt
			return;

		if (ti != null) {
			// Wir koennen zurzeit nur ein MLP trainieren
			JOptionPane
					.showMessageDialog(
							gui.getView(),
							"A training is already in progress. This version doesn't support more than one training window up at a time.",
							"Training", JOptionPane.ERROR_MESSAGE);
			return;
		}

		ti = new TrainingInfo(gui, new TrainingView(), gui.getSelectedNetwork());

		// Das JFreeChart zur Visualisierung erstellen
		XYSeriesCollection xyDataset = new XYSeriesCollection();
		xyDataset.addSeries(ti.trainingError);
		xyDataset.addSeries(ti.testError);
		JFreeChart chart = ChartFactory.createXYLineChart(
				"Training statistics", "Index", "Error value", xyDataset,
				PlotOrientation.VERTICAL, true, false, false);
		ChartPanel cp = new ChartPanel(chart);
		cp.setMouseZoomable(true, true);
		ti.tv.getGraphPanel().add(cp, BorderLayout.CENTER);
		ti.tv.getGraphPanel().validate();

		// Listener hinzufuegen
		new TrainAction(ti);
		new TrainingWindowListener(ti);
		new OptionItemChange(ti);

		// Einstellungen initialisieren
		ti.tv.getToolTrain().setIcon(ICON_TRAIN);
		ti.tv.getToolTrain().setText("Train");
		ti.tv.getOptionTrainingMethod().setModel(
				new DefaultComboBoxModel(new Object[] { new OnlineTraining(),
						new BatchTraining() }));
		ti.tv.getOptionTrainingMethod().setEditable(false);
		ti.tv.setTitle("Training: " + ti.network.getName());

		// Options init-aktivieren/deaktivieren
		ti.tv.getPreferencesTabs().setEnabledAt(1,
				ti.tv.getOptionModifyLearningrate().isSelected());
		ti.tv.getPreferencesTabs().setEnabledAt(2,
				ti.tv.getOptionUseMomentum().isSelected());
		ti.tv.getOptionDynamicMultiplier().setEnabled(
				ti.tv.getOptionDynamicAdjustment().isSelected());
		ti.tv.getOptionDynamicReductionfactor().setEnabled(
				ti.tv.getOptionDynamicAdjustment().isSelected());
		ti.tv.getOptionStaticIterations().setEnabled(
				ti.tv.getOptionStaticAdjustment().isSelected());
		ti.tv.getOptionStaticReductionfactor().setEnabled(
				ti.tv.getOptionStaticAdjustment().isSelected());

		ti.tv.setVisible(true);
	}

	static void setTestError(List<Double> errorData) {
		ti.testError.add(errorData.size(), errorData.get(errorData.size() - 1));
	}

	static void setTrainingError(List<Double> errorData) {
		ti.trainingError.add(errorData.size(), errorData
				.get(errorData.size() - 1));
	}

}
