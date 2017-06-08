package com.flow.src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.csvreader.CsvWriter;

/**
 * 
 * 记录产生每个版本产生的code semlls类型和数目 每个实验中的pmd-final-new.txt进行统计 结果统一保存 挑出这次修改的信息并保存
 * 保存形式 =============== x =============== 版本 作者 修改数量 pmd检测到code smell内容 数量
 * code-smells总数 file1-file2差值 是否有重命名？ priority 12XXXX DataflowAnomalyAnalysis
 * 10
 * 
 * 
 * fastjson-R ... ... pmd-final-new pmd-final-new
 * 
 * 10.8修改 加入了其他指标判断 10.15修改 增加一个新列 检测到的code smell（file1数量）
 * 检测到的code-smell（file2数量） 对应差值
 * 
 * 11.23加入 priority新列
 */

// 记录pmd检测到的去噪的code smells内容
public class calculate_codesmells {
	public ArrayList<String[][]> list;

	public ArrayList<String> version;// 版本
	public ArrayList<String> authors;// 作者
	public ArrayList<Integer> edit;// 修改地方的数量

	public ArrayList<Integer> filenum;// 记录file1-file2的差值
	public ArrayList<Integer> rename;// 是否含有重命名？0-没有 -1-有
	public ArrayList<Integer> addfile;// 已经添加
	public ArrayList<Integer> deletefile;// 已经删除

	public String[][] ss;

	calculate_codesmells() {
		list = new ArrayList<String[][]>();
		version = new ArrayList<String>();
		authors = new ArrayList<String>();
		edit = new ArrayList<Integer>();

		filenum = new ArrayList<Integer>();
		rename = new ArrayList<Integer>();
		addfile = new ArrayList<Integer>();
		deletefile = new ArrayList<Integer>();
	}

	// 统计版本，作者，修改数量(一次性读取)
	public void record_version_author_number(String path, int file_number) {
		// 访问fastjson-R.txt
		// 依次访问即可
		try {
			File f = new File(path);
			BufferedReader br = new BufferedReader(new FileReader(f));
			String str = br.readLine();
			ArrayList<String> editcontent = new ArrayList<String>();
			boolean flag = false;
			int index = 0;
			boolean ff = false;// 控制只进入一次
			boolean g = false;// 检测是否存在重命名
			boolean g1 = false;// 检测是否存在已添加
			boolean g2 = false;// 检测是否存在已删除
			while (str != null && index <= file_number) {
				// System.out.println(str);
				// Scanner sc=new Scanner(System.in);
				// sc.nextLine();

				if (str.startsWith("版本:")) {

					// 有的版本出现没有记录modification的情况
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

				if (str.startsWith("作者: ")) {
					authors.add(str.substring(4, str.length()));
				}

				if (flag && (str.contains("已修改:") || str.contains("已删除:") || str.contains("已添加:")
						|| str.contains("重命名:"))) {

					// System.out.println("查找到修改");
					// Scanner sc=new Scanner(System.in);

					if (str.contains("重命名:")) {
						// sc.nextLine();
						g = true;
					}
					if (str.contains("已添加")) {
						g1 = true;
					}
					if (str.contains("已删除")) {
						g2 = true;
					}

					editcontent.add(str);
					ff = true;
				} else if (ff) {
					// System.out.println("");
					// Scanner sc=new Scanner(System.in);
					// sc.nextLine();
					// 遇到空行
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
				// System.out.println("预读字符串："+str);
				// System.out.println(str==null);
				// System.out.println(str!=null);
				// System.out.println(index<=file_number);
				// System.out.println(index);
				// System.out.println(file_number);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 将pmd中涉及的code smell放入到Set中，然后统计个数
	// code smell类型 对应数量(单步调用)
	public void put_code_smell_to_Set(String path) {
		// System.out.println("调用");
		Set<String> set = new HashSet<String>();
		try {
			File f = new File(path);
			BufferedReader br = new BufferedReader(new FileReader(f));
			String str = br.readLine();

			int file1_number = 0;
			int file2_number = 0;
			while (str != null) {
				// 统计个数
				if (str.contains("【file1】")) {
					file1_number++;
				}

				if (str.contains("【file2】")) {
					file2_number++;
				}

				String[] sarray = str.split(",");
				set.add(sarray[sarray.length - 1]);
				str = br.readLine();
			}
			// 将code-smell放入到Set中，统计set大小
			// 创建的数组 名称--数量--file1数量--file2数量
			String[][] code_smells = new String[set.size()][4];
			Iterator it = set.iterator();
			for (int i = 0; i < set.size(); i++) {
				code_smells[i][0] = it.next().toString();
				code_smells[i][1] = String.valueOf("0");
				code_smells[i][2] = String.valueOf("0");
				code_smells[i][3] = String.valueOf("0");
			}

			// 再次读取，统计
			BufferedReader Zbr = new BufferedReader(new FileReader(f));
			String string = Zbr.readLine();
			// System.out.println("集合大小"+set.size());
			while (string != null) {
				String[] sarray = string.split(",");
				for (int t = 0; t < set.size(); t++) {
					// System.out.println(sarray[sarray.length-1]);
					// System.out.println(code_smells[t][0]);
					if (sarray[sarray.length - 1].equals(code_smells[t][0])) {
						// System.out.println(sarray[sarray.length-1]);
						// System.out.println(code_smells[t][0]);

						code_smells[t][1] = String.valueOf(Integer.valueOf(code_smells[t][1]) + 1);
						// System.out.println(code_smells[t][1]);
						if (sarray[0].contains("【file1】")) {
							code_smells[t][2] = String.valueOf(Integer.valueOf(code_smells[t][2]) + 1);
						}

						if (sarray[0].contains("【file2】")) {
							code_smells[t][3] = String.valueOf(Integer.valueOf(code_smells[t][3]) + 1);
						}

					}
					// Scanner sc=new Scanner(System.in);
					// sc.nextLine();
				}
				string = Zbr.readLine();
			}
			// System.out.println("添加code smell"+code_smells);
			list.add(code_smells);
			filenum.add(file2_number - file1_number);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println("发生错误");
			e.printStackTrace();
		}
	}

	// 计算code-smell的总数
	public String numberlet(String[][] t) {
		int size = t.length;
		int total = 0;
		for (int u = 0; u < size; u++) {
			total = total + Integer.valueOf(t[u][1]);
		}
		return String.valueOf(total);
	}

	// 访问addFuction文件
	public void getPriority() {
		// *************************************
		addFunctions af = new addFunctions();
		af.splitStrings();
		af.fetechpriorities();

		af.changeArray();
		// System.out.println(af.getInfo().length);
		ss = new String[af.getInfo().length][2];
		ss = af.getInfo();
		// System.out.println(ss[0][0]+" "+ss[0][1]);
		// ************************************
	}

	// Return priority
	public String RetrunPriority(String name) {
		// "UseSingleton"
		String instance = null;

		// System.out.println(name);
		// System.out.println(name.length());
		instance = name.substring(1, name.length() - 1);
		// System.out.println(instance);
		// Scanner sc=new Scanner(System.in);
		// sc.nextLine();

		String priority = null;
		for (int i = 0; i < ss.length; i++) {
			if (ss[i][0].equals(instance)) {
				priority = ss[i][1];
				break;
			}
		}
		// System.out.println(priority);
		return priority;
	}

	public void saveFile(String path, int file_number) {
		getPriority();
		try {
			// System.out.println("存入！！");
			// File f=new File(path);
			// BufferedWriter bw=new BufferedWriter(new FileWriter(f));
			// 将version authors edit list中的内容写进去
			// 写入到csv文件中
			System.out.println("fuck");
			CsvWriter wr = new CsvWriter(path, ',', Charset.forName("GBK"));
			// 版本 作者 修改数量 pmd检测到code smell内容 数量 code-smells总数 file1-file2差值
			// 是否有重命名？
			String[] header = { "序号", "版本", "作者", "修改个数", "pmd检测到code-smell", "对应的数量", "codesmell总数",
					"file2-file1差值(正为增)", "是否有重命名", "检测到的code-smell在file1中数量", "检测到的code-smell在file2中数量", "对应数量增减度",
					"priority" };
			// 0 1 2 3 4 5 6 7 8 9 10 11 12
			wr.writeRecord(header);

			int size = file_number;
			System.out.println(size);
			// System.out.println("准备进入循环");
			for (int i = 0; i < size; i++) {
				// System.out.println("循环。。。");
				int code_smell_size = list.get(i).length;
				System.out.println(i + "---" + code_smell_size);
				String[][] content;

				if (code_smell_size != 0) {

					content = new String[code_smell_size][13];
					for (int j = 0; j < code_smell_size; j++) {
						if (j == 0) {
							System.out.println("查看信息");
							content[j][0] = String.valueOf(i + 1);
							content[j][1] = version.get(i);
							content[j][2] = authors.get(i);
							content[j][3] = String.valueOf(edit.get(i));
							content[j][6] = numberlet(list.get(i));
							content[j][7] = String.valueOf(filenum.get(i));
							String rn = "";
							if (rename.get(i) == 1) {
								rn = rn + "|重命名";
							}
							if (addfile.get(i) == 1) {
								rn = rn + "|已添加";
							}
							if (deletefile.get(i) == 1) {
								rn = rn + "|已删除";
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
						content[j][9] = list.get(i)[j][2];// 对应code-smell在file1中数量
						content[j][10] = list.get(i)[j][3];// 对应code-smell在file2中数量
						content[j][11] = String
								.valueOf(Integer.valueOf(list.get(i)[j][3]) - Integer.valueOf(list.get(i)[j][2]));// 差值
						// System.out.println("$%$%#$#@$@"+RetrunPriority(list.get(i)[j][0]));
						content[j][12] = /* RetrunPriority(list.get(i)[j][4]) */RetrunPriority(list.get(i)[j][0]);
						// System.out.println("qaaaaaaaa"+content[j][12]);
						// System.out.println("====");
						// System.out.println(content[j][0]+"--"+content[j][1]+"--"+content[j][2]+"--"+content[j][3]+"--"+content[j][4]+"--"+content[j][5]+"--"+content[j][6]+"--"+content[j][7]+"--"+content[j][8]+"--"+content[j][9]+"--"+content[j][10]+"--"+content[j][11]);
						// System.out.println("====");
						wr.writeRecord(content[j]);
					}
				} else {
					System.out.println("code smell检测到0");
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
						rn = rn + "|重命名";
					}
					if (addfile.get(i) == 1) {
						rn = rn + "|已添加";
					}
					if (deletefile.get(i) == 1) {
						rn = rn + "|已删除";
					}
					content[0][8] = rn;
					content[0][9] = "";// 对应code-smell在file1中数量
					content[0][10] = "";// 对应code-smell在file2中数量
					content[0][11] = "";// 差值
					content[0][12] = "";// priority
					// System.out.println("第"+i+"出现问题");
					// System.out.println("====");
					// System.out.println(content[0][0]+"--"+content[0][1]+"--"+content[0][2]+"--"+content[0][3]+"--"+content[0][4]+"--"+content[0][5]+"--"+content[0][6]+"--"+content[0][7]+"--"+content[0][8]+"--"+content[0][9]+"--"+content[0][10]+"--"+content[0][11]);
					// System.out.println("====");
					wr.writeRecord(content[0]);
				}
			}
			wr.close();

		} catch (Exception e) {
			System.err.println("存取发生错误！");
			e.printStackTrace();
		}
	}

	// 展示所有信息
	public void showAll(int file_number) {
		// System.out.println(version.size());
		// System.out.println(version.get(0));
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

	public static void main(String[] args) {
		// 121 28 25
		// fastjson junit4 commons-io

		int file_number = 28;// 实验总数
		String record_path = "D://junit4not-R.txt";
		String output_file = "D://junit4not-information.csv";
		String p1 = "E://junit4not/";
		String p2 = "/pmd-final-new.txt";

		calculate_codesmells ccs = new calculate_codesmells();
		// 读取前三项
		ccs.record_version_author_number(record_path, file_number);
		System.out.println("前三项读取完毕");

		System.out.println("版本数量：" + ccs.version.size() + "---作者数量：" + ccs.authors.size() + "---修改记录量："
				+ ccs.edit.size() + "---重命名数量：" + ccs.rename.size());

		// 后两项
		for (int fn = 1; fn <= file_number; fn++) {
			// System.out.println("put开始");
			ccs.put_code_smell_to_Set(p1 + fn + p2);
			// System.out.println("put结束");
		}
		// System.out.println(ccs.list.size());
		// System.out.println(ccs.list.get(0)[0][0]+ccs.list.get(0)[0][1]);
		System.out.println("后两项读取完毕");
		// ccs.showAll(file_number);

		// 存入
		// System.out.println(file_number);
		ccs.saveFile(output_file, file_number);
		System.out.println("存入完毕");

		// ccs.showAll(file_number);
	}

}
