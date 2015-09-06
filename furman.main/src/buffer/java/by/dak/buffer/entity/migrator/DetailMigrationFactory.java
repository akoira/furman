package by.dak.buffer.entity.migrator;

import by.dak.buffer.entity.DilerOrderDetail;
import by.dak.cutting.SpringConfiguration;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.AOrderDetail;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 09.11.2010
 * Time: 17:32:41
 * To change this template use File | Settings | File Templates.
 */
public class DetailMigrationFactory<D extends AOrderDetail>
{
    //discriminator, migrator class name
    private HashMap<String, String> configuration = new HashMap<String, String>();
    //discriminator, migrator class
    private HashMap<String, DilerOrderDetailMigrator> cache = new HashMap<String, DilerOrderDetailMigrator>();

    public HashMap<String, String> getConfiguration()
    {
        return configuration;
    }

    public void setConfiguration(HashMap<String, String> configuration)
    {
        this.configuration = configuration;
    }

    public D migrate(DilerOrderDetail dilerOrderDetail)
    {
        String discriminator = dilerOrderDetail.getDiscriminator();
        String migratorClassName = getMigratorClassNameBy(discriminator);
        D orderDetail = null;
        if (!cache.containsKey(discriminator))
        {
            try
            {
                addCacheMap(migratorClassName, discriminator);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        if (!cache.isEmpty() && cache.containsKey(discriminator))
        {
            DilerOrderDetailMigrator migrator = cache.get(discriminator);
            orderDetail = (D) migrator.migrate(dilerOrderDetail);
        }
        return orderDetail;
    }

    private void addCacheMap(String migratorClassName, String discriminator) throws ClassNotFoundException, IllegalAccessException, InstantiationException
    {
        Class clazz = Class.forName(migratorClassName);
        DilerOrderDetailMigrator migrator = (DilerOrderDetailMigrator) clazz.newInstance();

        cache.put(discriminator, migrator);
    }

    private String getMigratorClassNameBy(String discriminator)
    {
        return configuration.get(discriminator);
    }

    public static void main(String[] args) throws Exception
    {
        SpringConfiguration springConfiguration = new SpringConfiguration();
        DetailMigrationFactory migrationFactory = (DetailMigrationFactory) springConfiguration.getApplicationContext().getBean("detailMigrationFactory");
        AOrderDetail orderDetail = migrationFactory.migrate(FacadeContext.getDilerOrderDetailFacade().loadAll().get(0));
    }
}
