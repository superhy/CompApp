package org.superhy.service;

import java.sql.ResultSet;

import org.superhy.dao.SqliteDao;
import org.superhy.domain.Setting;

public class SettingService {

	// ģ�����ݿ�
	static Setting settingDb;
	static SqliteDao sqliteObj = new SqliteDao();

	// ˢ�����ݣ���ȡ��ǰ�����ò���
	public void refreshData() throws Exception {
		String sqlQuery = "select * from setting where id = 1";
		System.out.println(sqlQuery);
		ResultSet rs = sqliteObj.query(sqlQuery);

		if (rs.next()) {
			settingDb = new Setting(rs.getInt(1), rs.getString(2));
		}
		sqliteObj.close();
	}

	// ������������
	public void updateSetting(String field, String data) throws Exception {
		String sqlUpdate = "update setting set " + field + " = '" + data
				+ "' where id = 1";
		sqliteObj.update(sqlUpdate);
	}

	// ��ȡ�������ò���
	public Setting getNewSetting() throws Exception {
		refreshData();

		return settingDb;
	}
}
