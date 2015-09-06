package by.dak.persistence;

import org.hibernate.Query;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * User: akoyro
 * Date: 17.11.2010
 * Time: 15:57:00
 */
public class NamedQueryParameter
{
    private String parameterMethodName;
    private String parameterName;
    private Object parameter;
    private Class parameterClass;

    public static NamedQueryParameter getParameterListParameter(String parameterName, Object[] parameterList)
    {
        NamedQueryParameter result = new NamedQueryParameter();
        result.parameterClass = Object[].class;
        result.parameterMethodName = "setParameterList";
        result.parameterName = parameterName;
        result.parameter = parameterList;
        return result;
    }

    public static NamedQueryParameter getLongParameter(String parameterName, Long parameter)
    {
        NamedQueryParameter result = new NamedQueryParameter();
        result.parameterClass = long.class;
        result.parameterMethodName = "setLong";
        result.parameterName = parameterName;
        result.parameter = parameter;
        return result;
    }

    public static NamedQueryParameter getObjectParameter(String parameterName, Object parameter)
    {
        NamedQueryParameter result = new NamedQueryParameter();
        result.parameterClass = Object.class;
        result.parameterMethodName = "setParameter";
        result.parameterName = parameterName;
        result.parameter = parameter;
        return result;
    }


    public static NamedQueryParameter getDateParameter(String parameterName, Date parameter)
    {
        NamedQueryParameter result = new NamedQueryParameter();
        result.parameterClass = java.util.Date.class;
        result.parameterMethodName = "setDate";
        result.parameterName = parameterName;
        result.parameter = parameter;
        return result;
    }

    public void fillQuery(Query query)
    {
        try
        {
            Method method = Query.class.getMethod(getParameterMethodName(), String.class, parameterClass);
            method.invoke(query, getParameterName(),getParameter());
        }
        catch (Throwable e)
        {
            throw new IllegalArgumentException(e);
        }
    }


    public String getParameterMethodName()
    {
        return parameterMethodName;
    }

    public void setParameterMethodName(String parameterMethodName)
    {
        this.parameterMethodName = parameterMethodName;
    }

    public String getParameterName()
    {
        return parameterName;
    }

    public void setParameterName(String parameterName)
    {
        this.parameterName = parameterName;
    }

    public Object getParameter()
    {
        return this.parameter;
    }

    public void setParameter(Object parameter)
    {
        this.parameter = parameter;
    }

    public Class getParameterClass()
    {
        return parameterClass;
    }

    public void setParameterClass(Class parameterClass)
    {
        this.parameterClass = parameterClass;
    }
    
}
