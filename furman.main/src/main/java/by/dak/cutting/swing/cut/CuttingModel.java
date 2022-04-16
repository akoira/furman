package by.dak.cutting.swing.cut;

import by.dak.cutting.cut.gui.CutSettings;
import by.dak.cutting.cut.guillotine.Strips;
import by.dak.cutting.swing.order.data.TextureBoardDefPair;
import by.dak.persistence.entities.AOrder;
import org.jdesktop.beans.AbstractBean;

import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author akoyro
 * @version 0.1 30.01.2009
 * @introduced [Preview Plugin Support]
 * @since 2.0.0
 */
public class CuttingModel extends AbstractBean
{
    public static final String PROPERTY_settingsMap = "settingsMap";
    private AOrder order;
    private List<TextureBoardDefPair> pairs;

    private Map<TextureBoardDefPair, CutSettings> settingsMap =
            new HashMap<TextureBoardDefPair, CutSettings>();


    private Map<TextureBoardDefPair, Strips> stripsMap = new HashMap<TextureBoardDefPair, Strips>();

    public CuttingModel()
    {
        super();
    }

    public AOrder getOrder()
    {
        return order;
    }

    public void setOrder(AOrder order)
    {
        this.order = order;
        this.settingsMap.clear();
        this.settingsMap.clear();
        pairs = null;
        fireRefresh();
    }


    public void setPairs(List<TextureBoardDefPair> pairs)
    {
        this.pairs = pairs;
    }


    public List<TextureBoardDefPair> getPairs()
    {
        return pairs;
    }


    public Map<TextureBoardDefPair, CutSettings> getSettingsMap()
    {
        return settingsMap;
    }

    public void setSettingsMap(Map<TextureBoardDefPair, CutSettings> settingsMap)
    {
        this.settingsMap.clear();
        this.settingsMap.putAll(settingsMap);
        firePropertyChange(PROPERTY_settingsMap, null, settingsMap);
    }

    public CutSettings getCutSettings(TextureBoardDefPair pair)
    {
        return settingsMap.get(pair);
    }

    public void putStrips(TextureBoardDefPair pair, Strips strips)
    {
        stripsMap.put(pair, strips);

        firePropertyChange(CuttingModelEvent.Type.STRIPS_CHANGED.name(), null, pair);
    }

    public Strips getStrips(TextureBoardDefPair pair)
    {
        return stripsMap.get(pair);
    }

    public void setStripsMap(Map<TextureBoardDefPair, Strips> map)
    {
        stripsMap.clear();
        stripsMap.putAll(map);
    }

    public Strips removeStrips(TextureBoardDefPair oldPair)
    {
        return stripsMap.remove(oldPair);
    }

    public boolean isStripsLoaded()
    {
        return stripsMap.size() == this.getPairs().size();

    }

    public void fireRefresh()
    {
        firePropertyChange(CuttingModelEvent.Type.REFRESH.name(), null, this);
    }


    public void addStripsChangedListener(PropertyChangeListener listener)
    {
        addPropertyChangeListener(CuttingModelEvent.Type.STRIPS_CHANGED.name(), listener);
    }

    public void removeStripsChangedListener(PropertyChangeListener listener)
    {
        removePropertyChangeListener(CuttingModelEvent.Type.STRIPS_CHANGED.name(), listener);
    }

    public void addRefreshListener(PropertyChangeListener listener)
    {
        addPropertyChangeListener(CuttingModelEvent.Type.REFRESH.name(), listener);
    }

    public void removeRefreshListener(PropertyChangeListener listener)
    {
        removePropertyChangeListener(CuttingModelEvent.Type.REFRESH.name(), listener);
    }

    public Map<TextureBoardDefPair, Strips> getStripsMap()
    {
        return stripsMap;
    }

    public CuttingModel replace(AOrder order) {
        this.order = order;
        return this;
    }
}
