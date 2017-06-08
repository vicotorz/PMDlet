package com.flow.src;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 2016.6.12
 * 
 * 输入文本数据
 * 
 * 查找里面有 信息： %refactor% 【向上】 版本 【向下】 到下一个版本 之间的信息摘取出来
 * 
 * 摘取的信息统一输出同一个文本中
 * 
 * 更换思路 设置两个BufferedReader
 * 
 * 计数
 * 
 * b1 向前走n步--探索具有refactor信息的内容 ArrayList记录访问的数据 记录本次向前走的步N A）如果探索到【信息】
 * 并检测到refactor信息，则记录下来，并继续记录到探索到信息为止 B） 否则ArrayList将后N记录清空 当b1探测到【版本字样的时候开始记录】
 * 
 * ！！这个程序存在问题
 * 
 * 
 * 1.有漏判的情况 develoers在信息下可能有多条信息 2.缺少版本号信息 junit4 incubator-systemml commons-io
 * druid
 * 
 * fastjson junit4 commons-io 2017.1.4加入频率矩阵 0为普通文件 1为自称认文件
 */
public class pick_up_refactors {

	// 文件路径
	String file_path = "D:\\commons-io.txt";
	String file_output_path = "E:\\commons-io-R.txt";

	String fileMark = "E:\\commons-io_mark.txt";

	public int step_forward;
	ArrayList<String> routeString = new ArrayList<String>();

	// BufferedReader
	BufferedReader reader;

	ArrayList<String> marklist = new ArrayList<String>();// 用来记录自重构标签

	pick_up_refactors() {
	}

	// 1.找具有refactor的信息
	public void search_step() {
		// 创建文件
		File file = new File(file_path);
		boolean flag = false;
		// 创建文件管道
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			step_forward = 1;// readLine一行--------就记录一个前进步骤

			while (line != null) {
				// System.out.println("【读入--"+line);
				// 记录开头的位置
				if (line.startsWith("版本:")) {
					// System.out.println("！！！找到[版本]！！！");
					// reader.mark(0);
					step_forward = 1;
					flag = true;
				}

				if (flag) {
					// System.out.println("写入】--"+line);
					routeString.add(line);
				}

				// 如果line是以信息开头
				// 并且下一行包含 %refactor%信息
				if (line.startsWith("信息:")) {

					line = reader.readLine();
					step_forward++;
					// System.out.println("写入--"+line);
					routeString.add(line);

					if (line.contains("refactor") || line.contains("Refactor")) {
						// 找对了
						System.out.println(line);
						// System.out.println("找到REFACTOR信息！！！！");
						// Scanner inn=new Scanner(System.in);
						// showlist();
						// inn.nextLine();
						// System.out.println(flag);
						// routeString.add(line);
						// record_start_end();
						// 继续记录下去
						marklist.add("1");
					} else {
						marklist.add("0");
						deletelist(step_forward);
						flag = false;
					}
					// ////////////////////////////////////////////////////////
				}
				// Scanner sc=new Scanner(System.in);
				// sc.nextLine();
				line = reader.readLine();

				if (flag) {
					step_forward++;
				}
			}

		} catch (Exception e) {
			System.out.println("异常");
			e.printStackTrace();
		}
	}

	public void showlist() {
		System.out.println("+++++++++++++++++++++++++++++++++++++");
		for (int i = 0; i <= routeString.size() - 1; i++) {
			System.out.println(routeString.get(i));
		}
		System.out.println("*************************************");
	}

	// 1.5.删除ArrayList中的前step信息
	public void deletelist(int step) {
		// System.out.println("删前查看");
		// showlist();
		// System.out.println(routeString.size() + " STEP " + step);
		for (int i = 0; i <= step - 1; i++) {
			routeString.remove(routeString.size() - 1);
		}
	}

	// 2.ArrayList输出文件
	public void record_start_end() {
		System.out.println("找到并记录输出");
		System.out.println("调用reset方法");

		try {
			// Scanner inn=new Scanner(System.in);
			// inn.nextLine();
			File outputfile = new File(file_output_path);
			BufferedWriter writer = new BufferedWriter(new FileWriter(outputfile, true));
			for (int i = 0; i <= routeString.size() - 1; i++) {
				// Scanner in=new Scanner(System.in);
				// in.nextLine();

				writer.write(routeString.get(i));
				// System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
				// System.out.println("****输出内容：" + routeString.get(i));
				writer.write("\r\n");
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 输出
	public void recordSelfMark() {
		try {
			File outputfile = new File(fileMark);
			BufferedWriter writer = new BufferedWriter(new FileWriter(outputfile, true));
			System.out.println("!!" + marklist.size());
			for (int i = 0; i <= marklist.size() - 1; i++) {
				System.out.println(marklist.get(i));
				writer.write(marklist.get(i));
				writer.write("\r\n");
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 主函数
	public static void main(String[] args) {
		pick_up_refactors r = new pick_up_refactors();
		r.search_step();
		r.record_start_end();
		r.recordSelfMark();
	}

}
