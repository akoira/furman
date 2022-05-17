package by.dak.cutting.swing.order.controls;

import by.dak.cutting.configuration.Constants;
import by.dak.cutting.swing.order.cellcomponents.editors.cuttoff.CutoffValidator;
import by.dak.cutting.swing.order.data.DTOUtils;
import by.dak.cutting.swing.order.data.OrderDetailsDTO;
import by.dak.cutting.swing.xml.XstreamHelper;
import by.dak.persistence.entities.OrderFurniture;
import by.dak.persistence.entities.OrderItem;

/**
 * User: akoyro
 * Date: 13.03.2009
 * Time: 21:08:57
 */
public class OrderFurnitureConverter
{
    private OrderItem orderItem;
    private OrderDetailsDTO dto;

    private OrderFurniture orderFurniture;

    public OrderFurnitureConverter(OrderDetailsDTO dto, OrderItem orderItem)
    {
        this.orderItem = orderItem;
        assert dto != null;
        this.dto = dto;
        convert();
    }

    private void convert()
    {
        if (dto.isNew())
        {
            orderFurniture = new OrderFurniture();
        }
        else
        {
            orderFurniture = dto.getOrderFurnitureEntity();
        }

        if (dto.getBoardDef().getComposite())
        {
            convertComplex();
        }
        else
        {
            convertSimple();
        }
    }

    private void convertComplex()
    {
        if (orderFurniture.getSecond() == null)
        {
            OrderFurniture second = new OrderFurniture();
            second.setSecond(orderFurniture);
            orderFurniture.setSecond(second);
        }
        //set primary.
        orderFurniture.setPrimary(Boolean.TRUE);
        orderFurniture.getSecond().setPrimary(Boolean.FALSE);

        fillOrderFurniture(orderFurniture, dto);
        fillOrderFurniture(orderFurniture.getSecond(), dto);
        //set usertype
        orderFurniture.setBoardDef(dto.getBoardDef().getSimpleType1());
        orderFurniture.setComlexBoardDef(dto.getBoardDef());
        orderFurniture.getSecond().setBoardDef(dto.getBoardDef().getSimpleType2());
        orderFurniture.getSecond().setComlexBoardDef(dto.getBoardDef());
        //set size
        orderFurniture.setLength(dto.getLength() + Constants.COMPEXT_FURNITURE_INCREASE);
        orderFurniture.setWidth(dto.getWidth() + Constants.COMPEXT_FURNITURE_INCREASE);
        orderFurniture.getSecond().setLength(dto.getLength() + Constants.COMPEXT_FURNITURE_INCREASE);
        orderFurniture.getSecond().setWidth(dto.getWidth() + Constants.COMPEXT_FURNITURE_INCREASE);
        //set number
        orderFurniture.setNumber((long) dto.getNumber());
        orderFurniture.getSecond().setNumber((long) dto.getSecondNumber());
    }

    private void convertSimple()
    {
        orderFurniture.setPrimary(Boolean.TRUE);
        orderFurniture.setSecond(null);
        fillOrderFurniture(orderFurniture, dto);
        //set usertype
        orderFurniture.setBoardDef(dto.getBoardDef());
        orderFurniture.setComlexBoardDef(null);
        //set size
        orderFurniture.setLength(dto.getLength());
        orderFurniture.setWidth(dto.getWidth());
        //set number
        orderFurniture.setNumber((long) dto.getNumber());
    }

    private void fillOrderFurniture(OrderFurniture orderFurniture, OrderDetailsDTO dto)
    {
        orderFurniture.setName(dto.getName());
        orderFurniture.setTexture(dto.getTexture());
        orderFurniture.setAmount(dto.getCount());
        orderFurniture.setRotatable(dto.isRotatable());
        orderFurniture.setOrderItem(orderItem);
        //todo конвертацию сделать на уровне Hibernate
        orderFurniture.setAngle45(DTOUtils.isValueSet(dto.getA45()) ? XstreamHelper.getInstance().toXML(dto.getA45()) : null);
//        orderFurniture.setDrilling(DTOUtils.isValueSet(dto.getDrilling()) ? XstreamHelper.getInstance().toXML(dto.getDrilling()) : null);
        orderFurniture.setGlueing(DTOUtils.isValueSet(dto.getGlueing()) ? XstreamHelper.getInstance().toXML(dto.getGlueing()) : null);
        orderFurniture.setGroove(DTOUtils.isValueSet(dto.getGroove()) ? XstreamHelper.getInstance().toXML(dto.getGroove()) : null);

        orderFurniture.setMilling(dto.getMilling());
        orderFurniture.setCutoff(new CutoffValidator(dto.getCutoff()).validate() ?
                XstreamHelper.getInstance().toXML(dto.getCutoff()) : null);
    }

    public OrderDetailsDTO getDTO()
    {
        return dto;
    }


    public OrderFurniture getOrderFurniture()
    {
        return orderFurniture;
    }
}
