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
 * 输入： xxx.txt 输出 ：(1)xxx-R.txt (2)xxx_mark.txt 作用： 挑选出Refactor信息
 * 【时间】2016.6.12，2017.1.4（加入频率矩阵 0为普通文件 1为自称认文件） 【作用】输入文本数据查找里面有 信息： %refactor%
 * 【向上】 版本 【向下】 到下一个版本 之间的信息摘取出来，摘取的信息统一输出同一个文本中 【设计思路 】设置两个BufferedReader ，计数
 * b1 向前走n步--探索具有refactor信息的内容 ArrayList记录访问的数据 记录本次向前走的步N A）如果探索到【信息】
 * 并检测到refactor信息，则记录下来，并继续记录到探索到信息为止 B） 否则ArrayList将后N记录清空 当b1探测到【版本字样的时候开始记录】
 * 【编程中遇到的问题】1.有漏判的情况 develoers在信息下可能有多条信息 2.缺少版本号信息
 */
public class pick_up_refactors {
	PathUtil pu = new PathUtil();
	// 文件路径
	public final String file_path = pu.file_path;
	public final String file_output_path = pu.R_path;
	public final String fileMark = pu.fileMark_output_path;
	public Set StandardSet = pu.addRefactoringKey();

	public int step_forward;
	ArrayList<String> routeString = new ArrayList<String>();

	// BufferedReader
	BufferedReader reader;

	ArrayList<String> marklist = new ArrayList<String>();// 用来记录自重构标签

	pick_up_refactors() {
	}

	// 1.找具有refactor的信息
	public void search_step() {
		// 创建文件
		File file = new File(file_path);
		boolean flag = false;
		// 创建文件管道
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			step_forward = 1;// readLine一行--------就记录一个前进步骤

			while (line != null) {
				//System.out.println("【读入】--" + line);
				// 记录开头的位置
				if (line.startsWith("版本:")) {
					//System.out.println("！！！找到[版本]！！！");
					// reader.mark(0);
					step_forward = 1;
					flag = true;
				}

				if (flag) {
					//System.out.println("【写入】--" + line);
					routeString.add(line);
				}

				// 如果line是以信息开头
				// 并且下一行包含 %refactor%信息
				if (line.startsWith("信息:")) {

					line = reader.readLine();
					step_forward++;
					//System.out.println("【写入】--" + line);
					routeString.add(line);

					if (Judge(line)) {
						// 找对了
						//System.out.println(line);
						//System.out.println("找到REFACTOR信息！！！！");
						//Scanner inn = new Scanner(System.in);
						// showlist();
						//inn.nextLine();
						// System.out.println(flag);
						// routeString.add(line);
						// record_start_end();
						// 继续记录下去
						marklist.add("1");
					} else {
						marklist.add("0");
						deletelist(step_forward);
						flag = false;
					}
				}
				line = reader.readLine();
				if (flag) {
					//System.out.println("前进");
					step_forward++;
				}
			}

		} catch (Exception e) {
			//System.out.println("异常");
			e.printStackTrace();
		}
	}

	public boolean Judge(String line) {
		//System.out.println("【判断】");
		// 正则表达式，不区分大小写匹配
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

	// 打印信息
	public void showlist() {
		System.out.println("+++++++++++++++++++++++++++++++++++++");
		for (int i = 0; i <= routeString.size() - 1; i++) {
			System.out.println(routeString.get(i));
		}
		System.out.println("*************************************");
	}

	// 1.5.删除ArrayList中的前step信息
	public void deletelist(int step) {
		// System.out.println("删前查看");
		// showlist();
		// System.out.println(routeString.size() + " STEP " + step);
		for (int i = 0; i <= step - 1; i++) {
			routeString.remove(routeString.size() - 1);
		}
	}

	// 2.ArrayList输出文件
	public void record_start_end() {
		System.out.println("找到并记录输出");
		System.out.println("调用reset方法");
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
				// System.out.println("****输出内容：" + routeString.get(i));
				writer.write("\r\n");
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 输出重构的标记
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

	// 主函数
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
