package by.dak.cutting.statistics.swing.tree;

import by.dak.cutting.statistics.StatisticFilter;
import by.dak.persistence.entities.BoardDef;
import by.dak.persistence.entities.TextureEntity;
import by.dak.persistence.entities.predefined.MaterialType;

import java.util.*;

/**
 * User: akoyro
 * Date: 18.05.2010
 * Time: 13:45:32
 */
public class BoardNode extends AStatisticsNode
{
    private Map<BoardDef, List<TextureEntity>> pairs;

    public BoardNode(Map<BoardDef, List<TextureEntity>> pairs, StatisticFilter filter)
    {
        super(filter);
        setUserObject(MaterialType.board);
        this.pairs = pairs;
    }

    @Override
    protected void initChildren()
    {
        Set<BoardDef> set = pairs.keySet();
        ArrayList<BoardDef> boardDefs = new ArrayList<BoardDef>(set);
        Collections.sort(boardDefs, new Comparator<BoardDef>()
        {
            @Override
            public int compare(BoardDef o1, BoardDef o2)
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

        for (BoardDef boardDef : boardDefs)
        {
            add(new BoardDefNode(boardDef, pairs.get(boardDef), getFilter()));
        }
    }
}
