package com.seeitgrow.supervisor.ui.NavTest

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.seeitgrow.supervisor.DataBase.Model.FarmerDetails
import com.seeitgrow.supervisor.data.ApiViewModel.MainViewModel
import com.seeitgrow.supervisor.data.api.ApiHelper
import com.seeitgrow.supervisor.data.api.RetrofitBuilder
import com.seeitgrow.supervisor.databinding.SubfarmerListBinding
import com.seeitgrow.supervisor.ui.base.ViewModelFactory
import com.seeitgrow.supervisor.ui.main.adapter.SubFarmerAdaptor
import com.seeitgrow.supervisor.ui.main.viewmodel.FarmerViewModel
import com.seeitgrow.supervisor.ui.main.viewmodel.Supervisor_ViewModel
import com.seeitgrow.supervisor.utils.AppUtils
import com.seeitgrow.supervisor.utils.NetworkUtil
import com.seeitgrow.supervisor.utils.Status
import dagger.hilt.android.AndroidEntryPoint


@Suppress("DEPRECATION")
@AndroidEntryPoint
class SubFarmerList_Fragment : Fragment() {
    private val args: SubFarmerList_FragmentArgs? by navArgs()
    lateinit var navController: NavController
    val mSupervisorViewModel: Supervisor_ViewModel by viewModels()
    lateinit var _binding: SubfarmerListBinding
    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
    }
    private val mfarmerviewModel: FarmerViewModel by viewModels()
    private lateinit var progessDialog: ProgressDialog
    private lateinit var UserArray: List<FarmerDetails>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SubfarmerListBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        val dataString = requireActivity().intent.dataString

        val data = try {
            if (args?.data.isNullOrEmpty()) dataString else args?.data
        } catch (e: Exception) {
            dataString
        }

        if (!data.isNullOrEmpty())
            Loadui(data)

    }

    private fun Loadui(championId: String) {
        if (!NetworkUtil.getConnectivityStatusString(requireContext())
                .equals("Not connected to Internet")
        ) {
            getChampionList(championId)
        } else {
            championId.let { get(it) }
        }
    }

    private fun getChampionList(championId: String) {
        viewModel.getSubFarmerList(championId, AppUtils.SEASON_CODE)
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


    private fun get(id: String) {
        mfarmerviewModel.readAllSubFarmerByGroupId(id)!!.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                reterive(it)
            }
        })

    }

    private fun reterive(users: List<FarmerDetails>) {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        _binding.recyclerView.layoutManager = layoutManager
        _binding.recyclerView.visibility = View.VISIBLE
        UserArray = users
        val adapter = SubFarmerAdaptor(UserArray, requireContext())
        _binding.recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }
}