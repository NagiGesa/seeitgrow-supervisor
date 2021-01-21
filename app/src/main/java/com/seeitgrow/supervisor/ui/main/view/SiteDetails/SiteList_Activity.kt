package com.seeitgrow.supervisor.ui.main.view.SiteDetails

import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.seeitgrow.supervisor.Model.SiteListResponse
import com.seeitgrow.supervisor.data.api.ApiHelper
import com.seeitgrow.supervisor.data.api.RetrofitBuilder
import com.seeitgrow.supervisor.databinding.SitelistBinding
import com.seeitgrow.supervisor.ui.base.ViewModelFactory
import com.seeitgrow.supervisor.ui.main.adapter.SiteListAdaptor
import com.seeitgrow.supervisor.ui.main.viewmodel.MainViewModel
import com.seeitgrow.supervisor.utils.AppUtils
import com.seeitgrow.supervisor.utils.Status

@Suppress("DEPRECATION")
class SiteList_Activity : AppCompatActivity() {
    lateinit var _binding: SitelistBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var progessDialog: ProgressDialog
    private lateinit var championId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = SitelistBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        val toolbar = _binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)


        LoadUi()

    }

    private fun LoadUi() {
        championId = intent.getStringExtra(AppUtils.FARMER_ID)!!
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(MainViewModel::class.java)

        getPendingSiteList(championId)
    }


    private fun getPendingSiteList(id: String) {
        viewModel.getPendingSiteList(id, AppUtils.SEASON_CODE)
            .observe(this, {
                it.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            resource.data.let { users ->
                                progessDialog.dismiss()
                                if (users?.get(0)?.Error != null) {
                                    AppUtils.showMessage(this, users[0].Error.toString())
                                } else {
                                    users?.let { it1 ->
                                        reterive(it1)
                                    }
                                }
                            }
                        }
                        Status.ERROR -> {
                            progessDialog.dismiss()
                            AppUtils.showMessage(this, it.message)

                        }
                        Status.LOADING -> {
                            progessDialog = ProgressDialog(this)
                            progessDialog.setMessage("Please Wait...")
                            progessDialog.show()
                        }
                    }
                }
            })
    }

    private fun reterive(users: List<SiteListResponse>) {
        _binding.recySite.visibility = View.VISIBLE
        val adapter = SiteListAdaptor(users, this)
        _binding.recySite.adapter = adapter
        _binding.recySite.layoutManager = LinearLayoutManager(this)
        adapter.notifyDataSetChanged()
    }
}