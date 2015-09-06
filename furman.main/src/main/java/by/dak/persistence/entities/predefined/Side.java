/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */
package by.dak.persistence.entities.predefined;

import by.dak.persistence.convert.Side2StringConverter;
import by.dak.utils.convert.StringValue;

/**
 * @author admin
 */

@StringValue(converterClass = Side2StringConverter.class)
public enum Side
{
    left(0),
    right(1),
    up(2),
    down(3);

    private int index;

    Side(int index)
    {
        this.index = index;
    }

    public int getIndex()
    {
        return index;
    }


}
