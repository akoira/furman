package by.dak.cutting.swing.store.tree;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.swing.store.Constants;
import by.dak.cutting.swing.store.helpers.BorderListUpdater;
import by.dak.cutting.swing.store.helpers.BorderTreeNEDActions;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Border;
import by.dak.persistence.entities.BorderDefEntity;
import by.dak.persistence.entities.TextureEntity;
import by.dak.swing.table.AListUpdater;
import by.dak.swing.table.ListUpdater;
import by.dak.swing.table.NewEditDeleteActions;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.jdesktop.application.Application;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 22.02.2010
 * Time: 14:34:56
 * To change this template use File | Settings | File Templates.
 */
public class BorderDefNode extends AFilterNode<BorderDefEntity, Border>
{
    public static class BorderFilterListUpdater extends AListUpdater<Border>
    {
        private BorderTreeNEDActions actions = new BorderTreeNEDActions();
        private AFilterNode filterNode;

        public BorderFilterListUpdater(AFilterNode filterNode)
        {
            this.filterNode = filterNode;
            setResourceMap(Application.getInstance().getContext().getResourceMap(BorderListUpdater.class));
        }

        @Override
        public void adjustFilter()
        {
            getSearchFilter().addAllCriterion(filterNode.getCriterions());
        }

        @Override
        public String[] getVisibleProperties()
        {
            return Constants.BORDER_TABLE_VISIBLE_PROPERTIES;
        }


        @Override
        public NewEditDeleteActions getNewEditDeleteActions()
        {
            return actions;
        }
    }

    public BorderDefNode(BorderDefEntity borderDef, List<SearchFilter.DCriterion<Criterion>> criterions)
    {
        super(borderDef, null, criterions);
    }

    @Override
    public ListUpdater getListUpdater()
    {
        return new BorderFilterListUpdater(this);
    }

    @Override
    protected void initChildren()
    {
        List<TextureEntity> textures = FacadeContext.getTextureFacade().findTexturesBy((BorderDefEntity) getUserObject());
        for (TextureEntity textureEntity : textures)
        {
            TextureBorderNode textureBorderNode = new TextureBorderNode(textureEntity, getCriterions());
            add(textureBorderNode);
        }
    }

    @Override
    public void adjustCriterions()
    {
        SearchFilter.DCriterion<Criterion> criterion = new SearchFilter.DCriterion<Criterion>(Border.PROPERTY_priceAware, Restrictions.eq(Border.PROPERTY_priceAware, getUserObject()));
        getCriterions().add(criterion);
    }
}