package by.dak.cutting.swing;

import by.dak.cutting.cut.base.Element;
import by.dak.cutting.cut.guillotine.Strips;
import by.dak.cutting.facade.AOrderBoardDetailFacade;
import by.dak.cutting.swing.cut.CuttingModel;
import by.dak.cutting.swing.order.data.TextureBoardDefPair;
import by.dak.persistence.entities.validator.AResourceValidator;
import com.jgoodies.validation.ValidationResult;

import java.util.List;

/**
 * User: akoyro
 * Date: 21.12.2010
 * Time: 15:44:00
 */
public class CuttingModelValidator extends AResourceValidator<CuttingModel>
{
    private AOrderBoardDetailFacade boardDetailFacade;

    @Override
    public ValidationResult validate(CuttingModel cuttingModel)
    {
        ValidationResult result = new ValidationResult();
//        if (!cuttingModel.isStripsLoaded())
//        {
//            result.addError(getResourceMap().getString("validator.size"));
//            return result;
//        }

        List<TextureBoardDefPair> pairs = cuttingModel.getPairs();
        for (TextureBoardDefPair pair : pairs)
        {
            int count = getBoardDetailFacade().getCountBy(cuttingModel.getOrder(), pair);
            Strips strips = cuttingModel.getStrips(pair);
            if (strips == null)
            {
                result.addError(getResourceMap().getString("validator.size"));
                return result;
            }

            Element[] elements = strips.getCuttedElements();
            if (elements == null || elements.length < 1)
            {
                result.addError(getResourceMap().getString("validator.size"));
                return result;
            }

            if (elements.length != count)
            {
                result.addError(getResourceMap().getString("validator.size"));
                return result;
            }
        }
        return result;
    }

    public AOrderBoardDetailFacade getBoardDetailFacade()
    {
        return boardDetailFacade;
    }

    public void setBoardDetailFacade(AOrderBoardDetailFacade boardDetailFacade)
    {
        this.boardDetailFacade = boardDetailFacade;
    }
}
