package by.dak.report.jasper.common.data.converter;

import java.util.Comparator;

/**
 * @author Denis Koyro
 * @version 0.1 29.03.2009
 * @introduced [Furniture constructor | Iteration 1]
 * @since 1.0.0
 */
public class StringComparator implements Comparator<String>
{
    public int compare(String o1, String o2)
    {
        return o1.compareTo(o2);
    }
}
