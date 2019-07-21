package com.jt;

public class Test01 {
	public static void main(String[] args) {
		String s1="aaads";
		String s4="aaads";
		String s2=new String("aaads");
		String s3=new String("aaads");
		System.out.println(s1 == s4);
		System.out.println(s1 == s2);
		System.out.println(s2 == s3);
		System.out.println(s1 == s2.intern());
	}

}
