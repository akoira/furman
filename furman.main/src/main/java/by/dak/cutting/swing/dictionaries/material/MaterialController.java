package by.dak.cutting.swing.dictionaries.material;

import by.dak.cutting.swing.DModPanel;
import by.dak.cutting.swing.dictionaries.PricesConfigModPanel;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.PriceAware;
import by.dak.persistence.entities.PriceEntity;
import by.dak.persistence.entities.Priced;
import by.dak.persistence.entities.predefined.MaterialType;
import by.dak.swing.wizard.DWizardController;
import by.dak.swing.wizard.WizardStep;

import java.util.ArrayList;
import java.util.List;

public class MaterialController extends DWizardController<MaterialWizardPanelProvider, MaterialModel, MaterialController.Step>
{

    public MaterialController()
    {
        setProvider(MaterialWizardPanelProvider.createInstance());
        MaterialModel materialModel = new MaterialModel();
        materialModel.setPrices(new ArrayList());
        setModel(materialModel);
    }

    public MaterialController(PriceAware priceAware)
    {
        this();
        if (priceAware != null)
        {
            getModel().setPriceAware(priceAware);
            getModel().setMaterialType(MaterialType.valueOf(priceAware.getClass()));
        }
    }

    @Override
    protected Step getStepBy(String id)
    {
        return Step.valueOf(id);
    }

    @Override
    protected String getIdBy(Step step)
    {
        return step.name();
    }

    protected void adjustCurrentStep(Step step)
    {
        switch (step)
        {
            case type:
                getProvider().getDictionaryTypePanel().setMaterialType(getModel().getMaterialType());
                boolean enable = getModel().getPriceAware() == null || getModel().getMaterialType() == null;
                getProvider().getDictionaryTypePanel().setEnabled(enable);
                break;
            case material:
                if (getModel().getPriceAware() == null)
                {
                    getModel().setPriceAware(FacadeContext.getMaterialTypeHelper().newPriceAwareBy(getModel().getMaterialType()));
                }
                PriceAware priceAware = getModel().getPriceAware();
                DModPanel modPanel = getProvider().getPriceAwarePanel(getModel().getMaterialType());
                //спрасываем предидущее значение потому что может быть тотже объект персистенс объект что был до этого
                modPanel.setValue(null);
                modPanel.setValue(priceAware);
                break;
            case source:
                List<PriceAware> priceAwares = FacadeContext.getMaterialTypeHelper().getPriceAwaresBy(getModel().getMaterialType());
                priceAwares.remove(getModel().getPriceAware());
                getProvider().getSourceMaterialPanel().setMaterialType(getModel().getMaterialType());
                getProvider().getSourceMaterialPanel().setSourceMaterials(priceAwares);
                List<? extends Priced> priceds = FacadeContext.getMaterialTypeHelper().findPricedsBy(getModel().getPriceAware());
                getProvider().getSourceMaterialPanel().setSelectedPriceds(priceds);
                break;
            case price:
                List<PriceEntity> list = FacadeContext.getPriceFacade().findAllBy(getModel().getPriceAware());
                //todo: эта полследовательность обязательна так как новый прайс тогда содается с пустым PriceAware
                PricesConfigModPanel panel = getProvider().getPricedsPanel(getModel().getMaterialType());
                panel.setValue(getModel().getPriceAware());
                panel.setPrices(list);
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    protected void adjustLastStep(Step step)
    {
        if (getLastSelectedStep() != null)
        {
            switch (getLastSelectedStep())
            {
                case type:
                    getModel().setMaterialType(getProvider().getDictionaryTypePanel().getMaterialType());
                    break;
                case material:
                    if (getProvider().getPriceAwarePanel(getModel().getMaterialType()).validateGUI())
                    {
                        getProvider().getPriceAwarePanel(getModel().getMaterialType()).save();
                    }
                    break;
                case source:
                    if (step == Step.price)
                    {
                        List<PriceEntity> prices = FacadeContext.getPriceFacade().findAllBy(getModel().getPriceAware());
                        List<Priced> priceds = getProvider().getSourceMaterialPanel().getSelectedPriceds();

                        for (PriceEntity price : prices)
                        {
                            boolean found = false;
                            for (Priced priced : priceds)
                            {
                                if (price.getPriced().getId().equals(priced.getId()))
                                {
                                    found = true;
                                    break;
                                }
                            }
                            if (!found)
                            {
                                FacadeContext.getPriceFacade().delete(price);
                            }
                        }

                        for (Priced priced : priceds)
                        {
                            PriceEntity price = FacadeContext.getPriceFacade().findUniqueBy(getModel().getPriceAware(), priced);
                            if (price == null)
                            {
                                price = new PriceEntity();
                                price.setPriceAware(getModel().getPriceAware());
                                price.setPriced(priced);
                                FacadeContext.getPriceFacade().save(price);
                            }

                        }
                        break;
                    }
            }
        }
    }

    public static enum Step implements WizardStep
    {
        type,
        material,
        source,
        price
    }

}
