package by.dak.design.draw.components.convert;

import by.dak.design.draw.FrontDesignerDrawing;
import by.dak.design.draw.TopDesignerDrawing;
import by.dak.design.draw.components.BoardElement;
import by.dak.design.draw.components.BoardFigure;
import by.dak.design.draw.components.CellFigure;
import by.dak.model3d.DBoxFacade;
import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.draw.Figure;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 24.08.11
 * Time: 19:27
 * To change this template use File | Settings | File Templates.
 */
public class DesignerFront2TopConverter
{

    private DBoxFacade boxFacade;
    private TopDesignerDrawing topDesignerDrawing;
    private FrontDesignerDrawing frontDesignerDrawing;

    public void convert()
    {
        CellFigure topCellFigure = boxFacade.getTopCellFigureBy(frontDesignerDrawing.getTopFigure().getBox());


        topDesignerDrawing.setTopFigure(topCellFigure);
        topDesignerDrawing.addDimensionsTo(topCellFigure,
                (int) Math.min(topCellFigure.getBounds().getWidth(), topCellFigure.getBounds().getHeight()) / 3);

        List<BoardFigure> frontBoardFigures = new ArrayList<BoardFigure>();
        findFrontBoards(frontDesignerDrawing.getTopFigure(), frontBoardFigures);
        sortBottom2Top(frontBoardFigures);
        showConvertedBoards(convertBoards(frontBoardFigures));
    }

    private void sortBottom2Top(List<BoardFigure> frontBoardFigures)
    {
        Collections.sort(frontBoardFigures, new Comparator<BoardFigure>()
        {
            @Override
            public int compare(BoardFigure boardFigure1, BoardFigure boardFigure2)
            {
                if (boardFigure1.getBounds().getCenterY() < boardFigure2.getBounds().getCenterY())
                {
                    return 1;
                }
                else if (boardFigure1.getBounds().getCenterY() > boardFigure2.getBounds().getCenterY())
                {
                    return -1;
                }
                else
                {
                    return 0;
                }
            }
        });
    }

    /**
     * вынимает из ячеек boards
     *
     * @param frontCellFigure
     * @param frontBoardFigures
     */
    private void findFrontBoards(CellFigure frontCellFigure, List<BoardFigure> frontBoardFigures)
    {
        BoardFigure frontBoard = (frontCellFigure).getBoardFigure();
        if (frontBoard != null)
        {
            frontBoardFigures.add(frontBoard);
            for (Figure child : frontCellFigure.getChildren())
            {
                findFrontBoards((CellFigure) child, frontBoardFigures);
            }
        }
    }

    private void showConvertedBoards(List<BoardFigure> convertedBoardFigures)
    {
        for (BoardFigure boardFigure : convertedBoardFigures)
        {
            topDesignerDrawing.add(boardFigure);
        }
    }

    private List<BoardFigure> convertBoards(List<BoardFigure> frontBoardFigures)
    {
        List<BoardFigure> convertedBoardFigures = new ArrayList<BoardFigure>();
        for (BoardFigure frontBoardFigure : frontBoardFigures)
        {
            BoardFigure topBoardFigure = new BoardFigure();
            topBoardFigure.setOrientation(frontBoardFigure.getOrientation());
            BoardElement element = frontBoardFigure.getBoardElement();
            topBoardFigure.setBoardElement(element);
            topBoardFigure.getNumerationTip().setVisible(frontBoardFigure.getNumerationTip().isVisible());

            Point2D.Double startPoint = new Point2D.Double(frontBoardFigure.getStartPoint().x,
                    topDesignerDrawing.getTopFigure().getStartPoint().y);
            Point2D.Double endPoint = null;
            switch (frontBoardFigure.getOrientation())
            {
                case Horizontal:
                    endPoint = new Point2D.Double(startPoint.x + frontBoardFigure.getBoardElement().getLength(),
                            startPoint.y + frontBoardFigure.getBoardElement().getWidth());
                    break;
                case Vertical:
                    endPoint = new Point2D.Double(startPoint.x + frontBoardFigure.getBoardElement().getBoardDef().getThickness(),
                            startPoint.y + frontBoardFigure.getBoardElement().getWidth());
                    break;
            }
            topBoardFigure.setBounds(startPoint, endPoint);

            topBoardFigure.set(AttributeKeys.CANVAS_FILL_OPACITY, 0.3);
            topBoardFigure.setTransformable(false);
            convertedBoardFigures.add(topBoardFigure);
        }
        return convertedBoardFigures;
    }


    private CellFigure convert(CellFigure frontCellFigure)
    {
        CellFigure cellFigure = new CellFigure();

        Point2D.Double startPoint = new Point2D.Double(
                frontCellFigure.getStartPoint().x,
                frontDesignerDrawing.getTopFigure().getStartPoint().y);
        Point2D.Double endPoint = new Point2D.Double(startPoint.x + frontCellFigure.getBounds().getWidth(),
                startPoint.y + ((CellFigure) frontCellFigure).getWidth());

        cellFigure.setBounds(startPoint, endPoint);

        return cellFigure;
    }


    public DBoxFacade getBoxFacade()
    {
        return boxFacade;
    }

    public void setBoxFacade(DBoxFacade boxFacade)
    {
        this.boxFacade = boxFacade;
    }

    public TopDesignerDrawing getTopDesignerDrawing()
    {
        return topDesignerDrawing;
    }

    public void setTopDesignerDrawing(TopDesignerDrawing topDesignerDrawing)
    {
        this.topDesignerDrawing = topDesignerDrawing;
    }

    public FrontDesignerDrawing getFrontDesignerDrawing()
    {
        return frontDesignerDrawing;
    }

    public void setFrontDesignerDrawing(FrontDesignerDrawing frontDesignerDrawing)
    {
        this.frontDesignerDrawing = frontDesignerDrawing;
    }

}
