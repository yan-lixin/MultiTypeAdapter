package com.multitypeadapter.library.manager;

import android.app.Activity;
import android.view.View;

import com.multitypeadapter.library.annotation.ContentView;
import com.multitypeadapter.library.annotation.EventBase;
import com.multitypeadapter.library.annotation.InjectView;
import com.multitypeadapter.library.listener.ListenerInvocationHandler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Copyright (c), 2018-2019
 *
 * @author: lixin
 * Date: 2019/3/25
 * Description:
 */
public class InjectManager {


    public static void inject(Activity activity) {
        //布局注入
        injectLayout(activity);
        //控件注入
        injectView(activity);
        //事件注入
        injectEvents(activity);
    }

    /**
     * 布局注入
     * @param activity
     */
    private static void injectLayout(Activity activity) {
        // 获取类
        Class<? extends Activity> clazz = activity.getClass();
        // 获取类的注解
        ContentView contentView = clazz.getAnnotation(ContentView.class);
        if (contentView != null) {
            // 获取注解的值 (R.layout.xx)
            int layoutId = contentView.value();
            try {
                // activity.setContentView(layoutId)

                // 获取指定的方法 (setContentView) 值为int类型
                Method method = clazz.getMethod("setContentView", int.class);
                //执行方法
                method.invoke(activity, layoutId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 控件注入
     * @param activity
     */
    private static void injectView(Activity activity) {
        // 获取类
        Class<? extends Activity> clazz = activity.getClass();
        // 获取当前类的所有属性
        Field[] fields = clazz.getDeclaredFields();
        // 获取每个属性
        for (Field field: fields) {
            //获取属性上的注解
            InjectView injectView = field.getAnnotation(InjectView.class);
            //获取注解的值
            if (injectView != null) { //并不是每个属性都有注解
                int viewId = injectView.value();

                // 获取指定方法 (findViewById())
                try {
                    Method method = clazz.getMethod("findViewById", int.class);
                    // findViewById返回值为 <T extends View>
                    Object view = method.invoke(activity, viewId);
                    //view = activity.findViewById(viewId);

                    //设置访问权限
                    field.setAccessible(true);
                    //属性的值赋给控件，在当前Activity
                    field.set(activity, view);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 事件注入
     * @param activity
     */
    public static void injectEvents(Activity activity) {
        // 获取类
        Class<? extends Activity> clazz = activity.getClass();
        //获取当前类的所有方法
        Method[] methods = clazz.getDeclaredMethods();
        //遍历方法
        for (Method method: methods) {
            //获取每个方法的注解 (OnClick可能对应多个控件id)
            Annotation[] annotations = method.getAnnotations();
            //遍历注解
            for (Annotation annotation: annotations) {
                //获取OnClick注解上的注解类型
                Class<? extends Annotation> annotationType = annotation.annotationType();
                if (annotationType != null) {
                    //通过EventBase指定获取
                    EventBase eventBase = annotationType.getAnnotation(EventBase.class);
                    if (eventBase != null) { //有的方法没有EventBase注解
                        //事件三大成员
                        String listenerSetter = eventBase.listenerSetter();
                        Class<?> listenerType = eventBase.listenerType();
                        String callbackListener = eventBase.callbackListener();

                        //获取注解的值
                        try {
                            //通过annotationType获取onClick注解的value值
                            Method valueMethod = annotationType.getDeclaredMethod("value");
                            //执行value方法获取注解的值
                            int[] viewIds = (int[]) valueMethod.invoke(annotation);

                            //动态代理
                            //拦截方法
                            //得到监听的代理对象(新建代理单例、类的加载器，指定要代理的对象类的类型、class实例)
                            ListenerInvocationHandler handler = new ListenerInvocationHandler(activity);
                            handler.addMethod(callbackListener, method);
                            //监听对象的代理对象
                            //ClassLoader loader：指定当前目标对象使用类加载器，获取加载器的方式是固定的
                            //Class<?>[] interfaces: 目标对象实现的接口类型，使用泛型方式确认类型
                            //InvocationHandler h: 事件处理，执行目标对象的方法时，会触发事件处理器的方法
                            Object listener = Proxy.newProxyInstance(listenerType.getClassLoader(),
                                    new Class[]{listenerType}, handler);
                            //遍历注解的值
                            for (int viewId: viewIds) {
                                //获取当前Activity的view
                                View view = activity.findViewById(viewId);
                                //获取指定的方法
                                Method setter = view.getClass().getMethod(listenerSetter, listenerType);
                                //执行方法
                                setter.invoke(view, listener);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
