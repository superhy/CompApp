package org.superhy.action;

import java.io.FileReader;

public class ReadResourceTextAction {
	
	public String execute(String filePath) throws Exception {
		String resStr = "";

		// ��ȡҪ�����Դ�����ļ�URL
		FileReader fr = new FileReader(filePath);
		int r;
		while ((r = fr.read()) != -1) {
			// ��������ַ����룬Ϊ�����ջ��з��Ϳո�
			resStr += ((char) r);
		}
		
		return resStr;
	}
}
