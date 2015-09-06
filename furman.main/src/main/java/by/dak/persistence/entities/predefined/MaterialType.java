/*
 * To change this template, choose Tools | Templates
 * and open the template in the draw.
 */
package by.dak.persistence.entities.predefined;

import by.dak.cutting.agt.AGTColor;
import by.dak.cutting.agt.AGTType;
import by.dak.cutting.zfacade.ZProfileColor;
import by.dak.cutting.zfacade.ZProfileType;
import by.dak.lang.Named;
import by.dak.persistence.convert.MaterialType2StringConverter;
import by.dak.persistence.entities.*;
import by.dak.persistence.entities.types.FurnitureCode;
import by.dak.persistence.entities.types.FurnitureType;
import by.dak.utils.convert.StringValue;


/**
 * @author admin
 */
@StringValue(converterClass = MaterialType2StringConverter.class)
public enum MaterialType implements Named
{
    board(BoardDef.class, TextureEntity.class, Board.class), //листовой
    border(BorderDefEntity.class, TextureEntity.class, Border.class), //Кромочный
    furniture(FurnitureType.class, FurnitureCode.class, Furniture.class), //Фурнитура
    zprofile(ZProfileType.class, ZProfileColor.class, Furniture.class), //zprofile
    agtprofile(AGTType.class, AGTColor.class, Furniture.class); //agtprofile

    private MaterialType(Class<? extends PriceAware> priceAwareClass, Class<? extends Priced> pricedClass, Class<? extends AStoreElement> storeElementClass)
    {
        this.priceAwareClass = priceAwareClass;
        this.pricedClass = pricedClass;
        this.storeElementClass = storeElementClass;
    }

    private Class<? extends PriceAware> priceAwareClass;
    private Class<? extends Priced> pricedClass;
    private Class<? extends AStoreElement> storeElementClass;

    public static MaterialType valueByStoreElementClass(Class<? extends AStoreElement> storeElementClass)
    {
        for (MaterialType value : values())
        {
            if (value.storeElementClass == storeElementClass)
            {
                return value;
            }
        }
        throw new IllegalArgumentException();
    }

    public static MaterialType valueOf(Class<? extends PriceAware> priceAwareClass)
    {
        for (MaterialType value : values())
        {
            if (value.priceAwareClass() == priceAwareClass)
            {
                return value;
            }
        }
        throw new IllegalArgumentException();
    }

    public Class<? extends PriceAware> priceAwareClass()
    {
        return priceAwareClass;
    }

    public Class<? extends Priced> pricedClass()
    {
        return pricedClass;
    }

    public static MaterialType[] parentTypes()
    {
        return new MaterialType[]{board, border, furniture};
    }

}
