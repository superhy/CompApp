package org.superhy.junit;

import java.util.Scanner;

import org.junit.Test;
import org.superhy.action.OpenAnsDirectoryAction;

public class OpenAnsDirectoryActionTest {
	
	@Test
	public void executeTest() throws Exception {
		
		Scanner cin = new Scanner(System.in);
		String filePath = cin.next();
		OpenAnsDirectoryAction testObj = new OpenAnsDirectoryAction();
		testObj.execute(filePath);
	}
}
