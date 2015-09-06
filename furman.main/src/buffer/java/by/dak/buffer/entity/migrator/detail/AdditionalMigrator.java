package by.dak.buffer.entity.migrator.detail;

import by.dak.additional.Additional;
import by.dak.buffer.entity.DilerOrderDetail;
import by.dak.buffer.entity.migrator.DilerOrderDetailMigrator;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 09.11.2010
 * Time: 14:43:19
 * To change this template use File | Settings | File Templates.
 */
public class AdditionalMigrator extends DilerOrderDetailMigrator<Additional>
{
    @Override
    public Additional migrate(DilerOrderDetail dilerOrderDetail)
    {
        Additional additional = new Additional();

        additional.setAmount(dilerOrderDetail.getAmount());
        additional.setNumber(dilerOrderDetail.getNumber());
        additional.setName(dilerOrderDetail.getName());
        additional.setDiscriminator(dilerOrderDetail.getDiscriminator());
        additional.setType(dilerOrderDetail.getType());
        additional.setSize(dilerOrderDetail.getSize());
        additional.setPrice(dilerOrderDetail.getPrice());

        return additional;
    }
}
