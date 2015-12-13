package org.superhy.demo;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.superhy.action.ExegesesProAction;

public class AddExegesisDemo {

	protected Shell shlAddexegesis;
	private Text txtId;
	private Text txtPrestring;
	private Text txtLaststring;
	static ExegesesProAction exegesesProActionObj = new ExegesesProAction();

	/**
	 * Open the window.
	 * 
	 * @wbp.parser.entryPoint
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlAddexegesis.open();
		shlAddexegesis.layout();
		while (!shlAddexegesis.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlAddexegesis = new Shell();
		shlAddexegesis.addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				e.doit = false;
				shlAddexegesis.setVisible(false);
			}
		});
		shlAddexegesis.setSize(300, 400);
		shlAddexegesis.setText("AddExegesis");

		Label lblId = new Label(shlAddexegesis, SWT.NONE);
		lblId.setBounds(10, 10, 61, 17);
		lblId.setText("id:");

		txtId = new Text(shlAddexegesis, SWT.BORDER);
		txtId.setText("auto");
		txtId.setEnabled(false);
		txtId.setBounds(111, 7, 163, 23);

		Label lblPrestring = new Label(shlAddexegesis, SWT.NONE);
		lblPrestring.setBounds(10, 39, 61, 17);
		lblPrestring.setText("pre_string:");

		txtPrestring = new Text(shlAddexegesis, SWT.BORDER);
		txtPrestring.setBounds(111, 36, 163, 23);

		Label lblLaststring = new Label(shlAddexegesis, SWT.NONE);
		lblLaststring.setBounds(10, 68, 61, 17);
		lblLaststring.setText("last_string:");

		txtLaststring = new Text(shlAddexegesis, SWT.BORDER);
		txtLaststring.setBounds(111, 65, 163, 23);

		Button btnAdd = new Button(shlAddexegesis, SWT.NONE);
		btnAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MessageBox box = new MessageBox(shlAddexegesis, SWT.OK);

				// 确保前一个字段不为空，否则给与提醒
				if ("".equals(txtPrestring.getText())) {
					box.setMessage("please input the essential information !");
					box.open();
					return;
				}

				try {
					if ("".equals(txtLaststring.getText())) {
						if (exegesesProActionObj.addExegesis(txtPrestring
								.getText()) > 0) {
							box.setMessage("insert successful !");
							box.open();
						}
					} else {
						if (exegesesProActionObj.addExegesis(
								txtPrestring.getText(), txtLaststring.getText()) > 0) {
							box.setMessage("insert successful !");
							box.open();
						}
					}
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}
		});
		btnAdd.setBounds(111, 325, 80, 27);
		btnAdd.setText("Add");

		Button btnCancel = new Button(shlAddexegesis, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				e.doit = false;
				shlAddexegesis.setVisible(false);
			}
		});
		btnCancel.setBounds(194, 325, 80, 27);
		btnCancel.setText("Cancel");

	}
}
