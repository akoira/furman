package by.dak.buffer.entity.migrator.item;

import by.dak.buffer.entity.DilerOrderItem;
import by.dak.buffer.entity.migrator.DilerOrderItemMigrator;
import by.dak.cutting.agt.AGTFacade;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 09.11.2010
 * Time: 13:23:46
 * To change this template use File | Settings | File Templates.
 */
public class AGTFacadeMigrator extends DilerOrderItemMigrator<AGTFacade>
{
    @Override
    public AGTFacade migrate(DilerOrderItem dilerOrderItem)
    {
        AGTFacade agtFacade = new AGTFacade();
        agtFacade.setAmount(dilerOrderItem.getAmount());
        agtFacade.setName(dilerOrderItem.getName());
        agtFacade.setType(dilerOrderItem.getType());
        agtFacade.setNumber(dilerOrderItem.getNumber());
        agtFacade.setLength(dilerOrderItem.getLength());
        agtFacade.setWidth(dilerOrderItem.getWidth());
        agtFacade.setDiscriminator(dilerOrderItem.getDiscriminator());

        return agtFacade;
    }
}
