package yaprnn.gui;

import java.util.Collection;
import java.util.List;
import java.util.Vector;

import yaprnn.Core;
import yaprnn.dvv.Data;
import yaprnn.gui.view.MainView;

/**
 * GUI-Controller von yaprnn.
 */
public class GUI {

	private Core core;
	private MainView mainView = null;
	private Collection<IGUIListener> listener = new Vector<IGUIListener>();

	/**
	 * Konstruktor
	 * 
	 * @param mv
	 *            Die zu verwaltende MainView.
	 */
	public GUI(Core core, MainView mv) {
		this.setView(mv);
		mv.setVisible(true);
	}

	/**
	 * Aktualisiert die Datenpunkte für den Graphen des Training-Fehlers.
	 * 
	 * @param errorData
	 *            Die Training-Fehler-Datenpunkte.
	 */
	public void setTrainingError(List<Double> errorData) {
		// TODO : GUI.setTrainingError
	}

	/**
	 * Aktualisiert die Datenpunkte für den Graphen des Test-Fehlers.
	 * 
	 * @param errorData
	 *            Die Test-Fehler-Datenpunkte.
	 */
	public void setTestError(List<Double> errorData) {
		// TODO : GUI.setTestError
	}

	/**
	 * Aktualisiert die Liste an verfügbaren DataSets in der MainView.
	 * 
	 * @param dataset
	 *            Das dataset.
	 */
	public void setDataSet(Collection<Data> dataset) {
		// TODO : GUI.setDataSet
	}

	/**
	 * Setzt die zu verwaltene MainView.
	 * 
	 * @param mainView
	 *            Die zu verwaltende MainView.
	 */
	public void setView(MainView mainView) {
		if (this.mainView != null)
			disconnectMainView();
		this.mainView = mainView;
		connectMainView();
	}

	/**
	 * Liefert die aktuell verwaltete MainView.
	 */
	public MainView getView() {
		return this.mainView;
	}

	/**
	 * Hängt die Eventhandler an eine MainView.
	 */
	private void connectMainView() {
		listener.add(new ToolImportActionListener(mainView));
	}

	/**
	 * Entfernt die Eventhandler von einer MainView.
	 */
	private void disconnectMainView() {
		for (IGUIListener l : listener)
			l.disconnect();
		listener.clear();
	}

}
