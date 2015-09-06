package by.dak.autocad.ocom;

import com.jacob.com.Variant;

import java.lang.reflect.Constructor;

/**
 * User: akoyro
 * Date: 22.02.11
 * Time: 20:20
 */
public enum AcadObjectName
{
    AcDbPolyline(AcadLWPolyline.class),
    AcDbCircle(AcadCircle.class),
    AcDb3dSolid(Acad3DSolid.class),
    AcDb3dPolyline(Acad3DPolyline.class),
    AcDb2dPolyline(AcadPolyline.class),
    AcDbFace(Acad3DFace.class),
    AcDb3PointAngularDimension(AcadDim3PointAngular.class),
    AcDbAlignedDimension(AcadDimAligned.class),
    AcDb2LineAngularDimension(AcadDimAngular.class),
    AcDbDiametricDimension(AcadDimDiametric.class),
    AcDbRadialDimension(AcadDimRadial.class),
    AcDbOrdinateDimension(AcadDimOrdinate.class),
    AcDbRotatedDimension(AcadDimRotated.class),
    AcDbHatch(AcadHatch.class),
    AcDbArc(AcadCircle.class),
    AcDbLine(AcadLine.class),
    AcDbMText(AcadMText.class);


    private Class<? extends AcadObject> acadObjectClass;

    private AcadObjectName(Class<? extends AcadObject> acadObjectClass)
    {
        this.acadObjectClass = acadObjectClass;
    }

    public Class<? extends AcadObject> getAcadObjectClass()
    {
        return acadObjectClass;
    }

    public AcadObject createInstanceBy(Variant createFrom, AcadApplication app)
    {
        try
        {
            Constructor<? extends AcadObject> constructor = getAcadObjectClass().getConstructor(Variant.class, AcadApplication.class);
            return constructor.newInstance(createFrom, app);
        }
        catch (Throwable e)
        {
            throw new IllegalArgumentException(e);
        }
    }
}
