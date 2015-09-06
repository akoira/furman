package by.dak.cutting.swing.order.cellcomponents.editors.milling;

import org.jhotdraw.draw.AttributeKey;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.tool.CreationTool;

import java.awt.geom.Point2D;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 29.7.2009
 * Time: 18.25.20
 * To change this template use File | Settings | File Templates.
 */
public class EllipseCreationTool extends CreationTool
{
    private ArcFigure arc1 = new ArcFigure(0, 90);
    private ArcFigure arc2 = new ArcFigure(90, 90);
    private ArcFigure arc3 = new ArcFigure(180, 90);
    private ArcFigure arc4 = new ArcFigure(270, 90);
    private ElementDrawing elementDrawing;

    public EllipseCreationTool(Figure prototype, Map<AttributeKey, Object> attributes, ElementDrawing elementDrawing)
    {
        super(prototype, attributes);
        this.elementDrawing = elementDrawing;

    }

    @Override
    public void activate(DrawingEditor editor)
    {
        super.activate(editor);
        getView().clearSelection();
        clearArea();
        calcElements();
        drawElements();
        super.fireToolDone();
        super.deactivate(editor);
    }

    private void calcElements()
    {
        double position1 = elementDrawing.getElement().getHeight() / 2;
        double position2 = elementDrawing.getElement().getWidth() / 2;

        Point2D.Double p1 = new Point2D.Double(0, 0);                       //точки для построения 4-х дуг
        Point2D.Double p2 = new Point2D.Double(position2, position1);       //
        Point2D.Double p3 = new Point2D.Double(position2, 0);               //
        Point2D.Double p4 = new Point2D.Double(position2 * 2, position1);   //
        Point2D.Double p5 = new Point2D.Double(position2 * 2, position1 * 2);//
        Point2D.Double p6 = new Point2D.Double(0, position1);                //
        Point2D.Double p7 = new Point2D.Double(position2, position1 * 2);    //

        arc1.setBounds(p3, p4);
        arc2.setBounds(p1, p2);
        arc3.setBounds(p6, p7);
        arc4.setBounds(p2, p5);
    }

    private void clearArea()
    {
        elementDrawing.removeAllChildren();
    }

    private void drawElements()
    {
        elementDrawing.add(arc1);
        elementDrawing.add(arc2);
        elementDrawing.add(arc3);
        elementDrawing.add(arc4);
    }
}
