package com.seeitgrow.supervisor.ui.main.view.LoginActivity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.seeitgrow.supervisor.data.ApiViewModel.MainViewModel
import com.seeitgrow.supervisor.data.Storage.SharedPrefManager
import com.seeitgrow.supervisor.data.api.ApiHelper
import com.seeitgrow.supervisor.data.api.RetrofitBuilder
import com.seeitgrow.supervisor.databinding.SignupLoginBinding
import com.seeitgrow.supervisor.ui.base.ViewModelFactory
import com.seeitgrow.supervisor.ui.main.view.ChampionList
import com.seeitgrow.supervisor.ui.main.viewmodel.FarmerViewModel
import com.seeitgrow.supervisor.ui.main.viewmodel.RejectedViewModel
import com.seeitgrow.supervisor.ui.main.viewmodel.Supervisor_ViewModel
import com.seeitgrow.supervisor.utils.AppUtils
import com.seeitgrow.supervisor.utils.AppUtils.SEASON_CODE
import com.seeitgrow.supervisor.utils.NetworkUtil
import com.seeitgrow.supervisor.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity() {

    lateinit var _binding: SignupLoginBinding
    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
    }
    private val mSupervisorViewModel: Supervisor_ViewModel by viewModels()
    private val mfarmerviewModel: FarmerViewModel by viewModels()
    private val rejectedViewModel: RejectedViewModel by viewModels()
    private lateinit var progessDialog: ProgressDialog
    private var SupervisorId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = SignupLoginBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        val toolbar = _binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        Loadui()

        _binding.btnProceed.setOnClickListener {
            if (!NetworkUtil.getConnectivityStatusString(applicationContext)
                    .equals("Not connected to Internet")
            ) {
                when {
                    _binding.edtMobileno.text.toString().isEmpty() -> {
                        AppUtils.showMessage(this, "Mobile number cannot be blank")
                    }
                    _binding.edtMobileno.text.toString().length != 10 -> {
                        AppUtils.showMessage(this, "Please Enter Complete Mobile Number")
                    }
                    else -> {
                        getSuperVisorDetails()

                    }
                }
            } else {
                AppUtils.showMessage(this, "Not connected to Internet")
            }
        }
    }

    private fun Loadui() {
//        _binding.edtMobileno.setText("9940395451")
    }

    private fun getSuperVisorDetails() {
        viewModel.getSupervisorDetails(_binding.edtMobileno.text.toString(), SEASON_CODE)
            .observe(this, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            resource.data?.let { users ->
                                run {

                                    if (users[0].Error != null) {
                                        progessDialog.dismiss()
                                        AppUtils.showMessage(this, users[0].Error.toString())
                                    } else {
                                        mfarmerviewModel.DeleteAll()
                                        SupervisorId = users[0].SupervisorId
                                        SharedPrefManager.getInstance(applicationContext)
                                            .saveSupervisorId(SupervisorId!!)
                                        mSupervisorViewModel.addUser(users)
                                        getFarmerDetails()

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


    private fun getFarmerDetails() {

        SupervisorId?.let { it ->
            viewModel.getFarmerList(it, SEASON_CODE).observe(this, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
//                            progessDialog.dismiss()
                            resource.data?.let { it1 -> mfarmerviewModel.addUser(it1) }
                            getRejectedMessage()
//                            startActivity(Intent(this, ChampionList::class.java))
                        }
                        Status.ERROR -> {
                            progessDialog.dismiss()
                            Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                        }
                        Status.LOADING -> {

                        }
                    }
                }
            })
        }
    }

    private fun getRejectedMessage() {
        SupervisorId?.let { _ ->
            viewModel.getRejectedStatus(SEASON_CODE).observe(this, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            progessDialog.dismiss()
                            resource.data?.let { it1 -> rejectedViewModel.addRejectedMessage(it1) }
                            startActivity(Intent(this, ChampionList::class.java))
                        }
                        Status.ERROR -> {
                            progessDialog.dismiss()
                            Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                        }
                        Status.LOADING -> {

                        }
                    }
                }
            })
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        val startMain = Intent(Intent.ACTION_MAIN)
        startMain.addCategory(Intent.CATEGORY_HOME)
        startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(startMain)
    }


}