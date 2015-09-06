package by.dak.view3d;


import javax.swing.*;
import java.awt.*;

/**
 * This is a JInternalFrame holding an universe, which can be configured to
 * be interactive -that is, where user can interact with object- or automatic
 * -where the object spins only-. When in automatic mode, spinning speed is
 * changed so that they look less the same. Changing the spinning start angle
 * helps unsynchronizing the rotations too.
 *
 * @author pepe
 */
public class TestFrame extends JFrame
{

    private View3dController view3dController = new View3dController();

    /**
     * DOCUMENT ME!
     */
    private Component comp;

    /**
     * Creates a new TestFrame object.
     *
     * @param isInteractive tells the world to be constructed as interactive
     * @param isDelayed     tells the rotator to start at a random alpha.
     */
    public TestFrame(boolean isInteractive, boolean isDelayed,
                     boolean isRandom)
    {
        super();
        setSize(1024, 1024);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(new View3dPanel(), BorderLayout.CENTER);
        pack();

//        SimpleUniverse universe = new SimpleUniverse(view3dController.getCanvas()
//                .getOffscreenCanvas3D()); //TODO: this is awful and must not be done like that in final version
//
//
//        // This will move the ViewPlatform back a bit so the
//        // objects in the scene can be viewed.
//        universe.getViewingPlatform().setNominalViewingTransform();
//        universe.getViewer().getView().setMinimumFrameCycleTime(30);
//        universe.addBranchGraph(view3dController.getRootGroup());
    }


}