package com.test;
import java.util.ArrayList;
import java.util.HashMap;


public class testHashMap {
	
	/**
	 * 问题解决：
	 * java中实现1对多的情况 使用HashMap key为普通类型， 但是value是一个ArrayList
	 * */
	public static void main(String[] args) {
		HashMap<String, ArrayList<String>> map=new HashMap<String, ArrayList<String>>();

		System.out.println(map.keySet());
		System.out.println(map.values());
	}

}
