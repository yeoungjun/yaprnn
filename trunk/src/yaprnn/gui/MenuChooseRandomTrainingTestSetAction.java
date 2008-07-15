package yaprnn.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import yaprnn.dvv.Data;
import yaprnn.mlp.NeuralNetwork;

class MenuChooseRandomTrainingTestSetAction implements ActionListener {

	private GUI gui;

	MenuChooseRandomTrainingTestSetAction(GUI gui) {
		this.gui = gui;
		setEnabled(false);
		gui.getView().getMenuChooseRandomTrainingTestData().addActionListener(
				this);
	}

	void setEnabled(boolean enabled) {
		gui.getView().getMenuChooseRandomTrainingTestData().setEnabled(enabled);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		NeuralNetwork n = gui.getSelectedNetwork();
		if (n == null)
			return;
		int trainPerc = 75, testPerc = 20;

		boolean notSatisfied;

		// Input Dialog vorbereiten.
		JPanel panel = new JPanel(new GridLayout(4, 1));
		JSpinner optionTrainPerc = new JSpinner(new SpinnerNumberModel(70, 1,
				99, 1));
		JSpinner optionTestPerc = new JSpinner(new SpinnerNumberModel(20, 1,
				99, 1));
		panel.add(new JLabel("Trainingset size in percent"));
		panel.add(optionTrainPerc);
		panel.add(new JLabel("Testset size in percent"));
		panel.add(optionTestPerc);

		// Parameter anfragen
		notSatisfied = true;
		while (notSatisfied) {
			int ret = JOptionPane.showConfirmDialog(gui.getView(), panel,
					"Choosing random sets", JOptionPane.OK_CANCEL_OPTION);
			if (ret == JOptionPane.CANCEL_OPTION)
				return;
			trainPerc = ((Integer) optionTrainPerc.getValue()).intValue();
			testPerc = ((Integer) optionTestPerc.getValue()).intValue();
			if (trainPerc + testPerc <= 100)
				notSatisfied = false;
			else
				JOptionPane.showMessageDialog(gui.getView(),
						"The sum of both values cannot exceed 100 percent.",
						"Error", JOptionPane.ERROR_MESSAGE);
		}

		List<Data> dataList = gui.getTreeModel().getDatasets();

		// Zuerst setzen wir alle wieder auf NotUsed
		for (Data d : dataList) {
			gui.getTreeModel().remove(d, n);
			d.setNotUsed();
		}

		// Der Core wählt die Sets aus.
		gui.getCore().chooseRandomTrainingData(trainPerc / 100d,
				testPerc / 100d);

		// Wir schauen nur noch nach welche wo gelandet sind.
		for (Data d : gui.getTreeModel().getDatasets()) {
			if (d.isTraining()) {
				gui.getTreeModel().add(d, n, true);
			} else if (d.isTest()) {
				gui.getTreeModel().add(d, n, false);
			}
		}

	}

}
