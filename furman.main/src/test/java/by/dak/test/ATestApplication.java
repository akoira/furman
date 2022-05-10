package by.dak.test;

import by.dak.cutting.SpringConfiguration;
import by.dak.persistence.MainFacade;
import org.jdesktop.application.SingleFrameApplication;

import javax.swing.*;

public abstract class ATestApplication extends SingleFrameApplication {

	protected JFrame frame = new JFrame();

	protected SpringConfiguration springConfiguration;
	protected MainFacade mainFacade;

	@Override
	protected void initialize(String[] args) {
		springConfiguration = SpringConfiguration.sc().get();
		mainFacade = springConfiguration.getMainFacade();
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (Exception e) {
			mainFacade.getExceptionHandler().handle(e);
		}
	}

	@Override
	protected void startup() {
		setMainFrame(frame);
		frame.setContentPane(getMainComponent());
		show(getMainFrame());
	}

	public abstract JComponent getMainComponent();

}
