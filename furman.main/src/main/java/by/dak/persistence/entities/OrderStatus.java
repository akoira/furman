package by.dak.persistence.entities;

import by.dak.persistence.convert.OrderStatus2StringConverter;
import by.dak.utils.convert.StringValue;
import org.jdesktop.application.ResourceMap;

import javax.swing.*;
import java.awt.Color;

/**
 * User: akoyro
 * Date: 09.08.2009
 * Time: 10:58:33
 */
@StringValue(converterClass = OrderStatus2StringConverter.class)
public enum OrderStatus
{
    miscalculation(new java.awt.Color(0x3c13af)),//просчет
    design(new java.awt.Color(0xFF0D00)),//дизайн
    production(new Color(0xFFD600)),//ПРОИЗВОДСТВО
    made(new Color(0x00C618)),//ПРОИЗВЕДЕНО
    shipped(new Color(0x39e24d)),//ОТГРУЖЕНО
    archive(new Color(0x66e275)),//АРХИВ
    webMiscalculation(new Color(0x66e275));


    private java.awt.Color color;

    OrderStatus(java.awt.Color color)
    {
        this.color = color;
    }

    public static OrderStatus[] notEditables()
    {
        return new OrderStatus[]{production, made, shipped, archive};
    }

    public Color getColor()
    {
        return color;
    }

    public Icon getIcon(ResourceMap resourceMap)
    {
        return resourceMap == null ? null : resourceMap.getIcon("icon." + name());
    }
}
