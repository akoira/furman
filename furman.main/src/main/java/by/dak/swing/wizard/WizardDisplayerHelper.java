package by.dak.swing.wizard;

import by.dak.cutting.CuttingApp;
import org.jdesktop.application.Application;
import org.netbeans.api.wizard.WizardDisplayer;
import org.netbeans.spi.wizard.Wizard;
import org.netbeans.spi.wizard.WizardObserver;
import org.netbeans.spi.wizard.WizardPanelProvider;

import java.util.HashMap;

/**
 * User: akoyro
 * Date: 09.06.2009
 * Time: 10:59:11
 */
public class WizardDisplayerHelper
{
    static
    {
        System.setProperty("WizardDisplayer.default", DWizardDisplayerImpl.class.getName());
    }

    private WizardPanelProvider wizardPanelProvider;
    private WizardObserver wizardObserver;

    @Deprecated
    public WizardDisplayerHelper(WizardPanelProvider wizardPanelProvider, WizardObserver wizardObserver)
    {
        this.wizardPanelProvider = wizardPanelProvider;
        this.wizardObserver = wizardObserver;
    }

    public WizardDisplayerHelper(DWizardController controller)
    {
        this.wizardObserver = controller.getGenericWizardObserver();
        this.wizardPanelProvider = controller.getProvider();
    }

    public Object showWizard()
    {
        HashMap map = new HashMap();
        map.put(DWizardDisplayerImpl.KEY_NAME, this.wizardPanelProvider.getClass().getSimpleName());
        Wizard wizard = this.wizardPanelProvider.createWizard();
        wizard.addWizardObserver(this.wizardObserver);
        //todo одно место для всех permissions
        if ((Application.getInstance() instanceof CuttingApp ? ((CuttingApp) Application.getInstance()).getPermissionManager().checkPermission(wizardPanelProvider) : true))
        {
            return WizardDisplayer.showWizard(wizard, null, null, map);
        }
        return null;

    }
}
