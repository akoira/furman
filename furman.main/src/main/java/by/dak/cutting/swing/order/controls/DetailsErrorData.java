package by.dak.cutting.swing.order.controls;

import by.dak.cutting.configuration.Constants;
import by.dak.cutting.swing.order.OrderDetailsPanel;
import by.dak.cutting.swing.order.OrderTable;
import by.dak.cutting.swing.order.data.OrderDetailsDTO;
import by.dak.cutting.swing.order.models.OrderDetailsTableModel;
import by.dak.cutting.swing.order.models.OrderTableColumn;
import by.dak.persistence.FacadeContext;
import by.dak.utils.validator.ValidatorAnnotationProcessor;
import com.jgoodies.validation.ValidationResult;
import org.apache.commons.lang.StringUtils;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;


public class DetailsErrorData
{

    private OrderTable orderTable1;
    private int wrongColumn;
    private String headerValue;

    private ValidationResult validationResult;
    private ResourceMap resourceMap;


    public DetailsErrorData(OrderTable orderTable1)
    {
        this.orderTable1 = orderTable1;
        this.resourceMap = Application.getInstance().getContext().getResourceMap(OrderDetailsPanel.class);
    }

    public boolean validate(int row)
    {
        validationResult = new ValidationResult();
        if (row == -1)
        {
            return true;
        }
        wrongColumn = getEmptyMandatoryColumn(row);
        if (wrongColumn == -1)
        {
            return true;
        }
        if (StringUtils.isBlank(headerValue))
            headerValue = getHeaderValue(wrongColumn);
        if (!validationResult.hasErrors())
            validationResult.addError(resourceMap.getString("message", headerValue));
        return false;
    }

    private int getEmptyMandatoryColumn(int row)
    {
        OrderDetailsDTO rowData = ((OrderDetailsTableModel) orderTable1.getModel()).getRowBy(row);
        if (StringUtils.isBlank(rowData.getName()))
        {
            return OrderTableColumn.name.index();
        }
        if (rowData.getBoardDef() == null)
        {
            return OrderTableColumn.boardDef.index();
        }

        if (rowData.getTexture() == null)
        {
            return OrderTableColumn.texture.index();
        }
        if (rowData.getLength() == null || !validateDetailLength(rowData) ||
                !validateDetailTrimSize(rowData))
        {
            return OrderTableColumn.length.index();
        }
        if (rowData.getWidth() == null || !validateDetailWidth(rowData) ||
                !validateDetailTrimSize(rowData))
        {
            return OrderTableColumn.width.index();
        }
        if (rowData.getCount() == null || !validateDetailCount(rowData.getCount()))
        {
            return OrderTableColumn.count.index();
        }

        ValidationResult result = ValidatorAnnotationProcessor.getProcessor().validate(rowData.getGlueing());
        if (result.hasErrors())
        {
            validationResult.addAllFrom(result);
            return OrderTableColumn.glueing.index();
        }

        return -1;
    }

    private boolean validateDetailTrimSize(OrderDetailsDTO detail)
    {
        long detailWidth = detail.getWidth();
        long detailLength = detail.getLength();


        if (detail.getBoardDef().getComposite())
        {
            detailWidth += Constants.COMPEXT_FURNITURE_INCREASE;
            detailLength += Constants.COMPEXT_FURNITURE_INCREASE;
        }


        long boardDefWidth = detail.getBoardDef().getDefaultWidth();
        long boardDefLength = detail.getBoardDef().getDefaultLength();

        long trimmedWidth = FacadeContext.getCutterFacade().
                trim(new by.dak.cutting.cut.base.Dimension((int) boardDefWidth, (int) boardDefLength),
                        detail.getBoardDef().getCutter()).getWidth();
        long trimmedLength = FacadeContext.getCutterFacade().
                trim(new by.dak.cutting.cut.base.Dimension((int) boardDefWidth, (int) boardDefLength),
                        detail.getBoardDef().getCutter()).getHeight();

        if (trimmedWidth - detailWidth < 0 || trimmedLength - detailLength < 0)
        {
            validationResult.addError(resourceMap.getString("trim.message"));
            return false;
        }
        return true;
    }

    private String getHeaderValue(int col)
    {
        if (col == -1)
        {
            return "";
        }
        return (String) orderTable1.getColumnModel().getColumn(col).getHeaderValue();
    }

    private boolean validateDetailCount(Integer count)
    {
        if (count > Constants.MAX_COUNT_DETAIL_VALUE)
        {
            validationResult.addError(resourceMap.getString("maximal.message", Constants.MAX_COUNT_DETAIL_VALUE, getHeaderValue(OrderTableColumn.count.index())));
            return false;
        }
        else if (count < Constants.MIN_COUNT_DETAIL_VALUE)
        {
            validationResult.addError(resourceMap.getString("minimal.message", Constants.MIN_COUNT_DETAIL_VALUE, getHeaderValue(OrderTableColumn.count.index())));
            return false;
        }
        return true;
    }

    private boolean validateDetailWidth(OrderDetailsDTO rowData)
    {
        if (rowData.getWidth() < Constants.MIN_DETAIL_WIDTH)
        {
            validationResult.addError(resourceMap.getString("minimal.message", Constants.MIN_DETAIL_WIDTH, getHeaderValue(OrderTableColumn.width.index())));
            return false;
        }
        else
        {
            long maxWidth = rowData.getBoardDef().getDefaultWidth() -
                    (rowData.getBoardDef().getCutter().getCutSizeBottom() + rowData.getBoardDef().getCutter().getCutSizeTop());
            if (rowData.getWidth() > maxWidth)
            {
                validationResult.addError(resourceMap.getString("maximal.message", maxWidth, getHeaderValue(OrderTableColumn.width.index())));
                return false;
            }
        }
        return true;
    }


    private boolean validateDetailLength(OrderDetailsDTO rowData)
    {
        if (rowData.getLength() < Constants.MIN_DETAIL_LENGTH)
        {
            validationResult.addError(resourceMap.getString("minimal.message", Constants.MIN_DETAIL_LENGTH, getHeaderValue(OrderTableColumn.length.index())));
            return false;
        }
        else
        {
            long maxLength = rowData.getBoardDef().getDefaultLength() -
                    (rowData.getBoardDef().getCutter().getCutSizeLeft() + rowData.getBoardDef().getCutter().getCutSizeRight());
            if (rowData.getLength() > maxLength)
            {
                validationResult.addError(resourceMap.getString("maximal.message", maxLength, getHeaderValue(OrderTableColumn.length.index())));
                return false;
            }
        }
        return true;
    }


    private boolean validateMaterialDim(Long matParams, int valueToCompare, int col)
    {
        if (matParams < valueToCompare)
        {
            validationResult.addError(resourceMap.getString("minimal.message", valueToCompare, getHeaderValue(col)));
            return false;
        }
        return true;
    }


    public int getWrongColumn()
    {
        return wrongColumn;
    }


    public String getHeaderValue()
    {
        return headerValue;
    }

    public ValidationResult getValidationResult()
    {
        return validationResult;
    }

}
