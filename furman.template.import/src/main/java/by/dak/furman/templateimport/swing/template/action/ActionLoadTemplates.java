package by.dak.furman.templateimport.swing.template.action;

import by.dak.furman.templateimport.swing.ANodeAction;
import by.dak.furman.templateimport.values.TemplateCategory;

public class ActionLoadTemplates extends ANodeAction
{
    private TemplateCategory category;

    public void action()
    {
        addChildren(category.getChildren(), getRootNode());
    }

    public void setCategory(TemplateCategory category)
    {
        this.category = category;
    }
}
