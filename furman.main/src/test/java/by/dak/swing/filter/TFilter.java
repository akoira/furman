package by.dak.swing.filter;

import by.dak.test.TestUtils;
import net.coderazzi.filters.gui.IFilterHeaderObserver;
import net.coderazzi.filters.gui.TableFilterHeader;
import net.coderazzi.filters.gui.editor.FilterEditor;
import org.jdesktop.swingx.JXTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * User: akoyro
 * Date: 16.11.2009
 * Time: 11:11:46
 */
public class TFilter extends JPanel
{
    public TFilter()
    {
        super(new BorderLayout());

        JXTable jxTable = new JXTable();
        DefaultTableModel model = new DefaultTableModel(
                new String[][]{
                        {"01", "02", "03"},
                        {"11", "12", "13"},
                        {"21", "22", "23"},
                        {"31", "32", "33"},
                        {"41", "42", "43"},
                },
                new String[]{"1", "2", "3"}
        );
        jxTable.setModel(model);
        TableFilterHeader filterHeader = new TableFilterHeader(jxTable);
        filterHeader.setPosition(TableFilterHeader.Position.TOP);

        filterHeader.addHeaderObserver(
                new IFilterHeaderObserver()
                {
                    @Override
                    public void tableFilterEditorCreated(TableFilterHeader header, FilterEditor editor)
                    {
                    }

                    @Override
                    public void tableFilterEditorExcluded(TableFilterHeader header, FilterEditor editor)
                    {
                    }

                    @Override
                    public void tableFilterUpdated(TableFilterHeader header, FilterEditor editor)
                    {
                        System.out.println("header = " + header);
                    }
                }
        );
        JScrollPane scrollPane = new JScrollPane(jxTable);
        add(scrollPane, BorderLayout.CENTER);
    }


    public static void main(String[] args)
    {
        TestUtils.showFrame(new TFilter(), "test");
    }
}
