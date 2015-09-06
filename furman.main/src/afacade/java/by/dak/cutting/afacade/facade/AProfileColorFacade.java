package by.dak.cutting.afacade.facade;

import by.dak.cutting.afacade.AProfileColor;
import by.dak.cutting.facade.PricedFacade;

/**
 * User: akoyro
 * Date: 07.07.2010
 * Time: 11:07:28
 */
public interface AProfileColorFacade<E extends AProfileColor> extends PricedFacade<E>
{
    public void fillChildTypes(E value);
}