package Tool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import Util.PathUtil;

/***
 * �����롿 xxx.txt �������xx_version.txt �����á� ���ļ������е�revision��Ϣȫ��ȡ����,�γ�
 * (�汾1|�汾2|...|�汾n )���ַ�����Ϣ
 **/

public class All_revisions {
	PathUtil pu = new PathUtil();

	public StringBuffer revisions = new StringBuffer();
	final String file_path = pu.file_path;
	final String file_output_path = pu.version_path;

	int totalNumber = 0;

	BufferedReader reader;

	BufferedWriter writer;

	// �����Ӵ�
	public String substr(String str) {
		return str.substring(str.indexOf(" ") + 1);
	}

	// ��ȡ�汾��
	public void fetch_revisions() {

		try {
			// �ļ��Ĳ���
			File file = new File(file_path);
			reader = new BufferedReader(new FileReader(file));

			String str = reader.readLine();
			while (str != null) {

				if (str.startsWith("�汾:")) {
					revisions.append(substr(str));
					revisions.append("|");
					totalNumber++;
				}

				str = reader.readLine();

			}

			File f = new File(file_output_path);
			writer = new BufferedWriter(new FileWriter(f));

			writer.write(String.valueOf(totalNumber));
			writer.write("\r\n");
			writer.write(revisions.toString());

			writer.close();
			reader.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		All_revisions ar = new All_revisions();
		ar.fetch_revisions();
	}

}
