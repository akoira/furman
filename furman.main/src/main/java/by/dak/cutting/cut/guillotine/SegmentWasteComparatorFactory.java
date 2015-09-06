package by.dak.cutting.cut.guillotine;

import java.util.Comparator;

/**
 * User: akoyro
 * Date: 27.07.2010
 * Time: 15:43:36
 */
public class SegmentWasteComparatorFactory
{
    public static final SegmentWasteComparatorFactory FACTORY = new SegmentWasteComparatorFactory();

    private DefaultWasteComparator defaultComparator = new DefaultWasteComparator();
    private GrayComparator grayComparator = new GrayComparator();
    private RedComparator redComparator = new RedComparator();
    private BlueComparator blueComparator = new BlueComparator();


    public Comparator getComparatorBy(SegmentType segmentType)
    {
        if (segmentType == null)
        {
            return defaultComparator;
        }
        switch (segmentType)
        {
            case green:
            case element:
                return defaultComparator;
            case blue:
                return blueComparator;
            case red:
                return redComparator;
            case gray:
                return grayComparator;
            default:
                throw new IllegalArgumentException();
        }
    }

    public static class DefaultWasteComparator implements Comparator<Segment>
    {
        public int compare(Segment seg1, Segment seg2)
        {
            int res = seg1.getFreeLength() - seg2.getFreeLength();
            if (res == 0)
            {
                int waste1 = seg1.getWaste();
                int waste2 = seg2.getWaste();
                res = (waste1 - waste2);
            }
            return res;
        }
    }

    public static class GrayComparator implements Comparator<Segment>
    {
        public int compare(Segment seg1, Segment seg2)
        {
            int res = seg1.getFreeLength() - seg2.getFreeLength();
            if (res == 0)
            {
                int waste1 = seg1.getWaste();
                int waste2 = seg2.getWaste();
                res = (waste1 - waste2);
            }
            return res;
        }
    }

    public static class RedComparator implements Comparator<Segment>
    {
        public int compare(Segment seg1, Segment seg2)
        {
            /**
             * Первым проверяем сортируем по минимальному остатку
             */
            int res = seg1.getFreeLength() - seg2.getFreeLength();

            /**
             * затем по высоте
             */
            if (res == 0)
            {
                res = seg1.getWidth() - seg2.getWidth();
                res *= -1;
            }

            if (res == 0)
            {
                int waste1 = seg1.getWaste();
                int waste2 = seg2.getWaste();
                res = (waste1 - waste2);
            }
            return res;
        }
    }

    public static class BlueComparator implements Comparator<Segment>
    {
        public int compare(Segment seg1, Segment seg2)
        {
            int res = seg1.getFreeLength() - seg2.getFreeLength();
            if (res == 0)
            {
                int waste1 = seg1.getWaste();
                int waste2 = seg2.getWaste();
                res = (waste1 - waste2);
            }
            return res;
        }
    }

}



