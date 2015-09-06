package by.dak.buffer.entity.migrator;

import by.dak.buffer.entity.DilerOrderDetail;
import by.dak.persistence.entities.AOrderDetail;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 09.11.2010
 * Time: 12:41:00
 * To change this template use File | Settings | File Templates.
 */
public abstract class DilerOrderDetailMigrator<D extends AOrderDetail>
{
    public abstract D migrate(DilerOrderDetail dilerOrderDetail);
}
