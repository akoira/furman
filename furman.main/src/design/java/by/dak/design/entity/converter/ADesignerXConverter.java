package by.dak.design.entity.converter;

import by.dak.utils.GenericUtils;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import org.jhotdraw.draw.Figure;

import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 7/26/11
 * Time: 4:57 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class ADesignerXConverter<F extends Figure> implements Converter
{
    @Override
    public void marshal(Object o, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext)
    {
        if(o instanceof Figure)
        {
            Point2D.Double startPoint = ((Figure) o).getStartPoint();
            double startX = startPoint.x;
            double startY = startPoint.y;
            Point2D.Double endPoint = ((Figure) o).getEndPoint();
            double endX = endPoint.x;
            double endY = endPoint.y;
            hierarchicalStreamWriter.addAttribute(Constants.startX, String.valueOf(startX));
            hierarchicalStreamWriter.addAttribute(Constants.startY, String.valueOf(startY));
            hierarchicalStreamWriter.addAttribute(Constants.endX, String.valueOf(endX));
            hierarchicalStreamWriter.addAttribute(Constants.endY, String.valueOf(endY));
        }

    }

    @Override
    public abstract Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext);

    @Override
    public boolean canConvert(Class aClass)
    {
        return aClass == GenericUtils.getParameterClass(this.getClass(), 0);
    }
}
