package by.dak.cutting.facade;

import by.dak.persistence.entities.Border;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: akoyro
 * Date: 19.11.2009
 * Time: 1:26:13
 */
@Transactional
public interface BorderFacade extends AStoreElementFacade<Border>
{
    public double getCurveGluingAdditionalLength();
}
