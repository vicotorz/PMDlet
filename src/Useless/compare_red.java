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
 * 从【修改的信息】中抽取
 * src/test/java/com/alibaba/json/bvt/parser/deser/asm/TestASM_double.java
 * 
 * 字符串匹配后，然后标红
 * 
 * 读取到final中的信息，抽取
 * 
 * */
// 第二个，分隔
// 【file2】【增加的code smell】
// ,com.alibaba.fastjson.serializer,src/main/java/com/alibaba/fastjson/serializer/SerializeConfig.java,
//3,452,"A method should have only one exit point, and that should be the last statement in the method",Controversial
// Rules,OnlyOneReturn

// 【file1】【減少的code smell】
// ,com.alibaba.json.bvt.support.spring.data,src/test/java/com/alibaba/json/bvt/support/spring/data/PageToJSONTest.java,3,16,A
// method/constructor shouldn't explicitly throw java.lang.Exception,Strict
// Exception Rules,SignatureDeclareThrowsException
public class compare_red {
	//121 28 25
	//fastjson   junit4  commons-io
	final int Total_number=28;

	public final String path = "E://junit4not/";//拼接串1
	public final String filename="/pmd-final-new.txt";//拼接串2
	
	public final String path_original = "D://junit4not.csv";//csv串
	
	//需要每个文件里都有useful_version文件
	public final String useful_versions="E://junit4not/junit4not_useful_version.txt";
	//String file_path = "D://commons-io-R.txt";//所有有关的版本截取--主要用于得到数目
	public final String file_outpath = "E://junit4not/junit4not.csv";//最终输出的文件
	
	ArrayList<String[]> list;

	public void file_operation() {
		try {
			
			//
			list = new ArrayList<String[]>();
			CsvReader reader = new CsvReader(path_original, ',',
					Charset.forName("GBK"));

			// 跳过表头
			reader.readHeaders();
			while (reader.readRecord()) {
				list.add(reader.getValues());
			}
			reader.close();

			for (int row = 0; row < Total_number; row++) {
				
				HashSet<String> s = new HashSet<String>();//Set  集合   csv中原有的修改信息

				//System.out.println(list.get(row)[6]);//【1】
				
				String[] str = list.get(row)[6].split("\n");
				
				//System.out.println(str.length);//【2】
				
				for (int index = 0; index < str.length; index++) {
					s.add(substring(str[index]));
				}
				
				//System.out.println("s集合加载完毕--"+s.size());//【3】
				
				
				//拼接路径
				String finPath=path+String.valueOf(row+1)+filename;
				
				//System.out.println(finPath);
				
				File f = new File(finPath);
				
				BufferedReader brd=new BufferedReader(new FileReader(f));
				
				//csv一个方框中的信息就和pmd-final中一个文件对应
				
				String st=brd.readLine();
				
				HashSet<String>  AllSet=new HashSet<String>();//所有信息的集合
				HashSet<String>  deleteSet=new HashSet<String>();//完全消除的信息  需要判断时候含“    ”(只要有空格就行)
				HashSet<String>  interactionset=new HashSet<String>();//交集集合
				
				while(st!=null){
					//System.out.println("*****"+st);
					//Scanner sc=new Scanner(System.in);
					//sc.nextLine();
					
					//System.out.println(st);
					//如果不是空行
					if(!st.equals("")){
					//处理子串 
					if(st.contains("【減少的code smell】    ,")){
						//完全消除的情况
						deleteSet.add(substr(st));
						
					}else{
						
						//普通的情况
						AllSet.add(substr(st));
					}
				
					}
					st=brd.readLine();
				}
				
				System.out.println("开始计算补集合");
				
				//两个Set进行对比（取交集）
			    //s和 AllSet交集
				System.out.println(s.size());
				System.out.println(AllSet.size());

				//Scanner sc=new Scanner(System.in);
				//sc.nextLine();
				
				interactionset.addAll(s);
				interactionset.retainAll(AllSet);
				
				AllSet.removeAll(interactionset);
				
				System.out.println("检查interactionset集合中的元素");
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
				
				
				
				//写入
				//1.interactionset是正常修改过的集合//对内容进行标红
				//2.deleteset是完全消除的集合//对内容进行标蓝
				
				//3.剩下补集AllSet写入//正常写入
				//对csv中的第八列进行填写
				
				CsvWriter wr =new CsvWriter(path_original,',',Charset.forName("GBK"));
				
				//拼接第八行需要添加的内容
				StringBuffer record=new StringBuffer();
				
				Iterator<String> it = deleteSet.iterator();
				Iterator<String> it2 = interactionset.iterator();
				Iterator<String> it3 = AllSet.iterator();
				
				System.out.println("开始拼接修改信息");
				
				while(it.hasNext()){
					record.append(it.next()+"【完全去除】");
					record.append("\r\n");
				}
				
				record.append("\r\n");
				
				while(it2.hasNext()){
					record.append(it2.next()+"【与修改一致】");
					record.append("\r\n");
				}
				
				record.append("\r\n");
				
				while(it3.hasNext()){
					record.append(it3.next()+"【搞不清楚】");
					record.append("\r\n");
				}
				
				System.out.println("拼接完毕");
				
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
//				if(line.startsWith("版本: ")){
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
		//将list中的信息重新输入到新文件中
	    
		CsvWriter wr = new CsvWriter(file_outpath, ',',
				Charset.forName("GBK"));
		String[][] contents=new String[Total_number][7];//get_total_num()
		String[] header = { "序号", "前一个版本", "当前版本", "作者", "日期", "作者标注信息",
				"修改的信息", "前一个版本修改信息","具体变化的信息", };//9个信息
		
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
	
	//对pmd-final信息进行筛选
	public String substr(String str){
		//文件形式
		// 【file2】【增加的code smell】
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
		// " " 和 "(" 之间的片段
		String string;
		if (str.indexOf("(") != -1) {
			//如果没有后面的括号
			string = str.substring(str.indexOf(" ") + 1, str.indexOf("("));
		} else {
			string = str.substring(str.indexOf(" ") + 1);
		}
		
		return string;
	}
	
	public void writeuseful_versions(){
		System.out.println("记录有用的版本信息");
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
