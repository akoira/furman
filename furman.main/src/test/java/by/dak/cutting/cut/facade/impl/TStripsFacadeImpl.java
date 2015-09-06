package by.dak.cutting.cut.facade.impl;

import by.dak.cutting.SpringConfiguration;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.cutting.entities.StripsEntity;

import java.sql.Date;
import java.util.List;

/**
 * User: akoyro
 * Date: 15.05.2010
 * Time: 14:45:35
 */
public class TStripsFacadeImpl
{
    public static void main(String[] args)
    {
        SpringConfiguration springConfiguration = new SpringConfiguration();
        Date start = new Date(0);
        Date end = new Date(System.currentTimeMillis());
        List<StripsEntity> list = FacadeContext.getStripsFacade().findBy(start, end);
        System.out.println(list);

    }
}
