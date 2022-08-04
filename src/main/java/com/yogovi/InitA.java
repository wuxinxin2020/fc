package com.yogovi;

public class InitA {

	public static void main(String[] args) {
		System.out.println(A.NUM1);
	}

}

interface A {
	static Thread t = new Thread() {
		{
			System.out.println("init....");
		}
	};
	static int NUM1 = Integer.valueOf(1);
}
