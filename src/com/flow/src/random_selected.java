package com.flow.src;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;


/**
 * 2017.2.22 ���ѡȡ����
 * ����fastjson_version.txt
 * ����fastjson_userful_version.txt
 * ��fastjson_version����ѡ�汾���鿴ʱ���Ѿ�ѡ���ˣ�����refactoring����İ汾��
 * 
 * �ҵ����õİ汾�Ժ󣬻�Ҫ֪���ð汾��ǰһ���汾
 * 
 * �ⷽ�����ϢҲ��Ҫ���
 * */
public class random_selected {
	public int min_num;//��С
	public int max_num;//���
	public int random_num;//��Ҫ����������ĸ���
	
	public String useful_version[];
	public String all_version[];
	

	
	
	//ƴ��һ�����ַ�����Ȼ���ٲ��
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
             
            //��ֲ�װ�뵽������
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
	
	//��useful_version 
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
             
            //��ֲ�װ�뵽������
            useful_version=concat_version.toString().split("|");
            random_num=useful_version.length;
            }catch(Exception e){
            	System.out.println("error2");
            	}
		}
	
	//���û��useful��version�����
	public void read_version(String path){
		File f=new File(path);
		try {
			BufferedReader br=new BufferedReader(new FileReader(f));
			String str;
			ArrayList<String> list=new ArrayList<String>();
			
			str = br.readLine();
			while(str!=null){
				
				if(str.startsWith("�汾:")){
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
	
	//���������
	public void random_number(){
		//�� min_num �� max_num ֮����������x  
		//������ȥall_version�в����ַ���s   ȥuserful_version�в����Ƿ���  || ȥ��¼���������ʱ����
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
	
	//��all_version�в��Ҹ����ַ�����ǰһ���ַ���
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
	
	//��ǰ��汾�Ž������
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
