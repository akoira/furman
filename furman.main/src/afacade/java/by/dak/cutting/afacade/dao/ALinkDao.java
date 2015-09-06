package by.dak.cutting.afacade.dao;

import by.dak.cutting.afacade.ALink;
import by.dak.persistence.dao.GenericDao;
import org.springframework.stereotype.Repository;

@Repository
public interface ALinkDao<L extends ALink, E> extends GenericDao<L>
{
    void deleteBy(E parent);
}
