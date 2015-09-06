package by.dak.cutting.swing.dictionaries;

import by.dak.cutting.swing.DComboBox;
import by.dak.cutting.swing.renderer.EntityListRenderer;
import by.dak.cutting.swing.renderer.EntityTableRenderer;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Manufacturer;
import by.dak.persistence.entities.PriceAware;
import by.dak.persistence.entities.PriceEntity;
import org.jdesktop.swingx.autocomplete.ComboBoxCellEditor;

import javax.swing.*;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 14.01.2010
 * Time: 15:52:22
 * To change this template use File | Settings | File Templates.
 */
public abstract class APricedCfgPanel<E extends PriceAware> extends AbstractPricesTab<E>
{
    @Override
    protected void initTableGui()
    {

        DComboBox comboBox = new DComboBox();
        comboBox.setModel(new DefaultComboBoxModel(new Vector(FacadeContext.getManufacturerFacade().loadAll())));
        comboBox.setRenderer(new EntityListRenderer());

//        BindingGroup bindingGroup = new BindingGroup();
//        Binding binding = SwingBindings.createJComboBoxBinding(AutoBinding.UpdateStrategy.READ_WRITE,
//                items, comboBox);
//        bindingGroup.addBinding(binding);
//        bindingGroup.bind();

        getTablePrices().setDefaultRenderer(Manufacturer.class, new EntityTableRenderer<Manufacturer>());
        getTablePrices().setDefaultEditor(Manufacturer.class, new ComboBoxCellEditor(comboBox));
    }

    public PriceEntity createEmptyPrice()
    {
        PriceEntity priceEntity = new PriceEntity();
        priceEntity.setPriced(FacadeContext.getMaterialTypeHelper().newPricedBy(getValue()));
        priceEntity.setPriceAware(getValue());
        return priceEntity;
    }
}
