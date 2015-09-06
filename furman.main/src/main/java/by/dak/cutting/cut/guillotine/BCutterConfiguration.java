package by.dak.cutting.cut.guillotine;

/**
 * User: akoyro
 * Date: 15.05.2009
 * Time: 10:21:08
 */
public interface BCutterConfiguration
{
    //todo вроде попомгло bug 7214 фиксирует размер сигмента
    public static final boolean FIX_SIZE_GREEN_SEGMENT_BY_CURRENT_ELEMENT_SIZE = true;
    //todo 7214 добавлен это break чтобы больше чем одна деталь не добавлялось в segment.
    public static final boolean ADD_TO_GREEN_SEGMENT_ONLY_ONE_ELEMENT = false;

    /**
     * Позволить добовлять только одинаковые елементы
     */
    public static final boolean ALLOW_ADD_TO_GREEN_ONLY_EQUALS_ELEMENTS = true;
}
