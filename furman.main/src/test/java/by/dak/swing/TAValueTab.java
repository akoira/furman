package by.dak.swing;

import by.dak.cutting.SpringConfiguration;
import by.dak.cutting.swing.store.tabs.BoardTab;
import by.dak.persistence.entities.Board;
import by.dak.test.TestUtils;

/**
 * User: akoyro
 * Date: 20.11.2009
 * Time: 15:51:42
 */
public class TAValueTab
{
    public static void main(String[] args)
    {
        new SpringConfiguration();
        BoardTab boardTab = new BoardTab();
        boardTab.init();
        boardTab.setValue(new Board());

        TestUtils.showFrame(boardTab, "s");
    }
}
