package by.dak.draw;

import org.jhotdraw.draw.DefaultDrawing;
import org.jhotdraw.draw.Figure;

import java.awt.geom.Point2D;
import java.util.Collection;

/**
 * User: akoyro
 * Date: 13.09.2009
 * Time: 18:31:19
 */
public class DefaultHierarchicalDrawing extends DefaultDrawing implements ShowDimention
{
    /**
     * Если данное свойство установлено в true работает hierarchical функцмональность.
     */
    private boolean hierarchicalEnabled = false;

    public void add(int index, Figure figure)
    {
        Point2D.Double center = new Point2D.Double(figure.getBounds().getCenterX(), figure.getBounds().getCenterY());
        Figure f = findChild(center);
        if (isHierarchicalEnabled() && f instanceof DefaultHierarchicalDrawing)
        {
            ((DefaultHierarchicalDrawing) f).add(((DefaultHierarchicalDrawing) f).getChildCount(), figure);
        }
        else
        {
            basicAdd(index, figure);
            //устаналеваем парента для добавленного элемента
            if (figure instanceof ChildFigure)
            {
                ((ChildFigure) figure).setParent(this);
            }
            if (getDrawing() != null)
            {
                figure.addNotify(getDrawing());
            }
            fireFigureAdded(figure, index);
            invalidate();
        }
    }

    public Figure findFigure(Point2D.Double p)
    {
        for (Figure f : getFiguresFrontToBack())
        {
            if (f.isVisible() && f.contains(p))
            {
                if (isHierarchicalEnabled())
                {
                    if (f instanceof DefaultHierarchicalDrawing)
                    {
                        Figure figure = ((DefaultHierarchicalDrawing) f).findFigure(p);
                        if (figure != null)
                        {
                            f = figure;
                        }
                    }
                }
                return f;
            }
        }
        return null;
    }

    @Override
    public boolean contains(Figure f)
    {
        if (super.contains(f))
        {
            return true;
        }
        else
        {
            if (isHierarchicalEnabled())
            {
                for (int i = 0; i < getChildren().size(); i++)
                {
                    Figure figure = getChildren().get(i);
                    if (figure instanceof DefaultHierarchicalDrawing)
                    {
                        return ((DefaultHierarchicalDrawing) figure).contains(f);
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean remove(Figure figure)
    {
        //удаляем парента для удаляемого элемента
        if (figure instanceof ChildFigure)
        {
            ((ChildFigure) figure).setParent(null);
        }
        boolean result = super.remove(figure);

        if (!result)
        {
            for (int i = 0; i < getChildren().size(); i++)
            {
                Figure f = getChildren().get(i);
                if (f instanceof DefaultHierarchicalDrawing)
                {
                    return ((DefaultHierarchicalDrawing) f).remove(figure);
                }
            }
        }
        return result;
    }

    public boolean isHierarchicalEnabled()
    {
        return hierarchicalEnabled;
    }

    public void setHierarchicalEnabled(boolean hierarchicalEnabled)
    {
        boolean old = this.hierarchicalEnabled;
        this.hierarchicalEnabled = hierarchicalEnabled;

        for (int i = 0; i < getChildren().size(); i++)
        {
            Figure f = getChildren().get(i);
            if (f instanceof DefaultHierarchicalDrawing)
            {
                ((DefaultHierarchicalDrawing) f).setHierarchicalEnabled(hierarchicalEnabled);
            }
        }
        firePropertyChange("hierarchicalEnabled", old, this.hierarchicalEnabled);
    }


    @Override
    public boolean isShowDimention()
    {
        return false;
    }

    @Override
    public void setShowDimention(boolean showDimention)
    {
        for (int i = 0; i < getChildren().size(); i++)
        {
            Figure f = getChildren().get(i);
            if (f instanceof ShowDimention)
            {
                ((ShowDimention) f).setShowDimention(showDimention);
            }
        }
    }

    @Override
    public Figure findFigureExcept(Point2D.Double p, Collection<? extends Figure> ignore)
    {
        Figure result = null;
        for (Figure f : getFiguresFrontToBack())
        {
            if (!ignore.contains(f) && f.isVisible())
            {
                if (f instanceof DefaultHierarchicalDrawing && ((DefaultHierarchicalDrawing) f).isHierarchicalEnabled())
                {
                    result = ((DefaultHierarchicalDrawing) f).findFigureExcept(p, ignore);
                }
                if (result == null && f.contains(p))
                {
                    result = f;
                }

                if (result != null)
                {
                    break;
                }
            }
        }
        return result;
    }

}
