package org.superhy.demo;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Label;
import org.superhy.action.CompAction;
import org.superhy.action.JobsProAction;
import org.superhy.action.OpenAnsDirectoryAction;
import org.superhy.action.OpenAnsFileAction;
import org.superhy.action.ReadResourceTextAction;
import org.superhy.service.SettingService;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;

public class MainDemo {

	protected Shell shlMain;
	private Text textFileResource;
	private final FormToolkit formToolkit = new FormToolkit(
			Display.getDefault());
	private Text textFileAns;
	static JobsProAction jobsProActionObj = new JobsProAction();

	private String fileResourcePath;
	private String fileAnsPath;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainDemo window = new MainDemo();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 * @throws Exception 
	 */
	public void open() throws Exception {
		Display display = Display.getDefault();
		createContents();
		shlMain.open();
		shlMain.layout();
		while (!shlMain.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 * @throws Exception 
	 */
	protected void createContents() throws Exception {
		// 覆写关闭窗口事件，确保关闭所有进程
		shlMain = new Shell();
		shlMain.setImage(SWTResourceManager.getImage("./image/about.gif"));
		shlMain.addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				System.exit(1);
			}
		});
		shlMain.setSize(600, 500);
		shlMain.setText("KMUST AiLab");

		Menu menu = new Menu(shlMain, SWT.BAR);
		shlMain.setMenuBar(menu);

		MenuItem mntmFile = new MenuItem(menu, SWT.CASCADE);
		mntmFile.setText("File");

		Menu menu_1 = new Menu(mntmFile);
		mntmFile.setMenu(menu_1);

		MenuItem mntmNew = new MenuItem(menu_1, SWT.CASCADE);
		mntmNew.setText("New");

		Menu menu_5 = new Menu(mntmNew);
		mntmNew.setMenu(menu_5);

		MenuItem mntmResourceFile = new MenuItem(menu_5, SWT.NONE);
		mntmResourceFile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// 打开选择文件对话框
				FileDialog fileResourceDialog = new FileDialog(shlMain,
						SWT.OPEN);
				fileResourceDialog.setFilterPath("c:/windows");
				if ((fileResourcePath = fileResourceDialog.open()) != null) {
					try {
						textFileResource.setText(new ReadResourceTextAction()
								.execute(fileResourcePath));
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		mntmResourceFile.setText("Resource File");

		MenuItem mntmBrowse = new MenuItem(menu_1, SWT.NONE);
		mntmBrowse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if ("".equals(fileAnsPath) || fileAnsPath == null) {
					MessageBox box = new MessageBox(shlMain, SWT.OK);
					box.setMessage("please execute a job first !");
					box.open();
				} else {
					try {
						OpenAnsDirectoryAction openAnsDirectoryActionObj = new OpenAnsDirectoryAction();
						openAnsDirectoryActionObj.execute(fileAnsPath);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		mntmBrowse.setText("Browse...");

		MenuItem mntmHistory = new MenuItem(menu_1, SWT.NONE);
		mntmHistory.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new ViewHistoryDemo().open();
			}
		});
		mntmHistory.setText("History");

		MenuItem mntmOpenRecent = new MenuItem(menu_1, SWT.CASCADE);
		mntmOpenRecent.setText("Open Recent");

		Menu menu_6 = new Menu(mntmOpenRecent);
		mntmOpenRecent.setMenu(menu_6);

		MenuItem mntmLastJob = new MenuItem(menu_6, SWT.NONE);
		mntmLastJob.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					if (jobsProActionObj.getAllJobs().equals("...")) {
						MessageBox box = new MessageBox(shlMain, SWT.OK);
						box.setMessage("have no jobs now !");
						box.open();
						return ;
					} else {
						textFileResource.setText(new ReadResourceTextAction().execute(jobsProActionObj.getLastJob()));
						fileResourcePath = jobsProActionObj.getLastJob();
					}
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		});
		mntmLastJob.setText(jobsProActionObj.getLastJob());

		new MenuItem(menu_1, SWT.SEPARATOR);

		MenuItem mntmClearTab = new MenuItem(menu_1, SWT.NONE);
		mntmClearTab.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				textFileResource.setText("");
				textFileAns.setText("");
				fileResourcePath = "";
				fileAnsPath = "";
			}
		});
		mntmClearTab.setText("Clear Tab");

		new MenuItem(menu_1, SWT.SEPARATOR);

		MenuItem mntmExit = new MenuItem(menu_1, SWT.NONE);
		mntmExit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.exit(1);
			}
		});
		mntmExit.setText("Exit");

		MenuItem mntmEdit = new MenuItem(menu, SWT.CASCADE);
		mntmEdit.setText("Edit");

		Menu menu_2 = new Menu(mntmEdit);
		mntmEdit.setMenu(menu_2);

		MenuItem mntmKeywords = new MenuItem(menu_2, SWT.NONE);
		mntmKeywords.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new KeywordsManagerDemo().open();
			}
		});
		mntmKeywords.setText("Keywords");

		MenuItem mntmExegeses = new MenuItem(menu_2, SWT.NONE);
		mntmExegeses.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new ExegesesManagerDemo().open();
			}
		});
		mntmExegeses.setText("Exegeses");

		MenuItem mntmSepchars = new MenuItem(menu_2, SWT.NONE);
		mntmSepchars.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new SepcharsManagerDemo().open();
			}
		});
		mntmSepchars.setText("Sepchars");

		new MenuItem(menu_2, SWT.SEPARATOR);

		MenuItem mntmPreferences = new MenuItem(menu_2, SWT.NONE);
		mntmPreferences.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					new SettingDemo().settingDemo.open();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mntmPreferences.setText("Preferences...");

		MenuItem mntmWindow = new MenuItem(menu, SWT.CASCADE);
		mntmWindow.setText("Window");

		Menu menu_3 = new Menu(mntmWindow);
		mntmWindow.setMenu(menu_3);

		MenuItem mntmOpenans = new MenuItem(menu_3, SWT.NONE);
		mntmOpenans.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if ("".equals(fileAnsPath) || fileAnsPath == null) {
					MessageBox box = new MessageBox(shlMain, SWT.OK);
					box.setMessage("please execute a job first !");
					box.open();
				} else {
					try {
						OpenAnsFileAction openAnsFileActionObj = new OpenAnsFileAction();
						openAnsFileActionObj.execute(fileAnsPath);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		mntmOpenans.setText("Open Ans");

		MenuItem mntmHelp = new MenuItem(menu, SWT.CASCADE);
		mntmHelp.setText("Help");

		Menu menu_4 = new Menu(mntmHelp);
		mntmHelp.setMenu(menu_4);

		MenuItem mntmAbout = new MenuItem(menu_4, SWT.NONE);
		mntmAbout.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new AboutDemo().open();
			}
		});
		mntmAbout.setText("About");

		textFileResource = new Text(shlMain, SWT.BORDER | SWT.READ_ONLY
				| SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		textFileResource.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WHITE));
		textFileResource.setBounds(10, 10, 564, 178);

		Button btnAnalyse = new Button(shlMain, SWT.NONE);
		btnAnalyse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				CompAction compObj = new CompAction();
				SettingService settingServiceObj = new SettingService();
				MessageBox box = new MessageBox(shlMain, SWT.OK);

				if ("".equals(fileResourcePath)) {
					box.setMessage("have not found job !");
					box.open();
					return;
				}

				try {
					fileAnsPath = settingServiceObj.getNewSetting()
							.getLocation_ans();
					System.out.println(fileAnsPath);
					if (fileAnsPath != null) {
						textFileAns.setText(compObj.execute(fileResourcePath,
								fileAnsPath));
						new JobsProAction().addJob(fileResourcePath,
								fileAnsPath);
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnAnalyse.setBounds(494, 194, 80, 27);
		btnAnalyse.setText("Analyse");

		textFileAns = new Text(shlMain, SWT.BORDER | SWT.READ_ONLY
				| SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		textFileAns.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		textFileAns.setBounds(10, 227, 564, 205);
		formToolkit.adapt(textFileAns, true, true);

		Label label = formToolkit.createSeparator(shlMain, SWT.HORIZONTAL);
		label.setEnabled(false);
		label.setBounds(10, 211, 478, 2);
	}
}
