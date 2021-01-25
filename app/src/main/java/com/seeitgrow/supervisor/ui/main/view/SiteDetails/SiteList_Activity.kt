package com.seeitgrow.supervisor.ui.main.view.SiteDetails

import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.seeitgrow.supervisor.Model.SiteListResponse
import com.seeitgrow.supervisor.data.ApiViewModel.MainViewModel
import com.seeitgrow.supervisor.data.api.ApiHelper
import com.seeitgrow.supervisor.data.api.RetrofitBuilder
import com.seeitgrow.supervisor.databinding.SitelistBinding
import com.seeitgrow.supervisor.ui.base.ViewModelFactory
import com.seeitgrow.supervisor.ui.main.adapter.SiteListAdaptor
import com.seeitgrow.supervisor.ui.main.viewmodel.FarmerViewModel
import com.seeitgrow.supervisor.ui.main.viewmodel.RejectedViewModel
import com.seeitgrow.supervisor.ui.main.viewmodel.Supervisor_ViewModel
import com.seeitgrow.supervisor.utils.AppUtils
import com.seeitgrow.supervisor.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Suppress("DEPRECATION")
class SiteList_Activity : AppCompatActivity() {
    lateinit var _binding: SitelistBinding
    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
    }
    private lateinit var progessDialog: ProgressDialog
    private lateinit var championId: String
    private lateinit var farmerName: String

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
        farmerName = intent.getStringExtra(AppUtils.FARMER_NAME)!!
        _binding.txtTitle.text = farmerName
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onStart() {
        super.onStart()
        getPendingSiteList(championId)

    }


}