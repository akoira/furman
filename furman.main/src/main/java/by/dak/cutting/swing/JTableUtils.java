package by.dak.cutting.swing;

import javax.swing.*;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: 28.12.2008
 * Time: 22:16:48
 * To change this template use File | Settings | File Templates.
 */
public class JTableUtils
{
    public static class AddEntity<E>
    {
        private List<E> list;
        private JTable table;

        public AddEntity(List<E> list, JTable table)
        {
            this.list = list;
            this.table = table;
        }

        public void add(E entity)
        {
            this.list.add(entity);
            int row = this.list.size() - 1;
            table.setRowSelectionInterval(row, row);
            table.scrollRectToVisible(table.getCellRect(row, 0, true));
        }
    }

}
