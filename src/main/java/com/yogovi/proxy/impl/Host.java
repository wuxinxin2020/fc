package com.yogovi.proxy.impl;

import com.yogovi.proxy.Rent;

public class Host implements Rent{

	@Override
	public void rent() {
		System.out.println("房东出租房屋");
	}
	
	public int furnish() {
		System.out.println("装修");
		return 1;
	}

}
