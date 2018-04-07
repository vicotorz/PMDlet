package Util;

/**
 * �����롿pmd-report.csv
 * �������pmd-report-1/2.csv
 * ��ʱ�䡿2016-10-17���������ļ�
 * �����á�(1)��.classpath��.project���� (2) ������pmd-report���Ʋ�����
 * E:\fastjson�¹��̣��޸ģ�\112\[1]011a84022b25a2876bcce960ab74fa793eecd91b\reports\pmd-report.csv
 * E:\fastjson�¹��̣��޸ģ�\112\pmd-report-1.csv �� ���Ҹ���
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
	// E://junit4�¹���/
	// C://Users/dell/Desktop/commons-ionot/
	public static final String src1 = "E://junit4not/";
	public static final String src2 = "/reports/pmd-report.csv";
	public static final String dest1 = "/pmd-report-1.csv";
	public static final String dest2 = "/pmd-report-2.csv";

	/**
	 * ���Ƶ����ļ�
	 * 
	 * @param srcFileName
	 *            �����Ƶ��ļ���
	 * @param descFileName
	 *            Ŀ���ļ���
	 * @param overlay
	 *            ���Ŀ���ļ����ڣ��Ƿ񸲸�
	 * @return ������Ƴɹ�����true�����򷵻�false
	 */
	public static boolean copyFile(String srcFileName, String destFileName, boolean overlay) {
		File srcFile = new File(srcFileName);
		System.out.println("�ж�Դ�ļ��Ƿ����  ");
		// �ж�Դ�ļ��Ƿ����
		if (!srcFile.exists()) {
			MESSAGE = "Դ�ļ���" + srcFileName + "�����ڣ�";
			// JOptionPane.showMessageDialog(null, MESSAGE);
			System.out.println(MESSAGE);
			return false;
		} else if (!srcFile.isFile()) {
			MESSAGE = "�����ļ�ʧ�ܣ�Դ�ļ���" + srcFileName + "����һ���ļ���";
			// JOptionPane.showMessageDialog(null, MESSAGE);
			System.out.println(MESSAGE);
			return false;
		}
		System.out.println("�ж�Ŀ���ļ��Ƿ����  ");
		// �ж�Ŀ���ļ��Ƿ����
		File destFile = new File(destFileName);
		if (destFile.exists()) {
			// ���Ŀ���ļ����ڲ�������
			if (overlay) {
				// ɾ���Ѿ����ڵ�Ŀ���ļ�������Ŀ���ļ���Ŀ¼���ǵ����ļ�
				new File(destFileName).delete();
			}
		} else {
			// ���Ŀ���ļ�����Ŀ¼�����ڣ��򴴽�Ŀ¼
			if (!destFile.getParentFile().exists()) {
				// Ŀ���ļ�����Ŀ¼������
				if (!destFile.getParentFile().mkdirs()) {
					// �����ļ�ʧ�ܣ�����Ŀ���ļ�����Ŀ¼ʧ��
					System.out.println("ʧ��");
					return false;
				}
			}
		}

		// �����ļ�
		int byteread = 0; // ��ȡ���ֽ���
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
	 * ��������Ŀ¼������
	 * 
	 * @param srcDirName
	 *            ������Ŀ¼��Ŀ¼��
	 * @param destDirName
	 *            Ŀ��Ŀ¼��
	 * @param overlay
	 *            ���Ŀ��Ŀ¼���ڣ��Ƿ񸲸�
	 * @return ������Ƴɹ�����true�����򷵻�false
	 */
	public static boolean copyDirectory(String srcDirName, String destDirName, boolean overlay) {
		// �ж�ԴĿ¼�Ƿ����
		File srcDir = new File(srcDirName);
		if (!srcDir.exists()) {
			MESSAGE = "����Ŀ¼ʧ�ܣ�ԴĿ¼" + srcDirName + "�����ڣ�";
			// JOptionPane.showMessageDialog(null, MESSAGE);
			return false;
		} else if (!srcDir.isDirectory()) {
			MESSAGE = "����Ŀ¼ʧ�ܣ�" + srcDirName + "����Ŀ¼��";
			// JOptionPane.showMessageDialog(null, MESSAGE);
			return false;
		}

		// ���Ŀ��Ŀ¼���������ļ��ָ�����β��������ļ��ָ���
		if (!destDirName.endsWith(File.separator)) {
			destDirName = destDirName + File.separator;
		}
		File destDir = new File(destDirName);
		// ���Ŀ���ļ��д���
		if (destDir.exists()) {
			// �����������ɾ���Ѵ��ڵ�Ŀ��Ŀ¼
			if (overlay) {
				new File(destDirName).delete();
			} else {
				MESSAGE = "����Ŀ¼ʧ�ܣ�Ŀ��Ŀ¼" + destDirName + "�Ѵ��ڣ�";
				// JOptionPane.showMessageDialog(null, MESSAGE);
				return false;
			}
		} else {
			// ����Ŀ��Ŀ¼
			System.out.println("Ŀ��Ŀ¼�����ڣ�׼������������");
			if (!destDir.mkdirs()) {
				System.out.println("����Ŀ¼ʧ�ܣ�����Ŀ��Ŀ¼ʧ�ܣ�");
				return false;
			}
		}

		boolean flag = true;
		File[] files = srcDir.listFiles();
		for (int i = 0; i < files.length; i++) {
			// �����ļ�
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
			MESSAGE = "����Ŀ¼" + srcDirName + "��" + destDirName + "ʧ�ܣ�";
			// JOptionPane.showMessageDialog(null, MESSAGE);
			return false;
		} else {
			return true;
		}
	}

	// ���������ļ��е�����
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

	// ����.classpath��.project
	public static void copyFile() {
		String srcFileName = "E://fastjson�¹��̣��޸ģ�/1/[1]a4807c9cb5cacf094f27d93b38b092aa55a04d90/.classpath";
		String srcFileName2 = "E://fastjson�¹��̣��޸ģ�/1/[1]a4807c9cb5cacf094f27d93b38b092aa55a04d90/.project";
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