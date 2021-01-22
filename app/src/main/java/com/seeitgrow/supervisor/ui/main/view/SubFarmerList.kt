package com.seeitgrow.supervisor.ui.main.view

import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.seeitgrow.supervisor.DataBase.Model.FarmerDetails
import com.seeitgrow.supervisor.data.api.ApiHelper
import com.seeitgrow.supervisor.data.api.RetrofitBuilder
import com.seeitgrow.supervisor.databinding.ActivityMainBinding
import com.seeitgrow.supervisor.ui.base.ViewModelFactory
import com.seeitgrow.supervisor.ui.main.adapter.SubFarmerAdaptor
import com.seeitgrow.supervisor.ui.main.viewmodel.FarmerViewModel
import com.seeitgrow.supervisor.data.ApiViewModel.MainViewModel
import com.seeitgrow.supervisor.ui.main.viewmodel.Supervisor_ViewModel
import com.seeitgrow.supervisor.utils.AppUtils
import com.seeitgrow.supervisor.utils.NetworkUtil
import com.seeitgrow.supervisor.utils.Status

@Suppress("DEPRECATION")
class SubFarmerList : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: SubFarmerAdaptor
    lateinit var binding: ActivityMainBinding
    private lateinit var mSupervisorViewModel: Supervisor_ViewModel
    private lateinit var mfarmerviewModel: FarmerViewModel
    private lateinit var progessDialog: ProgressDialog
    private lateinit var championId: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        LoadUi()

    }

    private fun LoadUi() {
        binding.txtTitle.text = "Sub Farmer List"
        championId = intent.getStringExtra(AppUtils.CHAMPION_ID)!!
        setupViewModel()
        if (!NetworkUtil.getConnectivityStatusString(applicationContext)
                .equals("Not connected to Internet")
        ) {
            getChampionList()
        } else {
            championId.let { get(it) }
        }

    }

    private fun getChampionList() {
        viewModel.getSubFarmerList(championId, AppUtils.SEASON_CODE)
            .observe(this, {
                it.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            resource.data.let { users ->
                                progessDialog.dismiss()
                                if (users?.get(0)?.Error != null) {
                                    AppUtils.showMessage(this, users[0].Error.toString())
                                } else {
                                    users?.let { it1 -> reterive(it1) }
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


    private fun setupViewModel() {
        mSupervisorViewModel = ViewModelProvider(this).get(Supervisor_ViewModel::class.java)
        mfarmerviewModel = ViewModelProvider(this).get(FarmerViewModel::class.java)

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(MainViewModel::class.java)
    }

    private fun get(id: String) {
        mfarmerviewModel.readAllSubFarmerByGroupId(id)!!.observe(this, Observer {
            if (it != null) {
                reterive(it)
            }
        })

    }

    private fun reterive(users: List<FarmerDetails>) {
        binding.recyclerView.visibility = View.VISIBLE
        val adapter = SubFarmerAdaptor(users, this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter.notifyDataSetChanged()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}