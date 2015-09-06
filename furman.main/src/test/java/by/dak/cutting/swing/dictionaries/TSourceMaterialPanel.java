package by.dak.cutting.swing.dictionaries;

import by.dak.cutting.SpringConfiguration;
import by.dak.persistence.FacadeContext;
import by.dak.test.TestUtils;

/**
 * Created by IntelliJ IDEA.
 * User: akoyro
 * Date: 07.06.2009
 * Time: 15:19:02
 * To change this template use File | Settings | File Templates.
 */
public class TSourceMaterialPanel
{
    public static void main(String[] args)
    {
        SpringConfiguration configuration = new SpringConfiguration();

        SourceMaterialPanel panel = new SourceMaterialPanel();
        panel.setSourceMaterials(FacadeContext.getBoardDefFacade().findSimpleBoardDefs());
        TestUtils.showFrame(panel, "TSourceMaterialPanel");

//        A1Dao a1Dao = (A1Dao)configuration.getApplicationContext().getBean("a1Dao");
//        A2Dao a2Dao = (A2Dao)configuration.getApplicationContext().getBean("a2Dao");
//        BDao bDao = (BDao)configuration.getApplicationContext().getBean("bDao");
//
//        A1 a1 = new A1();
//        a1Dao.save(a1);
//        A2 a2 = new A2();
//        a2Dao.save(a2);
//
//        B b = new B();
//        b.setA(a1);
//        bDao.save(b);
//
//        b = new B();
//        b.setA(a1);
//
//
//
//
//        a1 = new A1();
//        a1Dao.save(a1);

//        List<A1> list = a1Dao.findAll();
//        BDao bDao = (BDao)configuration.getApplicationContext().getBean("bDao");
//        a1Dao.findAll();
//        B b = bDao.findOrderedByNumber(list.get(0));
    }
}
