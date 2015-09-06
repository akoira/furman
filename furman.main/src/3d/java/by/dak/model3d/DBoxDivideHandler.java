package by.dak.model3d;

/**
 * User: akoyro
 * Date: 05.09.11
 * Time: 10:47
 */
public class DBoxDivideHandler
{
    private DBox parent;
    private DBox divider;
    private DBox rotatedDivider;


    private DBox frontBox;
    private DBox backBox;
    private DBox rightBox;
    private DBox leftBox;
    private DBox topBox;
    private DBox bottomBox;


    public void divide()
    {
        divideFront();
        divideBack();

        divideRight();
        divideLeft();

        divideTop();
        divideBottom();
    }

    private void divideBottom()
    {
        bottomBox = createDBox();

        bottomBox.setBoxLocation(DBoxLocation.BOTTOM);

        bottomBox.setLength(parent.getLength());
        bottomBox.setHeight(rotatedDivider.getMaxY() - parent.getMinY());
        bottomBox.setWidth(rotatedDivider.getWidth());

        bottomBox.setX(parent.getX());
        bottomBox.setY(rotatedDivider.getMaxY() - bottomBox.getHeight() / 2f);
        bottomBox.setZ(rotatedDivider.getZ());
    }

    private void divideTop()
    {
        topBox = createDBox();
        topBox.setBoxLocation(DBoxLocation.TOP);

        topBox.setLength(parent.getLength());
        topBox.setHeight(parent.getMaxY() - rotatedDivider.getMinY());
        topBox.setWidth(rotatedDivider.getWidth());

        topBox.setX(parent.getX());
        topBox.setY(parent.getMaxY() - topBox.getHeight() / 2f);
        topBox.setZ(rotatedDivider.getZ());
    }

    private void divideLeft()
    {
        leftBox = createDBox();
        leftBox.setBoxLocation(DBoxLocation.LEFT);

        leftBox.setLength(parent.getLength() - rotatedDivider.getLength());
        leftBox.setHeight(rotatedDivider.getHeight());
        leftBox.setWidth(rotatedDivider.getWidth());

        leftBox.setX(rotatedDivider.getMinX() - leftBox.getLength() / 2);
        leftBox.setY(parent.getY());
        leftBox.setZ(rotatedDivider.getZ());
    }


    private void divideRight()
    {
        rightBox = createDBox();
        rightBox.setBoxLocation(DBoxLocation.RIGHT);

        rightBox.setLength(parent.getLength() - rotatedDivider.getLength());
        rightBox.setHeight(rotatedDivider.getHeight());
        rightBox.setWidth(rotatedDivider.getWidth());
        rightBox.setX(rotatedDivider.getMaxX() + rightBox.getLength() / 2);
        rightBox.setY(parent.getY());
        rightBox.setZ(rotatedDivider.getZ());
    }

    private void divideBack()
    {
        backBox = createDBox();
        backBox.setBoxLocation(DBoxLocation.BACK);

        backBox.setLength(parent.getLength());
        backBox.setHeight(rotatedDivider.getHeight());

        double width = rotatedDivider.getMinZ() - parent.getMinZ();
        backBox.setWidth(width > 0 ? width : 0);

        backBox.setX(parent.getX());
        backBox.setY(parent.getY());
        backBox.setZ(rotatedDivider.getMinZ() - backBox.getWidth() / 2);
    }

    private void divideFront()
    {
        frontBox = createDBox();
        frontBox.setBoxLocation(DBoxLocation.FRONT);

        frontBox.setLength(parent.getLength());
        frontBox.setHeight(rotatedDivider.getHeight());

        double width = parent.getMaxZ() - rotatedDivider.getMaxZ();
        frontBox.setWidth(width > 0 ? width : 0);

        frontBox.setX(parent.getX());
        frontBox.setY(parent.getY());
        frontBox.setZ(rotatedDivider.getMaxZ() + frontBox.getWidth() / 2f);
    }

    protected DBox createDBox()
    {
        DBox dBox = new DBox();
        dBox.setParent(parent);
        return dBox;
    }

    public DBox getParent()
    {
        return parent;
    }

    public void setParent(DBox parent)
    {
        this.parent = parent;
    }

    public DBox getDivider()
    {
        return divider;
    }

    public void setDivider(DBox divider)
    {
        this.divider = divider;
        this.rotatedDivider = divider.getRotatedBox();
    }

    public DBox getFrontBox()
    {
        return frontBox;
    }

    public DBox getBackBox()
    {
        return backBox;
    }

    public DBox getRightBox()
    {
        return rightBox;
    }

    public DBox getLeftBox()
    {
        return leftBox;
    }

    public DBox getTopBox()
    {
        return topBox;
    }

    public DBox getBottomBox()
    {
        return bottomBox;
    }


    public DBox[] getDividedBoxes()
    {
        if (divider == null)
        {
            return new DBox[0];
        }
        else
        {
            return new DBox[]{getFrontBox(), getBackBox(), getTopBox(), getBottomBox(), getLeftBox(), getRightBox()};
        }
    }


}
