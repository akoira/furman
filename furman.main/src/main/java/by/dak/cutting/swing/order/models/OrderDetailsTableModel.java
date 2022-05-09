package by.dak.cutting.swing.order.models;


import by.dak.cutting.configuration.Constants;
import by.dak.cutting.swing.order.OrderDetailsPanel;
import by.dak.cutting.swing.order.controls.OrderDetailsDTOConverter;
import by.dak.cutting.swing.order.data.Cutoff;
import by.dak.cutting.swing.order.data.Milling;
import by.dak.cutting.swing.order.data.OrderDetailsDTO;
import by.dak.cutting.swing.xml.XstreamHelper;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.*;
import by.dak.utils.validator.ValidationUtils;
import org.apache.commons.lang.StringUtils;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailsTableModel extends AbstractTableModel
{
    private List<OrderDetailsDTO> data;
    private OrderDetailsDTO emptyDto;
    private OrderItem orderItem;

    private BoardDef defaultBoardDef;

    private ResourceMap resourceMap = Application.getInstance().getContext().getResourceMap(OrderDetailsPanel.class);

    public OrderDetailsTableModel()
    {
        this.data = new ArrayList<>();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        OrderTableColumn column = OrderTableColumn.valuerOf(columnIndex);
        switch (column)
        {
            case rotatable:
                return Boolean.class;
            default:
                return super.getColumnClass(columnIndex);
        }
    }

    public int getRowCount()
    {
        return getEmptyDto() != null ? data.size() + 1 : data.size();
    }

    public int getColumnCount()
    {
        return OrderTableColumn.values().length;
    }

    public Object getValueAt(int rowIndex, int columnIndex)
    {
        if (rowIndex < 0 || rowIndex >= getRowCount())
        {
            return "";
        }
        OrderDetailsDTO dto = getRowBy(rowIndex);
        OrderTableColumn column = OrderTableColumn.valuerOf(columnIndex);
        switch (column)
        {
            case number:
                return rowIndex + 1;
            case name:
                return dto.getName();
            case boardDef:
                return dto.getBoardDef();
            case manufacturer:
                return dto.getManufacturer();
            case texture:
                return dto.getTexture();
            case length:
                return dto.getLength();
            case width:
                return dto.getWidth();
            case count:
                return dto.getCount();
            case rotatable:
                return dto.isRotatable() || (dto.getTexture() != null && dto.getTexture().isRotatable());
            case glueing:
                return dto.getGlueing();
            case milling:
                return dto.getMilling();
            case drilling:
                return dto.getDrilling();
            case groove:
                return dto.getGroove();
            case a45:
                return dto.getA45();
            case cutoff:
                return dto.getCutoff();
            default:
                throw new IllegalArgumentException();
        }
    }


    public OrderDetailsDTO getRowBy(int rowIndex)
    {
        OrderDetailsDTO dto;
        if (rowIndex < this.data.size())
        {
            dto = this.data.get(rowIndex);
        }
        else
        {
            dto = getEmptyDto();
        }
        return dto;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
        if (rowIndex < 0 || rowIndex >= getRowCount())
        {
            return;
        }
        OrderDetailsDTO dto = getRowBy(rowIndex);
        OrderTableColumn column = OrderTableColumn.valuerOf(columnIndex);
        boolean changed = false;
        switch (column)
        {
            case number:
                dto.setNumber((long) rowIndex + 1);
                break;
            case name:
                dto.setName((String) aValue);
                break;
            case boardDef:
                //очищаем текстура когда меняем тип
                dto.setTexture(null);
                dto.setSecondNumber(null);
                if (aValue != null)
                {
                    dto.setBoardDef((BoardDef) aValue);
                    if (((BoardDef) aValue).getComposite())
                    {
                        dto.setSecondNumber(dto.getNumber());
                    }
                }
                break;
            case manufacturer:
                dto.setManufacturer((Manufacturer) aValue);
                break;
            case texture:
                dto.setTexture((TextureEntity) aValue);
                dto.setManufacturer(aValue != null ? ((TextureEntity) aValue).getManufacturer() : null);
                break;
            case length:
                dto.setLength(!ValidationUtils.isEmpty((String) aValue) ? Long.valueOf((String) aValue) : null);
                updateDependences(dto);
                break;
            case width:
                dto.setWidth(!ValidationUtils.isEmpty((String) aValue) ? Long.valueOf((String) aValue) : null);
                updateDependences(dto);
                break;
            case count:
                dto.setCount(!ValidationUtils.isEmpty((String) aValue) ? Integer.valueOf((String) aValue) : null);
                break;
            case rotatable:
                dto.setRotatable(Boolean.valueOf((Boolean) aValue));
                break;
            case milling:
                dto.setMilling((String) aValue);
                break;
            case glueing:
                //dto.setGlueing( aValue);
                break;
            case drilling:
                break;
            case groove:
                break;
            case a45:
                break;
            case cutoff:
                dto.setCutoff((Cutoff) aValue);
                break;
        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }


    public void updateDependences(OrderDetailsDTO orderDetails)
    {
        if (orderDetails.getMilling() != null)
        {
            Milling milling = (Milling) XstreamHelper.getInstance().fromXML(orderDetails.getMilling());
            FacadeContext.getRepositoryService().delete(milling.getFileUuid());
            FacadeContext.getRepositoryService().delete(milling.getImageFileUuid());
            orderDetails.setMilling(null);
        }
    }

    @Override
    public String getColumnName(int column)
    {
        return resourceMap.getString(OrderTableColumn.valuerOf(column).name());
    }

    public List<OrderDetailsDTO> getData()
    {
        return this.data;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return columnIndex != 0;
    }


    public OrderDetailsDTO createNewRow(OrderDetailsDTO dto)
    {
        OrderDetailsDTO newDto = createDTO();
        if (dto != null)
        {
            newDto.setNumber((long) getRowCount() + 1);
            newDto.setName(dto.getName());
            newDto.setTexture(dto.getTexture());
            newDto.setBoardDef(dto.getBoardDef());
            newDto.setManufacturer(dto.getTexture().getManufacturer());
        }
        return newDto;
    }

    public void setEmptyRow(OrderDetailsDTO emptyDto)
    {
        this.emptyDto = emptyDto;
        fireTableRowsInserted(getRowCount() - 1, getRowCount() - 1);
    }

    public void addRow(OrderDetailsDTO newRow)
    {
        this.data.add(newRow);
        fireTableRowsUpdated(data.size() - 1, data.size() - 1);
    }

    private OrderDetailsDTO createDTO()
    {
        OrderDetailsDTO dto = new OrderDetailsDTO();
        dto.setName(Constants.DEFAULT_DETAIL_NAME);
        dto.setBoardDef(getDefaultBoardDef());
        dto.setLength(0l);
        dto.setWidth(0l);
        dto.setCount(0);
        dto.setRotatable(false);
        return dto;
    }

    public boolean delete(int nRow)
    {
        this.data.remove(nRow);
        fireTableRowsDeleted(nRow, nRow);
        return true;
    }

    /**
     * @param orderItem the orderEntity to set
     */
    public void setOrder(OrderItem orderItem)
    {
        clear();
        this.orderItem = orderItem;
        List<OrderFurniture> furnList = FacadeContext.getOrderFurnitureFacade().loadOrderedByNumber(getOrderItem());
        for (OrderFurniture furn : furnList)
        {
            OrderDetailsDTO dto;
            if (furn.isComplex())
            {
                if (furn.isPrimary())
                {
                    dto = new OrderDetailsDTOConverter(furn).getDTO();
                    data.add(dto);
                }
            }
            else
            {
                dto = new OrderDetailsDTOConverter(furn).getDTO();
                data.add(dto);
            }

        }

        if (!isEmptyRows())
        {
            OrderDetailsDTO dto = createNewRow(null);
            emptyDto = dto;
        }
        fireTableDataChanged();
    }

    /**
     * todo не имользуется больше
     *
     * @deprecated
     */
    private boolean containDTOFor(OrderFurniture furn)
    {
        for (int i = data.size() - 1; i >= 0; i--)
        {
            OrderDetailsDTO dto = data.get(i);
            assert dto.getOrderFurnitureEntity() != null;
            if (dto.getOrderFurnitureEntity().getSecond() != null &&
                    dto.getOrderFurnitureEntity().getSecond().getId().equals(furn.getId()))
            {
                return true;
            }
        }
        return false;
    }


    public boolean isRowCompleted(int row)
    {
        OrderDetailsDTO rowData = data.get(row);
        return !StringUtils.isBlank(rowData.getName()) &&
                rowData.getTexture() != null &&
                rowData.getBoardDef() != null &&
                (rowData.getWidth() != null && rowData.getWidth() > 0) &&
                (rowData.getLength() != null && rowData.getWidth() > 0) &&
                (rowData.getCount() != null && rowData.getCount() > 0);
    }


    public boolean isEmptyRows()
    {
        return getEmptyDto() != null;
    }


    public OrderItem getOrderItem()
    {
        return orderItem;
    }

    public BoardDef getDefaultBoardDef()
    {
        if (defaultBoardDef == null)
        {
            defaultBoardDef = FacadeContext.getBoardDefFacade().findDefaultBoardDef();
        }
        return defaultBoardDef;
    }

    private void clear()
    {
        orderItem = null;
        data.clear();
    }

    public OrderDetailsDTO getEmptyDto()
    {
        return emptyDto;
    }
}
