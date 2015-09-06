package by.dak.view3d;

import by.dak.model3d.DBox;
import by.dak.model3d.DBoxLocation;

/**
 * User: akoyro
 * Date: 10.09.11
 * Time: 15:50
 */
public class FalseFactory
{
    private static float FALSE_WIDTH = 100f;
    private static float FALSE_HEIGHT = 18f;

    public DBox getFalseBy(DBoxLocation falseLocation, DBox parent)
    {
        DBox box = null;
        switch (falseLocation)
        {
            case RIGHT:
                return getRightFalse(parent);
            case LEFT:
                return getLeftFalse(parent);
            case TOP:
                return getTopFalse(parent);
            case BOTTOM:
                return getBottomFalse(parent);
            default:
                throw new IllegalArgumentException();
        }
    }

    private DBox getBottomFalse(DBox parent)
    {
        DBox topFalse = new DBox();
        topFalse.setLength(parent.getLength());
        topFalse.setHeight(FALSE_HEIGHT);
        topFalse.setWidth(FALSE_WIDTH);


        topFalse.setX(parent.getX());
        topFalse.setY(parent.getMinY() + FALSE_HEIGHT / 2f);
        topFalse.setZ(parent.getMaxZ() - FALSE_WIDTH / 2f);
        return topFalse;
    }

    private DBox getTopFalse(DBox parent)
    {
        DBox topFalse = new DBox();
        topFalse.setLength(parent.getLength());
        topFalse.setHeight(FALSE_HEIGHT);
        topFalse.setWidth(FALSE_WIDTH);


        topFalse.setX(parent.getX());
        topFalse.setY(parent.getMaxY() - FALSE_HEIGHT / 2f);
        topFalse.setZ(parent.getMaxZ() - FALSE_WIDTH / 2f);
        return topFalse;
    }


    private DBox getLeftFalse(DBox parent)
    {
        DBox leftFalse = new DBox();
        leftFalse.setX(parent.getMinX() + FALSE_HEIGHT / 2f);
        leftFalse.setY(parent.getY());
        leftFalse.setZ(parent.getMaxZ() - FALSE_WIDTH / 2f);

        leftFalse.setLength(parent.getHeight());
        leftFalse.setHeight(FALSE_HEIGHT);
        leftFalse.setWidth(FALSE_WIDTH);

        leftFalse.setAngleX(0);
        leftFalse.setAngleY(0);
        leftFalse.setAngleZ((float) Math.PI / 2f);
        return leftFalse;
    }

    private DBox getRightFalse(DBox parent)
    {
        DBox rightFalse = new DBox();
        rightFalse.setX(parent.getMaxX() - FALSE_HEIGHT / 2f);
        rightFalse.setY(parent.getY());
        rightFalse.setZ(parent.getMaxZ() - FALSE_WIDTH / 2f);

        rightFalse.setLength(parent.getHeight());
        rightFalse.setHeight(FALSE_HEIGHT);
        rightFalse.setWidth(FALSE_WIDTH);

        rightFalse.setAngleX(0);
        rightFalse.setAngleY(0);
        rightFalse.setAngleZ((float) Math.PI / 2f);
        return rightFalse;
    }
}
