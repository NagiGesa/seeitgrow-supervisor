package com.seeitgrow.supervisor.ui.main.adapter

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.seeitgrow.supervisor.Model.SiteListResponse
import com.seeitgrow.supervisor.R
import com.seeitgrow.supervisor.data.ApiViewModel.MainViewModel
import com.seeitgrow.supervisor.data.Storage.SharedPrefManager
import com.seeitgrow.supervisor.data.api.ApiHelper
import com.seeitgrow.supervisor.data.api.RetrofitBuilder
import com.seeitgrow.supervisor.databinding.RepearPicApproveViewBinding
import com.seeitgrow.supervisor.ui.base.ViewModelFactory
import com.seeitgrow.supervisor.ui.main.view.SiteDetails.RepeatPic_Approve
import com.seeitgrow.supervisor.utils.AppUtils
import com.seeitgrow.supervisor.utils.Status
import java.util.*


@Suppress("DEPRECATION")
class RepeatPicAdaptor(
    private val SiteListDetail: List<SiteListResponse>,
    private val rejectedMessage: ArrayList<String>,
    private val mContext: Context,
    private val clicklist: MyListClickListener
) :

    RecyclerView.Adapter<RepeatPicAdaptor.DataViewHolder>() {
    private lateinit var viewModel: MainViewModel
    private lateinit var rejectedComment: String
    lateinit var binding: RepearPicApproveViewBinding
    private lateinit var progessDialog: ProgressDialog

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataViewHolder {
        binding =
            RepearPicApproveViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        viewModel = ViewModelProvider(
            mContext as RepeatPic_Approve,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(MainViewModel::class.java)
        return DataViewHolder(binding.root)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val SiteDetail = SiteListDetail[position]

        binding.txtSiteName.text = SiteDetail.SiteName


        val lat = SiteDetail.InitialLatitude
        val long = SiteDetail.InitialLongitude
        if (lat != null && long != null) {
            binding.txtLocation.text =
                SiteDetail.RepeatLatitude.toString() + " , " + SiteDetail.RepeatLatitude.toString()
        }
        binding.txtSiteCreated.text =
            "CreatedOn : " + SiteDetail.RepeatCreatedOn.toString().substring(0, 10)
        val image = AppUtils.ImagePath(SiteDetail.SeasonCode!!, "Images")
        binding.imgSite.load(

            image + SiteDetail.RepeatImagePath
        ) {
            crossfade(true)
            placeholder(R.drawable.wheatfield1)

        }

        binding.imgSite.setOnClickListener {
            val Detail = SiteListDetail[holder.adapterPosition]
            OpenImage(Detail.RepeatImagePath!!, Detail.SeasonCode!!)
        }

        binding.btnAccept.setOnClickListener {
            val Detail = SiteListDetail[holder.adapterPosition]
            AcceptPopUp(
                Detail.SiteName!!,
                Detail.SiteId!!,
                Detail.ReportsId!!,
                holder.adapterPosition
            )
        }
        binding.btnReject.setOnClickListener {
            val Detail = SiteListDetail[holder.adapterPosition]

            clicklist.onItemRemoveClick(
                holder.adapterPosition,
                "Rejected",
                Detail.SiteId!!,
                Detail.ReportsId!!
            )
        }
    }

    override fun getItemCount(): Int = SiteListDetail.size


    private fun AcceptPopUp(siteName: String, siteId: String, reportId: String, position: Int) {
        val dialogClickListener =
            DialogInterface.OnClickListener { _, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        updateStatus(
                            siteId,
                            "1",
                            reportId,
                            SharedPrefManager.getInstance(mContext).getsupervisorId!!,
                            "Your - $siteName picture is accepted by our expert. Thank you for taking the correct picture. Continue taking pictures once a week ",
                            position
                        )
                    }
                    DialogInterface.BUTTON_NEGATIVE -> {
                    }
                }
            }

        val builder = AlertDialog.Builder(mContext)
        builder.setTitle("Sure you want to Accept Site")
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
            .setNegativeButton("No", dialogClickListener).show()
    }


    private fun OpenImage(imagePath: String, seasonCode: String) {
        val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(mContext)
        // ...Irrelevant code for customizing the buttons and title
        val dialogView: View =
            LayoutInflater.from(mContext).inflate(R.layout.custom_fullimage_dialog, null)
        dialogBuilder.setView(dialogView)
        val editText: ImageView = dialogView.findViewById(R.id.img_view)
        val img_close: LinearLayout = dialogView.findViewById(R.id.img_close)

        var ImageDetail: String?

        ImageDetail = AppUtils.ImagePath(seasonCode, "Repeat") + imagePath
        editText.load(let {
            ImageDetail
        }) {
            placeholder(R.drawable.ic_account)
            crossfade(true)

        }
        val alertDialog: AlertDialog = dialogBuilder.create()
        img_close.setOnClickListener {
            alertDialog.dismiss()
        }
        alertDialog.show()
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
        )
            .observe(mContext as RepeatPic_Approve,
                {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                progessDialog.dismiss()
                                clicklist.onItemRemoveClick(position, "Completed", "", "")
                            }
                            Status.ERROR -> {
                                progessDialog.dismiss()
                                AppUtils.showMessage(mContext, it.message)
                            }
                            Status.LOADING -> {
                                progessDialog = ProgressDialog(mContext)
                                progessDialog.setMessage("Please Wait...")
                                progessDialog.show()
                            }
                        }
                    }
                })
    }

    interface MyListClickListener {
        fun onItemRemoveClick(position: Int, status: String, siteId: String, reportId: String)
    }
}