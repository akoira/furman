package by.dak.swing.wizard;

import org.netbeans.spi.wizard.Wizard;
import org.netbeans.spi.wizard.WizardObserver;

import java.util.ArrayList;
import java.util.List;

public class GenericWizardObserver implements WizardObserver
{
    private List<WizardObserver> wizardObservers = new ArrayList<WizardObserver>();

    public void addWizardObserver(WizardObserver wizardObserver)
    {
        wizardObservers.add(wizardObserver);
    }

    public void removeWizardObserver(WizardObserver wizardObserver)
    {
        wizardObservers.remove(wizardObserver);
    }

    @Override
    public void stepsChanged(Wizard wizard)
    {
        for (WizardObserver wizardObserver : wizardObservers)
        {
            wizardObserver.stepsChanged(wizard);
        }
    }

    @Override
    public void navigabilityChanged(Wizard wizard)
    {
        for (WizardObserver wizardObserver : wizardObservers)
        {
            wizardObserver.navigabilityChanged(wizard);
        }
    }

    @Override
    public void selectionChanged(Wizard wizard)
    {
        for (WizardObserver wizardObserver : wizardObservers)
        {
            wizardObserver.selectionChanged(wizard);
        }
    }
}
