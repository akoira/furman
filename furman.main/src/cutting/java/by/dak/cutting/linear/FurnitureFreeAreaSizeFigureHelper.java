package by.dak.cutting.linear;

import by.dak.cutting.cut.guillotine.Segment;
import by.dak.cutting.cut.guillotine.SegmentType;
import by.dak.cutting.swing.FreeAreaSizeFigure;
import by.dak.cutting.swing.SegmentFigure;
import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.draw.GroupFigure;
import org.jhotdraw.draw.TextAreaFigure;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: dkoyro
 * Date: 12.03.11
 * Time: 12:58
 * To change this template use File | Settings | File Templates.
 */
public class FurnitureFreeAreaSizeFigureHelper implements FreeAreaSizeFigure
{
    @Override
    public void createFreeAreaSizeFigure(GroupFigure groupFigure, SegmentFigure segmentFigure)
    {
        Segment segment = segmentFigure.getSegment();
        if (segment.getSegmentType().equals(SegmentType.red))
        {
            TextAreaFigure textAreaFigure = new TextAreaFigure();
            textAreaFigure.setEditable(true);
            textAreaFigure.setFontSize(FREE_AREA_FONT_SIZE);
            textAreaFigure.setSelectable(false);
            textAreaFigure.setTransformable(false);
            textAreaFigure.set(AttributeKeys.FILL_COLOR, Color.LIGHT_GRAY);
            textAreaFigure.set(AttributeKeys.STROKE_COLOR, Color.LIGHT_GRAY);

            String text = String.valueOf(UnitConverter.convertToMetre(segment.getFreeLength() - segment.getPadding()));
            int anchorX = segment.getUsedLength() + segment.getPadding();
            int anchorY = segment.getPadding();
            int leadX = segment.getUsedLength() + segment.getFreeLength() - segment.getPadding();
            int leadY = segment.getWidth() - segment.getPadding();
            textAreaFigure.setText(text);
            Point2D.Double anchorPoint = new Point2D.Double(anchorX + FREE_AREA_FIGURE_OFFSET, anchorY + FREE_AREA_FIGURE_OFFSET);
            Point2D.Double leadPoint = new Point2D.Double(leadX - FREE_AREA_FIGURE_OFFSET, leadY - FREE_AREA_FIGURE_OFFSET);
            textAreaFigure.setBounds(anchorPoint, leadPoint);
            while (textAreaFigure.isTextOverflow())
            {
                textAreaFigure.setFontSize(textAreaFigure.getFontSize() - 1);
                textAreaFigure.invalidate();
            }

            groupFigure.add(textAreaFigure);


        }
    }
}
