package org.superhy.action;

import java.util.List;

import org.superhy.domain.Keyword;
import org.superhy.service.KeywordService;

public class KeywordsProAction {
	private Keyword keyword;
	static KeywordService keywordServiceObj = new KeywordService();

	public Keyword getKeyword() {
		return keyword;
	}

	public void setKeyword(Keyword keyword) {
		this.keyword = keyword;
	}
	
	public int addKeyword(String name) throws Exception {
		setKeyword(new Keyword(name));
		return keywordServiceObj.addKeyword(getKeyword());
	}
	
	public int deleteKeyword(String id) throws Exception {
		return keywordServiceObj.deleteKeyword(id);
	}
	
	public List<Keyword> getAllKeywords() throws Exception {
		return keywordServiceObj.getAllKeywords();
	}
}
