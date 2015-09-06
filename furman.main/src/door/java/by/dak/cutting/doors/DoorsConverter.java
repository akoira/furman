package by.dak.cutting.doors;

import by.dak.persistence.entities.OrderFurniture;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: vishutinov
 * Date: 24.08.2009
 * Time: 10:39:57
 * To change this template use File | Settings | File Templates.
 */
public class DoorsConverter
{
    public static List<OrderFurniture> convertToOrderFurnitureEntity(Doors doors)
    {
        List<OrderFurniture> list = new ArrayList<OrderFurniture>();

        for (Door door : doors.getDoors())
        {
//            for (Cell cell : door.getDoorDrawing().getCells())
//            {
//                OrderFurniture furnitureEntity = new OrderFurniture();
//                //todo bundles
//                furnitureEntity.setName(new String("Door " + doors.getDoors().indexOf(door)));
//                furnitureEntity.setAmount(1L);
//                furnitureEntity.setBorderDef(cell.getBoardDefEntity());
//                furnitureEntity.setTexture(cell.getTextureEntity());
//                furnitureEntity.setWidth((long) cell.getCell().getElement().getWidth());
//                furnitureEntity.setLength((long) cell.getCell().getElement().getHeight());
//                furnitureEntity.setMilling(new MillingConverter().save(cell.getCell()));
//            }
        }

        return list;
    }
}
