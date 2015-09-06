package by.dak.cutting.linear;

import by.dak.cutting.cut.gui.CutSettings;
import by.dak.cutting.cut.guillotine.Strips;
import by.dak.cutting.swing.cut.CuttingModelEvent;
import by.dak.ordergroup.OrderGroup;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: dkoyro
 * Date: 09.03.11
 * Time: 23:25
 * To change this template use File | Settings | File Templates.
 */
public class LinearCuttingModel
{
    private OrderGroup orderGroup;
    private List<FurnitureTypeCodePair> pairs;

    private Map<FurnitureTypeCodePair, CutSettings> cutSettingsMap =
            new HashMap<FurnitureTypeCodePair, CutSettings>();

    private Map<FurnitureTypeCodePair, Strips> stripsMap =
            new HashMap<FurnitureTypeCodePair, Strips>();

    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);


    public List<FurnitureTypeCodePair> getPairs()
    {
        return pairs;
    }

    public void setPairs(List<FurnitureTypeCodePair> pairs)
    {
        this.pairs = pairs;
    }

    public CutSettings getCutSettings(FurnitureTypeCodePair pair)
    {
        return cutSettingsMap.get(pair);
    }

    public void putCutSettings(FurnitureTypeCodePair pair, CutSettings cutSettings)
    {
        cutSettingsMap.put(pair, cutSettings);
    }

    public Strips getStrips(FurnitureTypeCodePair pair)
    {
        return stripsMap.get(pair);
    }

    public void putStrips(FurnitureTypeCodePair pair, Strips strips)
    {
        stripsMap.put(pair, strips);

        PropertyChangeEvent event = new PropertyChangeEvent(this,
                CuttingModelEvent.Type.STRIPS_CHANGED.name(),
                null, pair);

        pcs.firePropertyChange(event);

    }

    public OrderGroup getOrderGroup()
    {
        return orderGroup;
    }

    public void setOrderGroup(OrderGroup orderGroup)
    {
        this.orderGroup = orderGroup;
    }

    public void addStripsChangedListener(PropertyChangeListener listener)
    {
        pcs.addPropertyChangeListener(CuttingModelEvent.Type.STRIPS_CHANGED.name(), listener);
    }
}
