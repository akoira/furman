package by.dak.cutting.linear.entity;

import by.dak.persistence.FacadeContext;
import by.dak.persistence.cutting.entities.AEntityXConverter;
import by.dak.persistence.entities.Furniture;
import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import org.apache.commons.lang.StringUtils;

/**
 * Created by IntelliJ IDEA.
 * User: dkoyro
 * Date: 06.05.11
 * Time: 17:40
 * To change this template use File | Settings | File Templates.
 */
public class FurnitureXConverter extends AEntityXConverter<Furniture>
{
    @Override
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext)
    {
        if (!StringUtils.isBlank(hierarchicalStreamReader.getValue()))
        {
            Long id = Long.parseLong(hierarchicalStreamReader.getValue());
            try
            {
                return FacadeContext.getFurnitureFacade().findById(id, true);
            }
            catch (Throwable e)
            {
                throw new ConversionException(e);
            }
        }
        throw new ConversionException("String value is blank");
    }
}
