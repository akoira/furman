package by.dak.cutting.swing.store.tree;

import by.dak.cutting.SearchFilter;
import by.dak.persistence.entities.Board;
import by.dak.persistence.entities.TextureEntity;
import by.dak.swing.table.ListUpdater;
import org.hibernate.criterion.Criterion;

import java.util.List;

public class TextureBoardNode extends ATextureFilterNode<TextureEntity, Board>
{
    public TextureBoardNode(TextureEntity userObject, List<SearchFilter.DCriterion<Criterion>> criterions)
    {
        super(userObject, criterions);
    }

    @Override
    public ListUpdater getListUpdater()
    {
        return new BoardDefNode.BoardFilterListUpdater(this);
    }
}
