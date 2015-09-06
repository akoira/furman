package by.dak.persistence.entities;

import by.dak.persistence.entities.types.FurnitureCode;
import by.dak.persistence.entities.types.FurnitureType;

import javax.persistence.*;
import java.util.List;

/**
 * User: akoyro
 * Date: 19.07.2010
 * Time: 17:06:44
 */
@MappedSuperclass
public abstract class AFurnitureDetail extends AOrderDetail<FurnitureType, FurnitureCode>
{
    public static final String PROPERTY_size = "size";
    public static final String PROPERTY_children = "children";

    public static String PROPERTY_furnitureType = "furnitureType";
    public static String PROPERTY_furnitureCode = "furnitureCode";


    @Column(name = "SIZE", nullable = false)
    private Double size = 1d;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private List<FurnitureLink2FurnitureLink> children;

    public List<FurnitureLink2FurnitureLink> getChildren()
    {
        return children;
    }

    public void setChildren(List<FurnitureLink2FurnitureLink> children)
    {
        this.children = children;
    }

    public AFurnitureDetail()
    {
    }


    public FurnitureType getFurnitureType()
    {
        return getPriceAware();
    }

    public void setFurnitureType(FurnitureType furnitureType)
    {
        FurnitureType old = getPriceAware();
        setPriceAware(furnitureType);
        support.firePropertyChange(PROPERTY_furnitureType, old, furnitureType);
    }

    public FurnitureCode getFurnitureCode()
    {
        return getPriced();
    }

    public void setFurnitureCode(FurnitureCode furnitureCode)
    {
		FurnitureCode old = getPriced();
		setPriced(furnitureCode);
		support.firePropertyChange(PROPERTY_furnitureCode, old, furnitureCode);
	}

    public Double getSize()
    {
        return size;
    }

    public void setSize(Double size)
    {
        this.size = size;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        AFurnitureDetail that = (AFurnitureDetail) o;

        if (getSize() != null ? !getSize().equals(that.getSize()) : that.getSize() != null) return false;
        if (getFurnitureCode() != null ? !getFurnitureCode().equals(that.getFurnitureCode()) : that.getFurnitureCode() != null)
            return false;
        if (getFurnitureType() != null ? !getFurnitureType().equals(that.getFurnitureType()) : that.getFurnitureType() != null)
            return false;
        if (getOrderItem() != null ? !getOrderItem().equals(that.getOrderItem()) : that.getOrderItem() != null)
            return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = 31 * result + (getFurnitureType() != null ? getFurnitureType().hashCode() : 0);
        result = 31 * result + (getFurnitureCode() != null ? getFurnitureCode().hashCode() : 0);
        result = 31 * result + (getOrderItem() != null ? getOrderItem().hashCode() : 0);
        result = 31 * result + (getSize() != null ? getSize().hashCode() : 0);
        return result;
    }


}
