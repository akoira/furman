package by.dak.buffer.importer.swing.tab;

import by.dak.buffer.importer.DilerImportFile;
import by.dak.cutting.SpringConfiguration;
import by.dak.test.TestUtils;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 23.11.2010
 * Time: 18:02:16
 * To change this template use File | Settings | File Templates.
 */
public class TDilerImportFileTab
{
    public static void main(String[] args)
    {
        new SpringConfiguration();
        DilerImportFileTab dilerImportFileTab = new DilerImportFileTab();
        dilerImportFileTab.init();
        DilerImportFile dilerImportFile = new DilerImportFile();
//        dilerImportFile.setCustomer(FacadeContext.getCustomerFacade().loadAll().get(0));
//        dilerImportFile.setSelectedFile(new File("D:\\tmp\\test.zip"));
        dilerImportFileTab.setValue(dilerImportFile);

        TestUtils.showFrame(dilerImportFileTab, "test");
    }

}
