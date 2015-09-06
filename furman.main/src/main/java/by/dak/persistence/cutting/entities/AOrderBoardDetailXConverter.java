package by.dak.persistence.cutting.entities;

import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.AOrderBoardDetail;
import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import org.apache.commons.lang.StringUtils;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 25.09.11
 * Time: 15:28
 * To change this template use File | Settings | File Templates.
 */
public class AOrderBoardDetailXConverter extends AEntityXConverter<AOrderBoardDetail>
{

    @Override
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext)
    {
        if (!StringUtils.isBlank(hierarchicalStreamReader.getValue()))
        {
            Long id = Long.parseLong(hierarchicalStreamReader.getValue());
            try
            {
                return FacadeContext.getOrderDetailFacade().findById(id, true);
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
        return (AOrderBoardDetail.class.isAssignableFrom(aClass));
    }
}
