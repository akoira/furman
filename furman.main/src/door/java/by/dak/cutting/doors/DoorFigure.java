package by.dak.cutting.doors;

import by.dak.cutting.doors.profile.draw.DownProfileCompFigure;
import by.dak.cutting.doors.profile.draw.LeftProfileCompFigure;
import by.dak.cutting.doors.profile.draw.RightProfileCompFigure;
import by.dak.cutting.doors.profile.draw.TopProfileCompFigure;

import java.awt.geom.Rectangle2D;

/**
 * User: akoyro
 * Date: 12.09.2009
 * Time: 16:16:50
 */
public class DoorFigure extends CellFigure
{
    /**
     * Граничные профеля для данной двери
     */
    private TopProfileCompFigure topProfileFigure = new TopProfileCompFigure();
    private DownProfileCompFigure downProfileFigure = new DownProfileCompFigure();
    private LeftProfileCompFigure leftProfileFigure = new LeftProfileCompFigure();
    private RightProfileCompFigure rightProfileFigure = new RightProfileCompFigure();

    /**
     * Ячейка в которую можно добавлять стоковочные профедя
     */
    private CellFigure dockingCellFigure = new CellFigure();


    public DoorFigure(Rectangle2D.Double rectangle)
    {

        leftProfileFigure.transform(rectangle);
        rightProfileFigure.transform(rectangle);
        topProfileFigure.transform(rectangle);
        downProfileFigure.transform(rectangle);

        add(leftProfileFigure);
        add(rightProfileFigure);
        add(topProfileFigure);
        add(downProfileFigure);

        //добавляем граничные едементы
        boundsFigures.add(leftProfileFigure);
        boundsFigures.add(topProfileFigure);
        boundsFigures.add(rightProfileFigure);
        boundsFigures.add(downProfileFigure);

//        dockingCellFigure.setSelectable(false);
//        dockingCellFigure.setTransformable(false);
//        add(dockingCellFigure);
    }


//    @Override
//    public void setBounds(Point2D.Double anchor, Point2D.Double lead)
//    {
//        Rectangle2D.Double rect = new Rectangle2D.Double();
//        rect.setFrameFromDiagonal(anchor, lead);
//
//        //ограничения для изменеия размера
//        //todo нужен учет всех figures
//        if (rect.getWidth() < leftProfileFigure.getBounds().getWidth() + rightProfileFigure.getBounds().getWidth())
//        {
//            return;
//        }
//
//        if (rect.getHeight() < downProfileFigure.getBounds().getHeight() + topProfileFigure.getBounds().getHeight())
//        {
//            return;
//        }
//
//        bounds.setFrame(rect);
//
//        leftProfileFigure.setProfileComp(door.getLeftProfileComp());
//        leftProfileFigure.transform(getBounds());
//
//        rightProfileFigure.setProfileComp(door.getRightProfileComp());
//        rightProfileFigure.transform(getBounds());
//
//        //уменшаем ячейку на право и на лево
//        Rectangle2D.Double dockingRect = Graphics2DUtils.decreaseRectangle(bounds, Position.left, leftProfileFigure.getBounds().getWidth());
//        dockingRect = Graphics2DUtils.decreaseRectangle(dockingRect, Position.right, rightProfileFigure.getBounds().getWidth());
//
//        topProfileFigure.setProfileComp(door.getUpProfileComponent());
//        topProfileFigure.transform(dockingRect);
//
//        //downProfileFigure.transform(dockingRect);
//
//        dockingRect = Graphics2DUtils.decreaseRectangle(dockingRect, Position.top, topProfileFigure.getBounds().getHeight());
//        dockingRect = Graphics2DUtils.decreaseRectangle(dockingRect, Position.down, downProfileFigure.getBounds().getHeight());
//        dockingCellFigure.setBounds(dockingRect);
//    }


    @Override
    public Rectangle2D.Double getBounds()
    {
        if (cachedBounds == null)
        {
            super.getBounds();
            if (getCellShape() != null)
            {
                Rectangle2D cSBounds = getCellShape().getBounds2D();
                cachedBounds.add(cSBounds);
            }
        }
        return cachedBounds;
    }

}
