package by.dak.cutting.doors.profile.draw;

import by.dak.cutting.doors.CellDragLimiter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

/**
 * User: akoyro
 * Date: 29.09.2009
 * Time: 13:13:10
 */
@Aspect
public class RestrictedAreaDragAspect
{
    @Around("execution(* org.jhotdraw.draw.DragHandle.trackStep(java.awt.Point,java.awt.Point, int))")
    public Object restrictDrag(ProceedingJoinPoint pjp) throws Throwable
    {
        DragHandleJoinPoint jp = new DragHandleJoinPoint(pjp);

        if (jp.getParent() != null)
        {
            Point2D.Double newPoint = jp.getView().getConstrainer().constrainPoint(jp.getView().viewToDrawing(jp.getLead()));
            AffineTransform tx = new AffineTransform();
            tx.translate(newPoint.x - jp.getOldPoint().x, newPoint.y - jp.getOldPoint().y);
            if (!new CellDragLimiter(tx).isDragLimited(jp.getOwner()))
            {
                return pjp.proceed();
            }
        }
        else
        {
            return pjp.proceed();
        }
        return null;
    }
}
