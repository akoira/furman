package by.dak.cutting.facade.impl;

import by.dak.cutting.facade.BaseFacadeImpl;
import by.dak.cutting.facade.ManufacturerFacade;
import by.dak.persistence.entities.Manufacturer;

/**
 * @author Vitaly Kozlovski
 * @version 0.1 24.01.2009
 * @introduced [Furniture constructor | Iteration 1]
 * @since 1.0.0
 */
public class ManufacturerFacadeImpl extends BaseFacadeImpl<Manufacturer> implements ManufacturerFacade
{
    private String defaultName;
    //  @Override

    public void saveByTemplate(Manufacturer template, Integer count)
    {
        for (int i = 0; i < count; i++)
        {
            //   Border border = new Border();
            //    border.setOrder(template.getOrderItem());
            //    border.setStatus(StoreElementStatus.exist);
            //    border.setBorderDef(template.getBorderDef());
            //     border.setTexture(template.getTexture());
            //     border.setProvider(template.getProvider());
            //      border.setLength(template.getLength());
            //     save(border);
        }
    }

    @Override
    public Manufacturer getDefault()
    {
        return findUniqueByField(Manufacturer.PROPERTY_name, getDefaultName());
    }

    public String getDefaultName()
    {
        return defaultName;
    }

    public void setDefaultName(String defaultName)
    {
        this.defaultName = defaultName;
    }
}
