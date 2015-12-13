package org.superhy.action;

import java.util.List;

import org.superhy.domain.Exegesis;
import org.superhy.service.ExegesisService;

public class ExegesesProAction {
	private Exegesis exegesis;
	static ExegesisService exegesisServiceObj = new ExegesisService();

	public Exegesis getExegesis() {
		return exegesis;
	}

	public void setExegesis(Exegesis exegesis) {
		this.exegesis = exegesis;
	}

	public int addExegesis(String pre) throws Exception {
		setExegesis(new Exegesis(pre));
		return exegesisServiceObj.addExegesis(getExegesis());
	}

	public int addExegesis(String pre, String last) throws Exception {
		setExegesis(new Exegesis(pre, last));
		return exegesisServiceObj.addExegesis(getExegesis());
	}

	public int deleteExegesis(String id) throws Exception {
		return exegesisServiceObj.deleteExegesis(id);
	}

	public List<Exegesis> getAllExegeses() throws Exception {
		return exegesisServiceObj.getAllExegeses();
	}
}
