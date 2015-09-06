package by.dak.cutting.afacade.facade;

import by.dak.cutting.afacade.AProfileType;
import by.dak.cutting.facade.PriceAwareFacade;

/**
 * User: akoyro
 * Date: 07.07.2010
 * Time: 11:07:28
 */
public interface AProfileTypeFacade<E extends AProfileType> extends PriceAwareFacade<E>
{
    public void fillChildTypes(E value);
}
