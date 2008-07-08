package yaprnn.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
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
import yaprnn.mlp.NeuralNetwork;

class MenuTrainAction implements ActionListener {

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
	 * This worker invokes the training method to not block the awt dispatcher
	 * thread.
	 */
	private class TrainingWorker extends SwingWorker<Object, Object> {

		private TrainingInfo ti;
		double learningRate;
		double maxError;
		int maxIterations;
		boolean onlineLearning;

		TrainingWorker(TrainingInfo ti, double learningRate, int maxIterations,
				double maxError, boolean onlineLearning) {
			this.ti = ti;
			this.learningRate = learningRate;
			this.maxError = maxError;
			this.maxIterations = maxIterations;
			this.onlineLearning = onlineLearning;
		}

		@Override
		protected Object doInBackground() throws Exception {
			ti.tv.getToolTrain().setEnabled(false);
			ti.tv.getToolStop().setEnabled(true);

			if (onlineLearning) {
				System.out.println("online");
				ti.gui.getCore().trainOnline(learningRate, maxIterations,
						maxError, 0.99, 20, 0.8);
			} else {
				System.out.println("batch");
				ti.gui.getCore().trainBatch(learningRate, maxIterations,
						maxError, 0);
			}

			return null;
		}

		@Override
		protected void done() {
			ti.tw = null;
			ti.tv.getToolTrain().setEnabled(true);
			ti.tv.getToolStop().setEnabled(false);
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
			ti.testError.clear();
			ti.trainingError.clear();

			ti.tw = new TrainingWorker(
					ti,
					((Double) ti.tv.getOptionLearningRate().getValue())
							.doubleValue(),
					((Integer) ti.tv.getOptionMaxIterations().getValue())
							.intValue(),
					((Double) ti.tv.getOptionMaxError().getValue())
							.doubleValue(),
					ti.tv.getOptionTrainingMethod().getSelectedItem() instanceof OnlineTraining);
			ti.tw.execute();
		}

	}

	private class StopAction implements ActionListener {

		private TrainingInfo ti;

		StopAction(TrainingInfo ti) {
			this.ti = ti;
			ti.tv.getToolStop().addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO : Dieser Weg das Trainieren abzubrechen funktioniert nicht!
			if (ti.tw != null)
				ti.tw.cancel(true);
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
			// Kein Netzwerk ausgew�hlt
			return;

		if (ti != null) {
			// Wir k�nnen zurzeit nur ein MLP trainieren
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

		// ChartPanel hinzuf�gen
		ti.tv.getGraphPanel().add(cp, BorderLayout.CENTER);
		ti.tv.getGraphPanel().validate();

		new TrainAction(ti);
		new StopAction(ti);
		new TrainingWindowListener(ti);
		ti.tv.getOptionTrainingMethod().setModel(
				new DefaultComboBoxModel(new Object[] { new OnlineTraining(),
						new BatchTraining() }));
		ti.tv.getOptionTrainingMethod().setEditable(false);

		ti.tv.setTitle("Training: " + ti.network.getName());

		ti.tv.setVisible(true);
	}

	static void setTestError(List<Double> errorData) {
		Double val = errorData.get(errorData.size() - 1);
		ti.testError.add(errorData.size(), val);
	}

	static void setTrainingError(List<Double> errorData) {
		Double val = errorData.get(errorData.size() - 1);
		ti.trainingError.add(errorData.size(), val);
	}

}
