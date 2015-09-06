package by.dak.test;

import org.jdesktop.application.SingleFrameApplication;

import javax.swing.*;

public abstract class ATestApplication extends SingleFrameApplication {

	private JFrame frame = new JFrame();


	@Override
	protected void startup() {
		setMainFrame(frame);
		frame.setContentPane(getMainComponent());
		show(getMainFrame());
	}

	public abstract JComponent getMainComponent();

}
