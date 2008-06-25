package yaprnn.gui;

import java.util.Collection;
import java.util.List;
import yaprnn.Core;
import yaprnn.GUIInterface;
import yaprnn.dvv.Data;
import yaprnn.gui.view.MainView;

/**
 * GUI controller
 */
public class GUI implements GUIInterface {

	private Core core;
	private MainView mainView = new MainView();

	/**
	 * Constructor
	 * 
	 * @param mv
	 *            the main view to be used
	 */
	public GUI(Core core) {
		this.core = core;
		connectMainView();
		mainView.setVisible(true);
		this.core.setGUI(this);
	}

	/**
	 * Returns the MainView of the GUI.
	 * 
	 * @return the MainView
	 */
	MainView getView() {
		return this.mainView;
	}

	/**
	 * Connects the event handler to the main view.
	 */
	private void connectMainView() {
		// Add menu listeners
		new MenuExitActionListener(this);

		// Add button listeners
		new ToolImportActionListener(this);

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
