package com.yogovi.proxy.test;

import com.yogovi.proxy.handler.ProxyFactory;
import com.yogovi.proxy.impl.Host;

public class CglibTest {

	public static void main(String[] args) {
		ProxyFactory<Host> factory = new ProxyFactory<Host>(new Host());
		Host proxy = factory.getProxy();
		proxy.rent();
		proxy.furnish();
	}
	
}
