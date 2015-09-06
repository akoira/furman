package by.dak.furman.templateimport.service;

import by.dak.furman.templateimport.values.Category;
import by.dak.furman.templateimport.values.Template;
import by.dak.furman.templateimport.values.TemplateCategory;
import by.dak.template.TemplateOrder;

/**
 * User: akoyro
 * Date: 10/12/13
 * Time: 7:44 AM
 */
public interface ITemplateImportService
{
    public by.dak.category.Category importCategory(Category category);

    public by.dak.category.Category importTemplateCategory(TemplateCategory category);

    public TemplateOrder importTemplate(Template template);
}
