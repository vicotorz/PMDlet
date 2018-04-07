package Tool;

import Util.PathUtil;
import Util.mkDirectory;

//在程序开始之前，创建目录结构
public class CreateFolder {
	// 创建目录
	// 文件结构：
	// 一般目录：E：//xx_Files/
	// 脚本目录：E：//xx_Files/xx_bat/download_rename_check_[/d].bat
	// 下载项目目录：E：//SAR_XX/[/d];E://non_SAR/[/d]
	public void PrepareDirectory() {
		PathUtil pu = new PathUtil();
		mkDirectory mk = new mkDirectory();
		mk.mkDirectory(pu.RootPath);
		mk.mkDirectory(pu.SARbatFolder);
		mk.mkDirectory(pu.nonSARbatFolder);
		mk.mkDirectory(pu.SAR_StorePath_Root);
		mk.mkDirectory(pu.nonSAR_StorePath_Root);
		System.out.println("创建完毕");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
