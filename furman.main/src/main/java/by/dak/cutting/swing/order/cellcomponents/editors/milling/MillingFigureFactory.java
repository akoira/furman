/*
 * @(#)DrawFigureFactory.java  1.0  February 17, 2004
 *
 * Copyright (c) 1996-2006 by the original authors of JHotDraw
 * and all its contributors.
 * All rights reserved.
 *
 * The copyright of this software is owned by the authors and  
 * contributors of the JHotDraw project ("the copyright holders").  
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * the copyright holders. For details see accompanying license terms. 
 */

package by.dak.cutting.swing.order.cellcomponents.editors.milling;

import by.dak.cutting.swing.order.cellcomponents.editors.milling.glueing.Gluieng;
import org.jhotdraw.draw.AttributeKeys.Orientation;
import org.jhotdraw.draw.AttributeKeys.StrokePlacement;
import org.jhotdraw.draw.AttributeKeys.StrokeType;
import org.jhotdraw.draw.AttributeKeys.Underfill;
import org.jhotdraw.draw.*;
import org.jhotdraw.draw.connector.*;
import org.jhotdraw.draw.decoration.ArrowTip;
import org.jhotdraw.draw.liner.ElbowLiner;
import org.jhotdraw.samples.draw.DrawFigureFactory;
import org.jhotdraw.xml.DefaultDOMFactory;


/**
 * DrawFigureFactory.
 *
 * @author Werner Randelshofer
 * @version 1.0 February 17, 2004 Created.
 */
public class MillingFigureFactory extends DefaultDOMFactory
{
    private final static Object[][] classTagArray = {
            {DrawFigureFactory.class, "drawing"},
            {QuadTreeDrawing.class, "drawing"},
            {DiamondFigure.class, "diamond"},
            {TriangleFigure.class, "triangle"},
            {BezierFigure.class, "bezier"},
            {ArcFigure.class, "Arc2D"},
            {ArcEveryFigure.class, "ArcEvery2D"},
            {RectangleFigure.class, "r"},
            {RoundRectangleFigure.class, "rr"},
            {LineFigure.class, "l"},
            {BezierFigure.class, "b"},
            {LineConnectionFigure.class, "lnk"},
            {TextFigure.class, "t"},
            {TextAreaFigure.class, "ta"},
            {ImageFigure.class, "image"},
            {GroupFigure.class, "g"},
            {CurveFigure.class, "curve"},
            {ElementDrawing.class, "element"},
            {EllipseFigure.class, "ellipse"},


            {ArrowTip.class, "arrowTip"},
            {ChopRectangleConnector.class, "rConnector"},
            {ChopEllipseConnector.class, "ellipseConnector"},
            {ChopRoundRectangleConnector.class, "rrConnector"},
            {ChopTriangleConnector.class, "triangleConnector"},
            {ChopDiamondConnector.class, "diamondConnector"},
            {ChopBezierConnector.class, "bezierConnector"},

            {ElbowLiner.class, "elbowLiner"},
            {Gluieng.class, "glueing"}
    };
    private final static Object[][] enumTagArray = {
            {StrokePlacement.class, "strokePlacement"},
            {StrokeType.class, "strokeType"},
            {Underfill.class, "underfill"},
            {Orientation.class, "orientation"},
    };

    /**
     * Creates a new instance.
     */
    public MillingFigureFactory()
    {
        for (Object[] o : classTagArray)
        {
            addStorableClass((String) o[1], (Class) o[0]);
        }
        for (Object[] o : enumTagArray)
        {
            addEnumClass((String) o[1], (Class) o[0]);
        }
    }
}