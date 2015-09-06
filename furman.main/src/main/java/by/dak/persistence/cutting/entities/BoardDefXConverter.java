package by.dak.persistence.cutting.entities;

import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.BoardDef;
import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import org.apache.commons.lang.StringUtils;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 09.08.11
 * Time: 19:12
 * To change this template use File | Settings | File Templates.
 */
public class BoardDefXConverter extends AEntityXConverter<BoardDef>
{
    @Override
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext)
    {
        if (!StringUtils.isBlank(hierarchicalStreamReader.getValue()))
        {
            Long id = Long.parseLong(hierarchicalStreamReader.getValue());
            try
            {
                return FacadeContext.getBoardDefFacade().findById(id, true);
            }
            catch (Throwable e)
            {
                throw new ConversionException(e);
            }
        }
        throw new ConversionException("String value is blank");
    }
}
