package by.dak.order.replace.swing;

import by.dak.cutting.ACodeTypePair;
import by.dak.cutting.SpringConfiguration;
import by.dak.cutting.swing.order.data.TextureBoardDefPair;
import by.dak.order.replace.ReplaceValue;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.BoardDef;
import by.dak.persistence.entities.OrderItem;
import by.dak.persistence.entities.TextureEntity;
import by.dak.template.TemplateOrder;
import by.dak.test.TestUtils;
import nl.jj.swingx.gui.modal.JModalConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * User: akoyro
 * Date: 03.12.11
 * Time: 12:32
 */
public class TReplaceValuesTab
{
    public static void main(String[] args)
    {
        JModalConfiguration.enableWaitOnEDT();
        new SpringConfiguration();

        TemplateOrder templateOrder = FacadeContext.getTemplateOrderFacade().loadAll().get(0);
        OrderItem orderItem = FacadeContext.getOrderItemFacade().loadBy(templateOrder).get(0);

        List result = loadReplaceValues(templateOrder);

        ReplaceValuesTab valuesTab = new ReplaceValuesTab();
        valuesTab.setValues(new ArrayList<ReplaceValue<? extends ACodeTypePair, ? extends ACodeTypePair>>(result));
        valuesTab.setEditorsProvider(getEditorsProvider(result));
        valuesTab.init();
        TestUtils.showFrame(valuesTab, "");
        valuesTab.setValue(templateOrder);
    }

    private static List<ReplaceValue<TextureBoardDefPair, TextureBoardDefPair>> loadReplaceValues(TemplateOrder templateOrder)
    {
        List<TextureBoardDefPair> list = FacadeContext.getOrderFurnitureFacade().findUniquePairDefText(templateOrder);
        ArrayList<ReplaceValue<TextureBoardDefPair, TextureBoardDefPair>> result = new ArrayList<ReplaceValue<TextureBoardDefPair, TextureBoardDefPair>>(list.size());
        for (TextureBoardDefPair pair : list)
        {
            ReplaceValue<TextureBoardDefPair, TextureBoardDefPair> value = new ReplaceValue<TextureBoardDefPair, TextureBoardDefPair>();
            value.setSourcePair(pair);
            value.setReplacePair(TextureBoardDefPair.valueOf(pair));
            result.add(value);
        }
        return result;
    }

    private static AEditorsProvider<BoardDef, TextureEntity> getEditorsProvider(final List<ReplaceValue<TextureBoardDefPair, TextureBoardDefPair>> values)
    {
        AEditorsProvider<BoardDef, TextureEntity> editorsProvider = new AEditorsProvider<BoardDef, TextureEntity>()
        {
            @Override
            protected List<BoardDef> getTypes()
            {
                return FacadeContext.getBoardDefFacade().loadAll();

            }

            @Override
            protected BoardDef getCurrentTypeBy(int row)
            {
                ReplaceValue<TextureBoardDefPair, TextureBoardDefPair> value = values.get(row);
                if (value != null)
                {
                    TextureBoardDefPair pair = value.getReplacePair();
                    if (pair != null)
                    {
                        return pair.getBoardDef();
                    }
                }
                return null;
            }

            @Override
            protected List<TextureEntity> getCodesBy(BoardDef type)
            {
                return FacadeContext.getTextureFacade().findTexturesBy(type);
            }
        };
        return editorsProvider;
    }
}
