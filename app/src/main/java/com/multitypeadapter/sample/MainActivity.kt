package com.multitypeadapter.sample

import android.os.Bundle
import android.util.Log
import android.view.View
import com.multitypeadapter.library.annotation.ContentView
import com.multitypeadapter.library.annotation.OnItemClick
import com.multitypeadapter.library.annotation.OnItemLongClick
import com.multitypeadapter.library.base.RViewAdapter
import com.multitypeadapter.library.manager.InjectManager
import com.multitypeadapter.sample.adapter.MultiAdapter
import com.multitypeadapter.sample.base.BaseRViewActivity
import com.multitypeadapter.sample.entity.UserInfo
import kotlinx.android.synthetic.main.activity_recyclerview.*

@ContentView(R.layout.activity_recyclerview)
class MainActivity : BaseRViewActivity<UserInfo>() {

    private var datas: MutableList<UserInfo> = ArrayList()
    private lateinit var adapter: MultiAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
        InjectManager.injectEvents(this)
    }

    @OnItemClick(R.id.recyclerView)
    fun onItemClick(v: View, info: UserInfo, position: Int) {
        Log.e("TAG", "点击: $position")
    }

    @OnItemLongClick(R.id.recyclerView)
    fun onItemLongClick(v: View, info: UserInfo, position: Int): Boolean {
        Log.e("TAG", "长按: $position")
        return false
    }

    private fun initData() {

        Thread {
            if (datas.isEmpty()) {
                for (i in 0..14) {
                    for (j in 1..15) {
                        val user = UserInfo()
                        if (j % 15 == 1) {
                            user.type = 1
                            user.account = "第一种布局"
                        } else if (j % 15 == 2 || j % 15 == 3) {
                            user.type = 2
                            user.account = "第二种布局"
                        } else if (j % 15 == 4 || j % 15 == 5 || j % 15 == 6) {
                            user.type = 3
                            user.account = "第三种"
                        } else if (j % 15 == 7 || j % 15 == 8 || j % 13 == 9 || j % 15 == 10) {
                            user.type = 4
                            user.account = "第四种"
                        } else {
                            user.type = 5
                            user.account = "第五种"
                        }
                        datas.add(user)
                    }
                }
            }
            notifyAdapterDataSetChanged(datas)
        }.start()
    }

    override fun createRecyclerViewAdapter(): RViewAdapter<UserInfo> {
        adapter = MultiAdapter(datas)
        recyclerView.setRViewAdapter(adapter)
        return adapter
    }

    override fun onRefresh() {
        initData()
    }
}
