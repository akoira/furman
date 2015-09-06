package by.dak.cutting.afacade.swing;

import by.dak.cutting.swing.AEntityEditorTab;
import by.dak.cutting.swing.store.tabs.PriceAwareToPricedBinder;
import by.dak.persistence.entities.OrderFurniture;

/**
 * User: akoyro
 * Date: 19.07.2010
 * Time: 19:42:34
 */
public class AFillingTab extends AEntityEditorTab<OrderFurniture>
{
    @Override
    public void init()
    {
        super.init();
        getBindingGroup().addBindingListener(new PriceAwareToPricedBinder(this));
    }
}
