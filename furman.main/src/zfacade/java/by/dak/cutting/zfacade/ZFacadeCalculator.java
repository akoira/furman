package by.dak.cutting.zfacade;

import by.dak.cutting.afacade.AFacadeCalculator;

/**
 * User: akoyro
 * Date: 05.07.2010
 * Time: 16:08:18
 */
public class ZFacadeCalculator extends AFacadeCalculator<ZFacade>
{
    //длинна профиль должна быть увеличена на  DEFAULT_PROFILE_OFFSET мм
    public static final double DEFAULT_PROFILE_OFFSET = 50;

    public void calculate(ZFacade facade)
    {
        calcFilling(facade);
        calcProfiles(facade);
        calcAngle(facade);
        calcRubber(facade);
        calcButt(facade);
    }

    private void calcButt(ZFacade facade)
    {
        facade.getButt().setSize(1D);
        facade.getButt().setOrderItem(facade);
    }

    private void calcAngle(ZFacade facade)
    {
        facade.getAngle().setSize(1D);
        facade.getAngle().setAmount(4);
        facade.getAngle().setOrderItem(facade);
    }
}
