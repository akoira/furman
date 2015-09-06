package by.dak.furman.templateimport.service;

import by.dak.cutting.ACodeTypePair;
import by.dak.cutting.facade.BoardDefFacade;
import by.dak.cutting.facade.OrderFurnitureFacade;
import by.dak.cutting.facade.OrderItemFacade;
import by.dak.cutting.facade.TextureFacade;
import by.dak.cutting.swing.order.data.TextureBoardDefPair;
import by.dak.furman.templateimport.values.BoardDetail;
import by.dak.furman.templateimport.values.BoardItem;
import by.dak.persistence.entities.BorderDefEntity;
import by.dak.persistence.entities.OrderItem;
import by.dak.persistence.entities.TextureEntity;
import by.dak.persistence.entities.predefined.OrderItemType;
import by.dak.template.TemplateOrder;

import java.util.List;
import java.util.Map;

/**
 * User: akoyro
 * Date: 11/7/13
 * Time: 4:10 PM
 */
public class BoardItemImport
{
    private OrderItemFacade orderItemFacade;
    private BoardDefFacade boardDefFacade;
    private OrderFurnitureFacade orderFurnitureFacade;
    private TextureFacade textureFacade;

    private TemplateOrder templateOrder;

    private OrderItem orderItem;

    private BoardItem item;
    private Map<String, TextureBoardDefPair> boardDefMap;
    private Map<String, ACodeTypePair<TextureEntity, BorderDefEntity>> borderDefMap;


    public void run()
    {
        orderItem.setName(item.getName());
        orderItem.setAmount(1);
        orderItem.setType(OrderItemType.common);
        orderItem.setOrder(templateOrder);
        orderItemFacade.save(orderItem);

        List<BoardDetail> details = item.getChildren();
        for (BoardDetail detail : details)
        {
            try
            {
                BoardDetailImport boardDetailImport = new BoardDetailImport();
                boardDetailImport.setBoardDefMap(boardDefMap);
                boardDetailImport.setBorderDefMap(borderDefMap);
                boardDetailImport.setOrderItem(orderItem);
                boardDetailImport.setItem(item);
                boardDetailImport.setDetail((BoardDetail) detail);
                boardDetailImport.setBoardDefFacade(boardDefFacade);
                boardDetailImport.setOrderFurnitureFacade(orderFurnitureFacade);
                boardDetailImport.setTextureFacade(textureFacade);
                boardDetailImport.run();
            }
            catch (Exception e)
            {
                detail.addMessage("Import error", e);
            }
        }
    }

    public TemplateOrder getTemplateOrder()
    {
        return templateOrder;
    }

    public void setTemplateOrder(TemplateOrder templateOrder)
    {
        this.templateOrder = templateOrder;
    }

    public BoardItem getItem()
    {
        return item;
    }

    public void setItem(BoardItem item)
    {
        this.item = item;
    }

    public OrderItem getOrderItem()
    {
        return orderItem;
    }

    public void setOrderItem(OrderItem orderItem)
    {
        this.orderItem = orderItem;
    }

    public void setOrderItemFacade(OrderItemFacade orderItemFacade)
    {
        this.orderItemFacade = orderItemFacade;
    }

    public OrderItemFacade getOrderItemFacade()
    {
        return orderItemFacade;
    }

    public void setBoardDefFacade(BoardDefFacade boardDefFacade)
    {
        this.boardDefFacade = boardDefFacade;
    }

    public BoardDefFacade getBoardDefFacade()
    {
        return boardDefFacade;
    }

    public OrderFurnitureFacade getOrderFurnitureFacade()
    {
        return orderFurnitureFacade;
    }

    public void setOrderFurnitureFacade(OrderFurnitureFacade orderFurnitureFacade)
    {
        this.orderFurnitureFacade = orderFurnitureFacade;
    }

    public TextureFacade getTextureFacade()
    {
        return textureFacade;
    }

    public void setTextureFacade(TextureFacade textureFacade)
    {
        this.textureFacade = textureFacade;
    }

    public void setBoardDefMap(Map<String, TextureBoardDefPair> boardDefMap)
    {
        this.boardDefMap = boardDefMap;
    }

    public Map<String, TextureBoardDefPair> getBoardDefMap()
    {
        return boardDefMap;
    }

    public void setBorderDefMap(Map<String, ACodeTypePair<TextureEntity, BorderDefEntity>> borderDefMap)
    {
        this.borderDefMap = borderDefMap;
    }

    public Map<String, ACodeTypePair<TextureEntity, BorderDefEntity>> getBorderDefMap()
    {
        return borderDefMap;
    }
}
