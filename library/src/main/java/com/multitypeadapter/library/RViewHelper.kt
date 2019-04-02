package com.multitypeadapter.library

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.multitypeadapter.library.base.RView
import com.multitypeadapter.library.base.RViewAdapter
import com.multitypeadapter.library.listener.RViewCreate

/**
 * Copyright (c), 2018-2019
 * @author: lixin
 * Date: 2019/4/1
 * Description:
 */
class RViewHelper<T> private constructor(builder: Builder<T>) {

    /**
     * 下拉控件
     */
    private var swipeRefreshLayout: SwipeRefreshLayout? = null

    /**
     * 下拉刷新工具类
     */
    private var swipeRefreshHelper: SwipeRefreshHelper? = null

    /**
     * RecyclerView
     */
    private var recyclerView: RView

    /**
     * 适配器
     */
    private var adapter: RViewAdapter<T>

    /**
     * 布局管理
     */
    private var layoutManager: RecyclerView.LayoutManager

    /**
     * 分割线
     */
    private var itemDecoration: RecyclerView.ItemDecoration? = null

    /**
     * 开始页码
     */
    private var startPageNumber: Int = 0

    /**
     * 是否支持加载更多
     */
    private var isSupportPaging: Boolean = false

    /**
     * 下拉刷新监听
     */
    private var listener: SwipeRefreshHelper.SwipeRefreshListener? = null

    /**
     * 当前页数
     */
    private var currentPageNum: Int = 0

    init {
        this.swipeRefreshLayout = builder.create.createSwipeRefresh()
        this.recyclerView = builder.create.createRecyclerView()
        this.adapter = builder.create.createRecyclerViewAdapter()
        this.layoutManager = builder.create.createLayoutManager()
        this.itemDecoration = builder.create.createItemDecoration()
        this.startPageNumber = builder.create.startPageNumber()
        this.isSupportPaging = builder.create.isSupportPaging()
        this.listener = builder.listener

        this.currentPageNum = this.startPageNumber
        val colorRes: IntArray? = builder.create.colorRes()
        if (swipeRefreshLayout != null) {
            swipeRefreshHelper = if (colorRes != null) {
                SwipeRefreshHelper.createSwipeRefreshHelper(swipeRefreshLayout!!, null)
            } else {
                SwipeRefreshHelper.createSwipeRefreshHelper(swipeRefreshLayout!!, colorRes)
            }
        }
        init()
    }

    private fun init() {
        //RecyclerView初始化
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()

        if (itemDecoration != null) {
            recyclerView.addItemDecoration(itemDecoration!!)
        }

        //下拉刷新
        if (swipeRefreshHelper != null) {
            swipeRefreshHelper?.setSwipeRefreshListener(object : SwipeRefreshHelper.SwipeRefreshListener {
                override fun onRefresh() {
                    //重置页码
                    currentPageNum = startPageNumber
                    dismissSwipeRefresh()
                    if (listener != null) {
                        listener!!.onRefresh()
                    }
                }

            })
        }
    }

    /**
     * 关闭刷新动画
     */
    private fun dismissSwipeRefresh() {
        if (swipeRefreshLayout != null && swipeRefreshLayout!!.isRefreshing) {
            swipeRefreshLayout!!.isRefreshing = false
        }
    }

    fun notifyAdapterDataSetChanged(datas: MutableList<T>) {

        //首次加载或者下拉刷新后重置页码
        if (currentPageNum == startPageNumber) {
            adapter.updateDatas(datas)
        } else {
            adapter.addDatas(datas)
        }

        recyclerView.adapter = adapter

        if (isSupportPaging) {
            Log.e(">>", "暂无该功能")
        }
    }

    class Builder<T>(
        val create: RViewCreate<T>,
        val listener: SwipeRefreshHelper.SwipeRefreshListener?) {

        fun build(): RViewHelper<T> {
            return RViewHelper(this)
        }
    }
}