package by.dak.cutting.doors.profile.draw;

import by.dak.cutting.doors.profile.ProfileComp;
import by.dak.cutting.draw.Constants;
import by.dak.draw.Graphics2DUtils;
import by.dak.draw.Position;
import org.jhotdraw.draw.AbstractAttributedFigure;
import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.draw.ConnectionFigure;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.connector.Connector;
import org.jhotdraw.draw.handle.BoundsOutlineHandle;
import org.jhotdraw.draw.handle.DragHandle;
import org.jhotdraw.draw.handle.Handle;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Графическое предстовление компонента профиля.
 * User: akoyro
 * Date: 12.09.2009
 * Time: 13:14:22
 */
public abstract class AProfileCompFigure extends AbstractAttributedFigure
{

    private static final BasicStroke DEFAULT_JOIN_STROKE = new BasicStroke(1);
    private static final BasicStroke DEFAULT_FILLING_STROKE = Constants.DEFAULT_DUSH_STROKE;

    private ProfileComp profileComp;

    
    /**
     * Фигура для отображения границы для стоковки модулей.
     */
    Rectangle2D.Double joinBounds = new Rectangle2D.Double();

    private Rectangle2D.Double fillingBounds = new Rectangle2D.Double();

    private Position position;


//    private double profileWidth;
//    private List<Figure> lines;
//    private java.util.List<Figure> linesButt;

    public ProfileComp getProfileComp()
    {
        return profileComp;
    }

    public void setProfileComp(ProfileComp profileComp)
    {
        this.profileComp = profileComp;

    }

    @Override
    public boolean isSelectable()
    {
        return true;
    }


    @Override
    protected void drawFill(Graphics2D g)
    {
        Color color = get(AttributeKeys.FILL_COLOR);
        if (color != null)
        {
            g.fill(joinBounds);
        }
    }

    @Override
    public Connector findConnector(Point2D.Double p, ConnectionFigure prototype)
    {
        return null;
    }

    @Override
    public Connector findCompatibleConnector(Connector c, boolean isStartConnector)
    {
        return null;
    }

    @Override
    protected void drawStroke(Graphics2D g)
    {
        g.setStroke(DEFAULT_JOIN_STROKE);
        g.draw(joinBounds);
        g.setStroke(DEFAULT_FILLING_STROKE);
        g.draw(fillingBounds);
    }



    @Override
    public void setBounds(Point2D.Double anchor, Point2D.Double lead)
    {
        Point2D.Double min;
        Point2D.Double max;

        //todo зачем эта проверка и почему такой NEAR
        if (anchor.distance(Constants.NEAR) < lead.distance(Constants.NEAR))
        {
            min = anchor;
            max = getBoundsPoint(anchor, lead);
        }
        else
        {
            min = getBoundsPoint(anchor, lead);
            max = anchor;
        }

        updateProfileComp(min, max);
        joinBounds.setRect(calcJoinBounds(min, max));
        fillingBounds.setRect(calcFillingBounds());

    }


    private void updateLines()
    {
//        upLine.setStartPoint(new Point2D.Double(bounds.getMinX(), bounds.getMinY()));
//        upLine.setEndPoint(new Point2D.Double(bounds.getMaxX(), bounds.getMinY()));
//        upLine.setDirection(-1);
//
//        rigthLine.setStartPoint(new Point2D.Double(bounds.getMaxX(), bounds.getMinY()));
//        rigthLine.setEndPoint(new Point2D.Double(bounds.getMaxX(), bounds.getMaxY()));
//        rigthLine.setDirection(-1);
//
//        downLine.setStartPoint(new Point2D.Double(bounds.getMaxX(), bounds.getMaxY()));
//        downLine.setEndPoint(new Point2D.Double(bounds.getMinX(), bounds.getMaxY()));
//        downLine.setDirection(1);
//
//        leftLine.setStartPoint(new Point2D.Double(bounds.getMinX(), bounds.getMaxY()));
//        leftLine.setEndPoint(new Point2D.Double(bounds.getMinX(), bounds.getMinY()));
//        leftLine.setDirection(-1);
    }

    @Override
    public Rectangle2D.Double getDrawingArea()
    {
        //todo почему 10
        return Graphics2DUtils.increaseRectangle(joinBounds, 10);
    }

    @Override
    public Rectangle2D.Double getBounds()
    {
        return (Rectangle2D.Double) joinBounds.getBounds2D();
    }

    @Override
    public boolean contains(Point2D.Double p)
    {
        return joinBounds.contains(p);
    }

    @Override
    public Object getTransformRestoreData()
    {
        //todo надо изменить код
        return joinBounds.clone();
    }

    @Override
    public void restoreTransformTo(Object restoreData)
    {
        //todo надо изменить код
        Rectangle2D.Double r = (Rectangle2D.Double) restoreData;
        joinBounds.setRect(r);
    }


    @Override
    //todo хэндлы не нужны для вертикальных и горизонтальных
    public Collection<Handle> createHandles(int detailLevel)
    {
        LinkedList<Handle> handles = new LinkedList<Handle>();
        switch (detailLevel)
        {
            case -1:
                handles.add(new BoundsOutlineHandle(this, false, true));
                break;
            case 0:
                handles.add(new BoundsOutlineHandle(this));
//                handles.addAll(getResizeHandles());
//                handles.add(getDragHandle());
                break;
        }
        return handles;
    }


    /**
     * Возвращает кроевые линии к которым нельзя стыковать профили
     *
     * @return
     */
    public abstract List<Figure> getLines();

    /**
     * Возвращает кроевые линии к которым можно стыковать профили
     *
     * @return
     */
    public abstract List<Figure> getLinesButt();


    /**
     * Предоставляет упровляющих handle для перетаскивания этой фигуры.
     *
     * @return
     */
    protected abstract DragHandle getDragHandle();

    protected abstract List<Handle> getResizeHandles();

    /**
     * Опредяляет возвращает точку для изменения границ
     *
     * @param anchor
     * @param lead
     * @return
     */
    protected abstract Point2D.Double getBoundsPoint(Point2D.Double anchor, Point2D.Double lead);

    /**
     * Вычисляет стоковочные границы
     *
     * @param min
     * @param max
     * @return
     */
    protected abstract Rectangle2D.Double calcJoinBounds(Point2D.Double min, Point2D.Double max);

    /**
     * Вычисляет стоковочные границы
     *
     * @return
     */
    protected abstract Rectangle2D.Double calcFillingBounds();


    /**
     * Обнавляет ProfileComponent после изменений размеров
     *
     * @param min
     * @param max
     */
    protected abstract void updateProfileComp(Point2D.Double min, Point2D.Double max);


    /**
     * Вызывается при изменении всей ячейки
     * @param cellBounds
     */
    public abstract void transform(Rectangle2D.Double cellBounds);

    /**
     * Метод не используются так как нужны сложные расчеты вместо используется   transform(Rectangle2D.Double cellBounds)
     * @param tx
     */
    @Override
    public void transform(AffineTransform tx)
    {

    }

    public Position getPosition()
    {
        return position;
    }

    public void setPosition(Position position)
    {
        this.position = position;
    }
}
