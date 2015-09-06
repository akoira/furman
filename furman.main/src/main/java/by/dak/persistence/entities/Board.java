package by.dak.persistence.entities;

import by.dak.cutting.swing.order.data.TextureBoardDefPair;
import by.dak.persistence.convert.Board2StringConverter;
import by.dak.persistence.entities.validator.BoardValidator;
import by.dak.utils.convert.StringValue;
import by.dak.utils.validator.Validator;
import org.hibernate.annotations.DiscriminatorOptions;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.awt.*;

/**
 * @author Denis Koyro
 * @version 0.1 16.10.2008
 * @introduced [Builder | Overview ]
 * @since 2.0.0
 */
@Entity
@Proxy(lazy = false)

@DiscriminatorValue(value = "Board")
@DiscriminatorOptions(force = true)

@StringValue(converterClass = Board2StringConverter.class)
@NamedQueries(value =
        {
                @NamedQuery(name = "deleteRestBoardsCreatedByOrder",
                        query = "delete from Board b where b.createdByOrder = :order and b.order is null"),
                @NamedQuery(name = "deleteOrderBoardsByOrder",
                        query = "delete from Board b where b.order = :order and b.status = :status"),
                @NamedQuery(name = "deleteByTemplate",
                        query = "delete from Board b where " +
                                "b.order is null and " +
                                "(b.status = :status0 or " +
                                "b.status = :status1) and " +
                                "b.priceAware = :boardDef and " +
                                "b.priced = :texture and " +
                                "b.length = :length and " +
                                "b.provider = :provider and " +
                                "b.width = :width")
        }
)


@Validator(validatorClass = BoardValidator.class)
public class Board extends AStoreElement<BoardDef, TextureEntity>
{
    public static final String PROPERTY_length = "length";
    public static final String PROPERTY_width = "width";


    @Column(name = "LENGTH", nullable = false, columnDefinition = "double")
    private Long length;

    @Column(name = "WIDTH", nullable = false, columnDefinition = "double")
    private Long width;

    public void setLength(Long length)
    {
        Long old = this.length;
        this.length = length;
        support.firePropertyChange("length", old, length);
    }

    public Long getLength()
    {
        return length;
    }

    public void setWidth(Long width)
    {
        Long old = this.width;
        this.width = width;
        support.firePropertyChange("width", old, width);
    }

    public Long getWidth()
    {
        return width;
    }

    public void setBoardDef(BoardDef definition)
    {
        setPriceAware(definition);
    }

    public BoardDef getBoardDef()
    {
        return getPriceAware();
    }

    public void setTexture(TextureEntity texture)
    {
        setPriced(texture);
    }

    public TextureEntity getTexture()
    {
        return getPriced();
    }

    public TextureBoardDefPair getPair()
    {
        return new TextureBoardDefPair(getTexture(), getBoardDef());
    }

    public void setPair(TextureBoardDefPair pair)
    {
        setBoardDef(pair.getBoardDef());
        setTexture(pair.getTexture());
    }


    public boolean isWhole()
    {
        return getBoardDef().getDefaultLength().equals(getLength()) &&
                getBoardDef().getDefaultWidth().equals(getWidth());
    }

    public Board clone()
    {
        Board board = new Board();
        cloneFilling(board);

        board.setPair(getPair());
        board.setLength(getLength());
        board.setWidth(getWidth());
        return board;
    }

    public Dimension getSize()
    {
        Dimension result = new Dimension();
        if (getLength() != null && getWidth() != null)
        {
            result.setSize(getLength(), getWidth());
        }
        return result;
    }

}