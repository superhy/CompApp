package org.superhy.service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.superhy.dao.SqliteDao;
import org.superhy.domain.Job;

public class JobService {

	// ģ�����ݿ�
	static List<Job> jobsDb;
	static SqliteDao sqliteObj = new SqliteDao();

	// ˢ�����ݣ�����jobsDb����
	public void refreshData() throws Exception {
		jobsDb = new ArrayList<Job>();

		String sqlQuery = "select * from jobs";
		ResultSet rs = sqliteObj.query(sqlQuery);

		while (rs.next()) {
			jobsDb.add(new Job(rs.getInt(1), rs.getString(2), rs.getString(3),
					rs.getString(4)));
		}
		sqliteObj.close();
	}

	// ��ȡ��������
	public String getLastJob() throws Exception {
		String sqlLastQuery = "select * from jobs where id in (select max(id) from jobs)";
		ResultSet rs = sqliteObj.query(sqlLastQuery);
		
		String lastJobPath = "...";
		while (rs.next()) {
			lastJobPath = rs.getString(2);
		}
		sqliteObj.close();
		
		return lastJobPath;
	}

	// ��ϵͳ�����������
	public void addJob(Job job) throws Exception {
		String sqlInsert = "insert into jobs(file_name, res_name, cre_time) values('"
				+ job.getFile_name()
				+ "', '"
				+ job.getRes_name()
				+ "', '"
				+ job.getCre_time() + "')";
		System.out.println(sqlInsert);
		sqliteObj.update(sqlInsert);
	}

	// ɾ����ѡ������
	public int deleteJob(String id) throws Exception {
		String sqlUpdate = "delete from jobs where id = " + id + "";
		return sqliteObj.update(sqlUpdate);
	}

	// ����ϵͳ�����е����е�����ʵ��
	public List<Job> getAllJobs() throws Exception {
		refreshData();

		return jobsDb;
	}
}
