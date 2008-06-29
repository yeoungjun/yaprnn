package yaprnn.gui;

import java.util.Collection;
import java.util.List;
import java.awt.EventQueue;

import javax.swing.UIManager;

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

		// Look and Feel anpassen
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			// Nichts zu tun hier.
		}

		// TreeModel einsetzen
		mainView.getTreeNeuralNetwork().setModel(treeModel);
		mainView.getTreeNeuralNetwork()
				.setCellRenderer(treeModel.getRenderer());

		// EventHandler hinzufügen
		new NewMLPActionListener(this);
		new LoadMLPActionListener(this);
		new MenuExitActionListener(this);
		new ToolImportActionListener(this);

		// Das Anzeigen der View sollte verzögert geschehen.
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				mainView.setVisible(true);
			}
		});

	}

	MainView getView() {
		return mainView;
	}

	NetworkTreeModel getTreeModel() {
		return treeModel;
	}

	Core getCore() {
		return core;
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
