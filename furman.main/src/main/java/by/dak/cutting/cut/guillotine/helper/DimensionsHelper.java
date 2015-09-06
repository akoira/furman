package by.dak.cutting.cut.guillotine.helper;

import by.dak.cutting.cut.base.Dimension;
import by.dak.cutting.cut.base.Element;
import by.dak.cutting.cut.gui.cuttingApp.DimensionItem;
import by.dak.cutting.cut.guillotine.Segment;
import by.dak.cutting.cut.guillotine.Strips;
import by.dak.persistence.entities.AOrderDetail;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: dkoyro
 * Date: 26.02.11
 * Time: 18:27
 * To change this template use File | Settings | File Templates.
 */
public interface DimensionsHelper<V extends AOrderDetail, D extends Dimension>
{
    /**
     * возвращает все остатки
     *
     * @return
     */
    public List<D> getRestDimensions(Strips strips);

    public boolean isWhole(Segment segment);

    /**
     * возвращает AOrderDatail элемента
     *
     * @param element
     * @return
     */
    public V getElementOrderDetail(Element element);

    public void setElementOrderDetail(Element element, V detail);

    /**
     * устанавливает сегменту параметры dimensionItem
     *
     * @param segment
     * @param dimensionItem
     */
    public void fillWithDimensionItem(Segment segment, DimensionItem dimensionItem);

    public void fillWithDimensionItem(Element element, DimensionItem dimensionItem);

    public boolean isUseful(Dimension dimension);
}
