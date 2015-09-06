package by.dak.cutting.swing;

import nl.jj.swingx.gui.modal.JModalFrame;
import org.bushe.swing.event.annotation.AnnotationProcessor;

import java.awt.*;

/**
 * Base class for dictionary's dialogs
 *
 * @author Rudak Alexei
 */

@Deprecated
public abstract class BaseGridDialog extends JModalFrame
{

    protected DictionaryEditType editType = DictionaryEditType.FULL;


    public BaseGridDialog(Window window, boolean modal)
    {
        super(window, modal);
    }

    /**
     * @return the editType
     */
    public DictionaryEditType getEditType()
    {
        return editType;
    }

    /**
     * @param editType the editType to set
     */
    public void setEditType(DictionaryEditType editType)
    {
        this.editType = editType;
    }

    public BaseGridDialog()
    {
        this(DictionaryEditType.FULL);
    }

    public BaseGridDialog(DictionaryEditType editType)
    {
        setEditType(editType);
        setFocusable(true);
        AnnotationProcessor.process(this);
    }
}
