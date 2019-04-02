package com.multitypeadapter.sample.adapter

import com.multitypeadapter.library.base.RViewAdapter
import com.multitypeadapter.sample.entity.UserInfo

class MultiAdapter(datas: MutableList<UserInfo>) : RViewAdapter<UserInfo>(datas) {

    init {
        addItemStyles(AItem())
        addItemStyles(BItem())
        addItemStyles(CItem())
        addItemStyles(DItem())
        addItemStyles(EItem())
    }
}
