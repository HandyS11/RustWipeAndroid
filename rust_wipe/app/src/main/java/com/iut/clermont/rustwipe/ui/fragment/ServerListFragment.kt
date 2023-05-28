package com.iut.clermont.rustwipe.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.iut.clermont.rustwipe.R
import com.iut.clermont.rustwipe.databinding.FragmentListServerBinding
import com.iut.clermont.rustwipe.ui.utils.ServerRecyclerViewAdapter
import com.iut.clermont.rustwipe.viewmodel.ServerListViewModel

class ServerListFragment : Fragment(), ServerRecyclerViewAdapter.Callbacks {

    private val serverListAdapter = ServerRecyclerViewAdapter(this)
    private val serverListVM by viewModels<ServerListViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View {
        val binding = FragmentListServerBinding.inflate(inflater)
        binding.serverListVM = serverListVM
        binding.lifecycleOwner = viewLifecycleOwner
        with(binding.recyclerView) {
            adapter = serverListAdapter
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMenu()

        serverListVM.serverList.observe(viewLifecycleOwner) {
            serverListAdapter.submitList(it)
        }
    }

    private fun setupMenu() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.fragment_list_server, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.menu_item_new_server -> {
                        addNewServer()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun addNewServer() {
        listener?.onAddNewServer()
    }

    override fun onServerSelected(serverId: Long) {
        listener?.onServerSelected(serverId)
    }

    private inner class ServerListItemTouchHelper : ItemTouchHelper.Callback() {
        override fun isLongPressDragEnabled() = false

        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) =
            makeMovementFlags(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                ItemTouchHelper.END
            )

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                            target: RecyclerView.ViewHolder) = false

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            (viewHolder as ServerRecyclerViewAdapter.ServerViewHolder).server?.also {
                serverListVM.delete(it)
                listener?.onServerSwiped()
            }
        }
    }

    interface OnInteractionListener {
        fun onServerSelected(serverId: Long)
        fun onAddNewServer()
        fun onServerSwiped()
    }

    private var listener: OnInteractionListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnInteractionListener) {
            listener = context
        }
        else {
            throw RuntimeException("$context must implement OnInteractionListener!")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}