package by.dak.view3d;

import by.dak.test.TestUtils;

import javax.media.j3d.GraphicsConfigTemplate3D;
import java.awt.*;

/**
 * User: akoyro
 * Date: 03.09.11
 * Time: 17:23
 */
public class Test
{
    public static void main(String[] args)
    {
        View3dPanel view3dPanel = new View3dPanel();
        view3dPanel.init();
        TestUtils.showFrame(view3dPanel, "3D");

//        TestFrame jFrame = new TestFrame(true,true,true);
//        jFrame.addWindowListener(new WindowAdapter()
//        {
//            @Override
//            public void windowClosing(WindowEvent e)
//            {
//                System.exit(0);
//            }
//        });
//        jFrame.getContentPane().setLayout(new BorderLayout());
//        JCanvas3D canvas3D = new JCanvas3D(getTemplate3D());
//                SimpleUniverse universe = new SimpleUniverse(canvas3D.getOffscreenCanvas3D());

//        Box box = new Box(0.5f,0.5f,0.5f,null);
//        box.getLocalToVworld(new Transform3D());
//        BranchGroup group = new BranchGroup();
//        group.addChild(box);
//
//            // Create a red light that shines for 100m from the origin
//    Color3f light1Color = new Color3f(1.8f, 0.1f, 0.1f);
//BoundingBox boundingBox = new BoundingBox(new Point3d(0,0,0), new Point3d(100,100,100));
//        Vector3f light1Direction  = new Vector3f(0.0f, -7.0f, -12.0f);
//    DirectionalLight light1
//      = new DirectionalLight(light1Color, light1Direction);
//    light1.setInfluencingBounds(boundingBox);
//    group.addChild(light1);
//
//        universe.getViewingPlatform().setNominalViewingTransform();
//        universe.addBranchGraph(group);


//
        //jFrame.getContentPane().add(canvas3D,BorderLayout.CENTER);
    }


    private static GraphicsConfiguration getGraphicsConfiguration()
    {

        GraphicsConfigTemplate3D template = getTemplate3D();

        GraphicsConfiguration configuration = GraphicsEnvironment.getLocalGraphicsEnvironment().
                getDefaultScreenDevice().getBestConfiguration(template);
        if (configuration == null)
        {
            configuration = GraphicsEnvironment.getLocalGraphicsEnvironment().
                    getDefaultScreenDevice().getBestConfiguration(new GraphicsConfigTemplate3D());
        }
        return configuration;
    }

    private static GraphicsConfigTemplate3D getTemplate3D()
    {
        GraphicsConfigTemplate3D template = new GraphicsConfigTemplate3D();
        template.setSceneAntialiasing(GraphicsConfigTemplate3D.PREFERRED);
        String stereo = System.getProperty("j3d.stereo");
        if (stereo != null)
        {
            if ("REQUIRED".equals(stereo))
                template.setStereo(GraphicsConfigTemplate.REQUIRED);
            else if ("PREFERRED".equals(stereo))
                template.setStereo(GraphicsConfigTemplate.PREFERRED);
        }
        return template;
    }
}
