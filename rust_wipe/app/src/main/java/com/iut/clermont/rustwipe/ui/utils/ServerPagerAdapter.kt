package com.iut.clermont.rustwipe.ui.utils

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.iut.clermont.rustwipe.database.data.NEW_SERVER_ID
import com.iut.clermont.rustwipe.database.data.Server
import com.iut.clermont.rustwipe.ui.fragment.ServerFragment

class ServerPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    private var serverList = listOf<Server>()

    override fun getItemCount() = serverList.size

    override fun createFragment(position: Int) = ServerFragment.newInstance(serverList[position].serverId)

    fun positionFromId(serverId: Long) = serverList.indexOfFirst { it.serverId == serverId }

    fun serverIdAt(position: Int) = if (serverList.isEmpty()) NEW_SERVER_ID else serverList[position].serverId

    fun submitList(serverList: List<Server>) {
        this.serverList = serverList
        notifyDataSetChanged()
    }
}