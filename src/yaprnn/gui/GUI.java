package yaprnn.gui;

import java.util.Collection;
import java.util.List;
import java.awt.EventQueue;
import javax.swing.filechooser.FileNameExtensionFilter;
import yaprnn.Core;
import yaprnn.GUIInterface;
import yaprnn.dvv.Data;
import yaprnn.gui.NetworkTreeModel.DataNode;
import yaprnn.gui.NetworkTreeModel.ModelNode;
import yaprnn.gui.view.MainView;

public class GUI implements GUIInterface {

	// Package Globs
	final static FileNameExtensionFilter FILEFILTER_YDS = new FileNameExtensionFilter(
			"YAPRNN DataSet", "yds");
	final static FileNameExtensionFilter FILEFILTER_MLP = new FileNameExtensionFilter(
			"Neural Network", "mlp");
	final static FileNameExtensionFilter FILEFILTER_AIFF = new FileNameExtensionFilter(
			"Audio files", "aiff");
	final static FileNameExtensionFilter FILEFILTER_IMGPKG = new FileNameExtensionFilter(
			"Image package", "idx3-ubyte");
	final static FileNameExtensionFilter FILEFILTER_LBLPKG = new FileNameExtensionFilter(
			"Label package", "idx1-ubyte");

	private Core core;
	private MainView mainView = new MainView();
	private NetworkTreeModel treeModel = new NetworkTreeModel();

	private ModelNode selected = null;

	// Den benutzen wir zum auslesen des Zoom-Wertes
	private double zoom = 1.0;
	private int resolution = 16;
	private double overlap = 0.5;

	/**
	 * @param core
	 *            the core controller
	 */
	public GUI(Core core) {
		this.core = core;
		this.core.setGUI(this);

		mainView.getTreeNeuralNetwork().setModel(treeModel);
		mainView.getTreeNeuralNetwork().setCellRenderer(
				new NetworkTreeRenderer());

		// EventHandler erstellen
		new NewMLPAction(this);
		new LoadMLPAction(this);
		new SaveMLPAction(this);
		new LoadDataSetAction(this);
		new SaveDataSetAction(this);
		new ImportAudioAction(this);
		new ImportImagesAction(this);
		new MenuExitAction(this);
		new MenuClassifyAction(this);
		new MenuTrainAction(this);
		new MenuSubsamplingAction(this);
		new MenuResetNetworkAction(this);
		new TreeNeuralNetworkSelection(this);
		new OptionZoomAction(this);
		new OptionResolutionChange(this);
		new OptionOverlapChange(this);

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

	ModelNode getSelected() {
		return selected;
	}

	void setSelected(ModelNode selected) {
		this.selected = selected;
		updateOnSelectedNode();
	}

	double getZoom() {
		return zoom;
	}

	void setZoom(double zoom) {
		this.zoom = zoom;
		updateOnSelectedNode();
	}

	int getResolution() {
		return resolution;
	}

	void setResolution(int resolution) {
		this.resolution = resolution;
		updateOnSelectedNode();
	}

	double getOverlap() {
		return overlap;
	}

	void setOverlap(double overlap) {
		this.overlap = overlap;
		updateOnSelectedNode();
	}

	/**
	 * Takes the appropriate action due to a change or update on a selected
	 * node.
	 */
	void updateOnSelectedNode() {
		if (selected instanceof DataNode) {
			// Preview erzeugen
			DataNode dataNode = (DataNode) selected;
			Data data = dataNode.getData();
			mainView.getLabelPreview().setImage(
					ImagesMacros.createImagePreview((byte[][]) data
							.previewRawData(), zoom));
			mainView.getLabelPreviewSubsampled().setImage(
					ImagesMacros.createImagePreview((byte[][]) data
							.previewSubsampledData(resolution, overlap), zoom));
		}
	}

	@Override
	public void setDataSet(Collection<Data> dataset) {
		for (Data d : dataset)
			treeModel.add(d);
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
