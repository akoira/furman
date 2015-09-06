/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.gui.cuttingApp;

import by.dak.cutting.cut.base.Element;
import by.dak.cutting.cut.gui.CutSettings;
import by.dak.cutting.cut.guillotine.Segment;
import by.dak.cutting.cut.guillotine.Strips;

import java.util.ArrayList;
import java.util.List;

/**
 * The sawyer used to optimize red segment after common cuttung
 *
 * @author akoyro
 */
public class CVerticalSawyer extends CSawyer
{


    public CVerticalSawyer(List<Segment> redSegments, List<Segment> graySegments, int sawWidth)
    {
        List<Element> elements = new ArrayList<Element>();

        for (Segment segment : redSegments)
        {
            DimensionItem item = new DimensionItem(10, segment.getWidth(), segment);
            Element element = new Element(item);
            elements.add(element);
        }

        CutSettings cutSettings = new CutSettings();
        cutSettings.setElements(elements.toArray(new Element[elements.size()]));

        List<Segment> segments = new ArrayList<Segment>();

        for (Segment segment : graySegments)
        {
            DimensionItem item = new DimensionItem(15, segment.getLength(), segment);
            segments.add(Strips.createGraySegmentBy(item));
        }

        Strips strips = new Strips(segments);
        strips.setAllowRotation(false);
        strips.setSawWidth(sawWidth);
        cutSettings.setStrips(strips);

        setCutSettings(cutSettings);

        setForceBestFit(true);
        setForceMinimumRatio(0.2f);
        setForceMigrationRatio(0.5f);
    }
}
