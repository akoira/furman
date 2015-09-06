package by.dak.cutting.linear.report.data;

import by.dak.cutting.cut.guillotine.Segment;
import by.dak.cutting.cut.swing.BoundSegmentPainter;
import by.dak.cutting.cut.swing.SegmentPainter;
import by.dak.cutting.cut.swing.furniture.FurnitureElementPainter;
import by.dak.cutting.cut.swing.furniture.FurnitureFreeAreaTitlePainter;
import by.dak.draw.Graphics2DUtils;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: dkoyro
 * Date: 26.04.11
 * Time: 14:03
 * To change this template use File | Settings | File Templates.
 */
public class LinearBufferedImageCreator
{
    public static final float IMAGE_WIDTH = 1280;
    public static final float IMAGE_HEIGHT = 1024;
    public static final int OFFSET_Y = 50;
    public static final int borderWidth = 3; //отвечает за толщину краёв палки
    public static final float scaleFactorY = 0.3f;//размер палки по высоте не меняется
    public static final int START_Y = 10; //сдвиг относительно от начала картинки

    public BufferedImage createBufferedImage(List<Segment> segments)
    {
        System.gc();
        int segmentWidth = findMaxWidth(segments);
        float scale = IMAGE_WIDTH / segmentWidth;
        int width = 0;
        int height = 0;

        if (scale > 1)
        {
            scale = 1;
            width = (int) IMAGE_WIDTH;
            height = (int) IMAGE_HEIGHT;
        }
        else
        {
            width = (int) (scale * segmentWidth + 2 * borderWidth);
            height = (int) IMAGE_HEIGHT;    //height для картинки константа
        }
        BufferedImage bimage = Graphics2DUtils.createBufferedImage(width,
                height);

        Graphics2D g2d = bimage.createGraphics();
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(0, 0, width, height);
        AffineTransform transform = g2d.getTransform();
        AffineTransform newTransform = new AffineTransform(transform);
        newTransform.concatenate(AffineTransform.getScaleInstance(scale, scaleFactorY));
        newTransform.concatenate(AffineTransform.getTranslateInstance(borderWidth * 2, borderWidth * 2));
        g2d.setTransform(newTransform);
        int offsetY = START_Y;
        for (Segment segment : segments)
        {
            SegmentPainter segmentPainter = new SegmentPainter();
            segmentPainter.setBorderSheetWidth(borderWidth);
            segmentPainter.setScale(scale);
            segmentPainter.setElementPainter(new FurnitureElementPainter());
            segmentPainter.setFreeAreaTitlePainter(new FurnitureFreeAreaTitlePainter());
            segmentPainter.drawSegment(g2d, offsetY, (int) (borderWidth * 1 / scale), segment);

            BoundSegmentPainter boundSegmentPainter = new BoundSegmentPainter();
            boundSegmentPainter.setScale(scale);
            boundSegmentPainter.drawSegmentCut(g2d, offsetY, borderWidth * 1 / scale, segment, false);

            offsetY += segment.getLength() + OFFSET_Y;
        }
        g2d.setColor(Color.BLACK);
        g2d.setTransform(transform);
        g2d.dispose();
        bimage.flush();
        System.gc();
        return bimage;
    }

    /**
     * из всех сегментов находит максимальную длину сегмента, чтобы рассчитать масштаб
     *
     * @return
     */
    private int findMaxWidth(List<Segment> segments)
    {
        int maxWidth = segments.get(0).getWidth();
        for (Segment segment : segments)
        {
            if (maxWidth < segment.getWidth())
            {
                maxWidth = segment.getWidth();
            }
        }
        return maxWidth;
    }
}
