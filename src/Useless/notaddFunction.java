package Useless;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * 【输入】(1) xxx.txt (2) xxx not_useful_version.txt
 * 【输出】xxx not-R.txt
 * 【时间】2017-3-3 
 * 【作用】添加一个函数 针对not的情况 输出类似于 fastjson-R这样的文件
 * 【程序思路】
 * （1）读入random的数组 E://fastjsonnot/fastjsonnot_useful_version.txt 拆除放入到数组
 * （2）读入所有version的文档 每读入到版本进行判定，如果是 一直写入直到下一个版本开始
 * 
 */
public class notaddFunction {

	public final String path = "E://junit4not/junit4not_useful_version.txt";
	public final String allpath = "D://junit4.txt";

	public final String R_outputpath = "D://junit4not-R.txt";
	StringBuffer SB = new StringBuffer();// 用于拼接所有useful_version

	String[] uversion;// 拆分之后放入这个数组

	// 读入path，装备数组
	public void prepare() {

		try {
			File f = new File(path);
			BufferedReader br = new BufferedReader(new FileReader(f));
			String line = br.readLine();
			while (line != null) {
				// System.out.println(line);
				SB.append(line);
				line = br.readLine();
			}
			System.out.println(SB.toString());
			uversion = SB.toString().split("\\|");
			br.close();
		} catch (Exception e) {
			System.err.println("第一步骤出错");
		}
	}

	public ArrayList<Integer> isIn(String version) {
		int flag = 0;
		int loc = -1;
		for (int i = 0; i < uversion.length; i++) {
			if (version.equals(uversion[i])) {
				flag = 1;
				loc = i;
				break;
			}
		}
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(flag);
		list.add(loc);
		return list;
	}

	// 写入到R文件中
	public void writeFiles() {
		try {
			File f1 = new File(allpath);
			BufferedReader br = new BufferedReader(new FileReader(f1));

			File f2 = new File(R_outputpath);
			BufferedWriter bw = new BufferedWriter(new FileWriter(f2));

			String[] Array = new String[uversion.length];
			// 初始化
			// for(int i=0;i<uversion.length;i++){
			// Array[i]=null;
			// }

			String line = br.readLine();
			boolean beginRecord = false;
			int loc = -1;
			int index = 0;
			StringBuffer ssb = new StringBuffer();
			while (line != null) {

				// System.out.println("【访问】"+line);
				if (line.startsWith("版本:")) {

					if (index != 0 && beginRecord) {

						Array[loc] = ssb.toString();
						ssb = new StringBuffer();
						System.out.println("写入检查");
						System.out.println("content:" + ssb.toString() + "  added:" + Array[loc]);
					}
					loc = -1;
					beginRecord = false;
					String version = line.substring(4, line.length());
					if (isIn(version).get(0) == 1) {

						// System.out.println("这一次查找这个版本号： "+version);
						beginRecord = true;
						loc = isIn(version).get(1);
						System.out.println(loc);
						// System.out.println("找到！ 查找："+version+" 位置："+loc+"
						// 对比："+uversion[loc]);
					}
				}

				if (beginRecord) {

					ssb.append(line);
					ssb.append("\r\n");
					// System.out.println(loc);

					// System.out.println(ssb.toString());
					// bw.write(line);
					// bw.write("\r\n");
				}
				index++;
				line = br.readLine();
			}
			// 统一记录
			for (int i = 0; i < uversion.length; i++) {
				System.out.println("【写入内容】" + Array[i].toString());
				bw.write(Array[i].toString());
			}
			bw.close();
			br.close();
		} catch (Exception e) {
			System.err.println("第二步骤出错");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		notaddFunction nf = new notaddFunction();
		nf.prepare();
		nf.writeFiles();

	}

}
