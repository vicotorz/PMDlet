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
 * �����롿(1)xx.txt (2) xx_version.txt (3) xx-R.txt
 * 
 * ������� (1)xx_useful_versions.txt (2)xxx.csv �������csv�ļ��� ǰһ��revision����
 * csv������и���д����Ϣ (3)xxx_for_bat_versions.txt
 */
public class brevision {
	PathUtil pu = new PathUtil();
	public final String file_path_SAR = pu.R_path_SAR;// "D:\\commons-ionot-R.txt";//
	// ���н�ȡ����Ŀ�汾��������Ϣ // not����Ҫ��һ��
	// public final String file_path_nonSAR = pu.R_path_nonSAR;
	public final String f_path = pu.version_path;// "D:\\commons-io_version.txt";//
													// ������Ŀ�İ汾��Ϣ
	public final String all_file_path = pu.file_path;// "D:\\commons-io.txt";//
														// ������Ŀ��Ϣ

	public final String useful_versions = pu.useful_path;// "E://commons-ionot/commons-ionot_useful_versions.txt";//
															// ���ð汾��Ϣ
	public final String file_outpath = pu.csv_path;// "D:\\commons-ionot.csv";//
													// ����ļ�
	public final String for_bat_path = pu.for_bat_path;

	public String versions;
	public String[][] contents;
	public int Total_number;// ���ʵ�����Ŀ

	// ��ȡ�ļ��а汾������Ŀ
	public int get_total_num() {
		int number = 0;
		try {
			File file = new File(file_path_SAR);
			BufferedReader rd = new BufferedReader(new FileReader(file));
			String line = rd.readLine();
			while (line != null) {
				if (line.startsWith("�汾: ")) {
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

	// ����һ���汾��Ϣ��csv�ĵ�
	public void checkfiles() {
		File batfile = new File(for_bat_path);
		try {
			BufferedWriter bat_wr = new BufferedWriter(new FileWriter(batfile));
			System.out.println("checkfiles");// ����refactor�ؼ��ʵ��ļ�
			File file = new File(file_path_SAR);
			File f2 = new File(f_path);
			BufferedReader reader = new BufferedReader(new FileReader(file));
			BufferedReader r = new BufferedReader(new FileReader(f2));
			r.readLine();

			versions = r.readLine();

			String[] allversions = versions.split("\\|");

			// ����csv�ļ�
			// ������ܳ�������
			CsvWriter wr = new CsvWriter(file_outpath, ',', Charset.forName("GBK"));
			contents = new String[get_total_num()][9];
			String[] header = { "���", "ǰһ���汾", "��ǰ�汾", "����", "����", "���߱�ע��Ϣ", "�޸ĵ���Ϣ", "ǰһ���汾�޸���Ϣ", "����仯����Ϣ" };// 9����Ϣ

			wr.writeRecord(header);
			System.out.println("��ͷ");

			int version_num = 0;// ����š�

			// һ������fasthjson�İ汾��Ϣ����ȥversions��ƥ���ǰһ�Ȼ��һ��д��csv��
			String line = reader.readLine();

			String beforeversions;// ����¼ǰһ���汾��Ϣ��
			boolean enter_flag = false;

			while (line != null) {
				System.out.println(line);
				if (line.startsWith("�汾: ")) {
					System.out.println("�ҵ��˰汾");
					version_num++;
					String subver = line.substring(line.indexOf(" ") + 1);
					System.out.println(subver);
					// ��versions��Ϣ�в���
					beforeversions = find_before_version(subver, allversions);
					System.out.println(beforeversions);
					// д�롾��š���ǰһ����š�����ǰ�汾��
					System.out.println(version_num);
					System.out.println(String.valueOf(version_num));
					System.out.println(version_num - 1);

					contents[version_num - 1][0] = String.valueOf(version_num).toString();
					System.out.println("sdadasdwefe");
					contents[version_num - 1][1] = beforeversions;
					contents[version_num - 1][2] = subver;
					// д��bat�ļ���
					bat_wr.write(beforeversions);
					bat_wr.newLine();
					bat_wr.write(subver);
					bat_wr.newLine();
					System.out.println("&&&&&&&&&" + contents[0][0]);
					System.out.println("contents��һ�γ�ʼ�����");
					System.out.println("��������" + version_num + "  " + beforeversions + " " + subver);
					enter_flag = true;
				}
				// ��Ҫװ�����Ϣ
				// ����š� ��ǰһ���汾�� ���汾��
				// �����ߡ� �����ڡ� ����Ϣ�� ���޸ĵ���Ϣ�� �������仯����Ϣ��
				if (enter_flag) {
					System.out.println("�����ˣ�");
					String author = reader.readLine().substring(line.indexOf(" ") + 1);
					String date = reader.readLine().substring(line.indexOf(" ") + 1);
					reader.readLine();
					String info = reader.readLine();

					reader.readLine();
					reader.readLine();
					reader.readLine();
					// �޸ĵ���Ϣ
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
					contents[version_num - 1][8] = "��";
					enter_flag = false;
				}

				line = reader.readLine();
			}
			// д��csv��
			System.out.println("���contents");
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

	// ���Ұ汾����ǰ
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

	// װ����һ���汾��comment
	public String getbefore_comments(String version) {
		StringBuffer comment = new StringBuffer();
		System.out.println("��������汾" + version);
		File f = new File(all_file_path);
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));

			String line = br.readLine();
			boolean flag = false;

			while (line != null) {
				if (line.equals("�汾: " + version)) {
					System.out.println("�汾: " + version);
					System.out.println("�ҵ�������汾");
					flag = true;
				}
				if (line.equals("----") && flag) {
					System.out.println("׼��ת��comments��Ϣ");
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
		System.out.println("��¼���õİ汾��Ϣ");
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
