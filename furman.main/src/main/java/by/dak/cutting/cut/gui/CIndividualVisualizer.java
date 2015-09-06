/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */
package by.dak.cutting.cut.gui;

import by.dak.cutting.cut.base.Element;
import by.dak.cutting.cut.genetics.Individual;
import by.dak.cutting.cut.guillotine.CIndividual;
import by.dak.cutting.cut.guillotine.IndividualCutter;
import by.dak.cutting.cut.guillotine.Strips;
import by.dak.cutting.cut.guillotine.StripsPainter;

import javax.swing.*;
import java.awt.*;

/**
 * @author Peca
 */
public class CIndividualVisualizer extends IndividualVisualizer
{

    JFrame frame;
    Strips strips;
    CIndividual individual;
    Element[] elements;
    JPanel drawPanel;
    private Strips cuttedStrips = null;
    private float forceMinimumRatio;
    private boolean forceBestFit;

    public CIndividualVisualizer(CIndividual individual, Strips strips, Element[] elements, float forceMinimumRatio, boolean forceBestFit)
    {
        //this.segment = segment;
        this.individual = individual;
        this.elements = elements;
        this.strips = strips;
        this.forceMinimumRatio = forceMinimumRatio;
        this.forceBestFit = forceBestFit;
        this.frame = new JFrame();
        new BoxLayout(this.frame, BoxLayout.X_AXIS);
        //this.frame.setLayout();
        this.drawPanel = new JPanel()
        {

            @Override
            public void paint(Graphics g)
            {
                super.paint(g);
                draw(g);
            }
        };
        drawPanel.setBackground(Color.WHITE);
        frame.getContentPane().add(drawPanel);
        Dimension dim = new Dimension(700, 600);
        frame.setSize(dim);
        drawPanel.setSize(dim);
        frame.setPreferredSize(dim);
        drawPanel.setPreferredSize(dim);
        this.update();
    }

    private void draw(Graphics g)
    {
        if (this.cuttedStrips != null)
        {
            StripsPainter.draw(g, this.cuttedStrips);
        }
    }

    private void updateCut()
    {
        cuttedStrips = IndividualCutter.cutIndividual(individual, strips, elements, forceMinimumRatio, forceBestFit);
    }

    @Override
    public void setIndividual(Individual individual)
    {
        this.individual = (CIndividual) individual;
        this.update();
    }

    @Override
    public JFrame show(int x, int y)
    {
        this.update();
        frame.pack();
        frame.setLocation(x, y);
        frame.setVisible(true);
        return frame;
    }

    @Override
    public void update()
    {
        updateCut();
        //drawPanel.setPreferredSize(new Dimension(segment.getLength()+10, segment.getWidth()+50));
        drawPanel.setPreferredSize(new Dimension(100, 100));
        drawPanel.repaint();
    }
}
