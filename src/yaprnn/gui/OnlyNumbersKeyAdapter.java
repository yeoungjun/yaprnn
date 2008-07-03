package yaprnn.gui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Forces the user to just enter numbers.
 */
class OnlyNumbersKeyAdapter extends KeyAdapter {

	@Override
	public void keyTyped(KeyEvent e) {
		char c = e.getKeyChar();
		if (!(Character.isDigit(c) || c == '-' || c == '.' || c == 'E' || c == 'e'))
			e.consume();
	}

}
