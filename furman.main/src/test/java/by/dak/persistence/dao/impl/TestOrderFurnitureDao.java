package by.dak.persistence.dao.impl;

import by.dak.persistence.entities.TestStructureGenerator;
import org.junit.Test;

/**
 * @author admin
 * @version 0.1 25.01.2009
 * @introduced [Preview Plugin Support]
 * @since 2.0.0
 */
public class TestOrderFurnitureDao
{

    @Test
    public void findBy_order_texture_definition() throws Exception
    {
        final TestStructureGenerator testStructureGenerator = new TestStructureGenerator();
        try
        {
            testStructureGenerator.createStructure();

//            DBExecuter<List<OrderFurniture>> dbExecuter = new DBExecuter<List<OrderFurniture>>(new JPanel())
//            {
//                @Override
//                protected List<OrderFurniture> executeInternal()
//                {
//                    OrderDaoImpl orderDao = new OrderDaoImpl();
//                    List<Order> ordersP = orderDao.findAll();
//
//                    TextureDaoImpl textureDao = new TextureDaoImpl();
//                    List<Texture> texturesP = textureDao.findAll();
//
//                    BoardDefDaoImpl furnitureDefDao = new BoardDefDaoImpl();
//                    List<BoardDef> boardDefsP = furnitureDefDao.findBoardDefs();
//
//                    OrderFurnitureDaoImpl orderFurnitureDao = new OrderFurnitureDaoImpl();
//
//                    List<TextureEntity> textures = testStructureGenerator.get(TextureEntity.class);
//                    List<BoardDef> boardDefs = testStructureGenerator.get(BoardDef.class);
//                    List<Order> orders = testStructureGenerator.get(Order.class);
//                    List<OrderFurniture> orderFurnitures = orderFurnitureDao.findOrderedByNumber(orders.get(0),textures.get(0),boardDefs.get(0));
//                    return orderFurnitures;
//                }
//            };
//            dbExecuter.execute();
        }
        finally
        {
            testStructureGenerator.clear();
        }


    }

}
