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
 * 【输入】pmd-final.txt
 * 【输出】pmd-final-new.txt
 * 【时间】2016-9-8 计算两个字符串匹配次数 【】【】,“Package” ,”File”,”[Priority]
 * ”,”Line(有问题)”,”Description(数字有问题)”, “Rule set”, “Rule” 字符串一共八个部分，一共对比六个部分
 * 【编程思路】(1).先按照File路径存储在list中 存储形式：两个ArrayList<ArrayList<String>> File内容+具体内容
 * (2).对比删除 (3).重新写入文档之中，同时计算差值 【注意事项】缺少明显delete的删除 缺少没有file1，或file2对应的情况会产生干扰
 */
public class match_String {
	// C://Users/dell/Desktop/fastjsonnot/
	// E://junit4/
	PathUtil pu=new PathUtil();
	public final String path1 =pu.StorePath_Root;// "E://junit4not/";
	public final String path2 =pu.checkfirstPath;//"/pmd-final.txt";
	public final String path3 =pu.newFinalPath;//"/pmd-final-new.txt";
	// public String[] all_str;
	public ArrayList<ArrayList<String>> list;// list内容 File+具体内容

	public match_String() {
		// TODO Auto-generated constructor stub
		list = new ArrayList<ArrayList<String>>();
		// all_str=new String[2];
	}

	// 按照包名，写入到list中去

	public void doFile(String file_path) {
		String path = file_path;
		File f = new File(path);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String str = reader.readLine();
			if (str != null) {
				String[] st = str.split(",");

				String main_title = st[2];// 主要的File根基，以这个根基将内容放入到sblist中

				ArrayList<String> sblist = new ArrayList<String>();
				// 以st[2]内容为根基，如果st[2]的内容更换了，重新建立一个子list进行下一轮分析

				// sblist.add(main_title);//加入根基

				while (str != null) {
					// System.out.println("@@--"+main_title+"--"+str);
					String[] temp_st = str.split("\",\"");// 一共有八个元素
					// System.out.println(str);
					// System.out.println("%%--"+temp_st[2]);
					// System.out.println("^^--"+main_title);
					// System.out.println(!temp_st[2].equals(main_title));
					if (!temp_st[2].equals(main_title)) {

						main_title = temp_st[2];
						list.add(sblist);

						// 创建新的子list
						sblist = new ArrayList<String>();
						// sblist.add(main_title);

					}
					// 排除掉完全删除的情况--！！不能排除
					// if (!str.contains("【file1】【減少的code smell】 ")) {
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
	 * 输入：字符串1，字符串2 输出：对比程度
	 * 
	 */
	// 分解和对比
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
		// 一共有八个部分 对比1--2--3--6--7
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
		// 如果5的部分长度在合理区间，并且将数字替换后长度一样，字符串能够比配。则为同一个
		if (s1[5].length() == s2[5].length() || Math.abs(s1[5].length() - s2[5].length()) <= 5) {
			// 将数字替换成空格
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
		System.out.println("得分:" + match_number);
		// }
		return match_number;
	}

	/**
	 * 确定其中具有【file2】【增加的code smell】的第一个位置信息
	 * 
	 * 缺少没有file2对应的处理 缺少没有file1对应的处理
	 * 
	 * loc如果返回0，则没有file1 loc如果返回list.get(i).size(),且其中含有【file1】 则没有file2
	 */
	public int location(ArrayList<String> li) {
		int location = 0;
		for (int i = 0; i < li.size(); i++) {
			if (li.get(i).contains("【file2】【增加的code smell】")) {
				location = i;
				break;
			}
		}
		return location;
	}

	/**
	 * 处理list内容 如果查找到相同内容，则标记
	 */
	public void do_List() {
		// System.out.println(mt.compare_String(mt.all_str[0], mt.all_str[1]));
		for (int i = 0; i < list.size(); i++) {
			// 如果遇到明显相同的，在后面添加一个-1，使得子list的长度变位9，在进一步写入文件中时进行有选择的删除
			int loc = location(list.get(i));
			System.out.println("返回的loc: " + loc);
			// System.out.println("位置"+loc);
			if ((loc == 0)/* 没有file2,全是file1 */
					|| (loc == list.get(i).size() - 1 && list.get(i).get(loc).contains(
							"【file2】【增加的code smell】"))/* 没有file1,全是file2 */) {
				// 没有file1 说明全是增加的
				// 没有file2 说明全是减少的
				// 下面全部标【delete】 ---这里全部标删除不对，应该保留
				// for (int u = 0; u < list.get(i).size(); u++) {
				// list.get(i).set(u, list.get(i).get(u).concat(" 【delete】"));
				// }
				System.out.println(loc);
				System.out.println(list.get(i));

			} else {
				// 正常情况
				for (int j = 0; j < loc; j++) {
					// 同一类型的子list
					for (int k = loc; k < list.get(i).size(); k++) {
						// System.out.println(compare_String(list.get(i).get(j),
						// list.get(i).get(k)));
						if (compare_String(list.get(i).get(j), list.get(i).get(k)) == 6) {
							// 如果相同被标记
							list.get(i).set(j, list.get(i).get(j).concat("  【delete】"));
							list.get(i).set(k, list.get(i).get(k).concat("  【delete】"));
						}
					}
					// System.out.println();

				}
			}
		}
	}

	/**
	 * 过滤掉字符串中的数字 数字中存在的，也过滤掉
	 * 
	 */
	public static String filterUnNumber(String str) {
		// 只允数字
		// [^这里添排除的内容]
		String regEx = "([0-9](,))?[0-9]";// 如果是数字，或里面存在，都将替换
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		// 替换与模式匹配的所有字符（即非数字的字符将被""替换）
		return m.replaceAll("").trim();
	}

	/**
	 * 重新写入文档中
	 */
	public void Rewrite(String path) {
		String file_path = path;
		File f = new File(file_path);
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(f));
			for (int i = 0; i < list.size(); i++) {
				for (int j = 0; j < list.get(i).size(); j++) {
					if (!list.get(i).get(j).contains("  【delete】")) {
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
			System.out.println("处理文件" + fn + "中...");
			match_String mt = new match_String();

			mt.doFile(ms.path1 + fn + ms.path2);
			System.out.println("doFile处理完");
			mt.do_List();
			System.out.println("do_List处理完");
			// System.out.println("写入之前检查内容");
			// for(int i=0;i<mt.list.size();i++){
			// for(int j=0;j<mt.list.get(i).size();j++){
			// System.out.println(mt.list.get(i).get(j));
			// }
			// }
			//
			mt.Rewrite(ms.path1 + fn + ms.path3);
			System.out.println("重写完成");
			// 匹配数如果是6，则证明是相同的地方，之后删除。否则不删除
			// System.out.println(mt.list.get(0).size());
		}

	}

}
