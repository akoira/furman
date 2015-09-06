package by.dak.furman.templateimport.values;

import java.io.File;

/**
 * User: akoyro
 * Date: 5/1/13
 * Time: 5:46 PM
 */
public class Category extends ACategory<ACategory>
{
    public static Category valueOf(String name, File dir)
    {
        Category category = new Category();
        category.setFile(dir);
        category.setName(name);
        return category;
    }
}
