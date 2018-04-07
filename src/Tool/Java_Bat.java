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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import Util.PathUtil;
import Util.mkDirectory;

//��ʱ�䡿2018.3.16
public class Java_Bat {

	PathUtil pu = new PathUtil();
	public final String root_path = pu.Path_Root;
	public final String http_path = pu.http_path;
	public final String path_SAR = pu.for_bat_path;// SAR
	public final String path_nonSAR = pu.for_nonbat_path;
	public final String SAR_StorePath_root = pu.SAR_StorePath_Root;
	public final String nonSAR_StorePath_Root = pu.nonSAR_StorePath_Root;
	public final String Path_Root = pu.Path_Root;
	public String[] version_number;
	public int Number;

	public final String gitbatroot = pu.gitbatroot;

	public final String gitnonbatroot = pu.nonbatroot;

	// ����汾��Ϣ
	public void readVersions(String mark) {
		System.out.println("��ȡ�汾��Ϣ");
		File file;
		if (mark.equals("SAR")) {
			file = new File(path_SAR);
		} else {
			file = new File(path_nonSAR);
		}
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
			System.out.println(version_number.length);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("��ȡ���");
	}

	// ���ݰ汾��Ϣ������git����
	public void GitDownLoad(String root, String mark) {
		System.out.println("��ʼ�������ػ���");
		try {
			///////////////// ����bat�ļ�////////////////////////
			StringBuffer batchBatCommand = new StringBuffer();
			File batfile;
			System.out.println("jianchayajiancha" + version_number.length);
			int ver = 0;
			for (int i = 1; i <= Number / 2; i++) {// ÿһ���ļ���
				String batFilePath;
				if (mark.equals("SAR")) {
					batFilePath = gitbatroot + "_" + i + ".bat";
				} else {
					batFilePath = gitnonbatroot + "_" + i + ".bat";
				}
				batchBatCommand.append(batFilePath + " & ");
				File file = new File(batFilePath);
				BufferedWriter br = new BufferedWriter(new FileWriter(file));
				br.write(Path_Root);// E:
				br.newLine();
				String cdroot = Path_Root;
				StringBuffer command = new StringBuffer();
				for (int j = 1; j <= 2; j++) {
					String changedic = "cd " + root + i;// �л���E://GitTest/i
					String download = "git clone " + http_path + " " + version_number[ver];// ���ش���
					String rename = "ren " + version_number[ver] + " [" + String.valueOf(j) + "]" + version_number[ver];// ����
					String check_path = root + i + "/" + "[" + String.valueOf(j) + "]" + version_number[ver];
					String check = "pmd.bat -d " + check_path + " -R java-basic -f csv -r " + check_path// �ƶ�׼��
							+ "/report.csv";
					command.append(changedic + " && " + download + " && " + rename + " && " + check);
					if (j == 1) {
						command.append(" & ");
					}
					ver++;
				}
				System.out.println(command);
				br.write(command.toString());
				br.close();
			}
			System.out.println("д��ű����");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ִ�е����ű�
	// public void ExecBat(String path) {
	// System.out.println(path);
	// try {
	// Process pr = Runtime.getRuntime().exec(path);
	// InputStream in = pr.getInputStream();
	// BufferedReader BR = new BufferedReader(new InputStreamReader(in));
	// String tmp = null;
	// // while ((tmp = BR.readLine()) != null) {
	// // pr.waitFor();
	// System.out.println("�ű�����ִ���У���ȴ�...");
	// // }
	// // pr.waitFor();
	// // int return_value = pr.exitValue();
	// // if (return_value == 0) {
	// // System.out.println("ִ�����.");
	// // } else {
	// // System.out.println("ִ��ʧ��.");
	// // }
	// // BR.close();
	// // pr.destroy();
	// System.out.println("�ű� " + path + " ִ�����");
	// // Scanner sc = new Scanner(System.in);
	// // sc.nextLine();
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }

	// ��������
	public void StartBat(String root, String mark) {
		System.out.println("��ʼ����Ŀ¼");
		mkDirectory mk = new mkDirectory();
		String mkDirectoryPath;
		for (int i = 1; i <= Number / 2; i++) {
			// ����Ŀ¼
			mkDirectoryPath = root + String.valueOf(i);
			boolean flag = mk.mkDirectory(mkDirectoryPath);
			if (flag) {
				System.out.println(mkDirectoryPath + "�������");
			} else {
				System.out.println(mkDirectoryPath + "����ʧ�ܣ���Ŀ¼�����Ѿ����ڣ�");
			}
		}
		// ����Ŀ����룬�������ƶ�
		GitDownLoad(root, mark);
	}

	// ��ʼSAR�����ű�
	public void start_Bat(String mark) {
		if (mark.equals("SAR")) {
			readVersions("SAR");
			StartBat(SAR_StorePath_root, mark);
		} else {
			readVersions("nonSAR");
			StartBat(nonSAR_StorePath_Root, mark);
		}
	}

	public static void main(String[] args) {
		Java_Bat jb = new Java_Bat();
		jb.readVersions("SAR");
		jb.StartBat(jb.SAR_StorePath_root, "SAR");
	}
}
