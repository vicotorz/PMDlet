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
 * 【输入】rule_sets.txt(事先准备好，准备一份就好)
 * 
 * 【时间】2016.11.22
 * 
 * 【作用】对code_semll_information.csv文件进行读取，然后加列 "code_smell名称", "priority"
 * 
 * 【程序步骤】给检测到的code smell 添加优先级这一列 一共242个检测到的code smells
 * 
 * (1).先访问123456，拆分 <rule ref="rulesets/design.xml/FinalFieldCouldBeStatic"/>
 * 这个字符串中，获取到design.xml和FinalFieldCouldBeStatic两个字符串 把同是属于同一个xml中的后缀都放到一起
 * (2).统一访问，然后放到ArrayList中 (3).对code_semll_information.csv文件进行读取，然后加列
 * 
 * 
 * 【操作前提】：有rule_sets.txt 以及各种xml文件
 */
public class addFunctions {

	public final String path = "D:\\rule_sets.txt";
	public static final String basepath = "D:\\\\";
	public static final String Csvpath = "D:\\priorities_information.csv";

	public ArrayList<String> list;// 装xml主题

	public ArrayList<ArrayList<String>> list_c;// 每个主题下的小内容

	public String[][] info;

	public String[][] getInfo() {
		return info;
	}

	public addFunctions() {
		list = new ArrayList<String>();
		list_c = new ArrayList<ArrayList<String>>();
	}

	// 1. 访问拆分
	// 结果放在ArrayList中
	// 两个ArrayList
	// 前期处理了一下
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
			// 处理
			while (str != null) {
				// <rule ref="rulesets/design.xml/FinalFieldCouldBeStatic"/>
				String[] s = str.split("/");
				// <rule ref="rulesets--design.xml--UseSingleton"-->
				// System.out.println(s.length);
				// System.out.println(s[0]+"--"+s[1]+"--"+s[2]+"--"+s[3]);
				s[2] = s[2].substring(0, s[2].length() - 1);
				// 这里涉及到
				ss.add(s[1]);
				if (size != ss.size()) {
					// 新的主题
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

	// 2. 对规则进行具体文件访问
	public void fetechpriorities() {
		// 访问D盘的xxx
		for (int o = 0; o < list.size(); o++) {
			String path = basepath.concat(list.get(o));
			priorities(path, o);
		}
	}

	// 2.1 访问文件
	// 每一个name都去list中对比，如果对比成功，放在list最后，以"-"拼接
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
					// 开始
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
					// 结束
					addstring(name, priority, i);
				}
				string = br.readLine();
			}
			// Scanner sc=new Scanner(System.in);
			// sc.nextLine();
		} catch (Exception e) {
			System.out.println("访问：" + path);
			e.printStackTrace();
		}
	}

	// 2.1.1需要调用很多次
	public void addstring(String name, String priority, int i) {
		// System.out.println("调用了啊");
		// System.out.println(name);
		for (int n = 0; n < list_c.get(i).size(); n++) {
			if (list_c.get(i).get(n).equals(name)) {
				String content = list_c.get(i).get(n);
				String Acontent = content + "-" + priority;
				list_c.get(i).set(n, Acontent);// 重新设置
				break;
			}
		}
	}

	// 2.5转成二维数组
	public void changeArray() {
		// list_c中的内容
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

//	// 3.读取文件加列
//	// 【给两个文件后面加列】
//	// information.csv
//	// code_semll_information.csv
//	public void addColumn() {
//
//	}

	// 将priorities信息单独存入到csv文件中
	public void wirtePriority() {
		CsvWriter wr = new CsvWriter(Csvpath, ',', Charset.forName("GBK"));
		String[] header = { "code_smell名称", "priority" };
		try {
			wr.writeRecord(header);
			String[][] content = new String[info.length][2];

			// 赋初值
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
