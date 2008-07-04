package yaprnn.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import yaprnn.dvv.Data;
import yaprnn.gui.NetworkTreeModel.DataNode;
import yaprnn.gui.view.SubsamplingView;
import yaprnn.mlp.ActivationFunction;
import yaprnn.mlp.Linear;

/**
 * This eventhandler opens a new SubsamplingView that will ask for the
 * parameters and then calls the subsampling method in the core.
 */
class MenuSubsamplingAction implements ActionListener {

	/**
	 * Used to hold required parameters and view objects.
	 */
	private class SubsamplingInfo {

		GUI gui;
		SubsamplingView sv;
		Data previewData;
		double zoom = 1.0, overlap = 0.5;
		int resolution = 16;

		SubsamplingInfo(GUI gui, SubsamplingView sv, Data previewData) {
			this.gui = gui;
			this.sv = sv;
			this.previewData = previewData;
		}

	}

	private class OverlapChange implements ChangeListener {

		private SubsamplingInfo si;

		public OverlapChange(SubsamplingInfo si) {
			this.si = si;
			si.sv.getOptionOverlap().addChangeListener(this);
		}

		@Override
		public void stateChanged(ChangeEvent e) {
			si.overlap = ((Double) si.sv.getOptionOverlap().getValue())
					.doubleValue();
			MenuSubsamplingAction.createSubsampledPreview(si);
		}

	}

	private class ResolutionChange implements ChangeListener {

		private SubsamplingInfo si;

		public ResolutionChange(SubsamplingInfo si) {
			this.si = si;
			si.sv.getOptionResolution().addChangeListener(this);
		}

		@Override
		public void stateChanged(ChangeEvent e) {
			si.resolution = ((Integer) si.sv.getOptionResolution().getValue())
					.intValue();
			MenuSubsamplingAction.createSubsampledPreview(si);
		}

	}

	private class ZoomAction implements ActionListener {

		private SubsamplingInfo si;

		ZoomAction(SubsamplingInfo si) {
			this.si = si;
			si.sv.getOptionZoom().addActionListener(this);
			si.sv.getOptionZoom().addKeyListener(new OnlyNumbersKeyAdapter());
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			Object item = si.sv.getOptionZoom().getSelectedItem();
			if (item instanceof String) {
				try {
					si.zoom = Double.parseDouble((String) item);
					MenuSubsamplingAction.createSubsampledPreview(si);
				} catch (Exception ex) {
					JOptionPane
							.showMessageDialog(
									si.sv,
									"The value you have entered cannot be parsed to floating point value. Please enter a correct zoom value.",
									"Parsing error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}

	}

	private class ProcessAction implements ActionListener {

		private SubsamplingInfo si;

		ProcessAction(SubsamplingInfo si) {
			this.si = si;
			si.sv.getToolProcess().addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			si.sv.setVisible(false);
			si.sv.dispose();
			System.out.println("starten " + System.nanoTime());
			si.gui.getCore().preprocess(
					si.resolution,
					si.overlap,
					(ActivationFunction) si.sv.getOptionScaleFun()
							.getSelectedItem());
			System.out.println("fertig " + System.nanoTime());
		}

	}

	private GUI gui;

	MenuSubsamplingAction(GUI gui) {
		this.gui = gui;
		setEnabled(false);
		gui.getView().getMenuSubsampling().addActionListener(this);
	}

	void setEnabled(boolean enabled) {
		gui.getView().getMenuSubsampling().setEnabled(enabled);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Es wird eine Skalierungsfunktion benötigt, die lineare darf nicht
		// verwendet werden.
		Vector<ActivationFunction> scaleFuns = new Vector<ActivationFunction>(
				gui.getCore().getAllActivationFunctions());
		for (ActivationFunction f : scaleFuns)
			if (f instanceof Linear) {
				scaleFuns.remove(f);
				break;
			}

		Data data = null;
		if (gui.getSelected() instanceof DataNode)
			data = ((DataNode) gui.getSelected()).getData();

		SubsamplingInfo si = new SubsamplingInfo(gui, new SubsamplingView(),
				data);

		new OverlapChange(si);
		new ResolutionChange(si);
		new ZoomAction(si);
		new ProcessAction(si);

		si.sv.getOptionScaleFun().setModel(new DefaultComboBoxModel(scaleFuns));
		si.sv.getOptionScaleFun().setEditable(false);

		MenuSubsamplingAction.createSubsampledPreview(si);

		si.sv.setVisible(true);
	}

	private static void createSubsampledPreview(SubsamplingInfo si) {
		Data data = si.previewData;
		if (data == null)
			return;
		if (data.isPicture()) {
			si.sv.getLabelPreview().setImage(
					ImagesMacros.createImagePreview((byte[][]) data
							.previewRawData(), si.zoom));
			si.sv.getLabelPreviewSubsampled().setImage(
					ImagesMacros.createImagePreview((byte[][]) data
							.previewSubsampledData(si.resolution, si.overlap),
							si.zoom));
		} else if (data.isAudio()) {
			// TODO : Audio preview
			// mainView.getLabelPreview().setImage(
			// ImagesMacros.createAudioPreview(, si.zoom));
			// si.sv.getLabelPreviewSubsampled().setImage(
			// ImagesMacros.createAudioPreview(, si.zoom));
		}
	}
}
