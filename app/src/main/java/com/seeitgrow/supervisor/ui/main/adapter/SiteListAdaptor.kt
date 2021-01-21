package com.seeitgrow.supervisor.ui.main.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.google.gson.Gson
import com.seeitgrow.supervisor.Model.SiteListResponse
import com.seeitgrow.supervisor.R
import com.seeitgrow.supervisor.data.Storage.SharedPrefManager
import com.seeitgrow.supervisor.databinding.SitelistadapterViewBinding
import com.seeitgrow.supervisor.ui.main.view.SiteDetails.SiteInitialApprove
import com.seeitgrow.supervisor.utils.AppUtils

class SiteListAdaptor(
    private val SiteListDetail: List<SiteListResponse>,
    private val mContext: Context
) :
    RecyclerView.Adapter<SiteListAdaptor.DataViewHolder>() {
    lateinit var binding: SitelistadapterViewBinding

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataViewHolder {
        binding =
            SitelistadapterViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding.root)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val SiteDetail = SiteListDetail[position]

        binding.txtSiteName.text = SiteDetail.SiteName
        binding.txtPendingCount.text = SiteDetail.TotalPendingImage
        val lat = SiteDetail.InitialLatitude
        val long = SiteDetail.InitialLongitude
        if (lat != null && long != null) {
            binding.txtLocation.text = SiteDetail.InitialLatitude.toString()
                .substring(0, 5) + "," + SiteDetail.InitialLongitude.toString().substring(0, 5)
        }
        val image = AppUtils.ImagePath(SiteDetail.SeasonCode!!, "Site")
        binding.imgFarmerImage.load(

            image + "L311F00004C01S00051Ip.jpg"
        ) {
            crossfade(true)
            placeholder(R.drawable.wheatfield1)
            transformations(CircleCropTransformation())
        }

        binding.imgFrwd.setOnClickListener {
            val details = SiteListDetail[holder.adapterPosition]
            val intent = Intent(mContext, SiteInitialApprove::class.java)
            val gson = Gson()
            val json = gson.toJson(details)
            SharedPrefManager.getInstance(mContext).saveSiteId(json!!)
            mContext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = SiteListDetail.size
}