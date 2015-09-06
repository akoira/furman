package by.dak.cutting.swing.dictionaries.material;

import by.dak.cutting.swing.DModPanel;
import by.dak.persistence.entities.predefined.MaterialType;

import java.util.HashMap;
import java.util.Map;

/**
 * User: akoyro
 * Date: 02.09.2010
 * Time: 14:22:10
 */
public class MaterialTypePanelFactory
{
    //key - MaterialType, value - class
    private Map<String, String> panelClasses = new HashMap<String, String>();
    private Map<MaterialType, DModPanel> panels = new HashMap<MaterialType, DModPanel>();

    public DModPanel getPanelBy(MaterialType type)
    {
        DModPanel panel = panels.get(type);
        if (panel == null)
        {
            panel = createPanelBy(type);
            panels.put(type, panel);
        }
        return panel;
    }


    private DModPanel createPanelBy(MaterialType type)
    {
        try
        {
            String className = panelClasses.get(type.name());
            Class aClass = MaterialTypePanelFactory.class.getClassLoader().loadClass(className);
            DModPanel panel = (DModPanel) aClass.getConstructor(null).newInstance(null);
            panel.setShowOkCancel(false);
            return panel;
        }
        catch (Throwable t)
        {
            throw new IllegalArgumentException(t);
        }
    }

    public Map<String, String> getPanelClasses()
    {
        return panelClasses;
    }

    public void setPanelClasses(Map<String, String> panelClasses)
    {
        this.panelClasses = panelClasses;
    }


}
