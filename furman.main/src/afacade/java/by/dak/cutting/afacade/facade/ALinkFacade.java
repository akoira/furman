package by.dak.cutting.afacade.facade;

import by.dak.cutting.afacade.ALink;
import by.dak.cutting.facade.BaseFacade;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ALinkFacade<L extends ALink, E> extends BaseFacade<L>
{
    public L createAndSaveBy(E parent, E child, String keyword);

    void deleteBy(E parent);

    E findBy(E parent, String keyword);

    List<L> findAllBy(String keyword);

}
