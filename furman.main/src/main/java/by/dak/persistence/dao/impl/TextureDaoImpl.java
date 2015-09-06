package by.dak.persistence.dao.impl;

import by.dak.persistence.dao.TextureDao;
import by.dak.persistence.entities.BoardDef;
import by.dak.persistence.entities.BorderDefEntity;
import by.dak.persistence.entities.Manufacturer;
import by.dak.persistence.entities.TextureEntity;
import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;

import java.util.List;

import static org.hibernate.criterion.Restrictions.*;

/**
 * @author Denis Koyro
 * @version 0.1 08.12.2008
 * @introduced [Furniture constructor | Iteration 1]
 * @since 1.0.0
 */
public class TextureDaoImpl extends GenericDaoImpl<TextureEntity> implements TextureDao
{

    @Override
    public List<TextureEntity> findTexturesBy(BoardDef boardDef)
    {
        SQLQuery q = getSession().createSQLQuery("select distinct t.*  from TEXTURE t inner join PRICE p on p.PRICED_ID = t.ID where p.PRICEAWARE_ID = :boardDefID ORDER BY t.NAME");
        q.setLong("boardDefID", boardDef.getId());
        q.addEntity("texture", TextureEntity.class);
        return q.list();
    }

    @Override
    public List<TextureEntity> findTexturesBy(BorderDefEntity borderDef)
    {
        SQLQuery q = getSession().createSQLQuery("select distinct t.*  from TEXTURE t inner join PRICE p on p.PRICED_ID = t.ID where p.PRICEAWARE_ID = :borderDefID ORDER BY t.NAME");
        q.setLong("borderDefID", borderDef.getId());
        q.addEntity("texture", TextureEntity.class);
        return q.list();
    }

    @Override
    public List<TextureEntity> findTexturesBy(BoardDef boardDef, Manufacturer manufacturer)
    {
        SQLQuery q = getSession().createSQLQuery("select distinct t.*  from TEXTURE t inner join PRICE p on p.PRICED_ID = t.ID where p.PRICEAWARE_ID = :boardDef and t.MANUFACTURER_ID = :manufacturer ORDER BY t.NAME");
        q.setLong("boardDef", boardDef.getId());
        q.setLong("manufacturer", manufacturer.getId());
        q.addEntity("texture", TextureEntity.class);
        return q.list();
    }

    @Override
    public TextureEntity findTextureBy(BorderDefEntity gluingBorderDef, TextureEntity detailTexture)
    {
        SQLQuery q = getSession().createSQLQuery("select distinct t.*  from FURNITURE_CODE t inner join PRICE p on p.PRICED_ID = t.ID where p.PRICEAWARE_ID = :borderDefID and (t.ID = :textureID or t.NAME = :name)");
        q.setLong("borderDefID", gluingBorderDef.getId());
        q.setLong("textureID", gluingBorderDef.getId());
        q.setString("name", detailTexture.getName());
        q.addEntity("texture", TextureEntity.class);
        List<TextureEntity> textures = q.list();
        if (textures.size() > 0)
        {
            return textures.get(0);
        }
        else
        {
            return null;
        }
    }

    @Override
    public boolean isUniqueByCodeSurfaceManf(TextureEntity texture)
    {
        try
        {
            Object code = PropertyUtils.getProperty(texture, "code");
            Object surface = PropertyUtils.getProperty(texture, "surface");
            Object manufacturer = PropertyUtils.getProperty(texture, "manufacturer");
            if (code == null || manufacturer == null)
            {
                return false;
            }
            Criteria criteria = createCriteria(getPersistentClass());
            criteria.add(eq("code", code));
            criteria.add(eq("manufacturer", manufacturer));
            criteria.add(surface != null ? eq("surface", surface) : isNull("surface"));

            if (texture.hasId())
            {
                criteria.add(ne("id", texture.getId()));
            }
            TextureEntity result = (TextureEntity) criteria.uniqueResult();
            return result == null;
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }
}