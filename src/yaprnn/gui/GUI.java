package yaprnn.gui;

import java.util.Collection;
import java.util.List;
import java.awt.EventQueue;
import yaprnn.Core;
import yaprnn.GUIInterface;
import yaprnn.dvv.Data;
import yaprnn.gui.view.MainView;

public class GUI implements GUIInterface {

	private Core core;
	private MainView mainView = new MainView();
	private NetworkTreeModel treeModel = new NetworkTreeModel();

	/**
	 * @param mv
	 *            the main view to be used
	 */
	public GUI(Core core) {
		this.core = core;
		this.core.setGUI(this);

		// TreeModel einsetzen
		mainView.getTreeNeuralNetwork().setModel(treeModel);
		mainView.getTreeNeuralNetwork()
				.setCellRenderer(treeModel.getRenderer());

		// EventHandler hinzufügen
		new MenuExitActionListener(this);
		new ToolImportActionListener(this);

		// Das Anzeigen der View sollte verzögert geschehen.
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				mainView.setVisible(true);
			}
		});

	}

	/**
	 * @return the MainView
	 */
	MainView getView() {
		return mainView;
	}

	@Override
	public void setDataSet(Collection<Data> dataset) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setTestError(List<Double> errorData) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setTrainingError(List<Double> errorData) {
		// TODO Auto-generated method stub
	}

}
