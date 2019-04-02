package com.multitypeadapter.library.base

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.chaince.utils.extensions.clickWithTrigger
import com.multitypeadapter.library.holder.RViewHolder
import com.multitypeadapter.library.listener.RViewItem
import com.multitypeadapter.library.manager.RViewItemManager

/**
 * Copyright (c), 2018-2019
 * @author: lixin
 * Date: 2019/4/1
 * Description:
 */
abstract class RViewAdapter<T>(): RecyclerView.Adapter<RViewHolder>() {

    /**
     * 条目单击事件监听
     */
    private var onItemClickListener: RView.OnItemClickListener<T>? = null

    /**
     * 条目长按事件监听
     */
    private var onItemLongClickListener: RView.OnItemLongClickListener<T>? = null

    /**
     * item类型管理器
     */
    private var itemStyle: RViewItemManager<T>? = null

    private var datas: MutableList<T>? = null

    /**
     * 单样式
     */
    constructor(datas: MutableList<T>): this() {
        if (this.datas == null) {
            this.datas = ArrayList()
        } else {
            this.datas = datas
        }
        itemStyle = RViewItemManager()
    }

    /**
     * 多样式
     */
    constructor(datas: MutableList<T>, item: RViewItem<T>): this(datas) {
        if (this.datas == null) {
            this.datas = ArrayList()
        } else {
            this.datas = datas
        }
        itemStyle = RViewItemManager()
        addItemStyle(item)
    }

    /**
     * 将item类型加入
     */
    fun addItemStyle(item: RViewItem<T>) {
        itemStyle?.addStyles(item)
    }

    /**
     * 添加数据集合
     */
    fun addDatas(datas: MutableList<T>?) {
        if (datas == null) {
            return
        }
        this.datas?.addAll(datas)
        notifyDataSetChanged()
    }

    /**
     * 修改整个数据集合
     */
    fun updateDatas(datas: MutableList<T>?) {
        if (datas == null) {
            return
        }
        this.datas = datas
        notifyDataSetChanged()
    }

    /**
     * 是否包含多样式布局
     */
    private fun hasMultiStyle(): Boolean {
        return itemStyle?.getItemViewStylesCount()!! > 0
    }

    /**
     * 根据position获取当前Item的布局类型
     */
    override fun getItemViewType(position: Int): Int {
        if (hasMultiStyle()) {
            return itemStyle?.getItemViewType(datas?.get(position)!!, position)!!
        }
        return super.getItemViewType(position)
    }

    /**
     * 根据布局类型创建不同Item的ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RViewHolder {
        val item: RViewItem<T> = itemStyle?.getRViewItem(viewType)!!
        val layoutId: Int = item.getItemLayout()
        val holder = RViewHolder.createViewHolder(parent.context, parent, layoutId)

        //点击监听
        if (item.isOpenClick()) {
            setListener(holder)
        }
        return holder
    }

    /**
     * 将数据绑定到Item的ViewHolder上
     */
    override fun onBindViewHolder(holder: RViewHolder, position: Int) {
        convert(holder, datas!![position])
    }

    /**
     * 获取item总数
     */
    override fun getItemCount(): Int {
        return datas?.size ?: 0
    }

    /**
     * 添加不同类型Item布局
     */
    fun addItemStyles(item: RViewItem<T>) {
        itemStyle?.addStyles(item)
    }

    fun setOnItemClickListener(onItemClickListener: RView.OnItemClickListener<T>) {
        this.onItemClickListener = onItemClickListener
    }

    fun setOnItemLongClickListener(onItemLongClickListener: RView.OnItemLongClickListener<T>) {
        this.onItemLongClickListener = onItemLongClickListener
    }

    private fun setListener(holder: RViewHolder) {
        holder.getConvertView().clickWithTrigger {
            val position: Int = holder.adapterPosition
            this.onItemClickListener?.onItemClick(it, datas!![position], position)
        }

        holder.getConvertView().setOnLongClickListener(object : View.OnLongClickListener {
            override fun onLongClick(v: View?): Boolean {
                val position: Int = holder.adapterPosition
                this@RViewAdapter.onItemLongClickListener?.onItemLongClick(v!!, datas!![position], position)
                return false
            }

        })
    }

    private fun convert(holder: RViewHolder, entity: T) {
        itemStyle?.convert(holder, entity, holder.adapterPosition)
    }
}