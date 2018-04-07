package Tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.csvreader.CsvWriter;

import Util.PathUtil;

/**
 * �����롿(1)xxx-R, (2)pmd-final-new.txt �������xx-information.csv ��ʱ�䡿 10.8(�޸�
 * ����������ָ���ж�) 10.15�޸� ����һ������ ��⵽��code smell��file1������,��⵽��code-smell��file2������
 * ��Ӧ��ֵ 11.23���� priority����
 * 
 * �����á���¼����ÿ���汾������code semlls���ͺ���Ŀ ÿ��ʵ���е�pmd-final-new.txt����ͳ��
 * 
 * ��������ʽ���汾 |����|�޸�����|pmd��⵽code
 * smell����|����|code-smells����|file1-file2��ֵ|�Ƿ�����������|priority
 * 
 */

// ��¼pmd��⵽��ȥ���code smells����
public class calculate_codesmells {
	PathUtil pu = new PathUtil();
	public final int file_number = pu.refac_Number;
	public final String R_path = pu.R_path_SAR;
	public final String nonR_path = pu.R_path_nonSAR;
	public final String output_file_SAR = pu.info_Path;
	public final String output_file_nonSAR = pu.non_info_Path;
	public final String p1_1 = pu.SAR_StorePath_Root;
	public final String p1_2 = pu.nonSAR_StorePath_Root;
	public final String p2_1 = pu.newFinalPath_SAR;
	public final String p2_2 = pu.newFinalPath_nonSAR;

	public ArrayList<String[][]> list;

	public ArrayList<String> version;// �汾
	public ArrayList<String> authors;// ����
	public ArrayList<Integer> edit;// �޸ĵط�������

	public ArrayList<Integer> filenum;// ��¼file1-file2�Ĳ�ֵ
	public ArrayList<Integer> rename;// �Ƿ�����������0-û�� -1-��
	public ArrayList<Integer> addfile;// �Ѿ����
	public ArrayList<Integer> deletefile;// �Ѿ�ɾ��

	public String[][] ss;

	public calculate_codesmells() {
		list = new ArrayList<String[][]>();
		version = new ArrayList<String>();
		authors = new ArrayList<String>();
		edit = new ArrayList<Integer>();

		filenum = new ArrayList<Integer>();
		rename = new ArrayList<Integer>();
		addfile = new ArrayList<Integer>();
		deletefile = new ArrayList<Integer>();
	}

	// ͳ�ư汾�����ߣ��޸�����(һ���Զ�ȡ)
	public void record_version_author_number(String path, int file_number) {
		try {
			File f = new File(path);
			BufferedReader br = new BufferedReader(new FileReader(f));
			String str = br.readLine();
			ArrayList<String> editcontent = new ArrayList<String>();
			boolean flag = false;
			int index = 0;
			boolean ff = false;// ����ֻ����һ��
			boolean g = false;// ����Ƿ����������
			boolean g1 = false;// ����Ƿ���������
			boolean g2 = false;// ����Ƿ������ɾ��
			while (str != null && index <= file_number) {
				if (str.startsWith("�汾:")) {
					// �еİ汾����û�м�¼modification�����
					if (version.size() != edit.size() && version.size() != rename.size()
							&& version.size() != addfile.size() && version.size() != deletefile.size() && index != 0) {
						edit.add(0);
						rename.add(0);
						addfile.add(0);
						deletefile.add(0);
					}
					version.add(str.substring(4, str.length()));
					index++;
				}

				if (str.startsWith("����: ")) {
					authors.add(str.substring(4, str.length()));
				}
				if (flag && (str.contains("���޸�:") || str.contains("��ɾ��:") || str.contains("�����:")
						|| str.contains("������:"))) {
					if (str.contains("������:")) {
						g = true;
					}
					if (str.contains("�����")) {
						g1 = true;
					}
					if (str.contains("��ɾ��")) {
						g2 = true;
					}

					editcontent.add(str);
					ff = true;
				} else if (ff) {
					// System.out.println("");
					// Scanner sc=new Scanner(System.in);
					// sc.nextLine();
					// ��������
					// System.out.println(editcontent.size());
					if (g) {
						rename.add(1);
					} else {
						rename.add(0);
					}
					if (g1) {
						addfile.add(1);
					} else {
						addfile.add(0);
					}
					if (g2) {
						deletefile.add(1);
					} else {
						deletefile.add(0);
					}

					edit.add(editcontent.size());
					flag = false;
					editcontent.clear();
					ff = false;
					g = false;
					g1 = false;
					g2 = false;
				}

				if (str.startsWith("----")) {
					flag = true;
				}

				str = br.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ��pmd���漰��code smell���뵽Set�У�Ȼ��ͳ�Ƹ���
	// code smell���� ��Ӧ����(��������)
	public void put_code_smell_to_Set(String path) {
		Set<String> set = new HashSet<String>();
		try {
			File f = new File(path);
			BufferedReader br = new BufferedReader(new FileReader(f));
			String str = br.readLine();

			int file1_number = 0;
			int file2_number = 0;
			while (str != null) {
				// ͳ�Ƹ���
				if (str.contains("��file1��")) {
					file1_number++;
				}
				if (str.contains("��file2��")) {
					file2_number++;
				}
				String[] sarray = str.split(",");
				set.add(sarray[sarray.length - 1]);
				str = br.readLine();
			}
			// ��code-smell���뵽Set�У�ͳ��set��С
			// ���������� ����--����--file1����--file2����
			String[][] code_smells = new String[set.size()][4];
			Iterator it = set.iterator();
			for (int i = 0; i < set.size(); i++) {
				code_smells[i][0] = it.next().toString();
				code_smells[i][1] = String.valueOf("0");
				code_smells[i][2] = String.valueOf("0");
				code_smells[i][3] = String.valueOf("0");
			}
			// �ٴζ�ȡ��ͳ��
			BufferedReader Zbr = new BufferedReader(new FileReader(f));
			String string = Zbr.readLine();
			// System.out.println("���ϴ�С"+set.size());
			while (string != null) {
				String[] sarray = string.split(",");
				for (int t = 0; t < set.size(); t++) {
					if (sarray[sarray.length - 1].equals(code_smells[t][0])) {
						code_smells[t][1] = String.valueOf(Integer.valueOf(code_smells[t][1]) + 1);
						if (sarray[0].contains("��file1��")) {
							code_smells[t][2] = String.valueOf(Integer.valueOf(code_smells[t][2]) + 1);
						}

						if (sarray[0].contains("��file2��")) {
							code_smells[t][3] = String.valueOf(Integer.valueOf(code_smells[t][3]) + 1);
						}
					}
				}
				string = Zbr.readLine();
			}
			list.add(code_smells);
			filenum.add(file2_number - file1_number);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println("��������");
			e.printStackTrace();
		}
	}

	// ����code-smell������
	public String numberlet(String[][] t) {
		int size = t.length;
		int total = 0;
		for (int u = 0; u < size; u++) {
			total = total + Integer.valueOf(t[u][1]);
		}
		return String.valueOf(total);
	}

	// ����addFuction�ļ�
	public void getPriority() {
		addFunctions af = new addFunctions();
		af.splitStrings();
		af.fetechpriorities();
		af.changeArray();
		ss = new String[af.getInfo().length][2];
		ss = af.getInfo();

	}

	// Return priority
	public String RetrunPriority(String name) {
		String instance = null;
		instance = name.substring(1, name.length() - 1);

		String priority = null;
		for (int i = 0; i < ss.length; i++) {
			if (ss[i][0].equals(instance)) {
				priority = ss[i][1];
				break;
			}
		}
		return priority;
	}

	public void saveFile(String path, int file_number) {
		getPriority();
		try {
			// ��version authors edit list�е�����д��ȥ
			// д�뵽csv�ļ���
			System.out.println("fuck");
			CsvWriter wr = new CsvWriter(path, ',', Charset.forName("GBK"));
			// �汾 ���� �޸����� pmd��⵽code smell���� ���� code-smells���� file1-file2��ֵ
			// �Ƿ�����������
			String[] header = { "���", "�汾", "����", "�޸ĸ���", "pmd��⵽code-smell", "��Ӧ������", "codesmell����",
					"file2-file1��ֵ(��Ϊ��)", "�Ƿ���������", "��⵽��code-smell��file1������", "��⵽��code-smell��file2������", "��Ӧ����������",
					"priority" };
			// 0 1 2 3 4 5 6 7 8 9 10 11 12
			wr.writeRecord(header);

			int size = file_number;
			System.out.println(size);
			for (int i = 0; i < size; i++) {
				int code_smell_size = list.get(i).length;
				System.out.println(i + "---" + code_smell_size);
				String[][] content;

				if (code_smell_size != 0) {

					content = new String[code_smell_size][13];
					for (int j = 0; j < code_smell_size; j++) {
						if (j == 0) {
							System.out.println("�鿴��Ϣ");
							content[j][0] = String.valueOf(i + 1);
							content[j][1] = version.get(i);
							content[j][2] = authors.get(i);
							content[j][3] = String.valueOf(edit.get(i));
							content[j][6] = numberlet(list.get(i));
							content[j][7] = String.valueOf(filenum.get(i));
							String rn = "";
							if (rename.get(i) == 1) {
								rn = rn + "|������";
							}
							if (addfile.get(i) == 1) {
								rn = rn + "|�����";
							}
							if (deletefile.get(i) == 1) {
								rn = rn + "|��ɾ��";
							}
							content[j][8] = rn;
						} else {
							content[j][0] = "";
							content[j][1] = "";
							content[j][2] = "";
							content[j][3] = "";
							content[j][6] = "";
							content[j][7] = "";
							content[j][8] = "";
						}
						content[j][4] = list.get(i)[j][0];
						content[j][5] = list.get(i)[j][1];
						content[j][9] = list.get(i)[j][2];// ��Ӧcode-smell��file1������
						content[j][10] = list.get(i)[j][3];// ��Ӧcode-smell��file2������
						content[j][11] = String
								.valueOf(Integer.valueOf(list.get(i)[j][3]) - Integer.valueOf(list.get(i)[j][2]));// ��ֵ
						content[j][12] = /* RetrunPriority(list.get(i)[j][4]) */RetrunPriority(list.get(i)[j][0]);
						wr.writeRecord(content[j]);
					}
				} else {
					System.out.println("code smell��⵽0");
					content = new String[1][13];
					content[0][0] = String.valueOf(i + 1);
					content[0][1] = version.get(i);
					content[0][2] = authors.get(i);
					content[0][3] = String.valueOf(edit.get(i));
					content[0][4] = "";
					content[0][5] = "";
					content[0][6] = "0";
					content[0][7] = "0";
					String rn = "";
					if (rename.get(i) == 1) {
						rn = rn + "|������";
					}
					if (addfile.get(i) == 1) {
						rn = rn + "|�����";
					}
					if (deletefile.get(i) == 1) {
						rn = rn + "|��ɾ��";
					}
					content[0][8] = rn;
					content[0][9] = "";// ��Ӧcode-smell��file1������
					content[0][10] = "";// ��Ӧcode-smell��file2������
					content[0][11] = "";// ��ֵ
					content[0][12] = "";// priority
					wr.writeRecord(content[0]);
				}
			}
			wr.close();

		} catch (Exception e) {
			System.err.println("��ȡ��������");
			e.printStackTrace();
		}
	}

	// չʾ������Ϣ
	public void showAll(int file_number) {
		for (int num = 0; num < file_number; num++) {
			System.out.println(version.get(num));
			System.out.println(authors.get(num));
			System.out.println(edit.get(num));
			int size = list.get(num).length;
			for (int j = 0; j < size; j++) {
				System.out.println(list.get(num)[j][0] + "---" + list.get(num)[j][1]);
			}
		}
	}

	public void Start(String str) {
		String R_non_path;
		String path1;
		String path2;
		String output_path;
		if (str.equals("SAR")) {
			R_non_path = R_path;
			path1 = p1_1;
			path2 = p2_1;
			output_path = output_file_SAR;
		} else {
			R_non_path = nonR_path;
			path1 = p2_1;
			path2 = p2_2;
			output_path = output_file_nonSAR;
		}
		// ��ȡǰ����
		record_version_author_number(R_non_path, file_number);
		System.out.println("ǰ�����ȡ���");
		System.out.println("�汾������" + version.size() + "---����������" + authors.size() + "---�޸ļ�¼����" + edit.size()
				+ "---������������" + rename.size());
		// ������
		for (int fn = 1; fn <= file_number; fn++) {
			put_code_smell_to_Set(path1 + fn + path2);
		}
		System.out.println("�������ȡ���");
		// ccs.showAll(file_number);
		// ����
		saveFile(output_path, file_number);
		System.out.println("�������");
	}

	public static void main(String[] args) {
		calculate_codesmells ccs = new calculate_codesmells();
		ccs.Start("SAR");
		ccs.Start("nonSAR");

	}

}
