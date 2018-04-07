package Util;

import java.util.HashSet;

//ר�ô洢·��
public class PathUtil {
	// �ļ��ṹ��
	// һ��Ŀ¼��E��//xx_Files/
	// �ű�Ŀ¼��E��//xx_Files/xx_bat/download_rename_check_[/d].bat
	// ������ĿĿ¼��E��//SAR_XX/[/d];E://non_SAR/[/d]
	public final String Path_Root = "H:";
	public final String filename = "systemml";
	public final String Http = "https://github.com/apache/systemml.git";
	public final String RootPath = Path_Root + "\\" + filename + "_Files/";
	public final String appendix = ".txt";
	public final String csvappendix = ".csv";

	// ��1��pick_up_refactorings
	// ���룺 xxx.txt ��� ��(1)xxx-R.txt (2)xxx_mark.txt ���ã� ��ѡ��Refactors
	// (3)xxx-R-nonSAR.txt
	public final String file_path = RootPath + filename + appendix;// ��svn��show_log��ճ����(׼����)
	public final String R_path_SAR = RootPath + filename + "-R" + appendix;// commons-io-R.txt";
	public final String R_path_nonSAR = RootPath + filename + "-R-nonSAR" + appendix;// commons-io-R-nonSAR.txt";
	public final String fileMark_output_path = RootPath + filename + "_mark" + appendix;// commons-io_mark.txt";
	public HashSet<String> set = new HashSet<String>();

	// ��2��calculate_authors
	// ���룺(1)xxx.txt (2) xx-R.txt
	// �����(1)xxx_all_authors.txt(2)xxx_calculate_authors.txt
	public final String Zfile = file_path;
	public final String file_path_2 = RootPath + filename + "-R" + appendix;
	public final String Zsave_path = RootPath + filename + "_all_authors" + appendix;
	public final String save_path = RootPath + filename + "_calculate_authors" + appendix;

	// ��3��All_revisions
	// ���룺xxx.txt
	// �����xxx_version.txt
	// ���а汾����ȡ
	// public final String file_path="D:\\druid.txt";
	public final String version_path = RootPath + filename + "_version" + appendix;

	// ��4��brevision.java
	// ���� (1)xx.txt (2) xx_version.txt (3) xx-R.txt
	// ���: (1)xx_useful_versions.txt (2)xxx.csv (3) xxx_for_bat_versions.txt
	// csv�еĸ�ʽ �� "���", "ǰһ���汾", "��ǰ�汾", "����", "����", "���߱�ע��Ϣ", "�޸ĵ���Ϣ",
	// "ǰһ���汾�޸���Ϣ", "����仯����Ϣ"��
	public final String useful_path = RootPath + filename + "_useful_versions" + appendix;
	public final String csv_path = RootPath + filename + csvappendix;
	public final String for_bat_path = RootPath + filename + "_for_bat_versions" + appendix;

	// ��4.5��random_selected.java
	// ���ã����ѡ��non-SAR�汾��
	// ���룺(1) xxx_version.txt (2)xxx_useful_versions.txt(3)xxx-R.txt
	// �����nonSAR_versions.txt(ǰһ������һ���汾)
	// ��nonSAR��
	// systemml_for_bat_versions
	public final String for_nonbat_path = RootPath + filename + "_for_bat_nonSAR_versions" + appendix;

	// ��5������csv��ԭ�汾
	// ��6��pmd������Ŀ,����report
	// ��7��CopyFileUtil��report�ƶ���������
	// Java_Bat.java
	// ���룺xxx_for_bat_versions.txt,�ű���Ϣ
	// for_bat_path
	// ��SAR�� for_bat_path
	// ��nonSAR�� for_nonbat_path
	public final String SAR_filename = "SAR_" + filename;
	public final String nonSAR_filename = "nonSAR_" + filename;
	public final String http_path = Http;
	public final String SAR_StorePath_Root = Path_Root + "\\\\" + SAR_filename + "/";// �洢SAR��ĿĿ¼
	public final String nonSAR_StorePath_Root = Path_Root + "\\\\" + nonSAR_filename + "/";// �洢non-SAR��ĿĿ¼
	public final String gitbatroot = RootPath + filename + "_bat/download_rename_check";
	public final String finalbat = RootPath + filename + "_bat/" + filename + "_final.bat";
	public final String nonbatroot = RootPath + filename + "_nonbat/download_rename_check_non";
	public final String nonfinalbat = RootPath + filename + "_nonbat/" + filename + "_final.bat";

	public static int refac_Number;// refactoring�ĸ���

	//��7.5��
	//�滻pmd�еĲ��淶��ʽ
	//replaceExcel
	
	// ��8��
	// �ƶ��ļ�
	// MoveFile

	// ��9��compare_excel.java
	// ���룺(1) pmd-report-1.csv (2) pmd-report-2.csv
	// �����(1) pmd-final-SAR.txt (2) pmd-final-nonSAR.txt
	// ��SAR����nonSAR��
	public final String checkfirstPath_SAR = "/pmd-final-SAR" + appendix;
	public final String checkfirstPath_nonSAR = "/pmd-final-nonSAR" + appendix;

	// ��10��match_String.java
	// ���룺pmd-final.txt
	// �����pmd-final-new.txt(pmd-final-new-SAR.txt,pmd-final-new-nonSAR.txt)
	// ���ã�������ϴ
	// ��SAR����nonSAR��
	public final String newFinalPath_SAR = "/pmd-final-new-SAR" + appendix;
	public final String newFinalPath_nonSAR = "/pmd-final-new-nonSAR" + appendix;

	// ��11��calculate_codesmell.java
	// ���룺(1) xxx-R.txt (2) pmd-final-new.txt
	// �����(1) xxx-information.csv (2) xxx-Noninformation.csv
	public final String info_Path = SAR_StorePath_Root + filename + "-information.csv";
	public final String non_info_Path = nonSAR_StorePath_Root + filename + "-Noninformation.csv";

	// ��11.5��addFunctions.java
	// ���룺rule_sets.txt
	// �����priorities_information.csv
	public final String rule_path = "./lib/rule_sets.txt";
	public final String Priority_info = RootPath + "priorities_information.csv";
	// ===============================����ʵ�����=========================================

	// ��1��SimplifyInfo.java��һ���ԣ�
	// ����:All.txt(׼����)
	// �����pmd_info.csv
	public final String All_path = "./lib/All.txt";
	public final String pmd_info_path = RootPath + "pmd_info.csv";

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
		set.add("pull");
		set.add("pull up");
		set.add("pull down");
		set.add("callapse");
		set.add("spilt");
		set.add("substitude");
		return set;
	}

	public HashSet<String> NonSARKey() {
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
		set.add("pull");
		set.add("pull up");
		set.add("pull down");
		set.add("callapse");
		set.add("spilt");
		set.add("substitude");
		return set;
	}

}
