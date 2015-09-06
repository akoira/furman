package by.dak.cutting.afacade.facade;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.afacade.AFacade;
import by.dak.cutting.afacade.AFacadeCalculator;
import by.dak.cutting.afacade.AProfileColor;
import by.dak.cutting.afacade.AProfileType;
import by.dak.cutting.facade.BaseFacade;
import by.dak.cutting.facade.BaseFacadeImpl;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.AOrderDetail;
import by.dak.persistence.entities.FurnitureLink;
import by.dak.persistence.entities.OrderFurniture;
import by.dak.persistence.entities.predefined.Side;
import by.dak.utils.validator.ValidatorAnnotationProcessor;
import com.jgoodies.validation.ValidationResult;
import org.apache.commons.beanutils.BeanUtils;

import java.util.List;

/**
 * User: akoyro
 * Date: 24.08.2010
 * Time: 18:10:58
 */
public abstract class AFacadeFacadeImpl<E extends AFacade> extends BaseFacadeImpl<E> implements AFacadeFacade<E>
{
    private AFacadeCalculator<E> calculator;

    @Override
    public void delete(E entity)
    {
        FacadeContext.getFurnitureLinkFacade().deleteAllBy(entity);
        super.delete(entity);
    }

    @Override
    public void save(E entity)
    {
        super.save(entity);

        List<AOrderDetail> list = entity.getTransients();
        for (AOrderDetail aOrderDetail : list)
        {
            saveFurniture(entity, aOrderDetail);
        }
    }


    protected <T extends AOrderDetail> void saveFurniture(E aFacade, T detail)
    {
        if (detail == null)
        {
            return;
        }

        ValidationResult result = ValidatorAnnotationProcessor.getProcessor().validate(detail);
        BaseFacade<AOrderDetail> facade = FacadeContext.getFacadeBy(detail.getClass());
        if (result.hasErrors())
        {
            if (detail.hasId())
            {
                facade.delete(detail);
            }
        }
        else
        {
            detail.setOrderItem(aFacade);
            facade.save(detail);
        }
    }

    protected void fillUpProfile(E facade, FurnitureLink furnitureLink)
    {
        facade.setUpProfile(furnitureLink);
        facade.setProfileType((AProfileType) furnitureLink.getFurnitureType());
        facade.setProfileColor((AProfileColor) furnitureLink.getFurnitureCode());
    }

    protected void fillTransient(E facade, FurnitureLink link)
    {
        String propertyName = link.getName();
        fillTransient(facade, propertyName, link);
    }

    protected void fillTransient(E facade, String propertyName, FurnitureLink link)
    {
        try
        {
            BeanUtils.setProperty(facade, propertyName, link);
        }
        catch (Throwable t)
        {
            throw new IllegalArgumentException(t);
        }
    }

    @Override
    public List<E> fillTransients(List<E> facades)
    {
        for (E e : facades)
        {
            fillTransients(e);
        }
        return facades;
    }

    public E fillTransients(E facade)
    {
        SearchFilter searchFilter = new SearchFilter();
        searchFilter.eq(OrderFurniture.PROPERTY_orderItem, facade);
        searchFilter.addAscOrder(OrderFurniture.PROPERTY_number);
        List<OrderFurniture> list = FacadeContext.getOrderFurnitureFacade().loadAll(searchFilter);
        for (OrderFurniture furniture : list)
        {
            facade.setFilling(furniture);
        }

        List<FurnitureLink> links = FacadeContext.getFurnitureLinkFacade().loadAll(searchFilter);
        for (FurnitureLink furnitureLink : links)
        {
            if (furnitureLink.getName().equals(Side.up.name()))
            {
                fillUpProfile(facade, furnitureLink);
            }
            else if (furnitureLink.getName().equals(Side.left.name()))
            {
                facade.setLeftProfile(furnitureLink);
            }
            else
            {
                fillTransient(facade, furnitureLink);
            }
        }
        return facade;
    }

    public AFacadeCalculator<E> getCalculator()
    {
        return calculator;
    }

    public void setCalculator(AFacadeCalculator<E> calculator)
    {
        this.calculator = calculator;
    }
}
