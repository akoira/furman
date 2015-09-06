package by.dak.cutting.agt.swing.tree;

import by.dak.cutting.afacade.swing.tree.AProfileTypePanel;
import by.dak.cutting.agt.AGTType;
import by.dak.cutting.swing.AEntityEditorTab;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 14.01.2010
 * Time: 11:31:19
 * To change this template use File | Settings | File Templates.
 */
public class AGTTypePanel extends AProfileTypePanel<AGTType>
{

    @Override
    protected AEntityEditorTab<AGTType> createProfileTypePanel()
    {
        return new AGTTypeTab();
    }
}