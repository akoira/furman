package by.dak.cutting.zfacade;

import by.dak.cutting.zfacade.converter.ZFurnitureType2StringConverter;
import by.dak.lang.Named;
import by.dak.utils.convert.StringValue;

/**
 * User: akoyro
 * Date: 07.08.2010
 * Time: 13:11:05
 */
@StringValue(converterClass = ZFurnitureType2StringConverter.class)
public enum ZFurnitureType implements Named
{
    profile,//профиль
    filling,//заполнение
    angle,//уголок
    rubber,//уплотнитель
    butt//петли
}
