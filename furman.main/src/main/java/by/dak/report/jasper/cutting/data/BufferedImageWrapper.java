package by.dak.report.jasper.cutting.data;

import by.dak.cutting.cut.guillotine.Segment;
import by.dak.cutting.cut.swing.BoundSegmentPainter;
import by.dak.cutting.cut.swing.SegmentPainter;
import by.dak.cutting.cut.swing.board.BoardElementPainter;
import by.dak.cutting.cut.swing.board.BoardFreeAreaTitlePainter;
import by.dak.cutting.swing.SegmentFigure;
import by.dak.draw.Graphics2DUtils;
import by.dak.persistence.entities.Cutter;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.util.Vector;

/**
 * @author akoyro
 * @version 0.1 09.02.2009
 * @introduced [Preview Plugin Support]
 * @since 2.0.0
 */
public class BufferedImageWrapper extends BufferedImage
{
    static float lengthImage = 1024;
    static float widthImage = 768;
    //todo значения влияет на размер сохраненной картинки и размер шрифта в этой картинке
    static int borderWidth = 10;

    private BufferedImage sourceImage;

    private Segment segment;
    private Cutter cutter;

    private SegmentFigure.LengthWidhtGetter lengthWidhtGetter = new SegmentFigure.LengthWidhtGetter();


    public BufferedImageWrapper(Segment segment, Cutter cutter)
    {
        super(1, 1, Transparency.OPAQUE);
        this.segment = segment;
        this.cutter = cutter;
        lengthWidhtGetter.setSegment(segment);
        lengthWidhtGetter.setCutter(cutter);

    }

    private BufferedImage getSourceImage()
    {
        if (sourceImage == null)
        {
            sourceImage = createImage();
        }
        return sourceImage;
    }


    private BufferedImage createImage()
    {
        System.gc();
        double scale = Math.min(lengthImage / lengthWidhtGetter.getLength(), widthImage / lengthWidhtGetter.getWidth());
        int width = (int) (scale * lengthWidhtGetter.getLength() + borderWidth * 2);
        int heigth = (int) (scale * lengthWidhtGetter.getWidth() + borderWidth * 2);

        BufferedImage bimage = Graphics2DUtils.createBufferedImage(width,
                heigth);

        Graphics2D g2d = bimage.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, heigth);
        AffineTransform transform = g2d.getTransform();
        AffineTransform newTransform = new AffineTransform(transform);
        newTransform.concatenate(AffineTransform.getScaleInstance(scale, scale));
        newTransform.concatenate(AffineTransform.getTranslateInstance(borderWidth * 2, borderWidth * 2));
        g2d.setTransform(newTransform);

        BoardElementPainter elementPainter = new BoardElementPainter();
        elementPainter.setRotate(isRotate());

        BoardFreeAreaTitlePainter boardFreeAreaTitlePainter = new BoardFreeAreaTitlePainter();
        boardFreeAreaTitlePainter.setRotate(isRotate());

        SegmentPainter segmentPainter = new SegmentPainter();
        segmentPainter.setRotate(isRotate());
        segmentPainter.setBorderSheetWidth(borderWidth);
        segmentPainter.setScale((float) scale);
        segmentPainter.setElementPainter(elementPainter);
        segmentPainter.setFreeAreaTitlePainter(boardFreeAreaTitlePainter);

        BoundSegmentPainter boundSegmentPainter = new BoundSegmentPainter();
        boundSegmentPainter.setRotate(isRotate());
        boundSegmentPainter.setScale(scale);

        segmentPainter.drawSegment(g2d, 0, 0, segment);
        boundSegmentPainter.drawSegmentCut(g2d, 0, 0, segment, false);

        g2d.setTransform(transform);
        g2d.dispose();
        bimage.flush();
        System.gc();
        return bimage;

    }

    public boolean isRotate()
    {
        switch (cutter.getDirection())
        {
            case horizontal:
                return true;
            case vertical:
                return false;
            default:
                throw new IllegalArgumentException();
        }
    }


    @Override
    public int getType()
    {
        return getSourceImage().getType();
    }

    @Override
    public ColorModel getColorModel()
    {
        return getSourceImage().getColorModel();
    }

    @Override
    public WritableRaster getRaster()
    {
        return getSourceImage().getRaster();
    }

    @Override
    public WritableRaster getAlphaRaster()
    {
        return getSourceImage().getAlphaRaster();
    }

    @Override
    public int getRGB(int x, int y)
    {
        return getSourceImage().getRGB(x, y);
    }

    @Override
    public int[] getRGB(int startX, int startY, int w, int h, int[] rgbArray, int offset, int scansize)
    {
        return getSourceImage().getRGB(startX, startY, w, h, rgbArray, offset, scansize);
    }

    @Override
    public void setRGB(int x, int y, int rgb)
    {
        getSourceImage().setRGB(x, y, rgb);
    }

    @Override
    public void setRGB(int startX, int startY, int w, int h, int[] rgbArray, int offset, int scansize)
    {
        getSourceImage().setRGB(startX, startY, w, h, rgbArray, offset, scansize);
    }

    @Override
    public int getWidth()
    {
        return getSourceImage().getWidth();
    }

    @Override
    public int getHeight()
    {
        return getSourceImage().getHeight();
    }

    @Override
    public int getWidth(ImageObserver observer)
    {
        return getSourceImage().getWidth(observer);
    }

    @Override
    public int getHeight(ImageObserver observer)
    {
        return getSourceImage().getHeight(observer);
    }

    @Override
    public ImageProducer getSource()
    {
        return getSourceImage().getSource();
    }

    @Override
    public Object getProperty(String name, ImageObserver observer)
    {
        return getSourceImage().getProperty(name, observer);
    }

    @Override
    public Object getProperty(String name)
    {
        return getSourceImage().getProperty(name);
    }

    @Override
    public Graphics getGraphics()
    {
        return getSourceImage().getGraphics();
    }

    @Override
    public Graphics2D createGraphics()
    {
        return getSourceImage().createGraphics();
    }

    @Override
    public BufferedImage getSubimage(int x, int y, int w, int h)
    {
        return getSourceImage().getSubimage(x, y, w, h);
    }

    @Override
    public boolean isAlphaPremultiplied()
    {
        return getSourceImage().isAlphaPremultiplied();
    }

    @Override
    public void coerceData(boolean isAlphaPremultiplied)
    {
        getSourceImage().coerceData(isAlphaPremultiplied);
    }

    @Override
    public String toString()
    {
        return getSourceImage().toString();
    }

    @Override
    public Vector<RenderedImage> getSources()
    {
        return getSourceImage().getSources();
    }

    @Override
    public String[] getPropertyNames()
    {
        return getSourceImage().getPropertyNames();
    }

    @Override
    public int getMinX()
    {
        return getSourceImage().getMinX();
    }

    @Override
    public int getMinY()
    {
        return getSourceImage().getMinY();
    }

    @Override
    public SampleModel getSampleModel()
    {
        return getSourceImage().getSampleModel();
    }

    @Override
    public int getNumXTiles()
    {
        return getSourceImage().getNumXTiles();
    }

    @Override
    public int getNumYTiles()
    {
        return getSourceImage().getNumYTiles();
    }

    @Override
    public int getMinTileX()
    {
        return getSourceImage().getMinTileX();
    }

    @Override
    public int getMinTileY()
    {
        return getSourceImage().getMinTileY();
    }

    @Override
    public int getTileWidth()
    {
        return getSourceImage().getTileWidth();
    }

    @Override
    public int getTileHeight()
    {
        return getSourceImage().getTileHeight();
    }

    @Override
    public int getTileGridXOffset()
    {
        return getSourceImage().getTileGridXOffset();
    }

    @Override
    public int getTileGridYOffset()
    {
        return getSourceImage().getTileGridYOffset();
    }

    @Override
    public Raster getTile(int tileX, int tileY)
    {
        return getSourceImage().getTile(tileX, tileY);
    }

    @Override
    public Raster getData()
    {
        return getSourceImage().getData();
    }

    @Override
    public Raster getData(Rectangle rect)
    {
        return getSourceImage().getData(rect);
    }

    @Override
    public WritableRaster copyData(WritableRaster outRaster)
    {
        return getSourceImage().copyData(outRaster);
    }

    @Override
    public void setData(Raster r)
    {
        getSourceImage().setData(r);
    }

    @Override
    public void addTileObserver(TileObserver to)
    {
        getSourceImage().addTileObserver(to);
    }

    @Override
    public void removeTileObserver(TileObserver to)
    {
        getSourceImage().removeTileObserver(to);
    }

    @Override
    public boolean isTileWritable(int tileX, int tileY)
    {
        return getSourceImage().isTileWritable(tileX, tileY);
    }

    @Override
    public Point[] getWritableTileIndices()
    {
        return getSourceImage().getWritableTileIndices();
    }

    @Override
    public boolean hasTileWriters()
    {
        return getSourceImage().hasTileWriters();
    }

    @Override
    public WritableRaster getWritableTile(int tileX, int tileY)
    {
        return getSourceImage().getWritableTile(tileX, tileY);
    }

    @Override
    public void releaseWritableTile(int tileX, int tileY)
    {
        getSourceImage().releaseWritableTile(tileX, tileY);
    }

    @Override
    public int getTransparency()
    {
        return getSourceImage().getTransparency();
    }

    @Override
    public Image getScaledInstance(int width, int height, int hints)
    {
        return getSourceImage().getScaledInstance(width, height, hints);
    }

    @Override
    public void flush()
    {
        getSourceImage().flush();
    }

    @Override
    public ImageCapabilities getCapabilities(GraphicsConfiguration gc)
    {
        return getSourceImage().getCapabilities(gc);
    }

    @Override
    public void setAccelerationPriority(float priority)
    {
        getSourceImage().setAccelerationPriority(priority);
    }

    @Override
    public float getAccelerationPriority()
    {
        return getSourceImage().getAccelerationPriority();
    }
}
