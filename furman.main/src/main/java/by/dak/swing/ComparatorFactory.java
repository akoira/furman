package by.dak.swing;

import java.util.Comparator;

/**
 * User: akoyro
 * Date: 14.05.2009
 * Time: 21:06:37
 */
public interface ComparatorFactory
{
    Comparator getComparatorBy(Class aClass);
}
