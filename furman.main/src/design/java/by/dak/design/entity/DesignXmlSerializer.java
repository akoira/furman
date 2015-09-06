package by.dak.design.entity;

import by.dak.design.draw.FrontDesignerDrawing;
import by.dak.design.draw.components.BoardFigure;
import by.dak.design.draw.components.CellFigure;
import by.dak.design.draw.components.DimensionFigure;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.apache.commons.lang.StringUtils;
import org.jhotdraw.draw.Figure;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 7/26/11
 * Time: 3:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class DesignXmlSerializer
{
    private static final DesignXmlSerializer instance = new DesignXmlSerializer();
    private XStream xstream;

    public static DesignXmlSerializer getInstance()
    {
        return instance;
    }

    private DesignXmlSerializer()
    {
        xstream = new XStream(new DomDriver());
        xstream.processAnnotations(FrontDesignerDrawing.class);
        xstream.processAnnotations(BoardFigure.class);
        xstream.processAnnotations(CellFigure.class);
        xstream.processAnnotations(DimensionFigure.class);
    }

    public String serialize(Figure figure)
    {
        return figure != null ? xstream.toXML(figure) : null;
    }

    public Object unserialize(String xml)
    {
        return !StringUtils.isBlank(xml) ? xstream.fromXML(xml) : null;
    }
}
