package by.dak.common.swing;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: 18.01.2009
 * Time: 14:22:57
 * To change this template use File | Settings | File Templates.
 */
public interface ExceptionHandler
{
    void handle(Throwable e);

    void handle(Object source, Throwable e);
}
