package Tool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.charset.Charset;

import com.csvreader.CsvWriter;

import Util.PathUtil;

/**
 * 【输入】(1)xx.txt (2) xx_version.txt (3) xx-R.txt
 * 
 * 【输出】 (1)xx_useful_versions.txt (2)xxx.csv 处理填充csv文件中 前一个revision竖行
 * csv填充所有该填写的信息 (3)xxx_for_bat_versions.txt
 */
public class brevision {
	PathUtil pu = new PathUtil();
	public final String file_path_SAR = pu.R_path_SAR;// "D:\\commons-ionot-R.txt";//
	// 所有截取的项目版本的有用信息 // not这里要看一下
	// public final String file_path_nonSAR = pu.R_path_nonSAR;
	public final String f_path = pu.version_path;// "D:\\commons-io_version.txt";//
													// 所有项目的版本信息
	public final String all_file_path = pu.file_path;// "D:\\commons-io.txt";//
														// 整体项目信息

	public final String useful_versions = pu.useful_path;// "E://commons-ionot/commons-ionot_useful_versions.txt";//
															// 有用版本信息
	public final String file_outpath = pu.csv_path;// "D:\\commons-ionot.csv";//
													// 输出文件
	public final String for_bat_path = pu.for_bat_path;

	public String versions;
	public String[][] contents;
	public int Total_number;// 完成实验的数目

	// 获取文件中版本的总数目
	public int get_total_num() {
		int number = 0;
		try {
			File file = new File(file_path_SAR);
			BufferedReader rd = new BufferedReader(new FileReader(file));
			String line = rd.readLine();
			while (line != null) {
				if (line.startsWith("版本: ")) {
					number++;
				}
				line = rd.readLine();
			}
			rd.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Total_number = number;
		return number;
	}

	// 生成一个版本信息的csv文档
	public void checkfiles() {
		File batfile = new File(for_bat_path);
		try {
			BufferedWriter bat_wr = new BufferedWriter(new FileWriter(batfile));
			System.out.println("checkfiles");// 具有refactor关键词的文件
			File file = new File(file_path_SAR);
			File f2 = new File(f_path);
			BufferedReader reader = new BufferedReader(new FileReader(file));
			BufferedReader r = new BufferedReader(new FileReader(f2));
			r.readLine();

			versions = r.readLine();

			String[] allversions = versions.split("\\|");

			// 生成csv文件
			// 这里可能出现问题
			CsvWriter wr = new CsvWriter(file_outpath, ',', Charset.forName("GBK"));
			contents = new String[get_total_num()][9];
			String[] header = { "序号", "前一个版本", "当前版本", "作者", "日期", "作者标注信息", "修改的信息", "前一个版本修改信息", "具体变化的信息" };// 9个信息

			wr.writeRecord(header);
			System.out.println("表头");

			int version_num = 0;// 【序号】

			// 一旦读入fasthjson的版本信息，就去versions找匹配的前一项，然后一起写入csv中
			String line = reader.readLine();

			String beforeversions;// 【记录前一个版本信息】
			boolean enter_flag = false;

			while (line != null) {
				System.out.println(line);
				if (line.startsWith("版本: ")) {
					System.out.println("找到了版本");
					version_num++;
					String subver = line.substring(line.indexOf(" ") + 1);
					System.out.println(subver);
					// 在versions信息中查找
					beforeversions = find_before_version(subver, allversions);
					System.out.println(beforeversions);
					// 写入【序号】【前一个序号】【当前版本】
					System.out.println(version_num);
					System.out.println(String.valueOf(version_num));
					System.out.println(version_num - 1);

					contents[version_num - 1][0] = String.valueOf(version_num).toString();
					System.out.println("sdadasdwefe");
					contents[version_num - 1][1] = beforeversions;
					contents[version_num - 1][2] = subver;
					// 写入bat文件中
					bat_wr.write(beforeversions);
					bat_wr.newLine();
					bat_wr.write(subver);
					bat_wr.newLine();
					System.out.println("&&&&&&&&&" + contents[0][0]);
					System.out.println("contents第一次初始化完毕");
					System.out.println("！！！！" + version_num + "  " + beforeversions + " " + subver);
					enter_flag = true;
				}
				// 需要装入的信息
				// 【序号】 【前一个版本】 【版本】
				// 【作者】 【日期】 【信息】 【修改的信息】 ！！【变化的信息】
				if (enter_flag) {
					System.out.println("进入了！");
					String author = reader.readLine().substring(line.indexOf(" ") + 1);
					String date = reader.readLine().substring(line.indexOf(" ") + 1);
					reader.readLine();
					String info = reader.readLine();

					reader.readLine();
					reader.readLine();
					reader.readLine();
					// 修改的信息
					StringBuffer reviseinfo = new StringBuffer();
					String ei = reader.readLine();
					System.out.println(ei);
					while (!ei.equals("")) {
						reviseinfo.append(ei);
						reviseinfo.append("\n");
						ei = reader.readLine();
					}
					contents[version_num - 1][3] = author;
					contents[version_num - 1][4] = date;
					contents[version_num - 1][5] = info;
					contents[version_num - 1][6] = reviseinfo.toString();
					contents[version_num - 1][7] = getbefore_comments(contents[version_num - 1][1]);
					contents[version_num - 1][8] = "空";
					enter_flag = false;
				}

				line = reader.readLine();
			}
			// 写入csv中
			System.out.println("检查contents");
			int size = contents.length;
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < 7; j++) {
					System.out.print(contents[i][j] + " ");
				}
				System.out.println();
			}

			for (int i = 0; i < size; i++) {
				wr.writeRecord(contents[i]);
			}
			bat_wr.close();
			wr.close();
			r.close();
			reader.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 查找版本的以前
	public String find_before_version(String ver, String[] allversions) {
		// System.out.println(ver);
		// System.out.println(allversions[1]);
		String string = null;
		for (int i = 0; i < allversions.length; i++) {
			if (allversions[i].equals(ver)) {
				string = allversions[i + 1];
				break;
			}
		}
		return string;
	}

	// 装载上一个版本的comment
	public String getbefore_comments(String version) {
		StringBuffer comment = new StringBuffer();
		System.out.println("查找这个版本" + version);
		File f = new File(all_file_path);
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));

			String line = br.readLine();
			boolean flag = false;

			while (line != null) {
				if (line.equals("版本: " + version)) {
					System.out.println("版本: " + version);
					System.out.println("找到了这个版本");
					flag = true;
				}
				if (line.equals("----") && flag) {
					System.out.println("准备转载comments信息");
					line = br.readLine();
					while (!line.equals("")) {
						comment.append(line);
						comment.append("\n");
						line = br.readLine();

					}
					break;
				}
				line = br.readLine();
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Scanner sc=new Scanner(System.in);
		// sc.nextLine();
		System.out.println("#############");
		System.out.println(comment.toString());
		System.out.println("#############");
		return comment.toString();
	}

	public void writeuseful_versions() {
		System.out.println("记录有用的版本信息");
		File f = new File(useful_versions);

		try {
			BufferedWriter wr = new BufferedWriter(new FileWriter(f));

			for (int index = 0; index < Total_number; index++) {
				// System.out.println(index);
				wr.write(contents[index][1] + "|" + contents[index][2] + "|");
			}
			wr.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		brevision b = new brevision();
		b.checkfiles();
		b.writeuseful_versions();
	}

}
