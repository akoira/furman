package by.dak.furman.templateimport.values;

import java.io.File;

/**
 * User: akoyro
 * Date: 9/22/13
 * Time: 11:01 AM
 */
public class TemplateCategory extends ACategory<Template>
{
    public static TemplateCategory valueOf(File dir)
    {
        TemplateCategory result = new TemplateCategory();
        result.setName(dir.getName());
        result.setFile(dir);
        return result;
    }
}
