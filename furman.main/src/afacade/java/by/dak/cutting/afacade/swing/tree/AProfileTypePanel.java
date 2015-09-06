package by.dak.cutting.afacade.swing.tree;

import by.dak.cutting.afacade.AProfileType;
import by.dak.cutting.afacade.facade.AProfileTypeFacade;
import by.dak.cutting.swing.AEntityEditorTab;
import by.dak.cutting.swing.ValueSave;
import by.dak.cutting.swing.store.modules.AEntityEditorPanel;
import by.dak.persistence.FacadeContext;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 14.01.2010
 * Time: 11:31:19
 * To change this template use File | Settings | File Templates.
 */
public abstract class AProfileTypePanel<E extends AProfileType> extends AEntityEditorPanel<E>
{
    private AEntityEditorTab<E> profileTypePanel;

    public AProfileTypePanel()
    {
        setShowOkCancel(false);
    }

    @Override
    protected void addTabs()
    {
        profileTypePanel = createProfileTypePanel();
        addTab(profileTypePanel.getTitle(), profileTypePanel);
        setTab(profileTypePanel);
        setShowOkCancel(false);
        setValueSave(new ValueSave<E>()
        {
            @Override
            public void save(E value)
            {
                if (value != null)
                {
                    FacadeContext.getFacadeBy(getEntiryClass()).save(value);
                }
            }
        });
    }

    protected abstract AEntityEditorTab<E> createProfileTypePanel();

    @Override
    public void setValue(E value)
    {
        if (value != null && value.hasId())
        {
            ((AProfileTypeFacade<E>) FacadeContext.getFacadeBy(getEntiryClass())).fillChildTypes(value);
        }
        super.setValue(value);
    }

}