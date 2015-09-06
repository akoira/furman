package by.dak.draw;


import by.dak.utils.MathUtils;

import java.awt.*;
import java.awt.geom.*;

/**
 * @author admin
 * @version 0.1 14.10.2008
 * @introduced [Preview Plugin Support]
 * @since 2.0.0
 */
public class DimensionLinePainter extends AbstractPainter
{

    private static final GeneralPath DIMENSION_LINE_END;

    static
    {
        DIMENSION_LINE_END = new GeneralPath();
        DIMENSION_LINE_END.moveTo(-5, 5);
        DIMENSION_LINE_END.lineTo(5, -5);
        DIMENSION_LINE_END.moveTo(0, 5);
        DIMENSION_LINE_END.lineTo(0, -5);
    }

    private DimensionLine dimensionLine;
    private boolean selected;
//    private PlanComponent.PaintMode paintMode;

    public DimensionLinePainter(DimensionLine dimensionLine, boolean selected)
    {
        this.dimensionLine = dimensionLine;
        this.selected = selected;
    }

    public void paint(Graphics2D g2D)
    {
        AffineTransform previousTransform = g2D.getTransform();
        double angle = Math.atan2(dimensionLine.getYEnd() - dimensionLine.getYStart(),
                dimensionLine.getXEnd() - dimensionLine.getXStart());
        float dimensionLineLength = (float) Point2D.distance(dimensionLine.getXStart(), dimensionLine.getYStart(),
                dimensionLine.getXEnd(), dimensionLine.getYEnd());
        g2D.translate(dimensionLine.getXStart(), dimensionLine.getYStart());
        g2D.rotate(angle);
        g2D.translate(0, dimensionLine.getOffset());

//        if (paintMode == PlanComponent.PaintMode.PAINT
//            && selected)
//        {
//            // Draw selection border
//            g2D.setPaint(getSelectionOutlinePaint());
//            g2D.setStroke(getSelectionOutlineStroke());
//            // Draw dimension line
//            g2D.draw(new Line2D.Float(0, 0, dimensionLineLength, 0));
//            // Draw dimension line ends
//            g2D.draw(DIMENSION_LINE_END);
//            g2D.translate(dimensionLineLength, 0);
//            g2D.draw(DIMENSION_LINE_END);
//            g2D.translate(-dimensionLineLength, 0);
//            // Draw extension lines
//            g2D.draw(new Line2D.Float(0, -dimensionLine.getOffset(), 0, -5));
//            g2D.draw(new Line2D.Float(dimensionLineLength, -dimensionLine.getOffset(), dimensionLineLength, -5));
//
//            g2D.setPaint(getForegroundColor());
//
//        }

        g2D.setColor(getLineForegroundColor());
        g2D.setStroke(getDimensionLineStroke());
        // Draw dimension line
        g2D.draw(new Line2D.Float(0, 0, dimensionLineLength, 0));
        // Draw dimension line ends
        g2D.draw(DIMENSION_LINE_END);
        g2D.translate(dimensionLineLength, 0);
        g2D.draw(DIMENSION_LINE_END);
        g2D.translate(-dimensionLineLength, 0);
        // Draw extension lines
        g2D.setStroke(getExtensionLineStroke());
        g2D.draw(new Line2D.Double(0, -dimensionLine.getOffset(), 0, -5));
        g2D.draw(new Line2D.Double(dimensionLineLength, -dimensionLine.getOffset(), dimensionLineLength, -5));

        // Draw dimension length in middle
        String lengthText = String.valueOf(MathUtils.round(dimensionLineLength * PaintAttributesProvider.getInstanse().getPlanScale(), 2));
        Rectangle2D lengthTextBounds = g2D.getFontMetrics().getStringBounds(lengthText, g2D);

        //рисуем чистый квадрат под текстом
        g2D.setPaint(getBackgroundColor());
        Rectangle2D r = new Rectangle2D.Float((dimensionLineLength - (float) lengthTextBounds.getWidth()) / 2,
                dimensionLine.getOffset() <= 0
                        ? -g2D.getFontMetrics().getDescent() - 1 / getPlanScale() - g2D.getFontMetrics().getFont().getSize2D()
                        : g2D.getFontMetrics().getAscent() + 1 / getPlanScale() - g2D.getFontMetrics().getFont().getSize2D(), (float) lengthTextBounds.getWidth(), (float) lengthTextBounds.getHeight());
        g2D.fill(r);


        Graphics2D textG2D = (Graphics2D) g2D.create((int) r.getX(),
                (int) r.getY(),
                (int) r.getWidth(),
                (int) r.getHeight());
        paintText(textG2D, lengthText, lengthTextBounds, angle);

//        g2D.setColor(getTextForegroundColor());
//        g2D.rotate(- Math.PI);
//        g2D.drawString(lengthText,
//                       (dimensionLineLength - (float) lengthTextBounds.getWidth()) / 2,
//                       dimensionLine.getOffset() <= 0
//                       ? -g2D.getFontMetrics().getDescent() - 1 / getPlanScale()
//                       : g2D.getFontMetrics().getAscent() + 1 / getPlanScale());

        g2D.setTransform(previousTransform);
    }

    private void paintText(Graphics2D g2D, String text, Rectangle2D textBounds, double angle)
    {

        g2D.setColor(getTextForegroundColor());
        if (angle > 0)
            g2D.rotate(Math.PI, textBounds.getWidth() / 2d, g2D.getFont().getSize() / 2d);
        g2D.drawString(text,
                0,
                g2D.getFont().getSize());
//                       dimensionLine.getOffset() <= 0
//                       ? -g2D.getFontMetrics().getDescent() - 1 / getPlanScale()
//                       : g2D.getFontMetrics().getAscent() + 1 / getPlanScale());

    }


    private Color getLineForegroundColor()
    {
        return new Color(103, 104, 110);
    }

    private Color getTextForegroundColor()
    {
        return PaintAttributesProvider.getInstanse().getForegroundColor();
    }

//    public Format getUnitFormat()
//    {
//        return PaintAttributesProvider.getInstanse().getUnitFormat();
//    }


    public Stroke getDimensionLineStroke()
    {
        return PaintAttributesProvider.getInstanse().getPieceBorderStroke();
    }

    public Stroke getExtensionLineStroke()
    {
        BasicStroke extensionLineStroke = new BasicStroke(
                1 / getPlanScale(), BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 0,
                new float[]{2 / getPlanScale(), 3 / getPlanScale()}, 4 / getPlanScale());
        return extensionLineStroke;
    }

}