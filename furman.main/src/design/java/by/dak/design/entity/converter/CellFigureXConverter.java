package by.dak.design.entity.converter;

import by.dak.design.draw.components.BoardFigure;
import by.dak.design.draw.components.CellFigure;
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
 * Time: 5:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class CellFigureXConverter extends ADesignerXConverter<CellFigure>
{
    @Override
    public void marshal(Object o, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext)
    {
        if (o instanceof CellFigure)
        {
            if (((CellFigure) o).getParent() != null)
            {
                hierarchicalStreamWriter.startNode(Constants.child);
                hierarchicalStreamWriter.addAttribute(Constants.location, ((CellFigure) o).getLocation().name());
            }
            double width = ((CellFigure) o).getWidth();
            hierarchicalStreamWriter.addAttribute("width", String.valueOf(width));
            super.marshal(o, hierarchicalStreamWriter, marshallingContext);
            if (((CellFigure) o).getBoardFigure() != null)
            {
                hierarchicalStreamWriter.startNode(Constants.boardFigure);
                marshallingContext.convertAnother(((CellFigure) o).getBoardFigure());
                hierarchicalStreamWriter.endNode();
            }

            for (Figure figure : ((CellFigure) o).getChildren())
            {
                marshal(figure, hierarchicalStreamWriter, marshallingContext);
            }
            if (((CellFigure) o).getParent() != null)
            {
                hierarchicalStreamWriter.endNode();
            }
        }
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext)
    {
        if (hierarchicalStreamReader.getNodeName().equals(Constants.boardFigure))
        {
            BoardFigure boardFigure = (BoardFigure) unmarshallingContext.convertAnother(null, BoardFigure.class);
            if (boardFigure != null)
            {
                return boardFigure;
            }
            return null;
        }
        else
        {
            CellFigure parent = new CellFigure();
            String width = hierarchicalStreamReader.getAttribute("width");
            String startX = hierarchicalStreamReader.getAttribute(Constants.startX);
            String startY = hierarchicalStreamReader.getAttribute(Constants.startY);
            String endY = hierarchicalStreamReader.getAttribute(Constants.endY);
            String endX = hierarchicalStreamReader.getAttribute(Constants.endX);
            String location = hierarchicalStreamReader.getAttribute(Constants.location);

            Point2D.Double startPoint = new Point2D.Double(Double.valueOf(startX), Double.valueOf(startY));
            Point2D.Double endPoint = new Point2D.Double(Double.valueOf(endX), Double.valueOf(endY));

            parent.setBounds(startPoint, endPoint);
            parent.setWidth(Long.valueOf(width));
            if (location != null)
            {
                parent.setLocation(Enum.valueOf(CellFigure.Location.class, location));
            }

            while (hierarchicalStreamReader.hasMoreChildren())
            {
                hierarchicalStreamReader.moveDown();
                Figure figure = (Figure) unmarshal(hierarchicalStreamReader,
                        unmarshallingContext);
                if (figure instanceof CellFigure)
                {
                    CellFigure child = (CellFigure) figure;
                    parent.add(child);
                }
                else
                {
                    parent.setBoardFigure((BoardFigure) figure);
                }


                hierarchicalStreamReader.moveUp();
            }

            return parent;

        }

    }
}
