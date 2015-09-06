package by.dak.cutting.cut.swing.board;

import by.dak.cutting.cut.base.Element;
import by.dak.cutting.cut.guillotine.Segment;
import by.dak.cutting.cut.guillotine.helper.BoardDimensionsHelper;
import by.dak.cutting.cut.swing.Constants;
import by.dak.cutting.cut.swing.ElementPainter;
import by.dak.cutting.cut.swing.StringPainter;
import by.dak.cutting.swing.order.cellcomponents.editors.cuttoff.CutoffPainter;
import by.dak.cutting.swing.order.data.Cutoff;
import by.dak.cutting.swing.xml.XstreamHelper;
import by.dak.persistence.entities.AOrderBoardDetail;

import java.awt.*;

/**
 * User: akoyro
 * Date: 13.03.2009
 * Time: 0:27:22
 */

public class BoardElementPainter extends ElementPainter
{
    private final static BoardDimensionsHelper boardDimensionsHelper = new BoardDimensionsHelper();

    @Override
    public void paintElement(Graphics g, Segment segment, int x2, int y2, int w, int h)
    {
        if (segment.getElement() != null)
        {
            Element el = segment.getElement();
            g.setColor(Constants.COLOR_ELEMENT_PRINT);
            fillRect(g, x2, y2, w, h);
            g.setColor(Color.BLACK);
            Rectangle rct = new Rectangle(x2, y2, segment.getLength(), segment.getWidth());
            if ((segment.getLevel() % 2) == 1)
            {
                rotateRect(rct);
            }

            AOrderBoardDetail orderFurniture = boardDimensionsHelper.getElementOrderDetail(el);

            if (orderFurniture != null)
            {
                paintCuttof(g, orderFurniture, rct);
            }

            StringPainter elementDescriptionPainter = new ElementDescriptionPainter(rct, segment.getElement());
            elementDescriptionPainter.setScale(getScale());
            elementDescriptionPainter.setRotate(isRotate());
            elementDescriptionPainter.drawString(g);
        }
    }

    private void paintCuttof(Graphics g, AOrderBoardDetail entity, Rectangle rectangle)
    {
        if (entity.getCutoff() != null)
        {
            Graphics2D g2D = createGraphics2D(g, rectangle);
            Cutoff cutoff = (Cutoff) XstreamHelper.getInstance().fromXML(entity.getCutoff());
            CutoffPainter.CutoffLine cutoffLine = new CutoffPainter.CutoffLine();
            CutoffPainter.adjustLineBy(cutoffLine,
                    new Dimension(isRotate() ? (int) rectangle.getHeight() : (int) rectangle.getWidth(),
                            isRotate() ? (int) rectangle.getWidth() : (int) rectangle.getHeight()), cutoff, 1);
            cutoffLine.paint(g2D);
        }
    }
}
