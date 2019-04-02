package com.multitypeadapter.sample.adapter

import android.widget.TextView

import com.multitypeadapter.library.holder.RViewHolder
import com.multitypeadapter.library.listener.RViewItem
import com.multitypeadapter.sample.R
import com.multitypeadapter.sample.entity.UserInfo

class AItem : RViewItem<UserInfo> {

    override fun getItemLayout(): Int {
        return R.layout.item_a
    }

    override fun isOpenClick(): Boolean {
        return true
    }

    override fun isItemView(entity: UserInfo, position: Int): Boolean {
        return entity.type == 1
    }

    override fun convert(holder: RViewHolder, entity: UserInfo, position: Int) {
        val textView = holder.getView<TextView>(R.id.mtv)
        textView.text = entity.account
    }
}
