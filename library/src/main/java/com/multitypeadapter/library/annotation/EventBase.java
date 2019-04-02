package com.multitypeadapter.library.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Copyright (c), 2018-2019
 *
 * @author: lixin
 * Date: 2019/3/25
 * Description:
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventBase {
    /**
     * button.setOnClickListener(new View.OnClickListener() {
     *     @Override
     *     public void onClick(View v) {
     *
     *     }
     * });
     */

    //set方法名 (setOnClickListener)
    String listenerSetter();

    //监听的对象 (View.OnClickListener.class)
    Class<?> listenerType();

    //回调方法 (onClick(View v))
    String callbackListener();

}
