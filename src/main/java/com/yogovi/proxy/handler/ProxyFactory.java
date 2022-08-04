package com.yogovi.proxy.handler;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class ProxyFactory<T> implements MethodInterceptor{
	
	private T obj;
	
	public ProxyFactory(T obj) {
		super();
		this.obj = obj;
	}
	
	@SuppressWarnings("unchecked")
	public T getProxy() {
		//1.创建Enhancer类对象,类似jdk动态代理中的Proxy,该类就是获取代理对象的
		Enhancer enhancer = new Enhancer();
		//2.设置代理类的父类字节码为obj,代理类继承obj
		enhancer.setSuperclass(obj.getClass());
		//3.设置回调函数
		enhancer.setCallback(this);
		//4.创建代理对象
		return (T) enhancer.create();
	}

	@Override
	public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
		if(method.getName().equals("rent")) {
			System.out.println("看房");
		}
		Object invoke = method.invoke(obj, objects);
		if(method.getName().equals("rent")) {
			System.out.println("签合同");
			System.out.println("收中介费");
		}
		return invoke;
	}

}
