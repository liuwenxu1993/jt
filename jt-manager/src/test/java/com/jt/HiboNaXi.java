package com.jt;

public class HiboNaXi {
	private static Integer start=1;
	private static Integer last=1;
	private static Integer result=1;
	
	public static Integer getResult(Integer num) {
		while (num>2) {
			result=start+last;
			start = last;
			last = result;
			num--;
		}
		return result;
	}

}
