package by.dak.cutting.swing.store.tree;

import by.dak.cutting.CuttingApp;
import by.dak.cutting.DialogShowers;
import by.dak.cutting.SpringConfiguration;

/**
 * Created by IntelliJ IDEA.
 * User: user0
 * Date: 11.02.2010
 * Time: 17:08:02
 * To change this template use File | Settings | File Templates.
 */
public class TStoreTree
{
    public static void main(String[] args)
    {
        new SpringConfiguration();
        CuttingApp.getApplication();
        DialogShowers.showStoreExplorer();
    }
}
