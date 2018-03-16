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
 * �����롿pmd-final.txt
 * �������pmd-final-new.txt
 * ��ʱ�䡿2016-9-8 ���������ַ���ƥ����� ��������,��Package�� ,��File��,��[Priority]
 * ��,��Line(������)��,��Description(����������)��, ��Rule set��, ��Rule�� �ַ���һ���˸����֣�һ���Ա���������
 * �����˼·��(1).�Ȱ���File·���洢��list�� �洢��ʽ������ArrayList<ArrayList<String>> File����+��������
 * (2).�Ա�ɾ�� (3).����д���ĵ�֮�У�ͬʱ�����ֵ ��ע�����ȱ������delete��ɾ�� ȱ��û��file1����file2��Ӧ��������������
 */
public class match_String {
	// C://Users/dell/Desktop/fastjsonnot/
	// E://junit4/
	PathUtil pu=new PathUtil();
	public final String path1 =pu.StorePath_Root;// "E://junit4not/";
	public final String path2 =pu.checkfirstPath;//"/pmd-final.txt";
	public final String path3 =pu.newFinalPath;//"/pmd-final-new.txt";
	// public String[] all_str;
	public ArrayList<ArrayList<String>> list;// list���� File+��������

	public match_String() {
		// TODO Auto-generated constructor stub
		list = new ArrayList<ArrayList<String>>();
		// all_str=new String[2];
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
					// System.out.println("@@--"+main_title+"--"+str);
					String[] temp_st = str.split("\",\"");// һ���а˸�Ԫ��
					// System.out.println(str);
					// System.out.println("%%--"+temp_st[2]);
					// System.out.println("^^--"+main_title);
					// System.out.println(!temp_st[2].equals(main_title));
					if (!temp_st[2].equals(main_title)) {

						main_title = temp_st[2];
						list.add(sblist);

						// �����µ���list
						sblist = new ArrayList<String>();
						// sblist.add(main_title);

					}
					// �ų�����ȫɾ�������--���������ų�
					// if (!str.contains("��file1�����p�ٵ�code smell�� ")) {
					System.out.println(str);
					sblist.add(str);
					// }
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

		// System.out.println(str1+"--"+str2);
		int match_number = 0;
		String[] s1 = str1.split("\",\"");
		String[] s2 = str2.split("\",\"");
		// if(s1[4].equals("\"2519\"")){
		// Scanner sc=new Scanner(System.in);
		// sc.nextLine();
		// System.out.println(str1+" vs "+str2);
		// }

		// System.out.println(s1.length);
		// System.out.println(s2.length);
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
		// if(s1[4].equals("\"2519\"")){
		System.out.println("�÷�:" + match_number);
		// }
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
		// System.out.println(mt.compare_String(mt.all_str[0], mt.all_str[1]));
		for (int i = 0; i < list.size(); i++) {
			// �������������ͬ�ģ��ں������һ��-1��ʹ����list�ĳ��ȱ�λ9���ڽ�һ��д���ļ���ʱ������ѡ���ɾ��
			int loc = location(list.get(i));
			System.out.println("���ص�loc: " + loc);
			// System.out.println("λ��"+loc);
			if ((loc == 0)/* û��file2,ȫ��file1 */
					|| (loc == list.get(i).size() - 1 && list.get(i).get(loc).contains(
							"��file2�������ӵ�code smell��"))/* û��file1,ȫ��file2 */) {
				// û��file1 ˵��ȫ�����ӵ�
				// û��file2 ˵��ȫ�Ǽ��ٵ�
				// ����ȫ���꡾delete�� ---����ȫ����ɾ�����ԣ�Ӧ�ñ���
				// for (int u = 0; u < list.get(i).size(); u++) {
				// list.get(i).set(u, list.get(i).get(u).concat(" ��delete��"));
				// }
				System.out.println(loc);
				System.out.println(list.get(i));

			} else {
				// �������
				for (int j = 0; j < loc; j++) {
					// ͬһ���͵���list
					for (int k = loc; k < list.get(i).size(); k++) {
						// System.out.println(compare_String(list.get(i).get(j),
						// list.get(i).get(k)));
						if (compare_String(list.get(i).get(j), list.get(i).get(k)) == 6) {
							// �����ͬ�����
							list.get(i).set(j, list.get(i).get(j).concat("  ��delete��"));
							list.get(i).set(k, list.get(i).get(k).concat("  ��delete��"));
						}
					}
					// System.out.println();

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

	public static void main(String[] args) {
		match_String ms=new match_String();
		PathUtil pu=new PathUtil();
		for (int fn = 1; fn <= pu.refac_Number; fn++) {
			System.out.println("�����ļ�" + fn + "��...");
			match_String mt = new match_String();

			mt.doFile(ms.path1 + fn + ms.path2);
			System.out.println("doFile������");
			mt.do_List();
			System.out.println("do_List������");
			// System.out.println("д��֮ǰ�������");
			// for(int i=0;i<mt.list.size();i++){
			// for(int j=0;j<mt.list.get(i).size();j++){
			// System.out.println(mt.list.get(i).get(j));
			// }
			// }
			//
			mt.Rewrite(ms.path1 + fn + ms.path3);
			System.out.println("��д���");
			// ƥ���������6����֤������ͬ�ĵط���֮��ɾ��������ɾ��
			// System.out.println(mt.list.get(0).size());
		}

	}

}
