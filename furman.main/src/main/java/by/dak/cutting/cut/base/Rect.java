/*
 * CPRect.java
 *
 * Created on 25. jen 2006, 11:10
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the draw.
 */

package by.dak.cutting.cut.base;

import java.awt.*;

/**
 * @author phorny
 */


public class Rect extends Dimension
{

    private int x, y, x2, y2;

    public Rect()
    {
        super();
    }

    public Rect(int width, int height)
    {
        this(0, 0, width, height);
    }

    public Rect(Rect rct)
    {
        this(rct.x, rct.y, rct.x2, rct.y2);
    }

    public Rect(int x, int y, int x2, int y2)
    {
        super(x2 - x, y2 - y);
        this.x = x;
        this.y = y;
        this.x2 = x2;
        this.y2 = y2;
    }

    public int getX()
    {
        return this.x;
    }

    public int getY()
    {
        return this.y;
    }

    public int getX2()
    {
        return this.x2;
    }

    public int getY2()
    {
        return this.y2;
    }

    public void setX(int x)
    {
        this.x = x;
        this.setWidth(x2 - x);
    }

    public Rect move(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.x2 = x + this.getWidth();
        this.y2 = y + this.getHeight();
        return this;
    }

    public Rectangle toRectangle()
    {
        Rectangle rct = new Rectangle(x, y, getWidth(), getHeight());
        return rct;
    }

    /**
     * @param rct
     * @return true pokud se obdelniky prekryvaji (pocita se cela plocha, tj. maly ctverec ve velkym je true)
     */
    public boolean intersect(Rect rct)
    {
        if (rct.y2 < y) return false;
        if (rct.y > y2) return false;
        if (rct.x2 < x) return false;
        if (rct.x > x2) return false;
        return true;
    }

    /**
     * Rozdeli obdelnik na subobdelniky
     */
    public Rect[] split(Rect rct)
    {
        if (!intersect(rct)) return null;
        Rect rct1 = null, rct2 = null, rct3 = null, rct4 = null;
        int cnt = 0;
        if (rct.x > x)
        {
            rct1 = new Rect(x, y, rct.x, y2);
            cnt++;
        }
        if (rct.x2 < x2)
        {
            rct2 = new Rect(rct.x2, y, x2, y2);
            cnt++;
        }
        if (rct.y > y)
        {
            rct3 = new Rect(x, y, x2, rct.y);
            cnt++;
        }
        if (rct.y2 < y2)
        {
            rct4 = new Rect(x, rct.y2, x2, y2);
            cnt++;
        }
        Rect[] r = new Rect[cnt];
        cnt = 0;
        if (rct1 != null) r[cnt++] = rct1;
        if (rct2 != null) r[cnt++] = rct2;
        if (rct3 != null) r[cnt++] = rct3;
        if (rct4 != null) r[cnt++] = rct4;
        return r;
    }

    /**
     * Vrati true pokud je rct obsazen v obdelniku (this)
     */
    public boolean contains(Rect rct)
    {
        return ((rct.x >= x) && (rct.x2 <= x2) && (rct.y >= y) && (rct.y2 <= y2));
    }

    public Rect rotate()
    {
        super.rotate();
        this.x2 = this.x + getWidth();
        this.y2 = this.y + getHeight();
        return this;
    }
}



