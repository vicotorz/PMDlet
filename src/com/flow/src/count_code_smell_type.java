package com.flow.src;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

/**
 * 
 * 统计--检测出的code smell类型
 * 
 * 列：名字  数目   file1增减度  file2增减度   总增减度
 * 
 * */
public class count_code_smell_type {
      //检测到的放入到集合中
	  Set<String> s;
	  String[][]  str_num;//code smell数目  * 5
	  
	  
	  String[][] ss;//用于接收addFunctions中返回的priority
	  
	count_code_smell_type(){
		s=new HashSet<String>();
		
	}
	
	//放入到集合中
	//读入csv文件
	public void put_into_set(String path){
		System.out.println("输入");
		System.out.println(path);
		
	    try {
			CsvReader reader = new CsvReader(path, ',',
					Charset.forName("GBK"));

			// 跳过表头
			reader.readHeaders();
			while (reader.readRecord()) {			
				s.add(reader.getValues()[4]);
			}
			reader.close();
			//创建数组
			str_num=new String[s.size()][5];
		     Iterator it=s.iterator();
		     int i=0;
		     while(it.hasNext()){
		    	 str_num[i][0]=it.next().toString();
		    	 str_num[i][1]="0";
		    	 str_num[i][2]="0";
		    	 str_num[i][3]="0";
		    	 str_num[i][4]="0";
		    	 i++;
		     }
		     System.out.println("数组构建完毕");
		     //重新读入csv文件，然后在对应的code-smell后添加对应的数目
		     CsvReader rd = new CsvReader(path, ',',
						Charset.forName("GBK"));

				// 跳过表头
				rd.readHeaders();
				while (rd.readRecord()) {
					int index=0;
					while(index<s.size()){
						
						if(str_num[index][0].equals(rd.getValues()[4].toString())){
							if(!rd.getValues()[5].equals("")){
							str_num[index][1]=String.valueOf(Integer.valueOf(rd.getValues()[5].toString())
									+Integer.valueOf(str_num[index][1]));
							str_num[index][2]=String.valueOf(Integer.valueOf(rd.getValues()[9].toString())
									+Integer.valueOf(str_num[index][2]));
							str_num[index][3]=String.valueOf(Integer.valueOf(rd.getValues()[10].toString())
									+Integer.valueOf(str_num[index][3]));
							str_num[index][4]=String.valueOf(Integer.valueOf(rd.getValues()[11].toString())
									+Integer.valueOf(str_num[index][4]));
							break;
							}
						}
						index++;
					}
					
				}
			rd.close();
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	    
	}
	
	
	//对数组进行排序  str_num
	public void sortArray(){
		System.out.println("排序");
		int min_number=-1;
		int index;
		for(int i=0;i<s.size()-1;i++){
		min_number=Integer.valueOf(str_num[i][1]);
		index=i;
		for(int j=i+1;j<s.size();j++){
			if(Integer.valueOf(str_num[j][1])<min_number){
				min_number=Integer.valueOf(str_num[j][1]);
				index=j;
			}
		}
		System.out.println(i+"     "+index);
		//交换 i j
		String temp_name=str_num[i][0];
		String temp_number=str_num[i][1];
		String temp_file1_number=str_num[i][2];
		String temp_file2_number=str_num[i][3];
		String temp_minus_number=str_num[i][4];
		
		str_num[i][0]=str_num[index][0];
		str_num[i][1]=str_num[index][1];
		str_num[i][2]=str_num[index][2];
		str_num[i][3]=str_num[index][3];
		str_num[i][4]=str_num[index][4];
		
		str_num[index][0]=temp_name;
		str_num[index][1]=temp_number;
		str_num[index][2]=temp_file1_number;
		str_num[index][3]=temp_file2_number;
		str_num[index][4]=temp_minus_number;
		
		}
		System.out.println("排序完毕！");
		for(int i=0;i<s.size();i++){
			System.out.print(str_num[i][0]+"   "+str_num[i][1]+"   "+str_num[i][2]+"   "+str_num[i][3]+"   "+str_num[i][4]);
			System.out.println();
		}			
	}
	
	//访问addFuction文件
	public void getPriority(){
	//*************************************
				addFunctions af=new addFunctions();
				af.splitStrings();
				af.fetechpriorities();
				
				af.changeArray();
				System.out.println(af.getInfo().length);
				ss=new String[af.getInfo().length][2];
				ss=af.getInfo();
				System.out.println(ss[0][0]+"  "+ss[0][1]);
	//************************************
	}
	//Return priority
	public String RetrunPriority(String name){
		//"UseSingleton"
		String instance=null;
		
		System.out.println(name);
		//System.out.println(name.length());
		instance=name.substring(1, name.length()-1);
		System.out.println(instance);
//		Scanner sc=new Scanner(System.in);
//		sc.nextLine();
		
		String priority=null;
		for(int i=0;i<ss.length;i++){
			if(ss[i][0].equals(instance)){
				priority=ss[i][1];
				break;
			}
		}
		System.out.println(priority);
		return priority;
	}
	
	//将数组的内容放入到excel中
	public void WriteIntoExcel(){
		//写入到csv中
		
		getPriority();
		try {
			CsvWriter wr=new CsvWriter("D:\\code_semll_information.csv",',',Charset.forName("GBK"));
			String[] header={"code_smell","数量","file1数量","file2数量","总增减量","priority"};
			wr.writeRecord(header);
			String[][] content=new String[s.size()][6];
			for(int i=0;i<s.size();i++){
				if(!str_num[i][0].equals("")){
				content[i][0]=str_num[i][0];
			    content[i][1]=str_num[i][1];
			    content[i][2]=str_num[i][2];
			    content[i][3]=str_num[i][3];
			    content[i][4]=str_num[i][4];
			    content[i][5]=RetrunPriority(str_num[i][0]);
			    wr.writeRecord(content[i]);
			    }
			}
			wr.close();			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//输出到文件中
	public void output_into_file(String path){
		System.out.println("输出");
		try{
			File f=new File(path);
			BufferedWriter bw=new BufferedWriter(new FileWriter(f));
			Iterator it=s.iterator();
			bw.write("+++");
			bw.write("\r\n");
			bw.write(String.valueOf(s.size()));
			bw.write("\r\n");
			bw.write("+++");
			bw.write("\r\n");
//			while(it.hasNext()){
//				bw.write(it.next().toString());
//				bw.write("\r\n");
//			}
			
			for(int i=0;i<s.size();i++){
				bw.write(str_num[i][0]);
				bw.write("                   ");
				bw.write(str_num[i][1]);
				bw.write("\r\n");
			}
			
			bw.close();
		}catch(Exception e){
			
		}
	}
	
	
	//输入文件    information.csv
	//1.确定code-smell类型
	//2.重新访问文件，在数组中记录数目
	//3.排序后，输出
   public static void main(String[] args) {
	   
	String intopath="D:\\junit4-information.csv";
	String outpath="D:\\junit4_total_code_smell_type.txt";
	count_code_smell_type ccst=new count_code_smell_type();
	ccst.put_into_set(intopath);
    ccst.sortArray();
    ccst.WriteIntoExcel();
	ccst.output_into_file(outpath);
	
   }
}
