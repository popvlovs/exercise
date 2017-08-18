package com.syt.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Think on 2017/8/13.
 */

interface IVehicle {
    void run();
}

class Vehicle implements IVehicle {
    public void run() {
        System.out.println(this.getClass().getName() + ": run~");
    }
}

class ProxyFactory implements InvocationHandler{

    private Object target;

    public ProxyFactory(Object target) {
        this.target = target;
    }

    public Object getProxy() {
        return Proxy.newProxyInstance(getClassLoader(), getInterfaces(), this);
    }

    private Class<?>[] getInterfaces() {
        return target.getClass().getInterfaces();
    }

    private ClassLoader getClassLoader() {
        return target.getClass().getClassLoader();
    }

    public static <T> T getProxy(Object target) {
        return (T)new ProxyFactory(target).getProxy();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Object result = null;

        if (method.getName().equals("run")) {
            System.out.println("Before run");

            result = method.invoke(target, args);

            System.out.println("After run");
        }

        return result;
    }
}

public class AutoProxy {

    public static void main(String[] args) {

        IVehicle vehicleProxy = ProxyFactory.getProxy(new Vehicle());
        vehicleProxy.run();
    }

}
