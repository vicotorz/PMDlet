package test;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class testt {
	public static void main(String[] args) {
		String tt="\"【file2】【增加的code smell】\",\"com.alibaba.json.bvt.serializer.filters\",\"src/test/java/com/alibaba/json/bvt/serializer/filters/AfterFilterTest3.java\",\"1\",\"12\",\"Method names should not 1,54646 contain underscores\",\"Naming Rules\",\"MethodNamingConventions\"";
		String yy="作者: 高铁 <shaojin.wensj@alibaba-inc.com>";
		String pt="ter345asdaewr";
		 Pattern pattern = Pattern.compile("[0-9]*");
		  Matcher matcher = pattern.matcher(pt);
		  boolean b= matcher.matches();
		  //当条件满足时，将返回true，否则返回false
		 System.out.println(b);
		pt=pt.replace("/^[0-9]*$/", "  ");
		System.out.println(pt);
		
		String subyy=yy.substring(4, yy.length());
		System.out.println(subyy);
		
		String[] t=tt.split("\",\"");
		for(int i=0;i<t.length;i++){
		System.out.println(t[i]);
		
		
		String ttttt="		<description>";
		System.out.println("^%$%%$%"+ttttt.length());
		String rt="<description>rer";
		 Pattern pattern1 = Pattern.compile("<description>[a-zA-Z]+");
		  Matcher matcher1 = pattern1.matcher(rt);
		  boolean b1= matcher1.matches();
		  System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"+b1);
		  
		  
		  ArrayList list = new ArrayList();       

	        list.add(1);

	        list.add(2);

	        list.add(3);

	        list.add(4);

	        list.add(5);        

	        List listsub = list.subList(0, 3);

	        System.out.println(listsub);
		  
		  
		}
		
		
		
	}
}
