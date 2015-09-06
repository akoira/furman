package by.dak.cutting.swing.dictionaries.multiselect;

import by.dak.persistence.entities.PriceAware;
import by.dak.persistence.entities.PriceEntity;
import by.dak.persistence.entities.Priced;
import by.dak.swing.SourceTargetConverter;

/**
 * Created by IntelliJ IDEA.
 * User: akoyro
 * Date: 07.06.2009
 * Time: 16:31:02
 * To change this template use File | Settings | File Templates.
 */
public class Priced2PriceConverter implements SourceTargetConverter<PriceEntity, Priced>
{
    private PriceAware entity;

    public PriceAware getEntity()
    {
        return entity;
    }

    public void setEntity(PriceAware entity)
    {
        this.entity = entity;
    }


    @Override
    public Priced source(PriceEntity target)
    {
        return target.getPriced();
    }

    @Override
    public PriceEntity target(Priced source)
    {
        PriceEntity entity = null;//FacadeContext.getPriceFacade().findUniqueBy(getEntity(), source);
        if (entity == null)
        {
            entity = new by.dak.persistence.entities.PriceEntity();
            entity.setPriceAware(getEntity());
            entity.setPriced(source);
        }
        return entity;
    }
}