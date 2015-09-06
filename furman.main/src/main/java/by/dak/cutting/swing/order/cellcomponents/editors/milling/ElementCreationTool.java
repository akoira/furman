package by.dak.cutting.swing.order.cellcomponents.editors.milling;

import by.dak.draw.MoveScrollHandle;
import org.jhotdraw.draw.AttributeKey;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.tool.CreationTool;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 5.8.2009
 * Time: 12.19.31
 * To change this template use File | Settings | File Templates.
 */
public class ElementCreationTool extends CreationTool
{ //функционирует со Scroll'ом при создании figures
    private MoveScrollHandle scrollHandler;
    private Point2D.Double start = null;
    private ElementDrawing elementDrawing = null;

    public ElementCreationTool(Figure prototype, Map<AttributeKey, Object> attributes, ElementDrawing elementDrawing)
    {
        super(prototype, attributes);
        scrollHandler = new MoveScrollHandle();
        this.elementDrawing = elementDrawing;
    }

    public ElementCreationTool(Figure prototype, Map<AttributeKey, Object> attributes)
    {
        super(prototype, attributes);
        scrollHandler = new MoveScrollHandle();
    }

    public ElementCreationTool(Figure prototype)
    {
        super(prototype);
        scrollHandler = new MoveScrollHandle();
    }

    @Override
    public void mousePressed(MouseEvent evt)
    {
        DrawingView view = editor.findView((Container) evt.getSource());
        view.requestFocus();
        anchor = new Point(evt.getX(), evt.getY());
        isWorking = true;
        fireToolStarted(view);
        getView().clearSelection();
        createdFigure = createFigure();
        Point2D.Double p = viewToDrawing(new Point(anchor.x, anchor.y));
        anchor.x = evt.getX();
        anchor.y = evt.getY();
        createdFigure.setBounds(p, p);
        getDrawing().add(createdFigure);
    }

    @Override
    public void mouseDragged(MouseEvent evt)
    {
        if (start == null)
        {
            start = viewToDrawing(anchor);
        }
        scrollHandler.setView(getView());
        if (createdFigure != null)
        {
            scrollHandler.moveScroll(evt.getPoint());
            Point2D.Double p = constrainPoint(new Point(evt.getX(), evt.getY()));
            createdFigure.willChange();
            createdFigure.setBounds(start, p);
            createdFigure.changed();
        }
    }

    @Override
    public void mouseReleased(MouseEvent evt)
    {
        start = null;
        super.mouseReleased(evt);
    }

    @Override
    protected Figure createFigure()
    {
        Figure f = (Figure) prototype.clone();
        getEditor().applyDefaultAttributesTo(f);

        float fontSize = 0;
        TextFigureRelated figureFontSize = (TextFigureRelated) elementDrawing.getChildren().get(0);
        fontSize = figureFontSize.getTextFigures().get(0).getFontSize();
        TextFigureRelated figureRelated = (TextFigureRelated) f;
        if (fontSize != 0)
        {
            figureRelated.getTextFigures().get(0).setFontSize(fontSize);
        }
        if (prototypeAttributes != null)
        {
            for (Map.Entry<AttributeKey, Object> entry : prototypeAttributes.entrySet())
            {

                entry.getKey().set(f, entry.getValue());
            }
        }
        return f;
    }

}
