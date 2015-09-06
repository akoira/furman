/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */

package by.dak.cutting.swing.order;

import by.dak.persistence.entities.Dailysheet;
import org.netbeans.spi.wizard.WizardController;
import org.netbeans.spi.wizard.WizardPanelProvider;

import javax.swing.*;
import java.util.Map;

/**
 * @author admin
 */
public class DailyWizard extends WizardPanelProvider
{
    private Dailysheet dailysheet;
    private CurrentDatePanel currentDatePanel = new CurrentDatePanel();
    private CurrentShiftsPanel currentShiftsPanel = new CurrentShiftsPanel();


    public DailyWizard()
    {
        super("Edit Daily Information", new String[]{"1", "2"}, new String[]{"Manager information", "Shifts information"});
    }

    @Override
    protected JComponent createPanel(WizardController controller, String id, Map settings)
    {
        if (id.equals("1"))
        {
            return currentDatePanel;
        }
        else
        {
            return currentShiftsPanel;
        }
    }

    public Dailysheet getDailysheetEntity()
    {
        return currentDatePanel.getDailysheetEntity();
    }

    public void setDailysheetEntity(Dailysheet dailysheet)
    {
        currentDatePanel.setDailysheetEntity(dailysheet);
        currentShiftsPanel.setDailysheetEntity(dailysheet);
    }
}
