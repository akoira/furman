package by.dak.furman.templateimport.swing.nodes;

import org.apache.commons.lang3.StringUtils;

public class RootNode extends AValueNode<String>
{

    public RootNode()
    {
        setValue(StringUtils.EMPTY);
    }

    @Override
    public Object getValueAt(int column)
    {
        return StringUtils.EMPTY;
    }

    @Override
    public int getColumnCount()
    {
        return 2;
    }
}
