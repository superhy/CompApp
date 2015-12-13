package org.superhy.domain;

public class Setting {
	private int id;
	private String location_ans;
	
	//无参构造函数
	public Setting() {
	}

	public Setting(int id, String location_ans) {
		super();
		this.id = id;
		this.location_ans = location_ans;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLocation_ans() {
		return location_ans;
	}

	public void setLocation_ans(String location_ans) {
		this.location_ans = location_ans;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result
				+ ((location_ans == null) ? 0 : location_ans.hashCode());
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
		Setting other = (Setting) obj;
		if (id != other.id)
			return false;
		if (location_ans == null) {
			if (other.location_ans != null)
				return false;
		} else if (!location_ans.equals(other.location_ans))
			return false;
		return true;
	}
	
	
}
