package by.dak.cutting.permision;

import by.dak.cutting.CuttingApp;
import by.dak.cutting.NewDayManager;
import by.dak.license.LicenseChecker;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.Dailysheet;
import by.dak.persistence.entities.Employee;
import org.apache.commons.lang.StringUtils;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.swingx.JXLoginPane;
import org.jdesktop.swingx.auth.LoginService;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 20.02.2010
 * Time: 11:02:55
 * To change this template use File | Settings | File Templates.
 */
public class PermissionManager
{
    public static final PermissionManager PERMISSION_MANAGER = new PermissionManager();

    private ResourceMap resourceMap = Application.getInstance().getContext().getResourceMap(PermissionManager.class);
    private LoginService loginService = new LoginService("Cutting")
    {
        @Override
        public boolean authenticate(String name, char[] password, String server) throws Exception
        {
            Employee employee = FacadeContext.getEmployeeFacade().findUniqueByField("userName", name);
            if (employee != null)
            {
                if (employee.getPassword().equals(new String(password)))
                {
                    FacadeContext.setEmployee(employee);
                    return true;
                }
            }
            return false;
        }
    };


    public void login1()
    {
        try
        {
            loginService.authenticate("director", "director".toCharArray(), null);
            NewDayManager newDayManager = new NewDayManager();
            Dailysheet dailysheet = newDayManager.checkDailysheet();
        }
        catch (Exception e)
        {
            throw new IllegalStateException(e);
        }
    }

    public void login()
    {
        JXLoginPane pane = new JXLoginPane(loginService);
        JXLoginPane.Status status = JXLoginPane.showLoginDialog(CuttingApp.getApplication().getMainFrame(), pane);
        if (status == JXLoginPane.Status.SUCCEEDED)
        {
            Runnable runnable = new Runnable()
            {
                public void run()
                {
                    NewDayManager newDayManager = new NewDayManager();
                    Dailysheet dailysheet = newDayManager.checkDailysheet();
                    if (dailysheet == null)
                    {
                        Application.getInstance().exit();
                    }
                    if (!newDayManager.checkCurrency(dailysheet))
                    {
                        JOptionPane.showMessageDialog(((SingleFrameApplication) Application.getInstance()).getMainFrame(),
                                resourceMap.getString("permission.title.currency.empty"),
                                resourceMap.getString("permission.message.currency.empty"),
                                JOptionPane.ERROR_MESSAGE);
                        CuttingApp.getApplication().exit();
                    }

                }
            };
            SwingUtilities.invokeLater(runnable);
        }
        else
        {
            Application.getInstance().exit();
        }
    }

    public boolean checkPermission(Object o)
    {
        return checkPermission(o, true);
    }


    public boolean checkPermission(Object o, boolean showDenyMessage)
    {
        String message = resourceMap.getString("permission.message.deny");
        String title = resourceMap.getString("permission.title.deny");

        boolean deny = false;
        if (FacadeContext.getEmployee() != null)
        {
            String department = FacadeContext.getEmployee().getDepartment().getName();
            try
            {
                Role role = Role.valueOf(department);
                String permissions = resourceMap.getString("permission." + role.name() + ".deny");
                List<String> lPermissions = Arrays.asList(StringUtils.split(permissions, ','));
                String s;
                if (o instanceof String)
                    s = (String) o;
                else
                    s = o.getClass().getSimpleName();

                deny = lPermissions.contains(s);
                if (!deny && o instanceof Component)
                {
                    deny = lPermissions.contains(((Component) o).getName());
                }
            }
            catch (Exception e)
            {
                deny = true;
                //e.printStackTrace();
            }
            if (deny && showDenyMessage)
            {
                JOptionPane.showMessageDialog(CuttingApp.getApplication().getMainFrame(), message, title, JOptionPane.WARNING_MESSAGE);
            }
        }
        return !deny;
    }

    public void checkLicense()
    {
        LicenseChecker licenseChecker = new LicenseChecker();
        licenseChecker.check();
    }

}
