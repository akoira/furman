package by.dak.persistence.dao;

import by.dak.persistence.entities.BoardDef;
import by.dak.persistence.entities.BorderDefEntity;
import by.dak.persistence.entities.Manufacturer;
import by.dak.persistence.entities.TextureEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TextureDao extends GenericDao<TextureEntity>
{
    List<TextureEntity> findTexturesBy(BoardDef boardDef);

    List<TextureEntity> findTexturesBy(BorderDefEntity borderDef);

    /**
     * Ищем текстуру по текстуре детали в текстурах для данного оклеечного материала
     *
     * @param gluingBorderDef
     * @param detailTexture
     * @return
     */
    TextureEntity findTextureBy(BorderDefEntity gluingBorderDef, TextureEntity detailTexture);

    boolean isUniqueByCodeSurfaceManf(TextureEntity texture);

    List<TextureEntity> findTexturesBy(BoardDef boardDef, Manufacturer manufacturer);
}
