package Util;

import java.util.HashSet;

//专门存储路径
public class PathUtil {
	public final String RootPath = "E:\\Files/";
	public final String filename = "fastjson";
	public final String appendix = ".txt";
	public final String csvappendix = ".csv";

	// 【1】pick_up_refactorings
	// 输入： xxx.txt 输出 ：(1)xxx-R.txt (2)xxx_mark.txt 作用： 挑选出Refactors
	public final String file_path = RootPath + filename + appendix;// 从svn的show_log中粘贴出
	public final String R_path = RootPath + filename + "-R" + appendix;// commons-io-R.txt";
	public final String fileMark_output_path = RootPath + filename + "_mark" + appendix;// commons-io_mark.txt";
	public HashSet<String> set = new HashSet<String>();

	// 【2】calculate_authors
	// 输入：(1)xxx.txt (2) xx-R.txt
	// 输出：(1)xxx_all_authors.txt(2)xxx_calculate_authors.txt
	public final String Zfile = file_path;
	public final String file_path_2 = RootPath + filename + "-R" + appendix;
	public final String Zsave_path = RootPath + filename + "_all_authors" + appendix;
	public final String save_path = RootPath + filename + "_calculate_authors" + appendix;

	// 【3】All_revisions
	// 输入：xxx.txt
	// 输出：xxx_version.txt
	// 所有版本号提取
	// public final String file_path="D:\\druid.txt";
	public final String version_path = RootPath + filename + "_version" + appendix;

	// 【4】brevision.java
	// 输入 (1)xx.txt (2) xx_version.txt (3) xx-R.txt
	// 输出: (1)xx_userful_versions.txt (2)xxx.csv (3) xxx_for_bat_versions.txt
	//csv中的格式 【 "序号", "前一个版本", "当前版本", "作者", "日期", "作者标注信息", "修改的信息", "前一个版本修改信息", "具体变化的信息"】
	public final String useful_path = RootPath + filename + "_useful_versions" + appendix;
	public final String csv_path = RootPath + filename + csvappendix;
	public final String for_bat_path=RootPath + filename +"_for_bat_versions"+appendix;
	
	//【5】根据csv还原版本
	//【6】pmd分析项目
	//【7】CopyFileUtil将report移动并重命名
	//Java_Bat.java
	//输入：xxx_for_bat_versions.txt
	//for_bat_path
	public final String http_path="https://github.com/alibaba/fastjson.git";
	public final String StorePath_Root="E://GitTest/";
	public final String Path_Root="E:";
	public static int refac_Number;//refactoring的个数
	
	//【9】compare_excel.java
	//输入：(1) pmd-report-1.csv (2) pmd-report-2.csv
	//输出：pmd-final.txt
	public final String checkfirstPath="/pmd-final.txt";
	
	//【10】match_String.java
	//输入：pmd-final.txt
	//输出：pmd-final-new.txt
	//作用：数据清洗
	public final String newFinalPath="/pmd-final-new.txt";
	

	public HashSet<String> addRefactoringKey() {
		set.add("refactor");
		set.add("extract");
		set.add("inline");
		set.add("replace");
		set.add("introduce");
		set.add("rename");
		set.add("move");
		set.add("hide");
		set.add("encapsulate");
		set.add("change");
		set.add("convert");
		set.add("separate");
		set.add("decompose");
		set.add("consolidate");
		set.add("add");
		set.add("parameterize");
		set.add("preserve");
		set.add("pull up");
		set.add("pull down");
		set.add("callapse");
		set.add("spilt");
		set.add("substitude");
		return set;
	}

}
