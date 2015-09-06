package by.dak.cutting.facade.impl;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.facade.*;
import by.dak.ordergroup.OrderGroup;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.AOrder;
import by.dak.persistence.entities.Attachment;
import by.dak.persistence.entities.OrderItem;
import by.dak.persistence.entities.PersistenceEntity;
import by.dak.repository.IReportFacade;
import by.dak.repository.IRepositoryService;

import java.util.List;

/**
 * User: akoyro
 * Date: 12.04.11
 * Time: 14:56
 */
public abstract class AOrderFacadeImpl<E extends AOrder> extends BaseFacadeImpl<E> implements AOrderFacade<E>
{
    private IRepositoryService repositoryService;
    private IAttachmentService attachmentService;
    private OrderItemFacade orderItemFacade;
    private StorageElementLinkFacade storageElementLinkFacade;
    private BoardFacade boardFacade;
    private IReportFacade reportFacade;

    @Override
    public void delete(E order)
    {
        List<OrderItem> list = FacadeContext.getOrderItemFacade().loadBy(order);
        for (OrderItem orderItem : list)
        {
            orderItemFacade.delete(orderItem);
        }
        storageElementLinkFacade.deleteAllBy(order);
        boardFacade.deleteBy(order);
        reportFacade.removeAll(order);
        super.delete(order.getId());
    }

    @Override
    public void recalculate(E entity)
    {
    }

    @Override
    public List<E> loadAllBy(OrderGroup group)
    {
        SearchFilter searchFilter = SearchFilter.instanceUnbound();
        searchFilter.eq(AOrder.PROPERTY_orderGroup, group);
        searchFilter.addAscOrder(AOrder.PROPERTY_createdDailySheet + "." + PersistenceEntity.PROPERTY_id);
        searchFilter.addAscOrder(AOrder.PROPERTY_orderNumber);
        return loadAll(searchFilter);
    }


    @Override
    public List<Attachment> getAttachment(E order)
    {
        return attachmentService.readAttachments(order);
    }

    public IRepositoryService getRepositoryService()
    {
        return repositoryService;
    }

    public void setRepositoryService(IRepositoryService repositoryService)
    {
        this.repositoryService = repositoryService;
    }

    public IAttachmentService getAttachmentService()
    {
        return attachmentService;
    }

    public void setAttachmentService(IAttachmentService attachmentService)
    {
        this.attachmentService = attachmentService;
    }

    public OrderItemFacade getOrderItemFacade()
    {
        return orderItemFacade;
    }

    public void setOrderItemFacade(OrderItemFacade orderItemFacade)
    {
        this.orderItemFacade = orderItemFacade;
    }

    public StorageElementLinkFacade getStorageElementLinkFacade()
    {
        return storageElementLinkFacade;
    }

    public void setStorageElementLinkFacade(StorageElementLinkFacade storageElementLinkFacade)
    {
        this.storageElementLinkFacade = storageElementLinkFacade;
    }

    public void setBoardFacade(BoardFacade boardFacade)
    {
        this.boardFacade = boardFacade;
    }

    public void setReportFacade(IReportFacade reportFacade)
    {
        this.reportFacade = reportFacade;
    }

    public BoardFacade getBoardFacade()
    {
        return boardFacade;
    }

    public IReportFacade getReportFacade()
    {
        return reportFacade;
    }
}
