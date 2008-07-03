package yaprnn.gui;

import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class OptionResolutionChange implements ChangeListener {

	private GUI gui;

	public OptionResolutionChange(GUI gui) {
		this.gui = gui;
		gui.getView().getOptionResolution().addChangeListener(this);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		JSpinner spinner = gui.getView().getOptionResolution();
		// TODO : Keine Ahnung ob ein commitEdit notwendig ist
		/*
		 * try { spinner.commitEdit(); } catch (ParseException pe) { }
		 */
		gui.setResolution(((Integer) spinner.getValue()).intValue());
	}

}
