package by.dak.view3d;

import by.dak.model3d.DBox;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Sphere;

import javax.media.j3d.*;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;
import java.awt.*;
import java.util.Random;

/**
 * User: akoyro
 * Date: 12.09.11
 * Time: 11:18
 */
public class Node3dFactory
{
    private Random random = new Random();

    public Node getNodeBy(DBox box, boolean polygon, boolean showCenter)
    {
        return getBox3D(box, polygon, showCenter);
    }

    private Appearance getAppearancePolygon(boolean polygon)
    {
        Appearance appearance = new Appearance();
        Material material = new Material();
        material.setDiffuseColor(getMaterialColor());
        appearance.setMaterial(material);

        if (polygon)
        {
            PolygonAttributes polattr = new PolygonAttributes();
            polattr.setPolygonMode(PolygonAttributes.POLYGON_LINE);
            appearance.setPolygonAttributes(polattr);
        }
        return appearance;
    }

    private Node getBox3D(DBox dBox, boolean polygon, boolean showCenter)
    {
        if (dBox.getLength() <= 0 ||
                dBox.getHeight() <= 0 ||
                dBox.getWidth() <= 0)
            return null;
        final Box box = new Box((float) dBox.getLength() / 2f,
                (float) dBox.getHeight() / 2f,
                (float) dBox.getWidth() / 2f,
                getAppearancePolygon(polygon));
        TransformGroup tg = new TransformGroup();
        Transform3D transform = new Transform3D();
        //position
        Vector3d vector = new Vector3d(dBox.getX(), dBox.getY(), dBox.getZ());
        transform.setTranslation(vector);
        //angle

        Transform3D tAngle = new Transform3D();
        tAngle.rotX(dBox.getAngleX());
        transform.mul(tAngle);

        tAngle = new Transform3D();
        tAngle.rotY(dBox.getAngleY());
        transform.mul(tAngle);

        tAngle = new Transform3D();
        tAngle.rotZ(dBox.getAngleZ());
        transform.mul(tAngle);


//        Matrix3f angle = new Matrix3f(
//                (dBox.getAngleX() == 0 ? 1f : dBox.getAngleX()), 0f, 0f,
//                0f, (dBox.getAngleY() == 0 ? 1f : dBox.getAngleY()), 0f,
//                0f, 0f, (dBox.getAngleZ() == 0 ? 1f : dBox.getAngleZ()));

        tg.setTransform(transform);
        tg.addChild(box);

        if (showCenter)
        {
            Sphere center = getCenterSphere(dBox);
            tg.addChild(center);
        }

        return tg;
    }

    private Sphere getCenterSphere(DBox dBox)
    {
        Sphere center = new Sphere((float) dBox.getWidth() / 10f);
        Appearance appearance = new Appearance();
        Material material = new Material();
        material.setDiffuseColor(getMaterialColor());
        appearance.setMaterial(material);
        center.setAppearance(appearance);
        return center;
    }


    public Color3f getMaterialColor()
    {
        final float hue = random.nextFloat();
// Saturation between 0.1 and 0.3
        final float saturation = (random.nextInt(2000) + 1000) / 10000f;
        final float luminance = 0.9f;
        final Color color = Color.getHSBColor(hue, saturation, luminance);

        return new Color3f(color);

    }

}
