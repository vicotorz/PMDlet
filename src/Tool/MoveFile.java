package Tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import Util.CopyFileUtil;
import Util.PathUtil;
import Util.replaceExcel;

public class MoveFile {
	public String[] version_number;
	PathUtil pu = new PathUtil();

	public void readVersions(String mark) {
		System.out.println("��ȡ�汾��Ϣ");
		File file;
		if (mark.equals("SAR")) {
			file = new File(pu.for_bat_path);
		} else {
			file = new File(pu.for_nonbat_path);
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
			pu.refac_Number = version_number.length / 2;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("��ȡ���");
	}

	// ��xx://yy/[1-9]/[1|2]�е�report.csv ���Ƶ� xx://yy/[1-9] �в������� pmd-report-1 /
	// pmd-report-2
	public void copyUtil(String root, int Number) {
		CopyFileUtil cfu = new CopyFileUtil();
		int step = 0;
		for (int i = 1; i <= Number; i++) {
			String rootpath = root + String.valueOf(i);// ��һ��Ŀ¼
			for (int j = 1; j <= 2; j++) {
				String path = root + i + "/" + "[" + String.valueOf(j) + "]" + version_number[step];// ��һ��Ŀ¼
				String srcFileName = path + "/report.csv";
				String srcFileName_new = path + "/report-new.csv";
				String destFileName;
				if (path.toString().contains("[1]")) {
					destFileName = rootpath + "/pmd-report-1.csv";
				} else {
					destFileName = rootpath + "/pmd-report-2.csv";
				}
				System.out.println("[srcFileName]" + srcFileName);
				System.out.println("[destFileName]" + destFileName);
				System.out.println(srcFileName);
				// �ַ����滻
				replaceExcel re = new replaceExcel();
				re.ReplaceString(srcFileName, srcFileName_new);
				System.out.println(srcFileName + "�滻���");
				// �����Ƶ��ļ�����Ŀ���ļ��������Ŀ���ļ����ڣ��Ƿ񸲸�
				System.out.println("[Դ�ļ�]" + srcFileName_new);
				System.out.println("[Ŀ���ļ�]" + destFileName);
				cfu.copyFile(srcFileName_new, destFileName, true);
				step++;
			}
		}
	}

	public void startMove(String mark) {
		System.out.println("��ʼ�ƶ��ļ�");
		if (mark.equals("SAR")) {
			readVersions("SAR");
			copyUtil(pu.SAR_StorePath_Root, pu.refac_Number);
		} else {
			readVersions("nonSAR");
			copyUtil(pu.nonSAR_StorePath_Root, pu.refac_Number);
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
