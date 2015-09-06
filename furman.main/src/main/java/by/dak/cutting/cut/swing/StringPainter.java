package by.dak.cutting.cut.swing;

import org.apache.commons.lang.StringUtils;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: 18.02.2009
 * Time: 17:51:34
 * To change this template use File | Settings | File Templates.
 */
public class StringPainter extends AbstractPainter implements by.dak.cutting.configuration.Constants
{

    public final static int DEFAULT_FONT_SIZE = 16;

    public static final String DELIM = ", ";

    private Rectangle rectangle;

    private String text;

    private TextHelper textHelper;

    public StringPainter(Rectangle rectangle)
    {
        this.rectangle = rectangle;
    }

    public void drawString(Graphics g)
    {
        if (text == null)
        {
            return;
        }
        Font oldFont = g.getFont();


        Graphics2D g2 = createGraphics2D(g, rectangle);
        textHelper = new TextHelper(g2);

        g2.setFont(textHelper.getFont());

        AffineTransform origTransform = g2.getTransform();
        AffineTransform transform = new AffineTransform(origTransform);
        g2.setTransform(transform);
        drawString(g2, text);
        g2.setTransform(origTransform);
        g.setFont(oldFont);
    }

    private double getHeight()
    {
        return isRotate() ? rectangle.getWidth() : rectangle.getHeight();
    }

    private double getWidth()
    {
        return isRotate() ? rectangle.getHeight() : rectangle.getWidth();
    }

    protected void drawString(Graphics g, String text)
    {
        assert text != null;

        String[] texts = new String[]{text};

        Graphics2D g2d = (Graphics2D) g;
        AffineTransform original = g2d.getTransform();

        int cX = (int) getWidth() / 2;
        int cY = (int) getHeight() / 2;

        if (!getTextHelper().isSizeAccepted())
        {
            texts = getTextHelper().reduseTextLength();
        }

        int sw = g.getFontMetrics().stringWidth(text);
        int sh = g.getFontMetrics().getHeight();
        int fd = g.getFontMetrics().getDescent();

        int sX = cX - sw / 2;
        int sY = cY + fd;

        boolean isVert = isVertical(sw);
        if (isVert)
        {
            AffineTransform transform = new AffineTransform(g2d.getTransform());
            transform.concatenate(AffineTransform.getRotateInstance(Math.PI / 2, cX, cY));
            g2d.setTransform(transform);
        }
        double areaL = isVert ? getHeight() : getWidth();
        if (false) //todo sw > areaL)
        {
            String[] strings = StringUtils.split(text, ", ");
            ArrayList<String> result = new ArrayList<String>();
            String tmp = strings[0];
            for (int i = 1; i < strings.length; i++)
            {
                String string = strings[i];
                StringBuffer buffer = new StringBuffer(tmp).append(", ").append(string);
                sw = g.getFontMetrics().stringWidth(buffer.toString());
                if (sw > areaL)
                {
                    g.drawString(tmp, sX, sY);
                    tmp = string;
                    sY += sh / 2;
                }
                else
                {
                    tmp = buffer.toString();
                }
            }
            g.drawString(tmp, sX, sY);
        }
        else
        {
            for (String s : texts)
            {
                g.drawString(s, sX, sY);
                sY += sh;
            }
        }

        g2d.setTransform(original);
    }

    private boolean isVertical(int lengthString)
    {
        boolean isVert = lengthString > getWidth() && getWidth() < getHeight();
        return isVert;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public TextHelper getTextHelper()
    {
        return textHelper;
    }

    public class TextHelper
    {

        private Graphics2D g2D;

        private Font font;

        private boolean isSizeAccepted = true;

        public TextHelper(Graphics2D g2D)
        {
            this.g2D = g2D;
            int size = (int) (DEFAULT_FONT_SIZE / getScale());
            font = new Font(g2D.getFont().getName(), Font.BOLD, size);
            int w = g2D.getFontMetrics(font).stringWidth(text);
            double side = isVertical(w) ? getHeight() : getWidth();
            if (w > side)
            {
                size = (int) (size / 1.2);
                font = new Font(g2D.getFont().getName(), Font.BOLD, size);
                isSizeAccepted = false;
            }
        }

        public boolean isSizeAccepted()
        {
            return isSizeAccepted;
        }

        public Font getFont()
        {
            return font;
        }

        public String[] reduseTextLength()
        {
            java.util.List<String> result = new ArrayList<String>();
            result.add(text);
            StringBuffer buffer = new StringBuffer(text);
            int w = g2D.getFontMetrics(font).stringWidth(text);
            double side = isVertical(w) ? getHeight() : getWidth();
            while (w > side && buffer.length() > 2 && buffer.lastIndexOf(DELIM) > -1)
            {
                result.remove(0);
                String next = buffer.substring(buffer.lastIndexOf(DELIM), buffer.length());
                buffer.delete(buffer.lastIndexOf(DELIM), buffer.length());
                result.add(0, buffer.toString());
                result.add(1, next);
                w = g2D.getFontMetrics(font).stringWidth(buffer.toString());
                side = isVertical(w) ? getHeight() : getWidth();
            }
            return result.toArray(new String[]{});
        }
    }
}
