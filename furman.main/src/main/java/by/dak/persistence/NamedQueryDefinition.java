package by.dak.persistence;

import org.hibernate.Query;
import org.hibernate.transform.ResultTransformer;

import java.util.ArrayList;
import java.util.List;

/**
 * User: akoyro
 * Date: 17.11.2010
 * Time: 15:57:11
 */
public class NamedQueryDefinition
{
    private String nameQuery;
    private List<NamedQueryParameter> parameterList = new ArrayList<NamedQueryParameter>();
    private ResultTransformer resultTransformer;

    public String getNameQuery()
    {
        return nameQuery;
    }

    public void setNameQuery(String nameQuery)
    {
        this.nameQuery = nameQuery;
    }

    public List<NamedQueryParameter> getParameterList()
    {
        return parameterList;
    }

    public void setParameterList(List<NamedQueryParameter> parameterList)
    {
        this.parameterList = parameterList;
    }

    public ResultTransformer getResultTransformer()
    {
        return resultTransformer;
    }

    public void setResultTransformer(ResultTransformer transformer)
    {
        this.resultTransformer = transformer;
    }

    public void fillQuery(Query query)
    {
        if (getParameterList() != null)
        {
            for (NamedQueryParameter namedQueryParameter : getParameterList())
            {
                namedQueryParameter.fillQuery(query);
            }
        }

        if (resultTransformer != null)
        {
            query.setResultTransformer(getResultTransformer());
        }
    }
}
