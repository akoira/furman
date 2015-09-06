package by.dak.cutting.swing.order.data;

import by.dak.utils.Decorator;

/**
 * @author Denis Koyro
 * @version 0.1 15.03.2009
 * @introduced [Furniture constructor | Iteration 1]
 * @since 1.0.0
 */
public class DTODecorator implements DTO, Decorator<DTO>
{
    private DTO underlying;

    public DTODecorator(DTO underlying)
    {
        this.underlying = underlying;
    }

    public DTO getUnderlying()
    {
        return underlying;
    }

    public String getPicName()
    {
        return getUnderlying().getPicName();
    }

    public void setPicName(String picName)
    {
        getUnderlying().setPicName(picName);
    }

    public String getNotes()
    {
        return getUnderlying().getNotes();
    }

    public void setNotes(String notes)
    {
        getUnderlying().setNotes(notes);
    }

    public boolean isUp()
    {
        return getUnderlying().isUp();
    }

    public void setUp(boolean up)
    {
        getUnderlying().setUp(up);
    }

    public boolean isDown()
    {
        return getUnderlying().isDown();
    }

    public void setDown(boolean down)
    {
        getUnderlying().setDown(down);
    }

    public boolean isRight()
    {
        return getUnderlying().isRight();
    }

    public void setRight(boolean right)
    {
        getUnderlying().setRight(right);
    }

    public boolean isLeft()
    {
        return getUnderlying().isLeft();
    }

    public void setLeft(boolean left)
    {
        getUnderlying().setLeft(left);
    }
}
