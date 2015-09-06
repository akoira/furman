package by.dak.cutting.swing.order.controls;

import by.dak.cutting.configuration.Constants;
import by.dak.cutting.swing.order.data.*;
import by.dak.cutting.swing.xml.XstreamHelper;
import by.dak.persistence.entities.OrderFurniture;

/**
 * User: akoyro
 * Date: 13.03.2009
 * Time: 22:26:21
 */
public class OrderDetailsDTOConverter
{

    private OrderFurniture orderFurniture;

    private OrderDetailsDTO dto;

    public OrderDetailsDTOConverter(OrderFurniture orderFurniture)
    {
        this.orderFurniture = orderFurniture;
        convert();
    }

    private void convert()
    {
        dto = new OrderDetailsDTO();
        getDTO().setOrderFurnitureEntity(getOrderFurniture());
        getDTO().setName(getOrderFurniture().getName());
        getDTO().setTexture(getOrderFurniture().getTexture());
        getDTO().setCount(getOrderFurniture().getAmount());
        getDTO().setA45((A45) XstreamHelper.getInstance().fromXML(getOrderFurniture().getAngle45()));
        getDTO().setDrilling((Drilling) XstreamHelper.getInstance().fromXML(getOrderFurniture().getDrilling()));
        getDTO().setGlueing((Glueing) XstreamHelper.getInstance().fromXML(getOrderFurniture().getGlueing()));
        getDTO().setGroove((Groove) XstreamHelper.getInstance().fromXML(getOrderFurniture().getGroove()));
        getDTO().setMilling(getOrderFurniture().getMilling());
        getDTO().setCutoff((Cutoff) XstreamHelper.getInstance().fromXML(getOrderFurniture().getCutoff()));

        getDTO().setNumber(getOrderFurniture().getNumber());
        getDTO().setRotatable(getOrderFurniture().isRotatable());
        getDTO().setMatWidth(getOrderFurniture().getBoardDef().getDefaultWidth());
        getDTO().setMatLength(getOrderFurniture().getBoardDef().getDefaultLength());


        if (getOrderFurniture().isComplex())
        {
            getDTO().setSecondNumber(getOrderFurniture().getSecond().getNumber());
            getDTO().setBoardDef(getOrderFurniture().getComlexBoardDef());
            //set size
            getDTO().setLength(getOrderFurniture().getLength() - Constants.COMPEXT_FURNITURE_INCREASE);
            getDTO().setWidth(getOrderFurniture().getWidth() - Constants.COMPEXT_FURNITURE_INCREASE);
        }
        else
        {
            getDTO().setBoardDef(getOrderFurniture().getBoardDef());
            //set size
            getDTO().setLength(getOrderFurniture().getLength());
            getDTO().setWidth(getOrderFurniture().getWidth());
        }
    }

    public OrderFurniture getOrderFurniture()
    {
        return orderFurniture;
    }

    public OrderDetailsDTO getDTO()
    {
        return dto;
    }
}
