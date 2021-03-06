package com.seeitgrow.supervisor.ui.main.view

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.seeitgrow.supervisor.DataBase.Model.FarmerDetails
import com.seeitgrow.supervisor.R
import com.seeitgrow.supervisor.data.ApiViewModel.MainViewModel
import com.seeitgrow.supervisor.data.Storage.SharedPrefManager
import com.seeitgrow.supervisor.data.api.ApiHelper
import com.seeitgrow.supervisor.data.api.RetrofitBuilder
import com.seeitgrow.supervisor.databinding.ChampionListBinding
import com.seeitgrow.supervisor.ui.base.ViewModelFactory
import com.seeitgrow.supervisor.ui.main.adapter.ChampionAdaptor
import com.seeitgrow.supervisor.ui.main.view.SplashScreen.Splash
import com.seeitgrow.supervisor.ui.main.viewmodel.FarmerViewModel
import com.seeitgrow.supervisor.ui.main.viewmodel.RejectedViewModel
import com.seeitgrow.supervisor.ui.main.viewmodel.Supervisor_ViewModel
import com.seeitgrow.supervisor.utils.AppUtils
import com.seeitgrow.supervisor.utils.NetworkUtil
import com.seeitgrow.supervisor.utils.Status
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
@Suppress("DEPRECATION")
class ChampionList : AppCompatActivity() {

    private lateinit var adapter: ChampionAdaptor
    private lateinit var progessDialog: ProgressDialog
    lateinit var binding: ChampionListBinding
    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
    }
    private val mSupervisorViewModel: Supervisor_ViewModel by viewModels()
    private val mfarmerviewModel: FarmerViewModel by viewModels()
    private val rejectedViewModel: RejectedViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ChampionListBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.logout -> {
                LogOut()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun LogOut() {
        AlertDialog.Builder(this)
            .setMessage("Are you sure you want to sign out?")
            .setPositiveButton("Yes") { dialog: DialogInterface?, which: Int ->
                mSupervisorViewModel.DeleteAll()
                mfarmerviewModel.DeleteAll()
                rejectedViewModel.DeleteAll()

                startActivity(Intent(this, Splash::class.java))
            }
            .setNegativeButton("No") { dialog: DialogInterface?, which: Int -> }
            .show()
    }

    override fun onStart() {
        super.onStart()
        if (!NetworkUtil.getConnectivityStatusString(applicationContext)
                .equals("Not connected to Internet")
        ) {
            getChampionList()
        } else {
            get()
        }
    }

    private fun getChampionList() {
        val id = SharedPrefManager.getInstance(applicationContext).getsupervisorId
        if (id != null) {
            viewModel.getChampionList(id, AppUtils.SEASON_CODE)
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
    }


    private fun get() {
        mfarmerviewModel.readAllFarmerByGroupId()!!.observe(this, {
            if (it != null) {
                reterive(it)

            }
        })


    }

    private fun reterive(users: List<FarmerDetails>) {
        binding.recyclerView.visibility = View.VISIBLE
        val adapter = ChampionAdaptor(users, this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter.notifyDataSetChanged()
    }


    override fun onSupportNavigateUp(): Boolean {
        val startMain = Intent(Intent.ACTION_MAIN)
        startMain.addCategory(Intent.CATEGORY_HOME)
        startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(startMain)
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val startMain = Intent(Intent.ACTION_MAIN)
        startMain.addCategory(Intent.CATEGORY_HOME)
        startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(startMain)
    }
}
