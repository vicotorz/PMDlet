package test;

import java.io.IOException;

public class TestRunTime {
	public static void main(String[] args) {
		// ShellExecute(0,"runas", LPCSTR("cmd.exe"),LPCSTR("/c net user
		// administrator /active:yes"),"",SW_HIDE);
		// e:
		// cd E://SAR_fastjson\1
		// git clone https://github.com/alibaba/fastjson.git
		// a4807c9cb5cacf094f27d93b38b092aa55a04d90
		// cd..
		// ren a4807c9cb5cacf094f27d93b38b092aa55a04d90
		// [1]a4807c9cb5cacf094f27d93b38b092aa55a04d90
		String command1 = "E:";//
		String command2 = "cd E://SAR_fastjson/1";
		String command3 = "git clone https://github.com/alibaba/fastjson.git a4807c9cb5cacf094f27d93b38b092aa55a04d90";
		//String command4 = "cd..";
		String command5 = "ren a4807c9cb5cacf094f27d93b38b092aa55a04d90 [1]a4807c9cb5cacf094f27d93b38b092aa55a04d90";
		String command = command1 + " && " + command2 + " && " + command3 + " && "  + command5;
		Runtime runtime = Runtime.getRuntime();
		try {
			// "E://Java_workspace/PMDToolet/lib/nircmd.exe elevate " +
			String c = "nircmd.exe evelate " + command;
			System.out.println(c);
			Process pr = runtime.exec("cmd.exe " + command);
			System.out.println(pr.toString());
			System.out.println("µ÷ÓÃ½áÊø");
			// runtime.exe

			// runtime.exec
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
