package org.superhy.service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.superhy.dao.SqliteDao;
import org.superhy.domain.Keyword;

public class KeywordService {

	// ģ�����ݿ�
	static List<Keyword> keywordsDb;
	static SqliteDao sqliteObj = new SqliteDao();

	// ˢ�����ݣ�����keywordsDb����
	public void refreshData() throws Exception {
		keywordsDb = new ArrayList<Keyword>();

		String sqlQuery = "select * from keywords";
		ResultSet rs = sqliteObj.query(sqlQuery);

		while (rs.next()) {
			keywordsDb.add(new Keyword(rs.getInt(1), rs.getString(2)));
		}
		sqliteObj.close();
	}

	// ����ֶ�
	public int addKeyword(Keyword keyword) throws Exception {
		String sqlInsert = "insert into keywords(name) values('"
				+ keyword.getName() + "')";
		System.out.println(sqlInsert);
		return sqliteObj.update(sqlInsert);
	}

	// ɾ��ѡ�е��ֶ�
	public int deleteKeyword(String id) throws Exception {
		String sqlUpdate = "delete from keywords where id = " + id + "";
		return sqliteObj.update(sqlUpdate);
	}

	// ����ϵͳ�����еĹؼ���ʵ��
	public List<Keyword> getAllKeywords() throws Exception {
		refreshData();

		return keywordsDb;
	}
}
