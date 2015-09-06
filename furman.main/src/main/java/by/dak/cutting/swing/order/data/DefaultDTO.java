package by.dak.cutting.swing.order.data;

import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * @author Denis Koyro
 * @version 0.1 15.03.2009
 * @introduced [Furniture constructor | Iteration 1]
 * @since 1.0.0
 */
public class DefaultDTO implements DTO
{
    private String picName;
    private String notes;
    private boolean up;
    private boolean down;
    private boolean right;
    private boolean left;

    public DefaultDTO()
    {
    }

    /**
     * Used in Glueing, Groove
     */
    public DefaultDTO(boolean up, boolean down, boolean right, boolean left)
    {
        this(null, null, up, down, right, left);
    }

    /**
     * Used in Drilling
     */
    public DefaultDTO(String picName, String notes)
    {
        this(picName, notes, false, false, false, false);
    }

    /**
     * Used in Milling
     */
    public DefaultDTO(String picName, String notes, boolean up, boolean down, boolean right, boolean left)
    {
        this.picName = picName;
        this.notes = notes;
        this.up = up;
        this.down = down;
        this.right = right;
        this.left = left;
    }

    public String getPicName()
    {
        return picName;
    }

    public void setPicName(String picName)
    {
        this.picName = picName;
    }

    public String getNotes()
    {
        return notes;
    }

    public void setNotes(String notes)
    {
        this.notes = notes;
    }

    public boolean isUp()
    {
        return up;
    }

    public void setUp(boolean up)
    {

        this.up = up;
    }

    public boolean isDown()
    {
        return down;
    }

    public void setDown(boolean down)
    {

        this.down = down;
    }

    public boolean isRight()
    {
        return right;
    }

    public void setRight(boolean right)
    {
        this.right = right;
    }

    public boolean isLeft()
    {
        return left;
    }

    public void setLeft(boolean left)
    {

        this.left = left;
    }

    @Override
    public Object clone()
    {
        DefaultDTO dto = new DefaultDTO();
        if (getPicName() != null)
        {
            dto.setPicName(new String(getPicName()));
        }
        if (getNotes() != null)
        {
            dto.setNotes(new String(getNotes()));
        }
        dto.setUp(isUp());
        dto.setDown(isDown());
        dto.setRight(isRight());
        dto.setLeft(isLeft());
        return dto;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (obj == this)
        {
            return true;
        }
        if (obj.getClass() != getClass())
        {
            return false;
        }
        DefaultDTO dto = (DefaultDTO) obj;
        return new EqualsBuilder()
                .append(getPicName(), dto.getPicName())
                .append(getNotes(), dto.getNotes())
                .append(isUp(), dto.isUp())
                .append(isDown(), dto.isDown())
                .append(isLeft(), dto.isLeft())
                .append(isRight(), dto.isRight())
                .isEquals();
    }
}
