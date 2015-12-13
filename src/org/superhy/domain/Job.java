package org.superhy.domain;

public class Job {

	private int id;
	private String file_name;
	private String res_name;
	private String cre_time;

	// 无参构造函数
	public Job() {
	}

	// 缺少id的构造函数，添加时用
	public Job(String file_name, String res_name, String cre_time) {
		super();
		this.file_name = file_name;
		this.res_name = res_name;
		this.cre_time = cre_time;
	}

	public Job(int id, String file_name, String res_name, String cre_time) {
		super();
		this.id = id;
		this.file_name = file_name;
		this.res_name = res_name;
		this.cre_time = cre_time;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public String getRes_name() {
		return res_name;
	}

	public void setRes_name(String res_name) {
		this.res_name = res_name;
	}

	public String getCre_time() {
		return cre_time;
	}

	public void setCre_time(String cre_time) {
		this.cre_time = cre_time;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((cre_time == null) ? 0 : cre_time.hashCode());
		result = prime * result
				+ ((file_name == null) ? 0 : file_name.hashCode());
		result = prime * result + id;
		result = prime * result
				+ ((res_name == null) ? 0 : res_name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Job other = (Job) obj;
		if (cre_time == null) {
			if (other.cre_time != null)
				return false;
		} else if (!cre_time.equals(other.cre_time))
			return false;
		if (file_name == null) {
			if (other.file_name != null)
				return false;
		} else if (!file_name.equals(other.file_name))
			return false;
		if (id != other.id)
			return false;
		if (res_name == null) {
			if (other.res_name != null)
				return false;
		} else if (!res_name.equals(other.res_name))
			return false;
		return true;
	}

}
