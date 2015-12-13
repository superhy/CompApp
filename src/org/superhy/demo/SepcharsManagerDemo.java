package org.superhy.demo;

import java.util.List;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.superhy.action.SepcharsProAction;
import org.superhy.domain.Sepchar;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class SepcharsManagerDemo {

	protected Shell shlSepcharsmanager;
	private Table tableSepcharsManager;
	private List<Sepchar> sepchars;
	static SepcharsProAction sepcharsProActionObj = new SepcharsProAction();

	public List<Sepchar> getSepchars() {
		return sepchars;
	}

	public void setSepchars(List<Sepchar> sepchars) {
		this.sepchars = sepchars;
	}

	/**
	 * Open the window.
	 * 
	 * @wbp.parser.entryPoint
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlSepcharsmanager.open();
		shlSepcharsmanager.layout();
		while (!shlSepcharsmanager.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlSepcharsmanager = new Shell();

		// 覆写窗体关闭事件，使窗体可以重复打开
		shlSepcharsmanager.addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				e.doit = false;
				shlSepcharsmanager.setVisible(false);
			}
		});
		shlSepcharsmanager.setSize(500, 300);
		shlSepcharsmanager.setText("SepcharsManager");

		tableSepcharsManager = new Table(shlSepcharsmanager, SWT.SINGLE
				| SWT.H_SCROLL | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER
				| SWT.FULL_SELECTION);
		tableSepcharsManager.setBounds(10, 10, 464, 175);
		tableSepcharsManager.setHeaderVisible(true);
		tableSepcharsManager.setLinesVisible(true);

		TableColumn tblclmnId = new TableColumn(tableSepcharsManager, SWT.NONE);
		tblclmnId.setWidth(110);
		tblclmnId.setText("id");

		TableColumn tblclmnSepcharname = new TableColumn(tableSepcharsManager,
				SWT.NONE);
		tblclmnSepcharname.setWidth(170);
		tblclmnSepcharname.setText("Sepchar_name");

		TableColumn tblclmnSepchartype = new TableColumn(tableSepcharsManager,
				SWT.NONE);
		tblclmnSepchartype.setWidth(170);
		tblclmnSepchartype.setText("Sepchar_type");

		RefreshData();

		Button btnAdd = new Button(shlSepcharsmanager, SWT.NONE);
		btnAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new AddSepcharDemo().open();
			}
		});
		btnAdd.setBounds(222, 225, 80, 27);
		btnAdd.setText("Add");

		Button btnRefresh = new Button(shlSepcharsmanager, SWT.NONE);
		btnRefresh.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				RefreshData();
			}
		});
		btnRefresh.setBounds(308, 225, 80, 27);
		btnRefresh.setText("Refresh");

		Button btnDelete = new Button(shlSepcharsmanager, SWT.NONE);
		btnDelete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MessageBox box = new MessageBox(shlSepcharsmanager, SWT.YES
						| SWT.NO);
				box.setMessage("are you sure to delete this item ?");

				// 如果选择取消，不执行删除操作
				if (box.open() == 128) {
					return;
				}

				try {
					MessageBox box2 = new MessageBox(shlSepcharsmanager, SWT.OK);
					TableItem[] selectItems = tableSepcharsManager
							.getSelection();
					if (sepcharsProActionObj.deleteSepchar(selectItems[0]
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
		tableSepcharsManager.removeAll();

		try {
			setSepchars(sepcharsProActionObj.getAllSepchars());
			List<Sepchar> tableList = getSepchars();
			for (int i = 0; i < tableList.size(); i++) {
				final TableItem item[] = new TableItem[tableList.size()];
				item[i] = new TableItem(tableSepcharsManager, SWT.NONE);
				item[i].setText(new String[] {
						Integer.toString(tableList.get(i).getId()),
						tableList.get(i).getName(), tableList.get(i).getType() });
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
