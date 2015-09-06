package by.dak.cutting.linear;

import by.dak.persistence.entities.types.FurnitureCode;
import by.dak.persistence.entities.types.FurnitureType;
import by.dak.utils.convert.StringValue;

/**
 * Created by IntelliJ IDEA.
 * User: dkoyro
 * Date: 01.03.11
 * Time: 20:45
 * To change this template use File | Settings | File Templates.
 */

@StringValue(converterClass = FurnitureTypeCodePair2StringConverter.class)
public class FurnitureTypeCodePair
{
    private FurnitureType furnitureType;
    private FurnitureCode furnitureCode;

    public FurnitureTypeCodePair(FurnitureType furnitureType, FurnitureCode furnitureCode)
    {

        this.furnitureType = furnitureType;
        this.furnitureCode = furnitureCode;
    }

    public FurnitureType getFurnitureType()
    {
        return furnitureType;
    }

    public void setFurnitureType(FurnitureType furnitureType)
    {
        this.furnitureType = furnitureType;
    }

    public FurnitureCode getFurnitureCode()
    {
        return furnitureCode;
    }

    public void setFurnitureCode(FurnitureCode furnitureCode)
    {
        this.furnitureCode = furnitureCode;
    }
}
