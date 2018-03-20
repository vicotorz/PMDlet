package Tool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

import Util.CopyFileUtil;
import Util.PathUtil;

//��ʱ�䡿2018.3.16
public class Java_Bat {

	PathUtil pu = new PathUtil();
	public final String root_path = pu.Path_Root;
	public final String http_path = pu.http_path;
	public final String path = pu.for_bat_path;// SAR
	public final String SAR_StorePath_root = pu.SAR_StorePath_Root;
	public final String non_SARPath_Root = pu.non_SARPath_Root;
	public final String Path_Root = pu.Path_Root;
	public String[] version_number;
	public int Number;

	public final String gitbat = "E://Files/download_rename_check.bat";

	// ����汾��Ϣ
	public void readVersions() {
		System.out.println("��ȡ�汾��Ϣ");
		File file = new File(path);
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = br.readLine();
			StringBuffer sb = new StringBuffer();
			while (line != null) {
				sb.append(line + "--");
				line = br.readLine();
			}
			version_number = sb.toString().split("--");
			Number = version_number.length;
			pu.refac_Number = Number;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("��ȡ���");
	}

	// �����ļ���
	public static boolean mkDirectory(String path) {
		File file = null;
		try {
			file = new File(path);
			if (!file.exists()) {
				return file.mkdirs();
			} else {
				return false;
			}
		} catch (Exception e) {
		} finally {
			file = null;
		}
		return false;
	}

	// ���ݰ汾��Ϣ������git����
	public void GitDownLoad(String root) {
		File file = new File(gitbat);
		System.out.println("��ʼ�������ػ���");
		try {
			BufferedWriter br = new BufferedWriter(new FileWriter(file));
			// Runtime runtime = Runtime.getRuntime();
			br.write(Path_Root);// E:
			br.newLine();
			for (int i = 0; i < Number; i=i+2) {
				String cdroot = Path_Root;
				int ver = 0;
				for (int j = 1; j <= 2; j++) {
					ver = (j == 1 ? i : i + 1);
					String changedic = "cd " + root + String.valueOf(j);// �л���E://GitTest/j
					String download = "git clone " + http_path + " " + version_number[ver];// ���ش���
					String rename = "ren " + version_number[ver] + " [" + String.valueOf(j) + "]" + version_number[ver];// ����
					String check_path = root + "[" + String.valueOf(j) + "]" + version_number[ver];
					// �ƶ�׼��
					String check = "pmd.bat -d " + check_path + " -R java-basic -f csv -r " + check_path
							+ "/report.csv";
					String command = changedic + " && " + download + " && " + rename + " && " + check;
					System.out.println(command);
					br.write(command);
					br.newLine();
				}
				String returncom = "cd ..";// ���˵� E://GitTest/i/
				br.write(returncom);
				br.newLine();
			}
			br.close();
			// ִ��bat�ļ�
			System.out.println("��ʼִ��bat�ļ�");
			String bat_start = "cmd /c start " + gitbat;
			Process pr = Runtime.getRuntime().exec(bat_start);

			InputStream in = pr.getInputStream();
			BufferedReader BR = new BufferedReader(new InputStreamReader(in));
			String tmp = null;
			while ((tmp = BR.readLine()) != null) {
				// pr.waitFor();
				System.out.println("��ȴ�");
			}
			System.out.println("�ű�ִ����ϣ���ʼ�ƶ��ļ�");
			copyUtil(root);
			System.out.println("�ƶ����");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ��xx://yy/[1-9]/[1|2]�е�report.csv ���Ƶ� xx://yy/[1-9] �в������� pmd-report-1 /
	// pmd-report-2
	public void copyUtil(String root) {
		CopyFileUtil cfu = new CopyFileUtil();
		for (int i = 0; i < Number; i++) {
			String rootpath = root + String.valueOf(i + 1);// ��һ��Ŀ¼
			for (int j = 1; j <= 2; j++) {
				String path = root + String.valueOf(i + 1) + "/" + "[" + String.valueOf(j) + "]" + version_number[i];// ��һ��Ŀ¼
				String srcFileName = path + "/report.csv";
				String destFileName;
				if (path.toString().contains("[1]")) {
					destFileName = rootpath + "/pmd-report-1.csv";
				} else {
					destFileName = rootpath + "/pmd-report-2.csv";
				}
				// �����Ƶ��ļ�����Ŀ���ļ��������Ŀ���ļ����ڣ��Ƿ񸲸�
				cfu.copyFile(srcFileName, destFileName, true);
			}
		}
	}

	// ��������
	public void StartBat(String root) {
		System.out.println("��ʼ����Ŀ¼");
		String mkDirectoryPath;
		for (int i = 1; i <= Number; i++) {
			// for (int j = 1; j <= 2; j++) {+ "/" + String.valueOf(j)
			// ����Ŀ¼
			mkDirectoryPath = root + String.valueOf(i);
			if (mkDirectory(mkDirectoryPath)) {
				System.out.println(mkDirectoryPath + "�������");
			} else {
				System.out.println(mkDirectoryPath + "����ʧ�ܣ���Ŀ¼�����Ѿ����ڣ�");
			}
			// }
		}
		// ����Ŀ����룬�������ƶ�
		GitDownLoad(root);
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Java_Bat jb = new Java_Bat();
		// ��ȡ�汾����Ŀ
		jb.readVersions();
		sc.nextLine();
		// SAR
		System.out.println("SAR����");
		jb.StartBat(jb.SAR_StorePath_root);
		sc.nextLine();
		// non-SAR
		// System.out.println("nonSAR����");
		// jb.StartBat(jb.non_SARPath_Root);
		// sc.nextLine();

	}
}
