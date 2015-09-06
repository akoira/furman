/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.gui.cuttingApp;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * @author Peca
 */
public class ProgressPanel extends JPanel
{
    private float waste = 1.0f;

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;

        float w = getWidth();
        float h = getHeight();

        g.setColor(Color.white);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        //g2.setPaint(new GradientPaint(0, 0, Color.red, w, 0, new Color(250, 120, 120)));


        float progress = w * (1 - this.waste);
        //g2.fill(new Rectangle2D.Float(0, 0, progress, h));
        g.setColor(getColor(1 - waste));
        g.fillRect(0, 0, (int) progress, (int) h);

        String str = String.format("%.2f%%", waste * 100.0f);
        g.setColor(Color.black);
        Rectangle2D rect = g.getFontMetrics().getStringBounds(str, g);
        int textX = (int) (getWidth() - rect.getWidth()) / 2;
        int textY = (int) (getHeight() + rect.getHeight()) / 2;
        g.drawString(str, textX, textY - 2);
    }

    private Color getColor(float coef)
    {
        float a = 0.85f;
        float k = 1 / (a - 1);
        float q = 1 / (1 - a);
        float k2 = 1 / (1 - a); //1/a;
        float q2 = (2 * a - 1) / (a - 1); //0.0f;

        float r = k * coef + q;
        float g = k2 * coef + q2;
        return new Color(tresholdValue(r), tresholdValue(g), 0.0f);
    }

    private float tresholdValue(float value)
    {
        return Math.max(0.0f, Math.min(1.0f, value));
    }

    public float getWaste()
    {
        return waste;
    }

    public void setWaste(float waste)
    {
        this.waste = waste;
        this.repaint();
    }

}
