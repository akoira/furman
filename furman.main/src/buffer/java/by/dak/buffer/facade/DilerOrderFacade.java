package by.dak.buffer.facade;

import by.dak.buffer.entity.DilerOrder;
import by.dak.cutting.facade.BaseFacade;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface DilerOrderFacade extends BaseFacade<DilerOrder>
{
    public DilerOrder getDilerOrderFromTempTable();
}
