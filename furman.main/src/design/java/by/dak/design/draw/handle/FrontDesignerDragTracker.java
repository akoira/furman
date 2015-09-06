package by.dak.design.draw.handle;

import by.dak.design.draw.FrontDesignerDrawing;
import by.dak.design.draw.components.BoardFigure;
import by.dak.design.draw.components.CellFigure;
import by.dak.design.draw.components.DimensionFigure;
import org.jhotdraw.draw.Figure;

import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 6/24/11
 * Time: 4:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class FrontDesignerDragTracker extends ADesignerDragTracker
{
    private BoardSizeConstrainerHandle boardSizeConstrainerHandle;
    private CellFigureSizeHandler cellSizeHandler;
    /**
     * хранит размеры cell, чтобы board не вышел за пределы
     */
    private CellFigure originCellFigure;
    private BoardLocationConstrainerHandle boundsConstrainerHandle;


    public FrontDesignerDragTracker(Figure figure)
    {
        super(figure);
    }

    public FrontDesignerDragTracker()
    {
        super();
    }

    @Override
    public void mousePressed(MouseEvent evt)
    {
        super.mousePressed(evt);
        trackStart(getView().viewToDrawing(evt.getPoint()));
    }

    @Override
    public void mouseDragged(MouseEvent e)
    {
        if (anchorFigure instanceof BoardFigure)
        {
            super.mouseDragged(e);
            trackStep(getView().viewToDrawing(e.getPoint()));
        }
        else if (anchorFigure instanceof DimensionFigure)
        {
            DimensionPositionHandler dimensionPositionHandler = new DimensionPositionHandler((DimensionFigure) anchorFigure);
            dimensionPositionHandler.trackStep(viewToDrawing(e.getPoint()));
        }
    }

    @Override
    public void mouseReleased(MouseEvent evt)
    {
        super.mouseReleased(evt);
        trackEnd(getView().viewToDrawing(evt.getPoint()));
    }

    private BoardSizeConstrainerHandle getBoardSizeConstrainer()
    {
        if (boardSizeConstrainerHandle == null)
        {
            boardSizeConstrainerHandle = new BoardSizeConstrainerHandle((BoardFigure) anchorFigure);
        }
        return boardSizeConstrainerHandle;
    }

    private void trackStart(Point2D.Double point)
    {
        if (anchorFigure instanceof BoardFigure)
        {
            boardSizeConstrainerHandle = getBoardSizeConstrainer();
            boardSizeConstrainerHandle.setDrawing((FrontDesignerDrawing) getDrawing());
            originCellFigure = ((FrontDesignerDrawing) getDrawing()).findCellFigure(
                    new Point2D.Double(anchorFigure.getBounds().getCenterX(), anchorFigure.getBounds().getCenterY()));
            if (originCellFigure != null)
            {
                cellSizeHandler = new CellFigureSizeHandler(originCellFigure);
                cellSizeHandler.setDrawing((FrontDesignerDrawing) getDrawing());
            }
            if (originCellFigure != null)
            {
                boundsConstrainerHandle = new BoardLocationConstrainerHandle(originCellFigure);
            }
        }

    }

    private void trackStep(Point2D.Double point)
    {
        constrainBoard();
        if (cellSizeHandler != null)
        {
            cellSizeHandler.trackStep((BoardFigure) anchorFigure);
        }
    }


    private void trackEnd(Point2D.Double point)
    {
        if (anchorFigure instanceof BoardFigure)
        {
            if (originCellFigure != null)
            {
                BoardMagneticHandler magneticHandler = new BoardMagneticHandler(originCellFigure,
                        (BoardFigure) anchorFigure, ((FrontDesignerDrawing) getDrawing()).getTopFigure());
                magneticHandler.magnetize();
            }
            constrainBoard();
            delimCell(point);

            if (cellSizeHandler != null)
            {
                cellSizeHandler.trackEnd((BoardFigure) anchorFigure);
            }


            clearHandlers();
        }
    }

    private void delimCell(Point2D.Double point)
    {
        CellFigure cellFigure = ((FrontDesignerDrawing) getDrawing()).findCellFigure(point);
        if (cellFigure != null)
        {
            if (!((FrontDesignerDrawing) getDrawing()).getTopFigure().contains(anchorOrigin))
            {
                getBoardSizeConstrainer().setDrawing((FrontDesignerDrawing) getDrawing());
                getBoardSizeConstrainer().trackEnd(point);

                CellDelimeterHandle cellDelimeterHandle = new CellDelimeterHandle(cellFigure);
                cellDelimeterHandle.delim((BoardFigure) anchorFigure);
            }
        }
    }

    private void constrainBoard()
    {
        if (boundsConstrainerHandle != null)
        {
            boundsConstrainerHandle.constrainDraggedFigure((BoardFigure) anchorFigure);
        }
    }

    private void clearHandlers()
    {
        boardSizeConstrainerHandle = null;
        cellSizeHandler = null;
        originCellFigure = null;
        boundsConstrainerHandle = null;
    }


}
