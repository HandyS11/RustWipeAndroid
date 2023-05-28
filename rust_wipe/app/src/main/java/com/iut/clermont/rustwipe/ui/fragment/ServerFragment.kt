package com.iut.clermont.rustwipe.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.iut.clermont.rustwipe.R
import com.iut.clermont.rustwipe.database.data.NEW_SERVER_ID
import com.iut.clermont.rustwipe.databinding.FragmentServerBinding
import com.iut.clermont.rustwipe.ui.utils.viewModelFactory
import com.iut.clermont.rustwipe.viewmodel.ServerViewModel

class ServerFragment : Fragment() {

    companion object {
        private const val EXTRA_SERVER_ID = "com.iut.clermont.rustwipe.extra_serverid"

        fun newInstance(serverId: Long) = ServerFragment().apply {
            arguments = bundleOf(EXTRA_SERVER_ID to serverId)
        }
    }

    private var serverId: Long = NEW_SERVER_ID
    private lateinit var serverVM: ServerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        serverId = savedInstanceState?.getLong(EXTRA_SERVER_ID) ?: arguments?.getLong(EXTRA_SERVER_ID)
                ?: NEW_SERVER_ID

        serverVM = ViewModelProvider(this, viewModelFactory { ServerViewModel(serverId) }).get()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(EXTRA_SERVER_ID, serverId)
    }

    private lateinit var imageView: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding = FragmentServerBinding.inflate(inflater)
        binding.serverVM = serverVM
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    private fun setupMenu() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onPrepareMenu(menu: Menu) {
                super.onPrepareMenu(menu)
                if (serverId == NEW_SERVER_ID) {
                    menu.findItem(R.id.action_delete)?.isVisible = false
                }
            }

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.fragment_server, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_save -> {
                        saveServer()
                        true
                    }
                    R.id.action_delete -> {
                        deleteServer()
                        true
                    }
                    else -> false
                }
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMenu()

        imageView = requireView().findViewById(R.id.image_view)
        imageView.setImageBitmap(serverVM.getImage())
    }

    private fun saveServer() {
        serverVM.saveServer()
        listener?.onServerSaved()
    }

    private fun deleteServer() {
        if (serverId != NEW_SERVER_ID) {
            serverVM.deleteServer()
            listener?.onServerDeleted()
        }
    }

    interface OnInteractionListener {
        fun onServerSaved()
        fun onServerDeleted()
    }

    private var listener: OnInteractionListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}