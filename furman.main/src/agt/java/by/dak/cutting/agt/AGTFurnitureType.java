package by.dak.cutting.agt;

import by.dak.cutting.agt.converter.AGTFurnitureType2StringConverter;
import by.dak.lang.Named;
import by.dak.utils.convert.StringValue;

/**
 * User: akoyro
 * Date: 01.09.2010
 * Time: 18:20:48
 */
@StringValue(converterClass = AGTFurnitureType2StringConverter.class)
public enum AGTFurnitureType implements Named
{
    agtprofile,//профиль
    agtfilling, //заполнение
    agtrubber,//уплотнитель
    agtdowel, //шпонка
    agtglue//клей
}
