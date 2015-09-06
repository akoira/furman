package by.dak.cutting.store.swing;

import by.dak.cutting.DialogShowers;
import by.dak.cutting.SearchFilter;
import by.dak.cutting.store.StoreBooking;
import by.dak.cutting.swing.store.EditorCreators;
import by.dak.persistence.FacadeContext;
import by.dak.swing.AValueTab;
import org.jdesktop.application.Action;

import java.sql.Date;
import java.util.HashMap;

/**
 * User: akoyro
 * Date: 09.01.2011
 * Time: 12:56:30
 */
public class StoreBookingTab extends AValueTab<StoreBooking>
{
    public StoreBookingTab()
    {
        setCacheEditorCreator(EditorCreators.EDITOR_CREATORS);
        setVisibleProperties("productionDate");
    }


    @Action
    public void create()
    {
        HashMap map = new HashMap();
        map.put("PRODUCTION_DATE", new Date(getValue().getProductionDate().getTime()));

        SearchFilter filter = new SearchFilter();
        filter.eq("order.workedDailySheet.date", new Date(getValue().getProductionDate().getTime()));
        FacadeContext.getBorderFacade().loadAll(filter);
        String reportPath = "by/dak/ordergroup/report/border.production.jrxml";
        DialogShowers.showJasperViewer("BookingBorderReport", "BookingBorderReport", getResourceMap(), reportPath, map);
    }
}
