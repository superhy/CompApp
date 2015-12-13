package org.superhy.demo;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.superhy.action.KeywordsProAction;

public class AddKeywordDemo {

	protected Shell shlAddkeyword;
	private Text txtId;
	private Text txtKeywordName;
	static KeywordsProAction keywordsProActionObj = new KeywordsProAction();

	/**
	 * Open the window.
	 * 
	 * @wbp.parser.entryPoint
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlAddkeyword.open();
		shlAddkeyword.layout();
		while (!shlAddkeyword.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlAddkeyword = new Shell();
		shlAddkeyword.addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				e.doit = false;
				shlAddkeyword.setVisible(false);
			}
		});
		shlAddkeyword.setSize(300, 400);
		shlAddkeyword.setText("AddKeyword");

		Label lblId = new Label(shlAddkeyword, SWT.NONE);
		lblId.setBounds(10, 10, 61, 17);
		lblId.setText("id:");

		txtId = new Text(shlAddkeyword, SWT.BORDER);
		txtId.setEnabled(false);
		txtId.setText("auto");
		txtId.setBounds(111, 7, 163, 23);

		Label lblKeywordname = new Label(shlAddkeyword, SWT.NONE);
		lblKeywordname.setBounds(10, 39, 94, 17);
		lblKeywordname.setText("keyword_name:");

		txtKeywordName = new Text(shlAddkeyword, SWT.BORDER);
		txtKeywordName.setBounds(111, 36, 163, 23);

		Button btnAdd = new Button(shlAddkeyword, SWT.NONE);
		btnAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MessageBox box = new MessageBox(shlAddkeyword, SWT.OK);

				// 如果没有输入数据，给予提醒
				if ("".equals(txtKeywordName.getText())) {
					box.setMessage("please input some information !");
					box.open();
					return;
				}

				try {
					if (keywordsProActionObj.addKeyword(txtKeywordName
							.getText()) > 0) {
						box.setMessage("insert successful !");
						box.open();
					}

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnAdd.setBounds(111, 325, 80, 27);
		btnAdd.setText("Add");

		Button btnCancel = new Button(shlAddkeyword, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				e.doit = false;
				shlAddkeyword.setVisible(false);
			}
		});
		btnCancel.setBounds(194, 325, 80, 27);
		btnCancel.setText("Cancel");

	}
}
