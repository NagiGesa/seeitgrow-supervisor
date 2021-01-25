package com.seeitgrow.supervisor.ui.main.view.SiteDetails

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.google.gson.Gson
import com.seeitgrow.supervisor.DataBase.Model.RejectedMessageDetail
import com.seeitgrow.supervisor.Model.SiteListResponse
import com.seeitgrow.supervisor.R
import com.seeitgrow.supervisor.data.ApiViewModel.MainViewModel
import com.seeitgrow.supervisor.data.Storage.SharedPrefManager
import com.seeitgrow.supervisor.data.api.ApiHelper
import com.seeitgrow.supervisor.data.api.RetrofitBuilder
import com.seeitgrow.supervisor.databinding.RepeatpicApproveBinding
import com.seeitgrow.supervisor.ui.base.ViewModelFactory
import com.seeitgrow.supervisor.ui.main.adapter.RepeatPicAdaptor
import com.seeitgrow.supervisor.ui.main.viewmodel.FarmerViewModel
import com.seeitgrow.supervisor.ui.main.viewmodel.RejectedViewModel
import com.seeitgrow.supervisor.ui.main.viewmodel.Supervisor_ViewModel
import com.seeitgrow.supervisor.utils.AppUtils
import com.seeitgrow.supervisor.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Suppress("DEPRECATION")
class RepeatPic_Approve : AppCompatActivity(), RepeatPicAdaptor.MyListClickListener {

    lateinit var _binding: RepeatpicApproveBinding
    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
    }
    private val mSupervisorViewModel: Supervisor_ViewModel by viewModels()
    private val mfarmerviewModel: FarmerViewModel by viewModels()
    private val rejectedViewModel: RejectedViewModel by viewModels()
    private lateinit var progessDialog: ProgressDialog
    private lateinit var SiteDetail: SiteListResponse
    private lateinit var farmerId: String
    private lateinit var ImagePath: String
    var RejectedMessage = ArrayList<RejectedMessageDetail>()
    var RejectedArray = ArrayList<String>()
    private lateinit var adapter: RepeatPicAdaptor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = RepeatpicApproveBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        val toolbar = _binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        LoadUi()

        _binding.imgInitial.setOnClickListener {
            OpenImage(ImagePath, "Site")
        }
    }

    @SuppressLint("SetTextI18n")
    private fun LoadUi() {
        rejectedViewModel.readAllRejectedMessage()!!.observe(this, Observer {
            RejectedMessage = it as ArrayList<RejectedMessageDetail>

            for (i in RejectedMessage) {
                RejectedArray.add(i.EnglishDescription.toString())
            }


        })
        val gson = Gson()
        SiteDetail = gson.fromJson(
            SharedPrefManager.getInstance(applicationContext).getSiteDetail!!,
            SiteListResponse::class.java
        )

        val image = AppUtils.ImagePath(SiteDetail.SeasonCode!!, "Site")
        farmerId = SiteDetail.FarmerID!!
        ImagePath = image + SiteDetail.InitialImagePath
        _binding.imgInitial.load(
            ImagePath
        ) {
            crossfade(true)
            placeholder(R.drawable.wheatfield1)

        }
        val lat = SiteDetail.InitialLatitude
        val long = SiteDetail.InitialLongitude
        if (lat != null && long != null) {
            _binding.txtLocation.text =
                SiteDetail.InitialLatitude.toString() + " , " + SiteDetail.InitialLongitude.toString()
        }
        _binding.txtSiteCreated.text =
            "CreatedOn : " + SiteDetail.InitialCreatedOn.toString().substring(0, 10)
        getPendingSiteList(SiteDetail.SiteId!!)
        _binding.txtTitle.text = SiteDetail.SiteName
    }

    private fun OpenImage(imagePath: String, imageType: String) {
        val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
        // ...Irrelevant code for customizing the buttons and title
        val inflater = this.layoutInflater
        val dialogView: View = inflater.inflate(R.layout.custom_fullimage_dialog, null)
        dialogBuilder.setView(dialogView)
        val editText: ImageView = dialogView.findViewById(R.id.img_view)
        val img_close: LinearLayout = dialogView.findViewById(R.id.img_close)

        var ImageDetail: String? = null

        (if (imageType.equals("Site")) {
            imagePath
        } else {
            AppUtils.ImagePath(SiteDetail.SeasonCode!!, "Repeat") + imagePath
        }).also { ImageDetail = it }
        editText.load(let {
            ImageDetail
        }) {
            placeholder(R.drawable.ic_account)
            crossfade(true)

        }
        val alertDialog: AlertDialog = dialogBuilder.create()
        img_close.setOnClickListener { v: View? -> alertDialog.dismiss() }
        alertDialog.show()
    }


    private fun getPendingSiteList(siteIid: String) {
        viewModel.getPendingSiteImage(siteIid, AppUtils.SEASON_CODE)
            .observe(this, {
                it.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            resource.data.let { users ->
                                progessDialog.dismiss()
                                if (users?.get(0)?.Error != null) {
                                    AppUtils.showMessage(this, users[0].Error.toString())
                                } else {
                                    users?.let { it1 ->
                                        reterive(it1)
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

    private fun reterive(users: List<SiteListResponse>) {
        _binding.recySite.visibility = View.VISIBLE
        adapter = RepeatPicAdaptor(users, RejectedArray, this, this)
        _binding.recySite.adapter = adapter
        _binding.recySite.layoutManager = LinearLayoutManager(this)
        adapter.notifyDataSetChanged()
    }

    private fun OpenRejectedPopUp(siteId: String, reportId: String, position: Int) {
        val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
        val dialogView: View = LayoutInflater.from(this).inflate(R.layout.rejected_reason, null)

        dialogBuilder.setView(dialogView)
        val auto_reject: AutoCompleteTextView = dialogView.findViewById(R.id.auto_reject)
        val img_close: LinearLayout = dialogView.findViewById(R.id.img_close)
        val btn_reject: Button = dialogView.findViewById(R.id.btn_reject)


        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, RejectedArray)

        auto_reject.setSelection(0)
        auto_reject.setAdapter(adapter)

        var rejectComment: String? = null

        AdapterView.OnItemClickListener { _: AdapterView<*>?, _: View?, position: Int, _: Long ->
            rejectComment = RejectedArray.get(position)

        }.also { auto_reject.onItemClickListener = it }

        val alertDialog: AlertDialog = dialogBuilder.create()
        btn_reject.setOnClickListener { _: View? ->
            run {
                if (rejectComment != null) {
                    alertDialog.dismiss()
                    RejectPopUp(rejectComment!!, siteId, reportId, position)
                } else {
                    AppUtils.showMessage(this, "Select the reject message for the site")
                }
            }
        }
        img_close.setOnClickListener { alertDialog.dismiss() }
        alertDialog.show()
    }

    private fun RejectPopUp(
        rejectComment: String,
        siteId: String,
        reportId: String,
        position: Int
    ) {
        val dialogClickListener =
            DialogInterface.OnClickListener { _, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        updateStatus(
                            siteId,
                            "-1",
                            reportId,
                            SharedPrefManager.getInstance(this).getsupervisorId!!,
                            rejectComment,
                            position
                        )
                    }
                    DialogInterface.BUTTON_NEGATIVE -> {
                    }
                }
            }

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Sure you want to Reject Site")
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
            .setNegativeButton("No", dialogClickListener).show()
    }

    private fun updateStatus(
        SiteId: String,
        isApproved: String,
        reportId: String,
        isApproverId: String,
        approveComment: String,
        position: Int
    ) {

        viewModel.updateRepeatPictureStatus(
            SiteId,
            isApproved,
            reportId,
            isApproverId,
            approveComment
        ).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        progessDialog.dismiss()
                        adapter.notifyItemRemoved(position)
                    }
                    Status.ERROR -> {
                        progessDialog.dismiss()
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
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

    override fun onItemRemoveClick(
        position: Int,
        status: String,
        SiteId: String,
        reportId: String
    ) {
        if (status.equals("Completed")) {
            adapter.notifyItemRemoved(position)
        } else {
            OpenRejectedPopUp(SiteId, reportId, position)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}