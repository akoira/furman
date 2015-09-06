package by.dak.cutting.agt.swing;

import by.dak.cutting.afacade.swing.AFacadeModPanel;
import by.dak.cutting.afacade.swing.AFacadeTab;

/**
 * User: akoyro
 * Date: 06.09.2010
 * Time: 21:48:39
 */
public class AGTModPanel extends AFacadeModPanel
{
    @Override
    protected AFacadeTab createFacadeTab()
    {
        return new AGTTab(); 
    }
}
