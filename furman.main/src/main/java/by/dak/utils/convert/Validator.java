package by.dak.utils.convert;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: 13.02.2009
 * Time: 12:55:05
 * To change this template use File | Settings | File Templates.
 */
public interface Validator<S>
{
    boolean validate(S source);
}
