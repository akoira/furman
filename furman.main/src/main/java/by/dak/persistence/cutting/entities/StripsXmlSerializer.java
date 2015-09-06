package by.dak.persistence.cutting.entities;

import by.dak.cutting.cut.base.Element;
import by.dak.cutting.cut.guillotine.Segment;
import by.dak.cutting.cut.guillotine.Strips;
import by.dak.cutting.linear.LinearElementDimensionItem;
import by.dak.cutting.linear.LinearSheetDimensionItem;
import by.dak.cutting.swing.cut.ElementDimensionItem;
import by.dak.cutting.swing.cut.SheetDimentionItem;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.apache.commons.lang.StringUtils;

/**
 * User: akoyro
 * Date: 20.11.2010
 * Time: 17:22:44
 */
public class StripsXmlSerializer
{
    private final static StripsXmlSerializer instance = new StripsXmlSerializer();

    public static StripsXmlSerializer getInstance()
    {
        return instance;
    }

    private XStream xstream;

    public StripsXmlSerializer()
    {
        xstream = new XStream(new DomDriver());
        xstream.processAnnotations(Strips.class);
        xstream.processAnnotations(Segment.class);
        xstream.processAnnotations(Element.class);
        xstream.processAnnotations(SheetDimentionItem.class);
        xstream.processAnnotations(ElementDimensionItem.class);
        xstream.processAnnotations(LinearElementDimensionItem.class);
        xstream.processAnnotations(LinearSheetDimensionItem.class);

    }

    public String serialize(Strips strips)
    {
        return strips != null ? xstream.toXML(strips) : null;
    }

    public Strips unserialize(String xml)
    {
        try
        {
            return (Strips) (!StringUtils.isBlank(xml) ? xstream.fromXML(xml) : null);
        }
        catch (Throwable e)
        {
            throw new IllegalArgumentException(e);
        }
    }


}
