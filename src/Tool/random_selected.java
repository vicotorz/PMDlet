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
 * �����롿(1) xxx_version.txt (2)xxx_useful_versions.txt (3)xx-R.txt �����������汾
 * �����á�������ѡ��ı�� ��ʱ�䡿2017.2.22 ���������̡� ���ѡȡ���� ����fastjson_version.txt
 * ����fastjson_userful_version.txt
 * ��fastjson_version����ѡ�汾���鿴ʱ���Ѿ�ѡ���ˣ�����refactoring����İ汾�� �ҵ����õİ汾�Ժ󣬻�Ҫ֪���ð汾��ǰһ���汾
 */
public class random_selected {
	PathUtil pu = new PathUtil();
	public final String path1 = pu.version_path;// "D:\\junit4_version.txt";
	public final String path2 = pu.useful_path;// "D:\\junit4_useful_versions.txt";
	public final String path3 = pu.R_path_SAR;// "D:\\junit4-R.txt";
	public final String non_SAR_version = pu.for_nonbat_path;// ���ѡ����non-SAR�汾
	public int min_num;// ��С
	public int max_num;// ���
	public int random_num;// ��Ҫ����������ĸ���

	public String useful_version[];
	public String all_version[];

	StringBuffer pre_now = new StringBuffer();

	// 1 ƴ��һ�����ַ�����Ȼ���ٲ��
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

			// ��ֲ�װ�뵽������
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

	// 2 ��useful_version
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

			// ��ֲ�װ�뵽������
			useful_version = concat_version.toString().split("|");
			random_num = useful_version.length;
		} catch (Exception e) {
			System.out.println("error2");
		}
	}

	// 3 ���û��useful��version�����
	public void read_version(String path) {
		File f = new File(path);
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			String str;
			ArrayList<String> list = new ArrayList<String>();

			str = br.readLine();
			while (str != null) {

				if (str.startsWith("�汾:")) {
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

	// ���������
	public String random_number() {
		// �� min_num �� max_num ֮����������x
		// ������ȥall_version�в����ַ���s ȥuserful_version�в����Ƿ��� || ȥ��¼���������ʱ����

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
				System.out.println("������������");
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

	// ��all_version�в��Ҹ����ַ�����ǰһ���ַ���
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

	// ��ǰ��汾�Ž������
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
