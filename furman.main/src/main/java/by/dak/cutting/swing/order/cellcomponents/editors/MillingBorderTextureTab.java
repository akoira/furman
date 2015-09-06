package by.dak.cutting.swing.order.cellcomponents.editors;

import by.dak.cutting.swing.order.data.Milling;
import by.dak.cutting.swing.order.data.OrderDetailsDTO;
import by.dak.cutting.swing.store.EditorCreators;
import by.dak.cutting.swing.store.tabs.PriceAwareToPricedBinder;
import by.dak.cutting.swing.xml.XstreamHelper;
import by.dak.swing.AValueTab;
import by.dak.swing.ActionsPanel;
import by.dak.swing.WindowCallback;
import org.jdesktop.application.Action;
import org.jdesktop.application.Application;

/**
 * User: akoyro
 * Date: 21.04.11
 * Time: 17:08
 */
public class MillingBorderTextureTab extends ActionsPanel
{

    private Milling milling;
    private WindowCallback windowCallback;
    private OrderDetailsDTO orderDetails;
    private AValueTab<Milling> valueTab;

    public MillingBorderTextureTab(OrderDetailsDTO orderDetails, Milling milling, WindowCallback windowCallback)
    {
        super();
        this.orderDetails = orderDetails;
        this.milling = milling;
        this.windowCallback = windowCallback;
    }

    @Override
    public void init()
    {
        valueTab = new AValueTab<Milling>()
        {
            @Override
            public void init()
            {
                super.init();
                getBindingGroup().addBindingListener(new PriceAwareToPricedBinder(this));
            }
        };

        valueTab.setValueClass(Milling.class);
        valueTab.setVisibleProperties("borderDef", "texture");
        valueTab.setCacheEditorCreator(EditorCreators.EDITOR_CREATORS);
        valueTab.init();
        valueTab.setValue(milling);

        setContentComponent(valueTab);
        setActions("saveMilling");
        setSourceActionMap(Application.getInstance().getContext().getActionMap(this));
        super.init();
    }

    @Action
    public void saveMilling()
    {
        if (milling.getBorderDef() != null && milling.getTexture() != null)
        {
            orderDetails.setMilling(XstreamHelper.getInstance().toXML(milling));
            windowCallback.dispose();
        }
    }

}
