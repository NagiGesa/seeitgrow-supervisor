package com.seeitgrow.supervisor.ui.NavTest

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.seeitgrow.supervisor.R
import com.seeitgrow.supervisor.data.ApiViewModel.MainViewModel
import com.seeitgrow.supervisor.data.Storage.SharedPrefManager
import com.seeitgrow.supervisor.data.api.ApiHelper
import com.seeitgrow.supervisor.data.api.RetrofitBuilder
import com.seeitgrow.supervisor.databinding.SignupLoginBinding
import com.seeitgrow.supervisor.ui.base.ViewModelFactory
import com.seeitgrow.supervisor.ui.main.viewmodel.FarmerViewModel
import com.seeitgrow.supervisor.ui.main.viewmodel.RejectedViewModel
import com.seeitgrow.supervisor.ui.main.viewmodel.Supervisor_ViewModel
import com.seeitgrow.supervisor.utils.AppUtils
import com.seeitgrow.supervisor.utils.NetworkUtil
import com.seeitgrow.supervisor.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    lateinit var navController: NavController
    lateinit var _binding: SignupLoginBinding
    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
    }
    private val mSupervisorViewModel: Supervisor_ViewModel by viewModels()
    private val mfarmerviewModel: FarmerViewModel by viewModels()
    private val rejectedViewModel: RejectedViewModel by viewModels()
    private lateinit var progessDialog: ProgressDialog
    private var SupervisorId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SignupLoginBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        Loadui()

        _binding.btnProceed.setOnClickListener {
            if (!NetworkUtil.getConnectivityStatusString(requireContext())
                    .equals("Not connected to Internet")
            ) {
                when {
                    _binding.edtMobileno.text.toString().isEmpty() -> {
                        AppUtils.showMessage(requireContext(), "Mobile number cannot be blank")
                    }
                    _binding.edtMobileno.text.toString().length != 10 -> {
                        AppUtils.showMessage(
                            requireContext(),
                            "Please Enter Complete Mobile Number"
                        )
                    }
                    else -> {
                        getSuperVisorDetails()

                    }
                }
            } else {
                AppUtils.showMessage(requireContext(), "Not connected to Internet")
            }
        }

    }

    private fun Loadui() {
        _binding.edtMobileno.setText("9940395451")
    }

    private fun getSuperVisorDetails() {
        viewModel.getSupervisorDetails(_binding.edtMobileno.text.toString(), AppUtils.SEASON_CODE)
            .observe(requireActivity(), Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            resource.data?.let { users ->
                                run {

                                    if (users[0].Error != null) {
                                        progessDialog.dismiss()
                                        AppUtils.showMessage(
                                            requireContext(),
                                            users[0].Error.toString()
                                        )
                                    } else {
                                        SupervisorId = users[0].SupervisorId
                                        SharedPrefManager.getInstance(requireContext())
                                            .saveSupervisorId(SupervisorId!!)
                                        mSupervisorViewModel.addUser(users)
                                        getFarmerDetails()

                                    }
                                }
                            }
                        }
                        Status.ERROR -> {
                            progessDialog.dismiss()
                            AppUtils.showMessage(requireContext(), it.message)

                        }
                        Status.LOADING -> {
                            progessDialog = ProgressDialog(requireContext())
                            progessDialog.setMessage("Please Wait...")
                            progessDialog.show()
                        }
                    }
                }
            })
    }


    private fun getFarmerDetails() {

        SupervisorId?.let { it ->
            viewModel.getFarmerList(it, AppUtils.SEASON_CODE).observe(requireActivity(), Observer {
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
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
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
            viewModel.getRejectedStatus(AppUtils.SEASON_CODE).observe(requireActivity(), Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            progessDialog.dismiss()
                            resource.data?.let { it1 -> rejectedViewModel.addRejectedMessage(it1) }
//                            startActivity(Intent(this, ChampionList::class.java))
                            navController.navigate(R.id.action_loginActivity_to_championList_Nav)
                        }
                        Status.ERROR -> {
                            progessDialog.dismiss()
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                        }
                        Status.LOADING -> {

                        }
                    }
                }
            })
        }
    }
}