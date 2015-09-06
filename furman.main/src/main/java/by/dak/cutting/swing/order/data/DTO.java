package by.dak.cutting.swing.order.data;

public interface DTO
{
    String getPicName();

    void setPicName(String picName);

    String getNotes();

    void setNotes(String notes);

    boolean isUp();

    void setUp(boolean up);

    boolean isDown();

    void setDown(boolean down);

    boolean isRight();

    void setRight(boolean right);

    boolean isLeft();

    void setLeft(boolean left);
}
