package by.dak.cutting.doors.profile.draw;

import by.dak.draw.Graphics2DUtils;
import org.jhotdraw.draw.LineFigure;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 * User: akoyro
 * Date: 17.10.2009
 * Time: 23:00:14
 */
public class ArcFigure extends LineFigure {

//    private Arc2D.Double arc = new Arc2D.Double();
//
//    @Override
//    protected void drawFill(Graphics2D g)
//    {
//        //To change body of implemented methods use File | Settings | File Templates.
//    }

	//

	@Override
	protected void drawStroke(Graphics2D g) {
		super.drawStroke(g);
		Line2D.Double line = Graphics2DUtils.orderPoints(new Line2D.Double(getStartPoint(), getEndPoint()));

		double radius = line.getP2().distance(line.getP1());

		double ax = Graphics2DUtils.getXAngle(Graphics2DUtils.orderPoints(line));

//        System.out.println("ax = " + ax);
//        System.out.println("radius = " + radius);
//        System.out.println("q = " + Graphics2DUtils.getQuarterOfLine(new Line2D.Double(getStartPoint(), getEndPoint())));
		//при таком рассчете знак четвертей: -+-+
		Arc2D.Double arc = new Arc2D.Double(line.getP1().getX() - radius, line.getP1().getY() - radius, radius * 2, radius * 2, 0, ax, Arc2D.OPEN);
		Line2D.Double xline = new Line2D.Double(line.getP1().getX(), line.getP1().getY(), line.getP2().getX(), line.getP1().getY());
		Line2D.Double yline = new Line2D.Double(line.getP2().getX(), line.getP2().getY(), line.getP2().getX(), line.getP1().getY());
//        g.draw(xline);
//        g.draw(yline);
//        g.draw(arc);

//
//        double axn = ax + 60;
//        double dx = radius * Math.cos(axn);
//        double dy = radius * Math.sin(axn);
//        Point2D.Double np = new Point2D.Double(line.getP1().getX() - dx, line.getP1().getY() - dy);


		//вычисляем центральную точку для круга
		Point2D.Double center = Graphics2DUtils.rotate((Point2D.Double) line.getP2(), (Point2D.Double) line.getP1(), Math.PI / 3);
		g.draw(new Line2D.Double(line.getP1(), center));
		g.draw(new Line2D.Double(line.getP2(), center));

		//вычисляем точку для цендрального радиуса на дуге
		Point2D.Double p3 = Graphics2DUtils.rotate((Point2D.Double) line.getP1(), center, Math.PI / 6);
		g.draw(new Line2D.Double(center, p3));


		double ax1 = Graphics2DUtils.getXAngle(new Line2D.Double(center, line.getP1()));
		double ax2 = Graphics2DUtils.getXAngle(new Line2D.Double(center, line.getP2()));

		arc = new Arc2D.Double(center.getX() - radius, center.getY() - radius, radius * 2, radius * 2, ax1, ax2, Arc2D.OPEN);
		g.draw(arc);
//        g.draw(new Line2D.Double(line.getP1(), np));
//        g.draw(new Line2D.Double(line.getP2(), np));


//        g.draw(new Line2D.Double(arc.getBounds2D().getX(),
//                arc.getBounds2D().getY(),
//                arc.getBounds2D().getMaxX(),                                                                n
//                arc.getBounds2D().getMaxY()));
//        g.draw(arc);
//        g.draw(new Line2D.Double(getStartPoint(),getEndPoint()));

	}
//
//    @Override
//    public Rectangle2D.Double getBounds()
//    {
//        return (Rectangle2D.Double) arc.getBounds2D().clone();
//    }
//
//    @Override
//    public boolean contains(Point2D.Double p)
//    {
//        return arc.getBounds2D().contains(p);
//    }
//
//    @Override
//    public Object getTransformRestoreData()
//    {
//        return arc.clone();
//    }
//
//    @Override
//    public void restoreTransformTo(Object restoreData)
//    {
//    }
//
//    @Override
//    public void transform(AffineTransform tx)
//    {
//
//    }
//
//    @Override
//    public Point2D.Double getStartPoint()
//    {
//        return (Point2D.Double) arc.getStartPoint();
//    }
//
//    @Override
//    public Point2D.Double getEndPoint()
//    {
//        return (Point2D.Double) arc.getEndPoint();
//    }
//
//    @Override
//    public void setBounds(Point2D.Double anchor, Point2D.Double lead)
//    {
//        Rectangle2D.Double r = new Rectangle2D.Double();
//        r.setFrameFromDiagonal(anchor, lead);
//
//        double dist = anchor.distance(lead);
//
//        Point2D.Double oldAnchor = getStartPoint();
//        Point2D.Double oldLead = getEndPoint();
//        if (!oldAnchor.equals(anchor) || !oldLead.equals(lead))
//        {
//            willChange();
//            Line2D.Double tmp = new Line2D.Double(r.getCenterX(), r.getCenterY(), r.getMaxX() - r.height, r.getMaxY() - r.width);
//            Point2D.Double p = SplitUtil.getIntersectionPoint(tmp,new Line2D.Double(r.getX(),r.getY(),r.getMaxX(),r.getY()));
//            if (p != null)
//            arc.setArcByTangent(getStartPoint(), p, lead, lead.distance(anchor));
//            changed();
//            fireUndoableEditHappened(new SetBoundsEdit(this, oldAnchor, oldLead, anchor, lead));
//        }
//    }


}
