package by.dak.autocad.ocom;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

import java.awt.*;

public class AcadApplication
{
    ActiveXComponent component;
    Dispatch acad;

    public AcadApplication()
    {
        this.component = new ActiveXComponent("AutoCAD.Application");
        this.acad = this.component.getObject();
    }

    public void setVisible(boolean visible)
    {
        this.component.setProperty("Visible", new Variant(visible));
    }

    public AcadDocuments getDocuments()
    {
        return new AcadDocuments(this);
    }

    public AcadDocument getActiveDocument()
    {
        return new AcadDocument(this.component.getProperty("ActiveDocument"), this);
    }

    public void ZoomExtents()
    {
        Dispatch.call(this.acad, "ZoomExtents");
    }

    public void ZoomScaled(double scale)
    {
        ZoomScaled(scale, 0);
    }

    public void ZoomScaled(double scale, int scaleType)
    {
        Dispatch.call(this.acad, "ZoomScaled", new Double(scale), new Integer(scaleType));
    }

    public void quit()
    {
        Dispatch.call(this.acad, "Quit");
    }

    public void safeRelease()
    {
        if (this.component != null)
        {
            this.component.safeRelease();
        }
        if (this.acad != null)
        {
            this.acad.safeRelease();
        }
    }

    public static void main(String[] args)
    {
        AcadApplication app = new AcadApplication();
        app.getDocuments().open("C:\\temp\\template1.dwg");
        AcadDocument ad = app.getActiveDocument();
        AcadModelSpace ms = ad.getModelSpace();

        Rectangle.Double element = new Rectangle.Double(0, 0, 640, 480);


        AcadLWPolyline lwp = ms.AddLightweightPolyline(new Point2D[]{
                new Point2D(element.getMinX(), element.getMinY()),
                new Point2D(element.getMinX(), element.getMaxY()),
                new Point2D(element.getMaxX(), element.getMaxY()),
                new Point2D(element.getMaxX(), element.getMinY())});
        lwp.setClosed(true);
        //lwp.setBulge(2, -0.5D);
        app.setVisible(true);
        app.ZoomExtents();

//     app.setVisible(true);
//
//     Acad3DSolid box1 = ms.AddBox(new Point(0.0D, 0.0D, 0.0D), 5.0D, 5.0D, 1.0D);
//     Acad3DSolid box2 = ms.AddBox(new Point(3.0D, 3.0D, 0.0D), 5.0D, 5.0D, 1.0D);
//     box1.Boolean(AcBooleanType.acUnion, box2);
//     Acad3DSolid tube = ms.AddCylinder(new Point(1.5D, 1.5D, 0.0D), 2.0D, 5.0D);
//     box1.Boolean(AcBooleanType.acSubtraction, tube);
//
//     Point yyy = new Point(0.0D, 0.0D, 700.0D);
//     Acad3DPolyline poly = ms.Add3DPoly(new Point[] { yyy, new Point(0.0D, 740.0D, 700.0D), new Point(1720.0D, 740.0D, 0.0D), new Point(1800.0D, 660.0D, 0.0D), new Point(1800.0D, 0.0D, 0.0D) });
//
//     System.out.println(box1.getObjectName());
//     System.out.println(lwp.getObjectName());
//     System.out.println(poly.getObjectName());
//     System.out.println((AcadEntity)ms.Item(3));
//
//     Vector vec = new Vector();
//     vec.add(lwp);
//
//     AcadRegion r = ms.makeRegion(lwp);
//
//     AcadViewport v = ad.getActiveViewport();
//     v.setDirection(new Direction(0.5D, 1.0D, 0.5D));
//     ad.setActiveViewport(v);
//     app.ZoomExtents();
    }
}
