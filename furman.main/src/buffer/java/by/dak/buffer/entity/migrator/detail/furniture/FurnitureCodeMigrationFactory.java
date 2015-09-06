package by.dak.buffer.entity.migrator.detail.furniture;

import by.dak.cutting.SpringConfiguration;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Priced;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 09.12.2010
 * Time: 22:39:15
 * To change this template use File | Settings | File Templates.
 */
public class FurnitureCodeMigrationFactory<P extends Priced>
{
    //discriminator, migrator class name
    private HashMap<String, String> configuration = new HashMap<String, String>();
    //discriminator, migrator class
    private HashMap<String, AFurnitureCodeMigrator> cache = new HashMap<String, AFurnitureCodeMigrator>();

    public HashMap<String, String> getConfiguration()
    {
        return configuration;
    }

    public void setConfiguration(HashMap<String, String> configuration)
    {
        this.configuration = configuration;
    }

    public P migrate(Priced priced)
    {
        String discriminator = priced.getPricedType();
        String migratorClassName = getMigratorClassNameBy(discriminator);
        P migratedPriced = null;
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
            AFurnitureCodeMigrator migrator = cache.get(discriminator);
            migratedPriced = (P) migrator.migrate(priced);
        }
        return migratedPriced;
    }

    private void addCacheMap(String migratorClassName, String discriminator) throws ClassNotFoundException, IllegalAccessException, InstantiationException
    {
        Class clazz = Class.forName(migratorClassName);
        AFurnitureCodeMigrator migrator = (AFurnitureCodeMigrator) clazz.newInstance();

        cache.put(discriminator, migrator);
    }

    private String getMigratorClassNameBy(String discriminator)
    {
        return configuration.get(discriminator);
    }

    public static void main(String[] args) throws Exception
    {
        SpringConfiguration springConfiguration = new SpringConfiguration();
        FurnitureCodeMigrationFactory furnitureCodeMigrationFactory = (FurnitureCodeMigrationFactory) springConfiguration.getApplicationContext().getBean("furnitureCodeMigrationFactory");
        Priced priced = furnitureCodeMigrationFactory.migrate(FacadeContext.getDilerOrderDetailFacade().findById((long) 1318313, true).getFurnitureCode());
        priced.getPricedType();

    }
}
