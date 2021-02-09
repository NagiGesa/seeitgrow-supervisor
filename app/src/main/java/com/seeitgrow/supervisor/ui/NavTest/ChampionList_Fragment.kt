package com.seeitgrow.supervisor.ui.NavTest

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.seeitgrow.supervisor.DataBase.Model.FarmerDetails
import com.seeitgrow.supervisor.data.ApiViewModel.MainViewModel
import com.seeitgrow.supervisor.data.Storage.SharedPrefManager
import com.seeitgrow.supervisor.data.api.ApiHelper
import com.seeitgrow.supervisor.data.api.RetrofitBuilder
import com.seeitgrow.supervisor.databinding.ChampionListBinding
import com.seeitgrow.supervisor.ui.base.ViewModelFactory
import com.seeitgrow.supervisor.ui.main.adapter.ChampionAdaptor
import com.seeitgrow.supervisor.ui.main.viewmodel.FarmerViewModel
import com.seeitgrow.supervisor.ui.main.viewmodel.RejectedViewModel
import com.seeitgrow.supervisor.ui.main.viewmodel.Supervisor_ViewModel
import com.seeitgrow.supervisor.utils.AppUtils
import com.seeitgrow.supervisor.utils.NetworkUtil
import com.seeitgrow.supervisor.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Suppress("DEPRECATION")
class ChampionList_Fragment : Fragment() {
    lateinit var navController: NavController
    private lateinit var adapter: ChampionAdaptor
    private lateinit var progessDialog: ProgressDialog
    lateinit var _binding: ChampionListBinding
    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
    }
    private val mSupervisorViewModel: Supervisor_ViewModel by viewModels()
    private val mfarmerviewModel: FarmerViewModel by viewModels()
    private val rejectedViewModel: RejectedViewModel by viewModels()
    private lateinit var UserArray: List<FarmerDetails>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ChampionListBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        LoadUi()
    }

    private fun LoadUi() {
        if (!NetworkUtil.getConnectivityStatusString(requireContext())
                .equals("Not connected to Internet")
        ) {
            getChampionList()
        } else {
            get()
        }
    }


    private fun getChampionList() {
        val id = SharedPrefManager.getInstance(requireContext()).getsupervisorId
        if (id != null) {
            viewModel.getChampionList(id, AppUtils.SEASON_CODE)
                .observe(requireActivity(), {
                    it.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                resource.data.let { users ->
                                    progessDialog.dismiss()
                                    if (users?.get(0)?.Error != null) {
                                        AppUtils.showMessage(
                                            requireContext(),
                                            users[0].Error.toString()
                                        )
                                    } else {
                                        users?.let { it1 -> reterive(it1) }
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
    }


    private fun get() {
        mfarmerviewModel.readAllFarmerByGroupId()!!.observe(requireActivity(), {
            if (it != null) {
                reterive(it)

            }
        })


    }

    private fun reterive(users: List<FarmerDetails>) {
        _binding.recyclerView.visibility = View.VISIBLE
        UserArray = users
        val adapter = ChampionAdaptor(UserArray, requireContext())
        _binding.recyclerView.adapter = adapter
        _binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter.notifyDataSetChanged()
    }
}