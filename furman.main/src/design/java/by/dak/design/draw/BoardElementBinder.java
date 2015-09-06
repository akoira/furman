package by.dak.design.draw;

import by.dak.design.draw.components.BoardElement;
import by.dak.design.draw.components.BoardFigure;
import org.jhotdraw.draw.event.FigureAdapter;
import org.jhotdraw.draw.event.FigureEvent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * User: akoyro
 * Date: 14.09.11
 * Time: 14:30
 */
public class BoardElementBinder extends FigureAdapter implements PropertyChangeListener
{
    private BoardElement boardElement;
    private BoardFigure boardFigure;

    private BoardElementAdjuster boardElementAdjuster = new BoardElementAdjuster();
    private BoardFigureAdjuster boardFigureAdjuster = new BoardFigureAdjuster();


    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {

    }

    public void figureChanged()
    {
        boardElement.setLength(boardFigure.getBounds().getWidth());
        boardElement.setHeight(boardFigure.getBounds().getHeight());
    }

    public void boxChanged()
    {
        boardFigure.willChange();
        boardFigure.setBounds(boardElement.getStartPointXY(), boardElement.getEndPointXY());
        boardFigure.changed();
    }

    public BoardElement getBoardElement()
    {
        return boardElement;
    }

    public void setBoardElement(BoardElement boardElement)
    {
        this.boardElement = boardElement;
    }

    public BoardFigure getBoardFigure()
    {
        return boardFigure;
    }

    public void setBoardFigure(BoardFigure boardFigure)
    {
        this.boardFigure = boardFigure;
    }

    public void bind()
    {
        bindBoardFigure();
        bindBoardElement();

        //set values
        boardFigureAdjuster.propertyChange(null);
    }

    private void bindBoardFigure()
    {
        this.boardFigure.addFigureListener(boardElementAdjuster);
    }

    private void bindBoardElement()
    {
        this.boardElement.addPropertyChangeListener(BoardElement.PROPERTY_x, boardFigureAdjuster);
        this.boardElement.addPropertyChangeListener(BoardElement.PROPERTY_y, boardFigureAdjuster);
        this.boardElement.addPropertyChangeListener(BoardElement.PROPERTY_length, boardFigureAdjuster);
        this.boardElement.addPropertyChangeListener(BoardElement.PROPERTY_height, boardFigureAdjuster);
        this.boardElement.addPropertyChangeListener(BoardElement.PROPERTY_angleZ, boardFigureAdjuster);
    }

    public void unbind()
    {
        unbindBoardFigure();
        unbindBoardElement();
    }

    private void unbindBoardFigure()
    {
        this.boardFigure.removeFigureListener(boardElementAdjuster);
    }

    private void unbindBoardElement()
    {
        this.boardElement.removePropertyChangeListener(BoardElement.PROPERTY_x, boardFigureAdjuster);
        this.boardElement.removePropertyChangeListener(BoardElement.PROPERTY_y, boardFigureAdjuster);
        this.boardElement.removePropertyChangeListener(BoardElement.PROPERTY_length, boardFigureAdjuster);
        this.boardElement.removePropertyChangeListener(BoardElement.PROPERTY_height, boardFigureAdjuster);
        this.boardElement.removePropertyChangeListener(BoardElement.PROPERTY_angleZ, boardFigureAdjuster);
    }

    private class BoardFigureAdjuster implements PropertyChangeListener
    {
        @Override
        public void propertyChange(PropertyChangeEvent evt)
        {
            unbindBoardFigure();
            boardFigure.willChange();
            boardFigure.setBounds(boardElement.getStartPointXY(), boardElement.getEndPointXY());
            boardFigure.changed();
            bindBoardFigure();
        }
    }

    private class BoardElementAdjuster extends FigureAdapter
    {
        @Override
        public void figureChanged(FigureEvent e)
        {
            unbindBoardElement();
            boardElement.setX(boardFigure.getPresentationFigure().getBounds().getCenterX());
            boardElement.setY(boardFigure.getPresentationFigure().getBounds().getCenterY());
            switch (boardFigure.getOrientation())
            {
                case Horizontal:
                    boardElement.setAngleZ(0);
                    boardElement.setLength(boardFigure.getPresentationFigure().getBounds().getWidth());
                    break;
                case Vertical:
                    boardElement.setLength(boardFigure.getPresentationFigure().getBounds().getHeight());
                    boardElement.setAngleZ(Math.PI / 2d);
                    break;
                default:
                    throw new IllegalArgumentException();
            }
            bindBoardElement();
        }
    }


}
