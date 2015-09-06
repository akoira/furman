package by.dak.cutting.afacade.swing;

import by.dak.cutting.swing.AEntityEditorTab;
import by.dak.cutting.swing.store.tabs.PriceAwareToPricedBinder;
import by.dak.persistence.entities.AFurnitureDetail;
import by.dak.persistence.entities.FurnitureLink;
import by.dak.swing.FocusToFirstComponent;

import java.awt.*;

/**
 * User: akoyro
 * Date: 19.07.2010
 * Time: 21:21:03
 */
public abstract class AFurnitureLinkTab<E extends AFurnitureDetail> extends AEntityEditorTab<E> implements FocusToFirstComponent
{
    @Override
    public void init()
    {
        super.init();
        getBindingGroup().addBindingListener(new PriceAwareToPricedBinder(this));
    }

    @Override
    public void setFocusToFirstComponent()
    {
        Component component = getEditors().get(FurnitureLink.PROPERTY_priceAware);
        component.requestFocusInWindow();
    }
}
