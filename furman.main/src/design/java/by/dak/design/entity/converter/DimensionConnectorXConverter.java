package by.dak.design.entity.converter;

import by.dak.design.draw.components.DimensionConnector;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 15.08.11
 * Time: 13:42
 * To change this template use File | Settings | File Templates.
 */
public class DimensionConnectorXConverter implements Converter
{
    @Override
    public void marshal(Object o, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext)
    {
        if (o instanceof DimensionConnector)
        {
        }
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean canConvert(Class aClass)
    {
        return true;
    }
}
