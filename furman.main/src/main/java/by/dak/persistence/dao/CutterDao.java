package by.dak.persistence.dao;

import by.dak.persistence.entities.Cutter;
import org.springframework.stereotype.Repository;

@Repository
public interface CutterDao extends GenericDao<Cutter>
{

    public Cutter loadByName(String name);

}
