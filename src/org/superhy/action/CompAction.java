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

	// ��ʼ�����ݷ���
	public void initData() throws Exception {
		// ��ȡ��ǰϵͳ�����еĹؼ��֣�ע�ͣ��������
		setKeywords(new KeywordService().getAllKeywords());
		setExegeses(new ExegesisService().getAllExegeses());
		setSepchars(new SepcharService().getAllSepchars());

		setJobSer(new JobService());
	}

	// �ʷ����������ķ���
	public String execute(String fileResource, String fileAns) throws Exception {
		initData();

		// ������ǰɨ��Ļ����ַ���
		String temp = "";

		// �����ȡ��Դ�����ַ�����׼��д���ļ��Ľ���ַ���
		String resStr = "";
		String fileStr = "";
		// ����ɨ��ı�ǵ�
		int pos;

		// ��ȡҪ�����Դ�����ļ�URL
		resStr = new ReadResourceTextAction().execute(fileResource);

		// ������ǰ�ĳ�ʼ����������ǵ���㣬��ȡԴ��������ַ��������кš�
		pos = 0;
		char[] chs = resStr.toCharArray();
		char str;
		int line = 1; // ÿ����Ϊһ�����֣���1��ʼ����
		System.out.println("--------part" + line + ":");
		fileStr = fileStr + "--------part" + line + ":" + "\r\n";
		line++;

		// ��ʼ����ַ�����
		while (pos < chs.length) {
			int pos_pre;

			str = chs[pos];
			pos++; // ÿ�β����жϽ����Σ�����ǰ��һλ����ȡ��ǰ��Ҫ�жϵ��ַ�

			// ���ȼ���Ƿ�Ϊע��
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
			// �����ע�ͣ�����ע�Ͳ���
			if (exegesisLast != null) {
				System.out.println("exegesis : ");
				fileStr += "exegesis : " + "\r\n";
				// ���ע�Ͳ��ֽ�����
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

				temp = ""; // ���ɨ�軺��
				continue; // ������жϣ��Դ���Ԫ�ط���������ֱ������������һ��ѭ��
			}

			// �ж��Ƿ�Ϊ�����ֻ����Ǳ���������������
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

			// �ж��Ƿ�Ϊ����
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

			// �ж��Ƿ�Ϊ���з���ո��
			if (str == '\t' || str == '\n' || str == ' ') {
				// ���Ի��з������ո�����Բ�������
				if (str == '\t' || str == '\n') {
					System.out.println("\n--------part" + line + ":");
					fileStr = fileStr + "\r\n" + "--------part" + line + ":"
							+ "\r\n";

					line++;
				}

				continue;
			}

			// �ж��Ƿ�Ϊ�������Ԫ��
			int index_sepchar = -1;
			for (int i = 0; i < getSepchars().size(); i++) {
				if (str == getSepchars().get(i).getName().charAt(0)) {
					index_sepchar = i;
					break;
				}
			}
			// �ж�Ϊ�������Ԫ�أ���������������
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

		// ��������ı��ĵ����������д��
		new WriteAnsFileAction().execute(fileStr, fileAns);

		return fileStr;
	}
}
