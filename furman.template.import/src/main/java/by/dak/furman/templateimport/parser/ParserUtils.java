package by.dak.furman.templateimport.parser;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * User: akoyro
 * Date: 9/22/13
 * Time: 7:26 PM
 */
public class ParserUtils
{

    public static String adjustText(String text)
    {
        text = text.replace('\n', ' ');
        text = text.replace('\r', ' ');
        text = text.replace('\u0007', ' ');
        text = StringUtils.trimToEmpty(text);
        return text.toLowerCase().startsWith("лист 1") ? StringUtils.EMPTY : text;
    }

    public static boolean find(String sPattern, String text)
    {
        Pattern pattern = Pattern.compile(sPattern, Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        return pattern.matcher(text).find();
    }
}
