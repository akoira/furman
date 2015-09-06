/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */
package by.dak.cutting.swing.renderer;

import by.dak.utils.convert.Converter;
import by.dak.utils.convert.EntityToStringConverter;
import by.dak.utils.convert.StringValueAnnotationProcessor;
import org.jdesktop.swingx.renderer.DefaultListRenderer;

import javax.swing.*;
import java.awt.*;

/**
 * @author admin
 */
public class EntityListRenderer<E> extends DefaultListRenderer
{

    private EntityToStringConverter entityToStringConverter;

    public EntityListRenderer()
    {
    }

    public EntityListRenderer(EntityToStringConverter<E> entityToStringConverter)
    {
        this.entityToStringConverter = entityToStringConverter;
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value,
                                                  int index, boolean isSelected, boolean cellHasFocus)
    {
        if (value != null)
        {
            if (entityToStringConverter != null)
            {
                value = entityToStringConverter.convert(value);
            }
            else
            {
                value = StringValueAnnotationProcessor.getProcessor().getEntityToStringConverter(value.getClass()).convert(value);
            }
        }
        else
        {
            value = Converter.NULL_STRING;
        }
        return super.getListCellRendererComponent(list, value, index, isSelected,
                cellHasFocus);
    }
}
