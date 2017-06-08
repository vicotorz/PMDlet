package com.flow.src;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;


/**
 * 将文件中所有的revision信息全部取出来
 * 形成  
 *     版本1|版本2|...|版本n  
 * 的字符串信息
 * 
 * junit4
 * incubator-systemml
 * commons-io 
 * druid 
 * */

public class All_revisions {
	
	//StringBuffer
	public StringBuffer revisions=new StringBuffer();
	String file_path="D:\\druid.txt";
	
	String file_output_path="D:\\druid_version.txt";
	
	int totalNumber=0;
	
	BufferedReader reader;
	
	BufferedWriter writer;
	
	//返回子串
	public String substr(String str){
		return str.substring(str.indexOf(" ")+1);
	}
	
	//获取版本号
	public void fetch_revisions(){
		
		try{
			//文件的操作
			File file=new File(file_path);
			reader=new BufferedReader(new FileReader(file));
			
			String str=reader.readLine();
			while(str!=null){
				
				if(str.startsWith("版本:")){
					revisions.append(substr(str));
					revisions.append("|");
					totalNumber++;
				}
				
				str=reader.readLine();
				
			}
			
		  File f=new File(file_output_path);
		  writer=new BufferedWriter(new FileWriter(f));
		  
		  writer.write(String.valueOf(totalNumber));
		  writer.write("\r\n");
		  writer.write(revisions.toString());
		  
		  writer.close();
		  reader.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		All_revisions ar=new All_revisions();
		ar.fetch_revisions();
	}
	

}
