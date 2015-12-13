package org.superhy.service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.superhy.dao.SqliteDao;
import org.superhy.domain.Sepchar;

public class SepcharService {

	// ģ�����ݿ�
	static List<Sepchar> sepcharsDb;
	static SqliteDao sqliteObj = new SqliteDao();

	// �������ݣ�����sepcharsDb����
	public void refreshData() throws Exception {
		sepcharsDb = new ArrayList<Sepchar>();

		String sqlQuery = "select * from sepchars";
		ResultSet rs = sqliteObj.query(sqlQuery);

		while (rs.next()) {
			sepcharsDb.add(new Sepchar(rs.getInt(1), rs.getString(2), rs
					.getString(3)));
		}
		sqliteObj.close();
	}

	// ����ֶ�
	public int addSepchar(Sepchar sepchar) throws Exception {
		String sqlInsert = "insert into sepchars(name, type) values('"
				+ sepchar.getName() + "', '" + sepchar.getType() + "')";
		System.out.println(sqlInsert);
		return sqliteObj.update(sqlInsert);
	}

	// ɾ��ѡ�е��ֶ�
	public int deleteSepchar(String id) throws Exception {
		String sqlUpdate = "delete from sepchars where id = " + id + "";
		return sqliteObj.update(sqlUpdate);
	}

	// ����ϵͳ�����е�����ͽ��ʵ��
	public List<Sepchar> getAllSepchars() throws Exception {
		refreshData();

		return sepcharsDb;
	}
}
