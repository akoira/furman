/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.gui;

import by.dak.cutting.cut.guillotine.Segment;
import by.dak.cutting.cut.guillotine.SegmentPainter;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * @author Peca
 */
public class SegmentVisualizer extends JDialog
{
    private Segment segment;

    public SegmentVisualizer()
    {
        super(null, JDialog.ModalityType.TOOLKIT_MODAL);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        //g2.getTransform().s setToTranslation(20, 20);
        g2.setTransform(AffineTransform.getTranslateInstance(10, 40));
        if (segment != null) SegmentPainter.draw(g, segment);
    }


    public static void ShowDialog(Segment segment)
    {
        SegmentVisualizer diag = new SegmentVisualizer();
        // diag.setModalityType(JDialog.ModalityType.TOOLKIT_MODAL);
        diag.setSegment(segment);
        diag.setSize(segment.getLength() + 50, segment.getWidth() + 50);
        diag.setModal(true);
        diag.setVisible(true);
    }

    public Segment getSegment()
    {
        return segment;
    }

    public void setSegment(Segment segment)
    {
        this.segment = segment;
    }
}
