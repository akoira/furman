package by.dak.cutting.swing;

import by.dak.cutting.swing.renderer.EntityListRenderer;
import by.dak.utils.convert.StringValueAnnotationProcessor;
import com.jidesoft.swing.AutoCompletion;
import com.jidesoft.swing.AutoCompletionComboBox;
import com.jidesoft.swing.ComboBoxSearchable;
import com.jidesoft.swing.Searchable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: 27.12.2008
 * Time: 18:36:47
 * To change this template use File | Settings | File Templates.
 */
public class DComboBox<E> extends AutoCompletionComboBox
{
    public DComboBox()
    {
        super();
        setRenderer(new EntityListRenderer<E>());
    }

    public DComboBox(Vector<E> items)
    {
        super(items);
        setRenderer(new EntityListRenderer<E>());
    }

    public DComboBox(E[] items)
    {
        super(items);
        setRenderer(new EntityListRenderer<E>());
    }

    public DComboBox(ComboBoxModel aModel)
    {
        super(aModel);
        setRenderer(new EntityListRenderer<E>());
    }

    @Override
    protected AutoCompletion createAutoCompletion()
    {
        return new AutoCompletion(this, createSearchable());
    }

    protected Searchable createSearchable()
    {
        return new ComboBoxSearchable(this)
        {
            @Override
            protected String convertElementToString(Object object)
            {
                return StringValueAnnotationProcessor.getProcessor().convert(object);
            }
        };
    }

    @Override
    public void setEditor(final ComboBoxEditor anEditor)
    {
        ComboBoxEditor comboBoxEditor = new ComboBoxEditor()
        {
            public Component getEditorComponent()
            {
                return anEditor.getEditorComponent();
            }

            public void setItem(Object anObject)
            {
                anEditor.setItem(anObject);
            }

            public Object getItem()
            {
                return getSelectedItem();
            }

            public void selectAll()
            {
                anEditor.selectAll();
            }

            public void addActionListener(ActionListener l)
            {
                anEditor.addActionListener(l);
            }

            public void removeActionListener(ActionListener l)
            {
                anEditor.removeActionListener(l);
            }
        };
        super.setEditor(comboBoxEditor);
    }
}
