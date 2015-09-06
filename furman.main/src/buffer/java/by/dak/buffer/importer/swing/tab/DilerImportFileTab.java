package by.dak.buffer.importer.swing.tab;

import by.dak.buffer.importer.DilerImportFile;
import by.dak.cutting.swing.store.Constants;
import by.dak.swing.AValueTab;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 23.11.2010
 * Time: 19:21:00
 * To change this template use File | Settings | File Templates.
 */
public class DilerImportFileTab extends AValueTab<DilerImportFile>
{
    @Override
    public void init()
    {
        setCacheEditorCreator(Constants.getEntityEditorCreators(DilerImportFile.class));
        setVisibleProperties(Constants.getEntityEditorVisibleProperties(DilerImportFile.class));
        super.init();
    }
}
