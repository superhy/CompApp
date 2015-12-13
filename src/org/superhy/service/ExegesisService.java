package org.superhy.service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.superhy.dao.SqliteDao;
import org.superhy.domain.Exegesis;

public class ExegesisService {

	// ģ�����ݿ�
	static List<Exegesis> exegesesDb;
	static SqliteDao sqliteObj = new SqliteDao();

	// ˢ�����ݣ�����exegesesDb����
	public void refreshData() throws Exception {
		exegesesDb = new ArrayList<Exegesis>();

		String sqlQuery = "select * from exegeses";
		ResultSet rs = sqliteObj.query(sqlQuery);

		while (rs.next()) {
			exegesesDb.add(new Exegesis(rs.getInt(1), rs.getString(2), rs
					.getString(3)));
		}
		sqliteObj.close();
	}

	// ����ֶ�
	public int addExegesis(Exegesis exegesis) throws Exception {
		String sqlInsert;
		// �ж��Ƿ�ֻ���ǰһ���ֶ�
		if (exegesis.getLast() == null) {
			sqlInsert = "insert into exegeses(pre) values('"
					+ exegesis.getPre() + "')";
		} else {
			sqlInsert = "insert into exegeses(pre, last) values('"
					+ exegesis.getPre() + "', '" + exegesis.getLast() + "')";
		}
		System.out.println(sqlInsert);
		return sqliteObj.update(sqlInsert);
	}

	// ɾ��ѡ�е��ֶ�
	public int deleteExegesis(String id) throws Exception {
		String sqlUpdate = "delete from exegeses where id = " + id + "";
		return sqliteObj.update(sqlUpdate);
	}

	// ����ϵͳ�����е�ע��ʵ��
	public List<Exegesis> getAllExegeses() throws Exception {
		refreshData();

		return exegesesDb;
	}
}
