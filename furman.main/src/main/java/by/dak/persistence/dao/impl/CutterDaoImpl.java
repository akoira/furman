package by.dak.persistence.dao.impl;

import by.dak.persistence.dao.CutterDao;
import by.dak.persistence.entities.Cutter;

public class CutterDaoImpl extends GenericDaoImpl<Cutter> implements CutterDao
{

    @Override
    public Cutter loadByName(String name)
    {
        return findUniqueByField("name", name);
    }

    public static void main(String[] args)
    {

    }
}
