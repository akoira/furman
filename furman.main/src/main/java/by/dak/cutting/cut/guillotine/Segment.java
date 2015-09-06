package by.dak.cutting.cut.guillotine;

import by.dak.cutting.cut.base.Element;
import by.dak.cutting.cut.gui.cuttingApp.DimensionItem;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

/**
 * @author Peca
 */
@XStreamAlias("Segment")
public class Segment implements by.dak.cutting.cut.Segment
{
    private int width;
    private int length;
    private Segment parent;

    private Vector<Segment> items;
    private boolean fixedWidth;
    private boolean fixedLength;

    private Element element;
    private boolean rotateElement = false;

    private int usedLength;
    private int padding;

    private SegmentType segmentType;


    /**
     * Dimention из которого создается сигмент
     */
    private DimensionItem dimensionItem;


    /**
     * Размер листа из которого будут краится детали, устанавлевается только для level 0 и используется только в отчете.
     */
    private long materialLength;
    private long materialWidth;


    public Segment()
    {
        items = new Vector<Segment>();
        padding = 0;
        clear();
    }

    public Segment(int length, int width)
    {
        this();
        this.length = length;
        this.width = width;
    }

    public Segment(DimensionItem dimension)
    {
        this();
        this.dimensionItem = dimension;
        this.length = this.dimensionItem.getWidth();
        this.width = this.dimensionItem.getHeight();
    }

    public Integer getWidth()
    {
        return width;
    }

    public void setWidth(int width)
    {
        assert !this.isFixedWidth() : "Nelze nastavit sirku, protoze je fixni";
        if (parent != null)
        {
            //int delta = parent.getUsedLength() + width - this.getWidth() - parent.getLength();
            int delta = width - this.getWidth() - parent.getFreeLength();
            if (delta > 0)
                parent.setLength(parent.getLength() + delta);
            parent.notifyChildWidthChanged(this, this.getWidth(), width);
        }
        this.width = width;
    }

    public Integer getLength()
    {
        if (isFixedLength() || getParent() == null)
            return length;
        else
            return getParent().getWidth();
    }

    public void setLength(int length)
    {
        assert !this.isFixedLength() : "Nelze nastavit delku, protoze je fixni";
        if (getParent() != null)
            getParent().setWidth(length);
        else
            this.length = length;
    }

    public Boolean isFixedWidth()
    {
        return fixedWidth;
    }

    public Boolean isFixedLength()
    {
        return fixedLength;
    }

    public void setFixedWidth(boolean fixedWidth)
    {
        this.fixedWidth = fixedWidth;
    }

    public void setFixedLength(boolean fixedLength)
    {
        this.fixedLength = fixedLength;
    }

    public void addSegment(Segment segment)
    {
        //assert(this.getWidth() >= segment.getLength()) : "Je prilis mala sirka pro pridani dalsiho segmentu";
        //assert(this.getFreeLength() >= segment.getWidth()) : "Neni volna delka pro pridani dalsiho segmentu";
        if (this.getWidth() < segment.getLength())
            this.setWidth(segment.getLength());

        int requiredLength = segment.getWidth();
        if (this.items.size() > 0)
            requiredLength += this.padding;
        if (this.getFreeLength() < requiredLength)
            this.setLength(this.getUsedLength() + requiredLength);

        if (items.size() > 0)
            this.usedLength += this.padding;
        items.add(segment);
        this.usedLength += segment.getWidth();
        segment.parent = this;
    }

    public Segment removeSegment(Segment segment)
    {
        segment.parent = null;
        this.usedLength -= segment.getWidth();
        items.remove(segment);
        if (items.size() > 0)
            this.usedLength -= this.padding;
        this.getLength();
        return segment;
    }

    public boolean canAddSegment(Segment segment)
    {
        int requiredLength = segment.getWidth();
        /**
         * проверяется больше одного потомочто если один пропил можно не учитывать
         * так как больше ложить не будем и пропил может быть тоньше 
         */
        if (this.items.size() > 1)
            requiredLength += this.padding;
        if (this.getFreeLength() < requiredLength)
        {
            if (!this.canSetLength(this.getUsedLength() + requiredLength))
                return false;
        }

        if (segment.isFixedLength())
        {
            if (this.getWidth() == segment.getLength())
                return true;
            else if (this.items.size() > 0)
                return false;
        }

        if (this.getWidth() >= segment.getLength())
            return true;
        else
        {
            return canSetWidth(segment.getLength());
        }
    }

    public int getLevel()
    {
        if (getParent() != null)
            return getParent().getLevel() + 1;
        else
            return 0;
    }


    public Segment getParent()
    {
        return parent;
    }

    public int getFreeLength()
    {
        if (this.element != null)
            return 0;
        return this.getLength() - this.getUsedLength();
    }

    public Integer getUsedLength()
    {
        /*int l = 0;
        for(Segment seg : items){
            l += seg.getWidth();
        }
        return l;*/
        return this.usedLength;
    }

    public boolean canSetLength(int length)
    {
        if (this.fixedLength)
            return false;
        if (this.parent != null)
            return parent.canSetWidth(length);
        else
            return true;
    }

    public boolean canSetWidth(int width)
    {
        if (this.fixedWidth)
            return false;
        if (this.parent == null)
            return true;
        //volna delka u rodice po zvetseni, delta > 0 znamena ze to presahuje
        int delta = parent.getUsedLength() + width - this.getWidth() - parent.getLength();
        if (delta > 0)
            return parent.canSetLength(parent.getLength() + delta);
        else
            return true;
    }

    public void clear()
    {
        clear(true);
    }


    public void clear(boolean withChildren)
    {
        if (withChildren)
        {
            for (Segment seg : items)
            {
                seg.clear();
            }
        }
        items.clear();
        if (!isFixedLength())
            this.length = 0;
        if (!isFixedWidth())
            this.width = 0;
        usedLength = 0;
    }

    public Element getElement()
    {
        return element;
    }

    public boolean isRotateElement()
    {
        return rotateElement;
    }

    public void setElement(Element element, boolean rotate)
    {
        this.element = element;
        this.rotateElement = rotate;
        if (element != null)
        {
            this.fixedLength = true;
            if (!rotate)
            {
                this.length = element.getWidth();
                this.width = element.getHeight();
                this.usedLength = this.length;
            }
            else
            {
                this.length = element.getHeight();
                this.width = element.getWidth();
                this.usedLength = this.length;
            }
        }
    }

    public SegmentType getSegmentType()
    {
        return segmentType;
    }

    public void setSegmentType(SegmentType segmentType)
    {
        this.segmentType = segmentType;
    }

    /*public Segment clone(){
        Segment seg = new Segment();
        seg.width = this.width;
    seg.length = this.length;
    seg.parent = this.parent;
    seg.fixedWidth = this.fixedWidth;;
    seg.fixedLength = this.fixedLength;
        seg.element = this.element;
        return seg;
    }*/


    public int getWaste()
    {
        int waste = getFreeLength() * getWidth();
        for (Segment seg : items)
        {
            waste += seg.getWaste();
        }
        return waste;
    }

    public void sortByWaste()
    {
        SegmentType segmentType = null;
        for (Segment seg : items)
        {
            seg.sortByWaste();
            segmentType = seg.getSegmentType();
        }
        Collections.sort(items, SegmentWasteComparatorFactory.FACTORY.getComparatorBy(segmentType));
    }

    public int getUnWastedCount()
    {
        int res = 0;
        for (Segment seg : items)
        {
            if (seg.getWaste() == 0)
                res++;
        }
        return res;
    }

    private void getElements(ArrayList<Element> list)
    {
        //has been changed because exception on modification
        ArrayList<Segment> lItems = new ArrayList<Segment>();
        lItems.addAll(items);
        for (Segment seg : lItems)
        {
            seg.getElements(list);
        }
        if (this.element != null)
            list.add(this.element);
    }

    public Element[] getElements()
    {
        ArrayList<Element> list = new ArrayList<Element>();
        getElements(list);
        return list.toArray(new Element[list.size()]);
    }

    public int getItemsCount()
    {
        return items.size();
    }

    public int getMaxLevel()
    {
        int max = this.getLevel();
        for (Segment seg : items)
        {
            max = Math.max(max, seg.getMaxLevel());
        }
        return max;
    }

    private void notifyChildWidthChanged(Segment child, int oldWidth, int newWidth)
    {
        this.usedLength += (newWidth - oldWidth);
    }

    public Integer getPadding()
    {
        return padding;
    }

    public void setPadding(int padding)
    {
        this.padding = padding;
    }

    public long getFreeArea()
    {
        return getFreeLength() * getWidth();
    }

    public long getUsedArea()
    {
        return getUsedLength() * getWidth();
    }

    public long getTotalArea()
    {
        return getLength() * getWidth();
    }

    @Override
    public Segment clone()
    {
        Segment clonedSegment = new Segment();
        clonedSegment.width = this.width;
        clonedSegment.length = this.length;
        clonedSegment.parent = this.parent;
        clonedSegment.fixedWidth = this.fixedWidth;
        clonedSegment.fixedLength = this.fixedLength;
        clonedSegment.element = this.element;
        clonedSegment.usedLength = this.usedLength;
        clonedSegment.padding = this.padding;
        clonedSegment.materialLength = this.materialLength;
        clonedSegment.materialWidth = this.materialWidth;
        clonedSegment.dimensionItem = this.dimensionItem;
        clonedSegment.segmentType = this.segmentType;
        clonedSegment.rotateElement = this.rotateElement;


        for (Segment segment : this.items)
        {
            Segment childSegment = segment.clone();
            childSegment.parent = clonedSegment;
            clonedSegment.items.add(childSegment);
        }

        return clonedSegment;
    }


    /**
     * Метод используется только для востановления данных из базы
     *
     * @param element
     */
    public void setElement(Element element)
    {
        this.element = element;
    }

    /**
     * Метод используется только для востановления данных из базы
     *
     * @param parent
     */
    public void setParent(Segment parent)
    {
        this.parent = parent;
    }

    public List<Segment> getItems()
    {
        return Collections.unmodifiableList(items);
    }

//    public List<Segment> getSortedItems()
//    {
//        ArrayList<Segment> sorted = new ArrayList<Segment>(items);
////        if (true)
////            return sorted;
////        Collections.sort(sorted, new Comparator<Segment>()
////        {
////            @Override
////            public int compare(Segment o1, Segment o2)
////            {
////                int result = 0;
////                if ((o1.getLevel() % 2) == 0)
////                {
////                    result = o1.getUsedLength().compareTo(o2.getUsedLength()) * -1;
////                }
////                else
////                {
////                    result = Integer.valueOf(o1.getUsedLength()).compareTo(o2.getUsedLength()) * -1;
////                }
////                if (result == 0)
////                {
////                    result = o1.getFreeArea() < o2.getFreeArea() ? -1 : 1;
////                }
////                return result;
////            }
////        });
//        return sorted;
//    }

    /**
     * Метод используется только для востановления данных из базы
     *
     * @param items
     */
    public void setItems(List<Segment> items)
    {
        this.items.clear();
        this.items.addAll(items);
    }

    /**
     * Метод возвращает оплачиваемую длинну пропила (услуга Распил м.п.) в милиметрах.
     */
    public long getSawLength()
    {
        if (getElement() != null) //последний Segment уже пропилен
        {
            return 0;
        }
        long result = 0;
        if (getParent() != null && getWidth().equals(getParent().getLength()) && getLength().equals(getParent().getWidth())) //повторяющийся сигмент
        {
            result = 0;
        }
        else
        {
            result = getParent() == null ? 0 : getLength();//если parent не установлен то длинна этого сигмента уже пощитана.
        }
        for (Segment segment : items)
        {
            result += segment.getSawLength();
        }
        return result;
    }


    @Override
    public String toString()
    {
        return new ToStringBuilder(this)
                .append("segmentType", segmentType)
                .append("level", getLevel())
                .append("length", getLength())
                .append("width", getWidth())
                .append("usedLength", getUsedLength())
                .append("freeLength", getFreeLength())
                .append("freeArea", getFreeLength() + "x" + getWidth())
                .append("usedArea", getUsedLength() + "x" + getWidth())
                .toString();
    }


    public Long getMaterialLength()
    {
        return materialLength;
    }

    public void setMaterialLength(long materialLength)
    {
        this.materialLength = materialLength;
    }

    public Long getMaterialWidth()
    {
        return materialWidth;
    }

    public void setMaterialWidth(long materialWidth)
    {
        this.materialWidth = materialWidth;
    }

    public static Segment valueOf(by.dak.cutting.cut.Segment segment)
    {
        Segment newSegment = new Segment();
        newSegment.width = segment.getWidth();
        newSegment.length = segment.getLength();
        newSegment.fixedWidth = segment.isFixedWidth();
        newSegment.fixedLength = segment.isFixedLength();
        newSegment.usedLength = segment.getUsedLength();
        newSegment.padding = segment.getPadding();
        return newSegment;
    }

    public DimensionItem getDimensionItem()
    {
        return dimensionItem;
    }

    public void setDimensionItem(DimensionItem dimensionItem)
    {
        this.dimensionItem = dimensionItem;
    }


    public boolean compareTo(Segment segment)
    {
        EqualsBuilder equalsBuilder = new EqualsBuilder();
        equalsBuilder.append(getFreeLength(), segment.getFreeLength());
        equalsBuilder.append(getItemsCount(), segment.getItemsCount());
        equalsBuilder.append(getLength(), segment.getLength());
        equalsBuilder.append(getWidth(), segment.getWidth());
        equalsBuilder.append(getUsedLength(), segment.getUsedLength());
        equalsBuilder.append(isRotateElement(), segment.isRotateElement());
        equalsBuilder.append(getElements().length, segment.getElements().length);
        boolean result = equalsBuilder.isEquals();
        if (result)
        {
            List<Segment> sourceSegments = getItems();
            List<Segment> targetSegments = segment.getItems();
            for (int i = 0; i < sourceSegments.size(); i++)
            {
                result = sourceSegments.get(i).compareTo(targetSegments.get(i));
                if (!result)
                {
                    return result;
                }
            }
        }
        return result;
    }
}

