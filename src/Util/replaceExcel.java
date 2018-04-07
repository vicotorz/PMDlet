package Util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import com.csvreader.CsvWriter;

public class replaceExcel {
	// 替换excel中的字段

	ArrayList<String[]> list = new ArrayList<String[]>();

	public void ReadFile(String path) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));// 换成你的文件名
			reader.readLine();// 第一行信息，为标题信息，不用,如果需要，注释掉
			String line = null;
			while ((line = reader.readLine()) != null) {
				// System.out.println("读入句子" + line);
				// if (!line.contains("[1]") || !line.contains("[2]")) {
				// return;
				// }
				String item[] = line.split(",");// CSV格式文件为逗号分隔符文件，这里根据逗号切分
				String repaceString = item[2];
				String[] s = repaceString.split("\\\\");
				StringBuffer finalString = new StringBuffer("\"");
				for (int i = 4; i < s.length; i++) {
					finalString.append(s[i]);
					finalString.append("\\");
				}
				item[2] = finalString.toString().substring(0, finalString.length() - 1);
				// System.out.println(item[2]);
				list.add(item);
			}
			System.out.println(list.size());
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void ShowList() {
		Iterator it = list.iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
	}

	public void WriteFile(String path) {
		String csvpath = path.replace("report|report-new", "report-new");
		System.out.println(list.size());
		int step = 0;
		try {
			CsvWriter writer = new CsvWriter(csvpath, ',', Charset.forName("GBK"));
			String[] header = { "Problem", "Package", "File", "Priority", "Line", "Description", "Rule set", "Rule" };
			writer.writeRecord(header);
			String[][] content = new String[list.size()][8];
			Iterator it = list.iterator();
			while (it.hasNext()) {
				String[] item = (String[]) it.next();
				for (int i = 0; i < item.length; i++) {
					content[step][i] = item[i];
				}
				step++;
			}
			for (int i = 0; i < step; i++) {
				writer.writeRecord(content[i]);
			}
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void ReplaceString(String path, String pathnew) {
		System.out.println("[读取路径]" + path);
		System.out.println("[放置路径]" + pathnew);
		System.out.println("替换开始");
		ReadFile(path);
		WriteFile(pathnew);
	}

	public static void main(String[] args) {
		// replaceExcel re = new replaceExcel();
		// re.ReplaceString("H://SAR_systemml/39/[2]54ea986d5ddb7d95dd34a498ca537dfcce0ded71/report.csv");
		String path = "H://SAR_systemml/39/[2]54ea986d5ddb7d95dd34a498ca537dfcce0ded71/report.csv";
		System.out.println(path.replace("report", "report-new"));
	}

}
