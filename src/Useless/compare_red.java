package Useless;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

/**
 * 
 * �ӡ��޸ĵ���Ϣ���г�ȡ
 * src/test/java/com/alibaba/json/bvt/parser/deser/asm/TestASM_double.java
 * 
 * �ַ���ƥ���Ȼ����
 * 
 * ��ȡ��final�е���Ϣ����ȡ
 * 
 * */
// �ڶ������ָ�
// ��file2�������ӵ�code smell��
// ,com.alibaba.fastjson.serializer,src/main/java/com/alibaba/fastjson/serializer/SerializeConfig.java,
//3,452,"A method should have only one exit point, and that should be the last statement in the method",Controversial
// Rules,OnlyOneReturn

// ��file1�����p�ٵ�code smell��
// ,com.alibaba.json.bvt.support.spring.data,src/test/java/com/alibaba/json/bvt/support/spring/data/PageToJSONTest.java,3,16,A
// method/constructor shouldn't explicitly throw java.lang.Exception,Strict
// Exception Rules,SignatureDeclareThrowsException
public class compare_red {
	//121 28 25
	//fastjson   junit4  commons-io
	final int Total_number=28;

	public final String path = "E://junit4not/";//ƴ�Ӵ�1
	public final String filename="/pmd-final-new.txt";//ƴ�Ӵ�2
	
	public final String path_original = "D://junit4not.csv";//csv��
	
	//��Ҫÿ���ļ��ﶼ��useful_version�ļ�
	public final String useful_versions="E://junit4not/junit4not_useful_version.txt";
	//String file_path = "D://commons-io-R.txt";//�����йصİ汾��ȡ--��Ҫ���ڵõ���Ŀ
	public final String file_outpath = "E://junit4not/junit4not.csv";//����������ļ�
	
	ArrayList<String[]> list;

	public void file_operation() {
		try {
			
			//
			list = new ArrayList<String[]>();
			CsvReader reader = new CsvReader(path_original, ',',
					Charset.forName("GBK"));

			// ������ͷ
			reader.readHeaders();
			while (reader.readRecord()) {
				list.add(reader.getValues());
			}
			reader.close();

			for (int row = 0; row < Total_number; row++) {
				
				HashSet<String> s = new HashSet<String>();//Set  ����   csv��ԭ�е��޸���Ϣ

				//System.out.println(list.get(row)[6]);//��1��
				
				String[] str = list.get(row)[6].split("\n");
				
				//System.out.println(str.length);//��2��
				
				for (int index = 0; index < str.length; index++) {
					s.add(substring(str[index]));
				}
				
				//System.out.println("s���ϼ������--"+s.size());//��3��
				
				
				//ƴ��·��
				String finPath=path+String.valueOf(row+1)+filename;
				
				//System.out.println(finPath);
				
				File f = new File(finPath);
				
				BufferedReader brd=new BufferedReader(new FileReader(f));
				
				//csvһ�������е���Ϣ�ͺ�pmd-final��һ���ļ���Ӧ
				
				String st=brd.readLine();
				
				HashSet<String>  AllSet=new HashSet<String>();//������Ϣ�ļ���
				HashSet<String>  deleteSet=new HashSet<String>();//��ȫ��������Ϣ  ��Ҫ�ж�ʱ�򺬡�    ��(ֻҪ�пո����)
				HashSet<String>  interactionset=new HashSet<String>();//��������
				
				while(st!=null){
					//System.out.println("*****"+st);
					//Scanner sc=new Scanner(System.in);
					//sc.nextLine();
					
					//System.out.println(st);
					//������ǿ���
					if(!st.equals("")){
					//�����Ӵ� 
					if(st.contains("���p�ٵ�code smell��    ,")){
						//��ȫ���������
						deleteSet.add(substr(st));
						
					}else{
						
						//��ͨ�����
						AllSet.add(substr(st));
					}
				
					}
					st=brd.readLine();
				}
				
				System.out.println("��ʼ���㲹����");
				
				//����Set���жԱȣ�ȡ������
			    //s�� AllSet����
				System.out.println(s.size());
				System.out.println(AllSet.size());

				//Scanner sc=new Scanner(System.in);
				//sc.nextLine();
				
				interactionset.addAll(s);
				interactionset.retainAll(AllSet);
				
				AllSet.removeAll(interactionset);
				
				System.out.println("���interactionset�����е�Ԫ��");
				System.out.println("S");
				Iterator itt=s.iterator();
				while(itt.hasNext()){
					System.out.println(itt.next());
				}
				System.out.println("AllSet");
				Iterator itr=AllSet.iterator();
				while(itr.hasNext()){
					System.out.println(itr.next());
				}
				//Scanner scc=new Scanner(System.in);
				//scc.nextLine();
				System.out.println();
				
				
				
				//д��
				//1.interactionset�������޸Ĺ��ļ���//�����ݽ��б��
				//2.deleteset����ȫ�����ļ���//�����ݽ��б���
				
				//3.ʣ�²���AllSetд��//����д��
				//��csv�еĵڰ��н�����д
				
				CsvWriter wr =new CsvWriter(path_original,',',Charset.forName("GBK"));
				
				//ƴ�ӵڰ�����Ҫ��ӵ�����
				StringBuffer record=new StringBuffer();
				
				Iterator<String> it = deleteSet.iterator();
				Iterator<String> it2 = interactionset.iterator();
				Iterator<String> it3 = AllSet.iterator();
				
				System.out.println("��ʼƴ���޸���Ϣ");
				
				while(it.hasNext()){
					record.append(it.next()+"����ȫȥ����");
					record.append("\r\n");
				}
				
				record.append("\r\n");
				
				while(it2.hasNext()){
					record.append(it2.next()+"�����޸�һ�¡�");
					record.append("\r\n");
				}
				
				record.append("\r\n");
				
				while(it3.hasNext()){
					record.append(it3.next()+"���㲻�����");
					record.append("\r\n");
				}
				
				System.out.println("ƴ�����");
				
				System.out.println("!!!!"+list.get(row).length);
				list.get(row)[8]=record.toString();
				brd.close();
			    
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
//	public int get_total_num(){
//		int number=0;
//		try{
//			
//			File file=new File(file_path);
//			BufferedReader rd =new BufferedReader(new FileReader(file));
//			String line=rd.readLine();
//			while(line!=null){
//				if(line.startsWith("�汾: ")){
//				number++;
//				}
//				line=rd.readLine();
//			}
//			rd.close();
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		return number;
//	}
	
	
	
	public void writeNewFile(){
		//��list�е���Ϣ�������뵽���ļ���
	    
		CsvWriter wr = new CsvWriter(file_outpath, ',',
				Charset.forName("GBK"));
		String[][] contents=new String[Total_number][7];//get_total_num()
		String[] header = { "���", "ǰһ���汾", "��ǰ�汾", "����", "����", "���߱�ע��Ϣ",
				"�޸ĵ���Ϣ", "ǰһ���汾�޸���Ϣ","����仯����Ϣ", };//9����Ϣ
		
		try {
			wr.writeRecord(header);
			
			for(int i=0;i<Total_number;i++){
				wr.writeRecord(list.get(i));
			}
			wr.close();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//��pmd-final��Ϣ����ɸѡ
	public String substr(String str){
		//�ļ���ʽ
		// ��file2�������ӵ�code smell��
		// ,com.alibaba.fastjson.serializer,src/main/java/com/alibaba/fastjson/serializer/SerializeConfig.java,
		//3,452,"A method should have only one exit point, and that should be the last statement in the method",Controversial
		// Rules,OnlyOneReturn
		String[] mstring=str.split(",");
		if(mstring[2].contains("\"")){
			mstring[2]=mstring[2].substring(mstring[2].indexOf("\"")+1,mstring[2].lastIndexOf("\""));
		}
		return mstring[2];
		
		
	}

	public String substring(String str) {
		// " " �� "(" ֮���Ƭ��
		String string;
		if (str.indexOf("(") != -1) {
			//���û�к��������
			string = str.substring(str.indexOf(" ") + 1, str.indexOf("("));
		} else {
			string = str.substring(str.indexOf(" ") + 1);
		}
		
		return string;
	}
	
	public void writeuseful_versions(){
		System.out.println("��¼���õİ汾��Ϣ");
		File f= new File(useful_versions);
		try{
			BufferedWriter wr=new BufferedWriter(new FileWriter(f));
			for(int index=0;index<Total_number;index++){
				wr.write(list.get(index)[1]+"|"+list.get(index)[2]+"|");
			}
			wr.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		compare_red cr = new compare_red();
		cr.file_operation();
		cr.writeNewFile();
		//cr.writeuseful_versions();
	}

}
