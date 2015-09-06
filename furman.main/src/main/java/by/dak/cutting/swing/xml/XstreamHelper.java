package by.dak.cutting.swing.xml;

import by.dak.cutting.swing.order.data.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.apache.commons.lang.StringUtils;


public class XstreamHelper
{
    private XStream xstream;
    private static XstreamHelper instance;

    private XstreamHelper()
    {
        xstream = new XStream(new DomDriver());
        xstream.processAnnotations(A45.class);
        xstream.processAnnotations(Drilling.class);
        xstream.processAnnotations(Milling.class);
        xstream.processAnnotations(Glueing.class);
        xstream.processAnnotations(Groove.class);
        //xstream.autodetectAnnotations(true);
    }

    public static XstreamHelper getInstance()
    {
        if (instance == null)
        {
            instance = new XstreamHelper();
        }
        return instance;
    }

    public String toXML(Object obj)
    {
        return obj != null ? xstream.toXML(obj) : null;
    }

    @Deprecated //todo сделать UserType
    public Object fromXML(String xml)
    {
        return !StringUtils.isBlank(xml) ? xstream.fromXML(xml) : null;
    }

}
