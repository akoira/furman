package by.dak.design.swing.action;

import by.dak.design.draw.BoardFigureCreationTool;
import by.dak.design.draw.components.BoardElement;
import by.dak.design.draw.components.BoardFigure;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 08.08.11
 * Time: 0:11
 * To change this template use File | Settings | File Templates.
 */
public class BoardSelectionController
{
    private DElementChangeListener dElementChangeListener = new DElementChangeListener();
    private BoardAddListener boardAddListener = new BoardAddListener();
    private BoardFigureCreationTool boardFigureCreationTool;

    public BoardSelectionController(BoardFigureCreationTool boardFigureCreationTool)
    {
        this.boardFigureCreationTool = boardFigureCreationTool;

        initEnv();
    }

    private void initEnv()
    {
        //boardFigureCreationTool.addPropertyChangeListener(BoardFigureCreationTool., boardAddListener);
    }

    private class BoardAddListener implements PropertyChangeListener
    {
        @Override
        public void propertyChange(PropertyChangeEvent evt)
        {
            BoardFigure boardFigure = (BoardFigure) evt.getNewValue();
//            boardFigure.getBoardElement().addPropertyChangeListener(BoardElement.PROPERTY_priceAware, dElementChangeListener);
//            boardFigure.getBoardElement().addPropertyChangeListener(BoardElement.PROPERTY_priced, dElementChangeListener);
        }
    }

    private class DElementChangeListener implements PropertyChangeListener
    {
        @Override
        public void propertyChange(PropertyChangeEvent evt)
        {

            if (evt.getPropertyName().equals(BoardElement.PROPERTY_boardDef))
            {
//                boardFigureCreationTool.get().setBoardDef((BoardDef) evt.getNewValue());
            }
            else
            {
//                boardFigureCreationTool.getElement().setTexture((TextureEntity) evt.getNewValue());
            }


        }
    }


}
