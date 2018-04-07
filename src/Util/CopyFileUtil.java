package Util;

/**
 * 【输入】pmd-report.csv
 * 【输出】pmd-report-1/2.csv
 * 【时间】2016-10-17批量复制文件
 * 【作用】(1)将.classpath和.project复制 (2) 将分析pmd-report复制并改名
 * E:\fastjson新工程（修改）\112\[1]011a84022b25a2876bcce960ab74fa793eecd91b\reports\pmd-report.csv
 * E:\fastjson新工程（修改）\112\pmd-report-1.csv 中 并且改名
 * */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class CopyFileUtil {

	private static String MESSAGE = "";
	// E://junit4新工程/
	// C://Users/dell/Desktop/commons-ionot/
	public static final String src1 = "E://junit4not/";
	public static final String src2 = "/reports/pmd-report.csv";
	public static final String dest1 = "/pmd-report-1.csv";
	public static final String dest2 = "/pmd-report-2.csv";

	/**
	 * 复制单个文件
	 * 
	 * @param srcFileName
	 *            待复制的文件名
	 * @param descFileName
	 *            目标文件名
	 * @param overlay
	 *            如果目标文件存在，是否覆盖
	 * @return 如果复制成功返回true，否则返回false
	 */
	public static boolean copyFile(String srcFileName, String destFileName, boolean overlay) {
		File srcFile = new File(srcFileName);
		System.out.println("判断源文件是否存在  ");
		// 判断源文件是否存在
		if (!srcFile.exists()) {
			MESSAGE = "源文件：" + srcFileName + "不存在！";
			// JOptionPane.showMessageDialog(null, MESSAGE);
			System.out.println(MESSAGE);
			return false;
		} else if (!srcFile.isFile()) {
			MESSAGE = "复制文件失败，源文件：" + srcFileName + "不是一个文件！";
			// JOptionPane.showMessageDialog(null, MESSAGE);
			System.out.println(MESSAGE);
			return false;
		}
		System.out.println("判断目标文件是否存在  ");
		// 判断目标文件是否存在
		File destFile = new File(destFileName);
		if (destFile.exists()) {
			// 如果目标文件存在并允许覆盖
			if (overlay) {
				// 删除已经存在的目标文件，无论目标文件是目录还是单个文件
				new File(destFileName).delete();
			}
		} else {
			// 如果目标文件所在目录不存在，则创建目录
			if (!destFile.getParentFile().exists()) {
				// 目标文件所在目录不存在
				if (!destFile.getParentFile().mkdirs()) {
					// 复制文件失败：创建目标文件所在目录失败
					System.out.println("失败");
					return false;
				}
			}
		}

		// 复制文件
		int byteread = 0; // 读取的字节数
		InputStream in = null;
		OutputStream out = null;

		try {
			in = new FileInputStream(srcFile);
			out = new FileOutputStream(destFile);
			byte[] buffer = new byte[1024];

			while ((byteread = in.read(buffer)) != -1) {
				out.write(buffer, 0, byteread);
			}
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (out != null)
					out.close();
				if (in != null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 复制整个目录的内容
	 * 
	 * @param srcDirName
	 *            待复制目录的目录名
	 * @param destDirName
	 *            目标目录名
	 * @param overlay
	 *            如果目标目录存在，是否覆盖
	 * @return 如果复制成功返回true，否则返回false
	 */
	public static boolean copyDirectory(String srcDirName, String destDirName, boolean overlay) {
		// 判断源目录是否存在
		File srcDir = new File(srcDirName);
		if (!srcDir.exists()) {
			MESSAGE = "复制目录失败：源目录" + srcDirName + "不存在！";
			// JOptionPane.showMessageDialog(null, MESSAGE);
			return false;
		} else if (!srcDir.isDirectory()) {
			MESSAGE = "复制目录失败：" + srcDirName + "不是目录！";
			// JOptionPane.showMessageDialog(null, MESSAGE);
			return false;
		}

		// 如果目标目录名不是以文件分隔符结尾，则加上文件分隔符
		if (!destDirName.endsWith(File.separator)) {
			destDirName = destDirName + File.separator;
		}
		File destDir = new File(destDirName);
		// 如果目标文件夹存在
		if (destDir.exists()) {
			// 如果允许覆盖则删除已存在的目标目录
			if (overlay) {
				new File(destDirName).delete();
			} else {
				MESSAGE = "复制目录失败：目的目录" + destDirName + "已存在！";
				// JOptionPane.showMessageDialog(null, MESSAGE);
				return false;
			}
		} else {
			// 创建目的目录
			System.out.println("目的目录不存在，准备创建。。。");
			if (!destDir.mkdirs()) {
				System.out.println("复制目录失败：创建目的目录失败！");
				return false;
			}
		}

		boolean flag = true;
		File[] files = srcDir.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 复制文件
			if (files[i].isFile()) {
				flag = CopyFileUtil.copyFile(files[i].getAbsolutePath(), destDirName + files[i].getName(), overlay);
				if (!flag)
					break;
			} else if (files[i].isDirectory()) {
				flag = CopyFileUtil.copyDirectory(files[i].getAbsolutePath(), destDirName + files[i].getName(),
						overlay);
				if (!flag)
					break;
			}
		}
		if (!flag) {
			MESSAGE = "复制目录" + srcDirName + "至" + destDirName + "失败！";
			// JOptionPane.showMessageDialog(null, MESSAGE);
			return false;
		} else {
			return true;
		}
	}

	// 返回里面文件夹的名称
	public static ArrayList<String> showFileName(String srcDirName) {
		ArrayList<String> list = new ArrayList<String>();
		File srcDir = new File(srcDirName);
		File[] files = srcDir.listFiles();

		for (int i = 0; i < files.length; i++) {
			// System.out.println(files[i]);
			if (files[i].isDirectory()) {
				// System.out.println(true);
				String tempFile = files[i].toString();
				String tempFileName = tempFile.substring(tempFile.lastIndexOf("\\") + 1);
				System.out.println(tempFileName);
				list.add(tempFileName);
			}
		}
		return list;
	}

	public static void moveFile() {

		for (int i = 1; i <= 28; i++) {
			System.out.println(i);
			String destPath = src1 + i;
			ArrayList<String> list = CopyFileUtil.showFileName(destPath);

			String finalsrc1 = destPath + "/" + list.get(0) + src2;
			String finaldestPath1 = destPath + "/" + dest1;

			String finalsrc2 = destPath + "/" + list.get(1) + src2;
			String finaldestPath2 = destPath + "/" + dest2;

			copyFile(finalsrc1, finaldestPath1, true);
			copyFile(finalsrc2, finaldestPath2, true);
		}
	}

	// 复制.classpath和.project
	public static void copyFile() {
		String srcFileName = "E://fastjson新工程（修改）/1/[1]a4807c9cb5cacf094f27d93b38b092aa55a04d90/.classpath";
		String srcFileName2 = "E://fastjson新工程（修改）/1/[1]a4807c9cb5cacf094f27d93b38b092aa55a04d90/.project";
		String dest1 = "E://commons-ionot/";
		String dest2 = "/.classpath";
		String dest3 = "/.project";
		for (int i = 1; i <= 25; i++) {
			String destPath = dest1 + i;
			ArrayList<String> list = CopyFileUtil.showFileName(destPath);
			String finaldestPath1 = destPath + "/" + list.get(0) + dest2;
			String finaldestPath2 = destPath + "/" + list.get(0) + dest3;
			String finaldestPath3 = destPath + "/" + list.get(1) + dest2;
			String finaldestPath4 = destPath + "/" + list.get(1) + dest3;
			copyFile(srcFileName, finaldestPath1, true);
			copyFile(srcFileName2, finaldestPath2, true);
			copyFile(srcFileName, finaldestPath3, true);
			copyFile(srcFileName2, finaldestPath4, true);
		}
	}

	public static void main(String[] args) {
		// copyFile();
		moveFile();

	}
}