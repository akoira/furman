package by.dak.cutting.cut.facade;

import by.dak.persistence.cutting.entities.StripsEntity;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: akoyro
 * Date: 15.03.2009
 * Time: 14:17:04
 */
@Transactional
public interface StripsFacade extends ABoardStripsFacade<StripsEntity>
{
}
