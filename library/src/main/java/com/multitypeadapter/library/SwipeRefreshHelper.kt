package com.multitypeadapter.library

import android.annotation.SuppressLint
import android.support.annotation.ColorRes
import android.support.v4.widget.SwipeRefreshLayout

/**
 * Copyright (c), 2018-2019
 * @author: lixin
 * Date: 2019/4/1
 * Description: 下拉刷新的帮助类
 */
class SwipeRefreshHelper {

    private var swipeRefreshLayout: SwipeRefreshLayout

    private var swipeRefreshListener: SwipeRefreshListener? = null

    private constructor(swipeRefreshLayout: SwipeRefreshLayout, @ColorRes colorResIds: IntArray?) {
        this.swipeRefreshLayout = swipeRefreshLayout
        init(colorResIds)
    }

    @SuppressLint("ResourceAsColor")
    private fun init(@ColorRes colorResIds: IntArray?) {
        if (colorResIds == null) {
            swipeRefreshLayout.setColorSchemeColors(android.R.color.holo_orange_dark,
                android.R.color.holo_green_dark, android.R.color.holo_blue_dark)
        } else {
            swipeRefreshLayout.setColorSchemeColors(*colorResIds)
        }
        swipeRefreshLayout.setOnRefreshListener {
            if (swipeRefreshListener != null) {
                swipeRefreshListener!!.onRefresh()
            }
        }
    }

    fun setSwipeRefreshListener(swipeRefreshListener: SwipeRefreshListener) {
        this.swipeRefreshListener = swipeRefreshListener
    }

    companion object {
        fun createSwipeRefreshHelper(swipeRefreshLayout: SwipeRefreshLayout, @ColorRes colorResIds: IntArray?): SwipeRefreshHelper {
            return SwipeRefreshHelper(swipeRefreshLayout, colorResIds)
        }
    }

    interface SwipeRefreshListener {
        fun onRefresh()
    }
}