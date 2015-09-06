package by.dak.utils.convert;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Denis Koyro
 * @version 0.1 05.02.2009
 * @introduced [Furniture constructor | Iteration 1]
 * @since 1.0.0
 */
public class ListConverter<S, D> implements Converter<List<S>, List<D>>
{
    private final Converter<S, D> itemConverter;
    private final Validator<S> validator;

    private class NotNullValidator implements Validator<S>
    {
        @Override
        public boolean validate(S source)
        {
            return source != null;
        }
    }

    public ListConverter(Converter<S, D> itemConverter)
    {
        this.itemConverter = itemConverter;
        this.validator = new NotNullValidator();
    }

    public ListConverter(Converter<S, D> itemConverter, Validator<S> validator)
    {
        this.itemConverter = itemConverter;
        this.validator = validator;
    }

    public List<D> convert(List<S> source)
    {
        assert source != null;
        List<D> destination = new ArrayList<D>(source.size());
        for (S sourceItem : source)
        {
            if (validator.validate(sourceItem))
            {
                destination.add(itemConverter.convert(sourceItem));
            }
        }
        return destination;
    }
}