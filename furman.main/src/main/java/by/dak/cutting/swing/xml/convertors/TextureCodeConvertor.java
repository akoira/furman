package by.dak.cutting.swing.xml.convertors;

import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.TextureEntity;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import org.apache.commons.lang.StringUtils;


public class TextureCodeConvertor implements Converter
{

    public void marshal(Object o, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext)
    {
        TextureEntity texture = (TextureEntity) o;
        hierarchicalStreamWriter.setValue(String.valueOf(texture.getId()));
    }

    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext)
    {
        if (!StringUtils.isBlank(hierarchicalStreamReader.getValue()))
        {
            Long id = Long.parseLong(hierarchicalStreamReader.getValue());
            return FacadeContext.getTextureFacade().findBy(id);
        }
        return null;
    }

    public boolean canConvert(Class aClass)
    {
        return aClass.equals(TextureEntity.class);
    }
}
