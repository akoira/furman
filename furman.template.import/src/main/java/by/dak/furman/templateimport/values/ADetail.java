package by.dak.furman.templateimport.values;

import java.util.*;

/**
 * User: akoyro
 * Date: 5/1/13
 * Time: 11:18 AM
 */
public abstract class ADetail<P extends AItem> extends AFileValue<P, AValue>
{
    private Map<String, String> values = new HashMap<String, String>();
    private List<String> columns = new ArrayList<String>();

    public List<String> getColumns()
    {
        return Collections.unmodifiableList(columns);
    }

    public void setColumns(List<String> columns)
    {
        this.columns.clear();
        this.columns.addAll(columns);
    }

    public void setValue(int column, String value)
    {
        values.put(columns.get(column), value);
    }

    public String getValue(int column)
    {
        return values.get(columns.get(column));
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        for (String s : columns)
        {
            builder.append(String.format("%s=%s\n", s, getValue(columns.indexOf(s))));
        }
        return builder.toString();
    }
}
