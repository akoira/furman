package by.dak.repository;

import org.springframework.data.mongodb.core.query.Query;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.List;

public interface IRepositoryService
{

    public void store(BufferedImage image, String uuid);

    public void store(InputStream content, String uuid);

    public void store(File file, String uuid);

    public InputStream read(String uuid);

    public void read(String uuid, File toFile);

    public File readTempFile(String uuid);

    public void delete(String uuid);

    public <O> void store(O object);

    public <O> List<O> find(Query query, Class<O> objectClass);

    public <O> O read(Query query, Class<O> objectClass);

    public boolean exist(String uuid);
}
