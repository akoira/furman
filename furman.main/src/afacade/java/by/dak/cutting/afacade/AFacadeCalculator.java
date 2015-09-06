package by.dak.cutting.afacade;

import by.dak.persistence.entities.FurnitureLink;
import by.dak.persistence.entities.predefined.Side;
import by.dak.persistence.entities.predefined.Unit;

/**
 * User: akoyro
 * Date: 01.09.2010
 * Time: 20:22:42
 */
public abstract class AFacadeCalculator<E extends AFacade>
{
    private double profileOffset;

    public abstract void calculate(E aFacade);

    protected void calcProfiles(E facade)
    {
        calcProfile(facade.getUpProfile(), Side.up, facade);
        if (!facade.getLength().equals(facade.getWidth()))
            calcProfile(facade.getLeftProfile(), Side.left, facade);
    }

    protected void calcProfile(FurnitureLink profile, Side side, E facade)
    {
        profile.setNumber((long) side.getIndex());
        profile.setName(side.name());
        profile.setFurnitureCode(facade.getProfileColor());
        profile.setFurnitureType(facade.getProfileType());
        profile.setOrderItem(facade);
        switch (side)
        {

            case left:
                profile.setSize((facade.getLength() + getProfileOffset()));
                profile.setAmount(2);
                break;
            case up:
                profile.setSize((facade.getWidth() + getProfileOffset()));
                profile.setAmount(!facade.getLength().equals(facade.getWidth()) ? 2 : 4);
                break;
            default:
                throw new IllegalArgumentException();
        }

        profile.setSize(convert(profile.getSize(), Unit.linearMiliMetre, Unit.linearMetre));
    }

    protected void calcRubber(E facade)
    {
        double size = (facade.getLength() + facade.getWidth()) * 2;
        size = convert(size, Unit.linearMiliMetre, Unit.linearMetre);
        facade.getRubber().setSize(size);
        facade.getRubber().setAmount(1);
        facade.getRubber().setOrderItem(facade);
    }


    protected void calcFilling(E facade)
    {
        Double length = facade.getLength();
        Double width = facade.getWidth();

        double fillingLength = length - Math.max(facade.getProfileType().getOffsetLength(), facade.getProfileColor().getOffsetLength());
        double fillingWidth = width - Math.max(facade.getProfileType().getOffsetWidth(), facade.getProfileColor().getOffsetWidth());

        facade.getFilling().setLength((long) fillingLength);
        facade.getFilling().setWidth((long) fillingWidth);
        facade.getFilling().setOrderItem(facade);
        facade.getFilling().setAmount(1);
        facade.getFilling().setPrimary(Boolean.TRUE);
        facade.getFilling().setRotatable(facade.getFilling().getTexture() != null && facade.getFilling().getTexture().isRotatable());
        facade.getFilling().setNumber(facade.getNumber());
    }

    protected double convert(double from, Unit fromUnit, Unit toUnit)
    {
        double to = from;

        if (fromUnit == toUnit)
        {
            return from;
        }

        switch (toUnit)
        {
            case squareMetre:
            case linearMetre:
                to = from / 1000.0;
                break;
            case piece:
            case linearMiliMetre:
                break;
            case linearSantiMetre:
                to = from / 10.0;
                break;
            default:
                throw new IllegalArgumentException();
        }
        return to;
    }


    public double getProfileOffset()
    {
        return profileOffset;
    }

    public void setProfileOffset(double profileOffset)
    {
        this.profileOffset = profileOffset;
    }
}
