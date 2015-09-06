package by.dak.cutting.swing.dictionaries.multiselect;

import by.dak.persistence.entities.PriceEntity;
import by.dak.persistence.entities.TextureEntity;
import by.dak.persistence.entities.types.FurnitureCode;

import java.util.Comparator;

/**
 * Created by IntelliJ IDEA.
 * User: akoyro
 * Date: 07.06.2009
 * Time: 16:24:02
 * To change this template use File | Settings | File Templates.
 */
public class FurnitureCodeComparator implements Comparator
{
    @Override
    public int compare(Object o1, Object o2)
    {
        FurnitureCode t1 = null;
        FurnitureCode t2 = null;

        if (o1 instanceof TextureEntity)
        {
            t1 = (FurnitureCode) o1;
            t2 = (FurnitureCode) o2;
        }
        else if (o1 instanceof PriceEntity)
        {
            t1 = (FurnitureCode) ((PriceEntity) o1).getPriced();
            t2 = (FurnitureCode) ((PriceEntity) o2).getPriced();
        }
        if (t1 == null)
        {
            return -1;
        }
        if (t2 == null)
        {
            return 1;
        }
        return t1.getName().compareTo(t2.getName());
    }

}