package com.multitypeadapter.library.listener

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import com.multitypeadapter.library.base.RView
import com.multitypeadapter.library.base.RViewAdapter

/**
 * Copyright (c), 2018-2019
 * @author: lixin
 * Date: 2019/4/1
 * Description: 创建RViewHelper所需要的数据，它的实现类很方便创建RViewHelper对象
 */
interface RViewCreate<T> {

    /**
     * 创建SwipeRefreshLayout下拉
     */
    fun createSwipeRefresh(): SwipeRefreshLayout?

    /**
     * SwipeRefreshLayout下拉颜色
     */
    fun colorRes(): IntArray?

    /**
     * 创建RecyclerView
     */
    fun createRecyclerView(): RView

    /**
     * 创建RecyclerView.Adapter
     */
    fun createRecyclerViewAdapter(): RViewAdapter<T>

    /**
     * 创建LayoutManager
     */
    fun createLayoutManager(): RecyclerView.LayoutManager

    /**
     * 创建RecyclerView分割线
     */
    fun createItemDecoration(): RecyclerView.ItemDecoration?

    /**
     * 开始页码
     */
    fun startPageNumber(): Int

    /**
     * 是否支持分页
     */
    fun isSupportPaging(): Boolean
}