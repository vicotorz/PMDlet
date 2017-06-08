package com.flow.src;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;


/**
 * 2017.2.22 随机选取程序
 * 读入fastjson_version.txt
 * 读入fastjson_userful_version.txt
 * 在fastjson_version中挑选版本（查看时候已经选过了，或是refactoring里面的版本）
 * 
 * 找到有用的版本以后，还要知道该版本的前一个版本
 * 
 * 这方面的信息也需要输出
 * */
public class random_selected {
	public int min_num;//最小
	public int max_num;//最大
	public int random_num;//需要产生随机数的个数
	
	public String useful_version[];
	public String all_version[];
	

	
	
	//拼成一个长字符串，然后再拆分
	public void read_all_version(String path){
		StringBuffer concat_version = new StringBuffer();
		File f = new File(path);
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));

			
			br.readLine();
			String line = br.readLine();
			while (line != null) {
				concat_version.append(line);
				line=br.readLine();
			}
             br.close();
             
            //拆分并装入到数组中
             System.out.println(concat_version.toString());
             all_version=concat_version.toString().split("\\|");
             //System.out.println(all_version[0]);
             //System.out.println(all_version[1]);
             min_num=1;
             max_num=all_version.length;
             
             
	}catch(Exception e){
		System.out.println("error1");
	}
	}
	
	//读useful_version 
	public void read_useful_version(String path){
		StringBuffer concat_version = new StringBuffer();
		File f = new File(path);
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			String line = br.readLine();
			while (line != null) {
				concat_version.append(line);
				line=br.readLine();
			}
             br.close();
             
            //拆分并装入到数组中
            useful_version=concat_version.toString().split("|");
            random_num=useful_version.length;
            }catch(Exception e){
            	System.out.println("error2");
            	}
		}
	
	//针对没有useful—version的情况
	public void read_version(String path){
		File f=new File(path);
		try {
			BufferedReader br=new BufferedReader(new FileReader(f));
			String str;
			ArrayList<String> list=new ArrayList<String>();
			
			str = br.readLine();
			while(str!=null){
				
				if(str.startsWith("版本:")){
					String version=str.substring(4, str.length());
					list.add(version);
				}
				
				str=br.readLine();
			}
			//System.out.println("111");
			useful_version=list.toArray(new String[list.size()]);
			//System.out.println("222");
			random_num=useful_version.length;
			}catch(Exception e){
				System.out.println("error3");
			}
	}
	
	//产生随机数
	public void random_number(){
		//在 min_num 和 max_num 之间产生随机数x  
		//产生后去all_version中查找字符串s   去userful_version中查找是否有  || 去记录随机数中找时候有
		    StringBuffer st=new StringBuffer();
		    
		    StringBuffer pre_now=new StringBuffer();
			ArrayList<String> list=new ArrayList<String>();
			int total_number=0;
			while(true){
		 	Random random = new Random();         
	        int i = (random.nextInt(max_num)%(max_num-min_num+1) + min_num)-1;
	        //System.out.println(i);
	        if((!isIn(all_version[i],useful_version))&&(!isInNum(i,list))){
	        	list.add(String.valueOf(i));
	        	System.out.println(all_version[i]);
	        	st.append(all_version[i]+"|");
	        	pre_now.append(getPreviousVersion(all_version[i])+"|"+all_version[i]);
	        	pre_now.append("\r\n");
	        	total_number++;
	        }
	        if(total_number>=random_num){
	        	System.out.println(st.toString());
	        	System.out.println();
	        	System.out.println(pre_now.toString());
	        	break;
	        }
	        }
	}
	
	public boolean isInNum(int i,ArrayList<String> li){
		boolean flag=false;
		String istring=String.valueOf(i);
		//String[] liarray=(String[])li.toArray();
		for(i=0;i<li.size();i++){
			if(li.get(i).equals(istring)){
				flag=true;
				break;
			}
		}
		return flag;
	}
	
	public boolean isIn(String str,String[] s){
		boolean flag=false;
		for(int i=0;
				i<s.length;
				i++){
			if(s[i].equals(str)){
				flag=true;
				break;
			}
		}
		return flag;
	}
	
	//在all_version中查找给定字符串的前一个字符串
	public String getPreviousVersion(String version){
		int location=-1;
		for(int i=0;i<all_version.length;i++){
			if(all_version[i].equals(version)){
				location=i;
				break;
			}
		}
		return all_version[location+1];
	}
	
	//对前后版本号进行输出
//	public void before_now_version(){
//		StringBuffer sb= new StringBuffer();
//		for(int i=0;i<useful_version.length;i++){
//			sb.append(getPreviousVersion(useful_version[i])+"|"+useful_version[i]);
//			sb.append("\r\n");
//		}
//		System.out.println();
//		System.out.println(sb.toString());
//	}

	public static void main(String[] args) {
		//fastjson  junit4 commons-io druid incubator-systemml
		String path1="D:\\junit4_version.txt";
		String path2="D:\\junit4_useful_versions.txt";
		String path3="D:\\junit4-R.txt";
		random_selected rs=new random_selected();
		rs.read_all_version(path1);
		//rs.read_useful_version(path2);
		rs.read_version(path3);
		rs.random_number();
		//rs.before_now_version();
	}
}
