package by.dak.cutting.swing.dictionaries;

import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.PriceEntity;
import by.dak.persistence.entities.Priced;
import by.dak.persistence.entities.Service;
import by.dak.utils.validator.ValidationUtils;
import by.dak.utils.validator.ValidatorAnnotationProcessor;
import com.jgoodies.validation.ValidationResult;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingListener;
import org.jdesktop.beansbinding.PropertyStateEvent;
import org.jdesktop.observablecollections.ObservableCollections;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: akoyro
 * Date: 18.06.2009
 * Time: 21:15:55
 * To change this template use File | Settings | File Templates.
 */
public abstract class PriceListSupporter
{
    private List<PriceEntity> prices = ObservableCollections.observableList(new ArrayList<PriceEntity>());
    private Controller controller = new Controller();
    private AbstractPricesTab pricesTab;

    public PriceListSupporter(AbstractPricesTab pricesTab)
    {
        this.pricesTab = pricesTab;
    }

    public List<PriceEntity> getPrices()
    {
        return prices;
    }

    public void setPrices(List<PriceEntity> prices)
    {
        this.prices.clear();
        this.prices.addAll(prices);
        PriceEntity priceEntity = createEmptyPrice();
        this.prices.add(priceEntity);
    }

    protected abstract PriceEntity createEmptyPrice();

    public BindingListener getPriceChangedController()
    {
        return controller;
    }


    private class Controller implements BindingListener
    {
        @Override
        public void bindingBecameBound(Binding binding)
        {
        }

        @Override
        public void bindingBecameUnbound(Binding binding)
        {
        }

        @Override
        public void syncFailed(Binding binding, Binding.SyncFailure failure)
        {
        }

        @Override
        public void synced(Binding binding)
        {
            PriceEntity price = (PriceEntity) binding.getSourceObject();
            ValidationResult result = ValidatorAnnotationProcessor.getProcessor().validate(price);
            if (pricesTab.getWarningList() != null)
            {
                pricesTab.getWarningList().getModel().setResult(result);
                pricesTab.getWarningList().setModel(pricesTab.getWarningList().getModel());
            }
            if (!ValidationUtils.isErrors(result))
            {
                if (price.getId() == null || price.getId() == 0)
                {
                    PriceEntity priceEntity = createEmptyPrice();
                    if (priceEntity != null)
                    {
                        prices.add(priceEntity);
                    }
                }
                //todo may be need some other way
                savePriced(price.getPriced());
                FacadeContext.getPriceFacade().save(price);
            }
        }

        private void savePriced(Priced priced)
        {

            if (priced instanceof Service)
            {
                // не нужно для сервисов потому что при сохранении появляется prices и перезаписывает все.
                //FacadeContext.getServiceFacade().save((Service) priced);
            }
            else
            {
                FacadeContext.getFacadeBy(priced.getClass()).save(priced);
            }
        }

        @Override
        public void sourceChanged(Binding binding, PropertyStateEvent event)
        {
        }

        @Override
        public void targetChanged(Binding binding, PropertyStateEvent event)
        {
        }
    }

}
