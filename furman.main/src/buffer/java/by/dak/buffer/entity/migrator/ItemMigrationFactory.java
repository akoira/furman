package by.dak.buffer.entity.migrator;

import by.dak.buffer.entity.DilerOrderItem;
import by.dak.cutting.SpringConfiguration;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.OrderItem;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: alkoyro
 * Date: 09.11.2010
 * Time: 13:29:18
 * To change this template use File | Settings | File Templates.
 */
public class ItemMigrationFactory<I extends OrderItem>
{
    //discriminator, migrator class
    private HashMap<String, DilerOrderItemMigrator> cache = new HashMap<String, DilerOrderItemMigrator>();
    //discriminator, имя класса migratora
    private HashMap<String, String> configuration = new HashMap<String, String>();

    public void setConfiguration(HashMap<String, String> configuration)
    {
        this.configuration = configuration;
    }

    public HashMap<String, String> getConfiguration()
    {
        return configuration;
    }

    public I migrate(DilerOrderItem dilerOrderItem)
    {
        String discriminator = dilerOrderItem.getDiscriminator();
        String migratorClassName = getMigratorClassNameBy(discriminator);
        I orderItem = null;
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
            DilerOrderItemMigrator migrator = cache.get(discriminator);
            orderItem = (I) migrator.migrate(dilerOrderItem);
        }
        return orderItem;
    }

    private void addCacheMap(String migratorClassName, String discriminator) throws ClassNotFoundException, IllegalAccessException, InstantiationException
    {
        Class clazz = Class.forName(migratorClassName);
        DilerOrderItemMigrator migrator = (DilerOrderItemMigrator) clazz.newInstance();

        cache.put(discriminator, migrator);
    }

    private String getMigratorClassNameBy(String discriminator)
    {
        return configuration.get(discriminator);
    }

    public static void main(String[] args) throws Exception
    {
        SpringConfiguration springConfiguration = new SpringConfiguration();
        ItemMigrationFactory itemMigrationFactory = (ItemMigrationFactory) springConfiguration.getApplicationContext().getBean("itemMigrationFactory");


        itemMigrationFactory.migrate(FacadeContext.getDilerOrderItemFacade().loadAll().get(0));
    }
}