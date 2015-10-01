package by.dak.autocad.com.event;

import by.dak.cutting.swing.order.data.Milling;
import by.dak.cutting.swing.order.data.OrderDetailsDTO;
import by.dak.cutting.swing.xml.XstreamHelper;
import by.dak.persistence.FacadeContext;
import com.jacob.com.Variant;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;

/**
 * User: akoyro
 * Date: 23.02.11
 * Time: 14:06
 */
public class SaveEventHandler extends AcadApplicationEventHandler
{
    private static final Logger logger = Logger.getLogger(SaveEventHandler.class);

    private OrderDetailsDTO orderDetails;
    private File file;
    private File imageFile;

    public SaveEventHandler(OrderDetailsDTO orderDetails)
    {
        this.orderDetails = orderDetails;
    }

    @Override
    public void EndSave(Variant[] arguments)
    {
        file = new File(FacadeContext.getAutocadFacade().getApplication().getActiveDocument().getFullName());
        imageFile = new File(file.getAbsolutePath() + ".jpg");

        String imageFileName = imageFile.getName();
        Milling milling = FacadeContext.getAutocadFacade().getMilling();
        if (milling.getCurveLength() > 0)
        {
            milling.setFileUuid(file.getName());
            milling.setImageFileUuid(imageFileName);

            FileInputStream fileInputStream = null;
            FileInputStream imageFileInputStream = null;
            try
            {

                fileInputStream = new FileInputStream(file);
                FacadeContext.getRepositoryService().store(fileInputStream, milling.getFileUuid());

                FacadeContext.getAutocadFacade().exportToJPEG(imageFile.getAbsolutePath());
                imageFileInputStream = new FileInputStream(imageFile);
                FacadeContext.getRepositoryService().store(imageFileInputStream, milling.getImageFileUuid());

                orderDetails.setMilling(XstreamHelper.getInstance().toXML(milling));
            }
            catch (Throwable e)
            {
                throw new IllegalArgumentException(e);
            } finally {
                IOUtils.closeQuietly(fileInputStream);
                IOUtils.closeQuietly(imageFileInputStream);
            }
        }
        else if (orderDetails.getMilling() != null)
        {
            orderDetails.setMilling(null);
            FacadeContext.getRepositoryService().delete(milling.getFileUuid());
            FacadeContext.getRepositoryService().delete(milling.getImageFileUuid());
        }

        deleteTempFiles();
    }

    private void deleteFile(File file) {
        try {
            if (!file.delete()) {
                logger.error(String.format("Cannot delete file %s", file));
            }
        } catch (Exception e) {
            logger.error(String.format("Cannot delete file %s", file), e);
        }
    }


    @Override
    public void BeginQuit(Variant[] arguments)
    {
        deleteTempFiles();

        FacadeContext.getAutocadFacade().stopApplication();
    }

    private void deleteTempFiles() {
        try {
            if (file != null) {
                deleteFile(file);
                File backFile = new File(file.getParent(), FilenameUtils.getBaseName(file.getName()) + ".bak");
                if (backFile.exists()) {
                    deleteFile(backFile);
                }
            }


            if (imageFile != null) {
                deleteFile(imageFile);
            }
        } catch (Exception e) {
            logger.error("", e);
        }
    }
}
