package by.dak.cutting.swing.dictionaries.material;

import by.dak.cutting.agt.swing.tree.AGTColorCfgPanel;
import by.dak.cutting.swing.DModPanel;
import by.dak.cutting.swing.dictionaries.*;
import by.dak.cutting.zfacade.swing.tree.ZProfileCodeCfgPanel;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.predefined.MaterialType;
import by.dak.swing.wizard.DWizardPanelProvider;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

import javax.swing.*;
import java.util.HashMap;

public class MaterialWizardPanelProvider extends DWizardPanelProvider<MaterialController.Step>
{
    private static final ResourceMap resourceMap = Application.getInstance().getContext().getResourceMap(MaterialWizardPanelProvider.class);

    private DictionaryTypePanel dictionaryTypePanel;
    private HashMap<MaterialType, DModPanel> priceAwarePanels = new HashMap<MaterialType, DModPanel>();
    private SourceMaterialPanel sourceMaterialPanel;
    private HashMap<MaterialType, PricesConfigModPanel> pricesConfigModPanels = new HashMap<MaterialType, PricesConfigModPanel>();

    private MaterialWizardPanelProvider(String title, String[] steps, String[] descriptions)
    {
        super(title, steps, descriptions);
    }

    public static MaterialWizardPanelProvider createInstance()
    {
        return new MaterialWizardPanelProvider(resourceMap.getString("title"),
                new String[]{
                        MaterialController.Step.type.name(),
                        MaterialController.Step.material.name(),
                        MaterialController.Step.source.name(),
                        MaterialController.Step.price.name()
                },
                new String[]{
                        resourceMap.getString(MaterialController.Step.type.name()),
                        resourceMap.getString(MaterialController.Step.material.name()),
                        resourceMap.getString(MaterialController.Step.source.name()),
                        resourceMap.getString(MaterialController.Step.price.name())
                });
    }

    public JPanel getComponentBy(MaterialController.Step step)
    {
        MaterialType type = getDictionaryTypePanel().getMaterialType();
        switch (step)
        {
            case type:
                return getDictionaryTypePanel();
            case material:
                return getPriceAwarePanel(type);
            case source:
                return getSourceMaterialPanel();
            case price:
                return getPricedsPanel(type);
            default:
                throw new IllegalArgumentException();
        }
    }

    public DictionaryTypePanel getDictionaryTypePanel()
    {
        if (dictionaryTypePanel == null)
        {
            dictionaryTypePanel = new DictionaryTypePanel();
        }
        return dictionaryTypePanel;
    }

    public DModPanel getPriceAwarePanel(MaterialType type)
    {
        return FacadeContext.getMaterialTypePanelFactory().getPanelBy(type);
    }


    public SourceMaterialPanel getSourceMaterialPanel()
    {
        if (sourceMaterialPanel == null)
        {
            sourceMaterialPanel = new SourceMaterialPanel();
        }
        return sourceMaterialPanel;
    }

    public PricesConfigModPanel getPricedsPanel(MaterialType type)
    {
        PricesConfigModPanel pricesConfigModPanel = pricesConfigModPanels.get(type);
        if (pricesConfigModPanel == null)
        {
            pricesConfigModPanel = new PricesConfigModPanel();
            switch (type)
            {
                case board:
                case border:
                    pricesConfigModPanel.setPricedCfgPanel(new TextureConfigurePanel());
                    break;
                case furniture:
                    pricesConfigModPanel.setPricedCfgPanel(new FurnitureCodeCfgPanel());
                    break;
                case zprofile:
                    pricesConfigModPanel.setPricedCfgPanel(new ZProfileCodeCfgPanel());
                    break;
                case agtprofile:
                    pricesConfigModPanel.setPricedCfgPanel(new AGTColorCfgPanel());
                    break;
                default:
                    throw new IllegalArgumentException();
            }
            pricesConfigModPanel.init();
            pricesConfigModPanels.put(type, pricesConfigModPanel);
        }
        return pricesConfigModPanel;
    }

    @Override
    public MaterialController.Step valueOf(String id)
    {
        return MaterialController.Step.valueOf(id);
    }
}
