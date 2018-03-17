package Useless;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * �����롿(1) xxx.txt (2) xxx not_useful_version.txt
 * �������xxx not-R.txt
 * ��ʱ�䡿2017-3-3 
 * �����á����һ������ ���not����� ��������� fastjson-R�������ļ�
 * ������˼·��
 * ��1������random������ E://fastjsonnot/fastjsonnot_useful_version.txt ������뵽����
 * ��2����������version���ĵ� ÿ���뵽�汾�����ж�������� һֱд��ֱ����һ���汾��ʼ
 * 
 */
public class notaddFunction {

	public final String path = "E://junit4not/junit4not_useful_version.txt";
	public final String allpath = "D://junit4.txt";

	public final String R_outputpath = "D://junit4not-R.txt";
	StringBuffer SB = new StringBuffer();// ����ƴ������useful_version

	String[] uversion;// ���֮������������

	// ����path��װ������
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
			System.err.println("��һ�������");
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

	// д�뵽R�ļ���
	public void writeFiles() {
		try {
			File f1 = new File(allpath);
			BufferedReader br = new BufferedReader(new FileReader(f1));

			File f2 = new File(R_outputpath);
			BufferedWriter bw = new BufferedWriter(new FileWriter(f2));

			String[] Array = new String[uversion.length];
			// ��ʼ��
			// for(int i=0;i<uversion.length;i++){
			// Array[i]=null;
			// }

			String line = br.readLine();
			boolean beginRecord = false;
			int loc = -1;
			int index = 0;
			StringBuffer ssb = new StringBuffer();
			while (line != null) {

				// System.out.println("�����ʡ�"+line);
				if (line.startsWith("�汾:")) {

					if (index != 0 && beginRecord) {

						Array[loc] = ssb.toString();
						ssb = new StringBuffer();
						System.out.println("д����");
						System.out.println("content:" + ssb.toString() + "  added:" + Array[loc]);
					}
					loc = -1;
					beginRecord = false;
					String version = line.substring(4, line.length());
					if (isIn(version).get(0) == 1) {

						// System.out.println("��һ�β�������汾�ţ� "+version);
						beginRecord = true;
						loc = isIn(version).get(1);
						System.out.println(loc);
						// System.out.println("�ҵ��� ���ң�"+version+" λ�ã�"+loc+"
						// �Աȣ�"+uversion[loc]);
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
			// ͳһ��¼
			for (int i = 0; i < uversion.length; i++) {
				System.out.println("��д�����ݡ�" + Array[i].toString());
				bw.write(Array[i].toString());
			}
			bw.close();
			br.close();
		} catch (Exception e) {
			System.err.println("�ڶ��������");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		notaddFunction nf = new notaddFunction();
		nf.prepare();
		nf.writeFiles();

	}

}
