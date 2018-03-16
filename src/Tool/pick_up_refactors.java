package Tool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Util.PathUtil;

/***
 * ���룺 xxx.txt ��� ��(1)xxx-R.txt (2)xxx_mark.txt ���ã� ��ѡ��Refactor��Ϣ
 * ��ʱ�䡿2016.6.12��2017.1.4������Ƶ�ʾ��� 0Ϊ��ͨ�ļ� 1Ϊ�Գ����ļ��� �����á������ı����ݲ��������� ��Ϣ�� %refactor%
 * �����ϡ� �汾 �����¡� ����һ���汾 ֮�����Ϣժȡ������ժȡ����Ϣͳһ���ͬһ���ı��� �����˼· ����������BufferedReader ������
 * b1 ��ǰ��n��--̽������refactor��Ϣ������ ArrayList��¼���ʵ����� ��¼������ǰ�ߵĲ�N A�����̽��������Ϣ��
 * ����⵽refactor��Ϣ�����¼��������������¼��̽������ϢΪֹ B�� ����ArrayList����N��¼��� ��b1̽�⵽���汾������ʱ��ʼ��¼��
 * ����������������⡿1.��©�е���� develoers����Ϣ�¿����ж�����Ϣ 2.ȱ�ٰ汾����Ϣ
 */
public class pick_up_refactors {
	PathUtil pu = new PathUtil();
	// �ļ�·��
	public final String file_path = pu.file_path;
	public final String file_output_path = pu.R_path;
	public final String fileMark = pu.fileMark_output_path;
	public Set StandardSet = pu.addRefactoringKey();

	public int step_forward;
	ArrayList<String> routeString = new ArrayList<String>();

	// BufferedReader
	BufferedReader reader;

	ArrayList<String> marklist = new ArrayList<String>();// ������¼���ع���ǩ

	pick_up_refactors() {
	}

	// 1.�Ҿ���refactor����Ϣ
	public void search_step() {
		// �����ļ�
		File file = new File(file_path);
		boolean flag = false;
		// �����ļ��ܵ�
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			step_forward = 1;// readLineһ��--------�ͼ�¼һ��ǰ������

			while (line != null) {
				//System.out.println("�����롿--" + line);
				// ��¼��ͷ��λ��
				if (line.startsWith("�汾:")) {
					//System.out.println("�������ҵ�[�汾]������");
					// reader.mark(0);
					step_forward = 1;
					flag = true;
				}

				if (flag) {
					//System.out.println("��д�롿--" + line);
					routeString.add(line);
				}

				// ���line������Ϣ��ͷ
				// ������һ�а��� %refactor%��Ϣ
				if (line.startsWith("��Ϣ:")) {

					line = reader.readLine();
					step_forward++;
					//System.out.println("��д�롿--" + line);
					routeString.add(line);

					if (Judge(line)) {
						// �Ҷ���
						//System.out.println(line);
						//System.out.println("�ҵ�REFACTOR��Ϣ��������");
						//Scanner inn = new Scanner(System.in);
						// showlist();
						//inn.nextLine();
						// System.out.println(flag);
						// routeString.add(line);
						// record_start_end();
						// ������¼��ȥ
						marklist.add("1");
					} else {
						marklist.add("0");
						deletelist(step_forward);
						flag = false;
					}
				}
				line = reader.readLine();
				if (flag) {
					//System.out.println("ǰ��");
					step_forward++;
				}
			}

		} catch (Exception e) {
			//System.out.println("�쳣");
			e.printStackTrace();
		}
	}

	public boolean Judge(String line) {
		//System.out.println("���жϡ�");
		// ������ʽ�������ִ�Сдƥ��
		boolean flag = false;
		Iterator it = StandardSet.iterator();
		while (it.hasNext()) {
			Pattern p = Pattern.compile(it.next().toString(), Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(line);
			if (m.find()) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	// ��ӡ��Ϣ
	public void showlist() {
		System.out.println("+++++++++++++++++++++++++++++++++++++");
		for (int i = 0; i <= routeString.size() - 1; i++) {
			System.out.println(routeString.get(i));
		}
		System.out.println("*************************************");
	}

	// 1.5.ɾ��ArrayList�е�ǰstep��Ϣ
	public void deletelist(int step) {
		// System.out.println("ɾǰ�鿴");
		// showlist();
		// System.out.println(routeString.size() + " STEP " + step);
		for (int i = 0; i <= step - 1; i++) {
			routeString.remove(routeString.size() - 1);
		}
	}

	// 2.ArrayList����ļ�
	public void record_start_end() {
		System.out.println("�ҵ�����¼���");
		System.out.println("����reset����");
		try {
			// Scanner inn=new Scanner(System.in);
			// inn.nextLine();
			File outputfile = new File(file_output_path);
			BufferedWriter writer = new BufferedWriter(new FileWriter(outputfile, true));
			for (int i = 0; i <= routeString.size() - 1; i++) {
				// Scanner in=new Scanner(System.in);
				// in.nextLine();
				writer.write(routeString.get(i));
				// System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
				// System.out.println("****������ݣ�" + routeString.get(i));
				writer.write("\r\n");
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ����ع��ı��
	public void recordSelfMark() {
		try {
			File outputfile = new File(fileMark);
			BufferedWriter writer = new BufferedWriter(new FileWriter(outputfile, true));
			System.out.println("!!" + marklist.size());
			for (int i = 0; i <= marklist.size() - 1; i++) {
				System.out.println(marklist.get(i));
				writer.write(marklist.get(i));
				writer.write("\r\n");
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ������
	public static void main(String[] args) {
		pick_up_refactors r = new pick_up_refactors();
		System.out.println("1");
		r.search_step();
		System.out.println("2");
		r.record_start_end();
		System.out.println("3");
		r.recordSelfMark();
	}

}
