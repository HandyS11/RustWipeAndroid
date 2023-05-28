package com.iut.clermont.rustwipe.ui.activity

import android.os.Bundle
import android.widget.FrameLayout
import com.iut.clermont.rustwipe.R
import com.iut.clermont.rustwipe.ui.fragment.ServerFragment
import com.iut.clermont.rustwipe.ui.fragment.ServerListFragment

class ServerListActivity : SimpleFragmentActivity(), ServerListFragment.OnInteractionListener,
    ServerFragment.OnInteractionListener {

    private var isTwoPane: Boolean = false
    private lateinit var masterFragment: ServerListFragment

    override fun createFragment() = ServerListFragment().also { masterFragment = it }
    override fun getLayoutResId() = R.layout.toolbar_md_activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setIcon(R.mipmap.ic_launcher)

        isTwoPane = findViewById<FrameLayout>(R.id.container_fragment_detail) != null
        if (savedInstanceState != null)
            masterFragment = supportFragmentManager.findFragmentById(R.id.container_fragment) as ServerListFragment

        if (!isTwoPane) {
            removeDisplayedFragment()
        }
    }

    override fun onServerSelected(serverId: Long) {
        if (isTwoPane) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container_fragment_detail, ServerFragment.newInstance(serverId))
                .commit()
        }
        else {
            startActivity(ServerPagerActivity.getIntent(this, serverId))
        }
    }

    override fun onAddNewServer() { }

    private fun removeDisplayedFragment() {
        supportFragmentManager.findFragmentById(R.id.container_fragment_detail)?.let {
            supportFragmentManager.beginTransaction().remove(it).commit()
        }
    }

    override fun onServerSaved() { }

    override fun onServerDeleted() {
        if (isTwoPane) {
            removeDisplayedFragment()
        } else
            finish()
    }

    override fun onServerSwiped() {
        if (isTwoPane) {
            removeDisplayedFragment()
        }
    }
}