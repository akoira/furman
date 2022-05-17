package by.dak.furman.templateimport.service;

import by.dak.cutting.ACodeTypePair;
import by.dak.cutting.facade.BoardDefFacade;
import by.dak.cutting.facade.OrderFurnitureFacade;
import by.dak.cutting.facade.TextureFacade;
import by.dak.cutting.swing.order.data.Drilling;
import by.dak.cutting.swing.order.data.Glueing;
import by.dak.cutting.swing.order.data.Groove;
import by.dak.cutting.swing.order.data.TextureBoardDefPair;
import by.dak.cutting.swing.xml.XstreamHelper;
import by.dak.furman.templateimport.values.AItem;
import by.dak.furman.templateimport.values.BoardDetail;
import by.dak.persistence.entities.BorderDefEntity;
import by.dak.persistence.entities.OrderFurniture;
import by.dak.persistence.entities.OrderItem;
import by.dak.persistence.entities.TextureEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: akoyro
 * Date: 11/7/13
 * Time: 11:30 PM
 */
public class BoardDetailImport
{
    private static final Logger LOGGER = Logger.getLogger(BoardDetailImport.class);
    private BoardDefFacade boardDefFacade;
    private OrderFurnitureFacade orderFurnitureFacade;
    private TextureFacade textureFacade;

    private AItem item;
    private OrderItem orderItem;

    private BoardDetail detail;
    private Map<String, TextureBoardDefPair> boardDefMap;
    private Map<String, ACodeTypePair<TextureEntity, BorderDefEntity>> borderDefMap;


    public void run()
    {
        TextureBoardDefPair pair = getPair(detail.getBoardDef());
        OrderFurniture orderFurniture = new OrderFurniture();
        orderFurniture.setOrderItem(orderItem);
        orderFurniture.setName((detail).getCode());
        orderFurniture.setAmount(Integer.valueOf(detail.getAmount()));
        orderFurniture.setBoardDef(pair.getBoardDef());
        orderFurniture.setTexture(pair.getTexture());
        orderFurniture.setLength(detail.getLength().longValue());
        orderFurniture.setWidth(detail.getWidth().longValue());
        orderFurniture.setGlueing(getGlueing());
        orderFurniture.setGroove(getGroove());
        orderFurniture.setDrilling(getDrilling());
        orderFurnitureFacade.save(orderFurniture);
    }

    private TextureBoardDefPair getPair(String name)
    {
        TextureBoardDefPair result = boardDefMap.get(name);
        if (result == null)
        {
            result = (TextureBoardDefPair) boardDefMap.values().toArray()[0];
        }
        return result;
    }

    private String getGlueing()
    {

        try
        {
            ACodeTypePair<TextureEntity, BorderDefEntity> pair = borderDefMap.get(detail.getBorderDef());
            if (pair == null)
                pair = (ACodeTypePair<TextureEntity, BorderDefEntity>) borderDefMap.values().toArray()[0];
            Glueing glueing = new Glueing();
            String value = detail.getValue(BoardDetail.Column.length.getIndex());
            int count = StringUtils.countMatches(value, "/");
            boolean exist = false;
            if (count > 0)
            {
                exist = true;
                glueing.setUp(true);
                glueing.setUpBorderDef(pair.getType());
                glueing.setUpTexture(pair.getCode());

            }
            if (count > 1)
            {
                glueing.setDown(true);
                glueing.setDownBorderDef(pair.getType());
                glueing.setDownTexture(pair.getCode());
            }


            value = detail.getValue(BoardDetail.Column.width.getIndex());
            count = StringUtils.countMatches(value, "/");
            if (count > 0)
            {
                exist = true;
                glueing.setLeft(true);
                glueing.setLeftBorderDef(pair.getType());
                glueing.setLeftTexture(pair.getCode());
            }
            if (count > 1)
            {
                glueing.setRight(true);
                glueing.setRightBorderDef(pair.getType());
                glueing.setRightTexture(pair.getCode());
            }
            return exist ? XstreamHelper.getInstance().toXML(glueing) : null;
        }
        catch (Throwable e)
        {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }


    private String getGroove()
    {
        try
        {
            String node = detail.getNote().toLowerCase();
            if (node.contains("паз"))
            {
                Pattern pattern = Pattern.compile("([0-9]{1,2}/[0-9]{1,2}/[0-9]{1}/[0-9]*)", Pattern.UNICODE_CHARACTER_CLASS);
                Matcher matcher = pattern.matcher(node);
                if (matcher.find())
                {
                    String result = matcher.group();
                    String[] items = result.split("/");
                    if (items.length == 4)
                    {
                        Groove groove = new Groove();
                        int size = Integer.valueOf(items[3]);
                        if (size == detail.getLength())
                        {
                            groove.setUp(true);
                            groove.setDepthUp(Integer.valueOf(items[2]));
                            groove.setDistanceUp(Integer.valueOf(items[0]));
                        }
                        else
                        {
                            groove.setLeft(true);
                            groove.setDepthLeft(Integer.valueOf(items[2]));
                            groove.setDistanceLeft(Integer.valueOf(items[0]));
                        }
                        return XstreamHelper.getInstance().toXML(groove);
                    }
                }
            }
        }
        catch (Throwable e)
        {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    public String getDrilling()
    {
        try
        {
            String holes = detail.getHoles();
            if (holes != null)
            {
                Drilling drilling = new Drilling();
//                drilling.setPicName(holes);
                return XstreamHelper.getInstance().toXML(drilling);
            }
        }
        catch (Throwable e)
        {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    public AItem getItem()
    {
        return item;
    }

    public void setItem(AItem item)
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

    public BoardDetail getDetail()
    {
        return detail;
    }

    public void setDetail(BoardDetail detail)
    {
        this.detail = detail;
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

    public Map<String, ACodeTypePair<TextureEntity, BorderDefEntity>> getBorderDefMap()
    {
        return borderDefMap;
    }

    public void setBorderDefMap(Map<String, ACodeTypePair<TextureEntity, BorderDefEntity>> borderDefMap)
    {
        this.borderDefMap = borderDefMap;
    }
}
