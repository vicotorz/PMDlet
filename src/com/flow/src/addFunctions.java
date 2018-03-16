package com.flow.src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.csvreader.CsvWriter;

/**
 * �����롿rule_sets.txt(����׼���ã�׼��һ�ݾͺ�)
 * 
 * ��ʱ�䡿2016.11.22
 * 
 * �����á���code_semll_information.csv�ļ����ж�ȡ��Ȼ����� "code_smell����", "priority"
 * 
 * �������衿����⵽��code smell ������ȼ���һ�� һ��242����⵽��code smells
 * 
 * (1).�ȷ���123456����� <rule ref="rulesets/design.xml/FinalFieldCouldBeStatic"/>
 * ����ַ����У���ȡ��design.xml��FinalFieldCouldBeStatic�����ַ��� ��ͬ������ͬһ��xml�еĺ�׺���ŵ�һ��
 * (2).ͳһ���ʣ�Ȼ��ŵ�ArrayList�� (3).��code_semll_information.csv�ļ����ж�ȡ��Ȼ�����
 * 
 * 
 * ������ǰ�᡿����rule_sets.txt �Լ�����xml�ļ�
 */
public class addFunctions {

	public final String path = "D:\\rule_sets.txt";
	public static final String basepath = "D:\\\\";
	public static final String Csvpath = "D:\\priorities_information.csv";

	public ArrayList<String> list;// װxml����

	public ArrayList<ArrayList<String>> list_c;// ÿ�������µ�С����

	public String[][] info;

	public String[][] getInfo() {
		return info;
	}

	public addFunctions() {
		list = new ArrayList<String>();
		list_c = new ArrayList<ArrayList<String>>();
	}

	// 1. ���ʲ��
	// �������ArrayList��
	// ����ArrayList
	// ǰ�ڴ�����һ��
	public void splitStrings() {
		File f = new File(path);
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(f));
			String str;
			str = br.readLine();

			Set<String> ss = new HashSet<String>();
			int size = 0;
			ArrayList<String> al = new ArrayList<String>();
			// ����
			while (str != null) {
				// <rule ref="rulesets/design.xml/FinalFieldCouldBeStatic"/>
				String[] s = str.split("/");
				// <rule ref="rulesets--design.xml--UseSingleton"-->
				// System.out.println(s.length);
				// System.out.println(s[0]+"--"+s[1]+"--"+s[2]+"--"+s[3]);
				s[2] = s[2].substring(0, s[2].length() - 1);
				// �����漰��
				ss.add(s[1]);
				if (size != ss.size()) {
					// �µ�����
					if (!al.isEmpty()) {
						list_c.add(al);
					}
					size = ss.size();
					list.add(s[1]);
					al = new ArrayList<String>();
					al.add(s[2]);
				} else {
					al.add(s[2]);

				}
				str = br.readLine();
			}
			list_c.add(al);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 2. �Թ�����о����ļ�����
	public void fetechpriorities() {
		// ����D�̵�xxx
		for (int o = 0; o < list.size(); o++) {
			String path = basepath.concat(list.get(o));
			priorities(path, o);
		}
	}

	// 2.1 �����ļ�
	// ÿһ��name��ȥlist�жԱȣ�����Աȳɹ�������list�����"-"ƴ��
	// <rule name="MoreThanOneLogger"
	// <priority>2</priority>
	public void priorities(String path, int i) {
		File f = new File(path);
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(f));
			String string;
			string = br.readLine();
			String name = "";
			String priority = "";
			while (string != null) {
				if (string.contains("<rule") && !string.contains("set")) {
					// ��ʼ
					// System.out.println(string);
					//
					// System.out.println("$$$$$$$$$$$$$");
					// System.out.println(string);
					String[] s = string.split("\"");
					// System.out.println(s.length);
					// System.out.println(s[0]);
					// System.out.println(s[1]);
					// System.out.println("-------------");
					name = s[1].substring(0, s[1].length());
				}
				if (string.contains("<priority>")) {
					// System.out.println(string);
					// Scanner sc=new Scanner(System.in);
					// sc.nextLine();
					String[] str = string.split(">");
					String[] strr = str[1].split("<");
					priority = strr[0];
				}
				if (string.contains("</rule>")) {
					// ����
					addstring(name, priority, i);
				}
				string = br.readLine();
			}
			// Scanner sc=new Scanner(System.in);
			// sc.nextLine();
		} catch (Exception e) {
			System.out.println("���ʣ�" + path);
			e.printStackTrace();
		}
	}

	// 2.1.1��Ҫ���úܶ��
	public void addstring(String name, String priority, int i) {
		// System.out.println("�����˰�");
		// System.out.println(name);
		for (int n = 0; n < list_c.get(i).size(); n++) {
			if (list_c.get(i).get(n).equals(name)) {
				String content = list_c.get(i).get(n);
				String Acontent = content + "-" + priority;
				list_c.get(i).set(n, Acontent);// ��������
				break;
			}
		}
	}

	// 2.5ת�ɶ�ά����
	public void changeArray() {
		// list_c�е�����
		try {
			int number = 0;
			for (int i = 0; i < list_c.size(); i++) {
				number = number + list_c.get(i).size();
			}
			info = new String[number][2];
			int step_number = 0;
			for (int i = 0; i < list_c.size(); i++) {

				for (int j = 0; j < list_c.get(i).size(); j++) {
					String str = list_c.get(i).get(j);
					String[] sss = str.split("-");
					info[step_number][0] = sss[0];
					info[step_number][1] = sss[1];
					step_number++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	// 3.��ȡ�ļ�����
//	// ���������ļ�������С�
//	// information.csv
//	// code_semll_information.csv
//	public void addColumn() {
//
//	}

	// ��priorities��Ϣ�������뵽csv�ļ���
	public void wirtePriority() {
		CsvWriter wr = new CsvWriter(Csvpath, ',', Charset.forName("GBK"));
		String[] header = { "code_smell����", "priority" };
		try {
			wr.writeRecord(header);
			String[][] content = new String[info.length][2];

			// ����ֵ
			for (int i = 0; i < info.length; i++) {
				content[i][0] = info[i][0];
				content[i][1] = info[i][1];
				wr.writeRecord(content[i]);
			}
			wr.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void showAll() {
		for (int u = 0; u < list_c.get(0).size(); u++) {
			System.out.print(list_c.get(0).get(u) + " ");
		}
		try {
			for (int i = 0; i < info.length; i++) {

				System.out.println(info[i][0] + "  " + info[i][1]);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		addFunctions af = new addFunctions();
		af.splitStrings();
		af.fetechpriorities();
		af.changeArray();
		af.showAll();
	}
}
