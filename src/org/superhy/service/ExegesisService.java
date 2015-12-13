package org.superhy.service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.superhy.dao.SqliteDao;
import org.superhy.domain.Exegesis;

public class ExegesisService {

	// 模拟数据库
	static List<Exegesis> exegesesDb;
	static SqliteDao sqliteObj = new SqliteDao();

	// 刷新数据，存入exegesesDb容器
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

	// 添加字段
	public int addExegesis(Exegesis exegesis) throws Exception {
		String sqlInsert;
		// 判断是否只添加前一个字段
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

	// 删除选中的字段
	public int deleteExegesis(String id) throws Exception {
		String sqlUpdate = "delete from exegeses where id = " + id + "";
		return sqliteObj.update(sqlUpdate);
	}

	// 返回系统中所有的注释实例
	public List<Exegesis> getAllExegeses() throws Exception {
		refreshData();

		return exegesesDb;
	}
}
