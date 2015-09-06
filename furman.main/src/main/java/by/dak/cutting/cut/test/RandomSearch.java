/*
 * RandomSearch.java
 *
 * Created on 9. kv�ten 2007, 11:34
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the draw.
 */

package by.dak.cutting.cut.test;

import by.dak.cutting.cut.base.Element;
import by.dak.cutting.cut.common.Common;
import by.dak.cutting.cut.gui.GTreeIndividualVisuallizer;
import by.dak.cutting.cut.guillotine.GTreeIndividual;
import by.dak.cutting.cut.guillotine.GTreeRatingFunction;
import by.dak.cutting.cut.guillotine.Segment;

import javax.swing.*;

/**
 * @author Peca
 */
public class RandomSearch
{

    public static void main(String args[])
    {
        Element[] elements = Common.loadElementsFromXmlFile("g04.xml");
        Segment segment = new Segment(0, 200);
        segment.setFixedLength(false);
        segment.setFixedWidth(true);
        GTreeRatingFunction rFX = new GTreeRatingFunction(elements);
        GTreeIndividual ind = new GTreeIndividual(segment, elements, 4);

        GTreeIndividualVisuallizer iv = new GTreeIndividualVisuallizer(ind, elements);

        GTreeIndividual bestInd = ind;

        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);

        float bestRating = 0;
        for (int i = 0; i < 1000000; i++)
        {
            if ((i % 100) == 0)
            {
                System.out.print(i);
                System.out.print(" - ");
                System.out.println(bestRating);
                System.out.flush();
            }
            ind = new GTreeIndividual(segment, elements, 4);
            ind.initRandom();
            float r = rFX.rate(ind).getRating();
            if (r > bestRating)
            {
                bestRating = r;
                bestInd = (GTreeIndividual) ind.clone();
                if (r > 0.99) break;
                //System.out.println(r);
                /*iv.setIndividual(bestInd);
                iv.update();
                iv.show(0, 0);*/
            }
        }
        iv.setIndividual(bestInd);
        JFrame frm = iv.show(0, 0);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
