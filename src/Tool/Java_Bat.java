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

//【时间】2018.3.16
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

	// 读入版本信息
	public void readVersions(String mark) {
		System.out.println("获取版本信息");
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
		System.out.println("获取完毕");
	}

	// 根据版本信息，调用git下载
	public void GitDownLoad(String root, String mark) {
		System.out.println("开始进入下载环节");
		try {
			///////////////// 创建bat文件////////////////////////
			StringBuffer batchBatCommand = new StringBuffer();
			File batfile;
			System.out.println("jianchayajiancha" + version_number.length);
			int ver = 0;
			for (int i = 1; i <= Number / 2; i++) {// 每一个文件夹
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
					String changedic = "cd " + root + i;// 切换到E://GitTest/i
					String download = "git clone " + http_path + " " + version_number[ver];// 下载代码
					String rename = "ren " + version_number[ver] + " [" + String.valueOf(j) + "]" + version_number[ver];// 改名
					String check_path = root + i + "/" + "[" + String.valueOf(j) + "]" + version_number[ver];
					String check = "pmd.bat -d " + check_path + " -R java-basic -f csv -r " + check_path// 移动准备
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
			System.out.println("写入脚本完毕");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 执行单独脚本
	// public void ExecBat(String path) {
	// System.out.println(path);
	// try {
	// Process pr = Runtime.getRuntime().exec(path);
	// InputStream in = pr.getInputStream();
	// BufferedReader BR = new BufferedReader(new InputStreamReader(in));
	// String tmp = null;
	// // while ((tmp = BR.readLine()) != null) {
	// // pr.waitFor();
	// System.out.println("脚本正在执行中，请等待...");
	// // }
	// // pr.waitFor();
	// // int return_value = pr.exitValue();
	// // if (return_value == 0) {
	// // System.out.println("执行完成.");
	// // } else {
	// // System.out.println("执行失败.");
	// // }
	// // BR.close();
	// // pr.destroy();
	// System.out.println("脚本 " + path + " 执行完毕");
	// // Scanner sc = new Scanner(System.in);
	// // sc.nextLine();
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }

	// 启动程序
	public void StartBat(String root, String mark) {
		System.out.println("开始创建目录");
		mkDirectory mk = new mkDirectory();
		String mkDirectoryPath;
		for (int i = 1; i <= Number / 2; i++) {
			// 创建目录
			mkDirectoryPath = root + String.valueOf(i);
			boolean flag = mk.mkDirectory(mkDirectoryPath);
			if (flag) {
				System.out.println(mkDirectoryPath + "建立完毕");
			} else {
				System.out.println(mkDirectoryPath + "建立失败！此目录或许已经存在！");
			}
		}
		// 下载目标代码，分析，移动
		GitDownLoad(root, mark);
	}

	// 开始SAR分析脚本
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
