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
 * 编程思路：
 * 
 *1.读file1的{File}列装进set中
 *2.读file2的{File}列装进set2中
 *3.求非交集
 *4.排查分为两个部分，【1】非交集部分 【2】交集部分少或多的部分
 *
 *5.两个文件重新一点点载入
 *【1】 判断哪里的非交集，文字标红【在file3中写出来】
 *【2】 file2  的 {Line+Description} 与file1 的对应部分相对比
 *     不一样的在file2中标出  set中剩下的在file1中标出
 *     【在另一个文件file3中写出来】
 * 
 * 2016-9-5 修改成批量处理分析
 * 
 * 2016-10-7 前后添加“”,为清理做铺垫
 * 
 * */
public class compare_excel {
	//HashSet<String> set=new HashSet<String>();
	public HashSet<String> set1;
	public HashSet<String> set2;
	public Set N_instersection_set1,N_instersection_set2;
	public Set result;
	public Set Instersection;//放交集
	
	public File file1,file2,file3;//读入文件
	public BufferedReader reader1,reader2,reader_file1,reader_file2;
	public BufferedWriter writer;
	
    //读入文件
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
		System.out.println("调用pre-file方法");
		file1=new File(path1);
		file2=new File(path2);
		
		try{
		reader1=new BufferedReader(new FileReader(file1));
		reader2=new BufferedReader(new FileReader(file2));
		
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//处理set
     public void initset(){
    	 System.out.println("调用initset方法");
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
    	 System.out.println("set1&set2全部加载完");
    	 System.out.println("####"+set1.size());
    	 System.out.println("####"+set2.size());
    	 //1.补集1
    	 N_instersection_set1.addAll(set1);
    	 N_instersection_set1.removeAll(set2);
    	 
    	 //2.补集2
    	 N_instersection_set2.addAll(set2);
    	 N_instersection_set2.removeAll(set1);
    	 
    	 //3.大补集
    	 result.addAll(N_instersection_set1);
    	 result.addAll(N_instersection_set2);
    	 
    	 //4.交集
    	 Instersection.addAll(set1);
    	 Instersection.retainAll(set2);
    	 
    	 }catch(Exception e){
    		 e.printStackTrace();
    	 }
     }
     
     //查看指定字符串是否在补集中
     public boolean isInSet(String str,int num){
    	 //Scanner sc=new Scanner(System.in);
    	 //sc.nextLine();
    	 
    	 //System.out.println(str);
    	 //判断给定的str是否在给定的集合中
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
     
     //处理文件
     public void dosth(){
    	 System.out.println("开始处理!");
    	 try{
    	 reader_file1=new BufferedReader(new FileReader(file1));
    	 reader_file2=new BufferedReader(new FileReader(file2));
    	 file3=new File(path3);
    	 writer=new BufferedWriter(new FileWriter(file3));
    	 System.out.println("创建完毕！");
    	 System.out.println(reader_file1.readLine());
    	 System.out.println(reader_file2.readLine());
    	 
    	 //1.N_instersection_set1是文件1独有的
    	 //2.N_instersection_set2是文件2独有的
    	 //3.result是set1+set2的
    	 
    	 //步骤
    	 //先判断reader_file1的File中一栏String是不是在【set1】补集中// File+Line+Description【具体判断】
    	 
    	 //【1】如果在  记录【删减的code smell】+行信息
    	 
    	 //【2】不在set1中  记录这个时候的File+Line+Description的信息
    	                      //【2.1】检测reader_file2中的File下的String和reader_file1是不是一样的
    	                      // 【2.1.1】一样  装入集合判断
    	                      // 【2.1.2】不一样
    	                              //检测 reader_file2中的File下的String是否在set2中 记录【增加的code semll】+行信息
    	 //Scanner sc=new Scanner(System.in);
    	 //sc.nextLine();
    	  String str1=reader_file1.readLine(); 
    	  if(str1!=null){
    	  str1=str1.substring(str1.indexOf(","));
    	  }
    	  String str2=reader_file2.readLine();
    	  str2=str2.substring(str2.indexOf(","));
    	  
		  //System.out.println("@@str2读入"+str2);
    	  while(str1!=null){
    		 // System.out.println("@@Str1读入"+str1);
    		  String[] s=str1.split(",");
    		//先判断reader_file1的File中一栏String是不是在【set1】补集中
    		  if(isInSet(s[2], 1)){
    			  System.out.println("【1】");
    			 //Scanner scc=new Scanner(System.in);
				 //scc.nextLine();
    			//【1】如果在  记录【删减的code smell】+行信息
    			  writer.write("\"【file1】【減少的code smell】    \""+str1+"\r\n");
    			  str1=reader_file1.readLine();
    			  System.out.println("@@Str1读入"+str1);
    			  if(str1!=null){
    			  str1=str1.substring(str1.indexOf(","));
    			  }
    		  }else{
    			  System.out.println("【2】");
    			  //Scanner scc=new Scanner(System.in);
 				 //scc.nextLine();
    			//【2】不在set1中  记录这个时候的行信息
                  //【2.1】检测reader_file2中的File下的String和reader_file1是不是一样的
    			  
    			  if(str2!=null){
    			  System.out.println("****** "+str2);
    			  String[] s2=str2.split(",");
    			  
    			  
    			  if(s2[2].equals(s[2])){
    				  
    				  System.out.println(s2[2]+"^^^^"+s[2]);
    				  System.out.println("【2.1.1】");
    				// 【2.1.1】一样  装入集合判断
    				  HashSet<String> subset1=new HashSet<String>();
    				  HashSet<String> subset2=new HashSet<String>();
    				  
    				  //Bug：前面的序号不能参与对比，有串行的情况
    				  
    				   //装1
    				  while(String_value(str1, s[2])&&str1!=null){
    					  System.out.println("【[1]】");
    					  System.out.println("===加入元素==="+str1);
    					  subset1.add(str1);
    					  str1=reader_file1.readLine();
    					  if(str1!=null){
    					  str1=str1.substring(str1.indexOf(","));}
    				  }
    				  System.out.println(subset1.size());
    				  //装2
    				  while(String_value(str2,s[2])&&str2!=null){
    					  System.out.println("【[2]】");
    					  System.out.println("===加入元素==="+str2);
    					  subset2.add(str2);
    					  str2=reader_file2.readLine();//有多读一次的串行
    					  if(str2!=null){
    					  str2=str2.substring(str2.indexOf(","));
    					  }
    				  }
    				  System.out.println(subset2.size());
    				  
    				  //求两者补集
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
    				  
    				  
    				  
    				  //S_set1中有的是减少的
    				  if(!S_set1.isEmpty()){
    					  //System.out.println("？？咋进来的？？"+!S_set1.isEmpty());
    					  //Scanner scc=new Scanner(System.in);
        				  //scc.nextLine();
    					  Iterator<String> it = S_set1.iterator();
    					  while(it.hasNext()){
    						  System.out.println("写入1");
    						  writer.write("\"【file1】【減少的code smell】\""+it.next()+"\r\n");
    					  }
    				  }
    				  
    				//S_set2中有的是增加的
                     if(!S_set2.isEmpty()){
                    	 Iterator<String> it = S_set2.iterator();
                    	 while(it.hasNext()){
                    		 System.out.println("写入2");
   						  writer.write("\"【file2】【增加的code smell】\""+it.next()+"\r\n");
   					  }
    				  }
    				  
    				  
    			  }else{
    				  System.out.println("【2.1.2】");
    				// 【2.1.2】不一样
                      //检测 reader_file2中的File下的String是否在set2中 记录【增加的code semll】+行信息
    				  writer.write("\"【file2】【增加的code smell】\""+str2+"\r\n");
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
     
     //展示所有数据
     public void showAll(){
    	 System.out.println();
    	 System.out.println();
    	 System.out.println("调用showall方法");
    	 Iterator<String> it = N_instersection_set1.iterator(); 
    	 Iterator<String> it2 = N_instersection_set2.iterator();
    	 Iterator<String> it3 = result.iterator();
    	 Iterator<String> it4=Instersection.iterator();
    	 
    	 System.out.println("N_instersection_set1中的元素【set1中特有的元素】");
    	 while (it.hasNext()) {  
    	   System.out.println(it.next());
    	 }
    	 
    	 System.out.println("N_instersection_set2中的元素【set2特有的元素】");
    	 while (it2.hasNext()) {  
      	   System.out.println(it2.next().toString());
      	 }
    	 
    	 System.out.println("result的元素【set1+set2】");
    	 while (it3.hasNext()) {  
      	   System.out.println(it3.next().toString());
      	 }
    	 
//    	 System.out.println("Instersection中的元素【set1并set2】");
//    	 while (it4.hasNext()) {  
//      	   System.out.println(it4.next().toString());
//      	 }
    	 System.out.println("####"+set1.size());
    	 System.out.println("####"+set2.size());
    	 System.out.println(Instersection.size());
    	 
     }
     
     //关闭工作
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
		//junit4新工程  1-28
		//commons-io新工程 1-25
		//C://Users/dell/Desktop/commons-ionot/
		//E://commons-io/
		String path = "E://junit4not/";//拼接串1
		String filename="/pmd-final.txt";//拼接串2
		
		
//		String p1=path+23+"/pmd-report-1.csv";
//		String p2=path+23+"/pmd-report-2.csv";
//		String p3=path+23+filename;
//		compare_excel e=new compare_excel();
//		e.pre_file(p1,p2,p3);
//		e.initset();
//		e.showAll();
//		e.dosth();
//		System.out.println("处理完毕"+i);
		
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
		System.out.println("处理完毕"+i);		
		e.final_actions();
	}
	}

}
