package by.dak.report.jasper.common.dao;

import by.dak.persistence.dao.GenericDao;
import by.dak.persistence.entities.AOrder;
import by.dak.report.jasper.common.data.CommonData;
import org.springframework.stereotype.Repository;

@Repository
public interface CommonDataDao extends GenericDao<CommonData>
{
    public void deleteAllBy(AOrder order);
}
