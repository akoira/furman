package by.dak.persistence;

import by.dak.additional.facade.AdditionalFacade;
import by.dak.autocad.AutocadFacade;
import by.dak.buffer.facade.*;
import by.dak.category.facade.CategoryFacade;
import by.dak.common.swing.ExceptionHandler;
import by.dak.cutting.afacade.facade.FurnitureCodeLinkFacade;
import by.dak.cutting.afacade.facade.FurnitureTypeLinkFacade;
import by.dak.cutting.agt.facade.AGTColorFacade;
import by.dak.cutting.agt.facade.AGTFacadeFacade;
import by.dak.cutting.agt.facade.AGTTypeFacade;
import by.dak.cutting.currency.facade.CurrencyFacade;
import by.dak.cutting.cut.facade.StripsFacade;
import by.dak.cutting.def.swing.tree.MaterialTypeNodeFactory;
import by.dak.cutting.doors.mech.db.DoorColorFacade;
import by.dak.cutting.doors.mech.db.DoorComponentFacade;
import by.dak.cutting.doors.mech.db.DoorMechDefFacade;
import by.dak.cutting.doors.mech.db.DoorMechTypeFacade;
import by.dak.cutting.doors.profile.facade.ProfileCompDefFacade;
import by.dak.cutting.doors.profile.facade.ProfileCompFacade;
import by.dak.cutting.doors.profile.facade.ProfileDefFacade;
import by.dak.cutting.facade.*;
import by.dak.cutting.linear.facade.LinearStripsFacade;
import by.dak.cutting.swing.dictionaries.material.MaterialTypePanelFactory;
import by.dak.cutting.zfacade.ZButtLink;
import by.dak.cutting.zfacade.facade.ZFacadeFacade;
import by.dak.cutting.zfacade.facade.ZProfileColorFacade;
import by.dak.cutting.zfacade.facade.ZProfileTypeFacade;
import by.dak.ordergroup.facade.OrderGroupFacade;
import by.dak.persistence.entities.Employee;
import by.dak.plastic.facade.DSPPlasticDetailFacade;
import by.dak.plastic.strips.facade.DSPPlasticStripsFacade;
import by.dak.report.jasper.common.facade.CommonDataFacade;
import by.dak.repository.IReportFacade;
import by.dak.repository.IRepositoryService;
import by.dak.template.facade.ITemplateFacadeFacade;
import by.dak.template.facade.TemplateOrderFacade;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;


@Deprecated // replaced by MainFacade
public class FacadeContext
{
    private static final String DATA_SOURCE_NAME = MainFacade.getDataSourceName();
    private static ApplicationContext applicationContext;
    private static Employee employee;

    public static void setApplicationContext(ApplicationContext aC)
    {
        applicationContext = aC;
    }

    public static ApplicationContext getApplicationContext()
    {
        return applicationContext;
    }

    public static MainFacade getMainFacade() {
        return applicationContext.getBean(MainFacade.class);
    }
    public static ITemplateFacadeFacade getTemplateFacadeFacade()
    {
        return applicationContext.getBean(ITemplateFacadeFacade.class);
    }

    public static IRepositoryService getRepositoryService()
    {
        return applicationContext.getBean(IRepositoryService.class);
    }

    public static DesignerFacade getDesignerFacade()
    {
        return (DesignerFacade) applicationContext.getBean("designerFacade");
    }

    public static ShiftFacade getShiftFacade()
    {
        return (ShiftFacade) applicationContext.getBean("shiftFacade");
    }

    public static EmployeeFacade getEmployeeFacade()
    {
        return (EmployeeFacade) applicationContext.getBean("employeeFacade");
    }

    public static DailysheetFacade getDailysheetFacade()
    {
        return (DailysheetFacade) applicationContext.getBean("dailysheetFacade");
    }

    public static CustomerFacade getCustomerFacade()
    {
        return (CustomerFacade) applicationContext.getBean("customerFacade");
    }

    public static OrderFacade getOrderFacade()
    {
        return (OrderFacade) applicationContext.getBean("orderFacade");
    }

    public static DepartmentFacade getDepartmentFacade()
    {
        return (DepartmentFacade) applicationContext.getBean("departmentFacade");
    }

    public static BoardFacade getBoardFacade()
    {
        return (BoardFacade) applicationContext.getBean("boardFacade");
    }

    public static BoardDefFacade getBoardDefFacade()
    {
        return (BoardDefFacade) applicationContext.getBean("boardDefFacade");
    }

    public static BorderDefFacade getBorderDefFacade()
    {
        return (BorderDefFacade) applicationContext.getBean("borderDefFacade");
    }

    public static ManufacturerFacade getManufacturerFacade()
    {
        return (ManufacturerFacade) applicationContext.getBean("manufacturerFacade");
    }

    public static ProviderFacade getProviderFacade()
    {
        return (ProviderFacade) applicationContext.getBean("providerFacade");
    }

    public static PriceFacade getPriceFacade()
    {
        return (PriceFacade) applicationContext.getBean("priceFacade");
    }

    public static TextureFacade getTextureFacade()
    {
        return (TextureFacade) applicationContext.getBean("textureFacade");
    }

    public static CutterFacade getCutterFacade()
    {
        return (CutterFacade) applicationContext.getBean("cutterFacade");
    }

    public static OrderItemFacade getOrderItemFacade()
    {
        return (OrderItemFacade) applicationContext.getBean("orderItemFacade");
    }

    public static OrderFurnitureFacade getOrderFurnitureFacade()
    {
        return (OrderFurnitureFacade) applicationContext.getBean("orderFurnitureFacade");
    }

	@Deprecated
	public static StripsFacade getStripsFacade()
    {
        return (StripsFacade) applicationContext.getBean("stripsFacade");
    }

    public static <O, R> IReportFacade<O, R> getReportJCRFacade()
    {
        return (IReportFacade) applicationContext.getBean(IReportFacade.class);
    }

    public static ServiceFacade getServiceFacade()
    {
        return (ServiceFacade) applicationContext.getBean("serviceFacade");
    }

    public static DoorMechDefFacade getDoorMechDefFacade()
    {
        return (DoorMechDefFacade) applicationContext.getBean("doorMechDefFacade");
    }

    public static DoorColorFacade getDoorColorFacade()
    {
        return (DoorColorFacade) applicationContext.getBean("doorColorFacade");
    }

    public static DoorComponentFacade getDoorComponentFacade()
    {
        return (DoorComponentFacade) applicationContext.getBean("doorComponentFacade");
    }

    public static DoorMechTypeFacade getDoorMechTypeFacade()
    {
        return (DoorMechTypeFacade) applicationContext.getBean("doorMechTypeFacade");
    }

    public static ProfileCompDefFacade getProfileCompDefFacade()
    {
        return (ProfileCompDefFacade) applicationContext.getBean("profileCompDefFacade");
    }

    public static ProfileCompFacade getProfileCompFacade()
    {
        return (ProfileCompFacade) applicationContext.getBean("profileCompFacade");
    }

    public static ProfileDefFacade getProfileDefFacade()
    {
        return (ProfileDefFacade) applicationContext.getBean("profileDefFacade");
    }

    public static BorderFacade getBorderFacade()
    {
        return (BorderFacade) applicationContext.getBean("borderFacade");
    }

    public static FurnitureTypeFacade getFurnitureTypeFacade()
    {
        return (FurnitureTypeFacade) applicationContext.getBean("furnitureTypeFacade");
    }

	@Deprecated
	public static StorageElementLinkFacade getStorageElementLinkFacade()
    {
        return (StorageElementLinkFacade) applicationContext.getBean("storageElementLinkFacade");
    }

    public static MaterialTypeHelper getMaterialTypeHelper()
    {
        return (MaterialTypeHelper) applicationContext.getBean("materialTypeHelper");
    }

    public static FurnitureFacade getFurnitureFacade()
    {
        return (FurnitureFacade) applicationContext.getBean("furnitureFacade");
    }

    public static DeliveryFacade getDeliveryFacade()
    {
        return (DeliveryFacade) applicationContext.getBean("deliveryFacade");
    }

    public static FurnitureLinkFacade getFurnitureLinkFacade()
    {
        return (FurnitureLinkFacade) applicationContext.getBean("furnitureLinkFacade");
    }

    public static OrderGroupFacade getOrderGroupFacade()
    {
        return (OrderGroupFacade) applicationContext.getBean("orderGroupFacade");
    }


    public static ZProfileTypeFacade getZProfileTypeFacade()
    {
        return (ZProfileTypeFacade) applicationContext.getBean("zProfileTypeFacade");
    }

    public static OrderDetailFacade getOrderDetailFacade()
    {
        return (OrderDetailFacade) applicationContext.getBean("orderDetailFacade");
    }

    public static FurnitureLink2FurnitureLinkFacade getFurnitureLink2FurnitureLinkFacade()
    {
        return (FurnitureLink2FurnitureLinkFacade) applicationContext.getBean("furnitureLink2FurnitureLinkFacade");
    }

    public static <E> E createNewInstance(Class<E> eClass)
    {
        try
        {
            return eClass.getConstructor(null).newInstance(null);
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    public static FurnitureCodeFacade getFurnitureCodeFacade()
    {
        return (FurnitureCodeFacade) applicationContext.getBean("furnitureCodeFacade");
    }

    public static AStoreElementFacade getStoreFacadeBy(by.dak.persistence.entities.predefined.MaterialType materialType)
    {
        switch (materialType)
        {
            case board:
                return getBoardFacade();
            case furniture:
                return getFurnitureFacade();
            case border:
                return getBorderFacade();
            default:
                throw new IllegalArgumentException();
        }
    }

    public static BaseFacade getFacadeBy(Class eClass)
    {
        if (eClass == ZButtLink.class)
        {
            return getFurnitureLinkFacade();
        }
        else
        {
            return getFacadeBy0(eClass);
        }
    }

    private static BaseFacade getFacadeBy0(Class eClass)
    {
        try
        {
            String name = "get" + eClass.getSimpleName().replaceAll("Entity", "") + "Facade";

            Method method = FacadeContext.class.getMethod(name, null);

            return (BaseFacade) method.invoke(null, null);
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException(e);
        }
    }

    public static CurrencyFacade getCurrencyFacade()
    {
        return (CurrencyFacade) applicationContext.getBean("currencyFacade");
    }

    public static AdditionalFacade getAdditionalFacade()
    {
        return (AdditionalFacade) applicationContext.getBean("additionalFacade");
    }


    public static Employee getEmployee()
    {
        return employee;
    }

    public static void setEmployee(Employee employee)
    {
        FacadeContext.employee = employee;
    }


    public static ZProfileColorFacade getZProfileColorFacade()
    {
        return (ZProfileColorFacade) applicationContext.getBean("zProfileColorFacade");
    }

    public static ZFacadeFacade getZFacadeFacade()
    {
        return (ZFacadeFacade) applicationContext.getBean("zFacadeFacade");
    }

    public static FurnitureTypeLinkFacade getFurnitureTypeLinkFacade()
    {
        return (FurnitureTypeLinkFacade) applicationContext.getBean("furnitureTypeLinkFacade");

    }

    public static AGTColorFacade getAGTColorFacade()
    {
        return (AGTColorFacade) applicationContext.getBean("agtColorFacade");
    }

    public static AGTTypeFacade getAGTTypeFacade()
    {
        return (AGTTypeFacade) applicationContext.getBean("agtTypeFacade");
    }

    public static AGTFacadeFacade getAGTFacadeFacade()
    {
        return (AGTFacadeFacade) applicationContext.getBean("agtFacadeFacade");
    }

    public static CommonDataFacade getCommonDataFacade()
    {
        return (CommonDataFacade) applicationContext.getBean("commonDataFacade");
    }

    public static MaterialTypeNodeFactory getMaterialTypeNodeFactory()
    {
        return getBean(MaterialTypeNodeFactory.class);
    }

    public static MaterialTypePanelFactory getMaterialTypePanelFactory()
    {
        return getBean(MaterialTypePanelFactory.class);
    }


    private static <T> T getBean(Class<T> aClass)
    {
        String name = aClass.getSimpleName();
        name = StringUtils.lowerCase(name.substring(0, 1)) + name.substring(1);
        return (T) applicationContext.getBean(name);
    }

    public static FurnitureCodeLinkFacade getFurnitureCodeLinkFacade()
    {
        return (FurnitureCodeLinkFacade) applicationContext.getBean("furnitureCodeLinkFacade");
    }

    public static DilerOrderFacade getDilerOrderFacade()
    {
        return (DilerOrderFacade) applicationContext.getBean("dilerOrderFacade");
    }

    public static DilerOrderItemFacade getDilerOrderItemFacade()
    {
        return (DilerOrderItemFacade) applicationContext.getBean("dilerOrderItemFacade");
    }

    public static DilerOrderDetailFacade getDilerOrderDetailFacade()
    {
        return (DilerOrderDetailFacade) applicationContext.getBean("dilerOrderDetailFacade");
    }

    public static TemplateOrderFacade getTemplateOrderFacade()
    {
        return (TemplateOrderFacade) applicationContext.getBean("templateOrderFacade");
    }

    public static CategoryFacade getCategoryFacade()
    {
        return (CategoryFacade) applicationContext.getBean("categoryFacade");
    }

    public static AOrderFacade getOrderFacadeBy(Class aClass)
    {
        return (AOrderFacade) getFacadeBy(aClass);
    }

    public static AutocadFacade getAutocadFacade()
    {
        return (AutocadFacade) applicationContext.getBean("autocadFacade");
    }

    public static LinearStripsFacade getLinearStripsFacade()
    {
        return (LinearStripsFacade) applicationContext.getBean("linearStripsFacade");
    }

    public static ExceptionHandler getExceptionHandler()
    {
        return (ExceptionHandler) applicationContext.getBean("exceptionHandler");
    }

    public static DSPPlasticDetailFacade getDSPPlasticDetailFacade()
    {
        return (DSPPlasticDetailFacade) applicationContext.getBean("dspPlasticDetailFacade");
    }


    public static DSPPlasticStripsFacade getDSPPlasticStripsFacade()
    {
        return (DSPPlasticStripsFacade) applicationContext.getBean("dspPlasticStripsFacade");
    }

    public static TempOrderFacade getTempOrderFacade()
    {
        return (TempOrderFacade) applicationContext.getBean("tempOrderFacade");
    }

    public static TempOrderItemFacade getTempOrderItemFacade()
    {
        return (TempOrderItemFacade) applicationContext.getBean("tempOrderItemFacade");
    }

    public static TempOrderDetailFacade getTempOrderDetailFacade()
    {
        return (TempOrderDetailFacade) applicationContext.getBean("tempOrderDetailFacade");
    }

    public static Connection getJDBCConnection()
    {
        try
        {
            return ((DataSource) FacadeContext.getApplicationContext().getBean(DATA_SOURCE_NAME)).getConnection();
        }
        catch (SQLException e)
        {
            throw new IllegalArgumentException(e);
        }
    }
}


