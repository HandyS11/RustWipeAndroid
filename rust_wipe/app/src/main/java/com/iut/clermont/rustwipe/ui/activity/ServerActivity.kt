package com.iut.clermont.rustwipe.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.iut.clermont.rustwipe.R
import com.iut.clermont.rustwipe.database.data.NEW_SERVER_ID
import com.iut.clermont.rustwipe.ui.fragment.ServerFragment

class ServerActivity : SimpleFragmentActivity() {

    companion object {
        private const val EXTRA_SERVER_ID = "com.iut.rustwipe.extra_server_id"

        fun getIntent(context: Context, serverId: Long) =
            Intent(context, ServerActivity::class.java).apply {
                putExtra(EXTRA_SERVER_ID, serverId)
            }
    }

    private var serverId = NEW_SERVER_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        serverId = intent.getLongExtra(EXTRA_SERVER_ID, NEW_SERVER_ID)
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun createFragment() = ServerFragment.newInstance(serverId)
    override fun getLayoutResId() = R.layout.toolbar_activity
}