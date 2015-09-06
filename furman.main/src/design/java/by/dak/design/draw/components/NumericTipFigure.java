package by.dak.design.draw.components;

import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.draw.TextFigure;

import java.awt.*;
import java.awt.font.TextLayout;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 05.09.11
 * Time: 17:47
 * To change this template use File | Settings | File Templates.
 */
public class NumericTipFigure extends TextFigure
{
    public NumericTipFigure()
    {
    }

    public NumericTipFigure(String text)
    {
        super(text);
    }

    private Ellipse2D.Double getEllipse(Graphics2D g)
    {
        if (getText() != null)
        {
            Point2D.Double centerPoint = getStartPoint();
            FontMetrics fontMetrics = g.getFontMetrics(getFont());
            double radius = Math.max(fontMetrics.stringWidth(getText()), fontMetrics.getHeight());
            Ellipse2D.Double ellipse = new Ellipse2D.Double(centerPoint.x - radius / 2,
                    centerPoint.y - radius / 2, radius, radius);
            return ellipse;
        }
        return null;
    }

    @Override
    protected void drawStroke(Graphics2D g)
    {
        Ellipse2D.Double ellipse = getEllipse(g);
        if (ellipse != null)
        {
            g.draw(ellipse);
        }
    }

    @Override
    protected void drawFill(Graphics2D g)
    {
        Ellipse2D.Double r = getEllipse(g);
        double grow = AttributeKeys.getPerpendicularFillGrowth(this);
        r.x -= grow;
        r.y -= grow;
        r.width += grow * 2;
        r.height += grow * 2;
        if (r.width > 0 && r.height > 0)
        {
            g.fill(r);
        }
    }

    @Override
    protected void drawText(Graphics2D g)
    {
        if (getText() != null || isEditable())
        {
            TextLayout layout = getTextLayout();
            FontMetrics fontMetrics = g.getFontMetrics(getFont());
            layout.draw(g, (float) origin.x - fontMetrics.stringWidth(getText()) / 2,
                    (float) (origin.y + layout.getAscent() - fontMetrics.getHeight() / 2));
        }
    }
}
