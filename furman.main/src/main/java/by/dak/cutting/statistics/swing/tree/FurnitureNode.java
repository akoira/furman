package by.dak.cutting.statistics.swing.tree;

import by.dak.cutting.statistics.StatisticFilter;
import by.dak.persistence.entities.predefined.MaterialType;
import by.dak.persistence.entities.types.FurnitureCode;
import by.dak.persistence.entities.types.FurnitureType;

import java.util.*;

/**
 * User: akoyro
 * Date: 18.05.2010
 * Time: 17:24:50
 */
public class FurnitureNode extends AStatisticsNode
{
    private Map<FurnitureType, List<FurnitureCode>> map;

    public FurnitureNode(Map<FurnitureType, List<FurnitureCode>> map, StatisticFilter filter)
    {
        super(filter);
        this.map = map;
        setUserObject(MaterialType.furniture);
    }

    @Override
    protected void initChildren()
    {

        List<FurnitureType> list = new ArrayList<FurnitureType>(map.keySet());
        Collections.sort(list, new Comparator<FurnitureType>()
        {
            @Override
            public int compare(FurnitureType o1, FurnitureType o2)
            {
                if (o1 == null)
                {
                    return -1;
                }
                if (o2 == null)
                {
                    return 1;
                }

                return o1.getName().compareTo(o2.getName());
            }
        });

        for (FurnitureType furnitureType : list)
        {
            add(new FurnitureTypeNode(furnitureType, map.get(furnitureType), getFilter()));
        }
    }
}