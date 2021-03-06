package Tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.csvreader.CsvWriter;

import Util.PathUtil;

/**
 * 【输入】All.txt 【输出】pmd_info.csv(格式： "名称", "描述", "优先级") 【时间】2016.12.7
 * 【作用】梳理pmd检测到的所有信息名称
 * 
 */
public class SimplifyInfo {
	PathUtil pu = new PathUtil();
	public final String path = pu.All_path;// "E:\\All.txt";
	public final String pmd_info_path = pu.pmd_info_path;// "D:\\pmd_info.csv";
	public HashMap<String, ArrayList<String>> map;

	// 读入文件，然后prioirty和对应的值将值放入到Map中
	public void ReadFile() {
		map = new HashMap<String, ArrayList<String>>();

		File f = new File(path);
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(f));
			String string;
			string = br.readLine();
			String rulesetname = "";
			String priority;

			int step = 0;
			ArrayList<String> list = new ArrayList<String>();
			Pattern pattern = Pattern.compile("<description>[a-zA-Z]+");

			boolean flag = false;
			StringBuffer description = new StringBuffer();
			boolean properties_entry = false;

			while (string != null) {
				// 说明是ruleset name
				if (string.contains("<ruleset name=")) {
					if (step != 0) {
						// 说明进来了一个新的
						map.put(rulesetname, list);
						list = new ArrayList<String>();
						description = new StringBuffer();
						System.out.println("装进去");
						System.out.println(map.keySet());
						// System.out.println(map.values());

					}
					// System.out.println("ruleset name");
					String[] s = string.split("\"");
					rulesetname = s[1].substring(0, s[1].length());
					step++;
				}

				// 探测到开始进入properties
				if (string.contains("<properties>")) {
					properties_entry = true;
				}
				// 探测到离开properties
				if (string.contains("</properties>")) {
					properties_entry = false;
				}

				// 说明是普通的name
				if (string.contains("name=") && !string.contains("<ruleset name=")
						&& !string.contains("<property name=") && !properties_entry) {
					String[] s = string.split("\"");
					String rule_name = s[1].substring(0, s[1].length());
					list.add(rule_name);
				}

				// 说明是priority
				if (string.contains("<priority>")) {
					String[] str = string.split(">");
					String[] strr = str[1].split("<");
					priority = strr[0];
					list.add(priority);
				}

				// 1)<d>xxx</d>
				//
				// 2)<d>xxx
				// </d>
				//
				// 3)<d>
				// xxx
				// </d>

				// description第一种情况
				if (string.contains("<description>") && string.contains("</description>")) {
					// 第一种情况
					String[] str = string.split(">");
					String[] strr = str[1].split("<");
					list.add(strr[0]);

				} else if (pattern.matcher(string).matches()) {
					// 第二种情况
					String[] str = string.split(">");
					list.add(str[1]);

				} else if (string.contains("<description>")) {
					// 第三种情况
					flag = true;
				}

				// 第三种情况
				if (flag) {
					description.append(string);
				}
				// 第三种情况结束
				if (flag && string.contains("</description>")) {
					flag = false;
					list.add(description.toString());
					description = new StringBuffer();

				}

				string = br.readLine();
			}

		} catch (Exception e) {
			System.out.println("访问：" + path);
			e.printStackTrace();
		}
	}

	public void showAll() {
		// System.out.println(map.values());
		System.out.println(map.keySet());
	}

	// 写入文件中
	// 写入格式 【rulesetName】
	// name
	public void WriteFile() {

		CsvWriter wr = new CsvWriter(pmd_info_path, ',', Charset.forName("GBK"));
		String[] header = { "名称", "描述", "优先级" };
		try {
			wr.writeRecord(header);
			String[][] content = new String[286][3];

			Iterator iter = map.entrySet().iterator();
			int index = 0;
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				String key = (String) entry.getKey();
				ArrayList<String> val = (ArrayList<String>) entry.getValue();
				// System.out.println(key);
				System.out.println(val.size());

				content[index][0] = "【" + key + "】";
				content[index][1] = val.get(0).toString();
				content[index][2] = "";
				System.out.println(key + "--" + val.get(0));
				wr.writeRecord(content[index]);
				index++;

				if (val.size() >= 1) {
					List sublist = val.subList(1, val.size());
					System.out.println(sublist.size());
					for (int i = 0; i < sublist.size(); i = i + 3) {
						content[index][0] = sublist.get(i).toString();
						content[index][1] = sublist.get(i + 1).toString();
						content[index][2] = sublist.get(i + 2).toString();
						wr.writeRecord(content[index]);
						index++;

					}
				}
			}
			System.out.println(index);
			wr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void startSimplifyInfo() {
		System.out.println("1");
		ReadFile();
		System.out.println("2");
		showAll();
		WriteFile();
	}

	// list中第一个是ruleset的描述 其他依次顺序为 名称 描述 优先级
	public static void main(String[] args) {
		SimplifyInfo sfi = new SimplifyInfo();
		sfi.startSimplifyInfo();

	}

}
