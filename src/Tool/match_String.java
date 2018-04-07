package Tool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Util.PathUtil;

/**
 * �����롿pmd-final.txt �������pmd-final-new.txt ��ʱ�䡿2016-9-8 ���������ַ���ƥ�����
 * ��������,��Package�� ,��File��,��[Priority] ��,��Line(������)��,��Description(����������)��, ��Rule
 * set��, ��Rule�� �ַ���һ���˸����֣�һ���Ա��������� �����˼·��(1).�Ȱ���File·���洢��list��
 * �洢��ʽ������ArrayList<ArrayList<String>> File����+�������� (2).�Ա�ɾ�� (3).����д���ĵ�֮�У�ͬʱ�����ֵ
 * ��ע�����ȱ������delete��ɾ�� ȱ��û��file1����file2��Ӧ��������������
 */
public class match_String {
	PathUtil pu = new PathUtil();
	public final String path1_1 = pu.SAR_StorePath_Root;
	public final String path1_2 = pu.nonSAR_StorePath_Root;
	public final String path2_1 = pu.checkfirstPath_SAR;
	public final String path2_2 = pu.checkfirstPath_nonSAR;
	public final String path3_1 = pu.newFinalPath_SAR;
	public final String path3_2 = pu.newFinalPath_nonSAR;
	public ArrayList<ArrayList<String>> list;// list���� File+��������

	public match_String() {
		list = new ArrayList<ArrayList<String>>();
	}

	// ���հ�����д�뵽list��ȥ
	public void doFile(String file_path) {
		String path = file_path;
		File f = new File(path);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String str = reader.readLine();
			if (str != null) {
				String[] st = str.split(",");

				String main_title = st[2];// ��Ҫ��File��������������������ݷ��뵽sblist��
				ArrayList<String> sblist = new ArrayList<String>();
				// ��st[2]����Ϊ���������st[2]�����ݸ����ˣ����½���һ����list������һ�ַ���
				// sblist.add(main_title);//�������
				while (str != null) {
					String[] temp_st = str.split("\",\"");// һ���а˸�Ԫ��
					if (!temp_st[2].equals(main_title)) {
						main_title = temp_st[2];
						list.add(sblist);
						// �����µ���list
						sblist = new ArrayList<String>();
					}
					// �ų�����ȫɾ�������--���������ų�
					System.out.println(str);
					sblist.add(str);
					str = reader.readLine();
					if (str == null) {
						list.add(sblist);
					}
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * ���룺�ַ���1���ַ���2 ������Աȳ̶�
	 * 
	 */
	// �ֽ�ͶԱ�
	public int compare_String(String str1, String str2) {
		int match_number = 0;
		String[] s1 = str1.split("\",\"");
		String[] s2 = str2.split("\",\"");
		// һ���а˸����� �Ա�1--2--3--6--7
		if (s1[1].equals(s2[1])) {
			match_number++;
			System.out.println("%%%%%%%%1");
		}
		if (s1[2].equals(s2[2])) {
			match_number++;
			System.out.println("%%%%%%%%2");
		}
		if (s1[3].equals(s2[3])) {
			match_number++;
			System.out.println("%%%%%%%%3");
		}
		System.out.println(s1[6]);
		System.out.println(s2[6]);
		if (s1[6].equals(s2[6])) {

			match_number++;
			System.out.println("%%%%%%%%4");
		}
		if (s1[7].equals(s2[7])) {
			match_number++;
			System.out.println("%%%%%%%%5");
		}
		// ���5�Ĳ��ֳ����ں������䣬���ҽ������滻�󳤶�һ�����ַ����ܹ����䡣��Ϊͬһ��
		if (s1[5].length() == s2[5].length() || Math.abs(s1[5].length() - s2[5].length()) <= 5) {
			// �������滻�ɿո�
			s1[5] = filterUnNumber(s1[5]);
			s2[5] = filterUnNumber(s2[5]);
			System.out.println(s1[5]);
			System.out.println(s2[5]);
			if (s1[5].equals(s2[5])) {
				System.out.println("%%%%%%%%6");
				match_number++;
			}
		}
		System.out.println("�÷�:" + match_number);
		return match_number;
	}

	/**
	 * ȷ�����о��С�file2�������ӵ�code smell���ĵ�һ��λ����Ϣ
	 * 
	 * ȱ��û��file2��Ӧ�Ĵ��� ȱ��û��file1��Ӧ�Ĵ���
	 * 
	 * loc�������0����û��file1 loc�������list.get(i).size(),�����к��С�file1�� ��û��file2
	 */
	public int location(ArrayList<String> li) {
		int location = 0;
		for (int i = 0; i < li.size(); i++) {
			if (li.get(i).contains("��file2�������ӵ�code smell��")) {
				location = i;
				break;
			}
		}
		return location;
	}

	/**
	 * ����list���� ������ҵ���ͬ���ݣ�����
	 */
	public void do_List() {
		for (int i = 0; i < list.size(); i++) {
			// �������������ͬ�ģ��ں������һ��-1��ʹ����list�ĳ��ȱ�λ9���ڽ�һ��д���ļ���ʱ������ѡ���ɾ��
			int loc = location(list.get(i));
			System.out.println("���ص�loc: " + loc);
			if ((loc == 0)/* û��file2,ȫ��file1 */
					|| (loc == list.get(i).size() - 1 && list.get(i).get(loc).contains(
							"��file2�������ӵ�code smell��"))/* û��file1,ȫ��file2 */) {
				// û��file1 ˵��ȫ�����ӵ�
				// û��file2 ˵��ȫ�Ǽ��ٵ�
				// ����ȫ���꡾delete�� ---����ȫ����ɾ�����ԣ�Ӧ�ñ���
				System.out.println(loc);
				System.out.println(list.get(i));

			} else {
				// �������
				for (int j = 0; j < loc; j++) {
					// ͬһ���͵���list
					for (int k = loc; k < list.get(i).size(); k++) {
						if (compare_String(list.get(i).get(j), list.get(i).get(k)) == 6) {
							// �����ͬ�����
							list.get(i).set(j, list.get(i).get(j).concat("  ��delete��"));
							list.get(i).set(k, list.get(i).get(k).concat("  ��delete��"));
						}
					}
				}
			}
		}
	}

	/**
	 * ���˵��ַ����е����� �����д��ڵģ�Ҳ���˵�
	 * 
	 */
	public static String filterUnNumber(String str) {
		// ֻ������
		// [^�������ų�������]
		String regEx = "([0-9](,))?[0-9]";// ��������֣���������ڣ������滻
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		// �滻��ģʽƥ��������ַ����������ֵ��ַ�����""�滻��
		return m.replaceAll("").trim();
	}

	/**
	 * ����д���ĵ���
	 */
	public void Rewrite(String path) {
		String file_path = path;
		File f = new File(file_path);
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(f));
			for (int i = 0; i < list.size(); i++) {
				for (int j = 0; j < list.get(i).size(); j++) {
					if (!list.get(i).get(j).contains("  ��delete��")) {
						writer.write(list.get(i).get(j));
						writer.write("\r\n");
					}
				}
			}
			writer.close();
		} catch (Exception e) {

		}
	}

	public void startMatch() {
		PathUtil pu = new PathUtil();
		for (int t = 1; t <= 2; t++) {
			String temppath;
			String temppath2;
			String temppath3;
			if (t == 1) {
				System.out.println("��ʼ����SAR�汾");
				temppath = path1_1;
				temppath2 = path2_1;
				temppath3 = path3_1;
			} else {
				System.out.println("��ʼ����non-SAR�汾");
				temppath = path1_2;
				temppath2 = path2_2;
				temppath3 = path3_2;
			}
			for (int fn = 1; fn <= pu.refac_Number; fn++) {
				System.out.println("�����ļ�" + fn + "��...");
				doFile(temppath + fn + temppath2);
				System.out.println("doFile������");
				do_List();
				System.out.println("do_List������");
				Rewrite(temppath + fn + temppath3);
				System.out.println("��д���");
				// ƥ���������6����֤������ͬ�ĵط���֮��ɾ��������ɾ��
			}
		}
	}

	public static void main(String[] args) {
		match_String ms = new match_String();
		ms.startMatch();

	}

}
