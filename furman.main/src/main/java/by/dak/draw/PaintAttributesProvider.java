package by.dak.draw;


import javax.swing.*;
import java.awt.*;
import java.text.Format;
import java.util.HashMap;

/**
 * @author admin
 * @version 0.1 14.10.2008
 * @introduced [Preview Plugin Support]
 * @since 2.0.0
 */

/**
 * Предоставляет разичные атрибуды для прорисовки (color, )
 */
public class PaintAttributesProvider
{
    private Color selectionColor;
    /**
     * Цвет окантовки вокруг детали когда она выбранна
     */
    private Paint selectionOutlinePaint;
    /**
     * Окантовка вокруг детали когда она выбранна
     */
    private Stroke selectionOutlineStroke;

    /**
     * Оконтовка детали
     */
    private Stroke pieceBorderStroke;

    /**
     * Оконтовка детали для стойки
     */
    private Stroke falsePieceBorderStroke;

    private Color gridColor = UIManager.getColor("controlShadow");


    private Color backgroundColor = Color.WHITE;
    private Color foregroundColor = Color.BLACK;

    private Format unitFormat;

    private float planScale = 1f;

    private HashMap cache = new HashMap();

    private static final PaintAttributesProvider PAINT_ATTRIBUTES_PROVIDER = new PaintAttributesProvider();

    public Paint getSelectionOutlinePaint()
    {
        if (selectionOutlinePaint == null)
        {
            selectionOutlinePaint = new Color(getSelectionColor().getRed(), getSelectionColor().getGreen(), getSelectionColor().getBlue(), 128);
        }
        return selectionOutlinePaint;
    }

    public Stroke getSelectionOutlineStroke()
    {
        if (cache.get("SelectionOutlineStroke") == null)
        {
            selectionOutlineStroke = new BasicStroke(6 / planScale, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
            cache.put("SelectionOutlineStroke", selectionOutlineStroke);
        }
        return selectionOutlineStroke;
    }

    public Stroke getPieceBorderStroke()
    {
        if (cache.get("PieceBorderStroke") == null)
        {
            pieceBorderStroke = new BasicStroke(1f / getPlanScale());
            cache.put("PieceBorderStroke", pieceBorderStroke);
        }
        return pieceBorderStroke;
    }

    public Color getSelectionColor()
    {
        if (selectionColor == null)
        {
//            if (OperatingSystem.isMacOSX())
//            {
//                if (OperatingSystem.isMacOSXLeopardOrSuperior())
//                {
//                    selectionColor = UIManager.getColor("List.selectionBackground").darker();
//                }
//                else
//                {
//                    selectionColor = UIManager.getColor("textHighlight");
//                }
//            }
//            else
//            {
//                // On systems different from Mac OS X, take a darker color
            selectionColor = UIManager.getColor("textHighlight").darker();
//            }
        }
        return selectionColor;
    }

    public static PaintAttributesProvider getInstanse()
    {
        return PAINT_ATTRIBUTES_PROVIDER;
    }

    public float getPlanScale()
    {
        return planScale;
    }

    public void setPlanScale(float planScale)
    {
        this.planScale = planScale;
        cache.clear();
    }


    public Color getBackgroundColor()
    {
        return backgroundColor;
    }

    public Color getForegroundColor()
    {
        return foregroundColor;
    }

    public Stroke getFalsePieceBorderStroke()
    {
        if (cache.get("FalsePieceBorderStroke") == null)
        {
            falsePieceBorderStroke = new BasicStroke(
                    1 / getPlanScale(), BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 0,
                    new float[]{50 / getPlanScale(), 5 / getPlanScale(), 10 / getPlanScale(), 5 / getPlanScale()}, 2 / getPlanScale());
            cache.put("FalsePieceBorderStroke", falsePieceBorderStroke);
        }
        return falsePieceBorderStroke;
    }

//    public Format getUnitFormat()
//    {
//        if (unitFormat == null)
//        {
//            unitFormat = new DecimalFormat(getUnit() == UserPreferences.Unit.INCH
//                                           ? "#.###"
//                                           : "#");
//        }
//        return unitFormat;
//    }
//
//    public UserPreferences.Unit getUnit()
//    {
//        return UserPreferences.Unit.MILIMETER;
//    }

    public Color getGridColor()
    {
        if (gridColor == null)
        {
            //gridColor = UIManager.getColor("controlShadow");
            gridColor = new Color(225, 225, 223);
        }
        return gridColor;
    }
}