package by.dak.cutting.agt;

import by.dak.cutting.afacade.AFacadeCalculator;
import by.dak.cutting.agt.facade.AGTFacadeFacadeImpl;
import by.dak.utils.validator.ValidatorAnnotationProcessor;

/**
 * User: akoyro
 * Date: 23.08.2010
 * Time: 23:28:22
 */
public class AGTFacadeCalculator extends AFacadeCalculator<AGTFacade>
{
    @Override
    public void calculate(AGTFacade facade)
    {
        calcFilling(facade);
        calcProfiles(facade);
        calcRubber(facade);
        calcDowel(facade);
        calcGlue(facade);

    }

    private void calcGlue(AGTFacade facade)
    {
        double size = (facade.getLength() + facade.getWidth()) * 2 / 1000.0;
        facade.getGlue().setSize(size * 0.25);
        facade.getGlue().setAmount(1);

    }

    private void calcDowel(AGTFacade facade)
    {
        facade.getDowel().setSize(1D);
        facade.getDowel().setAmount(8);
    }

    protected void calcFilling(AGTFacade facade)
    {
        //ширина
        Double length = facade.getLength();
        //высота
        Double width = facade.getWidth();

        double profileWidth = AGTFacadeFacadeImpl.getProfileWidth(facade);
        double offset = (profileWidth - 7 + 1 + (ValidatorAnnotationProcessor.getProcessor().validate(facade.getRubber()).hasErrors() ? 0 : 1)) * 2;

        double fillingLength = length - offset;
        double fillingWidth = width - offset;

        facade.getFilling().setLength((long) fillingLength);
        facade.getFilling().setWidth((long) fillingWidth);
        facade.getFilling().setOrderItem(facade);
        facade.getFilling().setAmount(1);
        facade.getFilling().setPrimary(Boolean.TRUE);
        facade.getFilling().setNumber(facade.getNumber());
    }

}
