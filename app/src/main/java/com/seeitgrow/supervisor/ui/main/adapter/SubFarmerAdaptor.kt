package com.seeitgrow.supervisor.ui.main.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.seeitgrow.supervisor.DataBase.Model.FarmerDetails
import com.seeitgrow.supervisor.R
import com.seeitgrow.supervisor.databinding.FarmerListViewBinding
import com.seeitgrow.supervisor.ui.main.view.SiteDetails.SiteList_Activity
import com.seeitgrow.supervisor.utils.AppUtils
import com.seeitgrow.supervisor.utils.NetworkUtil

class SubFarmerAdaptor(private val users: List<FarmerDetails>, private val mContext: Context) :
    RecyclerView.Adapter<SubFarmerAdaptor.DataViewHolder>() {
    lateinit var binding: FarmerListViewBinding

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        binding = FarmerListViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding.root)
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val Far = users[position]


        binding.txtChampionName.text = Far.FirstName

        binding.txtPendingCount.text =  "Pending Count: " +Far.TotalPendingImage

        binding.imgFarmerImage.load(let {
            "http://52.183.134.41/PBINSURANCE/Pictures/2020/HR/Rabi2020/Sites/L51114F00964C05S00946Ip.jpg"
        }) {
            crossfade(true)
            placeholder(R.drawable.ic_account)
            transformations(CircleCropTransformation())
        }


        binding.imgFrwd.setOnClickListener {
            if (!NetworkUtil.getConnectivityStatusString(mContext)
                    .equals("Not connected to Internet")
            ) {
                val details = users[holder.adapterPosition]
                val intent = Intent(mContext, SiteList_Activity::class.java)
                intent.putExtra(AppUtils.FARMER_ID, details.FarmerID)
                intent.putExtra(AppUtils.FARMER_NAME, details.FirstName)
                mContext.startActivity(intent)
            } else {
                AppUtils.showMessage(mContext, "Not connected to Internet")
            }
        }
    }


}