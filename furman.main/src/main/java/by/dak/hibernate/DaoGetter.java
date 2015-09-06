package by.dak.hibernate;

import by.dak.persistence.dao.GenericDao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: 26.12.2008
 * Time: 21:19:59
 * To change this template use File | Settings | File Templates.
 */
public class DaoGetter<D extends GenericDao>
{
    protected D getDao() throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
    {
        Class<D> class1 = (Class<D>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        return class1.getConstructor(new Class[0]).newInstance();
    }
}
