/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.gui;

import by.dak.cutting.cut.base.Element;
import by.dak.cutting.cut.genetics.Individual;
import by.dak.cutting.cut.genetics.NChromosome;
import by.dak.cutting.cut.genetics.RChromosome;
import by.dak.cutting.cut.genetics.SChromosome;
import by.dak.cutting.cut.guillotine.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * @author Peca
 */
public class AIndividualVisualizer extends IndividualVisualizer
{
    JFrame frame;
    Strips strips;
    AIndividual individual;
    Element[] elements;
    JPanel drawPanel;


    public AIndividualVisualizer(AIndividual individual, Strips strips, Element[] elements)
    {
        //this.segment = segment;
        this.individual = individual;
        this.elements = elements;
        this.strips = strips;
        this.frame = new JFrame();
        this.drawPanel = new JPanel()
        {
            public void paint(Graphics g)
            {
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
        AIndividual ind = this.individual;
        GuillotineTreeCutter cutter = new GuillotineTreeCutter();

        List<Segment> segments = this.strips.getSegments();
        for (Segment seg : segments) seg.clear();
        int segmentIndex = 0;
        while (ind != null)
        {
            SChromosome sch = ind.getSChromosome();
            NChromosome nch = ind.getNChromosome();
            RChromosome rch = ind.getRChromosome();
            boolean cutSuccess = false;

            while (!cutSuccess && segmentIndex < segments.size())
            {
                Segment segment = segments.get(segmentIndex);
                //segment.clear();        

                Segment seg = new Segment();
                seg.setLength(segment.getWidth());
                seg.setFixedLength(true);

                CutResult res = cutter.cutSegment(seg, this.elements, sch.getElements(), rch.getElements(), nch.getElements()[0], 3);

                cutSuccess = res.isSuccess();
                if (cutSuccess)
                {
                    segment.addSegment(seg);
                    //segment.sort();                    
                }
            }

            ind = ind.getSister();
            //ind = null;
        }
        for (Segment seg : segments) seg.sortByWaste();
//        System.out.println(gch.getSegmentNode());

    }

    @Override
    public void setIndividual(Individual individual)
    {
        this.individual = (AIndividual) individual;
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
