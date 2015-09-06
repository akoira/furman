package by.dak.persistence.cutting.entities;

import by.dak.cutting.swing.order.data.TextureBoardDefPair;
import by.dak.persistence.entities.AOrder;
import by.dak.persistence.entities.BoardDef;
import by.dak.persistence.entities.TextureEntity;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 27.09.11
 * Time: 9:23
 * To change this template use File | Settings | File Templates.
 */
@MappedSuperclass
public abstract class ABoardStripsEntity extends AStripsEntity<BoardDef, TextureEntity>
{
    public final static String PROPERTY_order = "order";
    public final static String PROPERTY_strips = "strips";

    @ManyToOne
    @JoinColumn(name = "ORDER_ID", nullable = true)
    private AOrder order;

    public TextureEntity getTexture()
    {
        return getPriced();
    }

    public void setTexture(TextureEntity texture)
    {
        setPriced(texture);
    }

    public BoardDef getBoardDef()
    {
        return getPriceAware();
    }

    public void setBoardDef(BoardDef boardDef)
    {
        this.setPriceAware(boardDef);
    }

    public TextureBoardDefPair getPair()
    {
        return new TextureBoardDefPair(getTexture(), getBoardDef());
    }

    public AOrder getOrder()
    {
        return order;
    }

    public void setOrder(AOrder order)
    {
        this.order = order;
    }
}
