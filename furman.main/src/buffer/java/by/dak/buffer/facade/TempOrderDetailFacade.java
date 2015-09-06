package by.dak.buffer.facade;

import by.dak.buffer.entity.TempOrderDetail;
import by.dak.cutting.facade.BaseFacade;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface TempOrderDetailFacade extends BaseFacade<TempOrderDetail>
{
}
