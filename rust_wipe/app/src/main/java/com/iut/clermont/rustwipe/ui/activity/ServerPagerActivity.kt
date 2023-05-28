package com.iut.clermont.rustwipe.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.iut.clermont.rustwipe.R
import com.iut.clermont.rustwipe.database.data.NEW_SERVER_ID
import com.iut.clermont.rustwipe.ui.fragment.ServerFragment
import com.iut.clermont.rustwipe.ui.utils.ServerPagerAdapter
import com.iut.clermont.rustwipe.viewmodel.ServerPagerViewModel

class ServerPagerActivity : AppCompatActivity(), ServerFragment.OnInteractionListener {

    companion object {
        private const val EXTRA_SERVER_ID = "com.iut.clermont.rustwipe.extra_server_id"

        fun getIntent(context: Context, serverId: Long) =
            Intent(context, ServerPagerActivity::class.java).apply {
                putExtra(EXTRA_SERVER_ID, serverId)
            }
    }

    private val pagerAdapter = ServerPagerAdapter(this)
    private val serverPagerVM by viewModels<ServerPagerViewModel>()
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_pager)

        setSupportActionBar(findViewById(R.id.toolbar_activity))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewPager = ViewPager2(this)
        viewPager.id = R.id.view_pager
        findViewById<LinearLayout>(R.id.pager_layout).addView(viewPager)

        viewPager.adapter = pagerAdapter
        serverPagerVM.currentServerId = savedInstanceState?.getLong(EXTRA_SERVER_ID)
            ?: intent.getLongExtra(EXTRA_SERVER_ID, NEW_SERVER_ID)

        serverPagerVM.serverList.observe(this) {
            pagerAdapter.submitList(it)
            var position = pagerAdapter.positionFromId(serverPagerVM.currentServerId)
            if (position == -1) {
                position = 0
            }
            viewPager.currentItem = position
            supportActionBar?.subtitle = getString(R.string.servers_subtitle_format, position + 1)
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                serverPagerVM.currentServerId = pagerAdapter.serverIdAt(position)
                supportActionBar?.subtitle = getString(R.string.servers_subtitle_format, position + 1)
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(EXTRA_SERVER_ID, serverPagerVM.currentServerId)
    }

    override fun onServerSaved() = finish()
    override fun onServerDeleted() = finish()
}