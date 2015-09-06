package by.dak.cutting.swing.store.tree;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.swing.store.Constants;
import by.dak.cutting.swing.store.helpers.BoardListUpdater;
import by.dak.cutting.swing.store.helpers.BoardTreeNEDActions;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Board;
import by.dak.persistence.entities.BoardDef;
import by.dak.persistence.entities.TextureEntity;
import by.dak.swing.table.AListUpdater;
import by.dak.swing.table.ListUpdater;
import by.dak.swing.table.NewEditDeleteActions;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 22.02.2010
 * Time: 14:34:56
 * To change this template use File | Settings | File Templates.
 */
public class BoardDefNode extends AFilterNode<BoardDef, Board>
{
    public static class BoardFilterListUpdater extends AListUpdater<Board>
    {
        private BoardTreeNEDActions actions = new BoardTreeNEDActions();
        private AFilterNode filterNode;

        public BoardFilterListUpdater(AFilterNode filterNode)
        {
            this.filterNode = filterNode;
        }

        @Override
        public void adjustFilter()
        {
            getSearchFilter().addAllCriterion(filterNode.getCriterions());
        }

        @Override
        public String[] getVisibleProperties()
        {
            return Constants.BOARD_TABLE_VISIBLE_PROPERTIES;
        }

        @Override
        public ResourceMap getResourceMap()
        {
            return Application.getInstance().getContext().getResourceMap(BoardListUpdater.class);
        }

        @Override
        public NewEditDeleteActions getNewEditDeleteActions()
        {
            return actions;
        }
    }

    public BoardDefNode(BoardDef boardDef, List<SearchFilter.DCriterion<Criterion>> criterions)
    {
        super(boardDef, null, criterions);
    }

    @Override
    public ListUpdater getListUpdater()
    {
        return new BoardFilterListUpdater(this);
    }

    @Override
    protected void initChildren()
    {
        List<TextureEntity> textures = FacadeContext.getTextureFacade().findTexturesBy((BoardDef) getUserObject());
        for (TextureEntity textureEntity : textures)
        {
            TextureBoardNode textureBoardNode = new TextureBoardNode(textureEntity, getCriterions());
            add(textureBoardNode);
        }
    }

    @Override
    public void adjustCriterions()
    {
        SearchFilter.DCriterion<Criterion> criterion = new SearchFilter.DCriterion<Criterion>(Board.PROPERTY_priceAware, Restrictions.eq(Board.PROPERTY_priceAware, getUserObject()));
        getCriterions().add(criterion);
    }
}
