package by.dak.draw;

import org.jhotdraw.draw.CompositeFigure;

/**
 * User: akoyro
 * Date: 14.09.2009
 * Time: 18:54:26
 */
public interface ChildFigure<P extends CompositeFigure>
{
    public P getParent();

    public void setParent(P parent);
}
