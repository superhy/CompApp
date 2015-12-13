package org.superhy.demo;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.swt.widgets.Text;

public class AboutDemo {

	protected Shell shlAbout;
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private Text txtPoweredByAilab;

	/**
	 * Open the window.
	 * @wbp.parser.entryPoint
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlAbout.open();
		shlAbout.layout();
		while (!shlAbout.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlAbout = new Shell();
		shlAbout.addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				e.doit = false;
				shlAbout.setVisible(false);
			}
		});
		shlAbout.setSize(450, 300);
		shlAbout.setText("About");
		
		Label lblAbout = new Label(shlAbout, SWT.NONE);
		lblAbout.setImage(SWTResourceManager.getImage("./image/about.gif"));
		lblAbout.setBounds(10, 10, 101, 104);
		
		txtPoweredByAilab = new Text(shlAbout, SWT.BORDER | SWT.READ_ONLY | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL);
		txtPoweredByAilab.setEditable(false);
		txtPoweredByAilab.setEnabled(false);
		txtPoweredByAilab.setText("Powered By AiLab_403 of KMUST\r\nID : 1.0beta\r\n2013.10(1)\r\n\r\nEmail : superhy199148@hotmail.com");
		txtPoweredByAilab.setToolTipText("");
		txtPoweredByAilab.setBounds(117, 10, 307, 241);
		formToolkit.adapt(txtPoweredByAilab, true, true);

	}
}
