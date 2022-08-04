package com.yogovi;

public class Try {

	public static void main(String[] args) {
		System.out.println(value());
	}
	
	public static int value() {
		int i;
		try {
			i = 1;
			return i;
		} finally {
			i = 2;
			return i;
		}
	}
	
}
