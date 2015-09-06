package by.dak.autocad.ocom;


import com.jacob.com.Dispatch;
import com.jacob.com.SafeArray;
import com.jacob.com.Variant;

public class AcadModelSpace extends AcadObject
{
    AcadModelSpace(Variant createFrom, AcadApplication app)
    {
        super(createFrom, app);
    }

    public Acad3DFace Add3DFace(Point p1, Point p2, Point p3, Point p4)
    {
        return new Acad3DFace(
                Dispatch.call(this.impl.toDispatch(), "Add3DFace", p1.toVariant(), p2.toVariant(), p3.toVariant(), p4.toVariant()),
                this.application);
    }

    public Acad3DFace Add3DFace(Point p1, Point p2, Point p3)
    {
        return new Acad3DFace(
                Dispatch.call(this.impl.toDispatch(), "Add3DFace", p1.toVariant(), p2.toVariant(), p3.toVariant(), p3.toVariant()),
                this.application);
    }

    public Acad3DPolyline Add3DPoly(Point[] points)
    {
        return new Acad3DPolyline(
                Dispatch.call(this.impl.toDispatch(), "Add3DPoly", Point.toArray(points)),
                this.application);
    }

    public Acad3DSolid AddBox(Point origin, double length, double width, double height)
    {
        return new Acad3DSolid(Dispatch.call(this.impl.toDispatch(), "AddBox", origin.toVariant(), new Variant(length), new Variant(width), new Variant(height)), this.application);
    }

    public Acad3DSolid AddExtrudedSolid(AcadRegion r, double length, double taper)
    {
        return new Acad3DSolid(Dispatch.call(this.impl.toDispatch(), "AddExtrudedSolid", r.impl, new Double(length), new Double(taper)), this.application);
    }

    public AcadCircle AddCircle(Point center, double radius)
    {
        return new AcadCircle(Dispatch.call(this.impl.toDispatch(), "AddCircle", center.toVariant(), new Variant(radius)), this.application);
    }

    public Acad3DSolid AddCylinder(Point center, double radius, double height)
    {
        return new Acad3DSolid(Dispatch.call(this.impl.toDispatch(), "AddCylinder", center.toVariant(), new Variant(radius), new Variant(height)), this.application);
    }

    public AcadAttribute AddAttribute(double height, int mode, String prompt, Point insertionPoint, String tag, String value)
    {
        return new AcadAttribute(
                Dispatch.call(this.impl.toDispatch(), "AddAttribute", new Variant(height),
                        new Variant(mode), new Variant(prompt), insertionPoint.toVariant(), new Variant(tag), new Variant(value)),
                this.application);
    }

    public Acad3DSolid AddCone(Point center, double baseRadius, double height)
    {
        return new Acad3DSolid(
                Dispatch.call(this.impl.toDispatch(), "AddCone", center.toVariant(), new Variant(baseRadius), new Variant(height)),
                this.application);
    }

    public AcadObject AddCustomObject(String className)
    {
        return new Acad3DSolid(
                Dispatch.call(this.impl.toDispatch(), "AddCustomObject", className),
                this.application);
    }

    public AcadDim3PointAngular AddDim3PointAngular(Point angleVertex, Point firstEndPoint, Point secondEndPoint, Point textPoint)
    {
        return new AcadDim3PointAngular(
                Dispatch.call(this.impl.toDispatch(), "AddDim3PointAngular", angleVertex.toVariant(), firstEndPoint.toVariant(), secondEndPoint.toVariant(), textPoint.toVariant()),
                this.application);
    }

    public AcadDimAligned AddDimAligned(Point extLine1Point, Point extLine2Point, Point textPosition)
    {
        return new AcadDimAligned(
                Dispatch.call(this.impl.toDispatch(), "AddDimAligned", extLine1Point.toVariant(), extLine2Point.toVariant(), textPosition.toVariant()),
                this.application);
    }

    public AcadDimAngular AddDimAngular(Point angleVertex, Point firstEndPoint, Point secondEndPoint, Point textPoint)
    {
        return new AcadDimAngular(
                Dispatch.call(this.impl.toDispatch(), "AddDimAngular", angleVertex.toVariant(), firstEndPoint.toVariant(), secondEndPoint.toVariant(), textPoint.toVariant()),
                this.application);
    }

    public AcadDimDiametric AddDimDiametric(Point chordPoint, Point farChordPoint, double leaderLength)
    {
        return new AcadDimDiametric(
                Dispatch.call(this.impl.toDispatch(), "AddDimDiametric", chordPoint.toVariant(), farChordPoint.toVariant(), new Variant(leaderLength)),
                this.application);
    }

    public AcadDimOrdinate AddDimOrdinate(Point definitionPoint, Point leaderEndPoint, boolean useXAxis)
    {
        return new AcadDimOrdinate(
                Dispatch.call(this.impl.toDispatch(), "AddDimOrdinate", definitionPoint.toVariant(), leaderEndPoint.toVariant(), new Variant(useXAxis ? 1 : 0)),
                this.application);
    }

    public AcadDimRadial AddDimRadial(Point center, Point chordPoint, double leaderLength)
    {
        return new AcadDimRadial(
                Dispatch.call(this.impl.toDispatch(), "AddDimRadial", center.toVariant(), chordPoint.toVariant(), new Variant(leaderLength)),
                this.application);
    }

    public AcadDimRotated AddDimRotated(Point xLine1Point, Point xLine2Point, Point dimLineLocation, double rotationAngle)
    {
        return new AcadDimRotated(
                Dispatch.call(this.impl.toDispatch(), "AddDimRotated", xLine1Point.toVariant(), xLine2Point.toVariant(), dimLineLocation.toVariant(), new Variant(rotationAngle)),
                this.application);
    }

    public AcadEllipse AddEllipse(Point center, double majorAxis, double radiusRatio)
    {
        return new AcadEllipse(
                Dispatch.call(this.impl.toDispatch(), "AddEllipse", center.toVariant(), new Variant(majorAxis), new Variant(radiusRatio)),
                this.application);
    }

    public Acad3DSolid AddEllipticalCone(Point center, double majorAxis, double minorAxis, double height)
    {
        return new Acad3DSolid(
                Dispatch.call(this.impl.toDispatch(), "AddEllipticalCone", center.toVariant(), new Variant(majorAxis), new Variant(minorAxis), new Variant(height)),
                this.application);
    }

    public Acad3DSolid AddEllipticalCylinder(Point center, double majorAxis, double minorAxis, double height)
    {
        return new Acad3DSolid(
                Dispatch.call(this.impl.toDispatch(), "AddEllipticalCylinder", center.toVariant(), new Variant(majorAxis), new Variant(minorAxis), new Variant(height)),
                this.application);
    }

    public Acad3DSolid AddExtrudedSolidAlongPath(AcadRegion profile, AcadEntity path)
    {
        return new Acad3DSolid(
                Dispatch.call(this.impl.toDispatch(), "AddExtrudedSolidAlongPath", profile.impl, ((BaseAcadEntity) path).impl),
                this.application);
    }

    public AcadHatch AddHatch(int patternType, String patternName, boolean associativity)
    {
        return new AcadHatch(
                Dispatch.call(this.impl.toDispatch(), "AddHatch", new Variant(patternType), patternName, new Variant(associativity)),
                this.application);
    }

    public AcadLeader AddLeader(Point[] pointsArray, String annotation, int type)
    {
        return new AcadLeader(
                Dispatch.call(this.impl.toDispatch(), "AddLeader", Point.toArray(pointsArray), annotation, new Variant(type)),
                this.application);
    }

    public AcadLWPolyline AddLightweightPolyline(Point2D[] vertices)
    {
        return new AcadLWPolyline(Dispatch.call(this.impl.toDispatch(), "AddLightweightPolyline", Point2D.toArray(vertices)), this.application);
    }

    public AcadLine AddLine(Point startPoint, Point endPoint)
    {
        return new AcadLine(
                Dispatch.call(this.impl.toDispatch(), "AddLine", startPoint.toVariant(), endPoint.toVariant()),
                this.application);
    }

    public AcadMLine AddMLine(Point[] vertexList)
    {
        return new AcadMLine(
                Dispatch.call(this.impl.toDispatch(), "AddMLine", Point.toArray(vertexList)),
                this.application);
    }

    public AcadMText AddMText(Point insertionPoint, double width, String text)
    {
        return new AcadMText(
                Dispatch.call(this.impl.toDispatch(), "AddMText", insertionPoint.toVariant(), new Variant(width), text),
                this.application);
    }

    public AcadPoint AddPoint(Point point)
    {
        return new AcadPoint(
                Dispatch.call(this.impl.toDispatch(), "AddPoint", point.toVariant()),
                this.application);
    }

    public AcadPolyfaceMesh AddPolyfaceMesh(Point[] verticesList, int[] faceList)
    {
        SafeArray sa = new SafeArray(faceList.length, 3);
        Variant v = new Variant(sa);
        return new AcadPolyfaceMesh(
                Dispatch.call(this.impl.toDispatch(), "AddPolyfaceMesh", Point.toArray(verticesList), v),
                this.application);
    }

    public AcadPolyline AddPolyline(Point[] verticesList)
    {
        return new AcadPolyline(
                Dispatch.call(this.impl.toDispatch(), "AddPolyline", Point.toArray(verticesList)),
                this.application);
    }

    public AcadRaster AddRaster(String imageFileName, Point insertionPoint, double scaleFactor, double rotationAngle)
    {
        return new AcadRaster(
                Dispatch.call(this.impl.toDispatch(), "AddRaster", imageFileName, insertionPoint.toVariant(), new Variant(scaleFactor), new Variant(rotationAngle)),
                this.application);
    }

    public AcadRay AddRay(Point point1, Point point2)
    {
        return new AcadRay(
                Dispatch.call(this.impl.toDispatch(), "AddRay", point1.toVariant(), point2.toVariant()),
                this.application);
    }

    public AcadRegion AddRegion(AcadObject[] objectList)
    {
        if ((objectList[0] instanceof AcadLWPolyline))
        {
            return makeRegion(objectList[0]);
        }
        return null;
    }

    AcadRegion makeRegion(AcadObject object)
    {
        Variant v = Dispatch.call(object.impl.toDispatch(), "Explode");

        SafeArray s = v.toSafeArray(false);

        Variant regions = Dispatch.call(this.impl.toDispatch(), "AddRegion", v);

        SafeArray sa = v.toSafeArray(false);
        for (int i = sa.getLBound(); i < sa.getUBound() + 1; i++)
        {
            Dispatch.call(sa.getVariant(i).toDispatch(), "Delete");
        }
        sa = regions.toSafeArray(false);
        return new AcadRegion(sa.getVariant(sa.getLBound()), this.application);
    }

    public Acad3DSolid AddRevolvedSolid(AcadRegion profile, Point axisPoint, Point axisDir, double angle)
    {
        return new Acad3DSolid(
                Dispatch.call(this.impl.toDispatch(), "AddRegion", profile.impl, axisPoint.toVariant(), axisDir.toVariant(), new Variant(angle)),
                this.application);
    }

    public AcadShape AddShape(String name, Point insertionPoint, double scaleFactor, double rotation)
    {
        return new AcadShape(
                Dispatch.call(this.impl.toDispatch(), "AddShape", name, insertionPoint.toVariant(), new Variant(scaleFactor), new Variant(rotation)),
                this.application);
    }

    public AcadSolid AddSolid(Point point1, Point point2, Point point3, Point point4)
    {
        return new AcadSolid(
                Dispatch.call(this.impl.toDispatch(), "AddSolid", point1.toVariant(), point2.toVariant(), point3.toVariant(), point4.toVariant()),
                this.application);
    }

    public Acad3DSolid AddSphere(Point center, double radius)
    {
        return new Acad3DSolid(
                Dispatch.call(this.impl.toDispatch(), "AddSphere", center.toVariant(), new Variant(radius)),
                this.application);
    }

    public AcadSpline AddSpline(Point[] pointsArray, Point startTangent, Point endTangent)
    {
        return new AcadSpline(
                Dispatch.call(this.impl.toDispatch(), "AddSpline", Point.toArray(pointsArray), startTangent.toVariant(), endTangent.toVariant()),
                this.application);
    }

    public AcadText AddText(String textString, Point insertionPoint, double height)
    {
        return new AcadText(
                Dispatch.call(this.impl.toDispatch(), "AddText", textString, insertionPoint.toVariant(), new Variant(height)),
                this.application);
    }

    public AcadTolerance AddTolerance(String text, Point insertionPoint, double height)
    {
        return new AcadTolerance(
                Dispatch.call(this.impl.toDispatch(), "AddTolerance", text, insertionPoint.toVariant(), new Variant(height)),
                this.application);
    }

    public Acad3DSolid AddTorus(Point center, double torusRadius, double tubeRadius)
    {
        return new Acad3DSolid(
                Dispatch.call(this.impl.toDispatch(), "AddTorus", center.toVariant(), new Variant(torusRadius), new Variant(tubeRadius)),
                this.application);
    }

    public AcadTrace AddTrace(Point[] pointsArray)
    {
        return new AcadTrace(
                Dispatch.call(this.impl.toDispatch(), "AddTrace", Point.toArray(pointsArray)),
                this.application);
    }

    public Acad3DSolid AddWedge(Point center, double length, double width, double height)
    {
        return new Acad3DSolid(
                Dispatch.call(this.impl.toDispatch(), "AddWedge", center.toVariant(), new Variant(length), new Variant(width), new Variant(height)),
                this.application);
    }

    public Acad3DSolid AddXLine(Point point1, Point point2)
    {
        return new Acad3DSolid(
                Dispatch.call(this.impl.toDispatch(), "AddXLine", point1.toVariant(), point2.toVariant()),
                this.application);
    }

    public AcadExternalReference AttachExternalReference(String pathName, String name, Point insertionPoint, double xScale, double yScale, double zScale, double rotation, boolean bOverlay)
    {
        return new AcadExternalReference(
                Dispatch.call(this.impl.toDispatch(), "AttachExternalReference", pathName, name, insertionPoint.toVariant(), new Variant(xScale), new Variant(yScale), new Variant(rotation), new Variant(bOverlay)),
                this.application);
    }

    public AcadBlockReference InsertBlock(Point insertionPoint, String name, double xScale, double yScale, double zScale, double rotation)
    {
        return new AcadBlockReference(
                Dispatch.call(this.impl.toDispatch(), "InsertBlock", new Variant(xScale), new Variant(yScale), new Variant(zScale), new Variant(rotation)),
                this.application);
    }

    public AcadObject getItem(Integer index)
    {
        Variant v = Dispatch.call(this.impl.toDispatch(), "Item", new Variant(index));
        String name = Dispatch.get(v.toDispatch(), "ObjectName").toString();

        AcadObjectName objectName = AcadObjectName.valueOf(name);
        return objectName.createInstanceBy(v, this.application);
    }

    public Integer getCount()
    {
        return Dispatch.get(impl.getDispatch(), "Count").getInt();
    }
}




