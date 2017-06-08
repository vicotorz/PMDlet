package com.test;
import java.util.HashSet;
import java.util.Iterator;


public class test {
	public static void main(String[] args) {
		HashSet<String> set=new HashSet<String>();
		set.add("1");
		set.add("2");
		set.add("3");
		Iterator<String> iterator=set.iterator();
		while(iterator.hasNext()){
			System.out.println(iterator.next());
		}
		
		String s="1883,23#&2,3";
		System.out.println(s.indexOf(","));
		System.out.println(s.substring(s.indexOf(",")+1));
		
		
		String[][] contents={{"1","2","3"},{"4","5","6"},{"4","5","6"}};
		System.out.println(contents.length);
		
		String str="重命名: src/test/java/com/alibaba/json/bvt/parser/deser/asm/TestASM.java (从 src/test/java/com/alibaba/json/bvt/parser/deser/TestASM.java)";
		String string="重命名: src/test/java/com/alibaba/json/bvt/parser/deser/asm/TestASM.java";
		
//		String ss="已修改: src/main/java/com/alibaba/fastjson/parser/ParserConfig.java" +
//				"已修改: src/main/java/com/alibaba/fastjson/parser/deserializer/ASMDeserializerFactory.java
//已修改: src/main/java/com/alibaba/fastjson/parser/deserializer/JavaBeanDeserializer.java
//已修改: src/test/java/com/alibaba/json/bvt/parser/deser/TestASM_primitive.java";
		
		String ss="【file1】【減少的code smell】,com.alibaba.fastjson.serializer,src/main/java/com/alibaba/fastjson/serializer/SerializeConfig.java,3,297,Avoid excessively long variable names like javaBeanSerializer,Naming Rules,LongVariable";
		
		String teststr="【file1】【減少的code smell】    ,com.alibaba.json.bvt.support.spring.data,src/test/java/com/alibaba/json/bvt/support/spring/data/PageToJSONTest.java,3,16,A method/constructor shouldn't explicitly throw java.lang.Exception,Strict Exception Rules,SignatureDeclareThrowsException";
		
		String tt="\"com.alibaba.fastjson.parser.deserializer\"";
		
		System.out.println(teststr.contains(" "));
		
		System.out.println("!!!!!!!"+tt);
		
		System.out.println(tt.contains("\""));
		
		String[] g=ss.split(",");
		
		for(int i=0;i<g.length;i++){
			System.out.println(g[i]);
		}
		
		
		System.out.println(string.indexOf("("));
		if(str.indexOf("(")!=-1){
		System.out.println(str.substring(str.indexOf(" ")+1, str.indexOf("(")));
			}else{
				System.out.println(string.substring(str.indexOf(" ")+1));
			}
		
	}

}
