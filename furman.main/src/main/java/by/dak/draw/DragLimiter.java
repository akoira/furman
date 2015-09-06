package by.dak.draw;

import org.jhotdraw.draw.Figure;

/**
 * User: akoyro
 * Date: 16.09.2009
 * Time: 14:39:09
 */
public interface DragLimiter
{
    /**
     * Если возвращает true если drag данной figure ограничен.
     *
     * @param figure
     * @return
     */
    public boolean isDragLimited(Figure figure);
}
