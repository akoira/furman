package by.dak.cutting.swing.dictionaries.material;

import by.dak.persistence.entities.PriceAware;
import by.dak.persistence.entities.PriceEntity;
import by.dak.persistence.entities.predefined.MaterialType;

import java.beans.PropertyChangeSupport;
import java.util.List;

/**
 * User: akoyro
 * Date: 09.06.2009
 * Time: 11:12:26
 */
public class MaterialModel
{
    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    private MaterialType materialType;
    private PriceAware priceAware;

    private List<PriceEntity> prices;


    public MaterialType getMaterialType()
    {
        return materialType;
    }

    public void setMaterialType(MaterialType materialType)
    {
        MaterialType old = this.materialType;
        this.materialType = materialType;
        support.firePropertyChange("materialType", old, materialType);
    }

    public PriceAware getPriceAware()
    {
        return priceAware;
    }

    public void setPriceAware(PriceAware priceAware)
    {
        PriceAware old = this.priceAware;
        this.priceAware = priceAware;
        support.firePropertyChange("priceAware", old, priceAware);
    }

    public List<PriceEntity> getPrices()
    {
        return prices;
    }

    public void setPrices(List<PriceEntity> prices)
    {
        this.prices = prices;
    }


}
