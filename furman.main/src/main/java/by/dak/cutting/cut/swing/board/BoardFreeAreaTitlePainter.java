package by.dak.cutting.cut.swing.board;

import by.dak.cutting.CuttingApp;
import by.dak.cutting.cut.guillotine.Segment;
import by.dak.cutting.cut.swing.FreeAreaTitlePainter;
import by.dak.cutting.cut.swing.StringPainter;
import by.dak.report.jasper.ReportUtils;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

import java.awt.*;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: dkoyro
 * Date: 27.04.11
 * Time: 16:07
 * To change this template use File | Settings | File Templates.
 */
public class BoardFreeAreaTitlePainter extends FreeAreaTitlePainter
{
    private final Properties properties = new Properties();

    public BoardFreeAreaTitlePainter() {
        try {
            properties.load(BoardFreeAreaTitlePainter.class.getResourceAsStream("resources/BoardFreeAreaTitlePainter.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void titleFreeAria(Graphics g, Segment segment, int x2, int y2)
    {
        int free = segment.getFreeLength() - segment.getPadding();
        if (free > 0)
        {
            Rectangle rectangle = new Rectangle(x2, y2, free, segment.getWidth());
            if ((segment.getLevel() % 2) == 1)
            {
                rotateRect(rectangle);
            }
            StringPainter stringPainter = new StringPainter(rectangle)
            {
                @Override
                protected void drawString(Graphics g, String text)
                {
                    if (getTextHelper().isSizeAccepted())
                    {
                        super.drawString(g, text);
                    }
                }
            };
            stringPainter.setRotate(isRotate());

            if (ReportUtils.isUsefulDimension(new by.dak.cutting.cut.base.Dimension(rectangle.height, rectangle.width)))
            {
                stringPainter.setText(String.format(properties.getProperty("label.to.store"),
                        (int) rectangle.getHeight(), (int) rectangle.getWidth()));
            }
            else
            {
                stringPainter.setText((int) rectangle.getHeight() + "x" + (int) rectangle.getWidth());
            }
            stringPainter.setScale(getScale());
            stringPainter.drawString(g);
        }
    }
}
