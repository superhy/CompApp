package org.superhy.action;

import java.util.List;

import org.superhy.domain.Sepchar;
import org.superhy.service.SepcharService;

public class SepcharsProAction {
	private Sepchar sepchar;
	static SepcharService sepcharServiceObj = new SepcharService();

	public Sepchar getSepchar() {
		return sepchar;
	}

	public void setSepchar(Sepchar sepchar) {
		this.sepchar = sepchar;
	}

	public int addSepchar(String name, String type) throws Exception {
		setSepchar(new Sepchar(name, type));
		return sepcharServiceObj.addSepchar(getSepchar());
	}
	
	public int deleteSepchar(String id) throws Exception {
		return sepcharServiceObj.deleteSepchar(id);
	}
	
	public List<Sepchar> getAllSepchars() throws Exception {
		return sepcharServiceObj.getAllSepchars();
	}
}
