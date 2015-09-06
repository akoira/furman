package by.dak.design.entity.converter;

import by.dak.design.draw.components.DimensionFigure;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 7/28/11
 * Time: 3:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class DimensionFigureXConverter extends ADesignerXConverter<DimensionFigure>
{
    @Override
    public void marshal(Object o, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext)
    {
        if (o instanceof DimensionFigure)
        {
            super.marshal(o, hierarchicalStreamWriter, marshallingContext);
            double offset = ((DimensionFigure) o).getOffset();

            hierarchicalStreamWriter.addAttribute("offset", String.valueOf(offset));

        }
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext)
    {
        DimensionFigure dimensionFigure = new DimensionFigure();
        String startX = hierarchicalStreamReader.getAttribute(Constants.startX);
        String startY = hierarchicalStreamReader.getAttribute(Constants.startY);
        String endY = hierarchicalStreamReader.getAttribute(Constants.endY);
        String endX = hierarchicalStreamReader.getAttribute(Constants.endX);
        double offset = Double.valueOf(hierarchicalStreamReader.getAttribute("offset"));

        Point2D.Double startPoint = new Point2D.Double(Double.valueOf(startX), Double.valueOf(startY));
        Point2D.Double endPoint = new Point2D.Double(Double.valueOf(endX), Double.valueOf(endY));

        dimensionFigure.setOffset(offset);

        dimensionFigure.setStartPoint(startPoint);
        dimensionFigure.setEndPoint(endPoint);

        return dimensionFigure;
    }
}
