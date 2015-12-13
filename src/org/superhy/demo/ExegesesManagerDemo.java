package org.superhy.demo;

import java.util.List;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.superhy.action.ExegesesProAction;
import org.superhy.domain.Exegesis;

public class ExegesesManagerDemo {

	protected Shell shlExegesesmanager;
	private Table tableExegesesManager;
	private List<Exegesis> exegeses;
	static ExegesesProAction exegesesProActionObj = new ExegesesProAction();

	public List<Exegesis> getExegeses() {
		return exegeses;
	}

	public void setExegeses(List<Exegesis> exegeses) {
		this.exegeses = exegeses;
	}

	/**
	 * Open the window.
	 * 
	 * @wbp.parser.entryPoint
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlExegesesmanager.open();
		shlExegesesmanager.layout();
		while (!shlExegesesmanager.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlExegesesmanager = new Shell();

		// 覆写窗体关闭事件，使窗体可以重复打开
		shlExegesesmanager.addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				e.doit = false;
				shlExegesesmanager.setVisible(false);
			}
		});
		shlExegesesmanager.setSize(500, 300);
		shlExegesesmanager.setText("ExegesesManager");

		tableExegesesManager = new Table(shlExegesesmanager, SWT.SINGLE
				| SWT.H_SCROLL | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER
				| SWT.FULL_SELECTION);
		tableExegesesManager.setBounds(10, 10, 464, 175);
		tableExegesesManager.setHeaderVisible(true);
		tableExegesesManager.setLinesVisible(true);

		TableColumn tblclmnId = new TableColumn(tableExegesesManager, SWT.NONE);
		tblclmnId.setWidth(110);
		tblclmnId.setText("id");

		TableColumn tblclmnPrestring = new TableColumn(tableExegesesManager,
				SWT.NONE);
		tblclmnPrestring.setWidth(170);
		tblclmnPrestring.setText("pre_string");

		TableColumn tblclmnLaststring = new TableColumn(tableExegesesManager,
				SWT.NONE);
		tblclmnLaststring.setWidth(170);
		tblclmnLaststring.setText("last_string");

		RefreshData();

		Button btnAdd = new Button(shlExegesesmanager, SWT.NONE);
		btnAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new AddExegesisDemo().open();
			}
		});
		btnAdd.setBounds(222, 225, 80, 27);
		btnAdd.setText("Add");

		Button btnRefresh = new Button(shlExegesesmanager, SWT.NONE);
		btnRefresh.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				RefreshData();
			}
		});
		btnRefresh.setBounds(308, 225, 80, 27);
		btnRefresh.setText("Refresh");

		Button btnDelete = new Button(shlExegesesmanager, SWT.NONE);
		btnDelete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MessageBox box = new MessageBox(shlExegesesmanager, SWT.YES
						| SWT.NO);
				box.setMessage("are you sure to delete this item ?");

				// 如果选择取消，不执行删除操作
				if (box.open() == 128) {
					return;
				}

				try {
					MessageBox box2 = new MessageBox(shlExegesesmanager, SWT.OK);
					TableItem[] selectItems = tableExegesesManager
							.getSelection();
					if (exegesesProActionObj.deleteExegesis(selectItems[0]
							.getText(0)) > 0) {
						box2.setMessage("deleted successful !");
						box2.open();
						RefreshData();
					} else {
						box2.setMessage("deleted failed !");
						box2.open();
					}
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}
		});
		btnDelete.setBounds(394, 225, 80, 27);
		btnDelete.setText("Delete");

	}

	public void RefreshData() {
		tableExegesesManager.removeAll();

		try {
			setExegeses(exegesesProActionObj.getAllExegeses());
			List<Exegesis> tableList = getExegeses();
			for (int i = 0; i < tableList.size(); i++) {
				final TableItem item[] = new TableItem[tableList.size()];
				item[i] = new TableItem(tableExegesesManager, SWT.NONE);
				item[i].setText(new String[] {
						Integer.toString(tableList.get(i).getId()),
						tableList.get(i).getPre(), tableList.get(i).getLast() });
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
