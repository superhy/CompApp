package org.superhy.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class SqliteDao {

	private Connection conn = null;
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	// �޲εĹ��캯��
	public SqliteDao() {
	}

	public Connection ConnectSqlite() throws Exception {
		// ��������
		Class.forName("org.sqlite.JDBC");
		conn = DriverManager.getConnection("jdbc:sqlite:./db/Comp_DB.db");

		System.out.println("�ɹ��������ݿ����ӣ�" + conn);

		return conn;
	}

	public void DataBaseInit() throws Exception {
		conn = ConnectSqlite();
		// �����Զ��ύΪfalse
		conn.setAutoCommit(false);

		stmt = conn.createStatement();
		stmt.executeUpdate("create table if not exists keywords (id integer primary key autoincrement not null, name varchar(50) not null)");
		stmt.executeUpdate("create table if not exists sepchars (id integer primary key autoincrement not null, name varchar(10) not null, type varchar(20) not null)");
		stmt.executeUpdate("create table if not exists exegeses (id integer primary key autoincrement not null, pre varchar(10) not null, last varchar(10) null)");
		stmt.executeUpdate("create table if not exists jobs (id integer primary key autoincrement not null, file_name varchar(50) not null, res_name varchar(50) not null, cre_time date not null)");

		stmt.executeUpdate("create table if not exists setting (id integer primary key not null, location_ans varchar(100) null)");
		conn.commit();
	}

	/**
	 * ִ�����ݿ��ѯ�����ز�ѯ���
	 * 
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public ResultSet query(String sql) throws Exception {
		DataBaseInit();
		conn = ConnectSqlite();
		stmt = conn.createStatement();
		rs = stmt.executeQuery(sql);

		return rs;
	}

	/**
	 * ִ�����ݿ����
	 * 
	 * @param sql
	 * @throws Exception
	 */
	public int update(String sql) throws Exception {
		DataBaseInit();
		conn = ConnectSqlite();
		stmt = conn.createStatement();
		int r = stmt.executeUpdate(sql);

		close();
		return r;
	}

	/**
	 * ִ�в������Ľű����
	 * 
	 * @param sql
	 * @param args
	 * @throws Exception
	 */
	public int update(String sql, String[] args) throws Exception {
		DataBaseInit();
		conn = ConnectSqlite();
		pstmt = conn.prepareStatement(sql);
		for (int i = 0; i < args.length; i++) {
			pstmt.setString(i + 1, args[i]);
		}
		int r = pstmt.executeUpdate();

		close();
		return r;
	}

	public void close() throws Exception {
		if (rs != null) {
			rs.close();
		}
		if (stmt != null) {
			stmt.close();
		}
		if (pstmt != null) {
			pstmt.close();
		}
		if (conn != null) {
			conn.close();
		}

		System.out.println("���ݿ��ѳɹ��ر�");
	}
}
