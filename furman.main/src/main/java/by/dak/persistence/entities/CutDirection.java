package by.dak.persistence.entities;

import by.dak.persistence.convert.CutDirection2StringConverter;
import by.dak.utils.convert.StringValue;

/**
 * User: akoyro
 * Date: 18.09.11
 * Time: 16:55
 */
@StringValue(converterClass = CutDirection2StringConverter.class)
public enum CutDirection
{
    horizontal,
    vertical;
}
