package com.flow.src;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.csvreader.CsvWriter;



/**
 * 
 * 2016.12.7
 * ����pmd��⵽��������Ϣ����
 * 
 * ��������������ȡ������Ϣ��ǰ�ڵ�rule�Ѿ�����ճ������ͳһ���ļ��У�
 * 
 * ������ �����⵽<ruleset name=  ��ȡ���� keyֵ
 * 
 *         �����⵽name ������ <ruleset name= ����ȡ����
 *         
 *         ���ŵ�excel��
 *         
 *         list�洢���ݵĸ�ʽ��name priority description
 *         ����<description>�м�����̬
 *         1)<d>xxx</d>
 *         
 *         2)<d>xxx
 *         </d>
 *         
 *         3)<d>
 *         xxx
 *         </d>
 * 
 * */
public class SimplifyInfo {
	public HashMap<String,ArrayList<String>> map;

	
	//�����ļ���Ȼ��ֵ���뵽Map��
	public void ReadFile(){
		map=new HashMap<String, ArrayList<String>>();
		String path="E:\\All.txt";
		File f=new File(path);
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(f));
			String string;
			string = br.readLine();
			String rulesetname="";
			String priority;

			int step=0;
			ArrayList<String> list=new ArrayList<String>();
			Pattern pattern = Pattern.compile("<description>[a-zA-Z]+");
			
			boolean flag=false;
			StringBuffer description=new StringBuffer();
			boolean properties_entry=false;  
			
			while(string!=null){
				//˵����ruleset name
				if(string.contains("<ruleset name=")){
					 if(step!=0){
							//˵��������һ���µ�
						    	map.put(rulesetname, list);
						    	list=new ArrayList<String>();
						    	description=new StringBuffer();
						    	System.out.println("װ��ȥ");
						    	System.out.println(map.keySet());
						    	//System.out.println(map.values());
						    	
						    	}
					//System.out.println("ruleset name");
					String[] s=string.split("\"");
					rulesetname=s[1].substring(0, s[1].length());
					
					//System.out.println(rulesetname);
					step++;
				}
				
				//̽�⵽��ʼ����properties
				if(string.contains("<properties>")){
					properties_entry=true;
				}
				//̽�⵽�뿪properties
				if(string.contains("</properties>")){
					properties_entry=false;
				}
				
				//˵������ͨ��name
				if(string.contains("name=")&&!string.contains("<ruleset name=")&&!string.contains("<property name=")&&!properties_entry){
					//System.out.println("name");
					
					String[] s=string.split("\"");
					//System.out.println(string);
					String rule_name=s[1].substring(0, s[1].length());
					list.add(rule_name);
					
					//System.out.println(rule_name);
				}
				
				//˵����priority
				if(string.contains("<priority>")){
						//System.out.println("priority");
						String[] str=string.split(">");
						String[] strr=str[1].split("<");
						priority=strr[0];
					
						list.add(priority);
						//System.out.println(priority);
					}
				
				
				
// 1)<d>xxx</d>
//       
// 2)<d>xxx
//</d>
//       
// 3)<d>
//   xxx
// </d>

				//description��һ�����
				if(string.contains("<description>")&&string.contains("</description>")){
					//��һ�����
					String[] str=string.split(">");
					String[] strr=str[1].split("<");
					list.add(strr[0]);
					
				}else if(pattern.matcher(string).matches()){
					//�ڶ������
					String[] str=string.split(">");
					list.add(str[1]);
					
				}else if(string.contains("<description>")){
					//���������
					flag=true;
				}
				
				//���������
				if(flag){
					description.append(string);
				}
				//�������������
				if(flag&&string.contains("</description>")){
					flag=false;
					list.add(description.toString());
					description=new StringBuffer();
					
				}
				
				string=br.readLine();
			}
			
			}catch(Exception e){
				System.out.println("���ʣ�"+path);
				e.printStackTrace();
			}
	}
	
	public void showAll(){
		//System.out.println(map.values());
		System.out.println(map.keySet());
	}
	
//	public int getLength(){
//		
//	}
	
	//д���ļ���
	//д���ʽ  ��rulesetName��
	//           name
	public void WriteFile(){
    	CsvWriter wr=new CsvWriter("D:\\pmd_info.csv",',',Charset.forName("GBK"));
		String[] header={"����","����","���ȼ�"};
		try {
			wr.writeRecord(header);
			String[][] content=new String[286][3];
			/**
			 * Iterator iter = map.entrySet().iterator();
����						while (iter.hasNext()) {
						����Map.Entry entry = (Map.Entry) iter.next();
						����Object key = entry.getKey();
						����Object val = entry.getValue();
						����}
			 * 
			 * 
			 * */
			
			Iterator iter=map.entrySet().iterator();
			int index=0;
			while(iter.hasNext()){
				Map.Entry entry = (Map.Entry) iter.next();
				String key = (String)entry.getKey();
				ArrayList<String> val = (ArrayList<String>) entry.getValue();
				//System.out.println(key);
				System.out.println(val.size());
				
				
					content[index][0]="��"+key+"��";
					content[index][1]=val.get(0).toString();
					content[index][2]="";
				    System.out.println(key+"--"+val.get(0));
				    wr.writeRecord(content[index]);
					index++;
					
					
					if(val.size()>=1){
						List sublist=val.subList(1, val.size());
						System.out.println(sublist.size());
						for(int i=0;i<sublist.size();i=i+3){
						content[index][0]=sublist.get(i).toString();
						content[index][1]=sublist.get(i+1).toString();
						content[index][2]=sublist.get(i+2).toString();
						wr.writeRecord(content[index]);
						index++;
						
						}
					}
			}
			System.out.println(index);
			wr.close();		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	
	
	//list�е�һ����ruleset������  ��������˳��Ϊ  ���� ���� ���ȼ�
	public static void main(String[] args) {
		
		SimplifyInfo sfi=new SimplifyInfo();
		System.out.println("1");
		sfi.ReadFile();
		System.out.println("2");
		sfi.showAll();
		sfi.WriteFile();
		
	}

}
