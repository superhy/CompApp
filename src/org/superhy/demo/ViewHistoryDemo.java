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
import org.superhy.action.JobsProAction;
import org.superhy.domain.Job;

public class ViewHistoryDemo {

	protected Shell shlViewhistory;
	private Table tableViewhistory;
	private List<Job> jobs;
	static JobsProAction jobsProActionObj = new JobsProAction();

	public List<Job> getJobs() {
		return jobs;
	}

	public void setJobs(List<Job> jobs) {
		this.jobs = jobs;
	}

	/**
	 * Open the window.
	 * 
	 * @wbp.parser.entryPoint
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlViewhistory.open();
		shlViewhistory.layout();
		while (!shlViewhistory.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlViewhistory = new Shell();

		// 覆写窗体关闭事件，使窗体可以重复打开
		shlViewhistory.addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				e.doit = false;
				shlViewhistory.setVisible(false);
			}
		});
		shlViewhistory.setSize(600, 300);
		shlViewhistory.setText("ViewHistory");

		tableViewhistory = new Table(shlViewhistory, SWT.SINGLE | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER | SWT.FULL_SELECTION);
		tableViewhistory.setBounds(10, 10, 564, 175);
		tableViewhistory.setHeaderVisible(true);
		tableViewhistory.setLinesVisible(true);

		TableColumn tblclmnId = new TableColumn(tableViewhistory, SWT.NONE);
		tblclmnId.setWidth(80);
		tblclmnId.setText("id");

		TableColumn tblclmnFilename = new TableColumn(tableViewhistory,
				SWT.NONE);
		tblclmnFilename.setWidth(160);
		tblclmnFilename.setText("file_name");

		TableColumn tblclmnResname = new TableColumn(tableViewhistory, SWT.NONE);
		tblclmnResname.setWidth(160);
		tblclmnResname.setText("res_name");

		TableColumn tblclmnCreatetime = new TableColumn(tableViewhistory,
				SWT.NONE);
		tblclmnCreatetime.setWidth(160);
		tblclmnCreatetime.setText("create_time");

		RefreshData();

		Button btnRefresh = new Button(shlViewhistory, SWT.NONE);
		btnRefresh.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				RefreshData();
			}
		});
		btnRefresh.setBounds(369, 225, 80, 27);
		btnRefresh.setText("Refresh");

		Button btnDelete = new Button(shlViewhistory, SWT.NONE);
		btnDelete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MessageBox box = new MessageBox(shlViewhistory, SWT.YES
						| SWT.NO);
				box.setMessage("are you sure to delete this item ?");

				// 如果选择取消，不执行删除操作
				if (box.open() == 128) {
					return;
				}

				try {
					MessageBox box2 = new MessageBox(shlViewhistory, SWT.OK);
					TableItem[] selectItems = tableViewhistory.getSelection();
					if (jobsProActionObj.deleteJob(selectItems[0].getText(0)) > 0) {
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
		btnDelete.setBounds(468, 225, 80, 27);
		btnDelete.setText("Delete");

	}

	public void RefreshData() {
		tableViewhistory.removeAll();

		try {
			setJobs(jobsProActionObj.getAllJobs());
			List<Job> tableList = getJobs();
			for (int i = 0; i < tableList.size(); i++) {
				final TableItem item[] = new TableItem[tableList.size()];
				item[i] = new TableItem(tableViewhistory, SWT.NONE);
				item[i].setText(new String[] {
						Integer.toString(tableList.get(i).getId()),
						tableList.get(i).getFile_name(),
						tableList.get(i).getRes_name(),
						tableList.get(i).getCre_time() });
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
