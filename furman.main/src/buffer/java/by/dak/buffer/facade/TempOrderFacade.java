package by.dak.buffer.facade;

import by.dak.buffer.entity.TempOrder;
import by.dak.cutting.facade.BaseFacade;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface TempOrderFacade extends BaseFacade<TempOrder>
{
}
