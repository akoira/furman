/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package by.dak.profile.swing;

import by.dak.cutting.SpringConfiguration;
import by.dak.profile.PFurnitureTypeDef;
import by.dak.test.TestUtils;

/**
 *
 * @author admin
 */
public class TPExpressionsEditor
{
    public static void main(String[] args)
    {
        new SpringConfiguration();
        PExpressionsEditor editor = new PExpressionsEditor();
        editor.setFurnitureTypeDef(new PFurnitureTypeDef());
        TestUtils.showFrame(editor, "test");
    }
}
