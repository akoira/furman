package by.dak.cutting.swing;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import org.apache.commons.lang.StringUtils;

/**
 * User: akoyro
 * Date: 14.10.11
 * Time: 0:11
 */
public class AElement2StringConverterXConverter implements Converter
{
    @Override
    public void marshal(Object o, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext)
    {
        if (o instanceof AElement2StringConverter)
        {
            hierarchicalStreamWriter.setValue(o.getClass().getName());
        }
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext)
    {
        if (!StringUtils.isBlank(hierarchicalStreamReader.getValue()))
        {
            try
            {
                Class aClass = Class.forName(hierarchicalStreamReader.getValue());
                return aClass.getConstructor(new Class[]{}).newInstance(new Object[]{});
            }
            catch (Throwable e)
            {
                throw new ConversionException(e);
            }
        }
        throw new ConversionException("String value is blank");
    }

    @Override
    public boolean canConvert(Class aClass)
    {
        return (AElement2StringConverter.class.isAssignableFrom(aClass));
    }
}