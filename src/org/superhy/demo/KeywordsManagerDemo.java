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
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.superhy.action.KeywordsProAction;
import org.superhy.domain.Keyword;

public class KeywordsManagerDemo {

	protected Shell shlKeywordsmanager;
	private Table tableKeywordsmanager;
	private List<Keyword> keywords;
	static KeywordsProAction keywordsProActionObj = new KeywordsProAction();

	public List<Keyword> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<Keyword> keywords) {
		this.keywords = keywords;
	}

	/**
	 * Open the window.
	 * 
	 * @wbp.parser.entryPoint
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlKeywordsmanager.open();
		shlKeywordsmanager.layout();
		while (!shlKeywordsmanager.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlKeywordsmanager = new Shell();

		// 覆写窗体关闭事件，使窗体可以重复打开
		shlKeywordsmanager.addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				e.doit = false;
				shlKeywordsmanager.setVisible(false);
			}
		});
		shlKeywordsmanager.setSize(500, 300);
		shlKeywordsmanager.setText("KeywordsManager");

		tableKeywordsmanager = new Table(shlKeywordsmanager, SWT.SINGLE
				| SWT.H_SCROLL | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER
				| SWT.FULL_SELECTION);
		tableKeywordsmanager.setBounds(10, 10, 464, 175);
		tableKeywordsmanager.setHeaderVisible(true);
		tableKeywordsmanager.setLinesVisible(true);

		TableColumn tblclmnId = new TableColumn(tableKeywordsmanager, SWT.NONE);
		tblclmnId.setWidth(150);
		tblclmnId.setText("id");

		TableColumn tblclmnKeywordname = new TableColumn(tableKeywordsmanager,
				SWT.NONE);
		tblclmnKeywordname.setWidth(300);
		tblclmnKeywordname.setText("Keyword_name");

		RefreshData();

		Button btnAdd = new Button(shlKeywordsmanager, SWT.NONE);
		btnAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new AddKeywordDemo().open();
			}
		});
		btnAdd.setBounds(222, 225, 80, 27);
		btnAdd.setText("Add");

		Button btnRefresh = new Button(shlKeywordsmanager, SWT.NONE);
		btnRefresh.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				RefreshData();
			}
		});
		btnRefresh.setBounds(308, 225, 80, 27);
		btnRefresh.setText("Refresh");

		Button btnDelete = new Button(shlKeywordsmanager, SWT.NONE);
		btnDelete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MessageBox box = new MessageBox(shlKeywordsmanager, SWT.YES
						| SWT.NO);
				box.setMessage("are you sure to delete this item ?");

				// 如果选择取消，不执行删除操作
				if (box.open() == 128) {
					return;
				}

				try {
					MessageBox box2 = new MessageBox(shlKeywordsmanager, SWT.OK);
					TableItem[] selectItems = tableKeywordsmanager
							.getSelection();
					if (keywordsProActionObj.deleteKeyword(selectItems[0]
							.getText(0)) > 0) {
						box2.setMessage("deleted successful !");
						box2.open();
						RefreshData();
					} else {
						box2.setMessage("deleted failed !");
						box2.open();
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		btnDelete.setBounds(394, 225, 80, 27);
		btnDelete.setText("Delete");

	}

	public void RefreshData() {
		tableKeywordsmanager.removeAll();

		try {
			setKeywords(keywordsProActionObj.getAllKeywords());
			List<Keyword> tableList = getKeywords();
			for (int i = 0; i < tableList.size(); i++) {
				final TableItem item[] = new TableItem[tableList.size()];
				item[i] = new TableItem(tableKeywordsmanager, SWT.NONE);
				item[i].setText(new String[] {
						Integer.toString(tableList.get(i).getId()),
						tableList.get(i).getName() });
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
