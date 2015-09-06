package by.dak.buffer.facade;

import by.dak.buffer.entity.DilerOrderDetail;
import by.dak.buffer.entity.DilerOrderItem;
import by.dak.cutting.facade.BaseFacade;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface DilerOrderDetailFacade extends BaseFacade<DilerOrderDetail>
{
    public List<DilerOrderDetail> getDilerOrderDetailsFromTempTable();

    public List<DilerOrderDetail> findBy(DilerOrderItem dilerOrderItem);
}
