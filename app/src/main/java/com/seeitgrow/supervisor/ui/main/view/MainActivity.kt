package com.seeitgrow.supervisor.ui.main.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.seeitgrow.supervisor.DataBase.Model.FarmerDetails
import com.seeitgrow.supervisor.DataBase.Model.User
import com.seeitgrow.supervisor.data.api.ApiHelper
import com.seeitgrow.supervisor.data.api.RetrofitBuilder
import com.seeitgrow.supervisor.databinding.ActivityMainBinding
import com.seeitgrow.supervisor.ui.base.ViewModelFactory
import com.seeitgrow.supervisor.ui.main.adapter.MainAdapter
import com.seeitgrow.supervisor.ui.main.viewmodel.FarmerViewModel
import com.seeitgrow.supervisor.ui.main.viewmodel.MainViewModel
import com.seeitgrow.supervisor.ui.main.viewmodel.UserViewModel
import com.seeitgrow.supervisor.utils.Status.*


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MainAdapter
    lateinit var binding: ActivityMainBinding
    private lateinit var mUserViewModel: UserViewModel
    private lateinit var mfarmerviewModel: FarmerViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()
//        setupUI()
        //  setupObservers()
        get()

    }


    private fun setupViewModel() {
        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        mfarmerviewModel = ViewModelProvider(this).get(FarmerViewModel::class.java)

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(MainViewModel::class.java)
    }

    private fun setupUI() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(arrayListOf(),this)
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerView.context,
                (binding.recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.recyclerView.adapter = adapter
    }

    private fun setupObservers() {

        viewModel.getUsers("8883269845").observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    SUCCESS -> {
                        binding.recyclerView.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        resource.data?.let { users ->
                            InsertFarmerDetails(users)
                            /* retrieveList(users)*/
                        }
                    }
                    ERROR -> {
                        binding.recyclerView.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.recyclerView.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun InsertFarmerDetails(farmerDetails: List<FarmerDetails>) {
        mfarmerviewModel.addUser(farmerDetails)

    }

    private fun get() {
        mfarmerviewModel.readAllFarmerByGroupId("2")!!.observe(this, Observer {
            if (it != null) {
                reterive(it)

            }
        })
    }

    private fun reterive(users: List<FarmerDetails>) {
        binding.recyclerView.visibility = View.VISIBLE

        val adapter = MainAdapter(users, this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter.notifyDataSetChanged()
    }


    private fun retrieveList(users: User) {
        adapter.apply {
            val user = User(0, "", "users.phoneNumber", " users.status", "users.modifiedOn")
            mUserViewModel.addUser(user)
//            addUsers(users)
//            notifyDataSetChanged()
        }
    }
}
