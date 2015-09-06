/*
 * Draw.java
 *
 * Created on 25. jen 2006, 22:03
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the draw.
 */

package by.dak.cutting.cut.graphics;

import by.dak.cutting.cut.base.*;

import java.awt.*;

/**
 * @author Peca
 */
public class Draw
{
    static private Color[] colors = {Color.red, Color.blue, Color.green, Color.cyan, Color.gray,
            Color.magenta, Color.orange, Color.pink, Color.yellow, Color.white,
            Color.darkGray, Color.lightGray, new Color(200, 100, 50),
            new Color(100, 200, 50), new Color(50, 200, 250)};

    public static Color getColor(int index)
    {
        index = index % colors.length;
        return colors[index];
    }

    public static void drawRect(Rectangle rct, Graphics g)
    {
        g.drawRect(rct.x, rct.y, rct.width, rct.height);
    }

    public static void drawRect(Rect rct, Graphics g)
    {
        drawRect(rct.toRectangle(), g);
    }

    public static void fillRect(Rectangle rct, Graphics g)
    {
        g.fillRect(rct.x, rct.y, rct.width, rct.height);
    }

    public static void fillRect(Rect rct, Graphics g)
    {
        fillRect(rct.toRectangle(), g);
    }


    public static void drawWorkArea(WorkArea wa, float cornerSize, Graphics g)
    {
        drawRect(wa.toRectangle(), g);
        g.fillRect(wa.getX(), wa.getY(), (int) (wa.getWidth() * cornerSize), (int) (wa.getHeight() * cornerSize));
    }

    public static void drawWorkSpace(WorkSpace ws, Graphics g)
    {
        g.setColor(Color.black);
        drawRect(ws.getPlateSize().toRect().toRectangle(), g);

/*        for(int i=0; i < ws.boxes.size(); i++){
         g.setColor(colors[i]);
         fillRect(ws.boxes.get(i).toRectangle(), g);
      }
*/
        for (int i = 0; i < ws.workAreas.size(); i++)
        {
            g.setColor(Color.white);
            drawWorkArea(ws.workAreas.get(i), 0.0f, g);
        }

    }

    public static void drawElement(Element el, int x, int y, Graphics g)
    {
        Rect rct = el.toRect().move(x, y);
        fillRect(rct.toRectangle(), g);
        g.setColor(Color.black);
        Font f = new Font("Monospaced 12", Font.BOLD, 12);
        g.setFont(f);
        g.drawString(el.getId().toString(), x + (el.getWidth() / 2) + 4, y + (el.getHeight() / 2) + 5);

    }

    public static void drawCuttedElements(CuttedElement[] cea, Graphics g)
    {
        for (int i = 0; i < cea.length; i++)
        {
            CuttedElement ce = cea[i];
            if (ce == null) continue;
            g.setColor(getColor(i));
            fillRect(ce.toRectangle(), g);
            g.setColor(Color.black);
            drawRect(ce.toRectangle(), g);
            int x = ce.getX();
            int y = ce.getY();
            Font f = new Font("Monospaced 12", Font.BOLD, 12);
            g.setFont(f);
            g.drawString(ce.element.getId().toString(), x + (ce.getWidth() / 2) - 4, y + (ce.getHeight() / 2) + 5);
        }

    }
}
