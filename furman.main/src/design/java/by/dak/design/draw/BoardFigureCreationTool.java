package by.dak.design.draw;

import by.dak.design.draw.components.BoardFigure;
import by.dak.design.draw.components.CellFigure;
import by.dak.design.draw.handle.BoardNumerationHandler;
import by.dak.design.draw.handle.BoardSizeConstrainerHandle;
import by.dak.design.draw.handle.CellDelimeterHandle;
import org.jhotdraw.draw.*;
import org.jhotdraw.draw.tool.CreationTool;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 6/24/11
 * Time: 11:45 AM
 * To change this template use File | Settings | File Templates.
 */


/**
 * При создании новой фигуры, запрещается протягивание(только клик)
 * После клика устанавливаются те размеры, которые уже имеет сама фигура
 */
public class BoardFigureCreationTool extends CreationTool
{
    public static final double DEFAULT_length = 100;
    public static final double DEFAULT_width = 100;


    public BoardFigureCreationTool(BoardFigure prototype)
    {
        super(prototype);
    }


    @Override
    protected Figure createFigure()
    {
        BoardFigure f = (BoardFigure) prototype.clone();
        getEditor().applyDefaultAttributesTo(f);
        if (prototypeAttributes != null)
        {
            for (Map.Entry<AttributeKey, Object> entry : prototypeAttributes.entrySet())
            {
                f.set(entry.getKey(), entry.getValue());
            }
        }
        f.setBoardElement(BoardElementFacade.getInstance().createBoardElement());
        BoardElementFacade.getInstance().bind(f.getBoardElement(), f);
        return f;
    }

    @Override
    public void mouseDragged(MouseEvent evt)
    {
    }

    //todo возможно setBounds для создаваемой фигуры не правильно устанавливаются: просиходит constrain,
    // который или нужно убрать или для всех объектов использовать

    @Override
    public void mousePressed(MouseEvent evt)
    {
        DrawingView view = editor.findView((Container) evt.getSource());
        view.requestFocus();
        anchor = new Point(evt.getX(), evt.getY());
        isWorking = true;
        fireToolStarted(view);

        getView().clearSelection();
        createdFigure = createFigure();
        Point2D.Double pAnchor = constrainPoint(viewToDrawing(anchor));
        anchor.x = evt.getX();
        anchor.y = evt.getY();

        Point lead = new Point(evt.getX() + (int) createdFigure.getBounds().getWidth(),
                evt.getY() + (int) createdFigure.getBounds().getHeight());

        if (getDrawing().findFigure(getView().viewToDrawing(evt.getPoint())) instanceof BoardFigure)
        {
            fireToolDone();
        }
        else
        {
            getDrawing().add(createdFigure);
        }
    }

    @Override
    public void mouseReleased(MouseEvent evt)
    {
        if (createdFigure != null)
        {
            Rectangle2D.Double bounds = createdFigure.getBounds();
            if (bounds.width == 0 && bounds.height == 0)
            {
                getDrawing().remove(createdFigure);
                if (isToolDoneAfterCreation())
                {
                    fireToolDone();
                }
            }
            else
            {
                if (Math.abs(anchor.x - evt.getX()) < minimalSizeTreshold.width &&
                        Math.abs(anchor.y - evt.getY()) < minimalSizeTreshold.height)
                {
                    createdFigure.willChange();
                    createdFigure.setBounds(
                            constrainPoint(new Point(anchor.x, anchor.y)),
                            constrainPoint(new Point(
                                    anchor.x + (int) createdFigure.getBounds().getWidth(),
                                    anchor.y + (int) createdFigure.getBounds().getHeight())));
                    createdFigure.changed();
                }
                if (createdFigure instanceof CompositeFigure)
                {
                    ((CompositeFigure) createdFigure).layout();
                }
                final Figure addedFigure = createdFigure;
                final Drawing addedDrawing = getDrawing();
                getDrawing().fireUndoableEditHappened(new AbstractUndoableEdit()
                {

                    @Override
                    public String getPresentationName()
                    {
                        return presentationName;
                    }

                    @Override
                    public void undo() throws CannotUndoException
                    {
                        super.undo();
                        addedDrawing.remove(addedFigure);
                    }

                    @Override
                    public void redo() throws CannotRedoException
                    {
                        super.redo();
                        addedDrawing.add(addedFigure);
                    }
                });
                Rectangle r = new Rectangle(anchor.x, anchor.y, 0, 0);
                r.add(evt.getX(), evt.getY());
                maybeFireBoundsInvalidated(r);
                creationFinished(createdFigure);

                adjustFigure(evt, (BoardFigure) addedFigure);

//                firePropertyChange(PROPERTY_addedFigure, null, addedFigure);
                createdFigure = null;
            }
        }
        else
        {
            if (isToolDoneAfterCreation())
            {
                fireToolDone();
            }
        }

    }

    private void adjustFigure(MouseEvent evt, BoardFigure addedFigure)
    {
        BoardSizeConstrainerHandle boardSizeConstrainerHandle = new BoardSizeConstrainerHandle(addedFigure);
        boardSizeConstrainerHandle.setDrawing((FrontDesignerDrawing) getDrawing());
        boardSizeConstrainerHandle.trackEnd(getView().viewToDrawing(evt.getPoint()));
        CellFigure cellFigure = ((FrontDesignerDrawing) getDrawing()).
                findCellFigure(getView().viewToDrawing(evt.getPoint()));
        if (cellFigure != null)
        {
            CellDelimeterHandle cellDelimeterHandle = new CellDelimeterHandle(cellFigure);
            cellDelimeterHandle.delim(addedFigure);
        }

        BoardNumerationHandler boardNumerationHandler = new BoardNumerationHandler((FrontDesignerDrawing) getDrawing());
        boardNumerationHandler.initNumeration(addedFigure);
    }
}
