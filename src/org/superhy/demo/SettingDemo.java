package org.superhy.demo;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.superhy.service.SettingService;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;

public class SettingDemo {
	static Display display = Display.getCurrent();
	static Shell shlSettingDemo = new Shell(display);
	private Text textFileAnsLocation;

	SettingDemo settingDemo = this;

	private String fileAnsPath;

	public void open() {
		shlSettingDemo.open();
		shlSettingDemo.layout();
		while (!shlSettingDemo.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Launch the application.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public SettingDemo() throws Exception {
		// 覆写窗体关闭事件，使窗体可以重复打开
		shlSettingDemo.addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				e.doit = false;
				shlSettingDemo.setVisible(false);
			}
		});
		shlSettingDemo.setText("Setting Demo");
		shlSettingDemo.setSize(450, 160);

		fileAnsPath = new SettingService().getNewSetting().getLocation_ans();
		textFileAnsLocation = new Text(shlSettingDemo, SWT.BORDER);
		textFileAnsLocation.setText(fileAnsPath);
		textFileAnsLocation.setBounds(10, 10, 260, 23);

		Button btnOpen = new Button(shlSettingDemo, SWT.NONE);
		btnOpen.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fileAnsDialog = new FileDialog(shlSettingDemo,
						SWT.SAVE);
				fileAnsDialog.setFilterPath("c:/windows");
				if ((fileAnsPath = fileAnsDialog.open()) != null) {
					textFileAnsLocation.setText(fileAnsPath);
				}
			}
		});
		btnOpen.setBounds(306, 8, 80, 27);
		btnOpen.setText("Open");

		Button btnApply = new Button(shlSettingDemo, SWT.NONE);
		btnApply.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					new SettingService().updateSetting("location_ans",
							fileAnsPath);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnApply.setBounds(252, 85, 80, 27);
		btnApply.setText("Apply");

		Button btnCancel = new Button(shlSettingDemo, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				fileAnsPath = null;
				textFileAnsLocation.setText("");
			}
		});
		btnCancel.setBounds(344, 85, 80, 27);
		btnCancel.setText("Cancel");
	}
}
