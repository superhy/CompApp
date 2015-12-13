package org.superhy.demo;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.superhy.action.SepcharsProAction;

public class AddSepcharDemo {

	protected Shell shlAddsepchar;
	private Text txtId;
	private Text txtSepcharname;
	private Combo comboSepchartype;
	static SepcharsProAction sepcharsProActionObj = new SepcharsProAction();

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			AddSepcharDemo window = new AddSepcharDemo();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlAddsepchar.open();
		shlAddsepchar.layout();
		while (!shlAddsepchar.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlAddsepchar = new Shell();
		shlAddsepchar.addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				e.doit = false;
				shlAddsepchar.setVisible(false);
			}
		});
		shlAddsepchar.setSize(300, 400);
		shlAddsepchar.setText("AddSepchar");

		Label lblId = new Label(shlAddsepchar, SWT.NONE);
		lblId.setBounds(10, 10, 61, 17);
		lblId.setText("id:");

		txtId = new Text(shlAddsepchar, SWT.BORDER);
		txtId.setText("auto");
		txtId.setEnabled(false);
		txtId.setBounds(111, 7, 163, 23);

		Label lblSepcharname = new Label(shlAddsepchar, SWT.NONE);
		lblSepcharname.setBounds(10, 39, 93, 17);
		lblSepcharname.setText("sepchar_name:");

		txtSepcharname = new Text(shlAddsepchar, SWT.BORDER);
		txtSepcharname.setBounds(111, 36, 163, 23);

		Label lblSepchartype = new Label(shlAddsepchar, SWT.NONE);
		lblSepchartype.setBounds(10, 68, 93, 17);
		lblSepchartype.setText("sepchar_type:");

		comboSepchartype = new Combo(shlAddsepchar, SWT.READ_ONLY);
		comboSepchartype.setItems(new String[] { "op", "sep" });
		comboSepchartype.setBounds(111, 65, 163, 25);
		comboSepchartype.select(0);

		Button btnAdd = new Button(shlAddsepchar, SWT.NONE);
		btnAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MessageBox box = new MessageBox(shlAddsepchar, SWT.OK);

				// 如果没有输入数据，给予提醒
				if ("".equals(txtSepcharname.getText())
						|| "".equals(comboSepchartype.getText())) {
					box.setMessage("please input the essential information !");
					box.open();
					return;
				}

				try {
					if (sepcharsProActionObj.addSepchar(
							txtSepcharname.getText(),
							comboSepchartype.getText()) > 0) {
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

		Button btnCancel = new Button(shlAddsepchar, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				e.doit = false;
				shlAddsepchar.setVisible(false);
			}
		});
		btnCancel.setBounds(194, 325, 80, 27);
		btnCancel.setText("Cancel");

	}
}
