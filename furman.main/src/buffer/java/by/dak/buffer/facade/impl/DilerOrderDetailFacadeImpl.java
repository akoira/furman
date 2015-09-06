package by.dak.buffer.facade.impl;

import by.dak.buffer.entity.DilerOrderDetail;
import by.dak.buffer.entity.DilerOrderItem;
import by.dak.buffer.entity.TempOrderDetail;
import by.dak.buffer.facade.DilerOrderDetailFacade;
import by.dak.cutting.facade.BaseFacadeImpl;
import by.dak.persistence.FacadeContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 05.11.2010
 * Time: 11:20:49
 * To change this template use File | Settings | File Templates.
 */
public class DilerOrderDetailFacadeImpl extends BaseFacadeImpl<DilerOrderDetail> implements DilerOrderDetailFacade
{

    @Override
    public List<DilerOrderDetail> getDilerOrderDetailsFromTempTable()
    {
        List<TempOrderDetail> tempOrderDetails = FacadeContext.getTempOrderDetailFacade().loadAll();

        return copyFrom(tempOrderDetails);
    }

    private List<DilerOrderDetail> copyFrom(List<TempOrderDetail> tempOrderDetails)
    {
        List<DilerOrderDetail> dilerOrderDetails = new ArrayList<DilerOrderDetail>();

        for (TempOrderDetail tempOrderDetail : tempOrderDetails)
        {
            DilerOrderDetail dilerOrderDetail = new DilerOrderDetail();
            dilerOrderDetail.setDiscriminator(tempOrderDetail.getDiscriminator());
            dilerOrderDetail.setName(tempOrderDetail.getName());
            dilerOrderDetail.setNumber(tempOrderDetail.getNumber());
            dilerOrderDetail.setAmount(tempOrderDetail.getAmount());
            dilerOrderDetail.setPrice(tempOrderDetail.getPrice());
            dilerOrderDetail.setSide(tempOrderDetail.getSide());
            dilerOrderDetail.setSize(tempOrderDetail.getSize());
            dilerOrderDetail.setLength(tempOrderDetail.getLength());
            dilerOrderDetail.setWidth(tempOrderDetail.getWidth());
            dilerOrderDetail.setFurnitureCode(tempOrderDetail.getFurnitureCode());
            dilerOrderDetail.setFurnitureType(tempOrderDetail.getFurnitureType());
            dilerOrderDetail.setComlexFurnitureType(tempOrderDetail.getComlexFurnitureType());
            dilerOrderDetail.setSecond(tempOrderDetail.getSecond());
            dilerOrderDetail.setGroove(tempOrderDetail.getGroove());
            dilerOrderDetail.setGlueing(tempOrderDetail.getGlueing());
            dilerOrderDetail.setAngle45(tempOrderDetail.getAngle45());
            dilerOrderDetail.setCutoff(tempOrderDetail.getCutoff());
            dilerOrderDetail.setDrilling(tempOrderDetail.getDrilling());
            dilerOrderDetail.setMilling(tempOrderDetail.getMilling());
            dilerOrderDetail.setRotatable(tempOrderDetail.getRotatable());
            dilerOrderDetail.setPrimary(tempOrderDetail.getPrimary());

            dilerOrderDetail.setOrderItemId(tempOrderDetail.getOrderItemId());

            dilerOrderDetails.add(dilerOrderDetail);
        }

        return dilerOrderDetails;
    }


    @Override
    public List<DilerOrderDetail> findBy(DilerOrderItem dilerOrderItem)
    {
        return findAllByField(DilerOrderDetail.PROPERTY_dilerOrderItem, dilerOrderItem);
    }
}
