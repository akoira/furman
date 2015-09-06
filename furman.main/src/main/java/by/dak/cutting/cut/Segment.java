package by.dak.cutting.cut;

import by.dak.cutting.cut.guillotine.SegmentType;

/**
 * User: akoyro
 * Date: 15.03.2009
 * Time: 0:21:06
 */
public interface Segment
{
    Integer getWidth();

    Integer getLength();

    Boolean isFixedWidth();

    Boolean isFixedLength();

    Integer getUsedLength();

    Integer getPadding();

    Long getMaterialLength();

    Long getMaterialWidth();

    SegmentType getSegmentType();
}
