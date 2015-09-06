package by.dak.cutting.cut.swing;

/**
 * @author admin
 * @version 0.1 20.01.2009
 * @introduced [Preview Plugin Support]
 * @since 2.0.0
 */
public enum SegmentLevel
{
    FIRST,
    SECOND,
    THIRD,
    FOURTH,
    FIFTH;


    public static SegmentLevel valueOf(int i)
    {
        switch (i)
        {
            case 0:
                return FIRST;
            case 1:
                return SECOND;
            case 2:
                return THIRD;
            case 3:
                return FOURTH;
            case 4:
                return FIFTH;
            default:
                throw new IllegalArgumentException("Illegal index " + i);
        }
    }
}
