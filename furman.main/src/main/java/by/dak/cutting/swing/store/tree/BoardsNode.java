package by.dak.cutting.swing.store.tree;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.swing.store.helpers.ListUpdaters;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Board;
import by.dak.persistence.entities.BoardDef;
import by.dak.persistence.entities.predefined.MaterialType;
import by.dak.persistence.entities.predefined.StoreElementStatus;
import by.dak.swing.table.ListUpdater;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

public class BoardsNode extends AFilterNode<MaterialType, Board>
{
    public BoardsNode(StoreElementStatus status)
    {
        super(MaterialType.board, status, new ArrayList());
    }

    @Override
    public ListUpdater getListUpdater()
    {
        return ListUpdaters.createBoardListUpdaterBy(getStatus());
    }

    @Override
    protected void initChildren()
    {
        List<BoardDef> boardDefs = FacadeContext.getBoardDefFacade().loadAllSortedBy("name");

        for (BoardDef boardDef : boardDefs)
        {
            add(new BoardDefNode(boardDef, getCriterions()));
        }
    }

    public List<SearchFilter.DCriterion<Criterion>> getCriterions()
    {
        ArrayList<SearchFilter.DCriterion<Criterion>> criterions = new ArrayList<SearchFilter.DCriterion<Criterion>>();

        SearchFilter.DCriterion<Criterion> criterion = new SearchFilter.DCriterion<Criterion>("status", Restrictions.eq("status", getStatus()));
        criterions.add(criterion);
        criterion = new SearchFilter.DCriterion<Criterion>("status", Restrictions.eq("status", getStatus()));
        criterions.add(criterion);

        return criterions;
    }

    @Override
    public void adjustCriterions()
    {
    }
}
