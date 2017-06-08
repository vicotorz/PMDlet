package com.flow.src;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/**
 * 记录分析出有自称认重构作者信息
 * 
 * fastjson是所有信息
 * 
 * fastjson-R.txt具有refactor信息
 * 
 * 
 * junit4
 * incubator-systemml
 * commons-io 
 * druid
 * */
public class calculate_authors {
	public Set<String> auSet;
	
	public String[][] name_number;
	String Zfile="D:\\commons-io.txt";
	String file_path="D:\\commons-io-R.txt";
	
	String Zsave_path="D:\\commons-io_all_authors.txt";
	String save_path="D:\\commons-io_calculate_authors.txt";
	
	calculate_authors(){
		auSet=new HashSet<String>();
	}
	
	//统计所有作者
	public void cal_authors(){
	
	
	File f=new File(Zfile);
	try {
		BufferedReader br=new BufferedReader(new FileReader(f));
		String str;
		
			str = br.readLine();
		while(str!=null){
			//如果以‘作者:’开头，记录
			if(str.startsWith("作者:")){
				String author=str.substring(4, str.length());
				auSet.add(author);
				
			}
			str=br.readLine();
		}
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
	//对refactor中的作者计数
	public void cal(){
		name_number=new String[auSet.size()][3];//第一列标记作者  第二列标记refactor  第三列标记所有数目
		//赋值
		for(int i=0;i<auSet.size();i++){
			name_number[i][1]=String.valueOf("0");
		}
		Iterator itr=auSet.iterator();
		int index=0;
		while(itr.hasNext()){
			name_number[index][0]=itr.next().toString();
			name_number[index][1]=String.valueOf("0");
			name_number[index][2]=String.valueOf("0");
	        index++;
		}
		
		//访问fastjosn-R文件，开始计数
		File file=new File(file_path);
		try {
			BufferedReader bfr=new BufferedReader(new FileReader(file));
			String st=bfr.readLine();
			while(st!=null){
				if(st.startsWith("作者:")){
					String author=st.substring(4, st.length());
					add_this_author(author);
				}
				st=bfr.readLine();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		File file0=new File(Zfile);
		try {
			BufferedReader bfr=new BufferedReader(new FileReader(file0));
			String st=bfr.readLine();
			while(st!=null){
				if(st.startsWith("作者:")){
					String author=st.substring(4, st.length());
					add_Frequency(author);
				}
				st=bfr.readLine();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	//对应作者计数
	public void add_this_author(String author){
		for(int i=0;i<auSet.size();i++){
			if(name_number[i][0].equals(author)){
				//加一计数
				name_number[i][1]=String.valueOf(Integer.valueOf(name_number[i][1])+1);
			}
		}
	}
	
	
	//对应作者计数
		public void add_Frequency(String author){
			for(int i=0;i<auSet.size();i++){
				if(name_number[i][0].equals(author)){
					//加一计数
					name_number[i][2]=String.valueOf(Integer.valueOf(name_number[i][2])+1);
				}
			}
		}
	
	public void saveFile(){

		File Zfile=new File(Zsave_path);
		File f=new File(save_path);
		try {
			BufferedWriter Zbw=new BufferedWriter(new FileWriter(Zfile));
			BufferedWriter bw=new BufferedWriter(new FileWriter(f));
			//写入内容
			Iterator itr=auSet.iterator();
			Zbw.write("====================");
			Zbw.write("\r\n");
			Zbw.write("作者统计");
			Zbw.write("\r\n");
			Zbw.write("====================");
			Zbw.write("\r\n");
			while(itr.hasNext()){
				 Zbw.write(itr.next().toString());
				 Zbw.write("\r\n");
			}
			
			
			bw.write("====================");
			bw.write("\r\n");
			bw.write("作者          次数     总参与次数");
			bw.write("\r\n");
			bw.write("====================");
			bw.write("\r\n");
			for(int index=0;index<auSet.size();index++){
				 bw.write(name_number[index][0]+"                                                 "+name_number[index][1]+"                                                 "+name_number[index][2]);
				 bw.write("\r\n");
			}
			
			bw.close();
			Zbw.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		calculate_authors ca=new calculate_authors();
		ca.cal_authors();
		ca.cal();
		
		System.out.println(ca.auSet.size());
		System.out.println(ca.name_number.length);
		
		ca.saveFile();
		
	}

}
