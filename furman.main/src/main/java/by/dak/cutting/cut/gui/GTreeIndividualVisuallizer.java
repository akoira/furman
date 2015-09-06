/*
 * GIndividualVisuallizer.java
 *
 * Created on 5. kv�ten 2007, 11:23
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the draw.
 */

package by.dak.cutting.cut.gui;

import by.dak.cutting.cut.base.Element;
import by.dak.cutting.cut.genetics.GTreeChromosome;
import by.dak.cutting.cut.genetics.Individual;
import by.dak.cutting.cut.genetics.RChromosome;
import by.dak.cutting.cut.genetics.SChromosome;
import by.dak.cutting.cut.graphics.Draw;
import by.dak.cutting.cut.guillotine.GTreeIndividual;
import by.dak.cutting.cut.guillotine.GuillotineTreeCutter;
import by.dak.cutting.cut.guillotine.Segment;

import javax.swing.*;
import java.awt.*;

/**
 * @author Peca
 */
public class GTreeIndividualVisuallizer extends IndividualVisualizer
{

    Color[] cutColors = {Color.RED, Color.BLUE, new Color(0, 200, 0), Color.MAGENTA,
            Color.GREEN, Color.PINK};

    JFrame frame;
    //Segment segment;
    GTreeIndividual individual;
    Element[] elements;
    JPanel drawPanel;


    public GTreeIndividualVisuallizer(GTreeIndividual individual, Element[] elements)
    {
        //this.segment = segment;
        this.individual = individual;
        this.elements = elements;
        this.frame = new JFrame();
        this.drawPanel = new JPanel()
        {
            public void paint(Graphics g)
            {
                updateSegment();
                draw(g);
            }
        };
        drawPanel.setBackground(Color.WHITE);
        frame.getContentPane().add(drawPanel);
        this.update();
    }

    private void draw(Graphics g)
    {
        g.clearRect(0, 0, (int) g.getClipBounds().getWidth(), (int) g.getClipBounds().getHeight());
        drawSegment(0, 0, individual.getSegment(), g);
        drawSegmentCut(0, 0, individual.getSegment(), 3, g);
    }

    private void drawSegment(int x, int y, Segment segment, Graphics g)
    {

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(1));

        int s = 4 - segment.getLevel();
        s = 0;
        int w, h;
        Color c;
        if ((segment.getLevel() % 2) == 0)
        {
            w = segment.getLength();
            h = segment.getWidth();
        }
        else
        {
            w = segment.getWidth();
            h = segment.getLength();
        }
        if (segment.getLevel() == 0) c = Color.lightGray;
        else c = Draw.getColor(segment.getLevel());

        if (segment.getLevel() == 0)
        {
            g.setColor(c);
            g.fillRect(x, y, w + s, h + s);
            g.setColor(Color.BLACK);
            g.drawRect(x, y, w + s, h + s);
        }
        //g2.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[] {10, 5}, 0.0f));
        //if(segment.getLevel()== 4)g2.drawRect(x, y, w2+s, h2+s);


        int y2 = y;
        int x2 = x;
        for (Segment seg : segment.getItems())
        {
            drawSegment(x2, y2, seg, g);
            if ((segment.getLevel() % 2) == 0) x2 += seg.getWidth();
            else y2 += seg.getWidth();
        }

        if (segment.getElement() != null)
        {
            Element el = segment.getElement();
            g.setColor(Color.ORANGE);
            //g.setColor(Draw.getColor(el.index));
            g.fillRect(x2, y2, w, h);
            g.setColor(Color.BLACK);
            g.drawRect(x2, y2, w, h);
            int sw = g.getFontMetrics().stringWidth(String.valueOf(el.getId()));
            int sh = g.getFontMetrics().getHeight();
            g.drawString(String.valueOf(el.getId()), x2 + (w - sw) / 2, y2 + (h + sh) / 2);

        }

    }

    private void drawSegmentCut(int x, int y, Segment segment, int maxLevel, Graphics g)
    {

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(1));
        int y2 = y;
        int x2 = x;
        for (Segment seg : segment.getItems())
        {
            drawSegmentCut(x2, y2, seg, maxLevel, g);
            if ((segment.getLevel() % 2) == 0) x2 += seg.getWidth();
            else y2 += seg.getWidth();
        }


        if ((segment.getLevel() > 0) && (segment.getParent() != null))
        {
            int s = maxLevel - segment.getLevel() + 1;
            s = Math.max(s, 1);
            g.setColor(cutColors[(segment.getLevel() - 1) % cutColors.length]);
            g2.setStroke(new BasicStroke(s, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[]{2 * s, s}, 0.0f));

            if ((segment.getLevel() % 2) == 0)
            {
                y += segment.getWidth();
                //if((y < segment.getParent().getWidth())||(segment.getParent().getLevel()>0))
                g.drawLine(x, y, x + segment.getLength(), y);
            }
            else
            {
                x += segment.getWidth();
                g.drawLine(x, y, x, y + segment.getLength());
            }
        }
    }

    public GTreeIndividual getIndividual()
    {
        return individual;
    }

    public void setIndividual(Individual individual)
    {
        this.individual = (GTreeIndividual) individual;
        this.update();
    }

    private void updateSegment()
    {
        GuillotineTreeCutter cutter = new GuillotineTreeCutter();
        SChromosome sch = this.individual.getSChromosome();
        GTreeChromosome gch = this.individual.getGTreeChromosome();
        RChromosome rch = this.individual.getRChromosome();
        Segment segment = this.individual.getSegment();
        segment.clear();
        /*for(int i=0; i<sch.getElements().length; i++){
            //cutter.by.dak.cutting.cut(segment, elements[sch.elements[i]]);
            cutter.by.dak.cutting.cut(segment, elements[sch.getElements()[i]]);
        }*/
        boolean b;
        b = cutter.cut(segment, gch.getSegmentNode(), rch.getElements());
        segment.sortByWaste();
//        System.out.println(gch.getSegmentNode());

    }

    public void update()
    {
        // segment.sort();
        updateSegment();
        this.frame.setTitle(individual.toString());
        Segment segment = this.individual.getSegment();
        drawPanel.setPreferredSize(new Dimension(segment.getLength() + 10, segment.getWidth() + 50));
        drawPanel.repaint();
    }

    public JFrame show(int x, int y)
    {
        frame.pack();
        frame.setLocation(x, y);
        frame.setVisible(true);
        return frame;
    }

}
