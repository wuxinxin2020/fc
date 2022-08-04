package com.yogovi.proxy.test;

import com.yogovi.proxy.Rent;
import com.yogovi.proxy.handler.HostInvocationHandler;
import com.yogovi.proxy.impl.Host;

public class HostProxyTest {

	public static void main(String[] args) {
		Host host = new Host();
		HostInvocationHandler hih = new HostInvocationHandler();
		hih.setTarget(host);
		Rent rent = (Rent) hih.getProxy();
		rent.rent();
	}
	
}
