package by.dak.persistence.entities;

import by.dak.cutting.configuration.Constants;
import by.dak.cutting.facade.BaseFacade;
import by.dak.persistence.FacadeContext;
import org.apache.commons.lang.math.RandomUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author admin
 * @version 0.1 26.01.2009
 * @introduced [Preview Plugin Support]
 * @since 2.0.0
 */
public class TestStructureGenerator
{
    private HashMap<Class<? extends PersistenceEntity>, List<? extends PersistenceEntity>> map = new HashMap<Class<? extends PersistenceEntity>, List<? extends PersistenceEntity>>();
    private ArrayList<PersistenceEntity> persistenceEntities = new ArrayList<PersistenceEntity>();

    public TestStructureGenerator()
    {
        super();
    }

    private <E extends PersistenceEntity> E put(E entity)
    {
        List<E> entities = (List<E>) map.get(entity.getClass());
        if (entities == null)
        {
            entities = new ArrayList<E>();
            map.put(entity.getClass(), entities);
        }
        entities.add(0, entity);
        persistenceEntities.add(0, entity);
        return entity;
    }


    public <E extends PersistenceEntity> List<E> get(Class<E> entityClass)
    {
        return (List<E>) map.get(entityClass);
    }

    public <E extends PersistenceEntity> E write(BaseFacade<E> facade, E entity)
    {
        facade.save(entity);
        put(entity);
        return entity;
    }

    public DesignerEntity getDesignerEntity() throws Exception
    {
        return write(FacadeContext.getDesignerFacade(),
                TestEntityGenerator.getTestEntityGenerator(DesignerEntity.class).generate());
    }

    public DepartmentEntity getDepartmentEntity() throws Exception
    {
        return write(FacadeContext.getDepartmentFacade(),
                TestEntityGenerator.getTestEntityGenerator(DepartmentEntity.class).generate());
    }


    public Employee getEmployeeEntity(DepartmentEntity departmentEntity) throws Exception
    {
        Employee employee = TestEntityGenerator.getTestEntityGenerator(Employee.class).generate();
        employee.setDepartment(departmentEntity);
        return write(FacadeContext.getEmployeeFacade(),
                employee);
    }

    public ShiftEntity getShiftEntity(DepartmentEntity departmentEntity) throws Exception
    {
        ShiftEntity shiftEntity = TestEntityGenerator.getTestEntityGenerator(ShiftEntity.class).generate();
        shiftEntity.setDepartment(departmentEntity);
        return write(FacadeContext.getShiftFacade(),
                shiftEntity);
    }

    public TextureEntity getTextureEntity(Manufacturer manufacturer) throws Exception
    {
        TextureEntity textureEntity = TestEntityGenerator.getTestEntityGenerator(TextureEntity.class).generate();
        textureEntity.setManufacturer(manufacturer);
        return write(FacadeContext.getTextureFacade(),
                textureEntity);
    }

    public Customer getCustomerEntity() throws Exception
    {
        return write(FacadeContext.getCustomerFacade(),
                TestEntityGenerator.getTestEntityGenerator(Customer.class).generate());
    }

    public Dailysheet getDailysheetEntity(Employee employee, ShiftEntity[] shiftEntities) throws Exception
    {
        Dailysheet dailysheet = TestEntityGenerator.getTestEntityGenerator(Dailysheet.class).generate();
        dailysheet.setEmployee(employee);
        for (ShiftEntity shiftEntity : shiftEntities)
        {
            dailysheet.addShift(shiftEntity);
        }
        return write(FacadeContext.getDailysheetFacade(),
                dailysheet);
    }

    public Board getFeedStockEntity(TextureEntity textureEntity, BoardDef boardDef) throws Exception
    {
        Board feedStockEntity = TestEntityGenerator.getTestEntityGenerator(Board.class).generate();
        feedStockEntity.setTexture(textureEntity);
        feedStockEntity.setBoardDef(boardDef);
        return write(FacadeContext.getBoardFacade(),
                feedStockEntity);
    }

    public Manufacturer getManufacturerEntity() throws Exception
    {
        return write(FacadeContext.getManufacturerFacade(),
                TestEntityGenerator.getTestEntityGenerator(Manufacturer.class).generate());
    }

    public Order getOrderEntity(Dailysheet dailysheet,
                                Customer customer,
                                DesignerEntity designerEntity, OrderItem orderItem) throws Exception
    {
        Order order = TestEntityGenerator.getTestEntityGenerator(Order.class).generate();
        //todo refactiring Order
        order.setCreatedDailySheet(dailysheet);
        order.setCustomer(customer);
        order.setDesigner(designerEntity);
        order.addOrderItem(orderItem);
        return write(FacadeContext.getOrderFacade(),
                order);
    }

    public OrderItem getOrderItemEntity() throws Exception
    {
        OrderItem orderItem = TestEntityGenerator.getTestEntityGenerator(OrderItem.class).generate();
        return write(FacadeContext.getOrderItemFacade(),
                orderItem);
    }

    public OrderFurniture getOrderFurnitureEntity(OrderItem orderItem, TextureEntity textureEntity,
                                                  BoardDef boardDef) throws Exception
    {
        OrderFurniture orderFurniture = TestEntityGenerator.getTestEntityGenerator(OrderFurniture.class).generate();
        orderFurniture.setTexture(textureEntity);
        orderFurniture.setBoardDef(boardDef);
        orderFurniture.setOrderItem(orderItem);
        orderFurniture.setPrimary(true);
        orderFurniture.setLength(500L);
        orderFurniture.setWidth(350L);
        orderFurniture.setAmount(20);
        return write(FacadeContext.getOrderFurnitureFacade(), orderFurniture);
    }

    public BoardDef getBoardDefEntity() throws Exception
    {
        BoardDef entity = new BoardDef();
        entity.setDefaultLength(Constants.DEFAULT_SHEET_LENGTH);
        entity.setDefaultWidth(Constants.DEFAULT_SHEET_WIDTH);
        entity.setName("BoardDef" + RandomUtils.nextInt());
        entity.setThickness(18L);
        return write(FacadeContext.getBoardDefFacade(), entity);
//        return (BoardDef) write(FacadeContext.getBoardDefFacade(),
//
//
//                (BoardDef) TestEntityGenerator.getTestEntityGenerator(BoardDef.class).generate());
    }

//    public static void main(String[] args) throws Exception
//    {
//        JFrame jFrame = new JFrame();
//        ArrayList<PersistenceEntityImpl> arrayList = new ArrayList<PersistenceEntityImpl>();
//
//        try
//        {
//            createStructure(jFrame, arrayList);
//        }
//        finally
//        {
//            clear(arrayList);
//        }
//
//    }

    public void createStructure()
            throws Exception
    {

        DepartmentEntity departmentEntity = getDepartmentEntity();

        Employee employee = getEmployeeEntity(departmentEntity);

        ShiftEntity shiftEntity = getShiftEntity(departmentEntity);

        DesignerEntity designerEntity = getDesignerEntity();

        Customer customer = getCustomerEntity();

        Manufacturer manufacturer = getManufacturerEntity();

        TextureEntity[] textureEntities = new TextureEntity[1];
        for (int i = 0; i < textureEntities.length; i++)
        {
            textureEntities[i] = getTextureEntity(manufacturer);
        }

        BoardDef[] defs = new BoardDef[1];
        for (int i = 0; i < defs.length; i++)
        {
            defs[i] = getBoardDefEntity();
        }


        Dailysheet dailysheet = getDailysheetEntity(employee, new ShiftEntity[]{shiftEntity});


        OrderItem orderItem = getOrderItemEntity();

        Order order = getOrderEntity(dailysheet, customer, designerEntity, orderItem);

        OrderFurniture[] furnitures = new OrderFurniture[1];
        for (int i = 0; i < furnitures.length; i++)
        {
            furnitures[i] = getOrderFurnitureEntity(orderItem, textureEntities[0], defs[0]);
        }
    }


    public void clear()
    {
        for (final PersistenceEntity persistenceEntity : persistenceEntities)
        {
            if (persistenceEntity != null)
            {
                try
                {
                    if (persistenceEntity instanceof DesignerEntity)
                        FacadeContext.getDesignerFacade().delete((DesignerEntity) persistenceEntity);
                    if (persistenceEntity instanceof DepartmentEntity)
                        FacadeContext.getDepartmentFacade().delete((DepartmentEntity) persistenceEntity);
                    if (persistenceEntity instanceof BoardDef)
                        FacadeContext.getBoardDefFacade().delete((BoardDef) persistenceEntity);
                    if (persistenceEntity instanceof Dailysheet)
                        FacadeContext.getDailysheetFacade().delete((Dailysheet) persistenceEntity);
                    if (persistenceEntity instanceof Customer)
                        FacadeContext.getCustomerFacade().delete((Customer) persistenceEntity);
                    if (persistenceEntity instanceof Employee)
                        FacadeContext.getEmployeeFacade().delete((Employee) persistenceEntity);
                    if (persistenceEntity instanceof Board)
                        FacadeContext.getBoardFacade().delete((Board) persistenceEntity);
                    if (persistenceEntity instanceof Manufacturer)
                        FacadeContext.getManufacturerFacade().delete((Manufacturer) persistenceEntity);
                    if (persistenceEntity instanceof Order)
                        FacadeContext.getOrderFacade().delete((Order) persistenceEntity);
                    if (persistenceEntity instanceof OrderFurniture)
                        FacadeContext.getOrderFurnitureFacade().delete((OrderFurniture) persistenceEntity);
                    if (persistenceEntity instanceof OrderItem)
                        FacadeContext.getOrderItemFacade().delete((OrderItem) persistenceEntity);
                    if (persistenceEntity instanceof ShiftEntity)
                        FacadeContext.getShiftFacade().delete((ShiftEntity) persistenceEntity);
                    if (persistenceEntity instanceof TextureEntity)
                        FacadeContext.getTextureFacade().delete((TextureEntity) persistenceEntity);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                finally
                {
                }
            }
        }
    }
}
