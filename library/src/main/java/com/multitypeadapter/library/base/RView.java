package com.multitypeadapter.library.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Copyright (c), 2018-2019
 *
 * @author: lixin
 * Date: 2019/3/25
 * Description:
 */
public class RView extends RecyclerView {

    private RViewAdapter adapter;

    public RView(@NonNull Context context) {
        super(context);
    }

    public RView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setRViewAdapter(RViewAdapter adapter) {
        this.adapter = adapter;
    }

    /**
     * 条目单击监听接口
     * @param <T>
     */
    public interface OnItemClickListener<T> {
        void onItemClick(View view, T entity, int position);
    }

    /**
     * 条目长按监听接口
     * @param <T>
     */
    public interface OnItemLongClickListener<T> {
        boolean onItemLongClick(View view, T entity, int position);
    }

    /** 设置点击监听属性 */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        adapter.setOnItemClickListener(onItemClickListener);
    }

    /** 设置长按监听属性 */
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        adapter.setOnItemLongClickListener(onItemLongClickListener);
    }

}
