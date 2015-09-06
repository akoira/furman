/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.persistence.convert;

import by.dak.persistence.entities.BorderDefEntity;
import by.dak.utils.convert.EntityToStringConverter;

/**
 * @author Admin
 */
public class BorderDef2StringConverter implements EntityToStringConverter<BorderDefEntity>
{

    public String convert(BorderDefEntity entity)
    {
        return entity.getName();
    }

}
