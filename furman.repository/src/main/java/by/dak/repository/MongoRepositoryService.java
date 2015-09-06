package by.dak.repository;

import com.mongodb.gridfs.GridFSDBFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class MongoRepositoryService implements IRepositoryService
{

    private GridFsOperations gridFsOperations;
    private MongoOperations mongoOperations;

    @Override
    public void store(BufferedImage image, String uuid)
    {

        try
        {
            File file = File.createTempFile(uuid, uuid);
            ImageIO.write(image, "png", file);
            store(file, uuid);
        }
        catch (IOException e)
        {
            throw new IllegalArgumentException(e);
        }
    }

    public void store(File file, String uuid)
    {
        FileInputStream stream = null;
        try
        {
            stream = new FileInputStream(file);
            store(stream, uuid);
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException(e);
        }
    }


    public void store(InputStream content, String uuid)
    {
        gridFsOperations.store(content, uuid);
    }

    public InputStream read(String uuid)
    {
        try
        {
            GridFsResource gridFsResource = gridFsOperations.getResource(uuid);
            return gridFsResource.getInputStream();
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void read(String uuid, File toFile)
    {
        InputStream inputStream = read(uuid);
        try
        {
            FileUtils.copyInputStreamToFile(inputStream, toFile);
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException(e);
        }
        finally
        {
            IOUtils.closeQuietly(inputStream);
        }
    }

    @Override
    public File readTempFile(String uuid)
    {
        File file = new File(System.getProperty("java.io.tmpdir"), uuid);
        read(uuid, file);
        return file;
    }


    public boolean exist(String uuid)
    {
        List<GridFSDBFile> files = gridFsOperations.find(Query.query(Criteria.where("filename").is(uuid)));
        return !files.isEmpty();

    }

    @Override
    public void delete(String uuid)
    {
        gridFsOperations.delete(Query.query(Criteria.where("filename").is(uuid)));
    }

    public GridFsOperations getGridFsOperations()
    {
        return gridFsOperations;
    }

    public void setGridFsOperations(GridFsOperations gridFsOperations)
    {
        this.gridFsOperations = gridFsOperations;
    }

    public MongoOperations getMongoOperations()
    {
        return mongoOperations;
    }

    public void setMongoOperations(MongoOperations mongoOperations)
    {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public <O> void store(O object)
    {
        mongoOperations.save(object);
    }

    public <O> List<O> find(Query query, Class<O> objectClass)
    {
        return mongoOperations.find(query, objectClass);
    }


    @Override
    public <O> O read(Query query, Class<O> objectClass)
    {
        List<O> result = mongoOperations.find(query, objectClass);
        if (result.isEmpty())
            return null;
        if (result.size() > 1)
            throw new IllegalStateException();

        return result.get(0);
    }
}
