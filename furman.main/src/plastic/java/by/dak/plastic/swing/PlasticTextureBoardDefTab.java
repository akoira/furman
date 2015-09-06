package by.dak.plastic.swing;

import by.dak.cutting.swing.order.data.TextureBoardDefPair;
import by.dak.persistence.FacadeContext;
import by.dak.plastic.controller.DSPPlasticDelegator;
import by.dak.swing.ActionsPanel;
import by.dak.swing.WindowCallback;
import org.jdesktop.application.Action;
import org.jdesktop.application.Application;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 25.09.11
 * Time: 10:34
 * To change this template use File | Settings | File Templates.
 */
public class PlasticTextureBoardDefTab extends ActionsPanel
{
    private WindowCallback windowCallback;
    private DSPPlasticValuePanel dspPlasticValuePanel;
    private DSPPlasticDelegator dspPlasticDelegator;

    public WindowCallback getWindowCallback()
    {
        return windowCallback;
    }

    public void setWindowCallback(WindowCallback windowCallback)
    {
        this.windowCallback = windowCallback;
    }

    public DSPPlasticDelegator getDSPPlasticDelegator()
    {
        return dspPlasticDelegator;
    }

    public void setDSPPlasticDelegator(DSPPlasticDelegator DSPPlasticDelegator)
    {
        this.dspPlasticDelegator = DSPPlasticDelegator;
    }

    @Override
    public void init()
    {
        dspPlasticValuePanel = new DSPPlasticValuePanel();

        DSPPlasticValue value = new DSPPlasticValue();

        TextureBoardDefPair pair = new TextureBoardDefPair(null, FacadeContext.getBoardDefFacade().findDefaultBoardDef());
        value.setDspPair(pair);

//        pair = new TextureBoardDefPair(null, FacadeContext.getBoardDefFacade().findById(FacadeContext.getDSPPlasticDetailFacade().getPlasticBoardDefId(), true));


        initSecondSide(value);

        dspPlasticValuePanel.setValue(value);
        setContentComponent(dspPlasticValuePanel);
        setActions("selectPair");
        setSourceActionMap(Application.getInstance().getContext().getActionMap(this));
        super.init();
    }

    private void initSecondSide(DSPPlasticValue value)
    {
        List<TextureBoardDefPair> pairs = dspPlasticDelegator.getCommonCuttingModel().getPairs();
        for (TextureBoardDefPair p : pairs)
        {
            if (FacadeContext.getDSPPlasticDetailFacade().isPlastic(p.getBoardDef()))
            {
                SidePair sidePair = new SidePair();
                sidePair.setFirst(p);
                sidePair.setSecond(new TextureBoardDefPair(null, p.getBoardDef()));
                value.getSidePairs().add(sidePair);
            }
        }
    }

    @Action
    public void selectPair()
    {
        DSPPlasticValue value = dspPlasticValuePanel.getValue();
        if (dspPlasticValuePanel.validateGUI())
        {
            windowCallback.dispose();
            dspPlasticDelegator.setPlasticValue(value);
            dspPlasticDelegator.generate();
        }
    }
}
