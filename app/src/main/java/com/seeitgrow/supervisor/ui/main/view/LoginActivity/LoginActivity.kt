package com.seeitgrow.supervisor.ui.main.view.LoginActivity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.seeitgrow.supervisor.data.Storage.SharedPrefManager
import com.seeitgrow.supervisor.data.api.ApiHelper
import com.seeitgrow.supervisor.data.api.RetrofitBuilder
import com.seeitgrow.supervisor.databinding.SignupLoginBinding
import com.seeitgrow.supervisor.ui.base.ViewModelFactory
import com.seeitgrow.supervisor.ui.main.view.ChampionList
import com.seeitgrow.supervisor.ui.main.viewmodel.FarmerViewModel
import com.seeitgrow.supervisor.ui.main.viewmodel.MainViewModel
import com.seeitgrow.supervisor.ui.main.viewmodel.Supervidor_ViewModel
import com.seeitgrow.supervisor.utils.AppUtils
import com.seeitgrow.supervisor.utils.AppUtils.SEASON_CODE
import com.seeitgrow.supervisor.utils.NetworkUtil
import com.seeitgrow.supervisor.utils.Status

@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity() {

    lateinit var _binding: SignupLoginBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var mSupervidorViewModel: Supervidor_ViewModel
    private lateinit var mfarmerviewModel: FarmerViewModel
    private lateinit var progessDialog: ProgressDialog
    private var SupervisorId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = SignupLoginBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        val toolbar = _binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
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
        mSupervidorViewModel = ViewModelProvider(this).get(Supervidor_ViewModel::class.java)
        mfarmerviewModel = ViewModelProvider(this).get(FarmerViewModel::class.java)
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(MainViewModel::class.java)
        _binding.edtMobileno.setText("9940395451")
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
                                        SupervisorId = users[0].SupervisorId
                                        SharedPrefManager.getInstance(applicationContext).saveSupervisorId(SupervisorId!!)
                                        mSupervidorViewModel.addUser(users)
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
                            progessDialog.dismiss()
                            resource.data?.let { it1 -> mfarmerviewModel.addUser(it1) }
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


}