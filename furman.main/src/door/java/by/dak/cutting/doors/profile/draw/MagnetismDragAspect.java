package by.dak.cutting.doors.profile.draw;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * User: akoyro
 * Date: 25.09.2009
 * Time: 11:56:33
 */
@Aspect
public class MagnetismDragAspect
{
    @Around("execution(* org.jhotdraw.draw.DragHandle.trackStep(java.awt.Point,java.awt.Point, int))")
    public Object magnetism(ProceedingJoinPoint pjp) throws Throwable
    {
        DragHandleJoinPoint jp = new DragHandleJoinPoint(pjp);
        Object result = jp.getJoinPoint().proceed();
        if (jp.getParent() != null)
        {
        }
        return result;
    }
}
