package by.dak.cutting.swing.text;

import org.apache.commons.lang.StringUtils;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class NumericDocument extends PlainDocument
{

    protected int decimalPrecision = 0;
    protected boolean allowNegative = false;
    protected int maxInt = 0;

    public NumericDocument()
    {
    }

    public NumericDocument(int maxInt)
    {
        this.maxInt = maxInt;
    }

    public NumericDocument(int decimalPrecision, boolean allowNegative)
    {
        super();
        this.decimalPrecision = decimalPrecision;
        this.allowNegative = allowNegative;
    }


    @Override
    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException
    {
        if (str != null)
        {
            if (!StringUtils.isNumeric(str) && !str.equals(".") && !str.equals("-"))
            {
                //Toolkit.getDefaultToolkit().beep();
                return;
            }
            else if (str.equals(".") && super.getText(0, super.getLength()).contains("."))
            {
                //Toolkit.getDefaultToolkit().beep();
                return;
            }
            else if (StringUtils.isNumeric(str) && super.getText(0, super.getLength()).indexOf(".") != -1 && offset > super.getText(0, super.getLength()).indexOf(".") && super.getLength() - super.getText(0, super.getLength()).indexOf(".") > decimalPrecision && decimalPrecision > 0)
            {
                //Toolkit.getDefaultToolkit().beep();
                return;
            }
            else if (str.equals("-") && (offset != 0 || !allowNegative))
            {
                //todo
                //Toolkit.getDefaultToolkit().beep();
                return;
            }
            else if (maxInt != 0 && ((StringUtils.isNumeric(str) &&
                    !StringUtils.isBlank(getText(0, getLength())) && (getText(0, getLength()).equals("0")))
                    || Integer.parseInt(getText(0, getLength()) + str) > maxInt))
            {
                //Toolkit.getDefaultToolkit().beep();
                return;
            }

            super.insertString(offset, str, attr);
        }
    }
}