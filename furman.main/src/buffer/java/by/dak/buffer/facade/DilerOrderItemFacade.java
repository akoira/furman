package by.dak.buffer.facade;

import by.dak.buffer.entity.DilerOrder;
import by.dak.buffer.entity.DilerOrderItem;
import by.dak.cutting.facade.BaseFacade;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface DilerOrderItemFacade extends BaseFacade<DilerOrderItem>
{
    public List<DilerOrderItem> getDilerOrderItemsFromTempTable();

    public List<DilerOrderItem> findBy(DilerOrder dilerOrder);
}
