package by.dak.utils;

import java.lang.reflect.ParameterizedType;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 04.01.2010
 * Time: 23:25:18
 * To change this template use File | Settings | File Templates.
 */
public class GenericUtils
{
    public static Class getParameterClass(Class objectClass, int parameterIndex)
    {
        ParameterizedType parameterizedType =
                (ParameterizedType) objectClass.getGenericSuperclass();
        return (Class) parameterizedType.getActualTypeArguments()[parameterIndex];
    }


}
