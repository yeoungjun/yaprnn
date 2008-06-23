package yaprnn;

import yaprnn.gui.GUI;
import yaprnn.gui.view.MainView;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Core core = new Core();
		new GUI(core, new MainView());
	}

}
