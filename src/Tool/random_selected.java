package Tool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import Util.PathUtil;

/**
 * 【输入】(1) xxx_version.txt (2)xxx_useful_versions.txt (3)xx-R.txt 【输出】随机版本
 * 【作用】输出随机选择的编号 【时间】2017.2.22 【程序流程】 随机选取程序 读入fastjson_version.txt
 * 读入fastjson_userful_version.txt
 * 在fastjson_version中挑选版本（查看时候已经选过了，或是refactoring里面的版本） 找到有用的版本以后，还要知道该版本的前一个版本
 */
public class random_selected {
	PathUtil pu = new PathUtil();
	public final String path1 = pu.version_path;// "D:\\junit4_version.txt";
	public final String path2 = pu.useful_path;// "D:\\junit4_useful_versions.txt";
	public final String path3 = pu.R_path_SAR;// "D:\\junit4-R.txt";
	public final String non_SAR_version = pu.for_nonbat_path;// 输出选出的non-SAR版本
	public int min_num;// 最小
	public int max_num;// 最大
	public int random_num;// 需要产生随机数的个数

	public String useful_version[];
	public String all_version[];

	StringBuffer pre_now = new StringBuffer();

	// 1 拼成一个长字符串，然后再拆分
	public void read_all_version(String path) {
		StringBuffer concat_version = new StringBuffer();
		File f = new File(path);
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));

			br.readLine();
			String line = br.readLine();
			while (line != null) {
				concat_version.append(line);
				line = br.readLine();
			}
			br.close();

			// 拆分并装入到数组中
			System.out.println(concat_version.toString());
			all_version = concat_version.toString().split("\\|");
			// System.out.println(all_version[0]);
			// System.out.println(all_version[1]);
			min_num = 1;
			max_num = all_version.length - 1;

		} catch (Exception e) {
			System.out.println("error1");
		}
	}

	// 2 读useful_version
	public void read_useful_version(String path) {
		StringBuffer concat_version = new StringBuffer();
		File f = new File(path);
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			String line = br.readLine();
			while (line != null) {
				concat_version.append(line);
				line = br.readLine();
			}
			br.close();

			// 拆分并装入到数组中
			useful_version = concat_version.toString().split("|");
			random_num = useful_version.length;
		} catch (Exception e) {
			System.out.println("error2");
		}
	}

	// 3 针对没有useful—version的情况
	public void read_version(String path) {
		File f = new File(path);
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			String str;
			ArrayList<String> list = new ArrayList<String>();

			str = br.readLine();
			while (str != null) {

				if (str.startsWith("版本:")) {
					String version = str.substring(4, str.length());
					list.add(version);
				}

				str = br.readLine();
			}
			// System.out.println("111");
			useful_version = list.toArray(new String[list.size()]);
			// System.out.println("222");
			random_num = useful_version.length;
		} catch (Exception e) {
			System.out.println("error3");
		}
	}

	// 产生随机数
	public String random_number() {
		// 在 min_num 和 max_num 之间产生随机数x
		// 产生后去all_version中查找字符串s 去userful_version中查找是否有 || 去记录随机数中找时候有

		ArrayList<String> list = new ArrayList<String>();
		int total_number = 0;
		while (true) {
			Random random = new Random();
			int i = (random.nextInt(max_num) % (max_num - min_num + 1) + min_num) - 1;
			// System.out.println(i);
			if ((!isIn(all_version[i], useful_version)) && (!isInNum(i, list))) {
				list.add(String.valueOf(i));
				System.out.println(all_version[i]);
				pre_now.append(getPreviousVersion(all_version[i]) + "###" + all_version[i] + "###");
				// pre_now.append("\r\n");
				total_number++;
			}
			if (total_number >= random_num) {
				System.out.println("随机数产生完毕");
				break;
			}
		}
		return pre_now.toString();
	}

	public boolean isInNum(int i, ArrayList<String> li) {
		boolean flag = false;
		String istring = String.valueOf(i);
		// String[] liarray=(String[])li.toArray();
		for (i = 0; i < li.size(); i++) {
			if (li.get(i).equals(istring)) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	public boolean isIn(String str, String[] s) {
		boolean flag = false;
		for (int i = 0; i < s.length; i++) {
			if (s[i].equals(str)) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	// 在all_version中查找给定字符串的前一个字符串
	public String getPreviousVersion(String version) {
		int location = -1;
		for (int i = 0; i < all_version.length; i++) {
			if (all_version[i].equals(version)) {
				location = i;
				break;
			}
		}
		return all_version[location + 1];
	}

	// 对前后版本号进行输出
	public void before_now_version() {
		try {
			File file = new File(non_SAR_version);
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			String[] tempStr = pre_now.toString().split("###");
			System.out.println("chakan" + tempStr.length);
			for (int i = 0; i < tempStr.length; i++) {
				bw.write(tempStr[i]);
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void startRandom() {
		read_all_version(path1);
		read_useful_version(path2);
		read_version(path3);
		random_number();
		before_now_version();
	}

	public static void main(String[] args) {
		random_selected rs = new random_selected();
		rs.startRandom();
	}
}
