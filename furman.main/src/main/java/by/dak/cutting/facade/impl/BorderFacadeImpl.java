package by.dak.cutting.facade.impl;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.configuration.Constants;
import by.dak.cutting.facade.BorderFacade;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.*;
import by.dak.persistence.entities.predefined.StoreElementStatus;
import by.dak.report.jasper.common.data.BorderCommonData;
import by.dak.report.jasper.common.data.CommonData;
import by.dak.report.jasper.common.data.CommonDatas;
import by.dak.report.jasper.common.data.converter.BorderMaterialsConverter;

import java.util.List;

/**
 * User: akoyro
 * Date: 19.11.2009
 * Time: 1:27:09
 */
public class BorderFacadeImpl extends AStoreElementFacadeImpl<Border> implements BorderFacade
{
    private double curveGluingAdditionalLength = 150; //150 mm

    @Override
    public void attachTo(Order order)
    {
        BorderMaterialsConverter converter = new BorderMaterialsConverter(order);
        List<OrderFurniture> entities = FacadeContext.getOrderFurnitureFacade().findOrderedWithGlueing(order, Boolean.TRUE);
        CommonDatas<CommonData> datas = converter.convert(entities);
        for (CommonData commonData : datas)
        {
            BorderDefEntity borderDef = ((BorderCommonData) commonData).getBorderDef();
            TextureEntity texture = ((BorderCommonData) commonData).getTexture();

            double length = commonData.getCount();
            while (length > 0)
            {
                List<Border> borders = findBy(borderDef, texture, StoreElementStatus.exist);
                Border border;
                if (borders.size() > 0)
                {
                    //берем первый материал со склада
                    border = borders.get(0);
                    //выделяем из набоа один
                    if (border.getAmount() > 1)
                    {
                        border.setAmount(border.getAmount() - 1);
                        save(border);
                        border = border.clone();
                        border.setAmount(1);
                        save(border);
                    }
                }
                else
                {
                    //создаем если нет материала
                    border = new Border();
                    border.setTexture(texture);
                    border.setBorderDef(borderDef);
                    border.setLength(length);
                    border.setStatus(StoreElementStatus.order);
                    border.setOrder(order);
                    border.setAmount(1);
                    save(border);
                    break;
                }
                length = attachBorder(order, length, border);
            }
        }
    }

    private double attachBorder(Order order, double length, Border border)
    {
        if (border.getLength() <= length)
        {
            border.setOrder(order);
            border.setStatus(StoreElementStatus.used);
            length -= border.getLength();
            save(border);
        }
        else
        {
            if ((border.getLength() - length) <= Constants.MIN_BORDER_LENGTH)
            {
                //если остаток меньше минимальной длинны списываем все
                border.setOrder(order);
                border.setStatus(StoreElementStatus.used);
                save(border);
            }
            else
            {
                //создаем остаток и ложим на склад
                Border nBorder = border.clone();
                nBorder.setLength(border.getLength() - length);
                nBorder.setCreatedByOrder(order);
                nBorder.setStatus(StoreElementStatus.exist);
                save(nBorder);

                //списываем со склада
                border.setLength(length);
                border.setOrder(order);
                border.setStatus(StoreElementStatus.used);
                save(border);
            }
            length = 0;
        }
        return length;
    }

    /**
     * Сортируем по возрастанию длинны и по убаванию цены
     *
     * @param borderDef
     * @param texture
     * @return
     */
    public List<Border> findBy(BorderDefEntity borderDef, TextureEntity texture, StoreElementStatus status)
    {
        SearchFilter filter = SearchFilter.instanceSingle();
        filter.setResultSize(Integer.MAX_VALUE);
        filter.eq("status", status);
        filter.eq(Border.PROPERTY_priceAware, borderDef);
        filter.eq(Border.PROPERTY_priced, texture);
        filter.addAscOrder("length");
        filter.addDescOrder("price");
        return loadAll(filter);
    }

    @Override
    public List<Border> loadAll(SearchFilter filter)
    {
        filter.addAscOrder("priceAware.name");
        filter.addAscOrder("priced.name");
        filter.addAscOrder("length");
        filter.gt("amount", 0);
        return super.loadAll(filter);
    }

    private double getCommonLength(List<Border> borders)
    {
        double common = 0;
        for (Border border : borders)
        {
            common += border.getAmount() * border.getLength();
        }
        return common;
    }

    @Override
    public Border cancelOrdered(Border ordered)
    {
        if (ordered.getAmount() < 1 && ordered.getLength() < Constants.MIN_BORDER_LENGTH)
        {
            return null;
        }
        Border result = ordered;
        List<Border> borders = findByOrdered(ordered);

        if (borders.size() > 0)
        {
            if (ordered.getAmount() > 1)
            {
                result = ordered.clone();
                result.setAmount(1);
                ordered.setAmount(ordered.getAmount() - 1);
            }
            cancelOrder(result, borders);
            if (findByOrdered(ordered).size() > 0 &&
                    //ordered может быть уже пустым
                    ordered.getLength() > 0)
            {
                result = cancelOrdered(ordered);
            }
        }
        if (result != null && (result.getLength() == 0 || result == ordered))
            return null;
        else
            return result;
    }

    protected List<Border> findByOrdered(Border ordered)
    {
        SearchFilter filter = SearchFilter.instanceSingle();
        filter.eq(Border.PROPERTY_priceAware, ordered.getBorderDef());
        filter.eq(Border.PROPERTY_priced, ordered.getTexture());
        filter.eq("status", StoreElementStatus.order);
        filter.eq("order.status", OrderStatus.production);
        filter.addDescOrder("length");
        List<Border> borders = loadAll(filter);
        return borders;
    }

    private void cancelOrder(Border ordered, List<Border> borders)
    {
        boolean next = false;
        Border border = borders.get(0);
        if (border.getLength() <= ordered.getLength())
        {
            //заказанных больше или равно чем заказываемых
            next = border.getLength() < ordered.getLength();

            ordered.setLength(ordered.getLength() - border.getLength());
            if (ordered.getLength() == 0)
            {
                ordered.setAmount(0);
            }
        }
        else
        {
            //заказанных меньше чем заказываемых
            Border cloned = border.clone();
            border.setLength(border.getLength() - ordered.getLength());
            save(border);

            cloned.setLength(ordered.getLength());
            border = cloned;

            ordered.setLength(0D);
            ordered.setAmount(0);
        }
        border.setStatus(StoreElementStatus.used);
        border.setProvider(ordered.getProvider());
        border.setDelivery(ordered.getDelivery());
        save(border);
        if (next)
        {
            borders = findByOrdered(ordered);
            if (borders.size() > 0)
            {
                cancelOrder(ordered, borders);
            }
        }
    }

    public double getCurveGluingAdditionalLength()
    {
        return curveGluingAdditionalLength;
    }

    public void setCurveGluingAdditionalLength(double curveGluingAdditionalLength)
    {
        this.curveGluingAdditionalLength = curveGluingAdditionalLength;
    }
}
