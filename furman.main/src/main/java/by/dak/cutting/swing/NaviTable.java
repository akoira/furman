package by.dak.cutting.swing;

import by.dak.cutting.SearchFilter;
import net.coderazzi.filters.gui.TableFilterHeader;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.EventObject;

/**
 * Modifyed grid control
 *
 * @author Rudak Alexei
 */
public class NaviTable extends DPanel
{
    private ResourceMap rsMap = Application.getInstance().getContext().getResourceMap(NaviTable.class);
    private SearchFilter searchFilter = new SearchFilter();
    private ButtonListener btLst = new ButtonListener();
    private Integer pageNumber = 0;
    private Integer recordCount = 0;
    private TableFilterHeader tableFilterHeader;
    private boolean showFilterHeader = false;
    private boolean showNaviControls = true;

    public NaviTable()
    {
        initComponents();
        initEnvironment();
    }

    private void initEnvironment()
    {
        btDown.addActionListener(btLst);
        btUp.addActionListener(btLst);
        btDown.setFocusable(false);
        btUp.setFocusable(false);

        addPropertyChangeListener("showFilterHeader", new PropertyChangeListener()
        {
            @Override
            public void propertyChange(PropertyChangeEvent evt)
            {
                Boolean value = (Boolean) evt.getNewValue();
                if (value)
                {
                    getTableFilterHeader().setTable(getTable());
                }
                else
                {
                    getTableFilterHeader().setTable(null);
                }
            }
        });

        addPropertyChangeListener("showNaviControls", new PropertyChangeListener()
        {
            @Override
            public void propertyChange(PropertyChangeEvent evt)
            {
                labelRecords.setVisible(isShowNaviControls());
                btDown.setVisible(isShowNaviControls());
                btUp.setVisible(isShowNaviControls());
                labelPageNumber.setVisible(isShowNaviControls());
                labelPageCount.setVisible(isShowNaviControls());
                labelCount.setVisible(isShowNaviControls());
            }
        });


    }

    public void setMouseProcessed(Boolean mouseProcessed)
    {
        grid.setMouseProcessed(mouseProcessed);
    }

    public void setSize(Integer size)
    {
        recordCount = size;
        updateLabels();
    }

    public void setName(String name)
    {
        if (grid != null)
        {
            grid.setName(name);
        }
        super.setName(name);
    }

    public BaseTable getTable()
    {
        return grid;
    }

    public Long getSelectedId()
    {
        return grid.getSelectedId();
    }

    public void hideColumn(int n)
    {
        grid.hideColumn(n);
    }

    public void buttonProc(String cmd)
    {
        Integer limit = 0;
        if (cmd.equals("DOWN"))
        {
            if (pageNumber > 0)
                pageNumber--;

            searchFilter.setStartIndex(pageNumber * searchFilter.getPageSize());
            searchFilter.setPageSize(searchFilter.getPageSize());

            if (recordCount > searchFilter.getPageSize())
                limit = (pageNumber + 1) * searchFilter.getPageSize();
            else
                limit = recordCount;
        }

        if (cmd.equals("UP"))
        {
            if (pageNumber < recordCount / searchFilter.getPageSize())
                pageNumber++;
            if ((pageNumber + 1) * searchFilter.getPageSize() > recordCount)
                limit = recordCount;
            else
            {
                limit = (pageNumber + 1) * searchFilter.getPageSize();
            }
            searchFilter.setStartIndex(pageNumber * searchFilter.getPageSize());
            searchFilter.setPageSize(searchFilter.getPageSize());
        }

        updateLabels();
        grid.getEventDelegator().updateGrid();
    }

    private void updateLabels()
    {
        labelCount.setText(recordCount + " ");
        labelPageNumber.setText(rsMap.getString("label.pageNumber.text", pageNumber + 1));
        labelPageCount.setText(rsMap.getString("label.pageCount.text", recordCount / searchFilter.getPageSize() + (recordCount % searchFilter.getPageSize() > 0 ? 1 : 0)));
    }

    public SearchFilter getSearchFilter()
    {
        return searchFilter;
    }

    public void setSearchFilter(SearchFilter searchFilter)
    {
        this.searchFilter = searchFilter;
        pageNumber = 0;
        searchFilter.setStartIndex(0);
        updateLabels();
    }

    public boolean isShowFilterHeader()
    {
        return showFilterHeader;
    }

    public void setShowFilterHeader(boolean showFilterHeader)
    {
        boolean old = this.showFilterHeader;
        this.showFilterHeader = showFilterHeader;
        firePropertyChange("showFilterHeader", old, this.showFilterHeader);
    }

    public boolean isShowNaviControls()
    {
        return showNaviControls;
    }

    public void setShowNaviControls(boolean showNaviControls)
    {
        boolean old = this.showNaviControls;
        this.showNaviControls = showNaviControls;
        firePropertyChange("showNaviControls", old, this.showNaviControls);
    }

    public class ButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            buttonProc(e.getActionCommand());
        }
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <draw-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jButton2 = new javax.swing.JButton();
        labelRecords = new javax.swing.JLabel();
        btDown = new javax.swing.JButton();
        btUp = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        grid = new by.dak.cutting.swing.BaseTable()
        {
            @Override
            public boolean editCellAt(int row, int column, EventObject e)
            {
                adjustAdditional(row, column, e);
                return super.editCellAt(row, column, e);
            }
        };

        labelCount = new javax.swing.JLabel();

        labelPageCount = new JLabel();
        labelPageCount.setName("labelPageCount");
        labelPageCount.setText(rsMap.getString("label.pageCount.text", 0));

        labelPageNumber = new JLabel();
        labelPageNumber.setName("labelPageNumber");
        labelPageNumber.setText(rsMap.getString("label.pageNumber.text", 1));


        jButton2.setText("jButton2");

        setBackground(new java.awt.Color(255, 255, 255));

        labelRecords.setText("Records");
        labelRecords.setText(rsMap.getString("labelRecords.text"));

        btDown.setText("<");
        btDown.setActionCommand("DOWN");

        btUp.setText(">");
        btUp.setActionCommand("UP");

        grid.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null}
                },
                new String[]{
                        "Title 1", "Title 2", "Title 3", "Title 4"
                }
        ));
        jScrollPane1.setViewportView(grid);

        labelCount.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelCount.setText("Count");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);

        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(labelCount, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(labelRecords, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 180, Short.MAX_VALUE)
                                                .addComponent(labelPageNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(labelPageCount, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btDown, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btUp, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[]{labelPageNumber, labelPageCount});

        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(labelRecords)
                                        .addComponent(btDown)
                                        .addComponent(btUp)
                                        .addComponent(labelPageNumber)
                                        .addComponent(labelPageCount)
                                        .addComponent(labelCount)
                                )
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
                                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[]{btDown, btUp});

    }// </draw-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btDown;
    private javax.swing.JButton btUp;
    private by.dak.cutting.swing.BaseTable grid;
    private javax.swing.JButton jButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelCount;
    private javax.swing.JLabel labelRecords;
    // End of variables declaration//GEN-END:variables
    private JLabel labelPageCount;
    private JLabel labelPageNumber;


    public void setSortable(boolean sortable)
    {
        grid.setSortable(sortable);
    }

    public boolean isSortable()
    {
        return grid.isSortable();
    }

    public TableFilterHeader getTableFilterHeader()
    {
        if (tableFilterHeader == null)
        {
            tableFilterHeader = new TableFilterHeader();
//            tableFilterHeader.setMode(TableFilterHeader.EditorMode.CHOICE);
            tableFilterHeader.setPosition(TableFilterHeader.Position.TOP);
            tableFilterHeader.setAutoOptions(true);
        }
        return tableFilterHeader;
    }

    //editor started before the row is selected - but we need adjust selected element in list updater
    protected void adjustAdditional(int row, int column, EventObject e)
    {

    }

}
