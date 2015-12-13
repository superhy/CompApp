package org.superhy.junit;

import org.junit.Test;
import org.superhy.dao.SqliteDao;

public class SqliteDaoTest {

	@Test
	public void DataBaseInitTest() throws Exception {
		
		SqliteDao testObj = new SqliteDao();
		testObj.DataBaseInit();
	}
}
