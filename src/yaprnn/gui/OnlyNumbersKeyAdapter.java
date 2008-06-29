package yaprnn.gui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Forces the user to just enter numbers.
 */
class OnlyNumbersKeyAdapter extends KeyAdapter {

	@Override
	public void keyTyped(KeyEvent e) {
		if (!Character.isDigit(e.getKeyChar()))
			e.consume();
	}

}
