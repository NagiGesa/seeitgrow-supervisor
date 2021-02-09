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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.seeitgrow.supervisor.Model.SiteListResponse
import com.seeitgrow.supervisor.data.ApiViewModel.MainViewModel
import com.seeitgrow.supervisor.data.api.ApiHelper
import com.seeitgrow.supervisor.data.api.RetrofitBuilder
import com.seeitgrow.supervisor.databinding.SitelistBinding
import com.seeitgrow.supervisor.ui.base.ViewModelFactory
import com.seeitgrow.supervisor.ui.main.adapter.SiteListAdaptor
import com.seeitgrow.supervisor.ui.main.viewmodel.Supervisor_ViewModel
import com.seeitgrow.supervisor.utils.AppUtils
import com.seeitgrow.supervisor.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@Suppress("DEPRECATION")
@AndroidEntryPoint
class SiteList_Fragment : Fragment() {
    lateinit var navController: NavController
    val mSupervisorViewModel: Supervisor_ViewModel by viewModels()
    lateinit var _binding: SitelistBinding
    private val args: SiteList_FragmentArgs? by navArgs()
    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
    }
    private lateinit var progessDialog: ProgressDialog
    private lateinit var championId: String
    private lateinit var farmerName: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SitelistBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        val dataString = requireActivity().intent.dataString


        val farmerId = try {
            if (args?.farmerId.isNullOrEmpty()) dataString else args?.farmerId
        } catch (e: Exception) {
            dataString
        }

        val farmerName = try {
            if (args?.farmerId.isNullOrEmpty()) dataString else args?.farmerId
        } catch (e: Exception) {
            dataString
        }

        if (!farmerId.isNullOrEmpty())
            getPendingSiteList(farmerId)
    }

    private fun getPendingSiteList(id: String) {
        viewModel.getPendingSiteList(id, AppUtils.SEASON_CODE)
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
                                    users?.let { it1 ->
                                        reterive(it1)
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

    private fun reterive(users: List<SiteListResponse>) {
        _binding.recySite.visibility = View.VISIBLE
        val adapter = SiteListAdaptor(users, requireContext())
        _binding.recySite.adapter = adapter
        _binding.recySite.layoutManager = LinearLayoutManager(requireContext())
        adapter.notifyDataSetChanged()
    }
}