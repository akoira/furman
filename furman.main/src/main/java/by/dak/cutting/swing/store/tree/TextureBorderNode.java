package by.dak.cutting.swing.store.tree;

import by.dak.cutting.SearchFilter;
import by.dak.persistence.entities.Border;
import by.dak.persistence.entities.TextureEntity;
import by.dak.swing.table.ListUpdater;
import org.hibernate.criterion.Criterion;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 22.02.2010
 * Time: 15:35:10
 * To change this template use File | Settings | File Templates.
 */
public class TextureBorderNode extends ATextureFilterNode<TextureEntity, Border>
{
    public TextureBorderNode(TextureEntity userObject, List<SearchFilter.DCriterion<Criterion>> criterions)
    {
        super(userObject, criterions);
    }

    @Override
    public ListUpdater getListUpdater()
    {
        return new BorderDefNode.BorderFilterListUpdater(this);
    }


}