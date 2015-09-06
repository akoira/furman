package by.dak.design.draw.handle;

import by.dak.design.draw.components.CellFigure;
import org.jhotdraw.draw.Figure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 11.09.11
 * Time: 11:23
 * To change this template use File | Settings | File Templates.
 */
public class CellNumerationHandler
{
    private CellFigure parentCellFigure;

    public CellNumerationHandler(CellFigure parentCellFigure)
    {
        this.parentCellFigure = parentCellFigure;
    }

    /**
     * нумерует чайлдов после деления пополам
     *
     * @param child1
     * @param child2
     */
    public void initNumeration(CellFigure child1, CellFigure child2)
    {
        child1.setNumeration(parentCellFigure.getNumeration());
        int quantity = calcCellsQuantity(findTopCellFgiure(parentCellFigure));
        child2.setNumeration(quantity + 1);

        showNumerationFigures(child1, child2, parentCellFigure.getNumerationTip().isVisible());
    }

    private void showNumerationFigures(CellFigure child1, CellFigure child2, boolean show)
    {
        child1.getNumerationTip().setVisible(show);
        child2.getNumerationTip().setVisible(show);
    }

    /**
     * пересчёт нумерации
     */
    public void recalcNumeration()
    {
        List<CellFigure> cellFigures = new ArrayList<CellFigure>();
        findLastChilds(findTopCellFgiure(parentCellFigure), cellFigures);
        Collections.sort(cellFigures, new Comparator<CellFigure>()
        {
            @Override
            public int compare(CellFigure o1, CellFigure o2)
            {
                if (o1.getNumeration() < o2.getNumeration())
                {
                    return -1;
                }
                else if (o1.getNumeration() > o2.getNumeration())
                {
                    return 1;
                }
                else
                {
                    return 0;
                }
            }
        });
        if (cellFigures.size() > 0)
        {
            CellFigure cellFigure = cellFigures.get(0);
            if (cellFigure.getNumeration() != 1)
            {
                cellFigure.setNumeration(1);
            }
        }

        for (int count = 1; count < cellFigures.size(); count++)
        {
            if (cellFigures.get(count).getNumeration() - cellFigures.get(count - 1).getNumeration() > 1)
            {
                cellFigures.get(count).willChange();
                cellFigures.get(count).setNumeration(cellFigures.get(count).getNumeration() - 1);
                cellFigures.get(count).changed();
            }
            else if (cellFigures.get(count).getNumeration() - cellFigures.get(count - 1).getNumeration() < 1)
            {
                cellFigures.get(count).willChange();
                cellFigures.get(count).setNumeration(cellFigures.get(count).getNumeration() + 1);
                cellFigures.get(count).changed();
            }
        }
    }


    private CellFigure findTopCellFgiure(CellFigure parentCellFigure)
    {
        if (parentCellFigure.getParent() != null)
        {
            return findTopCellFgiure(parentCellFigure.getParent());
        }
        return parentCellFigure;
    }

    private int calcCellsQuantity(CellFigure cellFigure)
    {
        int quantity = 0;
        if (cellFigure.getChildCount() == 0)
        {
            quantity++;
        }
        for (Figure figure : cellFigure.getChildren())
        {
            quantity = calcCellsQuantity((CellFigure) figure) + quantity;
        }
        return quantity;
    }


    private void findLastChilds(CellFigure cellFigure, List<CellFigure> cellFigures)
    {
        if (cellFigure.getChildCount() == 0)
        {
            if (cellFigure.getBounds().getHeight() > 1 && cellFigure.getBounds().getWidth() > 1)
            {
                cellFigures.add(cellFigure);
            }
        }
        for (Figure figure : cellFigure.getChildren())
        {
            findLastChilds((CellFigure) figure, cellFigures);
        }
    }

}
