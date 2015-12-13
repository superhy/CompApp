package org.superhy.action;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class WriteAnsFileAction {

	public void execute(String AnsText, String filePath) throws Exception {
		// ��������ı��ĵ����������д��
		File file = new File(filePath);
		if (file.exists()) {
			file.delete();
		}
		file.createNewFile();
		BufferedWriter fo = new BufferedWriter(new FileWriter(file));
		fo.write(AnsText);
		fo.close();
	}
}
