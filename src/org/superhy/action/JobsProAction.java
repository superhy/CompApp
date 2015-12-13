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

	// ��ȡϵͳ��ǰʱ��
	public String getNowDate() {
		String temp_str = "";
		Date dt = new Date();
		// ����aa��ʾ�����硱�����硱 HH��ʾ24Сʱ�� �������hh��ʾ12Сʱ��
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
