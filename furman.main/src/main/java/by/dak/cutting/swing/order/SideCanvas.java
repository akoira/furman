package by.dak.cutting.swing.order;

import javax.swing.*;
import java.awt.*;


public class SideCanvas extends JComponent implements Icon
{

    private boolean isDown;
    private boolean isUp;
    private boolean isLeft;
    private boolean isRight;

    private int xOffset = 5;
    private int yOffset = 5;

    private Dimension dimension;

    public SideCanvas(int xOffset, int yOffset, Dimension dimension)
    {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.dimension = dimension;
    }

    public SideCanvas(int xOffset, int yOffset)
    {
        this();
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public SideCanvas()
    {
        dimension = new Dimension(60, 30);
    }

    public void drawLeftpolygon(Graphics g, int x, int y, int width, int height)
    {
        Polygon p = new Polygon();
        // make a triangle left.
        int y2 = height - y;
        int x2 = x + y;
        int y3 = y2 - y;
        int y4 = y * 2;

        p.addPoint(x, y);
        p.addPoint(x, y2);
        p.addPoint(x2, y3);
        p.addPoint(x2, y4);

        g.setColor(Color.black);
        g.drawPolygon(p);
        if (isLeft())
        {
            g.setColor(Color.gray);
            g.fillPolygon(p);
        }
    }

    public void drawUpPolygon(Graphics g, int x, int y, int width, int height)
    {
        Polygon p = new Polygon();
        // make a triangle top.
        int polWidth = width - x * 2;
        int x2 = x + polWidth;
        int x3 = x2 - x;
        int y2 = y * 2;
        int x4 = x * 2;

        p.addPoint(x, y);
        p.addPoint(x2, y);
        p.addPoint(x3, y2);
        p.addPoint(x4, y2);

        g.setColor(Color.black);
        g.drawPolygon(p);
        if (isUp())
        {
            g.setColor(Color.gray);
            g.fillPolygon(p);
        }
    }

    public void drawDownPolygon(Graphics g, int x, int y, int width, int height)
    {
        Polygon p = new Polygon();
        // make a triangle down.

        int polWidth = width - x * 2;
        int x2 = x + polWidth;
        int x3 = x2 - x;
        int y2 = y - x;
        int x4 = x * 2;

        p.addPoint(x, y);
        p.addPoint(x2, y);
        p.addPoint(x3, y2);
        p.addPoint(x4, y2);

        g.setColor(Color.black);
        g.drawPolygon(p);
        if (isDown())
        {
            g.setColor(Color.gray);
            g.fillPolygon(p);
        }
    }

    public void drawRightPolygon(Graphics g, int x, int y, int width, int height)
    {
        Polygon p = new Polygon();
        // make a triangle right.
        int y2 = height - y;
        int x2 = x - y;
        int y3 = y2 - y;
        int y4 = y * 2;

        p.addPoint(x, y);
        p.addPoint(x, y2);
        p.addPoint(x2, y3);
        p.addPoint(x2, y4);

        g.setColor(Color.black);
        g.drawPolygon(p);
        if (isRight())
        {
            g.setColor(Color.gray);
            g.fillPolygon(p);
        }
    }

    @Override
    public void paint(Graphics g)
    {
//        super.paint(g);
        Dimension d = getSize();
        int width = d.width;
        int height = d.height;

        drawLeftpolygon(g, xOffset, yOffset, width, height);
        drawUpPolygon(g, xOffset, yOffset, width, height);
        drawDownPolygon(g, xOffset, height - yOffset, width, height);
        drawRightPolygon(g, width - xOffset, yOffset, width, height);

    }

    public void paintIcon(Component c, Graphics g, int x, int y)
    {
        JButton cb = (JButton) c;
//	   ButtonModel model = cb.getModel();
        Dimension d = cb.getSize();
        int width = d.width;
        int height = d.height;
        drawDownPolygon(g, xOffset, height - yOffset, width, height);
        drawLeftpolygon(g, xOffset, yOffset, width, height);
        drawRightPolygon(g, width - xOffset, yOffset, width, height);
        drawUpPolygon(g, xOffset, yOffset, width, height);
    }

    public int getIconWidth()
    {
        return 10;
    }

    public int getIconHeight()
    {
        return 10;
    }

    @Override
    public Dimension getSize()
    {
        return dimension;
    }

    public Dimension getMinimumSize()
    {
        return new Dimension(50, 100);
    }

    public Dimension getPreferredSize()
    {
        return new Dimension(150, 300);
    }

    public Dimension getMaximumSize()
    {
        return new Dimension(200, 400);
    }


    public boolean isDown()
    {
        return isDown;
    }

    public void setDown(boolean down)
    {
        isDown = down;
        repaint();
    }

    public boolean isUp()
    {
        return isUp;
    }

    public void setUp(boolean up)
    {
        isUp = up;
        repaint();
    }

    public boolean isLeft()
    {
        return isLeft;
    }

    public void setLeft(boolean left)
    {
        isLeft = left;
        repaint();
    }

    public boolean isRight()
    {
        return isRight;
    }

    public void setRight(boolean right)
    {
        isRight = right;
        repaint();
    }

    public void flushSides()
    {
        setDown(false);
        setUp(false);
        setRight(false);
        setLeft(false);
        repaint();
    }
}
