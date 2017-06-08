package com.flow.src;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;


/**
 * ���ļ������е�revision��Ϣȫ��ȡ����
 * �γ�  
 *     �汾1|�汾2|...|�汾n  
 * ���ַ�����Ϣ
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
	
	//�����Ӵ�
	public String substr(String str){
		return str.substring(str.indexOf(" ")+1);
	}
	
	//��ȡ�汾��
	public void fetch_revisions(){
		
		try{
			//�ļ��Ĳ���
			File file=new File(file_path);
			reader=new BufferedReader(new FileReader(file));
			
			String str=reader.readLine();
			while(str!=null){
				
				if(str.startsWith("�汾:")){
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
