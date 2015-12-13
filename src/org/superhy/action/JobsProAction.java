package org.superhy.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.superhy.domain.Job;
import org.superhy.service.JobService;

public class JobsProAction {

	private Job job;
	static JobService jobServiceObj = new JobService();

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	// 获取系统当前时间
	public String getNowDate() {
		String temp_str = "";
		Date dt = new Date();
		// 最后的aa表示“上午”或“下午” HH表示24小时制 如果换成hh表示12小时制
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		temp_str = sdf.format(dt);
		return temp_str;
	}

	public void addJob(String fileResource, String fileAns) throws Exception {
		String dateTime = getNowDate();

		setJob(new Job(fileResource, fileAns, dateTime));
		jobServiceObj.addJob(getJob());
	}
	
	public int deleteJob(String id) throws Exception {
		return jobServiceObj.deleteJob(id);
	}
	
	public String getLastJob() throws Exception {
		return jobServiceObj.getLastJob();
	}
	
	public List<Job> getAllJobs() throws Exception {
		return jobServiceObj.getAllJobs();
	}
}
