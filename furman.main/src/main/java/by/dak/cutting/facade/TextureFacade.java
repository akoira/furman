package by.dak.cutting.facade;

import by.dak.persistence.FinderException;
import by.dak.persistence.entities.BoardDef;
import by.dak.persistence.entities.BorderDefEntity;
import by.dak.persistence.entities.Manufacturer;
import by.dak.persistence.entities.TextureEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface TextureFacade extends PricedFacade<TextureEntity>
{

    List<TextureEntity> findTexturesByGroup(String groupIdentifier) throws FinderException;

    TextureEntity findTextureByCode(String code);

    List<TextureEntity> findTexturesBy(BoardDef boardDef);

    TextureEntity getTextureBy(BoardDef boardDef, String code);

    List<TextureEntity> findTexturesBy(BoardDef boardDef, Manufacturer manufacturer);

    List<TextureEntity> findTexturesBy(BorderDefEntity borderDef);

    TextureEntity findBy(BorderDefEntity gluingBorderDef, TextureEntity detailTexture);

    boolean isUniqueByCodeSurfaceManf(TextureEntity texture);
}
