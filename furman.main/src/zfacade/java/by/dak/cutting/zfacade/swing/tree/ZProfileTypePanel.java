package by.dak.cutting.zfacade.swing.tree;

import by.dak.cutting.afacade.swing.tree.AProfileTypePanel;
import by.dak.cutting.swing.AEntityEditorTab;
import by.dak.cutting.zfacade.ZProfileType;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 14.01.2010
 * Time: 11:31:19
 * To change this template use File | Settings | File Templates.
 */
public class ZProfileTypePanel extends AProfileTypePanel<ZProfileType>
{
    @Override
    protected AEntityEditorTab<ZProfileType> createProfileTypePanel()
    {
        return new ZProfileTypeTab();
    }
}