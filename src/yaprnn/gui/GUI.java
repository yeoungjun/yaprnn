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
	private MainView mainView;

	/**
	 * Constructor
	 * 
	 * @param mv
	 *            the main view to be used
	 */
	public GUI(Core core, MainView mv) {
		mainView = mv;
		connectMainView();
		mainView.setVisible(true);
		core.setGUI(this);
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
		new MenuExitActionListener(mainView);

		// Add button listeners
		new ToolImportActionListener(mainView);

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
