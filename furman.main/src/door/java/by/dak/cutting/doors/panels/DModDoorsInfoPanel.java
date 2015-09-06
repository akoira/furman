package by.dak.cutting.doors.panels;

import by.dak.cutting.CuttingApp;
import by.dak.cutting.doors.Doors;
import by.dak.cutting.swing.DModPanel;
import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.ValidationResultModel;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 20.08.2009
 * Time: 12:24:53
 * To change this template use File | Settings | File Templates.
 */
public class DModDoorsInfoPanel extends DModPanel
{
    private DoorsInfoPanel doorsInfoPanel;

    public DModDoorsInfoPanel(Doors doors)
    {
        doorsInfoPanel = new DoorsInfoPanel(doors);
        initComponents();
    }

    public DoorsInfoPanel getDoorsInfoPanel()
    {
        return doorsInfoPanel;
    }

    @Override
    public void save()
    {

    }

    @Override
    public Boolean validateGUI()
    {
        ValidationResult validationResult = new ValidationResult();

        if (doorsInfoPanel.getJComboBox1().getComboBoxItem().getSelectedItem() == null)
        {
            validationResult.addError(CuttingApp.getApplication().getContext().getResourceMap(DModDoorsInfoPanel.class).getString("errorEmpty.info"));
        }
        if (doorsInfoPanel.getWidthDoors() <= 0)
        {
            validationResult.addError(CuttingApp.getApplication().getContext().getResourceMap(DModDoorsInfoPanel.class).getString("errorWidth.info"));
        }
        if (doorsInfoPanel.getHeightDoors() <= 0)
        {
            validationResult.addError(CuttingApp.getApplication().getContext().getResourceMap(DModDoorsInfoPanel.class).getString("errorHeight.info"));
        }
        if (doorsInfoPanel.getCountDoors() <= 0)
        {
            validationResult.addError(CuttingApp.getApplication().getContext().getResourceMap(DModDoorsInfoPanel.class).getString("errorCount.info"));
        }

        ValidationResultModel validationResultModel = getWarningList().getModel();
        validationResultModel.setResult(validationResult);
        getWarningList().setModel(validationResultModel);

        if (!validationResultModel.hasErrors())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private void initComponents()
    {
        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(layout.createSequentialGroup()
                                .addContainerGap()
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                        .add(doorsInfoPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                                        .add(getWarningList(), org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .add(doorsInfoPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(getWarningList(), org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                                .addContainerGap())
        );
    }
}
