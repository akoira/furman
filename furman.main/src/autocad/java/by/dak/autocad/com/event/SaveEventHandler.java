package by.dak.autocad.com.event;

import by.dak.cutting.swing.order.data.Milling;
import by.dak.cutting.swing.order.data.OrderDetailsDTO;
import by.dak.cutting.swing.xml.XstreamHelper;
import by.dak.persistence.FacadeContext;
import com.jacob.com.Variant;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;

/**
 * User: akoyro
 * Date: 23.02.11
 * Time: 14:06
 */
public class SaveEventHandler extends AcadApplicationEventHandler
{
    private OrderDetailsDTO orderDetails;

    public SaveEventHandler(OrderDetailsDTO orderDetails)
    {
        this.orderDetails = orderDetails;
    }

    @Override
    public void EndSave(Variant[] arguments)
    {
        File file = new File(FacadeContext.getAutocadFacade().getApplication().getActiveDocument().getFullName());
        File imageFile = new File(file.getAbsolutePath() + ".jpg");
        String imageFileName = imageFile.getName();
        Milling milling = FacadeContext.getAutocadFacade().getMilling();
        if (milling.getCurveLength() > 0)
        {
            milling.setFileUuid(file.getName());
            milling.setImageFileUuid(imageFileName);
            try
            {

                FileInputStream fileInputStream = new FileInputStream(file);
                FacadeContext.getRepositoryService().store(fileInputStream, milling.getFileUuid());

                FacadeContext.getAutocadFacade().exportToJPEG(imageFile.getAbsolutePath());
                FileInputStream imageFileInputStream = new FileInputStream(imageFile);
                FacadeContext.getRepositoryService().store(imageFileInputStream, milling.getImageFileUuid());

                orderDetails.setMilling(XstreamHelper.getInstance().toXML(milling));
            }
            catch (Throwable e)
            {
                throw new IllegalArgumentException(e);
            }
        }
        else if (orderDetails.getMilling() != null)
        {
            orderDetails.setMilling(null);
            FacadeContext.getRepositoryService().delete(milling.getFileUuid());
            FacadeContext.getRepositoryService().delete(milling.getImageFileUuid());
        }

        FileUtils.deleteQuietly(file);
        FileUtils.deleteQuietly(imageFile);
    }

    @Override
    public void BeginQuit(Variant[] arguments)
    {
        FacadeContext.getAutocadFacade().stopApplication();
    }
}
