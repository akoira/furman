/*
 * CuttingApp.java
 */
package by.dak.cutting;

import by.dak.common.Funcs;
import by.dak.common.swing.EventQueueProxy;
import by.dak.common.swing.ExceptionHandler;
import by.dak.cutting.permision.PermissionManager;
import by.dak.design.settings.SettingsWindowProperty;
import by.dak.design.swing.SettingsPanel;
import by.dak.persistence.FacadeContext;
import by.dak.swing.infonode.RootWindowProperty;
import net.infonode.docking.RootWindow;
import nl.jj.swingx.gui.JSplashWindow;
import nl.jj.swingx.gui.modal.JModalConfiguration;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.Constructor;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The main class of the application.
 */
public final class CuttingApp extends SingleFrameApplication {
    private ExceptionHandler exceptionHandler = new DefaultExceptionHandler();

    public static final String FURMAN_PROFILE  = "FURMAN_PROFILE";
    private static SpringConfiguration springConfiguration;

    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private java.util.List<Future> list = new ArrayList<Future>();
    private JSplashWindow splashWindow;

    private Timer initTimer = new Timer(5, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            synchronized (list) {
                for (Future future : list) {
                    if (!future.isDone()) {
                        return;
                    }
                }
            }

            if (splashWindow != null) splashWindow.setVisible(false);

            //checkLicense();

            CuttingView cuttingView = new CuttingView(CuttingApp.this);
            show(cuttingView);

            initTimer.stop();

            PermissionManager.PERMISSION_MANAGER.login();

            cuttingView.getDailyStatusPanel().setDailysheetEntity(FacadeContext.getDailysheetFacade().loadCurrentDailysheet());
        }
    });

    private void checkLicense() {
        try {
            PermissionManager.PERMISSION_MANAGER.checkLicense();
        } catch (Throwable t) {
            String message = getContext().getResourceMap(CuttingApp.class).getString("Application.msg.license.not.valid");
            JOptionPane.showMessageDialog(splashWindow,
                    message,
                    getContext().getResourceMap(CuttingApp.class).getString("Application.msg.license.not.valid"),
                    JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(CuttingApp.class.getName()).log(Level.SEVERE, t.getMessage(), t);
            System.exit(1);
        }
    }


    public CuttingApp() {
        super();
    }

    /**
     * At startup create and show the main frame of the application.
     */
    @Override
    protected void startup() {

        ImageIcon splashImage = getContext().getResourceMap(CuttingApp.class).getImageIcon("Application.splash.icon");
        splashWindow = new JSplashWindow(splashImage);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (dim.getWidth() / 2.0f - splashWindow.getWidth() / 2f);
        int y = (int) (dim.getHeight() / 2.0f - splashWindow.getHeight() / 2f);
        splashWindow.setLocation(x, y);
        splashWindow.setVisible(true);
    }

    @Override
    protected void initialize(String[] args) {
        initSwings();
        JModalConfiguration.enableWaitOnEDT();

        super.initialize(args);
        getInstance().getContext().getSessionStorage().putProperty(RootWindow.class, new RootWindowProperty());
        getInstance().getContext().getSessionStorage().putProperty(SettingsPanel.class, new SettingsWindowProperty());

        Callable callable = () -> {
            try {
                springConfiguration = SpringConfiguration.sc().get();
                return null;
            } catch (Throwable e) {
                exceptionHandler.handle(e);
                return null;
            }
        };
        synchronized (list) {
            list.add(executorService.submit(callable));
        }

        initTimer.start();
    }

    private void initSwings() {
        EventQueueProxy eventQueueProxy = new EventQueueProxy();
        eventQueueProxy.setExceptionHandler(exceptionHandler);
        Toolkit.getDefaultToolkit().getSystemEventQueue().push(eventQueueProxy);
    }


    public static ClassPathXmlApplicationContext getApplicationContext() {
        return springConfiguration.getApplicationContext();
    }

    /**
     * This method is to initialize the specified window by injecting resources. Windows shown in our application come
     * fully initialized from the GUI builder, so this additional configuration is not needed.
     */
    @Override
    protected void configureWindow(java.awt.Window root) {
        super.configureWindow(root);
    }

    /**
     * A convenient static getter for the application instance.
     *
     * @return the instance of CuttingApp
     */
    public static CuttingApp getApplication() {
        return Application.getInstance(CuttingApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        Locale.setDefault(new Locale("ru", "RU", "utf8"));

        if (args.length > 0 && args[0].equals("--upgrade")) {
            String profile = System.getenv().get(FURMAN_PROFILE);
            Supplier<SpringConfiguration> config = profile == null ? SpringConfiguration.prod_liquibase : SpringConfiguration.profile_liquibase.apply(profile);
            config.get();
        } else {
            Funcs.init_fonts.run();
            launch(CuttingApp.class, args);
        }
    }



    public static void showDialog(Class<? extends JFrame> dialogClass,
                                  HashMap<Class<? extends JFrame>, JFrame> dialogsMap) {
        try {
            // JDialog dialog = dialogsMap.get(dialogClass);
            // if (dialog == null)
            // {
            JFrame mainFrame = getApplication().getMainFrame();
            Constructor<? extends JFrame> constructor = dialogClass.getConstructor();
            JFrame dialog = constructor.newInstance();
            // dialogsMap.put(dialogClass, dialog);
            dialog.setLocationRelativeTo(mainFrame);
            // }

            CuttingApp.getApplication().show(dialog);
            dialog.dispose();
        } catch (Exception ex) {
            CuttingApp.getApplication().getExceptionHandler().handle(ex);
        }
    }

    @Override
    public void exit(EventObject eventObject) {
        super.exit(eventObject);
    }

    public ExceptionHandler getExceptionHandler() {
        if (exceptionHandler == null) {
            exceptionHandler = FacadeContext.getExceptionHandler();
        }
        return exceptionHandler;
    }

    public void setExceptionHandler(ExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    @Override
    public void show(JDialog c) {
        if (CuttingApp.getApplication().getPermissionManager().checkPermission(c)) {
            super.show(c);
        }
    }

    @Override
    public void show(JFrame c) {
        if (CuttingApp.getApplication().getPermissionManager().checkPermission(c)) {
            super.show(c);
        }
    }

    public PermissionManager getPermissionManager() {
        return PermissionManager.PERMISSION_MANAGER;
    }

}
