package by.dak.cutting.doors.panels;

import by.dak.cutting.doors.Doors;
import by.dak.cutting.swing.DModPanel;
import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.ValidationResultModel;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 20.08.2009
 * Time: 13:05:02
 * To change this template use File | Settings | File Templates.
 */
public class DModDoorsDetailPanel extends DModPanel
{
    private DoorsDetailPanel doorsDetailPanel;

    public DModDoorsDetailPanel(Doors doors)
    {
        doorsDetailPanel = new DoorsDetailPanel(doors);
        initComponents();
    }

    @Override
    public Boolean validateGUI()
    {
        ValidationResult validationResult = new ValidationResult();

        validationResult.addAllFrom(doorsDetailPanel.getValidationResult());

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

    @Override
    public void save()
    {

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
                                        .add(doorsDetailPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                                        .add(getWarningList(), org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .add(doorsDetailPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(getWarningList(), org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                                .addContainerGap())
        );
    }

    public DoorsDetailPanel getDoorsDetailPanel()
    {
        return doorsDetailPanel;
    }


}
