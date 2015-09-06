package by.dak.design.draw.handle;

import by.dak.design.draw.components.BoardFigure;
import by.dak.design.draw.components.CellFigure;

import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 6/27/11
 * Time: 3:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class CellDelimeterHandle
{
    private CellFigure parentCellFigure;

    public CellDelimeterHandle(CellFigure parentCellFigure)
    {
        this.parentCellFigure = parentCellFigure;
    }

    public void delim(BoardFigure delimeter)
    {
        double length1 = 0;
        double height1 = 0;
        double length2 = 0;
        double height2 = 0;


        switch (delimeter.getOrientation())
        {
            case Horizontal:
                length1 = parentCellFigure.getBounds().getWidth();
                height1 = delimeter.getBounds().getMinY() - parentCellFigure.getBounds().getMinY();
                length2 = parentCellFigure.getBounds().getWidth();
                height2 = parentCellFigure.getBounds().getHeight() - height1 - delimeter.getBounds().getHeight();
                break;
            case Vertical:
                height1 = parentCellFigure.getBounds().getHeight();
                length1 = delimeter.getBounds().getMinX() - parentCellFigure.getBounds().getMinX();
                height2 = parentCellFigure.getBounds().getHeight();
                length2 = parentCellFigure.getBounds().getWidth() - length1 - delimeter.getBounds().getWidth();
                break;
            default:
                break;
        }

        createDelimedFigures(delimeter, height1, height2, length1, length2);
        if (delimeter.getBoardElement().getBoardDef().getDefaultWidth().equals(delimeter.getBoardElement().getWidth()))
        {
            delimeter.getBoardElement().setWidth(parentCellFigure.getWidth());
        }
    }

    private void delimV(BoardFigure delimeter)
    {
        Point2D.Double startPoint = this.parentCellFigure.getStartPoint();

    }

    private void createDelimedFigures(BoardFigure delimeter, double height1, double height2, double length1, double length2)
    {
        parentCellFigure.removeAllChildren();

        CellFigure cellFigureChild1 = new CellFigure();
        CellFigure cellFigureChild2 = new CellFigure();

        cellFigureChild1.setWidth(parentCellFigure.getWidth());
        cellFigureChild2.setWidth(parentCellFigure.getWidth());

        Point2D.Double startPoint1 = new Point2D.Double();
        Point2D.Double startPoint2 = new Point2D.Double();

        CellFigure.Location location1 = null;
        CellFigure.Location location2 = null;

        switch (delimeter.getOrientation())
        {
            case Horizontal:
                startPoint1 = parentCellFigure.getStartPoint();
                startPoint2 = new Point2D.Double(startPoint1.x,
                        startPoint1.y + height1 + delimeter.getBoardElement().getBoardDef().getThickness());
                location1 = CellFigure.Location.Top;
                location2 = CellFigure.Location.Bottom;
                break;
            case Vertical:
                startPoint1 = parentCellFigure.getStartPoint();
                startPoint2 = new Point2D.Double(startPoint1.x + length1 + delimeter.getBoardElement().getBoardDef().getThickness(),
                        startPoint1.y);
                location1 = CellFigure.Location.Left;
                location2 = CellFigure.Location.Right;
                break;
            default:
                break;
        }

        cellFigureChild1.setBounds(startPoint1,
                new Point2D.Double(startPoint1.x + length1, startPoint1.y + height1));
        cellFigureChild2.setBounds(startPoint2,
                new Point2D.Double(startPoint2.x + length2, startPoint2.y + height2));
        cellFigureChild1.setLocation(location1);
        cellFigureChild2.setLocation(location2);

        CellNumerationHandler cellNumerationHandler = new CellNumerationHandler(parentCellFigure);
        cellNumerationHandler.initNumeration(cellFigureChild1, cellFigureChild2);
        parentCellFigure.add(cellFigureChild1);
        parentCellFigure.add(cellFigureChild2);
        parentCellFigure.setBoardFigure(delimeter);
    }


}
