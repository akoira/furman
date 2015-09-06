package by.dak.cutting.zfacade.swing;

import by.dak.cutting.afacade.swing.AFacadeModPanel;
import by.dak.cutting.afacade.swing.AFacadeTab;

/**
 * User: akoyro
 * Date: 06.09.2010
 * Time: 21:42:16
 */
public class ZFacadeModPanel extends AFacadeModPanel
{
    @Override
    protected AFacadeTab createFacadeTab()
    {
        return new ZFacadeTab();
    }
}
