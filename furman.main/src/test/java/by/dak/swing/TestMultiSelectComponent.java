package by.dak.swing;

import by.dak.test.TestUtils;

import java.util.ArrayList;

/**
 * User: akoyro
 * Date: 14.05.2009
 * Time: 11:29:20
 */
public class TestMultiSelectComponent
{
    public static void main(String[] args)
    {
        MultiSelectComponent multiSelectComponent = new MultiSelectComponent();

        ArrayList<String> strings = new ArrayList<String>();
        for (int i = 0; i < 10; i++)
        {
            String s = "string " + i;
            strings.add(s);
        }
        ArrayList<String> selStrings = new ArrayList<String>();
        selStrings.add(strings.get(0));
        selStrings.add(strings.get(1));
        selStrings.add(strings.get(3));
        selStrings.add(strings.get(7));
        multiSelectComponent.setElements(strings);
        multiSelectComponent.setSelectedElements(selStrings);

        TestUtils.showFrame(multiSelectComponent, "TestMultiSelectComponent");
    }

}
