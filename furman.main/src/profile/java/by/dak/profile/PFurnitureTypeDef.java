package by.dak.profile;

import by.dak.cutting.afacade.ALink;
import by.dak.persistence.entities.types.FurnitureCode;
import by.dak.persistence.entities.types.FurnitureType;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * User: akoyro
 * Date: 25.08.11
 * Time: 14:56
 */

/**
 * Relation of PSystem and FurnitureType
 */

@Entity
@Table(name = "PFurnitureTypeDef")
public class PFurnitureTypeDef extends ALink<PSystem, FurnitureType>
{

    public static final String PROPERTY_code = "code";
    public static final String PROPERTY_type = "type";
    public static final String PROPERTY_usingFormula = "usingFormula";
    public static final String PROPERTY_expressions = "expressions";


    @OneToOne
    private FurnitureCode code;

    private String usingFormula;

    private PExpressions expressions;


    public PFurnitureTypeDef()
    {
        this.expressions = new PExpressions();
    }

    public PExpressions getExpressions()
    {
        return expressions;
    }

    public void setExpressions(PExpressions expressions)
    {
        this.expressions = expressions;
    }

    public FurnitureCode getCode()
    {
        return code;
    }

    public void setCode(FurnitureCode furnitureCode)
    {
        this.code = furnitureCode;
    }


    public FurnitureType getType()
    {
        return getChild();
    }

    public void setType(FurnitureType type)
    {
        setChild(type);
    }

    public String getUsingFormula()
    {
        return usingFormula;
    }

    public void setUsingFormula(String usingFormula)
    {
        this.usingFormula = usingFormula;
    }

}
