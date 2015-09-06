package by.dak.category.facade;

import by.dak.category.Category;
import by.dak.cutting.SearchFilter;
import by.dak.cutting.facade.BaseFacadeImpl;
import by.dak.template.facade.TemplateOrderFacade;

import java.util.List;

/**
 * User: akoyro
 * Date: 24.03.11
 * Time: 13:09
 */
public class CategoryFacadeImpl extends BaseFacadeImpl<Category> implements CategoryFacade
{
    private TemplateOrderFacade templateOrderFacade;

    @Override
    public List<Category> loadBy(Category parent)
    {
        SearchFilter searchFilter = SearchFilter.instanceUnbound();
        if (parent != null)
        {
            searchFilter.eq(Category.PROPERTY_parent, parent);
        }
        else
        {
            searchFilter.isNull(Category.PROPERTY_parent);
        }
        searchFilter.addAscOrder(Category.PROPERTY_name);
        return loadAll(searchFilter);
    }

    public TemplateOrderFacade getTemplateOrderFacade()
    {
        return templateOrderFacade;
    }

    public void setTemplateOrderFacade(TemplateOrderFacade templateOrderFacade)
    {
        this.templateOrderFacade = templateOrderFacade;
    }

    @Override
    public void delete(Category entity)
    {
        templateOrderFacade.removeFrom(entity);
        super.delete(entity.getId());
    }
}
