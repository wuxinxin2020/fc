package com.yogovi.proxy.handler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class HostInvocationHandler implements InvocationHandler {
	
	private Object target;
	
	public void setTarget(Object target) {
		this.target = target;
	}
	
	public Object getProxy() {
		return Proxy.newProxyInstance(target.getClass().getClassLoader(), 
				target.getClass().getInterfaces(), this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("看房");
		Object result = method.invoke(target, args);
		System.out.println("签合同");
		System.out.println("收中介费");
		return result;
	}

}
