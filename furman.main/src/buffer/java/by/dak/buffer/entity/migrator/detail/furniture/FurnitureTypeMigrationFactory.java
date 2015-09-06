package by.dak.buffer.entity.migrator.detail.furniture;

import by.dak.cutting.SpringConfiguration;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.PriceAware;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 09.12.2010
 * Time: 22:39:38
 * To change this template use File | Settings | File Templates.
 */
public class FurnitureTypeMigrationFactory<P extends PriceAware>
{
    //discriminator, migrator class name
    private HashMap<String, String> configuration = new HashMap<String, String>();
    //discriminator, migrator class
    private HashMap<String, AFurnitureTypeMigrator> cache = new HashMap<String, AFurnitureTypeMigrator>();

    public HashMap<String, String> getConfiguration()
    {
        return configuration;
    }

    public void setConfiguration(HashMap<String, String> configuration)
    {
        this.configuration = configuration;
    }

    public P migrate(PriceAware priceAware)
    {
        String discriminator = priceAware.getDiscriminator();
        String migratorClassName = getMigratorClassNameBy(discriminator);
        P migratedPriceAware = null;
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
            AFurnitureTypeMigrator migrator = cache.get(discriminator);
            migratedPriceAware = (P) migrator.migrate(priceAware);
        }
        return migratedPriceAware;
    }

    private void addCacheMap(String migratorClassName, String discriminator) throws ClassNotFoundException, IllegalAccessException, InstantiationException
    {
        Class clazz = Class.forName(migratorClassName);
        AFurnitureTypeMigrator migrator = (AFurnitureTypeMigrator) clazz.newInstance();

        cache.put(discriminator, migrator);
    }

    private String getMigratorClassNameBy(String discriminator)
    {
        return configuration.get(discriminator);
    }

    public static void main(String[] args) throws Exception
    {
        SpringConfiguration springConfiguration = new SpringConfiguration();
        FurnitureTypeMigrationFactory furnitureCodeMigrationFactory = (FurnitureTypeMigrationFactory) springConfiguration.getApplicationContext().getBean("furnitureTypeMigrationFactory");
        PriceAware priceAware = furnitureCodeMigrationFactory.migrate(FacadeContext.getDilerOrderDetailFacade().findById((long) 1318313, true).getFurnitureType());
        priceAware.getDefaultSize();

    }
}
