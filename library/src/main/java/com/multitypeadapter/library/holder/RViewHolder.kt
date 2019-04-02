package com.multitypeadapter.library.holder

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Copyright (c), 2018-2019
 * @author: lixin
 * Date: 2019/4/1
 * Description:
 */
class RViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    /**
     * 所有控件的集合
     * key: R.id.xx
     * value: TextView、ImageView...
     */
    private var mViews: SparseArray<View> = SparseArray()

    /**
     * 当前View对象
     */
    private var mConvertView: View = itemView

    companion object {

        /**
         * 创建RViewHolder对象
         */
        fun createViewHolder(context: Context, parent: ViewGroup, layoutId: Int): RViewHolder {
            val itemView = LayoutInflater.from(context).inflate(layoutId, parent, false)
            return RViewHolder(itemView)
        }

    }

    /**
     * 获取当前View
     */
    fun getConvertView(): View {
        return mConvertView
    }

    fun <T : View> getView(viewId: Int): T {
        var view: View? = mViews.get(viewId)
        if (view == null) {
            view = mConvertView.findViewById(viewId)
            mViews.put(viewId, view)
        }
        return view as T
    }
}