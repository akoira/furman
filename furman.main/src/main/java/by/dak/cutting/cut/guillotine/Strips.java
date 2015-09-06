/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.cut.guillotine;

import by.dak.cutting.cut.base.Element;
import by.dak.cutting.cut.base.Utils;
import by.dak.cutting.cut.gui.cuttingApp.DimensionItem;
import by.dak.persistence.cutting.entities.AStripsEntity;
import by.dak.persistence.entities.Cutter;
import by.dak.report.jasper.ReportUtils;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Peca
 */
@XStreamAlias("Strips")
public class Strips
{
    private List<Segment> items = Collections.EMPTY_LIST;

    private int sawWidth = 0;
    private boolean allowRotation = false;

    @XStreamOmitField
    private AStripsEntity stripsEntity;

    public Strips()
    {
        items = new ArrayList<Segment>();
    }

    @Deprecated
    public Strips(DimensionItem[] sheets)
    {
        this();
        for (DimensionItem dimension : sheets)
        {
            int count = dimension.getCount();
            for (int i = 0; i < count; i++)
            {
                Segment segment = createGraySegmentBy(dimension);
                items.add(segment);
            }
        }
    }

    public Strips(List<Segment> segments)
    {
        this();
        items.addAll(segments);
    }

    public static Segment createGraySegmentBy(DimensionItem dimension)
    {
        Segment segment = new Segment(dimension);
        segment.setSegmentType(SegmentType.gray);
        segment.setFixedLength(true);
        segment.setFixedWidth(true);
        return segment;
    }

    public void addSegments(List<Segment> segments)
    {
        this.items.addAll(segments);
    }


    public void addSegment(Segment segment)
    {
        this.items.add(segment);
    }

    public void clear()
    {
        this.items.clear();
    }

    public void clearSegments()
    {
        for (Segment seg : items)
        {
            seg.clear();
        }
    }

    public List<Segment> getSegments()
    {
        return Collections.unmodifiableList(this.items);
    }

    public Segment getSegment(int index)
    {
        Segment segment = items.get(index);
        return segment;
    }

    public int getSegmentsCount()
    {
        return items.size();
    }

    public void sort()
    {
        for (Segment seg : items)
        {
            seg.sortByWaste();
        }
        Collections.sort(items, SegmentWasteComparatorFactory.FACTORY.getComparatorBy(SegmentType.gray));
    }

    public Element[] getCuttedElements()
    {
        ArrayList<Element> els = new ArrayList<Element>();
        for (Segment seg : items)
        {
            for (Element el : seg.getElements())
            {
                els.add(el);
            }
        }
        return els.toArray(new Element[els.size()]);
    }

    public int getSawWidth()
    {
        return sawWidth;
    }

    public void setSawWidth(int sawWidth)
    {
        this.sawWidth = sawWidth;
    }

    public boolean isAllowRotation()
    {
        return allowRotation;
    }

    public void setAllowRotation(boolean allowRotation)
    {
        this.allowRotation = allowRotation;
    }

    @Override
    public Strips clone()
    {
        Strips clonedStrips = new Strips();

        clonedStrips.sawWidth = this.sawWidth;
        clonedStrips.allowRotation = this.allowRotation;
        clonedStrips.stripsEntity = this.stripsEntity;

        for (Segment segment : this.items)
        {
            if (segment.getElements() != null && segment.getElements().length > 0)
            {
                Segment clonedSegment = segment.clone();
                clonedStrips.addSegment(clonedSegment);
            }
        }
        return clonedStrips;
    }


    public float getWasteRatio()
    {
        long usedArea = getUsedArea();
        return 1 - (Utils.getElementsTotalArea(this.getCuttedElements()) / (float) usedArea);
    }

    public long getUsedArea()
    {
        long usedArea = 0;
        for (Segment segment : this.items)
        {
            if (segment.getUsedArea() > 0)
            {
                usedArea += segment.getUsedArea();
                usedArea += segment.getFreeArea();
            }
        }
        return usedArea;
    }


    public List<Segment> getItems()
    {
        return items;
    }

    public void setItems(ArrayList<Segment> items)
    {
        this.items = items;
    }

    /**
     * Метод возвращает учитываемую длинну пропила (услуга Распил м.п.) в милиметрах.
     */
    public long getSawLength(Cutter cutter)
    {
        long result = 0;
        for (Segment segment : items)
        {
            result += ReportUtils.calcSawSheetSegment(segment, cutter);
        }
        return result;
    }

    public void replaceSegment(int index, Segment segment)
    {
        items.add(index, segment);
        items.remove(index + 1);
    }


    public Segment removeSegment(int index)
    {
        return items.remove(index);
    }

    public boolean removeSegment(Segment segment)
    {
        return items.remove(segment);
    }

    public AStripsEntity getStripsEntity()
    {
        return stripsEntity;
    }

    public void setStripsEntity(AStripsEntity stripsEntity)
    {
        this.stripsEntity = stripsEntity;
    }


    @Override
    public int hashCode()
    {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
