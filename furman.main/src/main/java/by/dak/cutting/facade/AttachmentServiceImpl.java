package by.dak.cutting.facade;

import by.dak.persistence.entities.Attachment;
import by.dak.persistence.entities.PersistenceEntity;
import by.dak.repository.IRepositoryService;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.io.File;
import java.util.List;
import java.util.UUID;

/**
 * User: akoyro
 * Date: 11/6/13
 * Time: 12:24 AM
 */
public class AttachmentServiceImpl implements IAttachmentService
{
    private IRepositoryService repositoryService;

    @Override
    public <E extends PersistenceEntity> Attachment storeAttachment(E entity, File file)
    {
        Attachment attachment = new Attachment();
        attachment.setEntityId(entity.getId());
        attachment.setEntityName(entity.getClass().getSimpleName());
        attachment.setName(file.getName());
        attachment.setUuid(UUID.randomUUID().toString());
        repositoryService.store(file, attachment.getUuid());
        repositoryService.store(attachment);
        return attachment;
    }

    @Override
    public <E extends PersistenceEntity> List<Attachment> readAttachments(E entity)
    {
        return repositoryService.find(Query.query(Criteria.where("entityId").is(entity.getId()))
                .addCriteria(Criteria.where("entityName").is(entity.getClass().getSimpleName())),
                Attachment.class);
    }

    @Override
    public Attachment readAttachment(String uuid)
    {
        return repositoryService.read(Query.query(Criteria.where("uuid").is(uuid)),
                Attachment.class);
    }
}
