package com.test;
import java.util.ArrayList;
import java.util.HashMap;


public class testHashMap {
	
	/**
	 * ��������
	 * java��ʵ��1�Զ����� ʹ��HashMap keyΪ��ͨ���ͣ� ����value��һ��ArrayList
	 * */
	public static void main(String[] args) {
		HashMap<String, ArrayList<String>> map=new HashMap<String, ArrayList<String>>();

		System.out.println(map.keySet());
		System.out.println(map.values());
	}

}
