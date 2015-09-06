package by.dak.template.swing.action;

import by.dak.category.Category;
import by.dak.category.facade.CategoryFacade;
import by.dak.persistence.FacadeContext;
import by.dak.template.swing.tree.ACategoryNode;
import by.dak.template.swing.tree.CategoryNode;
import by.dak.template.swing.tree.RootNode;
import org.jdesktop.application.Application;
import org.jdesktop.application.Task;

import javax.swing.tree.DefaultTreeModel;
import java.util.List;

/**
 * User: akoyro
 * Date: 10/12/13
 * Time: 8:12 PM
 */
public class ActionLoadCategories extends Task
{
    private DefaultTreeModel model;

    private CategoryFacade categoryFacade;

    public ActionLoadCategories()
    {
        super(Application.getInstance());
    }

    @Override
    protected Object doInBackground() throws Exception
    {
        List<Category> categories = FacadeContext.getCategoryFacade().loadBy(null);
        for (Category category : categories)
        {
            addCategory(getRootNode(), category);
        }
        return null;
    }

    private void addCategory(ACategoryNode parentNode, Category category)
    {
        CategoryNode categoryNode = CategoryNode.valueOf(category, parentNode.getContext());
        getModel().insertNodeInto(categoryNode, parentNode, parentNode.getChildCount());

        List<Category> categories = categoryFacade.loadBy(category);
        for (Category child : categories)
        {
            addCategory(categoryNode, child);
        }
        parentNode.updateIcons();
    }


    public RootNode getRootNode()
    {
        return (RootNode) getModel().getRoot();
    }


    public CategoryFacade getCategoryFacade()
    {
        return categoryFacade;
    }

    public void setCategoryFacade(CategoryFacade categoryFacade)
    {
        this.categoryFacade = categoryFacade;
    }

    public DefaultTreeModel getModel()
    {
        return model;
    }

    public void setModel(DefaultTreeModel model)
    {
        this.model = model;
    }
}
