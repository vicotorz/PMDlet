package Tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import Util.CopyFileUtil;
import Util.PathUtil;

//【时间】2018.3.16
public class Java_Bat {

	PathUtil pu = new PathUtil();
	public final String http_path = pu.http_path;
	public final String path = pu.for_bat_path;
	public String StorPath_root = pu.StorePath_Root;
	public String Path_Root = pu.Path_Root;
	public String[] version_number;
	public int Number;

	// 读入版本信息
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
			pu.refac_Number=Number;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	public void GitDownLoad() {
		try {
			Runtime runtime = Runtime.getRuntime();
			for (int i = 0; i < version_number.length; i++) {
				String cdcom = Path_Root;
				for (int j = 1; j <= 2; j++) {
					// E://GitTest/i/j
					String CDcom = "cd " + StorPath_root + String.valueOf(i + 1) + "/" + String.valueOf(j);
					// 克隆
					String command = "git clone " + http_path + " " + version_number[i];
					// 回退到 E://GitTest/i/
					String returncommand = "cd " + StorPath_root + String.valueOf(i + 1) + "/";
					// 改名 E://GitTest/i/[j]version_number[i]
					String Recommand = "ren " + version_number[i] + " [" + String.valueOf(j) + "]" + version_number[i];
					runtime.exec(CDcom);// 切换到目录
					runtime.exec(command);// 下载代码
					runtime.exec(returncommand);// 回退
					runtime.exec(Recommand);// 重命名
					String rootpath = StorPath_root + String.valueOf(i + 1);// 上一级目录
					String path = StorPath_root + String.valueOf(i + 1) + "/" + "[" + String.valueOf(j) + "]"
							+ version_number[i];// 下一级目录
					PMD_Check(path);
					copyUtil(rootpath, path);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// pmd检测
	public void PMD_Check(String path) {
		try {
			String command = "pmd.bat -d " + path + " -R java-basic -f csv -r " + path + "/report.csv";
			Runtime runtime = Runtime.getRuntime();
			runtime.exec(command);// pmd检测
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 将xx://yy/[1-9]/[1|2]中的report.csv 复制到 xx://yy/[1-9] 中并重命名 pmd-report-1 /
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
		// 待复制的文件名，目标文件名，如果目标文件存在，是否覆盖
		cfu.copyFile(srcFileName, destFileName, true);
	}

	public static void main(String[] args) {
		Java_Bat jb = new Java_Bat();
		// 获取版本和数目
		jb.readVersions();
		String mkDirectoryPath;
		for (int i = 1; i <= jb.Number; i++) {
			for (int j = 1; j <= 2; j++) {
				// 创建目录
				mkDirectoryPath = jb.StorPath_root + String.valueOf(i) + "/" + String.valueOf(j);
				if (mkDirectory(mkDirectoryPath)) {
					System.out.println(mkDirectoryPath + "建立完毕");
				} else {
					System.out.println(mkDirectoryPath + "建立失败！此目录或许已经存在！");
				}
			}
		}
		// 下载目标代码，分析，移动
		jb.GitDownLoad();
	}
}
