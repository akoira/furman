package by.dak.persistence.cutting.entities;

import by.dak.persistence.entities.StorageElementLink;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * User: akoyro
 * Date: 20.11.2010
 * Time: 20:00:01
 */
public class StorageElementLinkXConverter extends AEntityXConverter<StorageElementLink>
{
    @Override
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext)
    {
        try
        {
            return super.unmarshal(hierarchicalStreamReader, unmarshallingContext);
        }
        catch (Throwable e)
        {
            Logger.getLogger(StorageElementLinkXConverter.class.getName()).log(Level.WARNING, e.getMessage(), e);
            return StorageElementLink.NULL;
        }
    }
}
