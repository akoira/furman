/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.gui;

import by.dak.cutting.cut.base.Element;
import by.dak.cutting.cut.genetics.Individual;
import by.dak.cutting.cut.guillotine.GIndividual;
import by.dak.cutting.cut.guillotine.IndividualCutter;
import by.dak.cutting.cut.guillotine.Strips;
import by.dak.cutting.cut.guillotine.StripsPainter;

import javax.swing.*;
import java.awt.*;

/**
 * @author Peca
 */
public class GIndividualVisualizer extends IndividualVisualizer
{
    JFrame frame;
    Strips strips;
    GIndividual individual;
    Element[] elements;
    JPanel drawPanel;


    public GIndividualVisualizer(GIndividual individual, Strips strips, Element[] elements)
    {
        //this.segment = segment;
        this.individual = individual;
        this.elements = elements;
        this.strips = strips;
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
        StripsPainter.draw(g, this.strips);
    }

    private void updateCut()
    {
        IndividualCutter.cutIndividual(individual, strips, elements);
    }

    @Override
    public void setIndividual(Individual individual)
    {
        this.individual = (GIndividual) individual;
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
