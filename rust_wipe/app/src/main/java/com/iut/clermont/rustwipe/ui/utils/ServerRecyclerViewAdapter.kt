package com.iut.clermont.rustwipe.ui.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iut.clermont.rustwipe.database.data.Server
import com.iut.clermont.rustwipe.databinding.ItemListServerBinding

class ServerRecyclerViewAdapter(private val listener: Callbacks) :
    ListAdapter<Server, ServerRecyclerViewAdapter.ServerViewHolder>(DiffUtilServerCallback) {

    private object DiffUtilServerCallback : DiffUtil.ItemCallback<Server>() {
        override fun areItemsTheSame(oldItem: Server, newItem: Server) = oldItem.serverId == newItem.serverId
        override fun areContentsTheSame(oldItem: Server, newItem: Server) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ServerViewHolder(
            ItemListServerBinding.inflate(LayoutInflater.from(parent.context)), listener
        )

    override fun onBindViewHolder(holder: ServerViewHolder, position: Int) =
        holder.bind(getItem(position))


    class ServerViewHolder(private val binding: ItemListServerBinding, listener: Callbacks) :
        RecyclerView.ViewHolder(binding.root) {

        val server: Server? get() = binding.server

        init {
            itemView.setOnClickListener { server?.let { listener.onServerSelected(it.serverId) } }
        }

        fun bind(server: Server) {
            binding.server = server
            binding.executePendingBindings()
        }
    }

    interface Callbacks {
        fun onServerSelected(serverId: Long)
    }
}