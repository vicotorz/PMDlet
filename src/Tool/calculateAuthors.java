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
 * �����롿 (1)xxx.txt��(2)xx-R.txt �������(1)xxx-all_authors.txt
 * (2)xxx_calculate_authors.txt �����á� ��¼���������Գ����ع�������Ϣ
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

	// ͳ����������
	public void cal_authors() {
		File f = new File(Zfile);
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			String str;

			str = br.readLine();
			while (str != null) {
				// ����ԡ�����:����ͷ����¼
				if (str.startsWith("����:")) {
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

	// ��refactor�е����߼���
	public void cal() {
		name_number = new String[auSet.size()][3];// ��һ�б������ �ڶ��б��refactor
													// �����б��������Ŀ
		// ��ֵ
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

		// ����fastjosn-R�ļ�����ʼ����
		File file = new File(file_path);
		try {
			BufferedReader bfr = new BufferedReader(new FileReader(file));
			String st = bfr.readLine();
			while (st != null) {
				if (st.startsWith("����:")) {
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
				if (st.startsWith("����:")) {
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

	// ��Ӧ���߼���
	public void add_this_author(String author) {
		for (int i = 0; i < auSet.size(); i++) {
			if (name_number[i][0].equals(author)) {
				// ��һ����
				name_number[i][1] = String.valueOf(Integer.valueOf(name_number[i][1]) + 1);
			}
		}
	}

	// ��Ӧ���߼���
	public void add_Frequency(String author) {
		for (int i = 0; i < auSet.size(); i++) {
			if (name_number[i][0].equals(author)) {
				// ��һ����
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
			// д������
			Iterator itr = auSet.iterator();
			Zbw.write("====================");
			Zbw.write("\r\n");
			Zbw.write("����ͳ��");
			Zbw.write("\r\n");
			Zbw.write("====================");
			Zbw.write("\r\n");
			while (itr.hasNext()) {
				Zbw.write(itr.next().toString());
				Zbw.write("\r\n");
			}

			bw.write("====================");
			bw.write("\r\n");
			bw.write("����          ����     �ܲ������");
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
