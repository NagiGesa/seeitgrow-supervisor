package com.seeitgrow.supervisor.ui.main.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.google.gson.Gson
import com.seeitgrow.supervisor.Model.SiteListResponse
import com.seeitgrow.supervisor.R
import com.seeitgrow.supervisor.data.Storage.SharedPrefManager
import com.seeitgrow.supervisor.databinding.SitelistadapterViewBinding
import com.seeitgrow.supervisor.ui.main.view.SiteDetails.RepeatPic_Approve
import com.seeitgrow.supervisor.ui.main.view.SiteDetails.SiteInitialApprove
import com.seeitgrow.supervisor.utils.AppUtils


class SiteListAdaptor(
    private val SiteListDetail: List<SiteListResponse>,
    private val mContext: Context
) :
    RecyclerView.Adapter<SiteListAdaptor.DataViewHolder>() {
    lateinit var binding: SitelistadapterViewBinding

    class DataViewHolder(private val binding: SitelistadapterViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {

            }
        }

        fun bind(SiteDetail: SiteListResponse) {
            binding.apply {
                binding.txtSiteName.text = SiteDetail.SiteName
                binding.txtPendingCount.text = "Pending Count: " + SiteDetail.TotalPendingImage

                if (SiteDetail.InitialStatus.equals("0")) {
//                    val unwrappedDrawable =
//                        AppCompatResources.getDrawable(root.context, R.drawable.notification_dot)
//                    val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)
//                    DrawableCompat.setTint(wrappedDrawable, Color.RED)
                    binding.imgNotification.setImageResource(R.drawable.notification_dot_red)
                }else{
                    binding.imgNotification.setImageResource(R.drawable.notification_dot)
                }

                val lat = SiteDetail.InitialLatitude
                val long = SiteDetail.InitialLongitude
                if (lat != null && long != null) {
                    binding.txtLocation.text =
                        SiteDetail.InitialLatitude.toString() + " , " + SiteDetail.InitialLongitude
                }
                val image = AppUtils.ImagePath(SiteDetail.SeasonCode!!, "Site")
                binding.imgFarmerImage.load(
                    image + SiteDetail.InitialImagePath
                ) {
                    crossfade(true)
                    placeholder(R.drawable.wheatfield1)
                    transformations(CircleCropTransformation())
                }

                binding.imgFrwd.setOnClickListener {
                    val gson = Gson()
                    val json = gson.toJson(SiteDetail)
                    SharedPrefManager.getInstance(root.context).saveSiteId(json!!)
                    if (SiteDetail.InitialStatus.equals("0")) {
                        root.context.startActivity(Intent(root.context, SiteInitialApprove::class.java))
                    } else {
                        root.context.startActivity(Intent(root.context, RepeatPic_Approve::class.java))
                    }
                }

            }
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataViewHolder {
        binding =
            SitelistadapterViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val currentNote = SiteListDetail[position]

        if (currentNote != null && position != RecyclerView.NO_POSITION) {
            holder.bind(currentNote)
        }

    }

    override fun getItemCount(): Int = SiteListDetail.size
}