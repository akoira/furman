package by.dak.swing.image;

import by.dak.test.TestUtils;

/**
 * User: akoyro
 * Date: 13.04.11
 * Time: 8:49
 */
public class TImageSelectComponent
{
    public static void main(String[] args)
    {
        ImageSelectComponent component = new ImageSelectComponent();
        component.setName("ImageSelectComponent");
        TestUtils.showFrame(component, component.getName());
    }
}
