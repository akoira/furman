package by.dak.persistence.entities.predefined;

import by.dak.persistence.convert.Unit2StringConverter;
import by.dak.utils.convert.StringValue;

/**
 * Описание едениц измерения
 */
@StringValue(converterClass = Unit2StringConverter.class)
public enum Unit
{

    squareMetre,//метры квадратный
    piece,//штуки
    linearMetre, //метр погонный
    linearMiliMetre, //милиметере погонный
    linearSantiMetre, //сантиметр погонный
    //jar760g, //банка 760 г
    gramme, //грамм
}
