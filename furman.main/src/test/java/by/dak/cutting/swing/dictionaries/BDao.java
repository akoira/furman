package by.dak.cutting.swing.dictionaries;

import by.dak.persistence.dao.GenericDao;
import org.springframework.stereotype.Repository;

@Repository
public interface BDao extends GenericDao<B>
{
    public B findBy(A a);
}
