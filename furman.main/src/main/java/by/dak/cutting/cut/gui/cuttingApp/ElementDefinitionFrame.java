/*
 * JFrame1.java
 *
 * Created on 12. nor 2008, 16:37
 */

package by.dak.cutting.cut.gui.cuttingApp;

import by.dak.cutting.cut.gui.CutSettings;
import by.dak.cutting.cut.gui.XmlSerializer;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.ArrayList;

/**
 * @author Peca
 */


public class ElementDefinitionFrame extends MyFrame
{

    private ArrayList<DimensionItem> elementList;
    private ArrayList<DimensionItem> sheetList;
    private CutSettings cutSettings;
    private String currentFileName;
    private JFileChooser fileDialog = new JFileChooser();

    /**
     * Creates new form JFrame1
     */

    public ElementDefinitionFrame()
    {
        initComponents();
        cutSettings = new CutSettings();
        elementList = new ArrayList();
        sheetList = new ArrayList();
        tableElements1.setModel(new DimensionItemTableModel(elementList));
        tableSheets1.setModel(new DimensionItemTableModel(sheetList));

        //elementList.add(new Element(10, 20, "ryba", 1));
        //elementList.add(new Element(50, 55, "snek", 2));
        setTitle();
        this.setMinimumSize(new java.awt.Dimension(200, 500));
        updateList();
        updateControls();
        //jTable1.getr
        // jTextField1.set
    }

    public CutSettings getCutSettings()
    {
        updateCutSettings();
        return this.cutSettings;
    }

    public void setCutSettings(CutSettings value)
    {
        this.cutSettings = value;
        this.updateList();
        this.updateControls();
    }


    private void updateList()
    {
        elementList.clear();
        for (DimensionItem dim : cutSettings.getElementItems())
        {
            elementList.add(dim);
        }

        sheetList.clear();
        for (DimensionItem dim : cutSettings.getSheets())
        {
            sheetList.add(dim);
        }
    }

    @Override
    public void showDialog()
    {
        XmlSerializer ser = new XmlSerializer();
        try
        {
            currentFileName = (String) ser.readFromXmlFile("ElementDefinitionFrame.config");
        }
        catch (IOException ex)
        {
            //nevadi
        }
        setTitle();
        super.showDialog();
        try
        {
            ser.writeToXmlFile(currentFileName, "ElementDefinitionFrame.config");
        }
        catch (IOException ex)
        {
            //nevadi
        }
    }


    private void updateControls()
    {
        this.tableElements1.invalidate();
        this.tableElements1.repaint();

        this.tableSheets1.invalidate();
        this.tableSheets1.repaint();

        this.cbAllowRotation1.setSelected(this.cutSettings.getAllowRotation());
        this.spinnerSawWidth1.setValue(this.cutSettings.getSawWidth());
    }

    private void updateCutSettings()
    {
        DimensionItem[] elements = new DimensionItem[elementList.size()];
        elementList.toArray(elements);
        cutSettings.setElementItems(elements);

        DimensionItem[] dimensions = new DimensionItem[sheetList.size()];
        sheetList.toArray(dimensions);
        cutSettings.setSheets(dimensions);

        cutSettings.setAllowRotation(cbAllowRotation1.isSelected());

        cutSettings.setSawWidth((Integer) spinnerSawWidth1.getValue());
    }

    /* public void showDialog(){
        Dialog diag = new Dialog(ownerFrame);
        diag.setModal(true);
        diag.add(this);
        diag.setVisible(true);
    }*/

    public void setTitle()
    {
        String s = "Element definition";
        if (this.currentFileName != null) s += " - " + this.currentFileName;
        this.setTitle(s);
    }

    private void saveToFile(String fileName)
    {
        updateCutSettings();

        try
        {
            //xmlser.WriteToXmlFile(cutSettings, fileName);
            CutSettings.saveToFile(cutSettings, fileName);
            //throw new IOException();
        }
        catch (IOException ex)
        {
            JOptionPane.showMessageDialog(this, String.format("Error saving to \"%1$s\"\n\n%2$s", fileName, ex.getMessage()), "Chyba", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadFromFile(String fileName)
    {
        //XmlSerializer xmlser = new XmlSerializer();
        try
        {
            cutSettings = CutSettings.loadFromFile(fileName);
            updateList();
            updateControls();
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(this, String.format("Error loading \"%1$s\"\n\n%2$s", fileName, ex.getMessage()), "Chyba", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int parseInt(String s)
    {
        try
        {
            return Integer.valueOf(s);
        }
        catch (NumberFormatException ex)
        {
            return 0;
        }
    }

    private boolean parseBool(String s)
    {
        try
        {
            return Boolean.valueOf(s);
        }
        catch (NumberFormatException ex)
        {
            return true;
        }
    }

    private void importFromCsvFile(String fileName)
    {
        try
        {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            elementList.clear();
            sheetList.clear();
            boolean firstLine = true;
            while (line != null)
            {
                String[] cell = line.split(";");
                if (cell.length >= 5)
                {
                    if (firstLine)
                    {
                        firstLine = false;
                        boolean isValid = true;
                        isValid &= cell[0].toLowerCase().equals("id");
                        isValid &= cell[1].toLowerCase().equals("width");
                        isValid &= cell[2].toLowerCase().equals("height");
                        isValid &= cell[3].toLowerCase().equals("count");
                        isValid &= cell[4].toLowerCase().equals("type");
                        if (!isValid)
                        {
                            throw new Exception("Invalid header");
                        }
                    }
                    else
                    {
                        String id = cell[0];
                        int width = parseInt(cell[1]);
                        int height = parseInt(cell[2]);
                        int count = parseInt(cell[3]);
                        String type = cell[4].toLowerCase();

                        DimensionItem item = new DimensionItem(width, height, id, count);
                        if (type.equals("element"))
                        {
                            this.elementList.add(item);
                        }
                        if (type.equals("sheet"))
                        {
                            this.sheetList.add(item);
                        }

                    }
                }
                else
                {
                    throw new Exception("Invalid file format");
                }
                line = br.readLine();
            }
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(this, String.format("Error loading \"%1$s\"\n\n%2$s", fileName, ex.getMessage()), "Chyba", JOptionPane.ERROR_MESSAGE);
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

        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableElements1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        btnAddElement1 = new javax.swing.JButton();
        btnRemoveElement1 = new javax.swing.JButton();
        btnRotateElement1 = new javax.swing.JButton();
        btnImportFromCsv = new javax.swing.JButton();
        cbAllowRotation1 = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        spinnerSawWidth1 = new javax.swing.JSpinner();
        jPanel5 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        btnRotateSheet1 = new javax.swing.JButton();
        btnAddSheet1 = new javax.swing.JButton();
        btnRemoveSheet1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableSheets1 = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        btnLoadFromFile1 = new javax.swing.JButton();
        btnSaveToFile1 = new javax.swing.JButton();
        btnClose1 = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, " Elements to by.dak.cutting.cut "));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jScrollPane2.setPreferredSize(new java.awt.Dimension(452, 200));

        tableElements1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null}
                },
                new String[]{
                        "Title 1", "Title 2", "Title 3", "Title 4"
                }
        )
        {
            Class[] types = new Class[]{
                    java.lang.Integer.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex)
            {
                return types[columnIndex];
            }
        });
        jScrollPane2.setViewportView(tableElements1);

        jPanel3.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        btnAddElement1.setText("Add element");
        btnAddElement1.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                btnAddElement1MouseClicked(evt);
            }
        });

        btnRemoveElement1.setText("Remove element");
        btnRemoveElement1.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                btnRemoveElement1MouseClicked(evt);
            }
        });

        btnRotateElement1.setText("Rotate element");
        btnRotateElement1.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                btnRotateElement1MouseClicked(evt);
            }
        });

        btnImportFromCsv.setText("Import from csv");
        btnImportFromCsv.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                btnImportFromCsvMouseClicked(evt);
            }
        });

        cbAllowRotation1.setSelected(true);
        cbAllowRotation1.setText("Allow rotation");

        jLabel1.setText("Saw width:");

        jLabel2.setText("mm");

        spinnerSawWidth1.setModel(new javax.swing.SpinnerNumberModel(0, 0, 100, 1));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(btnRemoveElement1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnAddElement1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(btnRotateElement1)
                                        .addComponent(cbAllowRotation1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addGap(6, 6, 6)
                                                .addComponent(spinnerSawWidth1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel2))
                                        .addComponent(btnImportFromCsv))
                                .addContainerGap(115, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnAddElement1)
                                        .addComponent(btnRotateElement1)
                                        .addComponent(btnImportFromCsv))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnRemoveElement1)
                                        .addComponent(cbAllowRotation1)
                                        .addComponent(jLabel1)
                                        .addComponent(jLabel2)
                                        .addComponent(spinnerSawWidth1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        jPanel3.add(jPanel2, java.awt.BorderLayout.SOUTH);

        add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, " Sheets "));
        jPanel4.setLayout(new java.awt.BorderLayout());

        btnRotateSheet1.setText("Rotate sheet");
        btnRotateSheet1.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                btnRotateSheet1MouseClicked(evt);
            }
        });

        btnAddSheet1.setText("Add sheet");
        btnAddSheet1.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                btnAddSheet1MouseClicked(evt);
            }
        });

        btnRemoveSheet1.setText("Remove sheet");
        btnRemoveSheet1.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                btnRemoveSheet1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
                jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel7Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btnAddSheet1)
                                .addGap(18, 18, 18)
                                .addComponent(btnRemoveSheet1)
                                .addGap(18, 18, 18)
                                .addComponent(btnRotateSheet1)
                                .addContainerGap(167, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
                jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel7Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnAddSheet1)
                                        .addComponent(btnRemoveSheet1)
                                        .addComponent(btnRotateSheet1))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel7, java.awt.BorderLayout.PAGE_END);

        tableSheets1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tableSheets1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
        );

        jPanel4.add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel4, java.awt.BorderLayout.CENTER);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        btnLoadFromFile1.setText("Open");
        btnLoadFromFile1.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                btnLoadFromFile1MouseClicked(evt);
            }
        });

        btnSaveToFile1.setText("Save");
        btnSaveToFile1.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                btnSaveToFile1MouseClicked(evt);
            }
        });

        btnClose1.setText("Close");
        btnClose1.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                btnClose1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addComponent(btnLoadFromFile1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnSaveToFile1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnClose1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(109, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnClose1, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnLoadFromFile1, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                                .addComponent(btnSaveToFile1, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE))
        );

        jPanel5.add(jPanel6, java.awt.BorderLayout.PAGE_END);

        add(jPanel5, java.awt.BorderLayout.PAGE_END);
    }// </draw-fold>//GEN-END:initComponents

    private void btnAddElement1MouseClicked(java.awt.event.MouseEvent evt)
    {//GEN-FIRST:event_btnAddElement1MouseClicked
        elementList.add(new DimensionItem(10, 20, String.valueOf(elementList.size())));
        tableElements1.revalidate();
    }//GEN-LAST:event_btnAddElement1MouseClicked

    private void btnRemoveElement1MouseClicked(java.awt.event.MouseEvent evt)
    {//GEN-FIRST:event_btnRemoveElement1MouseClicked
        int rowIndex = tableElements1.getSelectedRow();
        elementList.remove(rowIndex);
        tableElements1.revalidate();

    }//GEN-LAST:event_btnRemoveElement1MouseClicked

    private void btnSaveToFile1MouseClicked(java.awt.event.MouseEvent evt)
    {//GEN-FIRST:event_btnSaveToFile1MouseClicked
        fileDialog.setDialogTitle("Save as");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Tocut file", "tocut");
        fileDialog.setFileFilter(filter);

        if (currentFileName != null)
        {
            File file = new File(currentFileName);
            fileDialog.setSelectedFile(file);
        }
        if (fileDialog.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
        {
            String s = fileDialog.getSelectedFile().getAbsolutePath();
            if (!s.toLowerCase().endsWith(".tocut")) s += ".tocut";
            saveToFile(s);
            tableElements1.revalidate();
            tableSheets1.revalidate();
            currentFileName = s;
            setTitle();
        }
    }//GEN-LAST:event_btnSaveToFile1MouseClicked

    private void btnLoadFromFile1MouseClicked(java.awt.event.MouseEvent evt)
    {//GEN-FIRST:event_btnLoadFromFile1MouseClicked

        fileDialog.setDialogTitle("Open");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Tocut file", "tocut");
        fileDialog.setFileFilter(filter);
        if (currentFileName != null)
        {
            File file = new File(currentFileName);
            fileDialog.setSelectedFile(file);
        }

        if (fileDialog.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
        {
            String s = fileDialog.getSelectedFile().getAbsolutePath();
            loadFromFile(s);
            tableElements1.revalidate();
            tableSheets1.revalidate();
            currentFileName = s;
            setTitle();
        }
    }//GEN-LAST:event_btnLoadFromFile1MouseClicked

    private void btnRotateElement1MouseClicked(java.awt.event.MouseEvent evt)
    {//GEN-FIRST:event_btnRotateElement1MouseClicked
        for (int rowIndex : tableElements1.getSelectedRows())
        {
            elementList.get(rowIndex).getDimension().rotate();
        }
        tableElements1.repaint();
    }//GEN-LAST:event_btnRotateElement1MouseClicked

    private void btnRotateSheet1MouseClicked(java.awt.event.MouseEvent evt)
    {//GEN-FIRST:event_btnRotateSheet1MouseClicked
        for (int rowIndex : tableSheets1.getSelectedRows())
        {
            sheetList.get(rowIndex).getDimension().rotate();
        }
        tableSheets1.repaint();
    }//GEN-LAST:event_btnRotateSheet1MouseClicked

    private void btnImportFromCsvMouseClicked(java.awt.event.MouseEvent evt)
    {//GEN-FIRST:event_btnImportFromCsvMouseClicked
        fileDialog.setDialogTitle("Open");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV file", "csv");
        fileDialog.setFileFilter(filter);
        if (currentFileName != null)
        {
            File file = new File(currentFileName);
            fileDialog.setSelectedFile(file);
        }

        if (fileDialog.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
        {
            String s = fileDialog.getSelectedFile().getAbsolutePath();
            importFromCsvFile(s);
            tableElements1.revalidate();
            tableElements1.repaint();
            tableSheets1.revalidate();
            tableSheets1.repaint();
            currentFileName = s;
            setTitle();
        }
    }//GEN-LAST:event_btnImportFromCsvMouseClicked

    private void btnAddSheet1MouseClicked(java.awt.event.MouseEvent evt)
    {//GEN-FIRST:event_btnAddSheet1MouseClicked
        sheetList.add(new DimensionItem(10, 20, String.valueOf(sheetList.size())));
        tableSheets1.revalidate();
    }//GEN-LAST:event_btnAddSheet1MouseClicked

    private void btnRemoveSheet1MouseClicked(java.awt.event.MouseEvent evt)
    {//GEN-FIRST:event_btnRemoveSheet1MouseClicked
        int rowIndex = tableSheets1.getSelectedRow();
        sheetList.remove(rowIndex);
        tableSheets1.revalidate();
    }//GEN-LAST:event_btnRemoveSheet1MouseClicked

    private void btnClose1MouseClicked(java.awt.event.MouseEvent evt)
    {//GEN-FIRST:event_btnClose1MouseClicked
        this.close();
    }//GEN-LAST:event_btnClose1MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception ex)
        {

        }
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                ElementDefinitionFrame frame = new ElementDefinitionFrame();
                frame.showDialog();
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddElement1;
    private javax.swing.JButton btnAddSheet1;
    private javax.swing.JButton btnClose1;
    private javax.swing.JButton btnImportFromCsv;
    private javax.swing.JButton btnLoadFromFile1;
    private javax.swing.JButton btnRemoveElement1;
    private javax.swing.JButton btnRemoveSheet1;
    private javax.swing.JButton btnRotateElement1;
    private javax.swing.JButton btnRotateSheet1;
    private javax.swing.JButton btnSaveToFile1;
    private javax.swing.JCheckBox cbAllowRotation1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSpinner spinnerSawWidth1;
    private javax.swing.JTable tableElements1;
    private javax.swing.JTable tableSheets1;
    // End of variables declaration//GEN-END:variables

    private class MyFileNameFilter implements FilenameFilter
    {

        String extension;

        public MyFileNameFilter(String extension)
        {
            this.extension = extension.toLowerCase();
        }

        public boolean accept(File dir, String name)
        {
            String s = String.format("\\.%1$s$", extension);
            boolean b = name.toLowerCase().matches(s);
            return b;
        }

    }

}
