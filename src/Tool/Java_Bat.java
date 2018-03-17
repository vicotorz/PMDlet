package Tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import Util.CopyFileUtil;
import Util.PathUtil;

//��ʱ�䡿2018.3.16
public class Java_Bat {

	PathUtil pu = new PathUtil();
	public final String http_path = pu.http_path;
	public final String path = pu.for_bat_path;// SAR
	public final String SAR_StorPath_root = pu.SAR_StorePath_Root;
	public final String non_SARPath_Root = pu.non_SARPath_Root;
	public final String Path_Root = pu.Path_Root;
	public String[] version_number;
	public int Number;

	// ����汾��Ϣ
	public void readVersions() {
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
		try {
			Runtime runtime = Runtime.getRuntime();
			for (int i = 0; i < version_number.length; i++) {
				String cdcom = Path_Root;
				for (int j = 1; j <= 2; j++) {
					// E://GitTest/i/j
					String CDcom = "cd " + root + String.valueOf(i + 1) + "/" + String.valueOf(j);
					// ��¡
					String command = "git clone " + http_path + " " + version_number[i];
					// ���˵� E://GitTest/i/

					String returncommand = "cd " + root + String.valueOf(i + 1) + "/";
					// ���� E://GitTest/i/[j]version_number[i]
					String Recommand = "ren " + version_number[i] + " [" + String.valueOf(j) + "]" + version_number[i];
					runtime.exec(CDcom);// �л���Ŀ¼
					System.out.println("��������" + version_number[i] + "�汾...");
					runtime.exec(command);// ���ش���
					System.out.println("�������");
					runtime.exec(returncommand);// ����
					runtime.exec(Recommand);// ������
					String rootpath = root + String.valueOf(i + 1);// ��һ��Ŀ¼
					String path = root + String.valueOf(i + 1) + "/" + "[" + String.valueOf(j) + "]"
							+ version_number[i];// ��һ��Ŀ¼
					PMD_Check(path);
					copyUtil(rootpath, path);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// pmd���
	public void PMD_Check(String path) {
		try {
			String command = "pmd.bat -d " + path + " -R java-basic -f csv -r " + path + "/report.csv";
			Runtime runtime = Runtime.getRuntime();
			runtime.exec(command);// pmd���
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ��xx://yy/[1-9]/[1|2]�е�report.csv ���Ƶ� xx://yy/[1-9] �в������� pmd-report-1 /
	// pmd-report-2
	public void copyUtil(String rootpath, String path) {
		String srcFileName = path + "/report.csv";
		String destFileName;
		if (path.toString().contains("[1]")) {
			destFileName = rootpath + "/pmd-report-1.csv";
		} else {
			destFileName = rootpath + "/pmd-report-2.csv";
		}
		CopyFileUtil cfu = new CopyFileUtil();
		// �����Ƶ��ļ�����Ŀ���ļ��������Ŀ���ļ����ڣ��Ƿ񸲸�
		cfu.copyFile(srcFileName, destFileName, true);
	}

	//��������
	public void StartBat(String root) {

		String mkDirectoryPath;
		for (int i = 1; i <= Number; i++) {
			for (int j = 1; j <= 2; j++) {
				// ����Ŀ¼
				mkDirectoryPath = root + String.valueOf(i) + "/" + String.valueOf(j);
				if (mkDirectory(mkDirectoryPath)) {
					System.out.println(mkDirectoryPath + "�������");
				} else {
					System.out.println(mkDirectoryPath + "����ʧ�ܣ���Ŀ¼�����Ѿ����ڣ�");
				}
			}
		}
		// ����Ŀ����룬�������ƶ�
		GitDownLoad(root);
	}

	public static void main(String[] args) {
		Java_Bat jb = new Java_Bat();
		// ��ȡ�汾����Ŀ
		jb.readVersions();
		// SAR
		System.out.println("SAR����");
		jb.StartBat(jb.SAR_StorPath_root);
		// non-SAR
		System.out.println("nonSAR����");
		jb.StartBat(jb.non_SARPath_Root);

	}
}
