package by.dak.buffer.entity.migrator.item;

import by.dak.buffer.entity.DilerOrderItem;
import by.dak.buffer.entity.migrator.DilerOrderItemMigrator;
import by.dak.cutting.zfacade.ZFacade;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 09.11.2010
 * Time: 13:24:03
 * To change this template use File | Settings | File Templates.
 */
public class ZFacadeMigrator extends DilerOrderItemMigrator<ZFacade>
{
    @Override
    public ZFacade migrate(DilerOrderItem dilerOrderItem)
    {
        ZFacade zFacade = new ZFacade();
        zFacade.setAmount(dilerOrderItem.getAmount());
        zFacade.setName(dilerOrderItem.getName());
        zFacade.setType(dilerOrderItem.getType());
        zFacade.setNumber(dilerOrderItem.getNumber());
        zFacade.setLength(dilerOrderItem.getLength());
        zFacade.setWidth(dilerOrderItem.getWidth());
        zFacade.setDiscriminator(dilerOrderItem.getDiscriminator());

        return zFacade;
    }
}
