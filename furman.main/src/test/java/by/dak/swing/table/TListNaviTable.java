package by.dak.swing.table;

import by.dak.cutting.SpringConfiguration;
import by.dak.cutting.swing.store.helpers.ListUpdaters;
import by.dak.persistence.entities.BoardDef;
import by.dak.test.TestUtils;

/**
 * User: akoyro
 * Date: 26.11.2009
 * Time: 12:04:29
 */
public class TListNaviTable
{
    public static void main(String[] args)
    {
        new SpringConfiguration();
        ListNaviTable<BoardDef> listNaviTable = new ListNaviTable<BoardDef>()
        {

        };


        listNaviTable.setListUpdater(ListUpdaters.BOARD_DEF_LIST_UPDATER);
        listNaviTable.init();
        TestUtils.showFrame(listNaviTable, "test");
    }
}
