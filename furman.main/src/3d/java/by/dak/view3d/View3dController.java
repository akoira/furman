package by.dak.view3d;

import by.dak.model3d.DBox;
import by.dak.model3d.DBoxFacade;
import com.sun.j3d.exp.swing.JCanvas3D;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseWheelZoom;
import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.media.j3d.*;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;
import java.awt.*;

/**
 * User: akoyro
 * Date: 12.09.11
 * Time: 11:48
 */
public class View3dController
{
    private View3dPanel view3dPanel;


    private JCanvas3D canvas;

    private float scale;

    private DBoxFacade boxFacade;
    private Node3dFactory node3dFactory = new Node3dFactory();


    private BranchGroup rootGroup;
    private TransformGroup userObjectsGroup;
    private SimpleUniverse universe;


    public void init()
    {
        initCanvas3D();
        //should be added before created SimpleUniverse, else second windows has been created
        if (view3dPanel != null)
        {
            view3dPanel.add(getCanvas(), BorderLayout.CENTER);
        }

        scale = 1f / (float) (Math.max(Math.max(boxFacade.getRoot().getWidth(), boxFacade.getRoot().getLength()), boxFacade.getRoot().getHeight()) * 0.75);
        initUniverse();
        reloadRootGroup();

    }

    public void reloadRootGroup()
    {
        if (rootGroup != null)
        {
            universe.cleanup();
        }
        initRootGroup();
        addNodes();
        universe.addBranchGraph(getRootGroup());
    }

    /**
     * Creates the world. Only exists to cleanup the source a bit
     *
     * @return a global branchgroup containing the world, as desired.
     */
    private void initRootGroup()
    {
        // Create the root of the branch graph
        rootGroup = new BranchGroup();
        //objRoot.setBounds(new BoundingBox(new Point3d(-3000,-3000,-3000), new Point3d(3000,3000,3000)));

        // Create the TransformGroup node and initialize it to the
        // identity. Enable the TRANSFORM_WRITE capability so that
        // our behavior code can modify it at run time. Add it to
        // the root of the subgraph.
        TransformGroup objTrans = new TransformGroup();
        Transform3D t3dTrans = new Transform3D();
        t3dTrans.setScale(scale);
        objTrans.setTransform(t3dTrans);
        rootGroup.addChild(objTrans);


        userObjectsGroup = new TransformGroup();
        userObjectsGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        objTrans.addChild(userObjectsGroup);

        addAxises();

        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,
                0.0, 0.0), 100.0);
        addLightNodes(bounds);

        addMouseBehaviors(bounds);
    }


    private void initUniverse()
    {
        universe = new SimpleUniverse(this.getCanvas()
                .getOffscreenCanvas3D());

        // This will move the ViewPlatform back a bit so the
        // objects in the scene can be viewed.
        universe.getViewingPlatform().setNominalViewingTransform();
        universe.getViewer().getView().setMinimumFrameCycleTime(30);
    }

    public BranchGroup getRootGroup()
    {
        return rootGroup;
    }


    public void initCanvas3D()
    {
        canvas = new JCanvas3D(new GraphicsConfigTemplate3D());
        canvas.setResizeMode(canvas.RESIZE_DELAYED);

        Dimension dim = new Dimension(640, 640);
        canvas.setPreferredSize(dim);
        canvas.setSize(dim);
    }

    public float getScale()
    {
        return scale;
    }

    public void setScale(float scale)
    {
        this.scale = scale;
    }


    private void addMouseBehaviors(BoundingSphere bounds)
    {
        final MouseRotate mr = new MouseRotate(canvas, userObjectsGroup);
        mr.setSchedulingBounds(bounds);
        mr.setSchedulingInterval(1);
        rootGroup.addChild(mr);

        MouseWheelZoom mouseWheelZoom = new MouseWheelZoom(canvas, userObjectsGroup);
        mr.setSchedulingBounds(bounds);
        mr.setSchedulingInterval(1);
        rootGroup.addChild(mouseWheelZoom);
    }

    private void addLightNodes(BoundingSphere bounds)
    {
        // Set up the ambient light
        Color3f ambientColor = new Color3f(0.3f, 0.3f, 0.3f);
        AmbientLight ambientLightNode = new AmbientLight(ambientColor);
        ambientLightNode.setInfluencingBounds(bounds);
        rootGroup.addChild(ambientLightNode);

        // Set up the directional lights
        Color3f light1Color = new Color3f(1.0f, 1.0f, 0.9f);
        Vector3f light1Direction = new Vector3f(1.0f / scale, 1.0f / scale, 1.0f / scale);
        Color3f light2Color = new Color3f(1.0f, 1.0f, 0.9f);
        Vector3f light2Direction = new Vector3f(-1.0f / scale, -1.0f / scale, -1.0f / scale);

        DirectionalLight light1 = new DirectionalLight(light1Color,
                light1Direction);
        light1.setInfluencingBounds(bounds);
        rootGroup.addChild(light1);

        DirectionalLight light2 = new DirectionalLight(light2Color,
                light2Direction);
        light2.setInfluencingBounds(bounds);
        rootGroup.addChild(light2);
    }

    private void addAxises()
    {
        userObjectsGroup.addChild(new Axis());
        addSmallBoxes();
    }

    private void addSmallBoxes()
    {
        for (int i = 0; i < 50; i++)
        {
            DBox box = new DBox();
            box.setX(0);
            box.setY(0);
            box.setZ(i * 50);
            box.setLength(10);
            box.setHeight(50);
            box.setWidth(2);
            userObjectsGroup.addChild(node3dFactory.getNodeBy(box, false, false));
        }
    }

    public JCanvas3D getCanvas()
    {
        return canvas;
    }


    private void addNodes()
    {
        Node node = node3dFactory.getNodeBy(boxFacade.getRoot(), true, false);
        userObjectsGroup.addChild(node);

        DBox divider = boxFacade.getRoot().getBoxDivideHandler().getDivider();
        if (divider != null)
        {
            addDivider(divider);
        }
    }

    private void addDivider(DBox divider)
    {
        Node node = node3dFactory.getNodeBy(divider, false, false);
        userObjectsGroup.addChild(node);

        if (divider != null)
        {
            DBox[] boxes = divider.getParent().getBoxDivideHandler().getDividedBoxes();
            for (DBox box : boxes)
            {
                if (box.getBoxDivideHandler().getDivider() != null)
                {
                    addDivider(box.getBoxDivideHandler().getDivider());
                }
            }
        }
    }


    public View3dPanel getView3dPanel()
    {
        return view3dPanel;
    }

    public void setView3dPanel(View3dPanel view3dPanel)
    {
        this.view3dPanel = view3dPanel;
    }

    public DBoxFacade getBoxFacade()
    {
        return boxFacade;
    }

    public void setBoxFacade(DBoxFacade boxFacade)
    {
        this.boxFacade = boxFacade;
    }

}

