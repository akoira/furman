/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.measurement.utils;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Peca
 */
public class ArgumentParser
{

    private HashMap<String, String> values;

    public ArgumentParser(String[] args)
    {
        values = new HashMap<String, String>();
        for (int i = 0; i < args.length / 2; i++)
        {
            String prefix = args[i * 2].toLowerCase();
            String value = args[i * 2 + 1];
            values.put(prefix, value);
        }
    }


    public boolean hasValue(String key)
    {
        return values.containsKey(key.toLowerCase());
    }

    public String getStringValue(String key)
    {
        return values.get(key.toLowerCase());
    }

    public int getIntValue(String key)
    {
        return Integer.parseInt(getStringValue(key));
    }

    public int getIntValue(String key, int def)
    {
        if (hasValue(key))
        {
            return Integer.parseInt(getStringValue(key));
        }
        return def;
    }

    public String getStringValue(String key, String def)
    {
        if (hasValue(key))
        {
            return getStringValue(key);
        }
        return def;
    }

    public float getFloatValue(String key)
    {
        return Float.parseFloat(getStringValue(key));
    }

    private long parseTime(String value)
    {
        Matcher matcher = Pattern.compile("(\\d+)").matcher(value);
        matcher.find();
        long el = Integer.parseInt(matcher.group()) * 1000;
        if (value.matches("\\d*m$"))
        {
            el *= 60;
        }
        else if (value.matches("\\d*h$"))
        {
            el *= 3600;
        }
        return el;
    }

    public float getTimeValue(String key)
    {
        return parseTime(getStringValue(key)) * 0.001f;
    }

    public float getTimeValue(String key, float def)
    {
        if (hasValue(key))
        {
            return getTimeValue(key);
        }
        return def;
    }
}
