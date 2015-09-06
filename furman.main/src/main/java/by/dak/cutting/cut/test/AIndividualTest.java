/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.test;

import by.dak.cutting.cut.base.Element;
import by.dak.cutting.cut.common.Common;
import by.dak.cutting.cut.gui.AIndividualVisualizer;
import by.dak.cutting.cut.guillotine.AIndividual;
import by.dak.cutting.cut.guillotine.Segment;
import by.dak.cutting.cut.guillotine.Strips;

/**
 * @author Peca
 */
public class AIndividualTest
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        int elementsCount = 30;
        int minElementSize = 15;
        int maxElementSize = 100;
        int elementGrid = 5;

        Element[] elements = new Element[elementsCount];

        int i = 0;
        while (i < elementsCount)
        {
            int w = Math.max(Common.nextInt((maxElementSize / elementGrid) + 1) * elementGrid, minElementSize);
            int h = Math.max(Common.nextInt((maxElementSize / elementGrid) + 1) * elementGrid, minElementSize);
            int cnt = Common.nextInt(1);
            for (int j = cnt; (j >= 0) && (i < elementsCount); j--)
            {
                elements[i] = new Element(w, h, i);
                i++;
            }
        }

        Segment segment = new Segment();
        segment.setWidth(200);
        segment.setFixedLength(false);
        segment.setFixedWidth(true);

        Strips strips = new Strips();
        strips.addSegment(segment);

        AIndividual ind = new AIndividual(elementsCount);
        ind.initRandom();
        AIndividualVisualizer viz = new AIndividualVisualizer(ind, strips, elements);
        viz.show(0, 0);
    }

}
