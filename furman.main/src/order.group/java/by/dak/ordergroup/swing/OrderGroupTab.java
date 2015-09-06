package by.dak.ordergroup.swing;

import by.dak.cutting.swing.AEntityEditorTab;
import by.dak.ordergroup.OrderGroup;

import static by.dak.ordergroup.OrderGroup.PROPERTY_dailysheet;
import static by.dak.ordergroup.OrderGroup.PROPERTY_name;

/**
 * User: akoyro
 * Date: 15.01.2011
 * Time: 15:09:08
 */
public class OrderGroupTab extends AEntityEditorTab<OrderGroup>
{
    private static final String[] VISIBLE_PROPERTIES = new String[]{PROPERTY_name, PROPERTY_dailysheet};

    public OrderGroupTab()
    {
        setVisibleProperties(VISIBLE_PROPERTIES);
    }
}
