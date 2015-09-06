package by.dak.cutting.swing;

import by.dak.cutting.swing.store.Constants;
import by.dak.persistence.entities.PersistenceEntity;
import by.dak.swing.AValueTab;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 06.01.2010
 * Time: 12:33:42
 * To change this template use File | Settings | File Templates.
 */
public class AEntityEditorTab<E extends PersistenceEntity> extends AValueTab<E>
{
//    public static <E extends PersistenceEntity> AEntityEditorTab<E> make(Class<E> clazz)
//    {
//        AEntityEditorTab<E> tab = new AEntityEditorTab<E>(clazz);
//        tab.setValueClass(clazz);
//        return tab;
//    }
//
//    private AEntityEditorTab(Class<E> clazz)
//    {
//    }

    public AEntityEditorTab()
    {
    }

    @Override
    public void init()
    {
        if (getCacheEditorCreator() == null)
        {
            setCacheEditorCreator(Constants.getEntityEditorCreators(getValueClass()));
        }
        if (getVisibleProperties() == null)
        {
            setVisibleProperties(Constants.getEntityEditorVisibleProperties(getValueClass()));
        }

        super.init();
    }
}
