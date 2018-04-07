package Util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import com.csvreader.CsvWriter;

public class replaceExcel {
	// �滻excel�е��ֶ�

	ArrayList<String[]> list = new ArrayList<String[]>();

	public void ReadFile(String path) {
		// System.out.println(path);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));// ��������ļ���
			reader.readLine();// ��һ����Ϣ��Ϊ������Ϣ������,�����Ҫ��ע�͵�
			String line = null;
			while ((line = reader.readLine()) != null) {
				// System.out.println("�������" + line);
				// if (!line.contains("[1]") || !line.contains("[2]")) {
				// return;
				// }
				String item[] = line.split(",");// CSV��ʽ�ļ�Ϊ���ŷָ����ļ���������ݶ����з�
				String repaceString = item[2];
				// int value = Integer.parseInt(last);//�������ֵ������ת��Ϊ��ֵ
				String[] s = repaceString.split("\\\\");
				// System.out.println(repaceString);
				// System.out.println(s[0]);
				StringBuffer finalString = new StringBuffer("\"");
				for (int i = 4; i < s.length; i++) {
					// System.out.println(s[i]);
					finalString.append(s[i]);
					finalString.append("\\");
				}
				// System.out.println(finalString);
				item[2] = finalString.toString().substring(0, finalString.length() - 1);
				// System.out.println(item[2]);
				list.add(item);
				// Scanner sc = new Scanner(System.in);
				// sc.nextLine();
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
					// System.out.println(item[i].length());
					// String temp;
					// if (item[i].length() >= 3) {
					// temp = item[i].substring(1, item[i].length() - 2);
					// } else {
					// temp = item[i];
					// }
					content[step][i] = item[i];
				}
				step++;
				// System.out.println("д�����");
				// for (int i = 0; i < content.length; i++) {
				// System.out.println(content[i]);
				// }
				// Scanner sc = new Scanner(System.in);
				// sc.nextLine();
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
		System.out.println("[��ȡ·��]" + path);
		System.out.println("[����·��]" + pathnew);
		System.out.println("�滻��ʼ");
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
