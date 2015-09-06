package by.dak.cutting.swing.order.data;


public class TypesWrapper<T, C>
{
    private T type;
    private C complexType;

    public T getType()
    {
        return type;
    }

    public void setType(T type)
    {
        this.type = type;
    }

    public C getComplexType()
    {
        return complexType;
    }

    public void setComplexType(C complexType)
    {
        this.complexType = complexType;
    }
}
