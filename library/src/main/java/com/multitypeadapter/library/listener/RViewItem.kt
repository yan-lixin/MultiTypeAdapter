package com.multitypeadapter.library.listener

import com.multitypeadapter.library.holder.RViewHolder

/**
 * Copyright (c), 2018-2019
 * @author: lixin
 * Date: 2019/4/1
 * Description: 某一类的item对象
 */
interface RViewItem<T> {

    /**
     * 获取item的布局
     */
    fun getItemLayout(): Int

    /**
     * 是否开启点击监听
     */
    fun isOpenClick(): Boolean

    /**
     * 是否为当前的item布局
     */
    fun isItemView(entity: T, position: Int): Boolean

    /**
     * 将item的控件与需要显示的数据绑定
     */
    fun convert(holder: RViewHolder, entity: T, position: Int)

}