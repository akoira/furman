package by.dak.cutting.doors;

import by.dak.cutting.swing.order.cellcomponents.editors.milling.ElementDrawing;
import org.jhotdraw.draw.DefaultDrawing;

import java.awt.*;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 24.08.2009
 * Time: 16:34:37
 * To change this template use File | Settings | File Templates.
 */
public class CellDrawing extends DefaultDrawing
{
    private ElementDrawing elementDrawing;
    private List<Cell> cells;

    @Override
    public void draw(Graphics2D g)
    {
        Color color = g.getColor();
        Stroke stroke = g.getStroke();

        if (!cells.isEmpty())
        {
            int step = (255 - 150) / cells.size();
            for (Cell cell : cells)
            {
                cell.setColor(new Color(0, 150 + step * cells.indexOf(cell), 0));
                g.setColor(cell.getColor());
                g.fill(cell.getPath());
            }
            repitedCell(g);
        }
        g.setStroke(stroke);
        g.setColor(color);

        super.drawChildren(g);
    }

    public ElementDrawing getElementDrawing()
    {
        return elementDrawing;
    }

    public void setElementDrawing(ElementDrawing elementDrawing)
    {
        removeAllChildren();
        addAll(elementDrawing.getChildren());
        this.elementDrawing = elementDrawing;
    }

    public List<Cell> getCells()
    {
        return cells;
    }

    public void setCells(List<Cell> cells)
    {
        this.cells = cells;
    }

    private void repitedCell(Graphics2D g)
    {
        for (int i = 0; i < cells.size(); i++)
        {
            Cell cell = cells.get(i);
            for (int j = i + 1; j < cells.size(); j++)
            {
                Cell c = cells.get(j);
                if (cell.getBoardDefEntity().equals(c.getBoardDefEntity()) &&
                        cell.getTextureEntity().equals(c.getTextureEntity()))
                {
                    c.setColor(cell.getColor());
                    g.setColor(c.getColor());
                    g.fill(c.getPath());
                }
            }
        }
    }
}
