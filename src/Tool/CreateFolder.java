package Tool;

import Util.PathUtil;
import Util.mkDirectory;

//�ڳ���ʼ֮ǰ������Ŀ¼�ṹ
public class CreateFolder {
	// ����Ŀ¼
	// �ļ��ṹ��
	// һ��Ŀ¼��E��//xx_Files/
	// �ű�Ŀ¼��E��//xx_Files/xx_bat/download_rename_check_[/d].bat
	// ������ĿĿ¼��E��//SAR_XX/[/d];E://non_SAR/[/d]
	public void PrepareDirectory() {
		PathUtil pu = new PathUtil();
		mkDirectory mk = new mkDirectory();
		mk.mkDirectory(pu.RootPath);
		mk.mkDirectory(pu.SARbatFolder);
		mk.mkDirectory(pu.nonSARbatFolder);
		mk.mkDirectory(pu.SAR_StorePath_Root);
		mk.mkDirectory(pu.nonSAR_StorePath_Root);
		System.out.println("�������");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
