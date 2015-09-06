package by.dak.cutting.agt;

/**
 * User: akoyro
 * Date: 09.09.2010
 * Time: 16:02:33
 */
public enum WidthGrooveSize
{
    size10(10),
    size8(8),
    size4(4),
    undefined(-1);

    private double size;

    WidthGrooveSize(double size)
    {
        this.size = size;
    }

    public double getSize()
    {
        return size;
    }

    public static WidthGrooveSize valueOf(double size)
    {
        for (int i = 0; i < values().length; i++)
        {
            WidthGrooveSize widthGrooveSize = values()[i];
            if (widthGrooveSize.getSize() == size)
            {
                return widthGrooveSize;
            }
        }
        return undefined;
    }
}
