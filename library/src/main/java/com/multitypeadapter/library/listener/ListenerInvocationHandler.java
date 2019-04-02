package com.multitypeadapter.library.listener;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Copyright (c), 2018-2019
 *
 * @author: lixin
 * Date: 2019/3/25
 * Description: 将回调的onClick方法拦截，执行自定义的方法
 */
public class ListenerInvocationHandler implements InvocationHandler {

    //需要拦截的对象
    private Object target;
    //需要拦截的对象键值对
    private HashMap<String, Method> methodHashMap = new HashMap<>();

    public ListenerInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (target != null) {
            //获取需要拦截的方法名
            String methodName = method.getName();
            //重新赋值，将onClick换为onShow
            method = methodHashMap.get(methodName);
            if (method != null) {
                return method.invoke(target, args);
            }
        }
        return null;
    }

    /**
     * 将需要拦截的方法添加
     * @param methodName 需要拦截的方法 比如：onClick()
     * @param method 执行自定义方法 比如：onShow()
     */
    public void addMethod(String methodName, Method method) {
        methodHashMap.put(methodName, method);
    }
}
