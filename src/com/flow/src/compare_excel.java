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
 * 2016.7.10
 * victorz
 * 
 * ���˼·��
 * 
 *1.��file1��{File}��װ��set��
 *2.��file2��{File}��װ��set2��
 *3.��ǽ���
 *4.�Ų��Ϊ�������֣���1���ǽ������� ��2�����������ٻ��Ĳ���
 *
 *5.�����ļ�����һ�������
 *��1�� �ж�����ķǽ��������ֱ�졾��file3��д������
 *��2�� file2  �� {Line+Description} ��file1 �Ķ�Ӧ������Ա�
 *     ��һ������file2�б��  set��ʣ�µ���file1�б��
 *     ������һ���ļ�file3��д������
 * 
 * 2016-9-5 �޸ĳ������������
 * 
 * 2016-10-7 ǰ����ӡ���,Ϊ�������̵�
 * 
 * */
public class compare_excel {
	//HashSet<String> set=new HashSet<String>();
	public HashSet<String> set1;
	public HashSet<String> set2;
	public Set N_instersection_set1,N_instersection_set2;
	public Set result;
	public Set Instersection;//�Ž���
	
	public File file1,file2,file3;//�����ļ�
	public BufferedReader reader1,reader2,reader_file1,reader_file2;
	public BufferedWriter writer;
	
    //�����ļ�
	String path1;
	String path2;
	String path3;
	
	
	compare_excel(){
		set1=new HashSet<String>();
		set2=new HashSet<String>();
		N_instersection_set1=new HashSet<String>();
		N_instersection_set2=new HashSet<String>();
		result=new HashSet<String>();
		Instersection=new HashSet<String>();
	}
	
	public void pre_file(String p1,String p2,String p3){
		
		path1=p1;
		path2=p2;
	    path3=p3;
		System.out.println("����pre-file����");
		file1=new File(path1);
		file2=new File(path2);
		
		try{
		reader1=new BufferedReader(new FileReader(file1));
		reader2=new BufferedReader(new FileReader(file2));
		
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//����set
     public void initset(){
    	 System.out.println("����initset����");
    	 try{
    		 reader1.readLine();
    		 reader2.readLine();
    	 String line1=reader1.readLine();
    	 String line2=reader2.readLine();
    	 
    	 while(line1!=null){
    		 //System.out.println(line1);
    	     String[] c1=line1.split(",");
    	     set1.add(c1[2]);
    	     line1=reader1.readLine();
    	 }
    	 
    	 System.out.println("!!!!!!!!!!!!!!!!!!");
    	 while(line2!=null){
    		 //System.out.println(line2);
    		 String[] c2=line2.split(",");
    	     set2.add(c2[2]);
    	     line2=reader2.readLine();
    	 }
    	 
    	 System.out.println();
    	 System.out.println("set1&set2ȫ��������");
    	 System.out.println("####"+set1.size());
    	 System.out.println("####"+set2.size());
    	 //1.����1
    	 N_instersection_set1.addAll(set1);
    	 N_instersection_set1.removeAll(set2);
    	 
    	 //2.����2
    	 N_instersection_set2.addAll(set2);
    	 N_instersection_set2.removeAll(set1);
    	 
    	 //3.�󲹼�
    	 result.addAll(N_instersection_set1);
    	 result.addAll(N_instersection_set2);
    	 
    	 //4.����
    	 Instersection.addAll(set1);
    	 Instersection.retainAll(set2);
    	 
    	 }catch(Exception e){
    		 e.printStackTrace();
    	 }
     }
     
     //�鿴ָ���ַ����Ƿ��ڲ�����
     public boolean isInSet(String str,int num){
    	 //Scanner sc=new Scanner(System.in);
    	 //sc.nextLine();
    	 
    	 //System.out.println(str);
    	 //�жϸ�����str�Ƿ��ڸ����ļ�����
    	 //1--set1
    	 //2--set2
    	 boolean flag=false;
    	 Iterator<String> it = N_instersection_set1.iterator();
    	 Iterator<String> it2 = N_instersection_set2.iterator(); 
    	 if(num==1){
    		 //System.out.println("num====1");
    	 while (it.hasNext()) {
    		 String its=it.next();
    		 //System.out.println(its+"--"+str);
    	   if(str.equals(its)) {
    		   //System.out.println("FLAG");
    		   flag=true;
    		   break;
    	   }
    	 }  
    	 }
    	 
    	 if(num==2){
    		 System.out.println("num====2");
        	 while (it2.hasNext()) {  
        	   if(str.equals(it2.next())) {
        		   flag=true;
        		   break;
        	   }
        	 }  
        	 }
        
         	 return flag;
     }
     
     //�����ļ�
     public void dosth(){
    	 System.out.println("��ʼ����!");
    	 try{
    	 reader_file1=new BufferedReader(new FileReader(file1));
    	 reader_file2=new BufferedReader(new FileReader(file2));
    	 file3=new File(path3);
    	 writer=new BufferedWriter(new FileWriter(file3));
    	 System.out.println("������ϣ�");
    	 System.out.println(reader_file1.readLine());
    	 System.out.println(reader_file2.readLine());
    	 
    	 //1.N_instersection_set1���ļ�1���е�
    	 //2.N_instersection_set2���ļ�2���е�
    	 //3.result��set1+set2��
    	 
    	 //����
    	 //���ж�reader_file1��File��һ��String�ǲ����ڡ�set1��������// File+Line+Description�������жϡ�
    	 
    	 //��1�������  ��¼��ɾ����code smell��+����Ϣ
    	 
    	 //��2������set1��  ��¼���ʱ���File+Line+Description����Ϣ
    	                      //��2.1�����reader_file2�е�File�µ�String��reader_file1�ǲ���һ����
    	                      // ��2.1.1��һ��  װ�뼯���ж�
    	                      // ��2.1.2����һ��
    	                              //��� reader_file2�е�File�µ�String�Ƿ���set2�� ��¼�����ӵ�code semll��+����Ϣ
    	 //Scanner sc=new Scanner(System.in);
    	 //sc.nextLine();
    	  String str1=reader_file1.readLine(); 
    	  if(str1!=null){
    	  str1=str1.substring(str1.indexOf(","));
    	  }
    	  String str2=reader_file2.readLine();
    	  str2=str2.substring(str2.indexOf(","));
    	  
		  //System.out.println("@@str2����"+str2);
    	  while(str1!=null){
    		 // System.out.println("@@Str1����"+str1);
    		  String[] s=str1.split(",");
    		//���ж�reader_file1��File��һ��String�ǲ����ڡ�set1��������
    		  if(isInSet(s[2], 1)){
    			  System.out.println("��1��");
    			 //Scanner scc=new Scanner(System.in);
				 //scc.nextLine();
    			//��1�������  ��¼��ɾ����code smell��+����Ϣ
    			  writer.write("\"��file1�����p�ٵ�code smell��    \""+str1+"\r\n");
    			  str1=reader_file1.readLine();
    			  System.out.println("@@Str1����"+str1);
    			  if(str1!=null){
    			  str1=str1.substring(str1.indexOf(","));
    			  }
    		  }else{
    			  System.out.println("��2��");
    			  //Scanner scc=new Scanner(System.in);
 				 //scc.nextLine();
    			//��2������set1��  ��¼���ʱ�������Ϣ
                  //��2.1�����reader_file2�е�File�µ�String��reader_file1�ǲ���һ����
    			  
    			  if(str2!=null){
    			  System.out.println("****** "+str2);
    			  String[] s2=str2.split(",");
    			  
    			  
    			  if(s2[2].equals(s[2])){
    				  
    				  System.out.println(s2[2]+"^^^^"+s[2]);
    				  System.out.println("��2.1.1��");
    				// ��2.1.1��һ��  װ�뼯���ж�
    				  HashSet<String> subset1=new HashSet<String>();
    				  HashSet<String> subset2=new HashSet<String>();
    				  
    				  //Bug��ǰ�����Ų��ܲ���Աȣ��д��е����
    				  
    				   //װ1
    				  while(String_value(str1, s[2])&&str1!=null){
    					  System.out.println("��[1]��");
    					  System.out.println("===����Ԫ��==="+str1);
    					  subset1.add(str1);
    					  str1=reader_file1.readLine();
    					  if(str1!=null){
    					  str1=str1.substring(str1.indexOf(","));}
    				  }
    				  System.out.println(subset1.size());
    				  //װ2
    				  while(String_value(str2,s[2])&&str2!=null){
    					  System.out.println("��[2]��");
    					  System.out.println("===����Ԫ��==="+str2);
    					  subset2.add(str2);
    					  str2=reader_file2.readLine();//�ж��һ�εĴ���
    					  if(str2!=null){
    					  str2=str2.substring(str2.indexOf(","));
    					  }
    				  }
    				  System.out.println(subset2.size());
    				  
    				  //�����߲���
    				  Set S_set1=new HashSet<String>();
    				  Set S_set2=new HashSet<String>();
    				  
    				  S_set1.addAll(subset1);
    				  S_set1.removeAll(subset2);
    				  
    				  S_set2.addAll(subset2);
    				  S_set2.removeAll(subset1);
    				  
    				  
    				  System.out.println(S_set1.size());
    				  System.out.println(S_set2.size());
    				  System.out.println(S_set1.isEmpty());
    				  System.out.println(!S_set1.isEmpty());
    				  //Scanner sc=new Scanner(System.in);
    				  //sc.nextLine();
    				  
    				  
    				  
    				  //S_set1���е��Ǽ��ٵ�
    				  if(!S_set1.isEmpty()){
    					  //System.out.println("����զ�����ģ���"+!S_set1.isEmpty());
    					  //Scanner scc=new Scanner(System.in);
        				  //scc.nextLine();
    					  Iterator<String> it = S_set1.iterator();
    					  while(it.hasNext()){
    						  System.out.println("д��1");
    						  writer.write("\"��file1�����p�ٵ�code smell��\""+it.next()+"\r\n");
    					  }
    				  }
    				  
    				//S_set2���е������ӵ�
                     if(!S_set2.isEmpty()){
                    	 Iterator<String> it = S_set2.iterator();
                    	 while(it.hasNext()){
                    		 System.out.println("д��2");
   						  writer.write("\"��file2�������ӵ�code smell��\""+it.next()+"\r\n");
   					  }
    				  }
    				  
    				  
    			  }else{
    				  System.out.println("��2.1.2��");
    				// ��2.1.2����һ��
                      //��� reader_file2�е�File�µ�String�Ƿ���set2�� ��¼�����ӵ�code semll��+����Ϣ
    				  writer.write("\"��file2�������ӵ�code smell��\""+str2+"\r\n");
    				  str2=reader_file2.readLine();
        			  str2=str2.substring(str2.indexOf(","));
    			  }
                  
                  
    		  }
    		  
    		  }
    	  }
    	 reader_file1.close();
    	 reader_file2.close();
    	  
    	  
    	  
    	 }catch(Exception e){
    		 e.printStackTrace();
    	 }
     }
     
     
     public boolean String_value(String str,String value){
    	 boolean flag=false;
    	 if(str!=null){
    	 String[] s=str.split(",");
    	 if(s[2].equals(value)){
    		 flag=true;
    	 }
    	 }
    	 //System.out.println(s[2]+"###############"+value+"%%%%%%%%%%%%%%%"+flag);
    	 return flag;
     }
     
     //չʾ��������
     public void showAll(){
    	 System.out.println();
    	 System.out.println();
    	 System.out.println("����showall����");
    	 Iterator<String> it = N_instersection_set1.iterator(); 
    	 Iterator<String> it2 = N_instersection_set2.iterator();
    	 Iterator<String> it3 = result.iterator();
    	 Iterator<String> it4=Instersection.iterator();
    	 
    	 System.out.println("N_instersection_set1�е�Ԫ�ء�set1�����е�Ԫ�ء�");
    	 while (it.hasNext()) {  
    	   System.out.println(it.next());
    	 }
    	 
    	 System.out.println("N_instersection_set2�е�Ԫ�ء�set2���е�Ԫ�ء�");
    	 while (it2.hasNext()) {  
      	   System.out.println(it2.next().toString());
      	 }
    	 
    	 System.out.println("result��Ԫ�ء�set1+set2��");
    	 while (it3.hasNext()) {  
      	   System.out.println(it3.next().toString());
      	 }
    	 
//    	 System.out.println("Instersection�е�Ԫ�ء�set1��set2��");
//    	 while (it4.hasNext()) {  
//      	   System.out.println(it4.next().toString());
//      	 }
    	 System.out.println("####"+set1.size());
    	 System.out.println("####"+set2.size());
    	 System.out.println(Instersection.size());
    	 
     }
     
     //�رչ���
     public void final_actions(){
    	 try{
    	 reader1.close();
    	 reader2.close();
    	 writer.close();
    	 }catch(Exception e){
    		 e.printStackTrace();
    	 }
     }
	
     
	public static void main(String[] args) {
		//fastjson  1-121
		//junit4�¹���  1-28
		//commons-io�¹��� 1-25
		//C://Users/dell/Desktop/commons-ionot/
		//E://commons-io/
		String path = "E://junit4not/";//ƴ�Ӵ�1
		String filename="/pmd-final.txt";//ƴ�Ӵ�2
		
		
//		String p1=path+23+"/pmd-report-1.csv";
//		String p2=path+23+"/pmd-report-2.csv";
//		String p3=path+23+filename;
//		compare_excel e=new compare_excel();
//		e.pre_file(p1,p2,p3);
//		e.initset();
//		e.showAll();
//		e.dosth();
//		System.out.println("�������"+i);
		
//		e.final_actions();

	for(int i=1;i<=28;i++){
		
		compare_excel e=new compare_excel();
		String p1=path+i+"/pmd-report-1.csv";
		String p2=path+i+"/pmd-report-2.csv";
		String p3=path+i+filename;
		System.out.println(p1);
		System.out.println(p2);
		System.out.println(p3);
		e.pre_file(p1,p2,p3);
		e.initset();
		e.showAll();
		e.dosth();
		System.out.println("�������"+i);		
		e.final_actions();
	}
	}

}
