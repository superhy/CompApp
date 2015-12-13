package org.superhy.domain;

public class Exegesis {

	private int id;
	private String pre;
	private String last;

	// 无参构造函数
	public Exegesis() {
	}

	// 缺少id和last的构造函数
	public Exegesis(String pre) {
		super();
		this.pre = pre;
	}

	// 缺少id的构造函数，添加时用
	public Exegesis(String pre, String last) {
		super();
		this.pre = pre;
		this.last = last;
	}

	public Exegesis(int id, String pre, String last) {
		super();
		this.id = id;
		this.pre = pre;
		this.last = last;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPre() {
		return pre;
	}

	public void setPre(String pre) {
		this.pre = pre;
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((last == null) ? 0 : last.hashCode());
		result = prime * result + ((pre == null) ? 0 : pre.hashCode());
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
		Exegesis other = (Exegesis) obj;
		if (id != other.id)
			return false;
		if (last == null) {
			if (other.last != null)
				return false;
		} else if (!last.equals(other.last))
			return false;
		if (pre == null) {
			if (other.pre != null)
				return false;
		} else if (!pre.equals(other.pre))
			return false;
		return true;
	}

}
