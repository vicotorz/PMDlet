package Tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import Util.CopyFileUtil;
import Util.PathUtil;

public class MoveFile {
	public String[] version_number;
	PathUtil pu = new PathUtil();

	public void readVersions(String mark) {
		System.out.println("获取版本信息");
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
			pu.refac_Number = version_number.length/2;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("获取完毕");
	}

	// 将xx://yy/[1-9]/[1|2]中的report.csv 复制到 xx://yy/[1-9] 中并重命名 pmd-report-1 /
	// pmd-report-2
	public void copyUtil(String root, int Number) {
		System.out.println(root + "," + Number);
		CopyFileUtil cfu = new CopyFileUtil();
		int step = 1;
		for (int i = 0; i < Number; i += 2) {
			String rootpath = root + String.valueOf(step);// 上一级目录
			int ver = 1;
			for (int j = 1; j <= 2; j++) {
				ver = (j == 1 ? i : i + 1);
				String path = root + String.valueOf(step) + "/" + "[" + String.valueOf(j) + "]" + version_number[ver];// 下一级目录
				String srcFileName = path + "/report.csv";
				String destFileName;
				if (path.toString().contains("[1]")) {
					destFileName = rootpath + "/pmd-report-1.csv";
				} else {
					destFileName = rootpath + "/pmd-report-2.csv";
				}
				System.out.println("[srcFileName]" + srcFileName);
				System.out.println("[destFileName]" + destFileName);
				// 待复制的文件名，目标文件名，如果目标文件存在，是否覆盖
				cfu.copyFile(srcFileName, destFileName, true);
			}
			step++;
		}
	}

	public void startMove(String mark) {
		System.out.println("开始移动文件");
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
