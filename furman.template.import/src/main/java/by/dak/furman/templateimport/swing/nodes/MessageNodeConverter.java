package by.dak.furman.templateimport.swing.nodes;

import javax.swing.*;

public class MessageNodeConverter extends AValueConverter<MessageNode>
{
    @Override
    public Icon getIcon(MessageNode value)
    {
        return getResourceMap().getIcon("icon.warning");
    }

    @Override
    public String getString(MessageNode value)
    {
        return getResourceMap().getString(value.getValue().getMessage(), value.getValue().getParams());
    }
}
