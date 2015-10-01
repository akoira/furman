package by.dak.autocad;

import by.dak.autocad.com.*;
import by.dak.autocad.com.entity.AEntity;
import by.dak.autocad.com.entity.ILength;
import by.dak.autocad.com.entity.LWPolyline;
import by.dak.autocad.com.event.SaveEventHandler;
import by.dak.cutting.swing.order.data.Milling;
import by.dak.cutting.swing.order.data.OrderDetailsDTO;
import by.dak.cutting.swing.xml.XstreamHelper;
import by.dak.persistence.FacadeContext;
import by.dak.persistence.entities.AOrder;
import by.dak.persistence.entities.OrderFurniture;
import by.dak.repository.IRepositoryService;
import by.dak.swing.image.FileChooserProperty;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import javax.swing.FocusManager;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.UUID;

/**
 * User: akoyro
 * Date: 28.03.11
 * Time: 10:20
 */
public class AutocadFacade {
	private IRepositoryService repositoryService;

	private AutocadDelegator autocadDelegator;
	private Application application;

	public Milling getMilling() {
		Milling milling = new Milling();
		Document document = getApplication().getActiveDocument();
		Blocks blocks = document.getBlocks();
		Block block = blocks.Item(0);
		int count = block.getCount();
		for (int k = 0; k < count; k++) {
			AEntity entity = block.Item(k);
			if (entity instanceof ILength) {
				if (entity.isCurve() || entity.getColor() != 256) {
					milling.setCurveLength(milling.getCurveLength() + ((ILength) entity).getLength());
					if (entity.getLinetype() == Linetype.ACAD_ISO06W100) {
						milling.setCurveGluingLength(milling.getCurveGluingLength() + ((ILength) entity).getLength());
					}
				} else {
					milling.setDirectLength(milling.getCurveLength() + ((ILength) entity).getLength());
					if (entity.getLinetype() == Linetype.ACAD_ISO06W100) {
						milling.setDirectGluingLength(milling.getDirectGluingLength() + ((ILength) entity).getLength());
					}
				}
			}
		}
		return milling;


	}

	public void openMilling(OrderDetailsDTO orderDetails) {
		String smilling = orderDetails.getMilling();
		Milling milling = (Milling) XstreamHelper.getInstance().fromXML(smilling);
		try {
			File file = repositoryService.readTempFile(milling.getFileUuid());
			openDocument(orderDetails, file);
		} catch (Throwable e) {
			FacadeContext.getExceptionHandler().handle(e);
			newMilling(orderDetails);
		}
	}

	public void newMilling(OrderDetailsDTO orderDetails) {
		String suuid = UUID.randomUUID().toString() + ".dwg";
		File file = new File(System.getProperty("java.io.tmpdir"), suuid);
		InputStream inputStream = null;
		FileOutputStream outputStream = null;
		try {
			inputStream = AutocadFacade.class.getResourceAsStream("resources/template.dwg");
			outputStream = new FileOutputStream(file);
			IOUtils.copy(inputStream, outputStream);
		} catch (Throwable e) {
			throw new IllegalArgumentException(e);
		} finally {
			IOUtils.closeQuietly(inputStream);
			IOUtils.closeQuietly(outputStream);
		}
		openDocument(orderDetails, file);
		createRectangle(orderDetails);
	}

	private void openDocument(OrderDetailsDTO orderDetails, File file) {
		getApplication().getDocuments().Open(file.getAbsolutePath());
		getApplication().getActiveDocument().SetVariable(Application.SYS_PELLIPSE, 1);
	}

	public void startApplication(OrderDetailsDTO orderDetails) {
		if (getAutocadDelegator() != null) {
			getAutocadDelegator().applicationStarting();
		}
		application = new Application();
		getApplication().addListener(new SaveEventHandler(orderDetails));
		application.setVisible(true);
		waitIdle();
	}

	public void stopApplication() {
		if (getAutocadDelegator() != null) {
			getAutocadDelegator().applicationStopped(getApplication());
		}
		application = null;
		autocadDelegator = null;
	}


	public void createRectangle(OrderDetailsDTO orderDetails) {
		Document ad = getApplication().getActiveDocument();
		ModelSpace ms = ad.getModelSpace();
		Rectangle.Double element = new Rectangle.Double(0, 0, orderDetails.getLength(), orderDetails.getWidth());
		LWPolyline lwp = ms.AddLightweightPolyline(new Point2D[]{
				new Point2D(element.getMinX(), element.getMinY()),
				new Point2D(element.getMinX(), element.getMaxY()),
				new Point2D(element.getMaxX(), element.getMaxY()),
				new Point2D(element.getMaxX(), element.getMinY())});
		lwp.setClosed(true);
	}

	public void exportToJPEG(String file) {
		Document document = getApplication().getActiveDocument();
		int sysBACKGROUNDPLOT = document.GetVariable(Application.SYS_BACKGROUNDPLOT).getInt();
		document.SetVariable(Application.SYS_BACKGROUNDPLOT, 0);
		PlotConfigurations plotConfigurations = document.getPlotConfigurations();
		PlotConfiguration configuration = plotConfigurations.Add("JPG", false);
		configuration.setConfigName(PlotConfiguration.CONFIG_NAME_PublishToWeb_JPG);
		//"VGA_(480.00_x_640.00_Pixels)"
		//configuration.setCanonicalMediaName("ARCH_D_(24.00_x_36.00_Inches)");
		configuration.setCanonicalMediaName("VGA_(480.00_x_640.00_Pixels)");
		document.Regen();
		Plot plot = document.getPlot();

		plot.PlotToFile(file, configuration.getConfigName());

		document.SetVariable(Application.SYS_BACKGROUNDPLOT, sysBACKGROUNDPLOT);
	}

	public void waitIdle() {
		AcadState acadState = getApplication().GetAcadState();

		while (!acadState.IsQuiescent()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				return;
			}
		}
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	public AutocadDelegator getAutocadDelegator() {
		return autocadDelegator;
	}

	public void setAutocadDelegator(AutocadDelegator autocadDelegator) {
		this.autocadDelegator = autocadDelegator;
	}


	public void exportAutocadFiles(AOrder order, File exportDir, String buttonText) {
		try {
			final JFileChooser fc = new JFileChooser();
			org.jdesktop.application.Application.getInstance().getContext().getSessionStorage().putProperty(JFileChooser.class, new FileChooserProperty());
			fc.setName("AutocadFiles");
			org.jdesktop.application.Application.getInstance().getContext().getSessionStorage().restore(fc, fc.getName());
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fc.setAcceptAllFileFilterUsed(false);
			int result = fc.showDialog(FocusManager.getCurrentManager().getActiveWindow(), buttonText);
			if (result == JFileChooser.APPROVE_OPTION) {
				fc.getParent().remove(fc);
				org.jdesktop.application.Application.getInstance().getContext().getSessionStorage().save(fc, fc.getName());
				exportDir = fc.getSelectedFile();
				java.util.List<OrderFurniture> list = FacadeContext.getOrderFurnitureFacade().findWithMillingBy(order);
				for (OrderFurniture orderFurniture : list) {
					Milling milling = (Milling) XstreamHelper.getInstance().fromXML(orderFurniture.getMilling());
					File file = FacadeContext.getRepositoryService().readTempFile(milling.getFileUuid());
					File target = new File(exportDir, StringUtils.join(new String[]{order.getNumber().getStringValue(), orderFurniture.getName(), orderFurniture.getNumber().toString(), "dwg"}, '.'));
					FileUtils.moveFile(file, target);
					target.deleteOnExit();
					FileUtils.deleteQuietly(file);
				}
			}
		} catch (Throwable e) {
			FacadeContext.getExceptionHandler().handle(e);
		}
	}

	public IRepositoryService getRepositoryService() {
		return repositoryService;
	}

	public void setRepositoryService(IRepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}
}
