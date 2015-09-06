package by.dak.cutting.cut.facade;

import by.dak.cutting.facade.BaseFacade;
import by.dak.persistence.cutting.entities.AStripsEntity;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AStripsFacade<S extends AStripsEntity> extends BaseFacade<S>
{

}
