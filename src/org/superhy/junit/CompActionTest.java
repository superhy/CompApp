package org.superhy.junit;

import java.util.Scanner;

import org.junit.Test;
import org.superhy.action.CompAction;

public class CompActionTest {

	@Test
	public void executeTest() throws Exception {
		
		Scanner cin = new Scanner(System.in);
		String fileResource = cin.next();
		String fileAns = cin.next();
		CompAction testObj = new CompAction();
		testObj.execute(fileResource, fileAns);
	}
}
