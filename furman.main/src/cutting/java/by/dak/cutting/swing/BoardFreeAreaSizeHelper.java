package by.dak.cutting.swing;

import by.dak.cutting.cut.guillotine.Segment;
import by.dak.persistence.entities.Cutter;
import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.draw.GroupFigure;
import org.jhotdraw.draw.TextAreaFigure;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: dkoyro
 * Date: 12.03.11
 * Time: 12:47
 * To change this template use File | Settings | File Templates.
 */
public class BoardFreeAreaSizeHelper implements FreeAreaSizeFigure
{
    @Override
    public void createFreeAreaSizeFigure(GroupFigure groupFigure, SegmentFigure segmentFigure)
    {
        TextAreaFigure textAreaFigure = new TextAreaFigure();
        textAreaFigure.setEditable(true);
        textAreaFigure.setFontSize(FREE_AREA_FONT_SIZE);
        textAreaFigure.setSelectable(false);
        textAreaFigure.setTransformable(false);
        textAreaFigure.set(AttributeKeys.FILL_COLOR, Color.LIGHT_GRAY);
        textAreaFigure.set(AttributeKeys.STROKE_COLOR, Color.LIGHT_GRAY);

        Segment segment = segmentFigure.getSegment();

        int paddingW = 0;
        int paddingL = 0;
        if (segment.getItems().size() > 0)
        {
            paddingW = segment.getLevel() % 2 == 0 ? 0 : segment.getPadding();
            paddingL = segment.getLevel() % 2 == 0 ? segment.getPadding() : 0;
        }

        String text = (segment.getWidth() - paddingW) + "x" +
                (segment.getFreeLength() - paddingL);

        Point2D.Double[] points = getPointsBy(segment, segmentFigure.getCutter());

        Point2D.Double anchorPoint = new Point2D.Double(points[0].getX() + FREE_AREA_FIGURE_OFFSET, points[0].getY() + FREE_AREA_FIGURE_OFFSET);
        Point2D.Double leadPoint = new Point2D.Double(points[1].getX() - FREE_AREA_FIGURE_OFFSET, points[1].getY() + FREE_AREA_FIGURE_OFFSET);
        switch (segment.getSegmentType())
        {
            case blue:
                anchorPoint = new Point2D.Double(points[0].getY() + FREE_AREA_FIGURE_OFFSET, points[0].getX() + FREE_AREA_FIGURE_OFFSET);
                leadPoint = new Point2D.Double(points[1].getY() - FREE_AREA_FIGURE_OFFSET, points[1].getX() - FREE_AREA_FIGURE_OFFSET);
                text = (segment.getFreeLength() - paddingW) + "x" +
                        (segment.getWidth() - paddingL);
                break;
            case red:
            case gray:
                break;
            case green:
                if (segment.getFreeLength() == 0)
                {
                    return;
                }
                break;
            default:
                return;
        }

        textAreaFigure.setText(text);
        textAreaFigure.setBounds(anchorPoint, leadPoint);

        groupFigure.add(textAreaFigure);
    }


    private Point2D.Double[] getPointsBy(Segment segment, Cutter cutter)
    {
        switch (segment.getSegmentType())
        {
            case red:
                switch (cutter.getDirection())
                {

                    case horizontal:
                        return getRotatedPoints(segment);
                    case vertical:
                        return getDirectPoints(segment);
                    default:
                        throw new IllegalArgumentException();
                }
            default:
                switch (cutter.getDirection())
                {

                    case horizontal:
                        return getDirectPoints(segment);
                    case vertical:
                        return getRotatedPoints(segment);
                    default:
                        throw new IllegalArgumentException();
                }
        }
    }

    private Point2D.Double[] getDirectPoints(Segment segment)
    {
        Point2D.Double anchor = new Point2D.Double(segment.getPadding(), segment.getUsedLength() + segment.getPadding());
        Point2D.Double lead = new Point2D.Double(segment.getWidth() - segment.getPadding(), segment.getUsedLength() + segment.getFreeLength() - segment.getPadding());
        return new Point2D.Double[]{anchor, lead};
    }

    private Point2D.Double[] getRotatedPoints(Segment segment)
    {
        Point2D.Double anchor = new Point2D.Double(segment.getUsedLength() + segment.getPadding(), segment.getPadding());
        Point2D.Double lead = new Point2D.Double(segment.getUsedLength() + segment.getFreeLength() - segment.getPadding(), segment.getPadding());
        return new Point2D.Double[]{anchor, lead};
    }
}
