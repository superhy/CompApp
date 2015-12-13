package org.superhy.action;

public class OpenAnsFileAction {
	
	public void execute(String filePath) throws Exception {
		Runtime.getRuntime().exec("rundll32 url.dll FileProtocolHandler file://" + filePath);
	}
}
