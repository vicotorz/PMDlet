package Tool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import Util.PathUtil;

/***
 * 【输入】 (1)xxx.txt，(2)xx-R.txt 【输出】(1)xxx-all_authors.txt
 * (2)xxx_calculate_authors.txt 【作用】 记录分析出有自称认重构作者信息
 **/

public class calculateAuthors {
	PathUtil pu = new PathUtil();
	public Set<String> auSet;

	public String[][] name_number;
	public final String Zfile = pu.Zfile;
	public final String file_path = pu.file_path_2;

	public final String Zsave_path = pu.Zsave_path;
	public final String save_path = pu.save_path;

	public calculateAuthors() {
		auSet = new HashSet<String>();
	}

	// 统计所有作者
	public void cal_authors() {
		File f = new File(Zfile);
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			String str;

			str = br.readLine();
			while (str != null) {
				// 如果以‘作者:’开头，记录
				if (str.startsWith("作者:")) {
					String author = str.substring(4, str.length());
					auSet.add(author);

				}
				str = br.readLine();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 对refactor中的作者计数
	public void cal() {
		name_number = new String[auSet.size()][3];// 第一列标记作者 第二列标记refactor
													// 第三列标记所有数目
		// 赋值
		for (int i = 0; i < auSet.size(); i++) {
			name_number[i][1] = String.valueOf("0");
		}
		Iterator itr = auSet.iterator();
		int index = 0;
		while (itr.hasNext()) {
			name_number[index][0] = itr.next().toString();
			name_number[index][1] = String.valueOf("0");
			name_number[index][2] = String.valueOf("0");
			index++;
		}

		// 访问fastjosn-R文件，开始计数
		File file = new File(file_path);
		try {
			BufferedReader bfr = new BufferedReader(new FileReader(file));
			String st = bfr.readLine();
			while (st != null) {
				if (st.startsWith("作者:")) {
					String author = st.substring(4, st.length());
					add_this_author(author);
				}
				st = bfr.readLine();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		File file0 = new File(Zfile);
		try {
			BufferedReader bfr = new BufferedReader(new FileReader(file0));
			String st = bfr.readLine();
			while (st != null) {
				if (st.startsWith("作者:")) {
					String author = st.substring(4, st.length());
					add_Frequency(author);
				}
				st = bfr.readLine();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 对应作者计数
	public void add_this_author(String author) {
		for (int i = 0; i < auSet.size(); i++) {
			if (name_number[i][0].equals(author)) {
				// 加一计数
				name_number[i][1] = String.valueOf(Integer.valueOf(name_number[i][1]) + 1);
			}
		}
	}

	// 对应作者计数
	public void add_Frequency(String author) {
		for (int i = 0; i < auSet.size(); i++) {
			if (name_number[i][0].equals(author)) {
				// 加一计数
				name_number[i][2] = String.valueOf(Integer.valueOf(name_number[i][2]) + 1);
			}
		}
	}

	public void saveFile() {
		File Zfile = new File(Zsave_path);
		File f = new File(save_path);
		try {
			BufferedWriter Zbw = new BufferedWriter(new FileWriter(Zfile));
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			// 写入内容
			Iterator itr = auSet.iterator();
			Zbw.write("====================");
			Zbw.write("\r\n");
			Zbw.write("作者统计");
			Zbw.write("\r\n");
			Zbw.write("====================");
			Zbw.write("\r\n");
			while (itr.hasNext()) {
				Zbw.write(itr.next().toString());
				Zbw.write("\r\n");
			}

			bw.write("====================");
			bw.write("\r\n");
			bw.write("作者          次数     总参与次数");
			bw.write("\r\n");
			bw.write("====================");
			bw.write("\r\n");
			for (int index = 0; index < auSet.size(); index++) {
				bw.write(name_number[index][0] + "                                                 "
						+ name_number[index][1] + "                                                 "
						+ name_number[index][2]);
				bw.write("\r\n");
			}

			bw.close();
			Zbw.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void startCal(){
		cal_authors();
		cal();
		System.out.println(auSet.size());
		System.out.println(name_number.length);
		saveFile();
	}

	public static void main(String[] args) {
		calculateAuthors ca = new calculateAuthors();
		ca.startCal();
	}
}
