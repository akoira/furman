package by.dak.buffer.facade;

import by.dak.buffer.entity.TempOrderItem;
import by.dak.cutting.facade.BaseFacade;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface TempOrderItemFacade extends BaseFacade<TempOrderItem>
{
}
