package by.dak.autocad.com.entity;

import com.jacob.com.Dispatch;

import java.lang.reflect.Constructor;

/**
 * User: akoyro
 * Date: 28.03.11
 * Time: 14:57
 */
public enum EntityName
{
    AcDbPolyline(LWPolyline.class),
    AcDb3dSolid(Solid3D.class),
    AcDb3dPolyline(Polyline3D.class),
    AcDb2dPolyline(Polyline.class),
    AcDbFace(Face3D.class),
    AcDb3PointAngularDimension(Dim3PointAngular.class),
    AcDbAlignedDimension(DimAligned.class),
    AcDb2LineAngularDimension(DimAngular.class),
    AcDbDiametricDimension(DimDiametric.class),
    AcDbRadialDimension(DimRadial.class),
    AcDbOrdinateDimension(DimOrdinate.class),
    AcDbRotatedDimension(DimRotated.class),
    AcDbArcDimension(ArcDimension.class),
    AcDbHatch(Hatch.class),
    AcDbArc(Arc.class),
    AcDbLine(Line.class),
    AcDbMText(MText.class),
    AcDbEllipse(Ellipse.class),
    AcDbCircle(Circle.class),
    AcDbSpline(Spline.class);

    private Class<? extends AEntity> entityClass;

    private EntityName(Class<? extends AEntity> entityClass)
    {
        this.entityClass = entityClass;
    }

    public Class<? extends AEntity> getEntityClass()
    {
        return entityClass;
    }

    public AEntity createInstanceBy(Dispatch dispatch)
    {
        try
        {
            Constructor<? extends AEntity> constructor = getEntityClass().getConstructor(Dispatch.class);
            return constructor.newInstance(dispatch);
        }
        catch (Throwable e)
        {
            throw new IllegalArgumentException(e);
        }
    }
}
