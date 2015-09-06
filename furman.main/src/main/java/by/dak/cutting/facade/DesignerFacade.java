package by.dak.cutting.facade;

import by.dak.persistence.entities.DesignerEntity;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface DesignerFacade extends BaseFacade<DesignerEntity>
{

}
