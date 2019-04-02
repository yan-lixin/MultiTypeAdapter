package com.multitypeadapter.sample.base

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.multitypeadapter.library.RViewHelper
import com.multitypeadapter.library.SwipeRefreshHelper
import com.multitypeadapter.library.base.RView
import com.multitypeadapter.library.base.RViewAdapter
import com.multitypeadapter.library.listener.RViewCreate
import com.multitypeadapter.library.manager.InjectManager
import com.multitypeadapter.sample.R

/**
 * Copyright (c), 2018-2019
 * @author: lixin
 * Date: 2019/4/1
 * Description:
 */
abstract class BaseRViewActivity<T>: AppCompatActivity(), RViewCreate<T>, SwipeRefreshHelper.SwipeRefreshListener {

    protected lateinit var helper: RViewHelper<T>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        InjectManager.inject(this)
        helper = RViewHelper.Builder(this, this).build()
    }

    override fun createSwipeRefresh(): SwipeRefreshLayout? {
        return findViewById(R.id.swipeRefreshLayout)
    }

    override fun colorRes(): IntArray? {
        return IntArray(0)
    }

    override fun createRecyclerView(): RView {
        return findViewById(R.id.recyclerView)
    }

    override fun createLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(this)
    }

    override fun createItemDecoration(): RecyclerView.ItemDecoration? {
        return DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
    }

    override fun startPageNumber(): Int {
        return 1
    }

    override fun isSupportPaging(): Boolean {
        return false
    }

    protected fun notifyAdapterDataSetChanged(datas: MutableList<T>) {
        helper.notifyAdapterDataSetChanged(datas)
    }
}