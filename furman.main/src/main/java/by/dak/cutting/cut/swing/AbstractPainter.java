package by.dak.cutting.cut.swing;

import java.awt.*;

/**
 * User: akoyro
 * Date: 13.03.2009
 * Time: 0:30:38
 */
public class AbstractPainter implements by.dak.cutting.configuration.Constants
{

    /**
     * Должно быть true когда пропилы делаются по гаризантали
     */
    private boolean rotate = ROTATE_SHEET;

    private float scale = 1.0f;

    protected Graphics2D createGraphics2D(Graphics g, Rectangle rct)
    {
        Graphics2D g2D = null;
        if (rotate)
            g2D = (Graphics2D) g.create(rct.y, rct.x, rct.height, rct.width);
        else
            g2D = (Graphics2D) g.create(rct.x, rct.y, rct.width, rct.height);
        return g2D;
    }

    protected void drawRect(Graphics g, int x, int y, int w, int h)
    {
        if (rotate)
        {
            g.drawRect(y, x, h, w);
        }
        else
        {
            g.drawRect(x, y, w, h);
        }
    }

    protected void fillRect(Graphics g, int x, int y, int w, int h)
    {
        if (rotate)
        {
            g.fillRect(y, x, h, w);
        }
        else
        {
            g.fillRect(x, y, w, h);
        }
    }

    protected void drawLine(Graphics g, int x1, int y1, int x2, int y2)
    {
        if (rotate)
        {
            g.drawLine(y1, x1, y2, x2);
        }
        else
        {
            g.drawLine(x1, y1, x2, y2);
        }
    }

    protected void rotateRect(Rectangle rect)
    {
        rect.setSize(rect.height, rect.width);
    }

    public float getScale()
    {
        return scale;
    }

    public void setScale(float scale)
    {
        this.scale = scale;
    }

    public boolean isRotate()
    {
        return rotate;
    }

    public void setRotate(boolean rotate)
    {
        this.rotate = rotate;
    }


}
