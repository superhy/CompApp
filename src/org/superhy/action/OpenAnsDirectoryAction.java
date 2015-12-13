package org.superhy.action;

public class OpenAnsDirectoryAction {

	public void execute(String filePath) throws Exception {
		int endIndex = 0;
		for (int i = filePath.length() - 1; i >= 0; i--) {
			if (filePath.charAt(i) == '\\') {
				endIndex = i + 1;
				break;
			}
		}

		String directoryPath = filePath.substring(0, endIndex);
		System.out.println(directoryPath);

		Runtime.getRuntime().exec("cmd /c start " + directoryPath);
	}
}
