package org.superhy.action;

import java.io.FileReader;

public class ReadResourceTextAction {
	
	public String execute(String filePath) throws Exception {
		String resStr = "";

		// 获取要读入的源代码文件URL
		FileReader fr = new FileReader(filePath);
		int r;
		while ((r = fr.read()) != -1) {
			// 必须逐个字符读入，为了吸收换行符和空格
			resStr += ((char) r);
		}
		
		return resStr;
	}
}
