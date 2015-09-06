package by.dak.utils.convert;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: 27.12.2008
 * Time: 18:44:51
 */
public interface EntityToStringConverter<E> extends Converter<E, String>
{
    @Override
    String convert(E entity);

}
