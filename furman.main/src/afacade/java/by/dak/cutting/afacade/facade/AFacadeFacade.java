package by.dak.cutting.afacade.facade;

import by.dak.cutting.afacade.AFacade;
import by.dak.cutting.afacade.AFacadeCalculator;
import by.dak.cutting.facade.BaseFacade;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface AFacadeFacade<E extends AFacade> extends BaseFacade<E>
{

    public List<E> fillTransients(List<E> facade);

    public E fillTransients(E facade);

    public AFacadeCalculator<E> getCalculator();
}
