/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */
package by.dak.cutting.swing.component;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.DecimalFormat;
import java.util.regex.Pattern;

/**
 * @author ebabich
 */
public class MaskTextField extends JFormattedTextField {

	private Pattern regPattern;

	public MaskTextField(DecimalFormat formatter) {
		super(formatter);
	}

	public MaskTextField() {
		super();
		attachFocusListener();
	}

	private void attachFocusListener() {
		addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				selectAll();
			}
		});
	}

	public void setMask(String regExp) {
		this.regPattern = Pattern.compile(regExp);
	}

	@Override
	protected Document createDefaultModel() {
		FieldDocument fieldDocument = new FieldDocument();
		return fieldDocument;
	}

	protected class FieldDocument extends PlainDocument {

		@Override
		public void insertString(int offs, String str, AttributeSet a) throws
				BadLocationException {
			if (str == null) {
				return;
			}
			StringBuffer strInsert = new StringBuffer(str);
			if (strInsert.length() > 0) {
				if (regPattern != null) {
					StringBuffer stringBuffer = new StringBuffer(MaskTextField.this.getText());
					stringBuffer.insert(offs, strInsert.toString());
					if (!regPattern.matcher(stringBuffer).matches()) {
						//todo
						//Toolkit.getDefaultToolkit().beep();
						return;
					}
				}
				super.insertString(offs, strInsert.toString(), a);
			}
		}
	}

}