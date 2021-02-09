package com.seeitgrow.supervisor.ui.NavTest

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import coil.load
import com.google.gson.Gson
import com.seeitgrow.supervisor.DataBase.Model.RejectedMessageDetail
import com.seeitgrow.supervisor.Model.SiteListResponse
import com.seeitgrow.supervisor.R
import com.seeitgrow.supervisor.data.ApiViewModel.MainViewModel
import com.seeitgrow.supervisor.data.Storage.SharedPrefManager
import com.seeitgrow.supervisor.data.api.ApiHelper
import com.seeitgrow.supervisor.data.api.RetrofitBuilder
import com.seeitgrow.supervisor.databinding.InitialImageApproveBinding
import com.seeitgrow.supervisor.ui.base.ViewModelFactory
import com.seeitgrow.supervisor.ui.main.viewmodel.FarmerViewModel
import com.seeitgrow.supervisor.ui.main.viewmodel.RejectedViewModel
import com.seeitgrow.supervisor.ui.main.viewmodel.Supervisor_ViewModel
import com.seeitgrow.supervisor.utils.AppUtils
import com.seeitgrow.supervisor.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SiteImageApprove_Fragment : Fragment() {

    lateinit var navController: NavController
    lateinit var _binding: InitialImageApproveBinding
    var RejectedMessage = ArrayList<RejectedMessageDetail>()
    var RejectedArray = ArrayList<String>()
    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
    }
    private val mSupervisorViewModel: Supervisor_ViewModel by viewModels()
    private val mfarmerviewModel: FarmerViewModel by viewModels()
    private val rejectedViewModel: RejectedViewModel by viewModels()
    private lateinit var progessDialog: ProgressDialog
    private lateinit var SiteDetail: SiteListResponse
    private lateinit var rejectComment: String
    private lateinit var ImagePath: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = InitialImageApproveBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        LoadUi()

        _binding.imgInitial.setOnClickListener {
            OpenImage()
        }
        _binding.btnReject.setOnClickListener {
            OpenRejectedPopUp()
        }

        _binding.btnAccept.setOnClickListener {
            AcceptPopUp()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun LoadUi() {
        rejectedViewModel.readAllRejectedMessage()!!.observe(viewLifecycleOwner, Observer {
            if (RejectedMessage != null) {
                RejectedMessage = it as ArrayList<RejectedMessageDetail>

                for (i in RejectedMessage) {
                    RejectedArray.add(i.EnglishDescription.toString())
                }
            }


        })

        val gson = Gson()
        SiteDetail = gson.fromJson(
            SharedPrefManager.getInstance(requireContext()).getSiteDetail!!,
            SiteListResponse::class.java
        )

        _binding.txtTitle.text = SiteDetail.SiteName!!
        _binding.txtSiteCreated.text =
            "CreatedOn : " + SiteDetail.InitialCreatedOn.toString().substring(0, 10)


        val lat = SiteDetail.InitialLatitude
        val long = SiteDetail.InitialLongitude
        if (lat != null && long != null) {
            _binding.txtLocation.text =
                SiteDetail.InitialLatitude.toString() + " , " + SiteDetail.InitialLongitude.toString()
        }

        val image = AppUtils.ImagePath(SiteDetail.SeasonCode!!, "Site")
        ImagePath = image + SiteDetail.InitialImagePath
        _binding.imgInitial.load(let {
            ImagePath
        }) {
            placeholder(R.drawable.wheatfield1)
            crossfade(true)

        }
    }

    private fun OpenRejectedPopUp() {
        val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        // ...Irrelevant code for customizing the buttons and title
        val inflater = this.layoutInflater
        val dialogView: View = inflater.inflate(R.layout.rejected_reason, null)
        dialogBuilder.setView(dialogView)
        val auto_reject: AutoCompleteTextView = dialogView.findViewById(R.id.auto_reject)
        val img_close: LinearLayout = dialogView.findViewById(R.id.img_close)
        val btn_reject: Button = dialogView.findViewById(R.id.btn_reject)


        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                RejectedArray
            )

        auto_reject.setSelection(0)
        auto_reject.setAdapter(adapter)

        auto_reject.onItemClickListener =
            AdapterView.OnItemClickListener { parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
                rejectComment = RejectedArray.get(position)

            }

        val alertDialog: AlertDialog = dialogBuilder.create()
        btn_reject.setOnClickListener { v: View? ->
            run {
                if (rejectComment != null) {
                    alertDialog.dismiss()
                    RejectPopUp()
                } else {
                    AppUtils.showMessage(requireContext(), "Select the reject message for the site")
                }
            }
        }
        img_close.setOnClickListener { v: View? -> alertDialog.dismiss() }
        alertDialog.show()
    }


    private fun RejectPopUp() {
        val dialogClickListener =
            DialogInterface.OnClickListener { dialog, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        updateStatus(
                            SiteDetail.SiteId!!,
                            "-1",
                            SharedPrefManager.getInstance(requireContext()).getsupervisorId!!,
                            rejectComment
                        )
                    }
                    DialogInterface.BUTTON_NEGATIVE -> {
                    }
                }
            }

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Sure you want to Reject Site")
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
            .setNegativeButton("No", dialogClickListener).show()
    }

    private fun AcceptPopUp() {
        val dialogClickListener =
            DialogInterface.OnClickListener { dialog, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        updateStatus(
                            SiteDetail.SiteId!!,
                            "1",
                            SharedPrefManager.getInstance(requireContext()).getsupervisorId!!,
                            "Your - ${SiteDetail.SiteName} picture is accepted by our expert. Thank you for taking the correct picture. Continue taking pictures once a week "
                        )
                    }
                    DialogInterface.BUTTON_NEGATIVE -> {
                    }
                }
            }

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Sure you want to Accept Site")
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
            .setNegativeButton("No", dialogClickListener).show()
    }

    private fun OpenImage() {
        val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        // ...Irrelevant code for customizing the buttons and title
        val inflater = this.layoutInflater
        val dialogView: View = inflater.inflate(R.layout.custom_fullimage_dialog, null)
        dialogBuilder.setView(dialogView)
        val editText: ImageView = dialogView.findViewById(R.id.img_view)
        val img_close: LinearLayout = dialogView.findViewById(R.id.img_close)
        editText.load(let {
            ImagePath
        }) {
            placeholder(R.drawable.ic_account)
            crossfade(true)

        }
        val alertDialog: AlertDialog = dialogBuilder.create()
        img_close.setOnClickListener { v: View? -> alertDialog.dismiss() }
        alertDialog.show()
    }


    private fun updateStatus(
        SiteId: String,
        isApproved: String,
        isApproverId: String,
        approveComment: String
    ) {

        viewModel.updateInitialSatus(SiteId, isApproved, isApproverId, approveComment)
            .observe(requireActivity(), Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            progessDialog.dismiss()
                            navController.navigateUp()
                        }
                        Status.ERROR -> {
                            progessDialog.dismiss()
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
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