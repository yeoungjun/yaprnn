package yaprnn.gui;

import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class OptionOverlapChange implements ChangeListener {

	private GUI gui;

	public OptionOverlapChange(GUI gui) {
		this.gui = gui;
		gui.getView().getOptionOverlap().addChangeListener(this);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		JSpinner spinner = gui.getView().getOptionOverlap();
		// TODO : Keine Ahnung ob ein commitEdit notwendig ist
		/*
		 * try { spinner.commitEdit(); } catch (ParseException pe) { }
		 */
		gui.setOverlap(((Double) spinner.getValue()).doubleValue());
	}

}
