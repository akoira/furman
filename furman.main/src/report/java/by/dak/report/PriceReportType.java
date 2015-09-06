package by.dak.report;

import by.dak.utils.convert.StringValue;

/**
 * User: akoyro
 * Date: 20.04.11
 * Time: 10:50
 */
@StringValue(converterClass = PriceReportType2StringConverter.class)
public enum PriceReportType
{
    owner,  //print full information
    dialer, //print dialer price, sale price
    designer //print sale price
}
