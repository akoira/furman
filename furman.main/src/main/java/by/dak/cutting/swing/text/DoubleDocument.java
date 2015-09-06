package by.dak.cutting.swing.text;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

/**
 * User: akoyro
 * Date: 26.04.2009
 * Time: 1:11:16
 */
public class DoubleDocument extends javax.swing.text.PlainDocument {

	public void insertString(int offset, String s, AttributeSet attributeSet)
			throws BadLocationException {
		StringBuffer buf = new StringBuffer(getText(0, getLength()));
		buf.insert(offset, s);
		if (offset == 0 && s.equals("-")) {
			super.insertString(offset, s, attributeSet);
			return;
		}
		super.insertString(offset, s, attributeSet);
	}
}

