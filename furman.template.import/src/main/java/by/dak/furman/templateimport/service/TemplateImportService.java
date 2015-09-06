package by.dak.furman.templateimport.service;

import by.dak.category.facade.CategoryFacade;
import by.dak.cutting.ACodeTypePair;
import by.dak.cutting.facade.*;
import by.dak.cutting.swing.order.data.TextureBoardDefPair;
import by.dak.furman.templateimport.values.*;
import by.dak.persistence.entities.BoardDef;
import by.dak.persistence.entities.BorderDefEntity;
import by.dak.persistence.entities.OrderItem;
import by.dak.persistence.entities.TextureEntity;
import by.dak.repository.IRepositoryService;
import by.dak.template.TemplateFacade;
import by.dak.template.TemplateOrder;
import by.dak.template.facade.ITemplateFacadeFacade;
import by.dak.template.facade.TemplateOrderFacade;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * User: akoyro
 * Date: 10/12/13
 * Time: 7:53 AM
 */
@Service
public class TemplateImportService implements ITemplateImportService
{
    private CategoryFacade categoryFacade;
    private TemplateOrderFacade templateOrderFacade;
    private IRepositoryService repositoryService;
    private OrderItemFacade orderItemFacade;
    private BoardDefFacade boardDefFacade;
    private BorderDefFacade borderDefFacade;
    private OrderFurnitureFacade orderFurnitureFacade;
    private TextureFacade textureFacade;
    private ITemplateFacadeFacade templateFacadeFacade;

    private Map<String, TextureBoardDefPair> boardDefMap;
    private Map<String, ACodeTypePair<TextureEntity, BorderDefEntity>> borderDefMap;


    private <C extends ACategory> void importChildren(C category, by.dak.category.Category cat)
    {
        List<AValue> children = category.getChildren();
        for (AValue child : children)
        {
            if (child instanceof ACategory)
            {
                importCategory0(cat, (ACategory) child, true);
            }
            else if (child instanceof Template)
            {
                importTemplate0(cat, (Template) child);
            }
            else
                throw new IllegalArgumentException();
        }
    }

    private <C extends ACategory> by.dak.category.Category importCategory0(by.dak.category.Category parent, C category, boolean importChildren)
    {
        by.dak.category.Category cat = categoryFacade.findUniqueByField(by.dak.category.Category.PROPERTY_name, category.getName());
        if (cat == null)
            cat = new by.dak.category.Category();
        cat.setName(category.getName());
        cat.setParent(parent);
        categoryFacade.save(cat);

        if (importChildren)
            importChildren(category, cat);
        return cat;
    }

    private <C extends ACategory> by.dak.category.Category importParents(C category)
    {
        C parent = (C) category.getParent();
        by.dak.category.Category pCat = null;
        if (parent != null)
        {
            pCat = importParents(parent);
        }
        return importCategory0(pCat, category, false);
    }

    @Override
    public by.dak.category.Category importCategory(Category category)
    {
        return importCategory0(null, category, true);
    }


    public by.dak.category.Category importTemplateCategory(TemplateCategory category)
    {
        by.dak.category.Category cat = importParents(category);
        return importCategory0(cat.getParent(), category, true);
    }


    private TemplateOrder importTemplate0(by.dak.category.Category cat, Template template)
    {
        TemplateOrder templateOrder = templateOrderFacade.findUniqueByField(TemplateOrder.PROPERTY_name, template.getName());
        if (templateOrder != null)
            templateOrderFacade.delete(templateOrder);
        templateOrder = templateOrderFacade.initNewOrderEntity(template.getName());
        templateOrder.setDescription(template.getDescription().getDescription());
        templateOrder.setCategory(cat);

        String uuid = UUID.randomUUID().toString();
        templateOrder.setFileUuid(uuid);
        repositoryService.store(template.getImage(), uuid);
        templateOrderFacade.save(templateOrder);

        importTemplateItems(templateOrder, template);
        return templateOrder;
    }


    @Override
    public TemplateOrder importTemplate(Template template)
    {
        TemplateCategory category = template.getParent();
        by.dak.category.Category cat = importParents(category);
        return importTemplate0(cat, template);
    }


    public void importTemplateItems(TemplateOrder templateOrder, Template template)
    {
        List<AItem> items = template.getChildren();
        for (int i = 0; i < items.size(); i++)
        {
            AItem aItem = items.get(i);
            if (aItem instanceof BoardItem)
            {
                importBoardItem(templateOrder,
                        i == 0 ? templateOrder.getOrderItems().get(0) : new OrderItem(),
                        template, (BoardItem) aItem);
            }
            else if (aItem instanceof FacadeItem)
            {
                importFacadeItem(templateOrder, template, (FacadeItem) aItem);
            }
        }
    }

    private void importFacadeItem(TemplateOrder templateOrder, Template template, FacadeItem aItem)
    {
        List<BoardDetail> boardDetails = aItem.getChildren();
        for (int i = 0; i < boardDetails.size(); i++)
        {
            try
            {
                BoardDetail boardDetail = boardDetails.get(i);
                TemplateFacade templateFacade = new TemplateFacade();
                templateFacade.setOrder(templateOrder);
                templateFacade.setNumber(new Long(i + 1));
                templateFacade.setName(boardDetail.getCode());
                templateFacade.setLength(boardDetail.getLength());
                templateFacade.setWidth(boardDetail.getWidth());
                templateFacade.setAmount(boardDetail.getAmount());
                templateFacadeFacade.save(templateFacade);
            }
            catch (Exception e)
            {
                template.addMessage("Import error", e);
            }
        }
    }

    private void importBoardItem(TemplateOrder templateOrder, OrderItem orderItem, Template template, BoardItem item)
    {
        BoardItemImport itemImport = new BoardItemImport();
        itemImport.setBoardDefMap(getBoardDefMap());
        itemImport.setBorderDefMap(getBorderDefMap());
        itemImport.setItem(item);
        itemImport.setTemplateOrder(templateOrder);
        //the first order item was created when template order was created
        itemImport.setOrderItem(orderItem);
        itemImport.setOrderItemFacade(orderItemFacade);
        itemImport.setBoardDefFacade(boardDefFacade);
        itemImport.setOrderFurnitureFacade(orderFurnitureFacade);
        itemImport.setTextureFacade(textureFacade);
        itemImport.run();
    }

    public CategoryFacade getCategoryFacade()
    {
        return categoryFacade;
    }

    public void setCategoryFacade(CategoryFacade categoryFacade)
    {
        this.categoryFacade = categoryFacade;
    }

    public TemplateOrderFacade getTemplateOrderFacade()
    {
        return templateOrderFacade;
    }

    public void setTemplateOrderFacade(TemplateOrderFacade templateOrderFacade)
    {
        this.templateOrderFacade = templateOrderFacade;
    }

    public IRepositoryService getRepositoryService()
    {
        return repositoryService;
    }

    public void setRepositoryService(IRepositoryService repositoryService)
    {
        this.repositoryService = repositoryService;
    }

    public OrderItemFacade getOrderItemFacade()
    {
        return orderItemFacade;
    }

    public void setOrderItemFacade(OrderItemFacade orderItemFacade)
    {
        this.orderItemFacade = orderItemFacade;
    }

    public BoardDefFacade getBoardDefFacade()
    {
        return boardDefFacade;
    }

    public void setBoardDefFacade(BoardDefFacade boardDefFacade)
    {
        this.boardDefFacade = boardDefFacade;
    }

    public OrderFurnitureFacade getOrderFurnitureFacade()
    {
        return orderFurnitureFacade;
    }

    public void setOrderFurnitureFacade(OrderFurnitureFacade orderFurnitureFacade)
    {
        this.orderFurnitureFacade = orderFurnitureFacade;
    }

    public TextureFacade getTextureFacade()
    {
        return textureFacade;
    }

    public void setTextureFacade(TextureFacade textureFacade)
    {
        this.textureFacade = textureFacade;
    }

    private Map<String, TextureBoardDefPair> getBoardDefMap()
    {
        if (boardDefMap == null)
        {
            boardDefMap = new HashMap<>();
            BoardDef boardDef = boardDefFacade.findUniqueByField(BoardDef.PROPERTY_name, "ДСП 18");
            TextureEntity texture = textureFacade.getTextureBy(boardDef, "112");
            boardDefMap.put("ДСтП-18мм", new TextureBoardDefPair(texture, boardDef));

            boardDef = boardDefFacade.findUniqueByField(BoardDef.PROPERTY_name, "ДВП");
            texture = textureFacade.getTextureBy(boardDef, "112");
            boardDefMap.put("ДВП-4мм", new TextureBoardDefPair(texture, boardDef));

            boardDef = boardDefFacade.findUniqueByField(BoardDef.PROPERTY_name, "ДСП 16");
            texture = textureFacade.getTextureBy(boardDef, " 112");
            boardDefMap.put("ДСтП-16мм", new TextureBoardDefPair(texture, boardDef));
        }
        return boardDefMap;
    }

    private Map<String, ACodeTypePair<TextureEntity, BorderDefEntity>> getBorderDefMap()
    {
        if (borderDefMap == null)
        {
            borderDefMap = new HashMap<>();

            ACodeTypePair<TextureEntity, BorderDefEntity> pair = new ACodeTypePair<>();
            pair.setType(borderDefFacade.findUniqueByField(BorderDefEntity.PROPERTY_name, "1х22"));
            pair.setCode(textureFacade.findTextureByCode("203"));
            borderDefMap.put("ПВХ – 1мм", pair);

            pair = new ACodeTypePair<>();
            pair.setType(borderDefFacade.findUniqueByField(BorderDefEntity.PROPERTY_name, "2х22"));
            pair.setCode(textureFacade.findTextureByCode("203"));
            borderDefMap.put("ПВХ – 2мм", pair);
        }
        return borderDefMap;
    }

    public BorderDefFacade getBorderDefFacade()
    {
        return borderDefFacade;
    }

    public void setBorderDefFacade(BorderDefFacade borderDefFacade)
    {
        this.borderDefFacade = borderDefFacade;
    }

    public ITemplateFacadeFacade getTemplateFacadeFacade()
    {
        return templateFacadeFacade;
    }

    public void setTemplateFacadeFacade(ITemplateFacadeFacade templateFacadeFacade)
    {
        this.templateFacadeFacade = templateFacadeFacade;
    }
}
