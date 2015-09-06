package by.dak.cutting.swing.dictionaries.multiselect;

import by.dak.persistence.entities.PriceEntity;
import by.dak.persistence.entities.TextureEntity;

import java.util.Comparator;

/**
 * Created by IntelliJ IDEA.
 * User: akoyro
 * Date: 07.06.2009
 * Time: 16:24:02
 * To change this template use File | Settings | File Templates.
 */
public class TextureComparator implements Comparator
{
    @Override
    public int compare(Object o1, Object o2)
    {
        TextureEntity t1 = null;
        TextureEntity t2 = null;

        if (o1 instanceof TextureEntity)
        {
            t1 = (TextureEntity) o1;
            t2 = (TextureEntity) o2;
        }
        else if (o1 instanceof PriceEntity)
        {
            t1 = (TextureEntity) ((PriceEntity) o1).getPriced();
            t2 = (TextureEntity) ((PriceEntity) o2).getPriced();
        }
        if (t1.getName() != null && t2.getName() != null)
            return t1.getName().compareTo(t2.getName());
        else
            return 0;
    }

}
