package by.dak.cutting.statistics.swing.tree;

import by.dak.cutting.statistics.StatisticFilter;
import by.dak.persistence.entities.BorderDefEntity;
import by.dak.persistence.entities.TextureEntity;
import by.dak.persistence.entities.predefined.MaterialType;

import java.util.*;

/**
 * User: akoyro
 * Date: 18.05.2010
 * Time: 17:24:50
 */
public class BorderNode extends AStatisticsNode
{
    private Map<BorderDefEntity, List<TextureEntity>> map;

    public BorderNode(Map<BorderDefEntity, List<TextureEntity>> map, StatisticFilter filter)
    {
        super(filter);
        this.map = map;
        setUserObject(MaterialType.border);
    }

    @Override
    protected void initChildren()
    {

        List<BorderDefEntity> list = new ArrayList<BorderDefEntity>(map.keySet());
        Collections.sort(list, new Comparator<BorderDefEntity>()
        {
            @Override
            public int compare(BorderDefEntity o1, BorderDefEntity o2)
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

        for (BorderDefEntity borderDef : list)
        {
            add(new BorderDefNode(borderDef, map.get(borderDef), getFilter()));
        }
    }
}
