package by.dak.draw;

import by.dak.test.TestUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * User: akoyro
 * Date: 12.08.2009
 * Time: 18:53:01
 */
public class TestArea
{
    public static class F
    {
        private GeneralPath p = new GeneralPath();
        private java.util.List<Shape> shapes = new ArrayList<Shape>();


        public void append(Shape shape)
        {
            p.append(shape, false);
            shapes.add(shape);
        }

        public void draw(Graphics2D g2D)
        {
            g2D.draw(p);
        }

        private F[] divide(Shape divider, Point2D.Double point1, Point2D.Double point2)
        {

            Shape shape1 = null;
            Shape shape2 = null;
            for (Shape shape : shapes)
            {
                if (new Rectangle2D.Double(point1.getX() - 1, point1.getY() - 1, 2, 2).intersectsLine((Line2D) (shape)))
                {
                    shape1 = shape;
                }
                if (new Rectangle2D.Double(point2.getX() - 1, point2.getY() - 1, 2, 2).intersectsLine((Line2D) (shape)))
                {
                    shape2 = shape;
                }
                if (shape1 != null && shape2 != null)
                {
                    break;
                }
            }
            if (shape1 != null && shape2 != null)
            {
                F[] fs = new F[2];
                fs[0] = new F();
                fs[1] = new F();
                int i1 = shapes.indexOf(shape1);
                int i2 = shapes.indexOf(shape2);
                for (int i = 0; i < i1; i++)
                {
                    fs[0].append(shapes.get(i));
                }
                fs[0].append(new Line2D.Double(((Line2D) shape1).getP1(), point1));
                fs[0].append(divider);
                fs[0].append(new Line2D.Double(point2, ((Line2D) shape2).getP2()));
                for (int i = i2 + 1; i < shapes.size(); i++)
                {
                    fs[0].append(shapes.get(i));
                }

                fs[1].append(new Line2D.Double(point1, ((Line2D) shape1).getP2()));
                for (int i = i1 + 1; i < i2; i++)
                {
                    fs[1].append(shapes.get(i));
                }
                fs[1].append(new Line2D.Double(((Line2D) shape2).getP1(), point2));
                fs[1].append(divider);
                return fs;
            }
            return null;
        }
    }


    public static void main(String[] args)
    {
        JComponent component = new JComponent()
        {
            @Override
            protected void paintComponent(Graphics g)
            {

                F f1 = new F();
                f1.append(new Line2D.Double(10, 10, 100, 10));
                //f1.append(new Arc2D.Double(55, 10, 90, 90, 90, -180, Arc2D.OPEN));
                f1.append(new Line2D.Double(100, 10, 100, 100));
                f1.append(new Line2D.Double(100, 100, 10, 100));
                f1.append(new Line2D.Double(10, 100, 10, 10));
                //f1.draw((Graphics2D) g);

                Point2D.Double point1 = new Point2D.Double(55, 10);
                Point2D.Double point2 = new Point2D.Double(10, 55);
                F[] fs = f1.divide(new Line2D.Double(point1, point2), point1, point2);
                fs[1].draw((Graphics2D) g);
            }

        };

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(component, BorderLayout.CENTER);
        TestUtils.showFrame(panel, "TestArea");
    }
}
