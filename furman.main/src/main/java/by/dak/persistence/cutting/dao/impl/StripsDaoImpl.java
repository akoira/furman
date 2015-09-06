package by.dak.persistence.cutting.dao.impl;

import by.dak.persistence.cutting.dao.StripsDao;
import by.dak.persistence.cutting.entities.StripsEntity;
import by.dak.persistence.entities.AOrder;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository
public class StripsDaoImpl extends ABoardStripsDaoImpl<StripsEntity> implements StripsDao
{
    @Override
    public void deleteAll(AOrder order)
    {
        Query q = getSession().getNamedQuery("deleteAllStripsEntity");
        q.setEntity("order", order);
        q.executeUpdate();
    }
}
