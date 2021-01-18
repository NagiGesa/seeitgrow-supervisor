package com.seeitgrow.supervisor.ui.main.view

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
import com.seeitgrow.supervisor.ui.main.adapter.MainAdapter
import com.seeitgrow.supervisor.ui.main.viewmodel.FarmerViewModel
import com.seeitgrow.supervisor.ui.main.viewmodel.MainViewModel
import com.seeitgrow.supervisor.ui.main.viewmodel.Supervidor_ViewModel

class SubFarmerList : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MainAdapter
    lateinit var binding: ActivityMainBinding
    private lateinit var mSupervidorViewModel: Supervidor_ViewModel
    private lateinit var mfarmerviewModel: FarmerViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()
        get()

    }


    private fun setupViewModel() {
        mSupervidorViewModel = ViewModelProvider(this).get(Supervidor_ViewModel::class.java)
        mfarmerviewModel = ViewModelProvider(this).get(FarmerViewModel::class.java)

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(MainViewModel::class.java)
    }

    private fun get() {
        mfarmerviewModel.readAllSubFarmerByGroupId("4")!!.observe(this, Observer {
            if (it != null) {
                reterive(it)

            }
        })

        /*   var id = SharedPrefManager.getInstance(applicationContext).getsupervisorId
           AppUtils.showMessage(this, id)*/
    }

    private fun reterive(users: List<FarmerDetails>) {
        binding.recyclerView.visibility = View.VISIBLE
        val adapter = MainAdapter(users, this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter.notifyDataSetChanged()
    }
}