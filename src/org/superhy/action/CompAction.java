package org.superhy.action;

import java.util.List;

import org.superhy.domain.Exegesis;
import org.superhy.domain.Keyword;
import org.superhy.domain.Sepchar;
import org.superhy.service.ExegesisService;
import org.superhy.service.JobService;
import org.superhy.service.KeywordService;
import org.superhy.service.SepcharService;

public class CompAction {

	private List<Keyword> keywords;
	private List<Exegesis> exegeses;
	private List<Sepchar> sepchars;
	private JobService jobSer;

	public List<Keyword> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<Keyword> keywords) {
		this.keywords = keywords;
	}

	public List<Exegesis> getExegeses() {
		return exegeses;
	}

	public void setExegeses(List<Exegesis> exegeses) {
		this.exegeses = exegeses;
	}

	public List<Sepchar> getSepchars() {
		return sepchars;
	}

	public void setSepchars(List<Sepchar> sepchars) {
		this.sepchars = sepchars;
	}

	public JobService getJobSer() {
		return jobSer;
	}

	public void setJobSer(JobService jobSer) {
		this.jobSer = jobSer;
	}

	// 初始化数据方法
	public void initData() throws Exception {
		// 获取当前系统中所有的关键字，注释，算符与界符
		setKeywords(new KeywordService().getAllKeywords());
		setExegeses(new ExegesisService().getAllExegeses());
		setSepchars(new SepcharService().getAllSepchars());

		setJobSer(new JobService());
	}

	// 词法分析器核心方法
	public String execute(String fileResource, String fileAns) throws Exception {
		initData();

		// 用作超前扫描的缓存字符串
		String temp = "";

		// 定义获取的源代码字符串和准备写入文件的结果字符串
		String resStr = "";
		String fileStr = "";
		// 定义扫描的标记点
		int pos;

		// 获取要读入的源代码文件URL
		resStr = new ReadResourceTextAction().execute(fileResource);

		// 做搜索前的初始化工作，标记点归零，获取源代码逐个字符，定义行号。
		pos = 0;
		char[] chs = resStr.toCharArray();
		char str;
		int line = 1; // 每行作为一个部分，从1开始计算
		System.out.println("--------part" + line + ":");
		fileStr = fileStr + "--------part" + line + ":" + "\r\n";
		line++;

		// 开始逐个字符搜索
		while (pos < chs.length) {
			int pos_pre;

			str = chs[pos];
			pos++; // 每次不管判断结果如何，都向前进一位，获取当前需要判断的字符

			// 首先检查是否为注释
			String exegesisLast = null;
			for (int i = 0; i < getExegeses().size(); i++) {
				String exegesisPre = getExegeses().get(i).getPre();

				pos_pre = pos - 1;
				for (int j = 0; j < exegesisPre.length(); j++) {
					if (chs[pos_pre] != exegesisPre.charAt(j)) {
						break;
					}
					pos_pre++;
				}
				if ((pos_pre - pos + 1) == exegesisPre.length()) {
					if (getExegeses().get(i).getLast() != null) {
						exegesisLast = getExegeses().get(i).getLast();
					} else {
						exegesisLast = "enter";
					}

					pos = pos_pre;
				}
			}
			// 如果是注释，解析注释部分
			if (exegesisLast != null) {
				System.out.println("exegesis : ");
				fileStr += "exegesis : " + "\r\n";
				// 检查注释部分结束符
				while (pos < chs.length) {
					str = chs[pos];

					if (str == exegesisLast.charAt(0)
							|| ("enter".equals(exegesisLast) && (str == '\t' || str == '\n'))) {
						if (("enter".equals(exegesisLast) && (str == '\t' || str == '\n'))) {
							System.out.println(temp);
							fileStr = fileStr + temp + "\r\n";
							break;
						} else {
							pos_pre = pos;
							for (int i = 0; i < exegesisLast.length(); i++) {
								if (chs[pos_pre] != exegesisLast.charAt(i)) {
									break;
								}
								pos_pre++;
							}
							if (pos_pre - pos == exegesisLast.length()) {
								pos = pos_pre;
								System.out.println(temp);
								fileStr = fileStr + temp + "\r\n";
								break;
							} else {
								temp += str;
							}
						}

					} else {
						temp += str;
					}

					pos++;
				}

				temp = ""; // 清空扫描缓存
				continue; // 进入此判断，对此类元素分析结束后直接跳出进入下一次循环
			}

			// 判断是否为保留字或者是变量，并加以区分
			if ((str >= 'A' && str <= 'Z') || (str >= 'a' && str <= 'z')
					|| str == '_') {
				temp += str;

				while (pos < chs.length) {
					str = chs[pos];

					if ((str >= '0' && str <= '9')
							|| (str >= 'A' && str <= 'Z')
							|| (str >= 'a' && str <= 'z') || str == '_') {
						temp += str;
					} else {
						int i;
						for (i = 0; i < getKeywords().size(); i++) {
							if (temp.equals(getKeywords().get(i).getName())) {
								System.out.println("keyword --> " + temp);
								fileStr = fileStr + "keyword --> " + temp
										+ "\r\n";
								break;
							}
						}

						if (i == getKeywords().size()) {
							System.out.println("variable --> " + temp);
							fileStr = fileStr + "variable --> " + temp + "\r\n";
						}

						break;
					}

					pos++;
				}

				temp = "";
				continue;
			}

			// 判断是否为数字
			if (str >= '0' && str <= '9') {
				temp += str;

				while (pos < chs.length) {
					str = chs[pos];

					if (str >= '0' && str <= '9') {
						temp += str;
					} else {
						System.out.println("number --> " + temp);
						fileStr = fileStr + "number --> " + temp + "\r\n";
						break;
					}

					pos++;
				}

				temp = "";
				continue;
			}

			// 判断是否为换行符或空格符
			if (str == '\t' || str == '\n' || str == ' ') {
				// 仅对换行符处理，空格符忽略不做处理
				if (str == '\t' || str == '\n') {
					System.out.println("\n--------part" + line + ":");
					fileStr = fileStr + "\r\n" + "--------part" + line + ":"
							+ "\r\n";

					line++;
				}

				continue;
			}

			// 判断是否为算符或界符元素
			int index_sepchar = -1;
			for (int i = 0; i < getSepchars().size(); i++) {
				if (str == getSepchars().get(i).getName().charAt(0)) {
					index_sepchar = i;
					break;
				}
			}
			// 判断为算符或界符元素，分析并加以区分
			if (index_sepchar != -1) {
				temp = getSepchars().get(index_sepchar).getName();

				if (getSepchars().get(index_sepchar).getType().equals("op")) {
					System.out.println("operator --> " + temp);
					fileStr = fileStr + "operator --> " + temp + "\r\n";
				} else if (getSepchars().get(index_sepchar).getType()
						.equals("sep")) {
					System.out.println("separating character --> " + temp);
					fileStr = fileStr + "separating character --> " + temp
							+ "\r\n";
				}

				temp = "";
				continue;
			}
		}

		System.out.println("end of compLagPrograme!");
		fileStr = fileStr + "end of compLagPrograme!" + "\r\n";

		// 创建结果文本文档，并将结果写入
		new WriteAnsFileAction().execute(fileStr, fileAns);

		return fileStr;
	}
}
