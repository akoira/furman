package by.dak.design.entity.converter;

import by.dak.design.draw.FrontDesignerDrawing;
import by.dak.design.draw.components.*;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import org.jhotdraw.draw.Figure;

import java.awt.geom.Point2D;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 7/28/11
 * Time: 6:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class DesignerDrawingXConverter extends ADesignerXConverter<FrontDesignerDrawing>
{
    @Override
    public void marshal(Object o, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext)
    {
        if (o instanceof FrontDesignerDrawing)
        {
            List<Figure> children = ((FrontDesignerDrawing) o).getChildren();
            for (Figure figure : children)
            {
                if (figure instanceof CellFigure)
                {
                    hierarchicalStreamWriter.startNode("cell");
                    marshallingContext.convertAnother(figure);
                }
                else if (figure instanceof DimensionFigure)
                {
                    hierarchicalStreamWriter.startNode("dimension");
                    marshallingContext.convertAnother(figure);
                }
                else
                {        //хранить boardFigure не надо - его берём из cell
                    continue;
                }
                hierarchicalStreamWriter.endNode();
            }
        }
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext)
    {
        FrontDesignerDrawing drawing = new FrontDesignerDrawing();

        while (hierarchicalStreamReader.hasMoreChildren())
        {
            hierarchicalStreamReader.moveDown();

            if (hierarchicalStreamReader.getNodeName().equals("cell"))
            {
                CellFigure cellFigure =
                        (CellFigure) unmarshallingContext.convertAnother(drawing, CellFigure.class);
                if (cellFigure != null)
                {
                    drawing.setTopFigure(cellFigure);
                    addBoardFigureToDrawing(cellFigure, drawing);
                }
            }
            else if (hierarchicalStreamReader.getNodeName().equals("dimension"))
            {
                DimensionFigure dimensionFigure =
                        (DimensionFigure) unmarshallingContext.convertAnother(drawing, DimensionFigure.class);
                if (dimensionFigure != null)
                {
                    drawing.add(dimensionFigure);
                }
            }

            hierarchicalStreamReader.moveUp();
        }
        updateConnectors(drawing);

        return drawing;
    }

    private void addBoardFigureToDrawing(CellFigure cellFigure, FrontDesignerDrawing drawing)
    {
        BoardFigure boardFigure = cellFigure.getBoardFigure();
        if (boardFigure != null)
        {
            drawing.add(boardFigure);
        }
        for (Figure figure : cellFigure.getChildren())
        {
            addBoardFigureToDrawing((CellFigure) figure, drawing);
        }
    }

    private void updateConnectors(FrontDesignerDrawing drawing)
    {
        for (Figure figure : drawing.getChildren())
        {
            if (figure instanceof DimensionFigure)
            {
                Point2D.Double startPoint = figure.getStartPoint();
                Point2D.Double endPoint = figure.getEndPoint();

                DimensionConnector startConnector = findConnectorBy(startPoint, drawing);
                DimensionConnector endConnector = findConnectorBy(endPoint, drawing);

                ((DimensionFigure) figure).setStartConnector(startConnector);
                ((DimensionFigure) figure).setEndConnector(endConnector);
            }
        }
    }

    private DimensionConnector findConnectorBy(Point2D.Double point, FrontDesignerDrawing drawing)
    {
        Figure figure = drawing.findFigure(point);
        DimensionConnector dimensionConnector = null;
        if (figure instanceof Connectable)
        {
            dimensionConnector = ((Connectable) figure).getConnector(point);
        }
        return dimensionConnector;
    }
}
