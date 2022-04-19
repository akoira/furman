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
import by.dak.persistence.entities.AOrder;
import by.dak.persistence.entities.Dailysheet;
import by.dak.persistence.entities.Employee;
import by.dak.plastic.facade.DSPPlasticDetailFacade;
import by.dak.plastic.strips.facade.DSPPlasticStripsFacade;
import by.dak.report.jasper.DefaultReportCreatorFactory;
import by.dak.report.jasper.common.facade.CommonDataFacade;
import by.dak.repository.IReportFacade;
import by.dak.repository.IRepositoryService;
import by.dak.template.facade.ITemplateFacadeFacade;
import by.dak.template.facade.TemplateOrderFacade;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Function;


public class MainFacade implements ApplicationContextAware
{
    public static final Function<MainFacade, Function<AOrder, Dailysheet>> dailysheet = mf -> order -> {
        Dailysheet dailysheet =  mf.getDailysheetFacade().loadCurrentDailysheet();
        if (dailysheet == null)
            if (order != null)
                return order.getCreatedDailySheet();
            else
                throw new IllegalArgumentException();
        else
            return dailysheet;
    };

    private static final String DATA_SOURCE_NAME = "dataSource";

    private ApplicationContext applicationContext;
    private Employee employee;

    private ITemplateFacadeFacade templateFacadeFacade;
    private IRepositoryService repositoryService;
    private DesignerFacade designerFacade;
    private ShiftFacade shiftFacade;
    private EmployeeFacade employeeFacade;
    private DailysheetFacade dailysheetFacade;
    private CustomerFacade customerFacade;
    private OrderFacade orderFacade;
    private DepartmentFacade departmentFacade;
    private BoardFacade boardFacade;
    private BoardDefFacade boardDefFacade;
    private BorderDefFacade borderDefFacade;
    private ManufacturerFacade manufacturerFacade;
    private ProviderFacade providerFacade;
    private PriceFacade priceFacade;
    private TextureFacade textureFacade;
    private CutterFacade cutterFacade;
    private OrderItemFacade orderItemFacade;
    private OrderFurnitureFacade orderFurnitureFacade;
    private StripsFacade stripsFacade;
    private IReportFacade reportFacade;
    private ServiceFacade serviceFacade;
    private DoorMechDefFacade doorMechDefFacade;
    private DoorColorFacade doorColorFacade;
    private DoorComponentFacade doorComponentFacade;
    private DoorMechTypeFacade doorMechTypeFacade;
    private ProfileCompDefFacade profileCompDefFacade;
    private ProfileCompFacade profileCompFacade;
    private ProfileDefFacade profileDefFacade;
    private BorderFacade borderFacade;
    private FurnitureTypeFacade furnitureTypeFacade;
    private StorageElementLinkFacade storageElementLinkFacade;
    private MaterialTypeHelper materialTypeHelper;
    private FurnitureFacade furnitureFacade;
    private DeliveryFacade deliveryFacade;
    private FurnitureLinkFacade furnitureLinkFacade;
    private OrderGroupFacade orderGroupFacade;
    private ZProfileTypeFacade zProfileTypeFacade;
    private OrderDetailFacade orderDetailFacade;
    private FurnitureLink2FurnitureLinkFacade furnitureLink2FurnitureLinkFacade;
    private FurnitureCodeFacade furnitureCodeFacade;
    private CurrencyFacade currencyFacade;
    private AdditionalFacade additionalFacade;
    private ZProfileColorFacade zProfileColorFacade;
    private ZFacadeFacade zFacadeFacade;
    private FurnitureTypeLinkFacade furnitureTypeLinkFacade;
    private AGTColorFacade agtColorFacade;
    private AGTTypeFacade agtTypeFacade;
    private AGTFacadeFacade agtFacadeFacade;
    private CommonDataFacade commonDataFacade;
    private FurnitureCodeLinkFacade furnitureCodeLinkFacade;
    private DilerOrderFacade dilerOrderFacade;
    private DilerOrderItemFacade dilerOrderItemFacade;
    private DilerOrderDetailFacade dilerOrderDetailFacade;
    private TemplateOrderFacade templateOrderFacade;
    private CategoryFacade categoryFacade;
    private AutocadFacade autocadFacade;
    private LinearStripsFacade linearStripsFacade;
    private ExceptionHandler exceptionHandler;
    private DSPPlasticDetailFacade dspPlasticDetailFacade;
    private DSPPlasticStripsFacade dspPlasticStripsFacade;
    private TempOrderFacade tempOrderFacade;
    private TempOrderItemFacade tempOrderItemFacade;
    private TempOrderDetailFacade tempOrderDetailFacade;
    private MaterialTypeNodeFactory materialTypeNodeFactory;
    private MaterialTypePanelFactory materialTypePanelFactory;


    public final DefaultReportCreatorFactory reportCreatorFactory;

    public MainFacade() {
        this.reportCreatorFactory = new DefaultReportCreatorFactory(this);
    }

    public static String getDataSourceName()
    {
        return DATA_SOURCE_NAME;
    }


    public <E> E createNewInstance(Class<E> eClass)
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


    public AStoreElementFacade getStoreFacadeBy(by.dak.persistence.entities.predefined.MaterialType materialType)
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

    public BaseFacade getFacadeBy(Class eClass)
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

    private BaseFacade getFacadeBy0(Class eClass)
    {
        try
        {
            String name = "get" + eClass.getSimpleName().replaceAll("Entity", "") + "Facade";

            Method method = this.getClass().getMethod(name, null);

            return (BaseFacade) method.invoke(null, null);
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException(e);
        }
    }


    private <T> T getBean(Class<T> aClass)
    {
        String name = aClass.getSimpleName();
        name = StringUtils.lowerCase(name.substring(0, 1)) + name.substring(1);
        return (T) applicationContext.getBean(name);
    }


    public Connection getJDBCConnection()
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

    public ApplicationContext getApplicationContext()
    {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext)
    {
        this.applicationContext = applicationContext;
    }

    public Employee getEmployee()
    {
        return employee;
    }

    public void setEmployee(Employee employee)
    {
        this.employee = employee;
    }

    public ITemplateFacadeFacade getTemplateFacadeFacade()
    {
        return templateFacadeFacade;
    }

    public void setTemplateFacadeFacade(ITemplateFacadeFacade templateFacadeFacade)
    {
        this.templateFacadeFacade = templateFacadeFacade;
    }

    public IRepositoryService getRepositoryService()
    {
        return repositoryService;
    }

    public void setRepositoryService(IRepositoryService repositoryService)
    {
        this.repositoryService = repositoryService;
    }

    public DesignerFacade getDesignerFacade()
    {
        return designerFacade;
    }

    public void setDesignerFacade(DesignerFacade designerFacade)
    {
        this.designerFacade = designerFacade;
    }

    public ShiftFacade getShiftFacade()
    {
        return shiftFacade;
    }

    public void setShiftFacade(ShiftFacade shiftFacade)
    {
        this.shiftFacade = shiftFacade;
    }

    public EmployeeFacade getEmployeeFacade()
    {
        return employeeFacade;
    }

    public void setEmployeeFacade(EmployeeFacade employeeFacade)
    {
        this.employeeFacade = employeeFacade;
    }

    public DailysheetFacade getDailysheetFacade()
    {
        return dailysheetFacade;
    }

    public void setDailysheetFacade(DailysheetFacade dailysheetFacade)
    {
        this.dailysheetFacade = dailysheetFacade;
    }

    public CustomerFacade getCustomerFacade()
    {
        return customerFacade;
    }

    public void setCustomerFacade(CustomerFacade customerFacade)
    {
        this.customerFacade = customerFacade;
    }

    public OrderFacade getOrderFacade()
    {
        return orderFacade;
    }

    public void setOrderFacade(OrderFacade orderFacade)
    {
        this.orderFacade = orderFacade;
    }

    public DepartmentFacade getDepartmentFacade()
    {
        return departmentFacade;
    }

    public void setDepartmentFacade(DepartmentFacade departmentFacade)
    {
        this.departmentFacade = departmentFacade;
    }

    public BoardFacade getBoardFacade()
    {
        return boardFacade;
    }

    public void setBoardFacade(BoardFacade boardFacade)
    {
        this.boardFacade = boardFacade;
    }

    public BoardDefFacade getBoardDefFacade()
    {
        return boardDefFacade;
    }

    public void setBoardDefFacade(BoardDefFacade boardDefFacade)
    {
        this.boardDefFacade = boardDefFacade;
    }

    public BorderDefFacade getBorderDefFacade()
    {
        return borderDefFacade;
    }

    public void setBorderDefFacade(BorderDefFacade borderDefFacade)
    {
        this.borderDefFacade = borderDefFacade;
    }

    public ManufacturerFacade getManufacturerFacade()
    {
        return manufacturerFacade;
    }

    public void setManufacturerFacade(ManufacturerFacade manufacturerFacade)
    {
        this.manufacturerFacade = manufacturerFacade;
    }

    public ProviderFacade getProviderFacade()
    {
        return providerFacade;
    }

    public void setProviderFacade(ProviderFacade providerFacade)
    {
        this.providerFacade = providerFacade;
    }

    public PriceFacade getPriceFacade()
    {
        return priceFacade;
    }

    public void setPriceFacade(PriceFacade priceFacade)
    {
        this.priceFacade = priceFacade;
    }

    public TextureFacade getTextureFacade()
    {
        return textureFacade;
    }

    public void setTextureFacade(TextureFacade textureFacade)
    {
        this.textureFacade = textureFacade;
    }

    public CutterFacade getCutterFacade()
    {
        return cutterFacade;
    }

    public void setCutterFacade(CutterFacade cutterFacade)
    {
        this.cutterFacade = cutterFacade;
    }

    public OrderItemFacade getOrderItemFacade()
    {
        return orderItemFacade;
    }

    public void setOrderItemFacade(OrderItemFacade orderItemFacade)
    {
        this.orderItemFacade = orderItemFacade;
    }

    public OrderFurnitureFacade getOrderFurnitureFacade()
    {
        return orderFurnitureFacade;
    }

    public void setOrderFurnitureFacade(OrderFurnitureFacade orderFurnitureFacade)
    {
        this.orderFurnitureFacade = orderFurnitureFacade;
    }

    public StripsFacade getStripsFacade()
    {
        return stripsFacade;
    }

    public void setStripsFacade(StripsFacade stripsFacade)
    {
        this.stripsFacade = stripsFacade;
    }

    public IReportFacade getReportFacade()
    {
        return reportFacade;
    }

    public void setReportFacade(IReportFacade reportFacade)
    {
        this.reportFacade = reportFacade;
    }

    public ServiceFacade getServiceFacade()
    {
        return serviceFacade;
    }

    public void setServiceFacade(ServiceFacade serviceFacade)
    {
        this.serviceFacade = serviceFacade;
    }

    public DoorMechDefFacade getDoorMechDefFacade()
    {
        return doorMechDefFacade;
    }

    public void setDoorMechDefFacade(DoorMechDefFacade doorMechDefFacade)
    {
        this.doorMechDefFacade = doorMechDefFacade;
    }

    public DoorColorFacade getDoorColorFacade()
    {
        return doorColorFacade;
    }

    public void setDoorColorFacade(DoorColorFacade doorColorFacade)
    {
        this.doorColorFacade = doorColorFacade;
    }

    public DoorComponentFacade getDoorComponentFacade()
    {
        return doorComponentFacade;
    }

    public void setDoorComponentFacade(DoorComponentFacade doorComponentFacade)
    {
        this.doorComponentFacade = doorComponentFacade;
    }

    public DoorMechTypeFacade getDoorMechTypeFacade()
    {
        return doorMechTypeFacade;
    }

    public void setDoorMechTypeFacade(DoorMechTypeFacade doorMechTypeFacade)
    {
        this.doorMechTypeFacade = doorMechTypeFacade;
    }

    public ProfileCompDefFacade getProfileCompDefFacade()
    {
        return profileCompDefFacade;
    }

    public void setProfileCompDefFacade(ProfileCompDefFacade profileCompDefFacade)
    {
        this.profileCompDefFacade = profileCompDefFacade;
    }

    public ProfileCompFacade getProfileCompFacade()
    {
        return profileCompFacade;
    }

    public void setProfileCompFacade(ProfileCompFacade profileCompFacade)
    {
        this.profileCompFacade = profileCompFacade;
    }

    public ProfileDefFacade getProfileDefFacade()
    {
        return profileDefFacade;
    }

    public void setProfileDefFacade(ProfileDefFacade profileDefFacade)
    {
        this.profileDefFacade = profileDefFacade;
    }

    public BorderFacade getBorderFacade()
    {
        return borderFacade;
    }

    public void setBorderFacade(BorderFacade borderFacade)
    {
        this.borderFacade = borderFacade;
    }

    public FurnitureTypeFacade getFurnitureTypeFacade()
    {
        return furnitureTypeFacade;
    }

    public void setFurnitureTypeFacade(FurnitureTypeFacade furnitureTypeFacade)
    {
        this.furnitureTypeFacade = furnitureTypeFacade;
    }

    public StorageElementLinkFacade getStorageElementLinkFacade()
    {
        return storageElementLinkFacade;
    }

    public void setStorageElementLinkFacade(StorageElementLinkFacade storageElementLinkFacade)
    {
        this.storageElementLinkFacade = storageElementLinkFacade;
    }

    public MaterialTypeHelper getMaterialTypeHelper()
    {
        return materialTypeHelper;
    }

    public void setMaterialTypeHelper(MaterialTypeHelper materialTypeHelper)
    {
        this.materialTypeHelper = materialTypeHelper;
    }

    public FurnitureFacade getFurnitureFacade()
    {
        return furnitureFacade;
    }

    public void setFurnitureFacade(FurnitureFacade furnitureFacade)
    {
        this.furnitureFacade = furnitureFacade;
    }

    public DeliveryFacade getDeliveryFacade()
    {
        return deliveryFacade;
    }

    public void setDeliveryFacade(DeliveryFacade deliveryFacade)
    {
        this.deliveryFacade = deliveryFacade;
    }

    public FurnitureLinkFacade getFurnitureLinkFacade()
    {
        return furnitureLinkFacade;
    }

    public void setFurnitureLinkFacade(FurnitureLinkFacade furnitureLinkFacade)
    {
        this.furnitureLinkFacade = furnitureLinkFacade;
    }

    public OrderGroupFacade getOrderGroupFacade()
    {
        return orderGroupFacade;
    }

    public void setOrderGroupFacade(OrderGroupFacade orderGroupFacade)
    {
        this.orderGroupFacade = orderGroupFacade;
    }

    public ZProfileTypeFacade getzProfileTypeFacade()
    {
        return zProfileTypeFacade;
    }

    public void setzProfileTypeFacade(ZProfileTypeFacade zProfileTypeFacade)
    {
        this.zProfileTypeFacade = zProfileTypeFacade;
    }

    public OrderDetailFacade getOrderDetailFacade()
    {
        return orderDetailFacade;
    }

    public void setOrderDetailFacade(OrderDetailFacade orderDetailFacade)
    {
        this.orderDetailFacade = orderDetailFacade;
    }

    public FurnitureLink2FurnitureLinkFacade getFurnitureLink2FurnitureLinkFacade()
    {
        return furnitureLink2FurnitureLinkFacade;
    }

    public void setFurnitureLink2FurnitureLinkFacade(FurnitureLink2FurnitureLinkFacade furnitureLink2FurnitureLinkFacade)
    {
        this.furnitureLink2FurnitureLinkFacade = furnitureLink2FurnitureLinkFacade;
    }

    public FurnitureCodeFacade getFurnitureCodeFacade()
    {
        return furnitureCodeFacade;
    }

    public void setFurnitureCodeFacade(FurnitureCodeFacade furnitureCodeFacade)
    {
        this.furnitureCodeFacade = furnitureCodeFacade;
    }

    public CurrencyFacade getCurrencyFacade()
    {
        return currencyFacade;
    }

    public void setCurrencyFacade(CurrencyFacade currencyFacade)
    {
        this.currencyFacade = currencyFacade;
    }

    public AdditionalFacade getAdditionalFacade()
    {
        return additionalFacade;
    }

    public void setAdditionalFacade(AdditionalFacade additionalFacade)
    {
        this.additionalFacade = additionalFacade;
    }

    public ZProfileColorFacade getzProfileColorFacade()
    {
        return zProfileColorFacade;
    }

    public void setzProfileColorFacade(ZProfileColorFacade zProfileColorFacade)
    {
        this.zProfileColorFacade = zProfileColorFacade;
    }

    public ZFacadeFacade getzFacadeFacade()
    {
        return zFacadeFacade;
    }

    public void setzFacadeFacade(ZFacadeFacade zFacadeFacade)
    {
        this.zFacadeFacade = zFacadeFacade;
    }

    public FurnitureTypeLinkFacade getFurnitureTypeLinkFacade()
    {
        return furnitureTypeLinkFacade;
    }

    public void setFurnitureTypeLinkFacade(FurnitureTypeLinkFacade furnitureTypeLinkFacade)
    {
        this.furnitureTypeLinkFacade = furnitureTypeLinkFacade;
    }

    public AGTColorFacade getAgtColorFacade()
    {
        return agtColorFacade;
    }

    public void setAgtColorFacade(AGTColorFacade agtColorFacade)
    {
        this.agtColorFacade = agtColorFacade;
    }

    public AGTTypeFacade getAgtTypeFacade()
    {
        return agtTypeFacade;
    }

    public void setAgtTypeFacade(AGTTypeFacade agtTypeFacade)
    {
        this.agtTypeFacade = agtTypeFacade;
    }

    public AGTFacadeFacade getAgtFacadeFacade()
    {
        return agtFacadeFacade;
    }

    public void setAgtFacadeFacade(AGTFacadeFacade agtFacadeFacade)
    {
        this.agtFacadeFacade = agtFacadeFacade;
    }

    public CommonDataFacade getCommonDataFacade()
    {
        return commonDataFacade;
    }

    public void setCommonDataFacade(CommonDataFacade commonDataFacade)
    {
        this.commonDataFacade = commonDataFacade;
    }

    public FurnitureCodeLinkFacade getFurnitureCodeLinkFacade()
    {
        return furnitureCodeLinkFacade;
    }

    public void setFurnitureCodeLinkFacade(FurnitureCodeLinkFacade furnitureCodeLinkFacade)
    {
        this.furnitureCodeLinkFacade = furnitureCodeLinkFacade;
    }

    public DilerOrderFacade getDilerOrderFacade()
    {
        return dilerOrderFacade;
    }

    public void setDilerOrderFacade(DilerOrderFacade dilerOrderFacade)
    {
        this.dilerOrderFacade = dilerOrderFacade;
    }

    public DilerOrderItemFacade getDilerOrderItemFacade()
    {
        return dilerOrderItemFacade;
    }

    public void setDilerOrderItemFacade(DilerOrderItemFacade dilerOrderItemFacade)
    {
        this.dilerOrderItemFacade = dilerOrderItemFacade;
    }

    public DilerOrderDetailFacade getDilerOrderDetailFacade()
    {
        return dilerOrderDetailFacade;
    }

    public void setDilerOrderDetailFacade(DilerOrderDetailFacade dilerOrderDetailFacade)
    {
        this.dilerOrderDetailFacade = dilerOrderDetailFacade;
    }

    public TemplateOrderFacade getTemplateOrderFacade()
    {
        return templateOrderFacade;
    }

    public void setTemplateOrderFacade(TemplateOrderFacade templateOrderFacade)
    {
        this.templateOrderFacade = templateOrderFacade;
    }

    public CategoryFacade getCategoryFacade()
    {
        return categoryFacade;
    }

    public void setCategoryFacade(CategoryFacade categoryFacade)
    {
        this.categoryFacade = categoryFacade;
    }

    public AutocadFacade getAutocadFacade()
    {
        return autocadFacade;
    }

    public void setAutocadFacade(AutocadFacade autocadFacade)
    {
        this.autocadFacade = autocadFacade;
    }

    public LinearStripsFacade getLinearStripsFacade()
    {
        return linearStripsFacade;
    }

    public void setLinearStripsFacade(LinearStripsFacade linearStripsFacade)
    {
        this.linearStripsFacade = linearStripsFacade;
    }

    public ExceptionHandler getExceptionHandler()
    {
        return exceptionHandler;
    }

    public void setExceptionHandler(ExceptionHandler exceptionHandler)
    {
        this.exceptionHandler = exceptionHandler;
    }

    public DSPPlasticDetailFacade getDspPlasticDetailFacade()
    {
        return dspPlasticDetailFacade;
    }

    public void setDspPlasticDetailFacade(DSPPlasticDetailFacade dspPlasticDetailFacade)
    {
        this.dspPlasticDetailFacade = dspPlasticDetailFacade;
    }

    public DSPPlasticStripsFacade getDspPlasticStripsFacade()
    {
        return dspPlasticStripsFacade;
    }

    public void setDspPlasticStripsFacade(DSPPlasticStripsFacade dspPlasticStripsFacade)
    {
        this.dspPlasticStripsFacade = dspPlasticStripsFacade;
    }

    public TempOrderFacade getTempOrderFacade()
    {
        return tempOrderFacade;
    }

    public void setTempOrderFacade(TempOrderFacade tempOrderFacade)
    {
        this.tempOrderFacade = tempOrderFacade;
    }

    public TempOrderItemFacade getTempOrderItemFacade()
    {
        return tempOrderItemFacade;
    }

    public void setTempOrderItemFacade(TempOrderItemFacade tempOrderItemFacade)
    {
        this.tempOrderItemFacade = tempOrderItemFacade;
    }

    public TempOrderDetailFacade getTempOrderDetailFacade()
    {
        return tempOrderDetailFacade;
    }

    public void setTempOrderDetailFacade(TempOrderDetailFacade tempOrderDetailFacade)
    {
        this.tempOrderDetailFacade = tempOrderDetailFacade;
    }

    public MaterialTypeNodeFactory getMaterialTypeNodeFactory()
    {
        return materialTypeNodeFactory;
    }

    public void setMaterialTypeNodeFactory(MaterialTypeNodeFactory materialTypeNodeFactory)
    {
        this.materialTypeNodeFactory = materialTypeNodeFactory;
    }

    public MaterialTypePanelFactory getMaterialTypePanelFactory()
    {
        return materialTypePanelFactory;
    }

    public void setMaterialTypePanelFactory(MaterialTypePanelFactory materialTypePanelFactory)
    {
        this.materialTypePanelFactory = materialTypePanelFactory;
    }
}
