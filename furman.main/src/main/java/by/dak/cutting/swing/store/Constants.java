package by.dak.cutting.swing.store;

import by.dak.persistence.entities.*;
import by.dak.swing.APropertyEditorCreator;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * User: akoyro
 * Date: 20.11.2009
 * Time: 17:32:40
 */
public class Constants
{
    private static String SUFFIX_EditorVisibleProperties = "EditorVisibleProperties";

    public static final String[] DesignerTableVisibleProperties = new String[]{
            "name"
    };

    public static final String[] CutterEditorVisibleProperties = new String[]{
            "name",
            "sawcutWidth",
            "cutSizeLeft",
            "cutSizeRight",
            "cutSizeBottom",
            "cutSizeTop",
            "direction"
    };

    public static final String[] CutterTableVisibleProperties = new String[]{
            "name",
            "sawcutWidth",
            "cutSizeLeft",
            "cutSizeRight",
            "cutSizeBottom",
            "cutSizeTop",
            "direction"
    };


    public static final String[] CustomerTableVisibleProperties = new String[]{
            "name",
            "address",
            "phoneNumber",
            "phoneNumber1",
            "faxNumber",
            "emailAddress",
            "deleted",
            "discount"
    };

    public static final String[] DeliveryTableVisibleProperties = new String[]{
            "deliveryDate",
            "provider",
            "materialType"
    };

    public static final String[] EmployeeEntityEditorVisibleProperties = new String[]{
            "name",
            "surname",
            "department",
    };

    public static final String[] OrderGroupEditorVisibleProperties = new String[]{
            "name"
    };

    public static final String[] FurnitureEditorVisibleProperties = new String[]{
            "furnitureType",
            "furnitureCode",
            "provider",
            "amount",
            "price",
            "status",
            "size",
            "unit",
    };

    public static final String[] DeliveryEditorVisibleProperties = new String[]{
            "deliveryDate",
            "provider"
    };

    public static final String[] OrderedFurnitureTableVisibleProperties = new String[]{
            "ordered",
            Furniture.PROPERTY_priceAware,
            Furniture.PROPERTY_priced,
            "provider",
            "size",
            "amount",
            "priceAware.unit",
            "order.number",
            "order.readyDate",
            "status",
    };


    public static final String[] FurnitureTableVisibleProperties = new String[]{
            Furniture.PROPERTY_priceAware,
            Furniture.PROPERTY_priced,
            "provider",
            "size",
            "amount",
            "priceAware.unit",
            "order.number",
            "order.readyDate",
            "status",
    };

    public static final String[] FurnitureTypeEditorVisibleProperties = new String[]{
            "name",
            "unit",
            "defaultSize",
            "cutter"
    };

    public static final String[] FurnitureTypeTableVisibleProperties = new String[]{
            "name",
            "unit",
            "defaultSize"
    };

    public static final String[] BOARD_DEF_TABLE_VISIBLE_PROPERTIES = new String[]{
            BoardDef.PROPERTY_id,
            BoardDef.PROPERTY_name,
            BoardDef.PROPERTY_thickness,
            BoardDef.PROPERTY_defaultLength,
            BoardDef.PROPERTY_defaultWidth,
            BoardDef.PROPERTY_reservedLength,
            BoardDef.PROPERTY_reservedWidth
    };

    public static final String[] BORDER_DEF_TABLE_VISIBLE_PROPERTIES = new String[]{
            "name",
            "thickness",
            "height",
    };

    public static final String[] BOARD_TABLE_VISIBLE_PROPERTIES = new String[]{
            Board.PROPERTY_priceAware,
            Board.PROPERTY_priced,
            "provider",
            "length",
            "width",
            "amount",
            "order.number",
            "order.readyDate",
            "status",
    };

    public static final String[] ORDER_BOARD_TABLE_VISIBLE_PROPERTIES = new String[]{
            "ordered",
            Board.PROPERTY_priceAware,
            Board.PROPERTY_priced,
            "provider",
            "length",
            "width",
            "amount",
            "order.number",
            "order.readyDate",
            "status",
    };


    public static final String[] BoardEditorVisibleProperties = new String[]{
            "boardDef",
            "texture",
            "provider",
            "length",
            "width",
            "amount",
            "price",
            "status",
    };


    public static final String[] BORDER_TABLE_VISIBLE_PROPERTIES = new String[]{
            Border.PROPERTY_priceAware,
            Border.PROPERTY_priced,
            "provider",
            "length",
            "amount",
            "order.number",
            "order.readyDate",
            "status"
    };

    public static final String[] ORDERED_BORDER_TABLE_VISIBLE_PROPERTIES = new String[]{
            "ordered",
            Border.PROPERTY_priceAware,
            Border.PROPERTY_priced,
            "provider",
            "length",
            "amount",
            "order.number",
            "order.readyDate",
            "status"
    };

    public static final String[] BorderEditorVisibleProperties = new String[]{
            Border.PROPERTY_borderDef,
            Border.PROPERTY_texture,
            "provider",
            "length",
            "amount",
            "price",
            "status"
    };

    public static final String[] SERVICE_TABLE_VISIBLE_PROPERTIES = new String[]{
            "serviceType",
    };

    public static final String[] PROVIDER_TABLE_VISIBLE_PROPERTIES = new String[]{
            "name",
            "shortName",
            "address",
            "phoneNumber",
            "faxNumber",
            "emailAddress",
            "officialSite"
    };

    public static final String[] EmployeeTableVisibleProperties = new String[]{
            "name",
            "surname",
            "department",
    };

    public static final String[] EmployeeEditorVisibleProperties = new String[]{
            "userName",
            "password",
            "name",
            "surname",
            "department",
    };

    public static final String[] ProviderEditorVisibleProperties = new String[]{
            "name",
            "shortName",
            "address",
            "phoneNumber",
            "faxNumber",
            "emailAddress",
            "officialSite"
    };

    public static final String[] MANUFACTURER_TABLE_VISIBLE_PROPERTIES = new String[]{
            "name",
            "shortName",
            "address",
            "phoneNumber",
            "faxNumber",
            "emailAddress",
            "officialSite"
    };

    public static final String[] ManufacturerEditorVisibleProperties = new String[]{
            "name",
            "shortName",
            "address",
            "phoneNumber",
            "faxNumber",
            "emailAddress",
            "officialSite"
    };


    public static final String[] OrderItemEditorVisibleProperties = new String[]{
            "name",
    };

    public static final String[] OrderTableVisibleProperties = new String[]{
            "orderNumber",
            "name",
            "createdDailySheet",
            "readyDate",
            "customer",
            "designer",
            "orderStatus"
    };

    public static final String[] PRICEAWARE_TABLE_VISIBLE_PROPERTIES = new String[]{
            PriceEntity.PROPERTY_priced,
            PriceEntity.PROPERTY_priced + ".manufacturer",
            PriceEntity.PROPERTY_price,
            PriceEntity.PROPERTY_currencyType,
            PriceEntity.PROPERTY_priceFaktor,
            PriceEntity.PROPERTY_priceDealer,
            PriceEntity.PROPERTY_priceSaleFaktor,
            PriceEntity.PROPERTY_priceSale,
    };


    public static final String[] PRICED_TABLE_VISIBLE_PROPERTIES = new String[]{
            PriceEntity.PROPERTY_priceAware,
            PriceEntity.PROPERTY_price,
            PriceEntity.PROPERTY_currencyType,
            PriceEntity.PROPERTY_priceFaktor,
            PriceEntity.PROPERTY_priceDealer,
            PriceEntity.PROPERTY_priceSaleFaktor,
            PriceEntity.PROPERTY_priceSale,
    };

    public static final String[] PRICE_TABLE_EDITABLE_PROPERTIES = new String[]{
            PriceEntity.PROPERTY_price,
            PriceEntity.PROPERTY_currencyType,
            PriceEntity.PROPERTY_priceFaktor,
            PriceEntity.PROPERTY_priceDealer,
            PriceEntity.PROPERTY_priceSaleFaktor,
            PriceEntity.PROPERTY_priceSale,
    };


    public static final String[] BoardDefEditorVisibleProperties = new String[]{
            BoardDef.PROPERTY_name,
            BoardDef.PROPERTY_thickness,
            BoardDef.PROPERTY_defaultLength,
            BoardDef.PROPERTY_defaultWidth,
            BoardDef.PROPERTY_reservedLength,
            BoardDef.PROPERTY_reservedWidth,
            BoardDef.PROPERTY_composite,
            BoardDef.PROPERTY_unit,
            BoardDef.PROPERTY_cutter
    };

    public static final String[] ZProfileTypeEditorVisibleProperties = new String[]{
            "name",
            "offsetLength",
            "offsetWidth",
            "angleType",
            "rubberType",
            "buttType",
            "defaultSize",
            "cutter"
    };


    public static final String[] ZFacadeEditorVisibleProperties = new String[]
            {
                    "name",
                    "furnitureType",
                    "furnitureColor",
                    "length",
                    "width",
                    "amount"
            };

    public static final String[] OrderFurnitureEditorVisibleProperties = new String[]{
            OrderFurniture.PROPERTY_boardDef,
            OrderFurniture.PROPERTY_texture,
            OrderFurniture.PROPERTY_rotatable,
    };

    public static final String[] FurnitureLinkEditorVisibleProperties = new String[]{
            FurnitureLink.PROPERTY_furnitureType,
            FurnitureLink.PROPERTY_furnitureCode
    };

    public static final String[] AGTTypeEditorVisibleProperties = new String[]{
            "name",
            "offsetLength",
            "offsetWidth",
            "dowelType",
            "glueType",
            "rubberType",
            "defaultSize",
            "cutter"
    };

    public static final String[] DilerImportFileEditorVisibleProperties = new String[]{
            "selectedFile",
            "customer"
    };
    public static final String[] DilerOrderFilterEditorVisibleProperties = new String[]{
            "customer",
            "start",
            "end"
    };
    public static final String[] DilerOrderTableEditableProperties = new String[]{
            "id",
            "name",
            "description",
            "cost",
            "status",
            "readyDate"
    };

    public static final String[] DilerOrderTableHiddenProperties = new String[]{
            "id",
    };

    public static String[] getEntityEditorVisibleProperties(Class entityClass)
    {
        String entityName = entityClass.getSimpleName();
        try
        {
            Field field = Constants.class.getField(entityName + SUFFIX_EditorVisibleProperties);
            return (String[]) field.get(null);
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    public static HashMap<Class, APropertyEditorCreator> getEntityEditorCreators(Class entityClass)
    {
        return new HashMap<Class, APropertyEditorCreator>(EditorCreators.EDITOR_CREATORS);
    }

    public static APropertyEditorCreator getPropertyEditorCreator(Class valueAClass)
    {
        return EditorCreators.EDITOR_CREATORS.get(valueAClass);
    }
}
