package by.dak.design.entity.converter;

import by.dak.design.draw.components.BoardElement;
import by.dak.design.draw.components.BoardFigure;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 7/28/11
 * Time: 12:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class BoardFigureXConverter extends ADesignerXConverter<BoardFigure>
{

    @Override
    public void marshal(Object o, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext)
    {
        if (o instanceof BoardFigure)
        {
            super.marshal(o, hierarchicalStreamWriter, marshallingContext);

            hierarchicalStreamWriter.addAttribute(Constants.orientation, ((BoardFigure) o).getOrientation().name());

            BoardElement element = ((BoardFigure) o).getBoardElement();
            if (element != null)
            {
                hierarchicalStreamWriter.startNode("element");
                marshallingContext.convertAnother(element, new DElementXConverter());
                hierarchicalStreamWriter.endNode();
            }
        }
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext)
    {
        BoardFigure boardFigure = new BoardFigure();
        String startX = hierarchicalStreamReader.getAttribute(Constants.startX);
        String startY = hierarchicalStreamReader.getAttribute(Constants.startY);
        String endY = hierarchicalStreamReader.getAttribute(Constants.endY);
        String endX = hierarchicalStreamReader.getAttribute(Constants.endX);
        String orientation = hierarchicalStreamReader.getAttribute(Constants.orientation);
        if (hierarchicalStreamReader.hasMoreChildren())
        {
            hierarchicalStreamReader.moveDown();
            if (hierarchicalStreamReader.getNodeName().equals("element"))
            {
                BoardElement element = (BoardElement) unmarshallingContext.convertAnother(boardFigure, BoardElement.class,
                        new DElementXConverter());
                if (element != null)
                {
                    boardFigure.setBoardElement(element);
                }
            }
            hierarchicalStreamReader.moveUp();

        }

        Point2D.Double startPoint = new Point2D.Double(Double.valueOf(startX), Double.valueOf(startY));
        Point2D.Double endPoint = new Point2D.Double(Double.valueOf(endX), Double.valueOf(endY));

        boardFigure.setOrientation(Enum.valueOf(BoardFigure.Orientation.class, orientation));
        boardFigure.setBounds(startPoint, endPoint);

        return boardFigure;
    }
}
