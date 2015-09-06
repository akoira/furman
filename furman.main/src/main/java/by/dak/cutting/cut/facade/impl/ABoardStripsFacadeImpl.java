package by.dak.cutting.cut.facade.impl;

import by.dak.cutting.SearchFilter;
import by.dak.cutting.cut.facade.ABoardStripsFacade;
import by.dak.cutting.cut.facade.helper.StripsLoader;
import by.dak.cutting.cut.gui.CutSettings;
import by.dak.cutting.cut.guillotine.Segment;
import by.dak.cutting.cut.guillotine.Strips;
import by.dak.cutting.facade.AOrderBoardDetailFacade;
import by.dak.cutting.statistics.StatisticFilter;
import by.dak.cutting.swing.cut.CutSettingsCreator;
import by.dak.cutting.swing.cut.CuttingModel;
import by.dak.cutting.swing.order.data.TextureBoardDefPair;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.NamedQueryDefinition;
import by.dak.persistence.NamedQueryParameter;
import by.dak.persistence.cutting.dao.ABoardStripsDao;
import by.dak.persistence.cutting.entities.ABoardStripsEntity;
import by.dak.persistence.cutting.entities.StripsEntity;
import by.dak.persistence.cutting.entities.StripsXmlSerializer;
import by.dak.persistence.entities.*;
import by.dak.report.jasper.ReportUtils;
import by.dak.utils.GenericUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.transform.AliasToBeanResultTransformer;

import javax.persistence.DiscriminatorValue;
import java.lang.reflect.Constructor;
import java.sql.Date;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 27.09.11
 * Time: 9:41
 * To change this template use File | Settings | File Templates.
 */
public abstract class ABoardStripsFacadeImpl<S extends ABoardStripsEntity> extends AStripsFacadeImpl<S> implements ABoardStripsFacade<S>
{
    private Class<? extends CutSettingsCreator> cutSettingsCreatorClass;
    private AOrderBoardDetailFacade orderBoardDetailFacade;

    public void setOrderBoardDetailFacade(AOrderBoardDetailFacade orderBoardDetailFacade)
    {
        this.orderBoardDetailFacade = orderBoardDetailFacade;
    }

    public AOrderBoardDetailFacade getOrderBoardDetailFacade()
    {
        return orderBoardDetailFacade;
    }

    protected S createEntity(AOrder order, TextureBoardDefPair pair)
    {
        try
        {
            S result = (S) GenericUtils.getParameterClass(this.getClass(), 0).getConstructor().newInstance();
            result.setOrder(order);
            result.setBoardDef(pair.getBoardDef());
            result.setTexture(pair.getTexture());
            result.setRotation(pair.getTexture().isRotatable());
            result.setSawWidth(pair.getBoardDef().getCutter().getSawcutWidth().intValue());
            result.setStrips("");
            save(result);
            return result;
        }
        catch (Throwable e)
        {
            throw new IllegalArgumentException(e);
        }
    }

    private void removeUnusedSegments(Strips strips)
    {
        Map<Long, Integer> map = new HashMap<Long, Integer>();
        List<Segment> segments = strips.getSegments();
        for (Segment segment : segments)
        {
            StorageElementLink storageElementLink = FacadeContext.getBoardFacade().getStorageElementLinkBy(segment);
            Integer count = map.get(storageElementLink.getId());
            if (count == null)
            {
                map.put(storageElementLink.getId(), 1);
            }
            else
            {
                map.put(storageElementLink.getId(), count + 1);
            }
        }
        List<StorageElementLink> storageElementLinks = FacadeContext.getStorageElementLinkFacade().loadAllBy(strips.getStripsEntity(), Board.class.getAnnotation(DiscriminatorValue.class).value());
        for (StorageElementLink storageElementLink : storageElementLinks)
        {
            if (map.containsKey(storageElementLink.getId()))
            {
                FacadeContext.getBoardFacade().releaseBy(storageElementLink, map.get(storageElementLink.getId()));
            }
            else
            {
                FacadeContext.getBoardFacade().releaseBy(storageElementLink, 0);
            }
        }
    }

    public void saveAll(CuttingModel cuttingModel)
    {
        List<TextureBoardDefPair> pairs = cuttingModel.getPairs();
        for (TextureBoardDefPair pair : pairs)
        {
            Strips strips = cuttingModel.getStrips(pair);
            removeUnusedSegments(strips);
            S stripsEntity = findBy(strips.getStripsEntity().getId());
            stripsEntity.setOrder(cuttingModel.getOrder());
            stripsEntity.setPaidValue(ReportUtils.getPaidValueBy(pair, strips));
            stripsEntity.setBoardDef(pair.getBoardDef());
            stripsEntity.setTexture(pair.getTexture());
            stripsEntity.setRotation(strips.isAllowRotation());
            stripsEntity.setSawWidth(strips.getSawWidth());
            stripsEntity.setStrips(StripsXmlSerializer.getInstance().serialize(strips));
            save(stripsEntity);
            strips.setStripsEntity(stripsEntity);
        }
    }

    @Override
    public void deleteAll(AOrder order)
    {
        List<S> boardStripsEntities = loadAllBy(order);
        for (S boardStripsEntity : boardStripsEntities)
        {
            FacadeContext.getStorageElementLinkFacade().deleteAllBy(boardStripsEntity);
        }
        ((ABoardStripsDao) dao).deleteAll(order);
    }

    private List<S> loadAllBy(AOrder order)
    {
        SearchFilter filter = SearchFilter.instanceUnbound();
        filter.eq(ABoardStripsEntity.PROPERTY_order, order);
        return loadAll(filter);
    }


    public List<S> findBy(Date start, Date end)
    {
        SearchFilter filter = SearchFilter.instanceUnbound();

        filter.ge(StringUtils.join(new String[]{StripsEntity.PROPERTY_order, Order.PROPERTY_readyDate}, '.'), start);
        filter.le(StringUtils.join(new String[]{StripsEntity.PROPERTY_order, Order.PROPERTY_readyDate}, '.'), end);
        filter.in(StringUtils.join(new String[]{StripsEntity.PROPERTY_order, Order.PROPERTY_orderStatus}, '.'),
                Arrays.asList(new OrderStatus[]{OrderStatus.archive, OrderStatus.made, OrderStatus.shipped, OrderStatus.production}));
        return loadAll(filter);
    }

    @Override
    public S findUniqueStrips(AOrder order, TextureEntity texture, BoardDef boardDef)
    {
        SearchFilter filter = SearchFilter.instanceUnbound();
        filter.eq(OrderItem.PROPERTY_order, order);
        filter.eq(StripsEntity.PROPERTY_priceAware, boardDef);
        filter.eq(StripsEntity.PROPERTY_priced, texture);
        return getFirstBy(filter);
    }

    public Map<TextureBoardDefPair, CutSettings> loadCutSettings(AOrder order, List<TextureBoardDefPair> pairs)
    {
        HashMap<TextureBoardDefPair, CutSettings> result = new HashMap<TextureBoardDefPair, CutSettings>();
        for (TextureBoardDefPair pair : pairs)
        {
            result.put(pair, getCutSettings(order, pair));
        }
        return result;
    }


    @Override
    public StripsLoader loadCuttingModel(AOrder order)
    {
        CuttingModel cuttingModel = new CuttingModel();
        cuttingModel.setOrder(order);
        cuttingModel.setPairs(getOrderBoardDetailFacade().findUniquePairDefText(order));

        return createStripsLoader(cuttingModel);
    }

    protected List<? extends AOrderBoardDetail> getFurnituresBy(AOrder order, TextureBoardDefPair pair)
    {
        List<? extends AOrderBoardDetail> furnitures = getOrderBoardDetailFacade().findBy(order, pair);
        if (furnitures == null)
        {
            furnitures = Collections.emptyList();
        }
        furnitures = Collections.unmodifiableList(furnitures);
        return furnitures;
    }

    protected CutSettings getCutSettings(AOrder order, TextureBoardDefPair pair)
    {
        S stripsEntity = createEntity(order, pair);
        CutSettingsCreator cutSettingsCreator = createCutSettingsCreator(stripsEntity);
        return cutSettingsCreator.create().getCutSettings();
    }


    protected CutSettingsCreator createCutSettingsCreator(S stripsEntity)
    {
        CutSettingsCreator cutSettingsCreator;
        if (getCutSettingsCreatorClass() != null)
        {
            try
            {
                Constructor<? extends CutSettingsCreator> constructor = getCutSettingsCreatorClass().getConstructor();
                cutSettingsCreator = constructor.newInstance();
            }
            catch (Throwable e)
            {
                FacadeContext.getExceptionHandler().handle(e);
                throw new IllegalArgumentException(e);
            }
        }
        else
        {
            cutSettingsCreator = new CutSettingsCreator();
        }
        TextureBoardDefPair pair = stripsEntity.getPair();
        cutSettingsCreator.setCutter(stripsEntity.getBoardDef().getCutter());
        cutSettingsCreator.setFurnitures(getFurnituresBy(stripsEntity.getOrder(), pair));
        cutSettingsCreator.setStripsEntity(stripsEntity);
        cutSettingsCreator.setPair(pair);
        return cutSettingsCreator;
    }


    public List<StatisticDTO> getStatistics(StatisticFilter statisticFilter, BoardDef boardDef, TextureEntity texture)
    {

        NamedQueryDefinition definition = statisticFilter.getNamedQueryDefinition();
        definition.setNameQuery("statistics");
        definition.getParameterList().add(NamedQueryParameter.getLongParameter("type", boardDef.getId()));
        definition.getParameterList().add(NamedQueryParameter.getLongParameter("code", texture.getId()));
        definition.setResultTransformer(new AliasToBeanResultTransformer(StatisticDTO.class));

        return getDao().findAllBy(definition);
    }

    public Map<BoardDef, List<TextureEntity>> findUniquePairsBy(StatisticFilter statisticFilter)
    {
        NamedQueryDefinition definition = statisticFilter.getNamedQueryDefinition();
        definition.setNameQuery("uniquePairsByStatisticFilter");
        List<Object[]> pairs = getDao().findAllBy(definition);
        HashMap<BoardDef, List<TextureEntity>> result = new HashMap<BoardDef, List<TextureEntity>>(pairs.size());
        for (Object[] objects : pairs)
        {
            List<TextureEntity> textures = result.get(objects[1]);
            if (textures == null)
            {
                textures = new ArrayList<TextureEntity>();
                result.put((BoardDef) objects[1], textures);
            }
            textures.add((TextureEntity) objects[0]);
        }
        return result;
    }

    public Class<? extends CutSettingsCreator> getCutSettingsCreatorClass()
    {
        return cutSettingsCreatorClass;
    }

    public void setCutSettingsCreatorClass(Class<? extends CutSettingsCreator> cutSettingsCreatorClass)
    {
        this.cutSettingsCreatorClass = cutSettingsCreatorClass;
    }

    protected abstract StripsLoader createStripsLoader(CuttingModel cuttingModel);
}
