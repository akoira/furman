package by.dak.buffer.entity.migrator.detail.furniture;

import by.dak.persistence.entities.Priced;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 09.12.2010
 * Time: 20:27:52
 * To change this template use File | Settings | File Templates.
 */
public abstract class AFurnitureCodeMigrator<P extends Priced>
{
    public abstract P migrate(Priced priced);
}
