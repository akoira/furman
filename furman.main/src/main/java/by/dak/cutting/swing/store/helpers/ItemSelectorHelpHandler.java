package by.dak.cutting.swing.store.helpers;

import by.dak.cutting.swing.ItemSelector;

import javax.swing.*;
import java.util.List;


/**
 *
 */
public class ItemSelectorHelpHandler
{
    private ItemSelector itemSelector = new ItemSelector();
    private Action newAction;

    public ItemSelectorHelpHandler(List list, Action newAction)
    {
        this.newAction = newAction;
        getItemSelector().setItems(list);
        init();
    }

    private void init()
    {
        if (newAction != null)
        {
            getItemSelector().setNewAction(newAction);
        }
    }

    public ItemSelector getItemSelector()
    {
        return itemSelector;
    }

    public void setItemSelector(ItemSelector itemSelector)
    {
        this.itemSelector = itemSelector;
    }
}
