package com.multitypeadapter.library.manager

import android.support.v4.util.SparseArrayCompat
import com.multitypeadapter.library.holder.RViewHolder
import com.multitypeadapter.library.listener.RViewItem

/**
 * Copyright (c), 2018-2019
 * @author: lixin
 * Date: 2019/4/1
 * Description:
 */
class RViewItemManager<T> {

    /**
     * key: viewType
     * value: RViewItem
     */
    private val styles: SparseArrayCompat<RViewItem<T>> = SparseArrayCompat()

    /**
     * 获取所有item样式总共有多少种
     */
    fun getItemViewStylesCount(): Int {
        return styles.size()
    }

    /**
     * 加入新的item样式（每次加入放置末尾）
     */
    fun addStyles(item: RViewItem<T>?) {
        if (item != null) {
            styles.put(styles.size(), item)
        }
    }

    fun getItemViewType(entity: T, position: Int): Int {
        //样式倒序循环，避免增删集合抛出异常
        for (i in styles.size() - 1 downTo 0) {
            val item: RViewItem<T> = styles.valueAt(i)
            //是否为当前样式
            if (item.isItemView(entity, position)) {
                return styles.keyAt(i)
            }
        }
        throw IllegalAccessException("位置: $position, 该item没有匹配的RViewItem类型")
    }

    /**
     * 根据显示的ViewType返回RViewItem对象
     */
    fun getRViewItem(viewType: Int): RViewItem<T>? {
        return styles.get(viewType)
    }

    /**
     * 视图与数据源绑定显示
     */
    fun convert(holder: RViewHolder, entity: T, position: Int) {
        for (i in 0 until styles.size()) {
            val item: RViewItem<T> = styles.valueAt(i)
            if (item.isItemView(entity, position)) {
                //视图与数据绑定显示
                item.convert(holder, entity, position)
                return
            }
        }
        throw IllegalAccessException("位置: $position, 该item没有匹配的RViewItem类型")
    }

}