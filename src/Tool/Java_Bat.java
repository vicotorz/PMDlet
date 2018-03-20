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

//【时间】2018.3.16
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

	// 读入版本信息
	public void readVersions() {
		System.out.println("获取版本信息");
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
		System.out.println("获取完毕");
	}

	// 创建文件夹
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

	// 根据版本信息，调用git下载
	public void GitDownLoad(String root) {
		File file = new File(gitbat);
		System.out.println("开始进入下载环节");
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
					String changedic = "cd " + root + String.valueOf(j);// 切换到E://GitTest/j
					String download = "git clone " + http_path + " " + version_number[ver];// 下载代码
					String rename = "ren " + version_number[ver] + " [" + String.valueOf(j) + "]" + version_number[ver];// 改名
					String check_path = root + "[" + String.valueOf(j) + "]" + version_number[ver];
					// 移动准备
					String check = "pmd.bat -d " + check_path + " -R java-basic -f csv -r " + check_path
							+ "/report.csv";
					String command = changedic + " && " + download + " && " + rename + " && " + check;
					System.out.println(command);
					br.write(command);
					br.newLine();
				}
				String returncom = "cd ..";// 回退到 E://GitTest/i/
				br.write(returncom);
				br.newLine();
			}
			br.close();
			// 执行bat文件
			System.out.println("开始执行bat文件");
			String bat_start = "cmd /c start " + gitbat;
			Process pr = Runtime.getRuntime().exec(bat_start);

			InputStream in = pr.getInputStream();
			BufferedReader BR = new BufferedReader(new InputStreamReader(in));
			String tmp = null;
			while ((tmp = BR.readLine()) != null) {
				// pr.waitFor();
				System.out.println("请等待");
			}
			System.out.println("脚本执行完毕，开始移动文件");
			copyUtil(root);
			System.out.println("移动完毕");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 将xx://yy/[1-9]/[1|2]中的report.csv 复制到 xx://yy/[1-9] 中并重命名 pmd-report-1 /
	// pmd-report-2
	public void copyUtil(String root) {
		CopyFileUtil cfu = new CopyFileUtil();
		for (int i = 0; i < Number; i++) {
			String rootpath = root + String.valueOf(i + 1);// 上一级目录
			for (int j = 1; j <= 2; j++) {
				String path = root + String.valueOf(i + 1) + "/" + "[" + String.valueOf(j) + "]" + version_number[i];// 下一级目录
				String srcFileName = path + "/report.csv";
				String destFileName;
				if (path.toString().contains("[1]")) {
					destFileName = rootpath + "/pmd-report-1.csv";
				} else {
					destFileName = rootpath + "/pmd-report-2.csv";
				}
				// 待复制的文件名，目标文件名，如果目标文件存在，是否覆盖
				cfu.copyFile(srcFileName, destFileName, true);
			}
		}
	}

	// 启动程序
	public void StartBat(String root) {
		System.out.println("开始创建目录");
		String mkDirectoryPath;
		for (int i = 1; i <= Number; i++) {
			// for (int j = 1; j <= 2; j++) {+ "/" + String.valueOf(j)
			// 创建目录
			mkDirectoryPath = root + String.valueOf(i);
			if (mkDirectory(mkDirectoryPath)) {
				System.out.println(mkDirectoryPath + "建立完毕");
			} else {
				System.out.println(mkDirectoryPath + "建立失败！此目录或许已经存在！");
			}
			// }
		}
		// 下载目标代码，分析，移动
		GitDownLoad(root);
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Java_Bat jb = new Java_Bat();
		// 获取版本和数目
		jb.readVersions();
		sc.nextLine();
		// SAR
		System.out.println("SAR分析");
		jb.StartBat(jb.SAR_StorePath_root);
		sc.nextLine();
		// non-SAR
		// System.out.println("nonSAR分析");
		// jb.StartBat(jb.non_SARPath_Root);
		// sc.nextLine();

	}
}
