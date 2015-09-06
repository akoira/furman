package by.dak.persistence.cutting.entities;

import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.PersistenceEntity;
import by.dak.utils.GenericUtils;
import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import org.apache.commons.lang.StringUtils;

/**
 * User: akoyro
 * Date: 20.11.2010
 * Time: 19:57:14
 */
public class AEntityXConverter<E extends PersistenceEntity> implements Converter
{
    @Override
    public void marshal(Object o, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext)
    {
        if (o instanceof PersistenceEntity && ((PersistenceEntity) o).hasId())
        {
            hierarchicalStreamWriter.setValue(String.valueOf(((PersistenceEntity) o).getId()));
        }
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext)
    {
        if (!StringUtils.isBlank(hierarchicalStreamReader.getValue()))
        {
            Long id = Long.parseLong(hierarchicalStreamReader.getValue());
            try
            {
                return FacadeContext.getFacadeBy(GenericUtils.getParameterClass(this.getClass(), 0)).findBy(id);
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
        return aClass == GenericUtils.getParameterClass(this.getClass(), 0);
    }
}