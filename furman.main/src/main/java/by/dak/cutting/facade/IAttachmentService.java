package by.dak.cutting.facade;

import by.dak.persistence.entities.Attachment;
import by.dak.persistence.entities.PersistenceEntity;

import java.io.File;
import java.util.List;

/**
 * User: akoyro
 * Date: 11/6/13
 * Time: 12:24 AM
 */
public interface IAttachmentService
{
    <E extends PersistenceEntity> Attachment storeAttachment(E entity, File file);

    <E extends PersistenceEntity> List<Attachment> readAttachments(E entity);

    Attachment readAttachment(String uuid);
}

